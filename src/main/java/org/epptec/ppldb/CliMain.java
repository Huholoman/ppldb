package org.epptec.ppldb;

import org.epptec.ppldb.common.inmemory.storage.PureStorage;
import org.epptec.ppldb.common.inmemory.storage.Storage;
import org.epptec.ppldb.domain.people.Person;
import org.epptec.ppldb.infrastructure.cli.runner.CliRunner;
import org.epptec.ppldb.infrastructure.cli.runner.commands.utils.IdentificationNumberScanner;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

import java.time.Clock;
import java.util.Scanner;

public class CliMain {

    public static void main(String[] args) {
        var context = new AnnotationConfigApplicationContext(Config.class);
        var runner = context.getBean(CliRunner.class);

        runner.run();
    }

    @Configuration
    @ComponentScan(basePackages = "org.epptec.ppldb")
    public static class Config {

        @Bean
        public Storage<Person.IdentificationNumber, Person> personStorage() {
            return new PureStorage<>(
                identifier -> identifier.toString().chars()
                    .mapToObj(Character::getNumericValue)
                    .toList()
            );
        }

        @Bean
        public Scanner scanner() {
            return new Scanner(System.in);
        }

        @Bean
        public IdentificationNumberScanner identificationNumberScanner(Scanner scanner) {
            return new IdentificationNumberScanner(scanner);
        }

        @Bean
        public Clock clock() {
            return Clock.systemUTC();
        }
    }
}
