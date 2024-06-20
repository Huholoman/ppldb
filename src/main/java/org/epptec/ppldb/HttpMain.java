package org.epptec.ppldb;

import org.epptec.ppldb.infrastructure.cli.runner.CliRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

@SpringBootApplication
@Import(CliMain.Config.class)
public class HttpMain {

    public static void main(String[] args) {
        ConfigurableApplicationContext context = SpringApplication.run(HttpMain.class, args);

        CliRunner runner = context.getBean(CliRunner.class);
        runner.run();

        context.close();
    }

    @Configuration
    public static class Config {

    }
}
