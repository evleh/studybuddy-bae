package at.technikum.studybuddy.config;

import at.technikum.studybuddy.service.UserService;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
public class StartUpEventListener {

    private final UserService userService;

    public StartUpEventListener(UserService userService) {
        this.userService = userService;
    }


    @EventListener
    @Transactional
    public void onApplicationReadyEvent(ApplicationReadyEvent event) {
        System.out.println("Application Ready");
        userService.createUserAdminIfNecessary();
    }
}
