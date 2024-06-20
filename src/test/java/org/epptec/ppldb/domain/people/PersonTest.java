package org.epptec.ppldb.domain.people;

import org.epptec.ppldb.domain.people.exceptions.InvalidIdentificationNumberException;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static org.junit.jupiter.api.Assertions.*;

class PersonTest {

    @ParameterizedTest
    @ValueSource(strings = {"9005056666", "865505/7777"})
    public void itCanCreateIdentificationNumberFromValidValue(String value) throws InvalidIdentificationNumberException {
        new Person.IdentificationNumber(value);
    }

    @ParameterizedTest
    @ValueSource(strings = {"900505666A", "865505/777", "86550/57777"})
    public void itThrowsInvalidIdentificationNumberException(String value) {
        assertThrows(InvalidIdentificationNumberException.class, () -> new Person.IdentificationNumber(value));
    }
}
