package org.epptec.ppldb.infrastructure.cli.runner;

import org.epptec.ppldb.infrastructure.cli.runner.commands.Command;
import org.epptec.ppldb.infrastructure.cli.runner.commands.QuitCommand;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Scanner;

@Service
public class CliRunner {

    private final Scanner scanner;
    private final List<Command> commands;

    public CliRunner(
        @Autowired Scanner scanner,
        @Autowired List<Command> commands
    ) {
        this.scanner = scanner;
        this.commands = commands.stream()
            .sorted((o1, o2) -> Integer.compare(o2.getPriority(), o1.getPriority()))
            .toList();
    }

    public void run() {
        while (true) {
            Command command = promptAndReturnCommand();
            if (command == null) {
                continue;
            }

            if (command instanceof QuitCommand) {
                break;
            }

            command.run();
        }
    }

    private Command promptAndReturnCommand() {
        System.out.println("\nAvailable commands:");
        for (int i = 0; i < commands.size(); i++) {
            System.out.println(i + 1 + ". " + commands.get(i).getName());
        }

        System.out.println("Number: ");
        String indexInput = scanner.nextLine();

        if (indexInput.isEmpty()) {
            return null;
        }

        var index = parseIndex(indexInput);
        if (index == null || index < 0 || index >= commands.size()) {
            return null;
        }

        return commands.get(index);
    }

    private Integer parseIndex(String input) {
        try {
            return Integer.parseInt(input) - 1;
        } catch(NumberFormatException e) {
            return null;
        }
    }
}
