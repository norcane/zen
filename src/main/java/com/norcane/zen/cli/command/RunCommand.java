package com.norcane.zen.cli.command;

import com.norcane.zen.config.AppConfig;
import com.norcane.zen.config.AppConfigManager;

import javax.inject.Inject;

import picocli.CommandLine;

@CommandLine.Command(
    name = "run",
    description = "adds copyright headers"
)
public class RunCommand extends SubCommand {

    private final AppConfigManager appConfigManager;

    @Inject
    public RunCommand(AppConfigManager appConfigManager) {
        this.appConfigManager = appConfigManager;
    }

    @Override
    public void run() {
        printProductHeader();

        System.out.println("RUN command here");
        final AppConfig appConfig = appConfigManager.loadDefaultConfig();
        System.out.println(appConfig);
    }
}
