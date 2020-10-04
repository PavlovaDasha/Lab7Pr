package Commands;

import java.io.IOException;
import java.net.Socket;

public class Exit implements CommandWithoutArg {
    @Override
    public String execute(Object o, Socket socket, String user) throws IOException {
        System.out.println("Один из клиентов отключился.");
        return null;

    }

    @Override
    public String getName() {
        return "exit";
    }
}
