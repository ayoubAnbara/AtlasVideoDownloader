package com.atlascoders.videodownloader.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class VideoDetails {

    private String title;
    private String author;
    private String duration;
    private String thumbnail;
    private String hostingService;

}
