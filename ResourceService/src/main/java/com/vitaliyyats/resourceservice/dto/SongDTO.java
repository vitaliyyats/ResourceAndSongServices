package com.vitaliyyats.resourceservice.dto;

import lombok.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SongDTO {
    private String name;
    private String artist;
    private String album;
    private String length;
    private String resourceId;
    private Integer year;
}
