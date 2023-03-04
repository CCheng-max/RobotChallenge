package au.com.insigniafinancial.challege.robot.simulation;

import au.com.insigniafinancial.challege.robot.Utils;

import java.util.Arrays;
import java.util.Optional;

public enum Direction {
    NORTH(0, 0,1),
    EAST(1,1, 0),
    SOUTH(2,-1,0),
    WEST(3,0,-1);
    private int value;
    private int dimensionFactorX;
    private int dimensionFactorY;
    Direction(int value, int dimensionFactorX, int dimensionFactorY){
        this.value = value;
        this.dimensionFactorX = dimensionFactorX;
        this.dimensionFactorY =dimensionFactorY;
    }
    public static Direction rotate(Direction currentDirection, boolean clockWise){
        int targetValue = currentDirection.value + ( clockWise ? 1:-1);
        targetValue = targetValue > 3 ? 0:targetValue;
        targetValue = targetValue < 0 ? 3:targetValue;
        return valueOf(targetValue);
    }
    public static Direction valueOf(int value){
        Optional<Direction> directionOptional = Arrays.stream(values()).filter(d -> d.value == value).findFirst();
        return directionOptional.orElseGet(null);
    }
    public static Direction parseByName(String name){
        if(name == null){
            throw new IllegalArgumentException("Direction name can't be null.");
        }
        return Direction.valueOf(Utils.trimLeftAndRight(name.toUpperCase()));
    }
    public int getDimensionFactorX() {
        return dimensionFactorX;
    }

    public int getDimensionFactorY() {
        return dimensionFactorY;
    }
}
