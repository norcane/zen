package com.norcane.zen.cli;

import com.norcane.zen.cli.command.RunCommand;
import com.norcane.zen.meta.ProductInfo;

import io.quarkus.picocli.runtime.annotations.TopCommand;
import picocli.CommandLine;

/**
 * Initial entry point of the CLI application.
 */
@TopCommand
@CommandLine.Command(
    name = ProductInfo.NAME,
    description = ProductInfo.DESCRIPTION,
    version = ProductInfo.VERSION,
    mixinStandardHelpOptions = true,
    subcommands = {RunCommand.class}
)
public class ZenApplication {
}
