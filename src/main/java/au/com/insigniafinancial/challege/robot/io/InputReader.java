package au.com.insigniafinancial.challege.robot.io;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class InputReader {
    BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

    public String prompAndRead(String message) {
        try {
            System.out.println(message);
            return br.readLine();
        } catch (IOException ioe) {
            System.out.println(ioe);
        }
        return null;
    }
}
