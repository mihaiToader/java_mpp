package Services;

/**
 * Created by IntelliJ IDEA.
 * User: grigo
 * Date: Mar 18, 2009
 * Time: 1:32:58 PM
 */
public class LabException extends Exception{
    public LabException() {
    }

    public LabException(String message) {
        super(message);
    }

    public LabException(String message, Throwable cause) {
        super(message, cause);
    }
}
