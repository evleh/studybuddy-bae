package at.technikum.studybuddy.controller;

import at.technikum.studybuddy.entity.FileInfo;
import at.technikum.studybuddy.security.RoleTypes;
import at.technikum.studybuddy.dto.FileDownload;
import at.technikum.studybuddy.security.UserPrincipal;
import at.technikum.studybuddy.service.FileService;
import jakarta.annotation.security.RolesAllowed;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@CrossOrigin
@RestController
@RequestMapping("/api/files")
public class FileController {

    private final FileService fileService;


    public FileController(FileService fileService) {

        this.fileService = fileService;
    }

    @PostMapping(value = "/upload",consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<String> uploadFile(@RequestPart("file") MultipartFile file, @AuthenticationPrincipal UserPrincipal user) {
        try {
            return ResponseEntity.ok(fileService.saveFile(file, user).getFileName());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error: " + e.getMessage());
        }
    }

    @GetMapping("/list")
    @RolesAllowed(RoleTypes.ADMIN)
    public ResponseEntity<List<String>> listFiles() {
        try {
            return ResponseEntity.ok(fileService.listFiles());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    @GetMapping("/list-fileinfo")
    @RolesAllowed(RoleTypes.ADMIN)
    public ResponseEntity<List<FileInfo>> listFileInfos() {
        try {
            List<FileInfo> fileInfos = fileService.listFileInfos();
            return ResponseEntity.ok(fileInfos);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }


    @GetMapping("/view/{fileName}")
    public ResponseEntity<Resource> viewFile(@PathVariable String fileName) {
        try {
            FileDownload fileDownload = fileService.getFileStream(fileName);

            return ResponseEntity.ok()
                    .contentType(MediaType.parseMediaType(fileDownload.contentType()))
                    .header(HttpHeaders.CONTENT_DISPOSITION, "inline; fileName=\"" + fileName + "\"")
                    .body(new InputStreamResource(fileDownload.stream()));

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @DeleteMapping("/delete/{fileName}")
    public ResponseEntity<String> deleteFile(@PathVariable String fileName, @AuthenticationPrincipal UserPrincipal user) {
        try {
            fileService.deleteFile(fileName, user);
            return ResponseEntity.ok("File deleted successfully: " + fileName);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error: " + e.getMessage());
        }
    }


}