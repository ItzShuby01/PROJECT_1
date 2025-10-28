package org.example.utils;

import org.example.commands.*;

import java.util.HashMap;
import java.util.Map;

public class FileManager {
    private final Map<String, Command> commandMap = new HashMap<>();

    public void registerCommands() {
        commandMap.put("cfile", new CreateFileCommand());
        commandMap.put("cdir", new CreateDirCommand());
        commandMap.put("lst", new ListDirContentsCommand());
        commandMap.put("dfile", new DeleteFileCommand());
        commandMap.put("check", new CheckFileAndDir());
    }

    public Command getCommand(String commandName){
        return commandMap.get(commandName);
    }
}
