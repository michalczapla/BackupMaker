package com.czaplon.backupmaker.backupMaker;

import java.io.IOException;
import java.nio.file.*;
import java.nio.file.attribute.BasicFileAttributes;
import java.sql.Timestamp;
import java.util.Date;

public class Mirror extends SimpleFileVisitor<Path> {
    private Path sourcePath;
    private Path targetPath;
    private boolean simulation;
    private static String report = "====== Report generated :" + new Timestamp(new Date().getTime()).toString() + " ======\n";

    public Mirror(Path sourcePath, Path targetPath, boolean simulation) {
        this.sourcePath = sourcePath;
        this.targetPath = targetPath;
        this.simulation = simulation;
    }

    @Override
    public FileVisitResult visitFileFailed(Path file, IOException exc) throws IOException {
        System.out.println("Error accessing file: "+file.toAbsolutePath() + " " + exc.getMessage());
        report += "Error accessing file: "+file.toAbsolutePath()+"\n";
        return FileVisitResult.CONTINUE;
    }

    @Override
    public FileVisitResult preVisitDirectory(Path dir, BasicFileAttributes attrs) throws IOException {
        Path sourcePathRelative = sourcePath.relativize(dir);
        Path targetPathRelative = targetPath.resolve(sourcePathRelative);

        try {
            Files.copy(dir,targetPathRelative,StandardCopyOption.REPLACE_EXISTING);
        } catch (DirectoryNotEmptyException dire) {
           // w przypadku kiedy katalog docelowy nie jest pusty
            report += "Dir "+ dir + " has already existed in target\n";
            return FileVisitResult.CONTINUE;
        }
        catch (IOException e) {
            System.out.println("Error copying dir: " + targetPathRelative.toAbsolutePath()+ " "+e.getMessage());
//            e.printStackTrace();
            report += "Error copying dir: " + targetPathRelative.toAbsolutePath()+"\n";
            return FileVisitResult.SKIP_SUBTREE;
        }
        return FileVisitResult.CONTINUE;
    }

    @Override
    public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {
        Path sourcePathRelative = sourcePath.relativize(file);
        Path targetPathRelative = targetPath.resolve(sourcePathRelative);


        try {
            if (Files.exists(targetPathRelative) && !(Files.size(file)==Files.size(targetPathRelative))) {
                Files.copy(file, targetPathRelative, StandardCopyOption.REPLACE_EXISTING);
                report += "Overwriting file: " + targetPath.relativize(targetPathRelative) + "\n";
            } else if (!Files.exists(targetPathRelative)) {
                Files.copy(file, targetPathRelative);
                report += "Copying file: " + targetPath.relativize(targetPathRelative) + "\n";
            }

        } catch (IOException e) {
            System.out.println("Error copying file: " + targetPathRelative.toAbsolutePath()+ " "+e.getMessage());
            e.printStackTrace();
            report += "Error copying file: " + targetPathRelative.toAbsolutePath()+"\n";
        }

        return FileVisitResult.CONTINUE;
    }

    public static String getReport() {
        return report;
    }


}
