package org.example;

import org.example.commands.DeleteDirCommand;
import org.example.utils.FileManager;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;


public class Main {
    public static void main(String[] args) {
        Map<String, String> availableCommands = new HashMap<>();
        availableCommands.put("cdir", "creates a new empty directory. Usage: cdir <directoryName(s)>");
        availableCommands.put("cfile", "creates new file if it doesn't exists. Usage: cfile <fileName(s)>");
        availableCommands.put("quit/exit", "exits console");
        availableCommands.put("lst", "List directory contents. Usage: lst <directoryName(s)>");
        availableCommands.put("dfile", "deletes a file. Usage: dfile <fileName(s)>");
        availableCommands.put("check", "check if a file or directory exists in a filesystem");
        availableCommands.put("deldir", "deletes directory and contents. Usage: deldir <directoryName(s)>");



        FileManager fileManager = new FileManager();

        System.out.println("Enter 'HELP' to see available commands");
        Scanner scanner = new Scanner(System.in);

        boolean running = true;
        while (running) {
            System.out.print("> ");
            String input = scanner.nextLine().trim();
            String[] parts = input.split("\\s+");
            String commandName =parts[0].toLowerCase();

            String[] arguments = Arrays.copyOfRange(parts,1, parts.length);

            if(commandName.equals("help")){
                for(String c : availableCommands.keySet()){
                    System.out.println(c + " : " + availableCommands.get(c));
                }

            } else if (commandName.equals("deldir")){
                DeleteDirCommand deleteDirCommand = new DeleteDirCommand();
                if(deleteDirCommand.confirmDeletion(scanner, arguments)){
                    deleteDirCommand.execute(arguments);
                } else{
                    System.out.println("Terminated");
                }
            }
            else if (commandName.equals("quit") || commandName.equals("exit")) {
                running = false;

            } else if (!availableCommands.containsKey(commandName)) {
                System.out.println("Unknown command: " + commandName + ". Enter 'HELP' to see available commands.");
            } else {
                fileManager.registerCommands();
                fileManager.getCommand(commandName).execute(arguments);
            }
        }
    }
}