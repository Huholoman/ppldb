package org.epptec.ppldb.domain.people.services;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.time.*;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

class IdentificationNumberToAgeCalculatorTest {

    @ParameterizedTest
    @MethodSource("itCanCalculateAgeFromIdentificationNumberProvider")
    public void itCanCalculateAgeFromIdentificationNumber(
        Instant dateOfBirth,
        Integer expectedAge
    ) {
        var clock = Clock.fixed(
            createInstant(2025, 6, 7),
            ZoneId.of("UTC")
        );
        var calculator = new IdentificationNumberToAgeCalculator(clock);

        var actualAge = calculator.calculateAge(dateOfBirth);

        assertEquals(expectedAge, actualAge);
    }

    private static Stream<Arguments> itCanCalculateAgeFromIdentificationNumberProvider() {
        return Stream.of(
            Arguments.of(
                createInstant(1995, 6, 6),
                30
            ),
            Arguments.of(
                createInstant(1995, 8, 7),
                29
            )
        );
    }

    private static Instant createInstant(int year, int month, int day) {
        return LocalDate.of(year, month, day)
            .atStartOfDay()
            .toInstant(ZoneOffset.UTC);
    }
}