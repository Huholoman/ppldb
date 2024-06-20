package org.epptec.ppldb.infrastructure.cli.runner.commands.utils;

import org.epptec.ppldb.domain.people.Person;
import org.epptec.ppldb.domain.people.exceptions.InvalidIdentificationNumberException;
import org.springframework.stereotype.Service;

import java.util.Scanner;

@Service
public class IdentificationNumberScanner {

    private final Scanner scanner;

    public IdentificationNumberScanner(Scanner scanner) {
        this.scanner = scanner;
    }

    public Person.IdentificationNumber scan() throws InvalidIdentificationNumberException {
        System.out.println("Identification number:");
        var value = scanner.nextLine();

        return new Person.IdentificationNumber(value);
    }
}
