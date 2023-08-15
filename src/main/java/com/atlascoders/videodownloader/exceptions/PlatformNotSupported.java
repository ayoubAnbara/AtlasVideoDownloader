package com.atlascoders.videodownloader.exceptions;

public class PlatformNotSupported extends Exception {

    public PlatformNotSupported() {
        super("This platform isn't supported yet!");
    }
}
