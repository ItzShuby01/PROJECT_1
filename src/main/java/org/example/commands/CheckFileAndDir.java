package org.example.commands;

import java.nio.file.Files;
import java.nio.file.LinkOption;
import java.nio.file.Path;
import java.nio.file.Paths;

public class CheckFileAndDir implements Command {
    @Override
    public void execute(String[] arguments) {
        if (arguments.length == 0) {
            System.out.println("Syntax: check <File/Dir>");
        }

        for (String arg : arguments){
            Path p = Paths.get(arg);
            if (Files.exists(p, LinkOption.NOFOLLOW_LINKS)){
                if(Files.isRegularFile(p)) {
                    System.out.println("File " + p.getFileName() + " exists");
                } else {
                    System.out.println("Directory " + p.getFileName() + " exists");
                }
            } else {
                System.out.println("File/Directory " + p.getFileName() + " doesn't exist");
                }
            }
        }
}
