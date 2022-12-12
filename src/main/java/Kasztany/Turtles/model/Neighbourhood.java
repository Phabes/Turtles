package Kasztany.Turtles.model;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.HashMap;

@Service
public class Neighbourhood {
    private final HashMap<Vector2d, Field> wholeNeighbourhood;

    @Autowired
    public Neighbourhood(){
        this.wholeNeighbourhood = new HashMap<>();
    }

    public void addField(Vector2d vector, Field field){
        wholeNeighbourhood.put(vector, field);
    }

    public Field getFieldByVector(Vector2d vector){
        return wholeNeighbourhood.get(vector);
    }

    public HashMap<Vector2d, Field> getWholeNeighbourhood() {
        return wholeNeighbourhood;
    }

    public Collection<Field> getFields(){
        return wholeNeighbourhood.values();
    }
}
