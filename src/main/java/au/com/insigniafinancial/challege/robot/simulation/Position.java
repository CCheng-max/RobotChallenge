package au.com.insigniafinancial.challege.robot.simulation;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode()
public class Position {
    private int x;
    private int y;
    public String toString(){
        return x +"," + y;
    }

}
