/*
 * Author: Oleksii Hnidan
 * Project: web-security26 | Lab: 1 | Class: TrackServiceTest
 */
package chnu.edu.websecurity26.service;

import chnu.edu.websecurity26.model.Track;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class TrackServiceTest {
    private final TrackService service = new TrackService();

    @Test
    void createsUpdatesAndDeletesTrack() {
        Track created = service.create(new Track(null, "Cherry Waves", "Deftones",
                "Saturday Night Wrist", 317));
        assertEquals("4", created.getId());
        assertEquals(4, service.getAll().size());

        Track updated = service.update(created.getId(), new Track(null, "Rivière", "Deftones",
                "Saturday Night Wrist", 225));
        assertEquals("Rivière", service.get(created.getId()).getTitle());
        assertEquals(created.getId(), updated.getId());

        service.delete(created.getId());
        assertEquals(3, service.getAll().size());
        assertEquals(HttpStatus.NOT_FOUND,
                assertThrows(ResponseStatusException.class, () -> service.get(created.getId())).getStatusCode());
    }

    @Test
    void rejectsMissingDetailsAndUnknownTrack() {
        assertEquals(HttpStatus.BAD_REQUEST,
                assertThrows(ResponseStatusException.class,
                        () -> service.create(new Track(null, "", "Deftones", "White Pony", 255)))
                        .getStatusCode());
        assertEquals(HttpStatus.NOT_FOUND,
                assertThrows(ResponseStatusException.class,
                        () -> service.update("999", new Track(null, "Title", "Artist", "Album", 180)))
                        .getStatusCode());
    }
}
