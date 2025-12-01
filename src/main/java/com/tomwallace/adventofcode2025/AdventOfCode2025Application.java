package com.tomwallace.adventofcode2025;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.shell.standard.ShellComponent;

@SpringBootApplication
@ShellComponent
public class AdventOfCode2025Application {

    public static void main(String[] args) {
        SpringApplication.run(AdventOfCode2025Application.class, args);
    }
}
