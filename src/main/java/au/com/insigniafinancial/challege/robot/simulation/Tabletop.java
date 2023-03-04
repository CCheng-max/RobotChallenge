package au.com.insigniafinancial.challege.robot.simulation;

import au.com.insigniafinancial.challege.robot.Constants;
import au.com.insigniafinancial.challege.robot.I18N;
import au.com.insigniafinancial.challege.robot.command.Command;
import au.com.insigniafinancial.challege.robot.command.PlaceCommand;
import au.com.insigniafinancial.challege.robot.command.RobotCommand;

import java.util.ArrayList;
import java.util.List;

public class Tabletop {
    private List<Robot> robots = new ArrayList<>();
    private Robot activeRobot;
    private final int maxDimensionX;
    private final int maxDimensionY;

    public Tabletop(int maxDimensionX, int dimensionY) {
        this.maxDimensionX = maxDimensionX;
        this.maxDimensionY = dimensionY;
    }

    public Response executeCommand(Command command) {
        if (command == null) {
            return new Response();
        }
        return switch (command.getCommandType()) {
            case PLACE -> placeRobot(command);
            case LEFT -> rotateRobot(false);
            case RIGHT -> rotateRobot(true);
            case MOVE -> moveRobotWithinBounds();
            case ROBOT -> activeRobot(((RobotCommand) command).getActiveRobot());
            case REPORT -> report();
            default -> null;
        };
    }

    Response report() {
        return new Response(Response.SUCCESS_RESPONSE,
                String.format(I18N.REPORT, getRobots().size(), getActiveRobot() == null?"":getActiveRobot().toString()));
    }

    Response activeRobot(int activeRobot) {
        if ( activeRobot >= Constants.ROBOT_ID_START_FROM && activeRobot <= this.getRobots().size()) {
            this.activeRobot = getRobots().get(activeRobot - Constants.ROBOT_ID_START_FROM );
        } else {//no robot with id activeRobot
            return new Response(Response.BAD_COMMAND,
                    String.format(I18N.ROBOT_NOT_EXIST, String.valueOf(activeRobot)));
        }
        return new Response(Response.SUCCESS_RESPONSE,
                String.format(I18N.ROBOT_ACTIVATED, String.valueOf(activeRobot)));
    }

    Response placeRobot(Command command) {
        PlaceCommand placeCommand = (PlaceCommand) command;
        if (checkPointInRange(placeCommand.getPosition())) {
            //robot ID starts from 1
            Robot robot = new Robot(getRobots().size() + Constants.ROBOT_ID_START_FROM, placeCommand.getPosition(), placeCommand.getDirection());
            this.getRobots().add(robot);
            if(robot.getId() == Constants.ROBOT_ID_START_FROM){
                activeRobot = robot;
            }
            return new Response(Response.SUCCESS_RESPONSE,
                    String.format(I18N.ROBOT_PLACED, robot.getPosition().getX(), robot.getPosition().getY(), robot.getDirection(), robot.getId()));
        } else {
            return new Response(Response.BAD_COMMAND,
                    String.format(I18N.ROBOT_OUT_BOUNDS, placeCommand.getPosition().getX(), placeCommand.getPosition().getY(), maxDimensionX, maxDimensionY));
        }

    }

    Response rotateRobot(boolean clockWise) {
        Robot currentRobot = getActiveRobot();
        if (currentRobot == null) {
            return new Response(Response.BAD_COMMAND, I18N.NO_ACTIVATED_ROBOT);
        }
        Direction direction =currentRobot.rotate(clockWise);
        return new Response(Response.SUCCESS_RESPONSE,
                String.format(I18N.ROBOT_ROTATED, currentRobot.getId(), direction, currentRobot.getDirection()));
    }

    Response moveRobotWithinBounds() {
        Robot currentRobot = getActiveRobot();
        if (currentRobot == null) {
            return new Response(Response.BAD_COMMAND, I18N.NO_ACTIVATED_ROBOT);
        }
        Position predictPosition = currentRobot.predictNextPosition();
        if(checkPointInRange(predictPosition)){
            Position oldPosition = currentRobot.move();
            return new Response(Response.SUCCESS_RESPONSE,
                    String.format(I18N.ROBOT_MOVED,currentRobot.getId(),
                            oldPosition.getX(), oldPosition.getY(),
                            currentRobot.getPosition().getX(), currentRobot.getPosition().getY()));
        }else {
            return  new Response(Response.BAD_COMMAND,
                    String.format(I18N.ROBOT_MOVE_OUT_BOUNDS,currentRobot.getId(),
                            currentRobot.getPosition().getX(), currentRobot.getPosition().getY(),currentRobot.getDirection()));
        }
    }

    boolean checkPointInRange(Position position) {
        if (position.getX() < 0 || position.getX() >= maxDimensionX) {
            return false;
        }
        if (position.getY() < 0 || position.getY() >= maxDimensionY) {
            return false;
        }
        return true;
    }

    public List<Robot> getRobots() {
        return robots;
    }

    public void setRobots(List<Robot> robots) {
        this.robots = robots;
    }

    public Robot getActiveRobot() {
        return activeRobot;
    }

    public void setActiveRobot(Robot activeRobot) {
        this.activeRobot = activeRobot;
    }
}
