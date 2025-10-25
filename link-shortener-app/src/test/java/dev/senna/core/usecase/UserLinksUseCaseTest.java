package dev.senna.core.usecase;

import dev.senna.core.domain.Link;
import dev.senna.core.domain.LinkFilter;
import dev.senna.core.domain.PaginatedResult;
import dev.senna.core.exception.BadFilterDateException;
import dev.senna.core.port.out.LinkRepositoryPortOut;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserLinksUseCaseTest {

    @Mock
    LinkRepositoryPortOut linkRepositoryPortOut;

    @Mock
    LinkFilter linkFilter;

    @InjectMocks
    UserLinksUseCase userLinksUseCase;

    @Nested
    class execute {

        @Test
        void shouldCallFindAllByUserId() {

            PaginatedResult<Link> userLinks = new PaginatedResult<>(new ArrayList<>(), null, false);
            String userId = UUID.randomUUID().toString();
            String nextToken = "NEXT_TOKEN";
            int limit = 10;
            doNothing().when(linkFilter).validate();
            doReturn(userLinks).when(linkRepositoryPortOut).findAllByUserId(userId, nextToken, limit, linkFilter);

           var output = userLinksUseCase.execute(userId, nextToken, limit, linkFilter);

            verify(linkRepositoryPortOut, times(1))
                    .findAllByUserId(userId, nextToken, limit, linkFilter);

            assertEquals(userLinks, output);


        }

        @Test
        void shouldNotCallFindAllByUserIdWhenFilterException() {

            String userId = UUID.randomUUID().toString();
            String nextToken = "NEXT_TOKEN";
            int limit = 10;
            doThrow(BadFilterDateException.class).when(linkFilter).validate();

            assertThrows(BadFilterDateException.class,
                    () -> userLinksUseCase.execute(userId, nextToken, limit, linkFilter));

            verify(linkRepositoryPortOut, never())
                    .findAllByUserId(anyString(), anyString(), anyInt(), any(LinkFilter.class));
        }
    }


  
}