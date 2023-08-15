package com.atlascoders.videodownloader.services.impl;

import com.atlascoders.videodownloader.exceptions.PlatformNotSupported;
import com.atlascoders.videodownloader.models.VideoDetails;
import com.atlascoders.videodownloader.services.VideoService;
import com.atlascoders.videodownloader.utils.Constants;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.services.youtube.YouTube;
import com.google.api.services.youtube.model.Video;
import com.google.api.services.youtube.model.VideoListResponse;
import org.apache.http.auth.AUTH;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
public class VideoServiceImpl implements VideoService {

    @Value("${avd.youtube.apikey}")
    private String apiKey;


    @Override
    public VideoDetails getVideoInformation(String url) throws PlatformNotSupported, IOException {
        VideoDetails currentVideoInformation = new VideoDetails();
        String requestedPlatform = getVideoPlatform(url);
        if (requestedPlatform.equals(Constants.YOUTUBE)) {
            throw new PlatformNotSupported();
        }
        currentVideoInformation.setHostingService(requestedPlatform);
        currentVideoInformation.setTitle(getVideoTitle(extractVideoId(url), apiKey));
    }

    public static String getVideoTitle(String videoId, String apiKey) throws IOException {
        YouTube youtube = new YouTube.Builder(new NetHttpTransport(), new JacksonFactory() , request -> {})
                .setApplicationName("AtlasVideoCoders")
                .build();

        YouTube.Videos.List videoRequest = youtube.videos().list("snippet");
        videoRequest.setId(videoId);
        videoRequest.setKey(apiKey);

        VideoListResponse response = videoRequest.execute();
        Video video = response.getItems().get(0);
        return video.getSnippet().getTitle();
    }

    private String getVideoPlatform(String url) {
        String youtubePattern = "(?:https?://)?(?:www\\.)?(?:youtube\\.com|youtu\\.be)";
        String dailymotionPattern = "(?:https?://)?(?:www\\.)?dailymotion\\.com";

        Pattern youtubeRegex = Pattern.compile(youtubePattern);
        Pattern dailymotionRegex = Pattern.compile(dailymotionPattern);

        Matcher youtubeMatcher = youtubeRegex.matcher(url);
        Matcher dailymotionMatcher = dailymotionRegex.matcher(url);

        if (youtubeMatcher.find()) {
            return "YouTube";
        } else if (dailymotionMatcher.find()) {
            return "Dailymotion";
        } else {
            return "Unknown";
        }
    }
    private static String extractVideoId(String videoUrl) {
        String videoId = null;
        Pattern pattern = Pattern.compile("(?<=v=|youtu.be/|/videos/|embed\\/|\\/v\\/|\\/e\\/|watch\\?v=|v%3D|u\\/\\w+\\/|v%3D|embed%2F|watch\\?v%3D|embed\\?feature=player_embedded&v=|%2Fvideos%2F|embed\\/?videoId=)([^#\\&\\?\\n]*)(?:[\\?\\&\\n]|$)");
        Matcher matcher = pattern.matcher(videoUrl);

        if (matcher.find()) {
            videoId = matcher.group(1);
        }

        return videoId;
    }
}
