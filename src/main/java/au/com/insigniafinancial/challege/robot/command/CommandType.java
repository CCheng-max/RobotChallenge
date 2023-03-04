package au.com.insigniafinancial.challege.robot.command;

import au.com.insigniafinancial.challege.robot.Utils;

public enum CommandType {
    PLACE,MOVE,REPORT,LEFT,RIGHT,ROBOT,UNKNOWN;
    public static CommandType parseCommand(String value){
        try{
            return CommandType.valueOf(CommandType.class, Utils.trimLeftAndRight(value));
        }catch (Exception e){//unknown type, do nothing.
            return UNKNOWN;
        }
    }
}
