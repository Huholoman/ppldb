package org.epptec.ppldb.infrastructure.cli.runner.commands;

import org.epptec.ppldb.domain.people.PersonRepository;
import org.epptec.ppldb.domain.people.exceptions.InvalidIdentificationNumberException;
import org.epptec.ppldb.domain.people.exceptions.PersonNotFoundException;
import org.epptec.ppldb.infrastructure.cli.runner.commands.utils.IdentificationNumberScanner;

public class RemovePersonCommand implements Command {

    private final PersonRepository personRepository;
    private final IdentificationNumberScanner identificationNumberScanner;

    public RemovePersonCommand(
        PersonRepository personRepository,
        IdentificationNumberScanner identificationNumberScanner
    ) {
        this.personRepository = personRepository;
        this.identificationNumberScanner = identificationNumberScanner;
    }

    @Override
    public void run() {
        try {
            var identificationNumber = identificationNumberScanner.scan();
            personRepository.remove(identificationNumber);
        } catch (InvalidIdentificationNumberException e) {
            System.out.println(e.getMessage());
        } catch (PersonNotFoundException e) {
            System.out.println("Person not found");
        }
    }

    @Override
    public String getName() {
        return "Remove Person";
    }
}
