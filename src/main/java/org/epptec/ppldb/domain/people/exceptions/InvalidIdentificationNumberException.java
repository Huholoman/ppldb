package org.epptec.ppldb.domain.people.exceptions;

public class InvalidIdentificationNumberException extends Exception {

    public InvalidIdentificationNumberException(String value) {
        super("Invalid identification number, expected YYMMDDXXXX, given " + value);
    }

}
