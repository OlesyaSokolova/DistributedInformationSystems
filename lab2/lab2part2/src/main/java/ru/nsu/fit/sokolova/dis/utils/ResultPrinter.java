package ru.nsu.fit.sokolova.dis.utils;

import ru.nsu.fit.sokolova.dis.inserters.INSERT_STRATEGY;
import ru.nsu.fit.sokolova.dis.inserters.Inserter;

import java.util.AbstractMap;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

public class ResultPrinter {

    public static void printSpeed(Map<INSERT_STRATEGY, Double> speeds) {
        speeds.forEach((key, value) -> System.out.println("Speed for strategy \"" + key + "\": " + value + " insertions/seconds."));
    }
}
