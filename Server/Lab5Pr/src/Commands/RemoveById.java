package Commands;

import Utilities.BandList;
import Utilities.SQLConnector;

import java.io.IOException;
import java.net.Socket;

public class RemoveById implements  Commandable {
    @Override
    public String execute(Object o, Socket socket, String user) throws IOException {
        BandList mbList = new BandList();
        SQLConnector.loadAllBands();
        int id;
        try {
            id = Integer.parseInt((String)o);
        } catch (NumberFormatException ex) {
           return ("wrong id parameter");

        }
        String a =  mbList.removeById(id,user);
        SQLConnector.uploadAllBands();
        return a;
    }

    @Override
    public String getName() {
        return "remove";
    }
}
