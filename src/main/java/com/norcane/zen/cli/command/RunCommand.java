package com.norcane.zen.cli.command;

import com.norcane.zen.config.AppConfig;
import com.norcane.zen.config.AppConfigManager;
import com.norcane.zen.ui.Console;

import javax.inject.Inject;

import picocli.CommandLine;

@CommandLine.Command(
    name = "run",
    description = "adds copyright headers"
)
public class RunCommand extends SubCommand {

    private final AppConfigManager appConfigManager;

    @Inject
    public RunCommand(AppConfigManager appConfigManager,
                      Console console) {

        super(console);
        this.appConfigManager = appConfigManager;
    }

    @Override
    public void execute() {
        System.out.println("RUN command here");
        final AppConfig appConfig = appConfigManager.userConfig();
        System.out.println(appConfig);
    }
}
