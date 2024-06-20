package org.epptec.ppldb.common.inmemory.storage;

import org.epptec.ppldb.common.inmemory.storage.exceptions.NodeNotFoundException;
import org.epptec.ppldb.common.inmemory.storage.exceptions.ValueExistsException;
import org.epptec.ppldb.domain.people.Person;
import org.epptec.ppldb.domain.people.exceptions.InvalidIdentificationNumberException;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class StorageTest {

    @Test
    void itCanStoreValue() throws NodeNotFoundException, InvalidIdentificationNumberException, ValueExistsException {
        var identificationNumber = new Person.IdentificationNumber("9905059999");
        var value = "who nose";
        var storage = new PureStorage<Person.IdentificationNumber, Integer, String>(
            identifier -> identifier.toString().chars()
                .mapToObj(Character::getNumericValue)
                .toList()
        );

        storage.addValue(identificationNumber, value);

        assertEquals(value, storage.getValue(identificationNumber));
    }

    @Test
    void itCanRemoveValueAndThrowExceptionWhenNodeNotFound() throws NodeNotFoundException, InvalidIdentificationNumberException, ValueExistsException {
        var identificationNumber = new Person.IdentificationNumber("9905059999");
        var storage = new PureStorage<Person.IdentificationNumber, Integer, String>(
            identifier -> identifier.toString().chars()
                .mapToObj(Character::getNumericValue)
                .toList()
        );

        storage.addValue(identificationNumber, "jozxyqk");
        storage.removeValue(identificationNumber);

        assertThrows(NodeNotFoundException.class, () -> storage.getValue(identificationNumber));
    }

    @Test
    void itCannotStoreTwoPeopleWithSameIdentificationNumber() throws InvalidIdentificationNumberException, ValueExistsException {
        var identificationNumber = new Person.IdentificationNumber("9905059999");
        var value = "who nose";
        var storage = new PureStorage<Person.IdentificationNumber, Integer, String>(
                identifier -> identifier.toString().chars()
                        .mapToObj(Character::getNumericValue)
                        .toList()
        );

        storage.addValue(identificationNumber, value);

        assertThrows(ValueExistsException.class, () -> storage.addValue(identificationNumber, value));
    }
}
