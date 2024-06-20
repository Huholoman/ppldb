package org.epptec.ppldb.domain.people.exceptions;

import org.epptec.ppldb.domain.people.Person;

public class PersonExistsException extends Exception {
    public PersonExistsException(Person.IdentificationNumber identificationNumber) {
        super("Person with identification number " + identificationNumber + " already exists");
    }
}
