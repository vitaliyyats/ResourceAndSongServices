package com.vitaliyyats.songservice.controller;

import com.vitaliyyats.songservice.dto.SongCreationResponse;
import com.vitaliyyats.songservice.dto.SongDTO;
import com.vitaliyyats.songservice.dto.SongDeletionResponse;
import com.vitaliyyats.songservice.service.SongService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.NoSuchElementException;

@RestController
@RequestMapping("/songs")
@RequiredArgsConstructor
@Validated
public class SongsController {
    private final SongService songService;

    @GetMapping("/{id}")
    SongDTO getSong(@PathVariable Long id) {
        try {
            return songService.getSong(id);
        } catch (NoSuchElementException ex) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, ex.getMessage(), ex);
        }
    }

    @PostMapping
    SongCreationResponse createSong(@RequestBody @Valid SongDTO songDTO) {
        return songService.createSong(songDTO);
    }

    @DeleteMapping()
    public SongDeletionResponse deleteSongs(@RequestParam(name = "id") @NotBlank @Size(max = 200) String ids) {
        return new SongDeletionResponse(songService.deleteSongs(ids));
    }
}
