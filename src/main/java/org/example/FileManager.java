package org.example;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.LinkOption;
import java.nio.file.Path;
import java.nio.file.Paths;

public class FileManager {
    public void listDirContents(String dirName){
        if(dirName.isEmpty()){
            dirName = ".";
        }
        Path dir = Paths.get(dirName);
        if(!Files.exists(dir, LinkOption.NOFOLLOW_LINKS)){
            System.err.println("Directory " + dir + " does not exist");
            return;
        } else if (!Files.isDirectory(dir)) {
            System.err.println( dir + " is not a directory");
            return;
        }
        try{
            Iterable<Path> dirStream = Files.newDirectoryStream(dir);
             for(Path path :dirStream){
                 System.out.println(path.getFileName());
             }
        } catch (IOException e) {
            System.err.println("Error accessing directory : " + e.getMessage());
        }

    }
    public void createDirectory(String dirName){
        if(dirName.isEmpty()){
            System.err.println("Syntax: cdir <directoryName>");
        }

        Path dir  = Paths.get(dirName);
        if(Files.exists(dir) && Files.isDirectory(dir)){
            System.out.println("Directory " + dir + " already exists");
        } else{
            try{
                Files.createDirectory(dir);
                System.out.println("Directory " + dir+ " successfully created");
            } catch(IOException e){
                System.err.println("Failed to create directory " + e.getMessage());
            }
        }
    }

    public void createFile(String fileName){
        if(fileName.isEmpty()){
            System.err.println("Syntax: cfile <fileName>");
            return;
        }
          Path file = Paths.get(fileName);
          if(Files.exists(file)){
            System.out.println("File: " + file + " already exists");
        } else {
            try {
                Files.createFile(file);
                System.out.println("File: " + file + " created successfully");
            } catch (IOException e) {
                System.err.println("Failed to create file: " + e.getMessage());
            }
        }
    }
}
