package org.epptec.ppldb.infrastructure.inmemory.repositories;

import org.epptec.ppldb.common.inmemory.storage.Storage;
import org.epptec.ppldb.common.inmemory.storage.exceptions.NodeNotFoundException;
import org.epptec.ppldb.common.inmemory.storage.exceptions.ValueExistsException;
import org.epptec.ppldb.domain.people.Person;
import org.epptec.ppldb.domain.people.PersonRepository;
import org.epptec.ppldb.domain.people.exceptions.PersonExistsException;
import org.epptec.ppldb.domain.people.exceptions.PersonNotFoundException;
import org.springframework.stereotype.Repository;

@Repository
public class InmemoryPersonRepository implements PersonRepository {
    private final Storage<Person.IdentificationNumber, Person> storage;

    public InmemoryPersonRepository(Storage<Person.IdentificationNumber, Person> storage) {
        this.storage = storage;
    }

    @Override
    public Person get(Person.IdentificationNumber identificationNumber) throws PersonNotFoundException {
        try {
            return storage.getValue(identificationNumber);
        } catch (NodeNotFoundException e) {
            throw new PersonNotFoundException(identificationNumber, e);
        }
    }

    @Override
    public void save(Person person) throws PersonExistsException {
        try {
            storage.addValue(person.getIdentificationNumber(), person);
        } catch (ValueExistsException e) {
            throw new PersonExistsException(person.getIdentificationNumber());
        }
    }

    @Override
    public void remove(Person.IdentificationNumber identificationNumber) throws PersonNotFoundException {
        try {
            storage.removeValue(identificationNumber);
        } catch (NodeNotFoundException e) {
            throw new PersonNotFoundException(identificationNumber, e);
        }
    }
}
