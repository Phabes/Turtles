package Kasztany.Turtles.model;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class Neighbourhood {
    private final Map<Direction, Optional<Field>> neighbours;

    public Neighbourhood(){
        this.neighbours = new HashMap<>();
        for (Direction direction: Direction.values()) {
            neighbours.put(direction, Optional.empty());
        }
    }

    public void setNeighbour(Direction direction, Field field){
        neighbours.put(direction, Optional.of(field));
    }

    public Optional<Field> getNeighbour(Direction direction){
        return neighbours.get(direction);
    }
}
