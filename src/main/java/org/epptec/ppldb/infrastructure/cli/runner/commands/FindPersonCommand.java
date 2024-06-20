package org.epptec.ppldb.infrastructure.cli.runner.commands;

import org.epptec.ppldb.domain.people.Person;
import org.epptec.ppldb.domain.people.PersonRepository;
import org.epptec.ppldb.domain.people.exceptions.InvalidIdentificationNumberException;
import org.epptec.ppldb.domain.people.exceptions.PersonNotFoundException;
import org.epptec.ppldb.domain.people.services.IdentificationNumberToAgeCalculator;
import org.epptec.ppldb.domain.people.services.IdentificationNumberToInstantConverter;
import org.epptec.ppldb.infrastructure.cli.runner.commands.utils.IdentificationNumberScanner;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class FindPersonCommand implements Command {

    private final PersonRepository personRepository;
    private final IdentificationNumberScanner identificationNumberScanner;
    IdentificationNumberToInstantConverter identificationNumberToInstantConverter;
    private final IdentificationNumberToAgeCalculator identificationNumberToAgeCalculator;

    public FindPersonCommand(
        @Autowired PersonRepository personRepository,
        @Autowired IdentificationNumberScanner identificationNumberScanner,
        @Autowired IdentificationNumberToInstantConverter identificationNumberToInstantConverter,
        @Autowired IdentificationNumberToAgeCalculator identificationNumberToAgeCalculator
    ) {
        this.personRepository = personRepository;
        this.identificationNumberScanner = identificationNumberScanner;
        this.identificationNumberToAgeCalculator = identificationNumberToAgeCalculator;
        this.identificationNumberToInstantConverter = identificationNumberToInstantConverter;
    }

    @Override
    public void run() {
        try {
            var identificationNumber = identificationNumberScanner.scan();
            var person = personRepository.get(identificationNumber);
            printPersonInfo(person);
        } catch(InvalidIdentificationNumberException e) {
            System.out.println(e.getMessage());
        } catch (PersonNotFoundException e) {
            System.out.println("Person not found.");
        }
    }

    private void printPersonInfo(Person person) {
        System.out.println("Identification number: " + person.getIdentificationNumber());
        System.out.println("First name: " + person.getFirstName());
        System.out.println("Last name: " + person.getLastName());

        var dateOfBirthInstant = identificationNumberToInstantConverter.toInstant(person.getIdentificationNumber());
        System.out.println("Age: " + identificationNumberToAgeCalculator.calculateAge(dateOfBirthInstant));
    }

    @Override
    public String getName() {
        return "Find Person";
    }

    @Override
    public int getPriority() {
        return 1;
    }
}
