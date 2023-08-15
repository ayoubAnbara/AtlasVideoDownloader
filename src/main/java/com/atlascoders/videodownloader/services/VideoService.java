package com.atlascoders.videodownloader.services;

import com.atlascoders.videodownloader.exceptions.PlatformNotSupported;
import com.atlascoders.videodownloader.models.VideoDetails;

import java.io.IOException;

public interface VideoService {

    VideoDetails getVideoInformation(String url) throws PlatformNotSupported, IOException;

}
