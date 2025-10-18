package org.example;

import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;


public class Main {
    public static void main(String[] args) {
        Map<String, String> availableCommands = new HashMap<>();
        availableCommands.put("cdir", "creates a new empty directory. Usage: cdir <directoryName>");
        availableCommands.put("cfile", "creates new file. Usage: cfile <fileName>");
        availableCommands.put("quit", "exits console");
        availableCommands.put("lst", "List directory contents. Usage: lst <directoryName>");



        FileManager fileManager = new FileManager();

        System.out.println("Enter 'HELP' to see available commands");
        Scanner scanner = new Scanner(System.in);

        boolean running = true;
        while (running) {
            System.out.println("> ");
            String input = scanner.nextLine().toLowerCase();
            String[] parts = input.split("\\s+", 2);
            String command =parts[0];
            String arg = parts.length > 1 ? parts[1].trim() : "";
            switch (command) {
                case "help":
                    for (String c : availableCommands.keySet()) {
                        System.out.println(c + ": " + availableCommands.get(c));
                    }
                    continue;
                case "cdir":
                    fileManager.createDirectory(arg);
                    continue;
                case "cfile":
                    fileManager.createFile(arg);
                    continue;
                case "lst":
                    fileManager.listDirContents(arg);
                    continue;
                case "quit":
                    System.out.println("Bye !");
                    running = false;
                default:
                    System.out.println("Unknown command");
                    break;

            }
        }
    }
}
