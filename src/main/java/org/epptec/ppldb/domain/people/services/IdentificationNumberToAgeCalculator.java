package org.epptec.ppldb.domain.people.services;

import org.springframework.stereotype.Service;

import java.time.*;

@Service
public class IdentificationNumberToAgeCalculator {

    private final Clock clock;

    public IdentificationNumberToAgeCalculator(Clock clock) {
        this.clock = clock;
    }

    public Integer calculateAge(Instant dateOfBirthInstant) {
        var nowInstant = clock.instant();

        LocalDate nowDate = nowInstant.atZone(ZoneId.of("UTC")).toLocalDate();
        LocalDate dateOfBirth = dateOfBirthInstant.atZone(ZoneId.of("UTC")).toLocalDate();
        Period period = Period.between(dateOfBirth, nowDate);

        return period.getYears();
    }
}
