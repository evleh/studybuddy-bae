package at.technikum.studybuddy.service;

import at.technikum.studybuddy.dto.FileDownload;
import at.technikum.studybuddy.entity.Box;
import at.technikum.studybuddy.entity.FileInfo;
import at.technikum.studybuddy.entity.User;
import at.technikum.studybuddy.exceptions.PermissionDeniedException;
import at.technikum.studybuddy.exceptions.ResourceNotFoundException;
import at.technikum.studybuddy.repository.FileInfoRepository;
import at.technikum.studybuddy.repository.UserRepository;
import at.technikum.studybuddy.security.UserPrincipal;
import io.minio.*;
import io.minio.messages.Item;
import jakarta.transaction.Transactional;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class FileService {

    private MinioClient minioClient;

    private FileInfoRepository fileInfoRepository;
    private UserRepository userRepository;

    private final String bucket = "studybuddybucket";

    public FileService(MinioClient minioClient, FileInfoRepository fileInfoRepository, UserRepository userRepository) {
        this.minioClient = minioClient;
        this.fileInfoRepository = fileInfoRepository;
        this.userRepository = userRepository;
    }


    @Transactional
    public FileInfo saveFile(MultipartFile file, UserPrincipal user) throws Exception{
        String fileName = UUID.randomUUID().toString() + "-" + file.getOriginalFilename();
        String originalFilename = file.getOriginalFilename();

        minioClient.putObject(
                PutObjectArgs.builder()
                        .bucket(bucket)
                        .object(fileName)
                        .stream(file.getInputStream(), file.getSize(), -1)
                        .contentType(file.getContentType())
                        .build());

        FileInfo fileInfo = new FileInfo();
        fileInfo.setOriginalFilename(originalFilename);
        fileInfo.setFileName(fileName);
        fileInfo.setContentType(file.getContentType());
        Optional<User> owner = this.userRepository.findByUsername(user.getUsername());
        if(owner.isEmpty()){
            throw new ResourceNotFoundException();
        }else{
            fileInfo.setOwner(owner.get());
        }



        return fileInfoRepository.save(fileInfo);

    }

    public List<String> listFiles() throws Exception {
        List<String> fileNames = new ArrayList<>();
        Iterable<Result<Item>> results = minioClient.listObjects(
                ListObjectsArgs.builder().bucket(bucket).build());
        for (Result<Item> result : results) {
            fileNames.add(result.get().objectName());
        }
        return fileNames;
    }

    public FileDownload getFileStream(String fileName) throws Exception {
        InputStream stream = minioClient.getObject(
                GetObjectArgs.builder()
                        .bucket(bucket)
                        .object(fileName)
                        .build()
        );

        String contentType = URLConnection.guessContentTypeFromName(fileName);
        if (contentType == null) {
            contentType = MediaType.APPLICATION_OCTET_STREAM_VALUE;
        }

        return new FileDownload(stream, contentType);
    }

    public void deleteFile(String fileName, UserPrincipal user) throws Exception {
        FileInfo fileInfo = fileInfoRepository.findById(fileName).orElseThrow(ResourceNotFoundException::new);
        if(!fileInfo.getOwner().getId().equals(user.getId()) && !user.isStudyBuddyAdmin()) {
            throw new PermissionDeniedException();
        }
        minioClient.removeObject(
                RemoveObjectArgs.builder().bucket(bucket).object(fileName).build());
    }


}

