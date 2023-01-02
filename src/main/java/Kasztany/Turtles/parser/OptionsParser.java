package Kasztany.Turtles.parser;

import org.springframework.stereotype.Component;

@Component
public class OptionsParser {
    public int getInt(String value) {
        return Integer.parseInt(value);
    }
}
