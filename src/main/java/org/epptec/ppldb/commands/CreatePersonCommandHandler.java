package org.epptec.ppldb.commands;

import org.epptec.ppldb.domain.people.Person;
import org.epptec.ppldb.domain.people.PersonRepository;
import org.epptec.ppldb.domain.people.exceptions.PersonExistsException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service()
public class CreatePersonCommandHandler {

    private final PersonRepository personRepository;

    public CreatePersonCommandHandler(@Autowired PersonRepository personRepository) {
        this.personRepository = personRepository;
    }

    public void handle(Command command) throws PersonExistsException {
        personRepository.save(command.person());
    }

    public record Command(Person person) {}
}
