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
import static org.mockito.ArgumentMatchers.eq;

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
    private static @NonNull UserPrincipal getSimpleRegisteredPrincipal(Long userId) {
        var authorities = List.of(new SimpleGrantedAuthority(RoleTypes.REGISTERED));
        return new UserPrincipal(userId,"testRequester"+userId.toString(),"", authorities);
    }
    private static @NonNull UserPrincipal getSimpleAdminPrincipal() {
        var authorities = List.of(new SimpleGrantedAuthority(RoleTypes.ADMIN));
        return new UserPrincipal(23L,"testAdmin","", authorities);
    }



    @Test
    void readAllThrowsPermissionDeniedForNonAdminRequester() {
        var requester = getSimpleRegisteredPrincipal();

        assertThrows(PermissionDeniedException.class, () -> {
            cardService.readAll(requester);
        });
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
        Mockito.when(cardRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(PermissionDeniedException.class,() -> {
            cardService.read(1L,requester);
        });
    }

    @Test
    void readCardThrowsActualNotFoundIfNotFoundAndAdmin() {
        var requester = getSimpleAdminPrincipal();
        Mockito.when(cardRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class,() -> {
            cardService.read(1L,requester);
        });
    }

    @Test
    void updateCardThrowsDeniedIfNotFoundAndNotAdmin() {
        var requester = getSimpleRegisteredPrincipal();
        Mockito.when(cardRepository.findById(1L)).thenReturn(Optional.empty());
        var cardDtoToUpdate = new CardDto();

        assertThrows(PermissionDeniedException.class,() -> {
            cardService.update(1L,cardDtoToUpdate,requester);
        });
        // also check save was never called
        Mockito.verify(cardRepository, Mockito.times(0)).save(Mockito.any(Card.class));
    }

    @Test
    void updateCardThrowsThrowsActualNotFoundIfNotFoundAndAdmin() {
        var requester = getSimpleAdminPrincipal();
        Mockito.when(cardRepository.findById(1L)).thenReturn(Optional.empty());
        var cardDtoToUpdate = new CardDto();

        assertThrows(ResourceNotFoundException.class,() -> {
            cardService.update(1L,cardDtoToUpdate,requester);
        });
        // also check save was never called
        Mockito.verify(cardRepository, Mockito.times(0)).save(Mockito.any(Card.class));
    }

    @Test
    void updateCardWithOnlyTitleDtoAndRequesterBeingCardOwnerCallsSaveMethodOnce() {
        var userId = 177L;
        var boxId = 31L;
        var cardId = 5L;

        var requester = new UserPrincipal(userId,"testRequester","",
                List.of(new SimpleGrantedAuthority(RoleTypes.REGISTERED)));
        // arrange box and card initial
        Box box = new Box();
        box.setId(boxId);
        box.setPublic(true);
        var owner = new User();
        owner.setId(userId);
        box.setOwner(owner);
        var card = new Card();
        card.setId(cardId);
        card.setBox(box);
        String preChangeAnswer = "Old Answer";
        String postChangeAnswer = "New Answer"; // NOTE: never checked, not sure if reasonable to try. research needed.
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
        Mockito.verify(cardRepository).save(eq(card));

        assertEquals(postChangeAnswer, card.getAnswer());

    }

    @Test
    void deleteCallsDeleteIfRequesterBoxOwner() {
        // arrange user + principal
        var userId = 177L;
        var cardId = 123L;
        var user = new User();
        user.setId(userId);
        var requester = new UserPrincipal(userId,"testRequester","",
                List.of(new SimpleGrantedAuthority(RoleTypes.REGISTERED)));

        var box = new Box();
        box.setOwner(user);
        box.setPublic(false);
        var card = new Card();
        card.setId(cardId);
        card.setBox(box);
        Mockito.when(cardRepository.findById(cardId))
                .thenReturn(Optional.of(card));

        // act
        cardService.delete(cardId,requester);

        // assert
        Mockito.verify(cardRepository, Mockito.times(1)).deleteById(Mockito.anyLong());
    }

    @Test
    void deleteDeniesIfBoxPublicButRequesterNotOwner() {
        // arrange user + principal
        var requesterId = 177L;
        var ownerId = 157L;
        var cardId = 123L;
        var owner = new User();
        owner.setId(ownerId);
        var requester = new UserPrincipal(requesterId,"testRequester","",
                List.of(new SimpleGrantedAuthority(RoleTypes.REGISTERED)));

        var box = new Box();
        box.setOwner(owner);
        box.setPublic(true);
        var card = new Card();
        card.setId(cardId);
        card.setBox(box);
        Mockito.when(cardRepository.findById(cardId))
                .thenReturn(Optional.of(card));

        // act
        assertThrows(PermissionDeniedException.class, () -> {
            cardService.delete(cardId,requester);
        });

        // assert
        Mockito.verify(cardRepository, Mockito.times(0)).deleteById(Mockito.anyLong());
    }

    @Test
    void userCanCreateCardForOwnedBox() {
        // arrange
        var userId = 123L;
        var boxId = 17L;

        var owner = new User(); owner.setId(userId);
        var box = new Box(); box.setOwner(owner);

        var cardDto = new CardDto(); cardDto.setBoxId(boxId);
        cardDto.setAnswer("its a nonempty string");
        cardDto.setQuestion("its another nonempty string");

        UserPrincipal requester = getSimpleRegisteredPrincipal(userId);

        Mockito.when(boxService.read(Mockito.anyLong())).thenReturn(box);

        // act
        this.cardService.create(cardDto,requester);

        // assert
        Mockito.verify(cardRepository, Mockito.times(1)).save(Mockito.any());

    }

}