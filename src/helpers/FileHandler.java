package helpers;

import java.io.IOException;
import java.lang.reflect.Type;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;


/**
 * <b>FileHandler</b> is a <i>helper</i> class for reading/writing/deleting files.
 * By utilizing <i>Gson</i> it also provides <i>.json</i> file handling.
 */
public abstract class FileHandler {

    /**
     * writes <i>String</i> to <i>file</i>
     * @param file String
     * @param input String
     */
    public static void write(String file, String input) {

        try {
            Files.write(Path.of(file), input.getBytes());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * appends <i>String</i> to <i>file</i>
     * @param file String
     * @param input String
     * @param append boolean
     */
    public static void write(String file, String input, boolean append) {

        if(append && Files.exists(Path.of(file))) {
            try {
                Files.write(Path.of(file), input.getBytes(), StandardOpenOption.APPEND);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }else{write(file, input);}

    }

    /**
     * reads <i>String</i> from <i>file</i>
     * @param file String
     * @return String
     * @throws Exception invalid file path
     */
    public static String read(String file) throws Exception {

        if(file == null || file.length() < 3 || !file.contains(".")) throw new Exception("Invalid file path");
        String content = "";
        try {
            Path path = Path.of(file);
            if(Files.exists(path)) {
                content = new String(Files.readAllBytes(path));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return content;

    }

    /**
     * deletes <i>file</i>
     * @param file String
     * @throws Exception file not existent
     */
    public static void delete(String file) throws Exception{

        if(Files.exists(Path.of(file))) {
            Files.delete(Path.of(file));
        } else {throw new Exception("File does not exsist");}

    }

}

