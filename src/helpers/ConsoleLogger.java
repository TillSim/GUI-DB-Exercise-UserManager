package helpers;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * <b>ConsoleLogger</b> is a <i>helper</i> class for printing log messages with timestamp to stream and/or file.
 */
public abstract class ConsoleLogger {

    private static final SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm:ss");


    /**
     * adds timestamp(<i>HH:mm:ss</i>) to String
     * @param logText String
     * @return String
     */
    private static String log(String logText) {
        Date date = new Date();
        String timestamp = dateFormat.format(date.getTime());
        return "[" + timestamp + "]\t" + logText;

    }

    /**
     * prints log message with timestamp(<i>HH:mm:ss</i>) to <b>System.out</b> stream
     * @param logText String
     */
    public static void printLog(String logText) {System.out.println(log(logText));}

    /**
     * prints log message with timestamp(<i>HH:mm:ss</i>) to <b>file</b>
     * @param logText String
     * @param file String
     */
    public static void printLog(String logText, String file) {FileHandler.write(file, log(logText) + "\n", true);}

    /**
     * prints log message with timestamp(<i>HH:mm:ss</i>) to <b>System.out</b> stream and <b>file</b>
     * @param logText String
     * @param file String
     * @param toStream boolean
     */
    public static void printLog(String logText, String file, boolean toStream) {
        printLog(logText, file);
        if(toStream) {printLog(logText);}
    }

}
