package com.vitaliyyats.songservice.model;

import jakarta.persistence.*;
import lombok.*;


@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
@Entity
@Table(name = "songs")
public class Song {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false)
    private String name;
    @Column(nullable = false)
    private String artist;
    @Column
    private String album;
    @Column
    private String length;
    @Column
    private String resourceId;
    @Column
    private Integer year;
}
