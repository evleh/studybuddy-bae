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

    public PublicController(BoxService boxService) { this.boxService = boxService; };

    /**
     * TODO: without further information the frontend has few options for sorting and/or prioritization of display. :-/
     *       Sorting likely to be based on order-of-add or some other internal thing I suspect.
     * @return public box info of public boxes (list of dto)
     */
    @GetMapping("/boxes")
    public List<PublicBoxInfoDto> readPublicBoxes() {
        return this.boxService.readPublicBoxes().stream().map(PublicBoxInfoDto::new).toList();
    }

}
