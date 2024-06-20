package org.epptec.ppldb.domain.people;

import org.epptec.ppldb.domain.people.exceptions.EmptyNameException;
import org.epptec.ppldb.domain.people.exceptions.InvalidIdentificationNumberException;

import java.util.Objects;
import java.util.regex.Pattern;

public class Person  {

    private final IdentificationNumber identificationNumber;
    private final Name firstName;
    private final Name lastName;

    public Person(
        IdentificationNumber identificationNumber,
        Name firstName,
        Name lastName
    ) {
        this.identificationNumber = identificationNumber;
        this.firstName = firstName;
        this.lastName = lastName;
    }

    public IdentificationNumber getIdentificationNumber() {
        return identificationNumber;
    }

    public Name getFirstName() {
        return firstName;
    }

    public Name getLastName() {
        return lastName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Person person = (Person) o;
        return Objects.equals(identificationNumber, person.identificationNumber);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(identificationNumber);
    }

    public static class IdentificationNumber {

        private static final Pattern regexPattern = Pattern.compile("^\\d{6}(\\/)?\\d{4}$");

        private final String value;

        public IdentificationNumber(String value) throws InvalidIdentificationNumberException {
            this.value = validateAndNormalize(value);
        }

        @Override
        public String toString() {
            return value;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            IdentificationNumber that = (IdentificationNumber) o;
            return Objects.equals(value, that.value);
        }

        @Override
        public int hashCode() {
            return Objects.hashCode(value);
        }

        private static String validateAndNormalize(String value) throws InvalidIdentificationNumberException {
            if (!regexPattern.matcher(value).matches()) {
                throw new InvalidIdentificationNumberException(value);
            }

            return value.replace("/", "");
        }
    }

    public static class Name {

        private final String value;

        public Name(String value) throws EmptyNameException {
            this.value = validateAndNormalize(value);
        }

        @Override
        public String toString() {
            return value;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            Name name = (Name) o;
            return Objects.equals(value, name.value);
        }

        @Override
        public int hashCode() {
            return Objects.hash(value);
        }

        private static String validateAndNormalize(String value) throws EmptyNameException {
            if (value.isEmpty()) {
                throw new EmptyNameException();
            }

            return value;
        }
    }
}
