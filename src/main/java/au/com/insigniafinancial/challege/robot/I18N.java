package au.com.insigniafinancial.challege.robot;

public class I18N {
    public static final String PROMPT_MESSAGE = "Please type a command or say %s:";
    public static final String UNKNOWN_COMMAND = "Unknown Command or insufficient command and parameters, original input: (%s).";
    public static final String ROBOT_PLACED = "New robot has been placed to %s, %s, with direction %s, id '%s'.";
    public static final String ROBOT_ACTIVATED = "Activated the robot '%s'.";
    public static final String ROBOT_MOVED = "The robot (%s) has been moved from (%s,%s) to (%s,%s).";
    public static final String ROBOT_MOVE_OUT_BOUNDS = "Did NOT move the robot (%s) at (%s, %s) with direction %s because it will out of bounds.";
    public static final String ROBOT_NOT_EXIST = "The robot '%s' that you want to active does NOT exist.";
    public static final String ROBOT_OUT_BOUNDS = "The position (%s,%s) you indicated is out the bounds (0 - %s, 0 - %s).";
    public static final String NO_ACTIVATED_ROBOT = "There is no robot placed yet.";
    public static final String ROBOT_ROTATED = "The robot %s has rotated from %s to %s.";
    public static final String REPORT = "Output: present robots: %s, active robot: %s";
}
