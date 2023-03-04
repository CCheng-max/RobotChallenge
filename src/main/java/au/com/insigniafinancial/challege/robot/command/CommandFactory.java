package au.com.insigniafinancial.challege.robot.command;

import au.com.insigniafinancial.challege.robot.Utils;
import au.com.insigniafinancial.challege.robot.simulation.Direction;
import au.com.insigniafinancial.challege.robot.simulation.Position;
public class CommandFactory {
    public Command buildCommand(String input){
        if(Utils.isBlank(input)){
            return null;
        }
        input = Utils.trimLeftAndRight(input);
        String commandTypeInput = input.contains(" ")? input.split(" ")[0]:input;
        if(Utils.isBlank(commandTypeInput)){
            return null;
        }

        CommandType commandType = CommandType.parseCommand(commandTypeInput.toUpperCase());
        return switch (commandType){
            case PLACE -> buildPlaceCommand(input);
            case LEFT,RIGHT,REPORT,MOVE-> new Command(commandType);
            case ROBOT -> buildRobotCommand(input);
            default ->null;
        };
    }
    PlaceCommand buildPlaceCommand(String input){
        PlaceCommand placeCommand = null;
        String parameters = input.replaceAll("^(?i)"+CommandType.PLACE,"").replaceAll("\\s","");
        String[] positionAndDirection = parameters.split(",");
        if(positionAndDirection.length == 3){
            try{
                placeCommand =new PlaceCommand(new Position(Integer.parseInt(positionAndDirection[0].trim()),Integer.parseInt(positionAndDirection[1].trim())),
                        Direction.parseByName(positionAndDirection[2]));
            }catch (IllegalArgumentException e){//illegal parameter //do nothing
            }
        }
        return placeCommand;
    }
    RobotCommand buildRobotCommand(String input){
        String parameters = input.replaceAll("^(?i)"+CommandType.ROBOT,"").replaceAll("\\s","");
        RobotCommand robotCommand =null;
        try{
            robotCommand = new RobotCommand(Integer.parseInt(parameters));
        }catch (NumberFormatException e){//illegal parameter //do nothing
        }
        return robotCommand;
    }
}
