package org.epptec.ppldb.domain.people.services;

import org.epptec.ppldb.domain.people.Person;
import org.epptec.ppldb.domain.people.exceptions.InvalidIdentificationNumberException;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.time.*;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

class IdentificationNumberToDateConverterTest {

    @ParameterizedTest
    @MethodSource("itCanSuccessfullyConvertProvider")
    public void itCanSuccessfullyConvert(
        Person.IdentificationNumber identificationNumber,
        Instant expectedInstant
    ) {
        var service = new IdentificationNumberToInstantConverter();

        var actualInstant = service.toInstant(identificationNumber);

        assertEquals(expectedInstant, actualInstant);
    }

    public static Stream<Arguments> itCanSuccessfullyConvertProvider() throws InvalidIdentificationNumberException {
        return Stream.of(
            Arguments.of(
                new Person.IdentificationNumber("935501/6666"),
                createDate(1993, 5, 1)
            ),
            Arguments.of(
                new Person.IdentificationNumber("200105/6666"),
                createDate(2020, 1, 5)
            )
        );
    }

    private static Instant createDate(int year, int month, int day) {
        return LocalDate.of(year, month, day)
            .atStartOfDay()
            .toInstant(ZoneOffset.UTC);
    }
}