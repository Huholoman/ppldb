package org.epptec.ppldb.commands;

import org.epptec.ppldb.domain.people.Person;
import org.epptec.ppldb.domain.people.PersonRepository;
import org.epptec.ppldb.domain.people.exceptions.PersonNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RemovePersonCommandHandler {

    private final PersonRepository personRepository;

    public RemovePersonCommandHandler(@Autowired PersonRepository personRepository) {
        this.personRepository = personRepository;
    }

    public void handle(RemovePersonCommandHandler.Command command) throws PersonNotFoundException {
        personRepository.remove(command.identificationNumber());
    }

    public record Command(
        Person.IdentificationNumber identificationNumber
    ) {}
}
