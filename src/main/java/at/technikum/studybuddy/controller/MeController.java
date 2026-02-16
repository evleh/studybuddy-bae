package at.technikum.studybuddy.controller;

import at.technikum.studybuddy.dto.BoxDto;
import at.technikum.studybuddy.dto.UserDtoPrivilegedInfo;
import at.technikum.studybuddy.security.UserPrincipal;
import at.technikum.studybuddy.service.BoxService;
import at.technikum.studybuddy.service.UserService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@CrossOrigin
@RestController
@RequestMapping("/me")
public class MeController {
    private final BoxService boxService;
    private final UserService userService;

    public MeController(BoxService boxService, UserService userService) {
        this.boxService = boxService;
        this.userService = userService;
    }

    private Long readUserIdFromSecurityContext() {
        UserPrincipal userPrincipal = (UserPrincipal) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return userPrincipal.getId();
    }

    @GetMapping("/boxes")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_REGISTERED')")
    public List<BoxDto> readOwnBoxes() {
        return this.boxService.readBoxesOfUser(this.readUserIdFromSecurityContext()).stream().map(BoxDto::new).toList();
    }

    @GetMapping("/self")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_REGISTERED')")
    public UserDtoPrivilegedInfo readOwnUserInfo() {
        var userInfo = this.userService.read(this.readUserIdFromSecurityContext());;
        return new UserDtoPrivilegedInfo(userInfo);
    }

}
