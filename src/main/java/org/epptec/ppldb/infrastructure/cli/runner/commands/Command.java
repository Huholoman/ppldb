package org.epptec.ppldb.infrastructure.cli.runner.commands;

public interface Command {
    void run();
    String getName();
    int getPriority();
}
