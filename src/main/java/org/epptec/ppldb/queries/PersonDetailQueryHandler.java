package org.epptec.ppldb.queries;

import org.epptec.ppldb.domain.people.Person;
import org.epptec.ppldb.domain.people.PersonRepository;
import org.epptec.ppldb.domain.people.exceptions.PersonNotFoundException;
import org.epptec.ppldb.domain.people.services.IdentificationNumberToAgeCalculator;
import org.epptec.ppldb.domain.people.services.IdentificationNumberToInstantConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PersonDetailQueryHandler {

    private final PersonRepository personRepository;
    private final IdentificationNumberToInstantConverter identificationNumberToInstantConverter;
    private final IdentificationNumberToAgeCalculator identificationNumberToAgeCalculator;

    public PersonDetailQueryHandler(
        @Autowired PersonRepository personRepository,
        @Autowired IdentificationNumberToInstantConverter identificationNumberToInstantConverter,
        @Autowired IdentificationNumberToAgeCalculator identificationNumberToAgeCalculator
    ) {
        this.personRepository = personRepository;
        this.identificationNumberToInstantConverter = identificationNumberToInstantConverter;
        this.identificationNumberToAgeCalculator = identificationNumberToAgeCalculator;
    }

    public Response find(Query query) throws PersonNotFoundException {
        var person = personRepository.get(query.identificationNumber());
        var dayOfBirthInstant = identificationNumberToInstantConverter.toInstant(query.identificationNumber());

        return new Response(
            person.getIdentificationNumber(),
            person.getFirstName(),
            person.getLastName(),
            identificationNumberToAgeCalculator.calculateAge(dayOfBirthInstant)
        );
    }

    public record Query(
        Person.IdentificationNumber identificationNumber
    ) {}

    public record Response(
        Person.IdentificationNumber identificationNumber,
        Person.Name firstName,
        Person.Name lastName,
        Integer age
    ) {}
}
