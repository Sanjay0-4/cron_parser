package com.example.demo.strategy;

import java.util.List;

public class StrategyRegistry {
    private static final List<FieldPartParserStrategy> strategies = List.of(
            new StepStrategy(),
            new RangeStrategy(),
            new WildcardStrategy(),
            new LiteralValueStrategy()
    );

    public static FieldPartParserStrategy getMatchingStrategy(String part) {
        return strategies.stream()
                .filter(s -> s.supports(part))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Unsupported part: " + part));
    }
}
