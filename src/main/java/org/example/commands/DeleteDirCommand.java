package org.example.commands;

import java.io.IOException;
import java.nio.file.*;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.Scanner;
import java.util.stream.Stream;

import static java.nio.file.FileVisitResult.CONTINUE;
import static java.nio.file.FileVisitResult.SKIP_SUBTREE;
import static java.nio.file.Files.walk;

public class DeleteDirCommand implements Command {
    @Override
    public void execute(String[] arguments) {
        if (arguments.length == 0) {
            System.out.println("Usage: deldir <directoryName(s)>");
            return;
        }

        Path currentDir = Paths.get("").toAbsolutePath().normalize();
        for (String dirName : arguments) {
            try {
                Path dir = Paths.get(dirName).toAbsolutePath().normalize();

                if(dir.getParent() == null){
                    System.out.println("FATAL: Cannot delete root directory");
                    continue;
                }

                if(dir.equals(currentDir)){
                    System.out.println("Cannot delete the current directory "  + dir);
                    continue;
                }

                if(dir.equals(currentDir.getParent())){
                    System.out.println("Cannot delete the parent directory "  + dir);
                    continue;
                }

                if (!Files.exists(dir)) {
                    System.out.println("Directory " + dir + " does not exist");
                    continue;
                } else if (!Files.isDirectory(dir)) {
                    System.out.println(dir + " is not a directory");
                    continue;
                }
                Files.walkFileTree(dir, new DeleteDirContents());
            } catch (IOException e) {
                System.err.println("Error: " + e.getMessage());
            }

        }
    }

    public boolean confirmDeletion(Scanner scanner, String[] arguments){
        int maxDepth = 10;

        System.out.println("About to delete directory(ies): " + String.join(", ", arguments));
        for(String arg : arguments){
            Path dir = Paths.get(arg).toAbsolutePath().normalize();
            System.out.println("Preview of directory: " + dir);
            if(!Files.exists(dir) || !Files.isDirectory(dir)){
                System.out.println(   "(" + dir + " doesn't exist or is not a directory )");
                continue;
            }
            try {
                Stream<Path> dirStream = walk(dir, maxDepth);
                dirStream.forEach(path -> System.out.println(dir.relativize(path)));

            } catch (IOException e) {
                System.out.println("Preview failed: " + e.getMessage());
            }
        }

        System.out.print("Proceed to delete? (y/n): ");
        String option = scanner.nextLine().trim().toLowerCase();
        return option.equals("y");


    }


    // Deletes all files and subdirectories in a directory, including symbolic links. By default, symbolic links are not followed.
    static class DeleteDirContents extends SimpleFileVisitor<Path> {
        @Override
        public FileVisitResult visitFile(Path file, BasicFileAttributes attributes) {
            try {
                if (Files.isSymbolicLink(file)) {
                    Files.delete(file);
                    System.out.println("Deleted symbolic link: " + file);
                } else {
                    Files.delete(file);
                    System.out.println("Deleted regular file: " + file);
                }
            } catch (IOException e) {
                System.err.println("Failed to delete file: " + file + " due to " + e.getMessage());
                return CONTINUE;
            }
            return CONTINUE;
        }

        @Override
        /*
        If the directory is a symbolic link, it deletes ONLY the symlink and skips traversing its contents.
         */
        public FileVisitResult preVisitDirectory(Path dir, BasicFileAttributes attrs) {
            if(Files.isSymbolicLink(dir)){
                try {
                    Files.delete(dir);
                    System.out.println("Deleted directory symlink: " + dir);
                    return SKIP_SUBTREE;
                } catch (IOException e) {
                    System.out.println("Error deleting directory: " + e.getMessage());
                }
            }
            return CONTINUE;
        }
        @Override
        public FileVisitResult postVisitDirectory(Path dir, IOException exc) throws IOException {
            if (exc != null) {
                System.out.println("Failed to delete dir: " + dir + " due to " + exc.getMessage());
                return CONTINUE;
            }
            Files.delete(dir);
            System.out.println("Deleted directory: " + dir);
            return CONTINUE;
        }
    }
}
