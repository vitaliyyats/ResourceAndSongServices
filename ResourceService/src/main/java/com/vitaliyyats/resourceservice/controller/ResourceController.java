package com.vitaliyyats.resourceservice.controller;

import com.vitaliyyats.resourceservice.Service.ResourceService;
import com.vitaliyyats.resourceservice.dto.CreationResponse;
import com.vitaliyyats.resourceservice.dto.DeletionResponse;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.RequiredArgsConstructor;
import org.apache.tika.exception.TikaException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.NoSuchElementException;

@RestController
@RequestMapping("/resources")
@RequiredArgsConstructor
public class ResourceController {
    private final ResourceService resourceService;

    @GetMapping(value = "/{id}", produces = "audio/mpeg")
    public byte[] getResource(@PathVariable Long id) {
        try {
            return resourceService.getResource(id).getData();
        } catch (NoSuchElementException ex) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, ex.getMessage(), ex);
        }
    }
    @PostMapping(consumes = "audio/mpeg")
    public CreationResponse createResource(@RequestBody byte[] audioData) {
        try {
            return resourceService.uploadResource(audioData);
        } catch (TikaException ex) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, ex.getMessage(), ex);
        }
    }

    @DeleteMapping()
    public DeletionResponse deleteResources(@RequestParam(name = "id") @NotBlank @Size(max = 200) String ids) {
        return new DeletionResponse(resourceService.deleteResources(ids));
    }
}
