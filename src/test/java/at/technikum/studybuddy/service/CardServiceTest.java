package at.technikum.studybuddy.service;

import at.technikum.studybuddy.entity.Card;
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
        var requester = new UserPrincipal(17L,"testRequester","", authorities);
        return requester;
    }
    private static @NonNull UserPrincipal getSimpleAdminPrincipal() {
        var authorities = List.of(new SimpleGrantedAuthority(RoleTypes.ADMIN));
        var requester = new UserPrincipal(23L,"testAdmin","", authorities);
        return requester;
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

}