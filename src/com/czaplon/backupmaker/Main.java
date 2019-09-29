package com.czaplon.backupmaker;

import com.czaplon.backupmaker.backupMaker.BackupMaker;
import com.czaplon.backupmaker.backupMaker.Mirror;
import com.czaplon.backupmaker.profileReader.Profile;
import com.czaplon.backupmaker.profileReader.ProfileListReader;
import com.czaplon.backupmaker.profileReader.ProfileReader;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.Path;
import java.util.Scanner;

public class Main {
//    static ProfileReader profileReader;
    static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        System.out.println("##### BackupMaker v.0.2.2 #####\n");
//        Path profilePath = FileSystems.getDefault().getPath("profiles","profile1.profile");
//        profileReader = new ProfileReader(profilePath);

        ProfileListReader profiles = new ProfileListReader();
        try {
            profiles.readProfilesList();

            int selectedProfile=1;
            if (profiles.getProfilesList().size()>1) {
                System.out.println("Multiple profiles read. Which profile would you like to load: ");
                for (Profile profile: profiles.getProfilesList()) {
                    System.out.println(selectedProfile++ +") "+profile.getDescription() + " | source: " + profile.getSource()+ " | destination: " + profile.getDestination() );
                }
                while (true) {
                    String input = scanner.nextLine();
                    try {
                        if (Integer.parseInt(input) <= profiles.getProfilesList().size()) {
                            selectedProfile = Integer.parseInt(input);
                            break;
                        }
                    } catch (Exception e){
                        selectedProfile = -1;
                    }
                    System.out.println("Please select valid option");
                }
            }

            //odczyt profilu i wykonanie mirroru
//            try {
                Profile profile = profiles.getProfile(selectedProfile-1);


                System.out.println("Description: " + profile.getDescription());
                System.out.println("Source: " + profile.getSource());
                System.out.println("Destination: " + profile.getDestination());
                System.out.println("\nDo you want to continue ? [y/n]");


                while (true) {
                    String input = scanner.nextLine();
                    if (input.equalsIgnoreCase("y")) {
                        BackupMaker backupMaker = new BackupMaker(profile.getSource(), profile.getDestination());
                        backupMaker.backup();
                    }
                    break;
                }
//            } catch (FileNotFoundException filee) {
//                System.out.println("Profile file corrupted.");
//            }

            System.out.println(Mirror.getReport());

        } catch (IOException e) {
            System.out.println("Error accesing profiles");
        }




    }
}
