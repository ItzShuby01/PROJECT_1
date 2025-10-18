package org.example.commands;

import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class DeleteFileCommand implements Command{
    @Override
    public void execute(String[] arguments) {
        if(arguments.length == 0) {
            System.out.println("Syntax: dfile <fileName(s)>");
        }
        for(String fileName : arguments){
            Path file = Paths.get(fileName);
            if (Files.exists(file) && Files.isRegularFile(file)){
                try{
                Files.delete(file);
                    System.out.println("File " + file + " deleted successfully");
                } catch (IOException e){
                    System.out.println("Error deleting file");
                }
            } else {
                System.out.println("File " + file + " does not exist or is not a regular file");
            }
        }
    }
}
