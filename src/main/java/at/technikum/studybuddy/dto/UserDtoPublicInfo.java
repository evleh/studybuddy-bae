package at.technikum.studybuddy.dto;

import at.technikum.studybuddy.entity.User;

import java.net.URL;

public class UserDtoPublicInfo extends UserDto {

    public UserDtoPublicInfo() { };
    public UserDtoPublicInfo(User user) {
        super(user);
    }


}
