package org.epptec.ppldb.infrastructure.cli.runner.commands;

import org.epptec.ppldb.domain.people.exceptions.InvalidIdentificationNumberException;
import org.epptec.ppldb.domain.people.exceptions.PersonNotFoundException;
import org.epptec.ppldb.infrastructure.cli.runner.commands.utils.IdentificationNumberScanner;
import org.epptec.ppldb.queries.PersonDetailQueryHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class FindPersonCommand implements Command {

    private final IdentificationNumberScanner identificationNumberScanner;
    private final PersonDetailQueryHandler personDetailQueryHandler;

    public FindPersonCommand(
        @Autowired PersonDetailQueryHandler personDetailQueryHandler,
        @Autowired IdentificationNumberScanner identificationNumberScanner
    ) {
        this.personDetailQueryHandler = personDetailQueryHandler;
        this.identificationNumberScanner = identificationNumberScanner;
    }

    @Override
    public void run() {
        try {
            var identificationNumber = identificationNumberScanner.scan();
            var personDetail = personDetailQueryHandler.find(new PersonDetailQueryHandler.Query(
                identificationNumber
            ));

            printPersonInfo(personDetail);
        } catch(InvalidIdentificationNumberException e) {
            System.out.println(e.getMessage());
        } catch (PersonNotFoundException e) {
            System.out.println("Person not found.");
        }
    }

    private void printPersonInfo(PersonDetailQueryHandler.Response personDetail) {
        System.out.println("Identification number: " + personDetail.identificationNumber().toString());
        System.out.println("First name: " + personDetail.firstName().toString());
        System.out.println("Last name: " + personDetail.lastName().toString());
        System.out.println("Age: " + personDetail.age());
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
