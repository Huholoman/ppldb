package org.epptec.ppldb.domain.people.exceptions;

public class PersonExistsException extends Exception {
    public PersonExistsException(String message) {
        super(message);
    }
}
