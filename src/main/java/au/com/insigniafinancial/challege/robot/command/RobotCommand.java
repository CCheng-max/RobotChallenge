package au.com.insigniafinancial.challege.robot.command;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class RobotCommand extends Command{
    private int activeRobot;
    public RobotCommand(){
        super(CommandType.ROBOT);
    }
    public RobotCommand(int activeRobot){
        super(CommandType.ROBOT);
        this.activeRobot =activeRobot;
    }
}
