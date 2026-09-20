/*
 * Author: Oleksii Hnidan
 * Project: web-security26 | Lab: 1 | Class: TrackService
 */
package chnu.edu.websecurity26.service;

import chnu.edu.websecurity26.model.Track;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

@Service
public class TrackService {
    private final Map<String, Track> tracks = new ConcurrentHashMap<>();
    private final AtomicLong nextId = new AtomicLong(4);

    public TrackService() {
        tracks.put("1", new Track("1", "Digital Bath", "Deftones", "White Pony", 255));
        tracks.put("2", new Track("2", "Subwoofer Lullaby", "C418", "Minecraft - Volume Alpha", 208));
        tracks.put("3", new Track("3", "Doomsday", "MF DOOM", "Operation: Doomsday", 298));
    }

    public List<Track> getAll() {
        return tracks.values().stream()
                .sorted((a, b) -> Long.compare(Long.parseLong(a.getId()), Long.parseLong(b.getId())))
                .toList();
    }

    public Track get(String id) {
        Track track = tracks.get(id);
        if (track == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Track not found: " + id);
        }
        return track;
    }

    public Track create(Track track) {
        validate(track);
        Track saved = new Track(String.valueOf(nextId.getAndIncrement()), track.getTitle(),
                track.getArtist(), track.getAlbum(), track.getDurationSeconds());
        tracks.put(saved.getId(), saved);
        return saved;
    }

    public Track update(String id, Track track) {
        validate(track);
        get(id);
        Track saved = new Track(id, track.getTitle(), track.getArtist(),
                track.getAlbum(), track.getDurationSeconds());
        tracks.put(id, saved);
        return saved;
    }

    public void delete(String id) {
        if (tracks.remove(id) == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Track not found: " + id);
        }
    }

    private void validate(Track track) {
        if (track == null || isBlank(track.getTitle()) || isBlank(track.getArtist())
                || isBlank(track.getAlbum()) || track.getDurationSeconds() == null
                || track.getDurationSeconds() <= 0) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
                    "Title, artist, album, and positive durationSeconds are required");
        }
    }

    private boolean isBlank(String value) {
        return value == null || value.isBlank();
    }
}
