package Commands;

import Utilities.BandList;
import Utilities.SQLConnector;

import java.io.IOException;
import java.net.Socket;

public class Show implements CommandWithoutArg {
    @Override
    public String execute(Object o, Socket socket, String user) throws IOException {
        BandList bandList = new BandList();
        SQLConnector.loadAllBands();

        if (bandList.getSize()>0){
            return "\n-----------------------\n"+bandList.show();
        }
        else return "Коллекция пуста.";
    }

    @Override
    public String getName() {
        return "show";
    }
}
