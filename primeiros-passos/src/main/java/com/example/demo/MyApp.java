package com.example.demo;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class MyApp implements CommandLineRunner {
    @Autowired
    private Calculate calculate;

    @Override
    public void run(String... args) throws Exception {
        System.out.println("Hello World Spring boot!");

        System.out.println("Soma: " + calculate.somar(10, 20));
    }
}
