package au.com.insigniafinancial.challege.robot.simulation;

import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class DirectionTest {

    @Test
    public void testRotateClockWise(){
        Map<Direction, Direction> expectedDirections =new HashMap<>();
        expectedDirections.put(Direction.NORTH, Direction.EAST);
        expectedDirections.put(Direction.EAST, Direction.SOUTH);
        expectedDirections.put(Direction.SOUTH, Direction.WEST);
        expectedDirections.put(Direction.WEST, Direction.NORTH);
        expectedDirections.entrySet().stream().forEach(
                e->{
                    assertEquals(Direction.rotate(e.getKey(),true) ,e.getValue());
                }
        );
    }
    @Test
    public void testRotateCounterClockWise(){
        Map<Direction, Direction> expectedDirections =new HashMap<>();
        expectedDirections.put(Direction.NORTH, Direction.WEST);
        expectedDirections.put(Direction.WEST, Direction.SOUTH);
        expectedDirections.put(Direction.SOUTH, Direction.EAST);
        expectedDirections.put(Direction.EAST, Direction.NORTH);
        expectedDirections.entrySet().stream().forEach(
                e->{
                    assertEquals(Direction.rotate(e.getKey(),false) ,e.getValue());
                }
        );
    }
}
