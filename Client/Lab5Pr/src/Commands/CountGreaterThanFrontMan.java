package Commands;

import java.io.IOException;
import java.net.Socket;

public class CountGreaterThanFrontMan implements CommandWithoutArg {
    @Override
    public String execute(Object o, Socket socket, String user) throws IOException {
        return null;
    }

    @Override
    public String getName() {
        return "count_greater_than_front_man";
    }
}
