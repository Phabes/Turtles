package Kasztany.Turtles.parser;


import Kasztany.Turtles.model.Direction;
import Kasztany.Turtles.model.Field;
import Kasztany.Turtles.model.Vector2d;

import java.util.Arrays;

public class MapParser {
    private final OptionsParser optionsParser=new OptionsParser();

    public Field parseMapLine(String line){
        String[] splitedLine=line.split("\\|");
        String[] coordinates=splitedLine[0].split(":");
        Vector2d currVec = new Vector2d(optionsParser.getInt(coordinates[0]), optionsParser.getInt(coordinates[1]));
        Field field=new Field(currVec);
        if(splitedLine.length>=2){
            String[] directionsStrings=splitedLine[1].split(";");
            Arrays.stream(directionsStrings).toList().forEach(dir->{
                Direction direction= switch (dir) {
                    case "n" -> Direction.NORTH;
                    case "e" -> Direction.EAST;
                    case "s" -> Direction.SOUTH;
                    case "w" -> Direction.WEST;
                    default -> null;
                };
                if(direction!=null){
                    field.addPossibleDirection(direction);
                }
            });
        }
        if(splitedLine.length==3){
            field.addFruit(optionsParser.getInt(splitedLine[2]));
        }

        return field;
    }

}
