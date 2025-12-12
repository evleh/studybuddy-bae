package at.technikum.studybuddy.controller;

import at.technikum.studybuddy.dto.Registration;
import at.technikum.studybuddy.dto.UserDto;
import at.technikum.studybuddy.dto.UserDtoPrivilegedInfo;
import at.technikum.studybuddy.dto.UserDtoPublicInfo;
import at.technikum.studybuddy.entity.User;
import at.technikum.studybuddy.exceptions.EntityNotFoundException;
import at.technikum.studybuddy.security.RoleTypes;
import at.technikum.studybuddy.service.UserService;
import jakarta.annotation.security.RolesAllowed;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin
@RestController
@RequestMapping("/users")
public class UserController {
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    @RolesAllowed(RoleTypes.ADMIN)
    public List<UserDto> readAll() {
        return this.userService.readAll().stream()
                .map(UserDtoPrivilegedInfo::new)
                .map(UserDtoPrivilegedInfo::downCastToAbstract)
                .toList();
    }

    @GetMapping("/{id}")
    public UserDto read(@PathVariable long id) {
        return new UserDtoPrivilegedInfo(this.userService.read(id));
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public User create (@RequestBody @Valid Registration registration) {
        return this.userService.register(registration);
    }

    @PutMapping("/{id}")
    @RolesAllowed({RoleTypes.ADMIN, RoleTypes.REGISTERED})
    public UserDto update(
            @PathVariable long id,
            @RequestBody UserDtoPublicInfo userDto
    ) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String userName = authentication.getName(); // inquiries into getting-the-id with springboot went nowhere;
        User requester = userService.getByUsername(userName);
        User toUpdate = userService.getByUsername(userDto.getUsername()); // id/name redundant information atm.

        boolean requesterHasAdminRole = requester.isAdmin();
        boolean requesterEqualUserToUpdate = (requester.getId() == userDto.getId());

        //throw new EntityUpdateNotAllowedException("hello worlds");
        throw new EntityNotFoundException("aaaaaa");

        /*if (requesterHasAdminRole || requesterEqualUserToUpdate) {
            // to the thing with the update
            //return this.userService.update(id,user);
            return userDto;
        } else {
            throw new EntityUpdateNotAllowedException("403: Update denied.");
        }*/
    }

    @DeleteMapping("/{id}")
    @RolesAllowed(RoleTypes.ADMIN)
    public User delete (@PathVariable int id){
        return userService.delete(id);
    }
}
