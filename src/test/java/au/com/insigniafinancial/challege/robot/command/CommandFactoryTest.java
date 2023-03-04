package au.com.insigniafinancial.challege.robot.command;

import au.com.insigniafinancial.challege.robot.simulation.Direction;
import au.com.insigniafinancial.challege.robot.simulation.Position;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class CommandFactoryTest {
    @Test
    public void testCommandFactoryBuildPlaceCommand(){
        CommandFactory commandFactory =new CommandFactory();
        List<String> testInputs =new ArrayList<>();
        List<Command> expectedCommands = new ArrayList<>();
        testInputs.add("place ");expectedCommands.add(null);//not enough value

        testInputs.add("place 1,2,0");expectedCommands.add(null);//wrong direction
        testInputs.add("place, 1,2,NORTH");expectedCommands.add(null);//wrong command :'place,'
        testInputs.add("place, 1,ab,NORTH");expectedCommands.add(null);//wrong position value
        testInputs.add("place, ab,1,NORTH");expectedCommands.add(null);//wrong position value
        testInputs.add("place 1,2,NORTH");expectedCommands.add(new PlaceCommand(new Position(1,2), Direction.NORTH));
        testInputs.add("place   1,2,NORTH");expectedCommands.add(new PlaceCommand(new Position(1,2), Direction.NORTH));//will trim the spaces
        testInputs.add("place 1 ,2 ,NORTH");expectedCommands.add(new PlaceCommand(new Position(1,2), Direction.NORTH));//will trim the spaces
        testInputs.add(" place 2,3,EAST ");expectedCommands.add(new PlaceCommand(new Position(2,3), Direction.EAST));
        for(int i = 0 ;i<testInputs.size();i++){
            assertEquals(expectedCommands.get(i), commandFactory.buildCommand(testInputs.get(i)),"source:" + testInputs.get(i));
        }
    }
    @Test
    public void testCommandFactoryBuildUnknownCommand(){
        CommandFactory commandFactory =new CommandFactory();
        List<String> testInputs =new ArrayList<>();
        List<Command> expectedCommands = new ArrayList<>();
        testInputs.add("ROBOT ");expectedCommands.add(null);//not enough value
        testInputs.add("ROBOT 1,2,0");expectedCommands.add(null);//too many values
        testInputs.add("ROBOT, 1");expectedCommands.add(null);//wrong format
        testInputs.add("ROBOT bac");expectedCommands.add(null);//not an id after robot command
        testInputs.add("ROBOT 1");expectedCommands.add(new RobotCommand(1));
        testInputs.add("ROBOT  1");expectedCommands.add(new RobotCommand(1));
        for(int i = 0 ;i<testInputs.size();i++){
            assertEquals(expectedCommands.get(i), commandFactory.buildCommand(testInputs.get(i)),"source: " +testInputs.get(i));
        }
    }
    @Test
    public void testCommandFactoryBuildIllegalCommand(){
        CommandFactory commandFactory =new CommandFactory();
        List<String> testInputs =new ArrayList<>();
        List<Command> expectedCommands = new ArrayList<>();
        testInputs.add("ROBOT ");expectedCommands.add(null);
        testInputs.add("23   1,2,0");expectedCommands.add(null);
        testInputs.add("place2");expectedCommands.add(null);
        testInputs.add("1move");expectedCommands.add(null);
        testInputs.add("2robot, ");expectedCommands.add(null);
        testInputs.add("left, 1,");expectedCommands.add(null);
        testInputs.add("2RIGHT 2 ");expectedCommands.add(null);
        testInputs.add("2 RIGHT 2 ");expectedCommands.add(null);
        testInputs.add(",REPORT");expectedCommands.add(null);
        for(int i = 0 ;i<testInputs.size();i++){
            assertEquals(expectedCommands.get(i), commandFactory.buildCommand(testInputs.get(i)),"source: " +testInputs.get(i));
        }
    }
    @Test
    public void testCommandFactoryBuildSingleCommand(){
        CommandFactory commandFactory =new CommandFactory();
        List<String> testInputs =new ArrayList<>();
        List<Command> expectedCommands = new ArrayList<>();
        Command left = new Command(CommandType.LEFT);
        Command right =new Command(CommandType.RIGHT);
        Command report  =new Command(CommandType.REPORT);
        Command move  =new Command(CommandType.MOVE);
        testInputs.add("LEFT");expectedCommands.add(left);
        testInputs.add("LEFT ");expectedCommands.add(left);
        testInputs.add("left 1");expectedCommands.add(left);
        testInputs.add(" LEFT");expectedCommands.add(left);
        testInputs.add("RIGHT");expectedCommands.add(right);
        testInputs.add("RIGHT ");expectedCommands.add(right);
        testInputs.add("right ,");expectedCommands.add(right);
        testInputs.add(" RIGHT ");expectedCommands.add(right);
        testInputs.add("REPORT");expectedCommands.add(report);
        testInputs.add("REPORT ");expectedCommands.add(report);
        testInputs.add("report ,");expectedCommands.add(report);
        testInputs.add(" REPORT 2");expectedCommands.add(report);
        testInputs.add("MOVE");expectedCommands.add(move);
        testInputs.add("MOVE ");expectedCommands.add(move);
        testInputs.add("move ,");expectedCommands.add(move);
        testInputs.add(" move 2");expectedCommands.add(move);
        for(int i = 0 ;i<testInputs.size();i++){
            assertEquals(expectedCommands.get(i), commandFactory.buildCommand(testInputs.get(i)),"source: " +testInputs.get(i));
        }
    }

}
