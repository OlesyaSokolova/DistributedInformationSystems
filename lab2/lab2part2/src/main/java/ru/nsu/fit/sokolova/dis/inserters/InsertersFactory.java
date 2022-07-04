package ru.nsu.fit.sokolova.dis.inserters;

import ru.nsu.fit.sokolova.dis.inserters.impl.*;

import java.util.HashMap;
import java.util.Map;

public class InsertersFactory {
    private static final Map<INSERT_STRATEGY, Inserter> inserters = new HashMap<>();
    static {
       inserters.put(INSERT_STRATEGY.STRING, new StringInserter());
       inserters.put(INSERT_STRATEGY.PREPARED_STATEMENT, new PreparedStatementInserter());
       inserters.put(INSERT_STRATEGY.BATCH, new BatchInserter());
    }

    public static Inserter getInserterByStartegyType(INSERT_STRATEGY strategyType) {
        return inserters.get(strategyType);
    }
}
