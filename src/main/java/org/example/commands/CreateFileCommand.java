package org.example.commands;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class CreateFileCommand  implements Command {
    @Override
    public void execute(String[] arguments) {
        if (arguments.length == 0) {
            System.out.println("Syntax: cfile <fileName>");
            return;
        }
        for (String fileName : arguments) {
            Path file = Paths.get(fileName);
            if (Files.exists(file)) {
                System.out.println("File: " + file + " already exists");
            } else {
                try {
                    Files.createFile(file);
                    System.out.println("File: " + file + " created successfully");
                } catch (IOException e) {
                    System.out.println("Failed to create file: " + e.getMessage());
                }
            }
        }
    }
}