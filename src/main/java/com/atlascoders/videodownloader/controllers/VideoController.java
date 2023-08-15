package com.atlascoders.videodownloader.controllers;

import com.atlascoders.videodownloader.exceptions.PlatformNotSupported;
import com.atlascoders.videodownloader.models.VideoDetails;
import com.atlascoders.videodownloader.services.VideoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@RestController
public class VideoController {

    @Autowired
    private VideoService videoService;

    @GetMapping("/prepareVideo")
    public ResponseEntity<VideoDetails> getVideoInitialInfo(@RequestBody String videoUrl) {
        try {
            VideoDetails videoDetails = videoService.getVideoInformation(videoUrl);
            return ResponseEntity.ok(videoDetails);
        } catch (PlatformNotSupported e) {
            return ResponseEntity.badRequest().build();
        } catch (IOException e) {
            return ResponseEntity.notFound().build();
        }
    }
}
