package com.czaplon.backupmaker.backupMaker;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.sql.SQLOutput;

public class BackupMaker {
    private final Path sourcePath;
    private final Path destinationPath;

    public BackupMaker(Path sourcePath, Path destinationPath) {
        this.sourcePath = sourcePath;
        this.destinationPath = destinationPath;
    }

    public void backup() {
        if (sourcePath!=null && destinationPath!=null) {
            System.out.println("Backup started... Please wait");
            if (Files.isReadable(sourcePath)) {

                try {
                    Files.walkFileTree(sourcePath, new Mirror(sourcePath,destinationPath, true));
                } catch (IOException e) {
                    System.out.println("Error during backup :(");
                } finally {
                    System.out.println("Backup finished.");
                }
            } else {
                System.out.println("Source path unreachable. No backup made.");
            }
        }
    }
}
