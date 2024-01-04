package com.vitaliyyats.songservice.service;

import com.vitaliyyats.songservice.dto.SongCreationResponse;
import com.vitaliyyats.songservice.dto.SongDTO;
import com.vitaliyyats.songservice.model.Song;
import com.vitaliyyats.songservice.repository.SongRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Stream;

@Service
@RequiredArgsConstructor
@Slf4j
public class SongService {
    private final SongRepository songRepository;

    public SongDTO getSong(Long id) {
        var song = songRepository.findById(id);
        song.ifPresent(s -> log.info("found song: {}", s));
        return toDTO(song.orElseThrow(() -> new NoSuchElementException("Song with id " + id + " does not exists.")));
    }

    public SongCreationResponse createSong(SongDTO songDTO) {
        var songModel = toModel(songDTO);
        var saved = songRepository.save(songModel);
        log.info("saved song: {}", saved);
        return new SongCreationResponse(saved.getId());
    }

    public List<Long> deleteSongs(String ids) {
        List<Long> idList = Stream.of(ids.split(","))
                .map(Long::parseLong)
                .toList();
        var existingIds = idList.stream()
                .filter(songRepository::existsById)
                .toList();
        songRepository.deleteAllByIdInBatch(idList);
        log.info("deleted songs with ids: {}", existingIds);
        return existingIds;
    }

    private SongDTO toDTO(Song song) {
        return SongDTO.builder()
                .name(song.getName())
                .artist(song.getArtist())
                .album(song.getAlbum())
                .length(song.getLength())
                .resourceId(song.getResourceId())
                .year(song.getYear())
                .build();
    }

    private Song toModel(SongDTO songDTO) {
        return Song.builder()
                .name(songDTO.getName())
                .artist(songDTO.getArtist())
                .album(songDTO.getAlbum())
                .length(songDTO.getLength())
                .resourceId(songDTO.getResourceId())
                .year(songDTO.getYear())
                .build();
    }
}
