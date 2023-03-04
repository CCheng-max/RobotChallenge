package au.com.insigniafinancial.challege.robot.simulation;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Response {
    public static final int SUCCESS_RESPONSE = 200;
    public static final int BAD_COMMAND= 400;
    private int code;
    private String message;
}
