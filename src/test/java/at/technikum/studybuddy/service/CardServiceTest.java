package at.technikum.studybuddy.service;

import at.technikum.studybuddy.dto.CardDto;
import at.technikum.studybuddy.entity.Box;
import at.technikum.studybuddy.entity.Card;
import at.technikum.studybuddy.entity.User;
import at.technikum.studybuddy.exceptions.PermissionDeniedException;
import at.technikum.studybuddy.exceptions.ResourceNotFoundException;
import at.technikum.studybuddy.repository.CardRepository;
import at.technikum.studybuddy.security.RoleTypes;
import at.technikum.studybuddy.security.UserPrincipal;
import org.jspecify.annotations.NonNull;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@ExtendWith(MockitoExtension.class)
class CardServiceTest {

    @Mock
    private CardRepository cardRepository;
    @Mock
    private BoxService boxService;

    @InjectMocks
    private CardService cardService;

    /**
     * Useful shortcuts; syntax partly via IDE refactor
     */
    private static @NonNull UserPrincipal getSimpleRegisteredPrincipal() {
        var authorities = List.of(new SimpleGrantedAuthority(RoleTypes.REGISTERED));
        return new UserPrincipal(17L,"testRequester","", authorities);
    }
    private static @NonNull UserPrincipal getSimpleAdminPrincipal() {
        var authorities = List.of(new SimpleGrantedAuthority(RoleTypes.ADMIN));
        return new UserPrincipal(23L,"testAdmin","", authorities);
    }



    @Test
    void readAllReturnsZeroItemsForNonAdminRequester() {
        var requester = getSimpleRegisteredPrincipal();

        var readAllResults = cardService.readAll(requester);

        assertEquals(0, readAllResults.size());
    }

    @Test
    void readAllDoesReturnItemsForAdminRequester() {
        var requester = getSimpleAdminPrincipal();
        var cardToRead = new Card();
        Mockito.when(cardRepository.findAll())
                .thenReturn(List.of(cardToRead));

        var readAllResults = cardService.readAll(requester);

        assertEquals(1, readAllResults.size());
    }

    @Test
    void readCardThrowsDeniedIfNotFoundAndNotAdmin() {
        var requester = getSimpleRegisteredPrincipal();
        Mockito.when(cardRepository.findById(1L)).thenThrow(ResourceNotFoundException.class);

        assertThrows(PermissionDeniedException.class,() -> {
            cardService.read(1L,requester);
        });
    }

    @Test
    void readCardThrowsActualNotFoundIfNotFoundAndAdmin() {
        var requester = getSimpleAdminPrincipal();
        Mockito.when(cardRepository.findById(1L)).thenThrow(ResourceNotFoundException.class);

        assertThrows(ResourceNotFoundException.class,() -> {
            cardService.read(1L,requester);
        });
    }

    @Test
    void updateCardThrowsDeniedIfNotFoundAndNotAdmin() {
        var requester = getSimpleRegisteredPrincipal();
        Mockito.when(cardRepository.findById(1L)).thenThrow(ResourceNotFoundException.class);
        var cardDtoToUpdate = new CardDto();

        assertThrows(PermissionDeniedException.class,() -> {
            cardService.update(1L,cardDtoToUpdate,requester);
        });
    }

    @Test
    void updateCardThrowsThrowsActualNotFoundIfNotFoundAndAdmin() {
        var requester = getSimpleAdminPrincipal();
        Mockito.when(cardRepository.findById(1L)).thenThrow(ResourceNotFoundException.class);
        var cardDtoToUpdate = new CardDto();

        assertThrows(ResourceNotFoundException.class,() -> {
            cardService.update(1L,cardDtoToUpdate,requester);
        });
    }

    @Test
    void updateCardWithOnlyTitleDtoAndRequesterBeingCardOwnerCallsSaveMethodOnce() {
        var userId = 177L;
        var boxId = 31L;
        var cardId = 5L;

        var requester = new UserPrincipal(userId,"testRequester","",
                List.of(new SimpleGrantedAuthority(RoleTypes.REGISTERED)));
        // arrange box and card initial
        var box = new Box();
        box.setId(boxId);
        box.setPublic(true);
        var owner = new User();
        owner.setId(userId);
        box.setOwner(owner);
        var card = new Card();
        card.setId(cardId);
        card.setBox(box);
        String preChangeAnswer = "Old Answer";
        String postChangeAnswer = "New Answer"; // never checked, not sure if reasonable to try. research needed.
        card.setAnswer(preChangeAnswer);

        // instruct the mock
        Mockito.when(cardRepository.findById(Mockito.anyLong()))
                .thenReturn(Optional.of(card));
        Mockito.when(cardRepository.save(Mockito.any(Card.class)))
                .thenReturn(card);

        // arrange dto for update
        var updateCardDto = new CardDto();
        updateCardDto.setAnswer(postChangeAnswer);

        // act
        var updatedCard = cardService.update(cardId,updateCardDto,requester);

        // assert
        Mockito.verify(cardRepository, Mockito.times(1)).save(Mockito.any(Card.class));

    }

}