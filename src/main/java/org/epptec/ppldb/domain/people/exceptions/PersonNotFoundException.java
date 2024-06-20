package org.epptec.ppldb.domain.people.exceptions;

import org.epptec.ppldb.domain.people.Person;

public class PersonNotFoundException extends Exception {

    public PersonNotFoundException(Person.IdentificationNumber identificationNumber) {
        super("Person with identification number " + identificationNumber + " not found");
    }

    public PersonNotFoundException(Person.IdentificationNumber identificationNumber, Exception parent) {
        super("Person with identification number " + identificationNumber + " not found", parent);
    }

}
