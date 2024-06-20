package org.epptec.ppldb.infrastructure.cli.runner.commands;

import org.springframework.stereotype.Service;

@Service
public class QuitCommand implements Command {

    @Override
    public void run() {

    }

    @Override
    public String getName() {
        return "Quit";
    }

    @Override
    public int getPriority() {
        return 0;
    }
}
