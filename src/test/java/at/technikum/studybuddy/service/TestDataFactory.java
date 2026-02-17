package at.technikum.studybuddy.service;

import at.technikum.studybuddy.dto.BoxCommentDto;
import at.technikum.studybuddy.entity.Box;
import at.technikum.studybuddy.entity.BoxComment;
import at.technikum.studybuddy.entity.User;

public class TestDataFactory {
    static User user(Long id) {
        User u = new User();
        u.setId(id);
        u.setUsername("user" + id);
        return u;
    }

    static Box box(Long id, User owner, boolean isPublic) {
        Box b = new Box();
        b.setId(id);
        b.setOwner(owner);
        b.setPublic(isPublic);
        return b;
    }

    static BoxComment comment(User author, Box box) {
        BoxComment c = new BoxComment();
        c.setAuthor(author);
        c.setBox(box);
        return c;
    }

    static BoxCommentDto boxCommentDto(Long boxId) {
        BoxCommentDto dto = new BoxCommentDto();
        dto.setBoxId(boxId);
        dto.setText("test comment");
        return dto;
    }

}
