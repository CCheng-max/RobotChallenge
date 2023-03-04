package au.com.insigniafinancial.challege.robot.simulation;

import org.junit.jupiter.api.Test;
import org.mockito.MockedStatic;
import org.mockito.Mockito;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.when;

public class RobotTest {
    @Test
    public void testRotate(){
       MockedStatic<Direction> directionMockedStatic = Mockito.mockStatic(Direction.class);
       directionMockedStatic.when(() ->Direction.rotate(Direction.NORTH, true)).thenReturn(Direction.EAST);
       Robot robot = new Robot();
       robot.setDirection(Direction.NORTH);
       Direction oldDirection  = robot.rotate(true);
       assertEquals( Direction.EAST,robot.getDirection());
       assertEquals(Direction.NORTH,oldDirection);
       directionMockedStatic.verify(()->Direction.rotate(Direction.NORTH,true),times(1));
    }
    @Test
    public void testPredictMove(){
        Direction directionMocked = Mockito.mock(Direction.class);
        when(directionMocked.getDimensionFactorX()).thenReturn(-1);
        when(directionMocked.getDimensionFactorY()).thenReturn(1);
        Robot robot =new Robot(0,new Position(2,0),directionMocked);
        Position nextPosition = robot.predictNextPosition();
        assertEquals(1, nextPosition.getX());
        assertEquals(1, nextPosition.getY());
        Mockito.verify(directionMocked, times(1)).getDimensionFactorX();
        Mockito.verify(directionMocked, times(1)).getDimensionFactorY();
    }
    @Test
    public void testMove(){
        Direction directionMocked = Mockito.mock(Direction.class);
        when(directionMocked.getDimensionFactorX()).thenReturn(-1);
        when(directionMocked.getDimensionFactorY()).thenReturn(1);
        Robot robot =new Robot(0,new Position(2,0),directionMocked);
        Position oldPotion = robot.move();
        assertEquals(2, oldPotion.getX());
        assertEquals(0, oldPotion.getY());
        assertEquals(1,robot.getPosition().getX());
        assertEquals(1,robot.getPosition().getY());
        Mockito.verify(directionMocked, times(1)).getDimensionFactorX();
        Mockito.verify(directionMocked, times(1)).getDimensionFactorY();
    }
}
