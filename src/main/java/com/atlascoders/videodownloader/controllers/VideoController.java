package com.atlascoders.videodownloader.controllers;

import com.atlascoders.videodownloader.models.VideoDetails;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class VideoController {

    @GetMapping("/prepareVideo")
    public ResponseEntity<VideoDetails> getVideoInitialInfo(@RequestBody String videoUrl) {
        return null;
    }
}
