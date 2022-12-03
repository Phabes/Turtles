package Kasztany.Turtles.parser;

import org.springframework.stereotype.Controller;

@Controller
public class OptionsParser {
    public int getInt(String value){
        return Integer.parseInt(value);
    }
}
