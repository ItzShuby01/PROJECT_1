package org.example.commands;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.LinkOption;
import java.nio.file.Path;
import java.nio.file.Paths;

public class ListDirContentsCommand implements Command {
    @Override
    public void execute(String[] arguments) {
        if (arguments.length == 0) {
            try {
                Path dir = Paths.get("./");
                Iterable<Path> dirStream = Files.newDirectoryStream(dir);
                for (Path path : dirStream) {
                    System.out.println(path.getFileName());
                }
            } catch (IOException e) {
                System.err.println("Failed to access directory: " + e.getMessage());
            }
        } else {
            for (String dirName : arguments) {
            Path dir = Paths.get(dirName);
            if (!Files.exists(dir, LinkOption.NOFOLLOW_LINKS)) {
                System.out.println("Directory " + dir + " does not exist");
                continue;
            } else if (!Files.isDirectory(dir)) {
                System.out.println(dir + " is not a directory");
                continue;
            }
            try {
                System.out.println("Contents of directory: " + dir);
                Iterable<Path> dirStream = Files.newDirectoryStream(dir);
                for (Path path : dirStream) {
                    System.out.println(path.getFileName());
                }
                System.out.println("---------------------------");
            } catch (IOException e) {
                System.err.println("Error accessing directory : " + e.getMessage());
            }
        }
    }
}
}