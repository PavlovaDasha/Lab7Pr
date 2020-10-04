package Commands;

import Utilities.BandList;
import Utilities.SQLConnector;

import java.io.IOException;
import java.net.Socket;

public class Sort implements CommandWithoutArg {
    @Override
    public String execute(Object o, Socket socket, String user) throws IOException {
        BandList bandList = new BandList();
        SQLConnector.loadAllBands();
        if (bandList.getSize()>0){
            bandList.sort();
            SQLConnector.uploadAllBands();
            return "Коллекция успешно отсортирована.";
        }
        else return "Коллекция пуста.";

    }

    @Override
    public String getName() {
        return "sort";
    }
}
