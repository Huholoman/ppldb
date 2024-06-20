package org.epptec.ppldb.domain.people.exceptions;

public class EmptyNameException extends Exception {
    public EmptyNameException() {
        super("Name is empty");
    }
}
