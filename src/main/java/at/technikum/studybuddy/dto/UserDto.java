package at.technikum.studybuddy.dto;

import at.technikum.studybuddy.entity.User;

public abstract class UserDto {
    /*
     * Abstract class, defines a constructor signature
     */

    UserDto() {}
    UserDto(User fromUserEntity) {}
}
