package com.czaplon.backupmaker.profileReader;

import java.nio.file.Path;

public class Profile {
    private final String description;
    private Path source;
    private Path destination;

    public Profile(String description, Path source, Path destination) {
        this.description = description;
        this.source = source;
        this.destination = destination;
    }

    public String getDescription() {
        return description;
    }

    public Path getSource() {
        return source;
    }

    public void setSource(Path source) {
        this.source = source;
    }

    public Path getDestination() {
        return destination;
    }

    public void setDestination(Path destination) {
        this.destination = destination;
    }
}
