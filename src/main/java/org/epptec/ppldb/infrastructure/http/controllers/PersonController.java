package org.epptec.ppldb.infrastructure.http.controllers;

import org.epptec.ppldb.commands.CreatePersonCommandHandler;
import org.epptec.ppldb.commands.RemovePersonCommandHandler;
import org.epptec.ppldb.domain.people.Person;
import org.epptec.ppldb.domain.people.exceptions.EmptyNameException;
import org.epptec.ppldb.domain.people.exceptions.InvalidIdentificationNumberException;
import org.epptec.ppldb.domain.people.exceptions.PersonExistsException;
import org.epptec.ppldb.domain.people.exceptions.PersonNotFoundException;
import org.epptec.ppldb.queries.PersonDetailQueryHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class PersonController {

    private final CreatePersonCommandHandler createPersonCommandHandler;
    private final RemovePersonCommandHandler removePersonCommandHandler;
    private final PersonDetailQueryHandler personDetailQueryHandler;

    public PersonController(
        @Autowired CreatePersonCommandHandler createPersonCommandHandler,
        @Autowired RemovePersonCommandHandler removePersonCommandHandler,
        @Autowired PersonDetailQueryHandler personDetailQueryHandler
    ) {
        this.createPersonCommandHandler = createPersonCommandHandler;
        this.removePersonCommandHandler = removePersonCommandHandler;
        this.personDetailQueryHandler = personDetailQueryHandler;
    }

    @PostMapping("/people")
    public ResponseEntity<?> create(PersonRequest request) throws InvalidIdentificationNumberException, EmptyNameException {
        try {
            createPersonCommandHandler.handle(new CreatePersonCommandHandler.Command(
                new Person(
                    new Person.IdentificationNumber(request.identificationNumber()),
                    new Person.Name(request.firstName()),
                    new Person.Name(request.lastName())
                )
            ));

            return new ResponseEntity<>(HttpStatus.CREATED);
        } catch (PersonExistsException e) {
            return new ResponseEntity<>(HttpStatus.CONFLICT);
        }
    }

    @GetMapping("/people/{identificationNumberStr}")
    public ResponseEntity<PersonDetailResponse> get(@PathVariable String identificationNumberStr) {
        try {
            var detail = personDetailQueryHandler.find(new PersonDetailQueryHandler.Query(
                    new Person.IdentificationNumber(identificationNumberStr)
            ));

            return new ResponseEntity<>(
                new PersonDetailResponse(
                    detail.identificationNumber().toString(),
                    detail.firstName().toString(),
                    detail.lastName().toString(),
                    detail.age()
                ),
                HttpStatus.OK
            );
        } catch (InvalidIdentificationNumberException|PersonNotFoundException e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @DeleteMapping("/people/{identificationNumberStr}")
    public ResponseEntity<?> delete(@PathVariable String identificationNumberStr) {
        try {
            removePersonCommandHandler.handle(
                new RemovePersonCommandHandler.Command(
                    new Person.IdentificationNumber(identificationNumberStr)
                )
            );

            return new ResponseEntity<>(
                HttpStatus.OK
            );
        } catch (InvalidIdentificationNumberException|PersonNotFoundException e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    public record PersonRequest(
        String identificationNumber,
        String firstName,
        String lastName
    ) {}

    public record PersonDetailResponse(
        String identificationNumber,
        String firstName,
        String lastName,
        Integer age
    ) {}

}
