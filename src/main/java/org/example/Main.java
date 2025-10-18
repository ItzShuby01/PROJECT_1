package org.example;

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
            } else if (commandName.equals("quit") || commandName.equals("exit")) {
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