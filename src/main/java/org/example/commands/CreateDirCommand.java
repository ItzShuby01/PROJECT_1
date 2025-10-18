package org.example.commands;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class CreateDirCommand implements Command {
    @Override
    public void execute(String[] arguments) {
        if (arguments.length == 0) {
            System.err.println("Syntax: cdir <directoryName>");
        }

        for (String dirName : arguments) {
            Path dir = Paths.get(dirName);
            if (Files.exists(dir) && Files.isDirectory(dir)) {
                System.out.println("Directory " + dir + " already exists");
            } else {
                try {
                    Files.createDirectory(dir);
                    System.out.println("Directory " + dir + " successfully created");
                } catch (IOException e) {
                    System.err.println("Failed to create directory " + e.getMessage());
                }
            }
        }
    }
}