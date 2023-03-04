package au.com.insigniafinancial.challege.robot.command;

import au.com.insigniafinancial.challege.robot.simulation.Direction;
import au.com.insigniafinancial.challege.robot.simulation.Position;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class PlaceCommand extends Command{
    private Position position;
    private Direction direction;
    public PlaceCommand(){
        super(CommandType.PLACE);
    }
    public PlaceCommand(Position position, Direction direction){
        super(CommandType.PLACE);
        this.position = position;
        this.direction =direction;
    }
}
