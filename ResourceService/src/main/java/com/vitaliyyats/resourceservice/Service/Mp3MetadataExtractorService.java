package com.vitaliyyats.resourceservice.Service;

import com.vitaliyyats.resourceservice.dto.SongDTO;
import org.apache.tika.exception.TikaException;
import org.apache.tika.metadata.Metadata;
import org.apache.tika.parser.ParseContext;
import org.apache.tika.parser.mp3.Mp3Parser;
import org.apache.tika.sax.BodyContentHandler;
import org.springframework.stereotype.Service;
import org.xml.sax.SAXException;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;

@Service
public class Mp3MetadataExtractorService {

    public SongDTO extractMetadata(byte[] data) throws TikaException, IOException, SAXException {
        BodyContentHandler handler = new BodyContentHandler();
        Metadata metadata = new Metadata();
        InputStream inputstream = new ByteArrayInputStream(data);
        ParseContext context = new ParseContext();

        Mp3Parser Mp3Parser = new Mp3Parser();
        Mp3Parser.parse(inputstream, handler, metadata, context);

        var songDTO = new SongDTO();
        for (String name : metadata.names()) {
            switch (name) {
                case String s when s.contains("title") -> songDTO.setName(metadata.get(s));
                case String s when s.contains("artist") -> songDTO.setArtist(metadata.get(s));
                case String s when s.contains("album") -> songDTO.setAlbum(metadata.get(s));
                case String s when s.contains("releaseDate") -> songDTO.setYear(Integer.valueOf(metadata.get(s)));
                case String s when s.contains("duration") -> songDTO.setLength(metadata.get(s));
                default -> {
                }
            }
        }

        return songDTO;
    }
}
