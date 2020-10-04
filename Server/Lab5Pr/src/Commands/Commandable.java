/**
 * Interface for working with commands which have an argument
 *
 * @author SaintLaurentPrince and Mariec
 * @version 1.0
 */

package Commands;

import java.io.IOException;
import java.io.Serializable;
import java.net.Socket;

public interface Commandable extends Serializable {
    String execute(Object o, Socket socket,String user) throws IOException;
    String getName();
}
