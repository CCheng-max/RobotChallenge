package au.com.insigniafinancial.challege.robot.simulation;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode()
public class Robot {
    private int id;
    private Position position;
    private Direction direction;
    public Position predictNextPosition(){
        return new Position(position.getX()+ direction.getDimensionFactorX(),
                position.getY() + direction.getDimensionFactorY());
    }

    /**
     *
     * @return the old postion before moving
     */
    public Position move(){
        Position currentPosition = position;
        position = new Position(position.getX()+ direction.getDimensionFactorX(),
                position.getY() + direction.getDimensionFactorY());
        return currentPosition;
    }

    /**
     * rotate the direction 90 degrees
     * @param clockWise true clockSize, false counter clockSize
     * @return Direction before rotated.
     */
    public Direction rotate(boolean clockWise){
        Direction oldDirection = this.direction;
        this.direction = Direction.rotate(direction,clockWise);
        return oldDirection;
    }
    public String toString(){
        return new StringBuilder().append(position).append(",").append(direction).toString();
    }
}
