package org.epptec.ppldb.infrastructure.cli.runner.commands;

import org.epptec.ppldb.domain.people.Person;
import org.epptec.ppldb.domain.people.PersonRepository;
import org.epptec.ppldb.domain.people.exceptions.EmptyNameException;
import org.epptec.ppldb.domain.people.exceptions.InvalidIdentificationNumberException;
import org.epptec.ppldb.infrastructure.cli.runner.commands.utils.IdentificationNumberScanner;

import java.util.Scanner;

public class CreatePersonCommand implements Command {

    private final Scanner scanner;
    private final PersonRepository personRepository;
    private final IdentificationNumberScanner identificationNumberScanner;

    public CreatePersonCommand(
        Scanner scanner,
        PersonRepository personRepository,
        IdentificationNumberScanner identificationNumberScanner
    ) {
        this.scanner = scanner;
        this.personRepository = personRepository;
        this.identificationNumberScanner = identificationNumberScanner;
    }

    @Override
    public void run() {
        try {
            var person = tryScanPerson();
            personRepository.save(person);
            System.out.println("Person created.");
        } catch (InvalidIdentificationNumberException e) {
            System.out.println(e.getMessage());
        } catch (EmptyNameException e) {
            System.out.println();
        }
    }

    private Person tryScanPerson() throws InvalidIdentificationNumberException, EmptyNameException {
        var identificationNumber = identificationNumberScanner.scan();
        var firstName = scanName("First name:");
        var lastName = scanName("Last name:");

        return new Person(identificationNumber, firstName, lastName);
    }

    private Person.Name scanName(String name) throws EmptyNameException {
        System.out.println(name + ":");
        var value = scanner.nextLine();

        return new Person.Name(value);
    }

    @Override
    public String getName() {
        return "Create Person";
    }
}
