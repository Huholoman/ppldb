package org.epptec.ppldb.domain.people.services;

import org.epptec.ppldb.domain.people.Person;

import java.time.*;
import java.util.List;

public class IdentificationNumberToInstantConverter {
    public Instant toInstant(Person.IdentificationNumber identificationNumber) {
        var parts = identificationNumber.toString().chars()
                .mapToObj(c -> Character.toString((char) c))
                .toList();

        var year = resolveYear(parts.subList(0, 2));
        var month = resolveMonth(parts.subList(2, 4));
        var day = resolveDay(parts.subList(4, 6));

        return LocalDate.of(year, month, day)
            .atStartOfDay()
            .toInstant(ZoneOffset.UTC);
    }

    private static Integer resolveYear(List<String> parts) {
        var stringValue = String.join("", parts);
        var yearLastTwoDigits = Integer.parseInt(stringValue);

        if (yearLastTwoDigits < 54) {
            return yearLastTwoDigits + 2000;
        }

        return yearLastTwoDigits + 1900;
    }

    private static Integer resolveMonth(List<String> parts) {
        var stringValue = String.join("", parts);
        var month = Integer.parseInt(stringValue);

        if (month > 50) {
            return month - 50;
        }

        return month;
    }

    private Integer resolveDay(List<String> parts) {
        var stringValue = String.join("", parts);

        return Integer.parseInt(stringValue);
    }
}
