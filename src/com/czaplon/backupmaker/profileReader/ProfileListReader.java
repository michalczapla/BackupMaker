package com.czaplon.backupmaker.profileReader;

import java.io.IOException;
import java.nio.file.DirectoryStream;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

public class ProfileListReader {
    private List<Profile> profilesList;

    public ProfileListReader() {
        this.profilesList = new ArrayList<>();
    }

    public void readProfilesList() throws IOException {
        Path profilesDir = FileSystems.getDefault().getPath("profiles");

        try (DirectoryStream<Path> dirContents = Files.newDirectoryStream(profilesDir))
        {
            for (Path path: dirContents) {
                if (Files.isReadable(path)) {
                    profilesList.add(new ProfileReader(path).readProfile());
                }
            }
        }
    }

    public List<Profile> getProfilesList() {
        return profilesList;
    }

    public Profile getProfile(int index) {
        return profilesList.get(index);
    }
}
