package org.epptec.ppldb.domain.people;

import org.epptec.ppldb.domain.people.exceptions.PersonExistsException;
import org.epptec.ppldb.domain.people.exceptions.PersonNotFoundException;

public interface PersonRepository {
    Person get(Person.IdentificationNumber identificationNumber) throws PersonNotFoundException;
    void save(Person person) throws PersonExistsException;
    void remove(Person.IdentificationNumber identificationNumber) throws PersonNotFoundException;
}
