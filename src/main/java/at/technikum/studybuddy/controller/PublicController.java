package at.technikum.studybuddy.controller;

import at.technikum.studybuddy.dto.PublicBoxInfoDto;
import at.technikum.studybuddy.service.BoxService;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@CrossOrigin
@RestController
@RequestMapping("/public")
public class PublicController {
    private final BoxService boxService;

    public PublicController(BoxService boxService) {
        this.boxService = boxService;
    };

    /**
     * @return public box info of public boxes (list of dto)
     */
    @GetMapping("/boxes")
    public List<PublicBoxInfoDto> readPublicBoxes() {
        return this.boxService.readPublicBoxes().stream().map(PublicBoxInfoDto::new).toList();
    }

}
