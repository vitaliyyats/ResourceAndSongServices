package com.vitaliyyats.songservice.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;
@Data
@AllArgsConstructor
public class SongDeletionResponse {
    private List<Long> ids;
}
