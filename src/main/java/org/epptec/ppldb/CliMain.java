package org.epptec.ppldb;

import org.epptec.ppldb.common.inmemory.storage.PureStorage;
import org.epptec.ppldb.domain.people.Person;
import org.epptec.ppldb.domain.people.services.IdentificationNumberToAgeCalculator;
import org.epptec.ppldb.domain.people.services.IdentificationNumberToInstantConverter;
import org.epptec.ppldb.infrastructure.cli.runner.CliRunner;
import org.epptec.ppldb.infrastructure.cli.runner.commands.CreatePersonCommand;
import org.epptec.ppldb.infrastructure.cli.runner.commands.FindPersonCommand;
import org.epptec.ppldb.infrastructure.cli.runner.commands.QuitCommand;
import org.epptec.ppldb.infrastructure.cli.runner.commands.RemovePersonCommand;
import org.epptec.ppldb.infrastructure.cli.runner.commands.utils.IdentificationNumberScanner;
import org.epptec.ppldb.infrastructure.inmemory.repositories.InmemoryPersonRepository;

import java.time.Clock;
import java.util.List;
import java.util.Scanner;


public class CliMain {

    public static void main(String[] args) {
        var clock = Clock.systemUTC();

        var personStorage = new PureStorage<Person.IdentificationNumber, Integer, Person>(
            identifier -> identifier.toString().chars()
                .mapToObj(Character::getNumericValue)
                .toList()
        );
        var personRepository = new InmemoryPersonRepository(personStorage);
        var identificationNumberToInstantConverter = new IdentificationNumberToInstantConverter();
        var identificationNumberToAgeCalculator = new IdentificationNumberToAgeCalculator(clock);

        var scanner = new Scanner(System.in);
        var identificationNumberScanner = new IdentificationNumberScanner(scanner);

        var runner = new CliRunner(
            scanner,
            List.of(
                new CreatePersonCommand(scanner, personRepository, identificationNumberScanner),
                new FindPersonCommand(
                    personRepository,
                    identificationNumberScanner,
                    identificationNumberToInstantConverter,
                    identificationNumberToAgeCalculator
                ),
                new RemovePersonCommand(personRepository, identificationNumberScanner),
                new QuitCommand()
            )
        );

        runner.run();
    }
}
