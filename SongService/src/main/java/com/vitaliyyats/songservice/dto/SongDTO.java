package com.vitaliyyats.songservice.dto;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.NotBlank;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class SongDTO {
    @NotBlank
    private String name;
    @NotBlank
    private String artist;
    private String album;
    private String length;
    @NotBlank
    private String resourceId;
    @Max(10000)
    private Integer year;
}
