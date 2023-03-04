package au.com.insigniafinancial.challege.robot;

import au.com.insigniafinancial.challege.robot.command.Command;
import au.com.insigniafinancial.challege.robot.command.CommandFactory;
import au.com.insigniafinancial.challege.robot.io.InputReader;
import au.com.insigniafinancial.challege.robot.simulation.Response;
import au.com.insigniafinancial.challege.robot.simulation.Tabletop;

public class Application {
    public static void main(String[] args) {
        InputReader inputReader = new InputReader();
        String input = null;
        CommandFactory commandFactory =new CommandFactory();
        Tabletop tabletop = new Tabletop(Constants.MAX_DIMENSION_X, Constants.MAX_DIMENSION_X);
        while(!Constants.EXIT_SIGNAL.equalsIgnoreCase(input)){
            input = inputReader.prompAndRead(String.format(I18N.PROMPT_MESSAGE, Constants.EXIT_SIGNAL));
            Command command = commandFactory.buildCommand(input);
            if(command!=null){
                Response response = tabletop.executeCommand(command);
                System.out.println(response.getMessage());
            }else{
                System.out.println(String.format(I18N.UNKNOWN_COMMAND, input));
            }
        }
    }
}
