package at.technikum.studybuddy.service;

import at.technikum.studybuddy.dto.CardDto;
import at.technikum.studybuddy.entity.Card;
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

}