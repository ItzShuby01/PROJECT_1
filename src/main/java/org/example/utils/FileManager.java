package org.example.utils;

import org.example.commands.Command;
import org.example.commands.CreateDirCommand;
import org.example.commands.CreateFileCommand;
import org.example.commands.ListDirContentsCommand;

import java.util.HashMap;
import java.util.Map;

public class FileManager {
    private final Map<String, Command> commandMap = new HashMap<>();

    public void registerCommands() {
        commandMap.put("cfile", new CreateFileCommand());
        commandMap.put("cdir", new CreateDirCommand());
        commandMap.put("lst", new ListDirContentsCommand());
    }

    public Command getCommand(String commandName){
        return commandMap.get(commandName);
    }
}
