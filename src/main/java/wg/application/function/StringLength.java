package wg.application.function;

import java.util.function.Function;

public class StringLength implements Function<String, Integer> {

    @Override
    public Integer apply(String str) {
        return str.length();
    }
}
