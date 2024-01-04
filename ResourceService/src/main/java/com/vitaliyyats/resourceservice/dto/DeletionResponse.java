package com.vitaliyyats.resourceservice.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class DeletionResponse {
    private List<Long> ids;
}
