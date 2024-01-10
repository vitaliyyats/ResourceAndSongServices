package com.vitaliyyats.resourceservice.Service;

import com.netflix.appinfo.InstanceInfo;
import com.netflix.discovery.EurekaClient;
import com.vitaliyyats.resourceservice.dto.CreationResponse;
import com.vitaliyyats.resourceservice.dto.SongDTO;
import com.vitaliyyats.resourceservice.model.Resource;
import com.vitaliyyats.resourceservice.repository.ResourceRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.tika.exception.TikaException;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;
import org.xml.sax.SAXException;

import java.io.IOException;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Stream;

import static org.springframework.http.MediaType.APPLICATION_JSON;

@Service
@RequiredArgsConstructor
@Slf4j
public class ResourceService {
    private final Mp3MetadataExtractorService mp3MetadataExtractorService;
    private final ResourceRepository resourceRepository;
    private final EurekaClient eurekaClient;

    public Resource getResource(Long id) {
        var resource = resourceRepository.findById(id);
        return resource.orElseThrow(() -> new NoSuchElementException("Resource with id " + id + " does not exists."));
    }

    public CreationResponse uploadResource(byte[] data) throws TikaException {
        SongDTO song;
        try {
            song = mp3MetadataExtractorService.extractMetadata(data);
        } catch (TikaException | IOException | SAXException e) {
            throw new TikaException("Cannot parse audio file", e);
        }
        var resource = Resource.builder().data(data).build();
        var savedResource = resourceRepository.save(resource);
        song.setResourceId(String.valueOf(savedResource.getId()));
        log.info("Saved song. Sending song metadata to song service: {}", song);
        sendMetadataToSongService(song);
        return new CreationResponse(savedResource.getId());
    }

    private void sendMetadataToSongService(SongDTO song) {
        RestClient restClient = RestClient.create();
        restClient.post()
                .uri(songServiceUrl() + "/songs")
                .contentType(APPLICATION_JSON)
                .body(song)
                .retrieve()
                .toBodilessEntity();
    }

    private String songServiceUrl() {
        InstanceInfo instance = eurekaClient.getNextServerFromEureka("song-service", false);
        return instance.getHomePageUrl();
    }

    public List<Long> deleteResources(String ids) {
        List<Long> idList = Stream.of(ids.split(","))
                .map(Long::parseLong)
                .toList();
        var existingIds = idList.stream()
                .filter(resourceRepository::existsById)
                .toList();
        resourceRepository.deleteAllByIdInBatch(idList);
        log.info("deleted resources with ids: {}", existingIds);
        return existingIds;
    }

}
