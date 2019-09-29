package com.czaplon.backupmaker.profileReader;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;


public class ProfileReader{
    private Path profilePath;
    private Profile profile;

    private final String sourceHeaderProfileFile = "SOURCE";
    private final String destinationHeaderProfileFile = "DESTINATION";
    private final String descriptionHeaderProfileFile = "DESCRIPTION";


    public ProfileReader(Path profilePath) {
        this.profilePath = profilePath;
    }


    public Profile readProfile() {
        String input;
        try (BufferedReader bufReader = new BufferedReader(new FileReader(profilePath.toString()))){
            String description="", source="", destination="";
            while ((input = bufReader.readLine())!=null) {
                String data[] = input.split("#");
                if (data[0].trim().equalsIgnoreCase(descriptionHeaderProfileFile)){
                    description = data[1].trim();
                } else if (data[0].trim().equalsIgnoreCase(sourceHeaderProfileFile)) {
                    source = data[1].trim();
                } else if (data[0].trim().equalsIgnoreCase(destinationHeaderProfileFile)) {
                    destination = data[1].trim();
                }
            }

            if (!description.isEmpty() && !source.isEmpty() && !destination.isEmpty()) {
                Path sourcePath = Paths.get(source).normalize();
                Path destinationPath = Paths.get(destination).normalize();

                this.profile = new Profile(description,sourcePath,destinationPath);
                return this.profile;
            }

        } catch (FileNotFoundException fe) {
            System.out.println("Profile file does not exist");
        } catch (IOException | ArrayIndexOutOfBoundsException e) {
            System.out.println("Fatal error occurred trying opening profile.");
        }
        return null;
    }

    public Profile getProfile() throws FileNotFoundException{
        if (profile==null) {
            throw new FileNotFoundException("Profile file corrupted");
        } else {
            return profile;
        }
    }
}
