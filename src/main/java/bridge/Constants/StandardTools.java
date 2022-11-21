package bridge.Constants;

public class StandardTools {

    public static final String NULL = "";
    public static final String ONLY_NUMBER = "^[0-9]*$";
    public static final int UP_SIDE = 1;
    public static final String BRIDGE_HEAD = "[";
    public static final boolean FAILED = false;
    public static final boolean SUCCEED = true;
    public static final boolean POSSIBLE_ZONE = true;
    public static final boolean IMPOSSIBLE_ZONE = false;

    public enum retry {
        RETRY, QUIT
    }
}
