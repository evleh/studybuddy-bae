package at.technikum.studybuddy.service;

import at.technikum.studybuddy.entity.CardProgress;
import at.technikum.studybuddy.exceptions.ResourceNotFoundException;
import at.technikum.studybuddy.repository.UserRepository;
import at.technikum.studybuddy.entity.User;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {
    private UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public List<User> getAll(){
        return this.userRepository.findAll();
    }

    public User get(long id) {
        Optional<User> user = this.userRepository.findById(id);
        if(user.isEmpty()){
            throw new ResourceNotFoundException();
        }
        return user.get();
    }

    public User create(User user){
        return this.userRepository.save(user);
    }

    public User update(long id, User user) {
        Optional<User> findUser = userRepository.findById(id); // nur save wenn es schon existiert
        if(findUser.isEmpty()){
            throw new ResourceNotFoundException();
        }

        this.userRepository.save(user);
        return findUser.get();
    }

    public User delete(long id){
        Optional<User> user = this.userRepository.findById(id);
        if(user.isEmpty()){
            throw new ResourceNotFoundException();
        }
        this.userRepository.deleteById(id);
        return user.get();
    }
}
