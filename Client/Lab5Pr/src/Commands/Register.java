package Commands;

import java.io.IOException;
import java.net.Socket;

public class Register implements CommandWithoutArg {
    @Override
    public String execute(Object o, Socket socket, String user) throws IOException {
        return null;
    }

    @Override
    public String getName() {
        return "register";
    }
}
