package com.vitaliyyats.songservice.controller;

import com.vitaliyyats.songservice.dto.SongCreationResponse;
import com.vitaliyyats.songservice.dto.SongDTO;
import com.vitaliyyats.songservice.repository.SongRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.testcontainers.service.connection.ServiceConnection;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.util.Objects;

import static org.assertj.core.api.Assertions.assertThat;

@Testcontainers
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class SongsControllerTest {

    @Container
    @ServiceConnection
    static PostgreSQLContainer<?> postgres = new PostgreSQLContainer<>("postgres:latest");

    @Autowired
    SongRepository songRepository;

    @Autowired
    TestRestTemplate restTemplate;

    @Test
    void shouldCreateSong() {
        ResponseEntity<SongCreationResponse> response = restTemplate.postForEntity("/songs", songDTO(), SongCreationResponse.class);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        var savedSong = songRepository.findById(Objects.requireNonNull(response.getBody()).getId());
        assertThat(savedSong.isPresent()).isEqualTo(true);
    }


    private SongDTO songDTO() {
        return SongDTO.builder()
                .name("We are the champions")
                .artist("Queen")
                .album("News of the world")
                .length("2:59")
                .year(1977)
                .resourceId("1")
                .build();
    }


}