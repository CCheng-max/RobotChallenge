package au.com.insigniafinancial.challege.robot.simulation;

import au.com.insigniafinancial.challege.robot.Constants;
import au.com.insigniafinancial.challege.robot.I18N;
import au.com.insigniafinancial.challege.robot.command.Command;
import au.com.insigniafinancial.challege.robot.command.CommandType;
import au.com.insigniafinancial.challege.robot.command.PlaceCommand;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.when;

public class TabletopTest {
    @Test
    public void testExecuteCommandRotate(){
        Tabletop tabletop =new Tabletop(Constants.MAX_DIMENSION_X, Constants.MAX_DIMENSION_Y);
        Tabletop tabletopSpy = Mockito.spy(tabletop);
        Command commandLeft = new Command(CommandType.LEFT);
        Command commandRight = new Command(CommandType.RIGHT);
        Response fakeResponse = new Response();
        doReturn(fakeResponse).when(tabletopSpy).rotateRobot(false);
        doReturn(fakeResponse).when(tabletopSpy).rotateRobot(true);
        assertEquals(fakeResponse, tabletopSpy.executeCommand(commandLeft));
        assertEquals(fakeResponse, tabletopSpy.executeCommand(commandRight));
        Mockito.verify(tabletopSpy, times(1)).rotateRobot(false);
        Mockito.verify(tabletopSpy, times(1)).rotateRobot(true);
    }
    @Test
    public void testReportNoRobots(){
        Tabletop tabletop =new Tabletop(Constants.MAX_DIMENSION_X, Constants.MAX_DIMENSION_Y);
        Tabletop tabletopSpy = Mockito.spy(tabletop);
        List<Robot> robots =new ArrayList<>();
        doReturn(robots).when(tabletopSpy).getRobots();
        doReturn(null).when(tabletopSpy).getActiveRobot();
        Response response = tabletopSpy.report();
        assertEquals(Response.SUCCESS_RESPONSE,response.getCode());
        String expectedMessage = String.format(I18N.REPORT,0,"");
        assertEquals(expectedMessage, response.getMessage());
        Mockito.verify(tabletopSpy, times(1)).getRobots();
        Mockito.verify(tabletopSpy, times(1)).getActiveRobot();
    }
    @Test
    public void testReportHasRobots(){
        Tabletop tabletop =new Tabletop(Constants.MAX_DIMENSION_X, Constants.MAX_DIMENSION_Y);
        Tabletop tabletopSpy = Mockito.spy(tabletop);
        Robot robot = new Robot(0, new Position(1,2),Direction.NORTH);
        List<Robot> robots =new ArrayList<>();
        robots.add(robot);
        doReturn(robots).when(tabletopSpy).getRobots();
        doReturn(robot).when(tabletopSpy).getActiveRobot();
        Response response = tabletopSpy.report();
        assertEquals(Response.SUCCESS_RESPONSE,response.getCode());
        String expectedMessage = String.format(I18N.REPORT,1,"1,2,NORTH");
        assertEquals(expectedMessage, response.getMessage());
        Mockito.verify(tabletopSpy, times(1)).getRobots();
        Mockito.verify(tabletopSpy, times(2)).getActiveRobot();
    }
    @Test
    public void testActiveRobotNoInvalidID(){
        Tabletop tabletop =new Tabletop(Constants.MAX_DIMENSION_X, Constants.MAX_DIMENSION_Y);
        Tabletop tabletopSpy = Mockito.spy(tabletop);
        List<Robot> robots =new ArrayList<>();
        int robotToActive = -1;
        doReturn(robots).when(tabletopSpy).getRobots();
        Response response = tabletopSpy.activeRobot(robotToActive);
        assertEquals(Response.BAD_COMMAND,response.getCode());
        String expectedMessage = String.format(I18N.ROBOT_NOT_EXIST,robotToActive);
        assertEquals(expectedMessage, response.getMessage());
        Mockito.verify(tabletopSpy, times(0)).getRobots();
    }
    @Test
    public void testActiveRobotNoRobots(){
        Tabletop tabletop =new Tabletop(Constants.MAX_DIMENSION_X, Constants.MAX_DIMENSION_Y);
        Tabletop tabletopSpy = Mockito.spy(tabletop);
        List<Robot> robots =new ArrayList<>();
        int robotToActive = 2;
        doReturn(robots).when(tabletopSpy).getRobots();
        Response response = tabletopSpy.activeRobot(robotToActive);
        assertEquals(Response.BAD_COMMAND,response.getCode());
        String expectedMessage = String.format(I18N.ROBOT_NOT_EXIST,robotToActive);
        assertEquals(expectedMessage, response.getMessage());
        Mockito.verify(tabletopSpy, times(1)).getRobots();
    }
    @Test
    public void testActiveRobotWithCorrectID(){
        Tabletop tabletop =new Tabletop(Constants.MAX_DIMENSION_X, Constants.MAX_DIMENSION_Y);
        Tabletop tabletopSpy = Mockito.spy(tabletop);
        List<Robot> robots =new ArrayList<>();
        Robot robot = new Robot(0, new Position(1,2),Direction.NORTH);
        robots.add(robot);
        int robotToActive = 1;
        doReturn(robots).when(tabletopSpy).getRobots();
        Response response = tabletopSpy.activeRobot(robotToActive);
        assertEquals(Response.SUCCESS_RESPONSE,response.getCode());
        String expectedMessage = String.format(I18N.ROBOT_ACTIVATED,robotToActive);
        assertEquals(expectedMessage, response.getMessage());
        Mockito.verify(tabletopSpy, times(2)).getRobots();
    }
    @Test
    public void testPlaceCommandOutBounds(){
        PlaceCommand placeCommand =new PlaceCommand();
        placeCommand.setPosition(new Position(1,1));
        placeCommand.setDirection(Direction.NORTH);
        Tabletop tabletop =new Tabletop(Constants.MAX_DIMENSION_X, Constants.MAX_DIMENSION_Y);
        Tabletop tabletopSpy = Mockito.spy(tabletop);
        doReturn(false).when(tabletopSpy).checkPointInRange(placeCommand.getPosition());//mock the checkPointInRange, not real result
        int robotSize = tabletopSpy.getRobots().size();
        Robot activatedRobot = tabletopSpy.getActiveRobot();
        Response response = tabletopSpy.placeRobot(placeCommand);
        assertEquals(Response.BAD_COMMAND, response.getCode());
        assertEquals(String.format(I18N.ROBOT_OUT_BOUNDS,
                placeCommand.getPosition().getX(), placeCommand.getPosition().getY(),
                Constants.MAX_DIMENSION_X, Constants.MAX_DIMENSION_Y), response.getMessage());
        assertEquals(robotSize, tabletopSpy.getRobots().size());
        assertEquals(activatedRobot, tabletopSpy.getActiveRobot());
        Mockito.verify(tabletopSpy, times(1)).checkPointInRange(placeCommand.getPosition());
    }
    @Test
    public void testPlaceCommandInBounds(){
        PlaceCommand placeCommand =new PlaceCommand();
        placeCommand.setPosition(new Position(1,1));
        placeCommand.setDirection(Direction.NORTH);
        Tabletop tabletop =new Tabletop(Constants.MAX_DIMENSION_X, Constants.MAX_DIMENSION_Y);
        Tabletop tabletopSpy = Mockito.spy(tabletop);
        doReturn(true).when(tabletopSpy).checkPointInRange(placeCommand.getPosition());//mock the checkPointInRange, not real result
        Response response = tabletopSpy.placeRobot(placeCommand);
        assertEquals(Response.SUCCESS_RESPONSE, response.getCode());
        assertEquals(String.format(I18N.ROBOT_PLACED,
                placeCommand.getPosition().getX(), placeCommand.getPosition().getY(),
                Direction.NORTH, Constants.ROBOT_ID_START_FROM), response.getMessage());
        Mockito.verify(tabletopSpy, times(1)).checkPointInRange(placeCommand.getPosition());
        Robot activatedRobot = tabletopSpy.getActiveRobot();
        assertNotNull(activatedRobot);//first robot default activated
        assertEquals(Constants.ROBOT_ID_START_FROM,activatedRobot.getId());
        assertEquals(placeCommand.getPosition(),activatedRobot.getPosition());
        assertEquals(placeCommand.getDirection(), activatedRobot.getDirection());
        Response response2 = tabletopSpy.placeRobot(placeCommand);
        assertEquals(Response.SUCCESS_RESPONSE, response2.getCode());
        assertEquals(String.format(I18N.ROBOT_PLACED,
                placeCommand.getPosition().getX(), placeCommand.getPosition().getY(),
                Direction.NORTH, Constants.ROBOT_ID_START_FROM + 1), response2.getMessage());
        assertEquals(activatedRobot, tabletopSpy.getActiveRobot());//No change on the activated robot
    }
    @Test
    public void testRotateRobot(){
        //no activated robot
        Tabletop tabletop =new Tabletop(Constants.MAX_DIMENSION_X, Constants.MAX_DIMENSION_Y);
        Tabletop tabletopSpy = Mockito.spy(tabletop);
        doReturn(null).when(tabletopSpy).getActiveRobot();
        Response response = tabletopSpy.rotateRobot(true);
        assertEquals(Response.BAD_COMMAND, response.getCode());
        assertEquals(I18N.NO_ACTIVATED_ROBOT, response.getMessage());
        Mockito.verify(tabletopSpy, times(1)).getActiveRobot();
        //has activated robot
        int id = 1;
        Robot robot = new Robot(id,new Position(1,2),Direction.NORTH);
        doReturn(robot).when(tabletopSpy).getActiveRobot();
        Response response2 = tabletopSpy.rotateRobot(true);
        assertEquals(Response.SUCCESS_RESPONSE, response2.getCode());
        assertEquals(String.format(I18N.ROBOT_ROTATED,id,Direction.NORTH,Direction.EAST), response2.getMessage());
        Mockito.verify(tabletopSpy, times(2)).getActiveRobot();
    }
    @Test
    public void testMoveRobotWithinBoundsNoActivatedRobot(){
        Tabletop tabletop =new Tabletop(Constants.MAX_DIMENSION_X, Constants.MAX_DIMENSION_Y);
        Tabletop tabletopSpy = Mockito.spy(tabletop);
        doReturn(null).when(tabletopSpy).getActiveRobot();
        Response response = tabletopSpy.moveRobotWithinBounds();
        assertEquals(Response.BAD_COMMAND, response.getCode());
        assertEquals(I18N.NO_ACTIVATED_ROBOT, response.getMessage());
        Mockito.verify(tabletopSpy, times(1)).getActiveRobot();
    }
    @Test
    public void testMoveRobotWithinBounds(){
        Tabletop tabletop =new Tabletop(Constants.MAX_DIMENSION_X, Constants.MAX_DIMENSION_Y);
        Tabletop tabletopSpy = Mockito.spy(tabletop);
        Robot robot = Mockito.mock(Robot.class);
        doReturn(robot).when(tabletopSpy).getActiveRobot();
        Position predictPosition = new Position(2,3);
        Position oldPosition = new Position(1,2);
        int robotId = 2;
        when(robot.getId()).thenReturn(robotId);
        when(robot.predictNextPosition()).thenReturn(predictPosition);
        when(robot.move()).thenReturn(oldPosition);
        when(robot.getPosition()).thenReturn(predictPosition);
        doReturn(true).when(tabletopSpy).checkPointInRange(predictPosition);
        Response response = tabletopSpy.moveRobotWithinBounds();
        assertEquals(Response.SUCCESS_RESPONSE, response.getCode());
        assertEquals( String.format(I18N.ROBOT_MOVED,robotId,
                oldPosition.getX(), oldPosition.getY(),
                predictPosition.getX(),predictPosition.getY()), response.getMessage());
        Mockito.verify(tabletopSpy, times(1)).getActiveRobot();
        Mockito.verify(robot, times(1)).predictNextPosition();
        Mockito.verify(robot, times(1)).move();
    }
    @Test
    public void testMoveRobotOutOfBounds(){
        Tabletop tabletop =new Tabletop(Constants.MAX_DIMENSION_X, Constants.MAX_DIMENSION_Y);
        Tabletop tabletopSpy = Mockito.spy(tabletop);
        Robot robot = Mockito.mock(Robot.class);
        doReturn(robot).when(tabletopSpy).getActiveRobot();
        Position predictPosition = new Position(2,3);
        Position oldPosition = new Position(1,2);
        int robotId = 2;
        when(robot.getId()).thenReturn(robotId);
        when(robot.predictNextPosition()).thenReturn(predictPosition);
        when(robot.getDirection()).thenReturn(Direction.EAST);
        when(robot.getPosition()).thenReturn(oldPosition);
        doReturn(false).when(tabletopSpy).checkPointInRange(predictPosition);
        Response response = tabletopSpy.moveRobotWithinBounds();
        assertEquals(Response.BAD_COMMAND, response.getCode());
        assertEquals( String.format(I18N.ROBOT_MOVE_OUT_BOUNDS,robotId,
                oldPosition.getX(), oldPosition.getY(),Direction.EAST
                ), response.getMessage());
        Mockito.verify(tabletopSpy, times(1)).getActiveRobot();
        Mockito.verify(robot, times(1)).predictNextPosition();
        Mockito.verify(robot, times(0)).move();//important, if the next position out of bound should not call move
    }
}
