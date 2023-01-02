package Kasztany.Turtles.parser;


import Kasztany.Turtles.model.Direction;
import Kasztany.Turtles.model.Field;
import Kasztany.Turtles.model.Vector2d;

import java.util.Arrays;

public class MapParser {
    private final OptionsParser optionsParser = new OptionsParser();

    public Field parseMapLine(String line) {
        String[] splitedLine = line.split("\\|");
        String[] coordinates = splitedLine[0].split(":");
        Vector2d currVec = new Vector2d(optionsParser.getInt(coordinates[0]), optionsParser.getInt(coordinates[1]));
        Field field = new Field(currVec);
        if (splitedLine.length >= 2) {

            String[] splitedDirectionsStrings = splitedLine[1].split(";");
            String[] forwardDirectionsStrings = new String[0];
            String[] backwardDirectionsStrings = new String[0];
            if(splitedDirectionsStrings.length>0)
                forwardDirectionsStrings = splitedDirectionsStrings[0].split(",");
            Arrays.stream(forwardDirectionsStrings).toList().forEach(dir -> {
                Direction direction = switch (dir) {
                    case "n" -> Direction.NORTH;
                    case "e" -> Direction.EAST;
                    case "s" -> Direction.SOUTH;
                    case "w" -> Direction.WEST;
                    default -> null;
                };
                if (direction != null) {
                    field.addPossibleForwardDirections(direction);
                }
            });
            if(splitedDirectionsStrings.length>1)
                backwardDirectionsStrings = splitedDirectionsStrings[1].split(",");
            Arrays.stream(backwardDirectionsStrings).toList().forEach(dir -> {
                Direction direction = switch (dir) {
                    case "n" -> Direction.NORTH;
                    case "e" -> Direction.EAST;
                    case "s" -> Direction.SOUTH;
                    case "w" -> Direction.WEST;
                    default -> null;
                };
                if (direction != null) {
                    field.addPossibleBackwardDirections(direction);
                }
            });
        }
        if (splitedLine.length == 3) {
            field.addFruit(optionsParser.getInt(splitedLine[2]));
        }

        return field;
    }

}
