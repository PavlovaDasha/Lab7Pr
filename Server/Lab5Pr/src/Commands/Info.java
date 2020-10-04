package Commands;

import Utilities.BandList;
import Utilities.SQLConnector;

import java.io.IOException;
import java.net.Socket;

public class Info implements CommandWithoutArg {
    @Override
    public String execute(Object o, Socket socket, String user) throws IOException {
        SQLConnector.loadAllBands();
        return new BandList().info();
    }

    @Override
    public String getName() {
        return "info";
    }
}
