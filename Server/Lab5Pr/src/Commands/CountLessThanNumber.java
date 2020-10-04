package Commands;

import Utilities.BandList;
import Utilities.SQLConnector;

import java.io.IOException;
import java.net.Socket;

public class CountLessThanNumber implements  Commandable {
    @Override
    public String execute(Object o, Socket socket, String user) throws IOException {
        BandList bandList = new BandList();
        SQLConnector.loadAllBands();
        int nop;
        try {
            nop = Integer.parseInt((String)o);
        } catch (NumberFormatException ex) {
            return ("wrong id");

        }
        return "Групп,где кол-во участников меньше заданного = "+bandList.countLessThanNumberOfParticipants(nop);

    }

    @Override
    public String getName() {
        return "count_less_than_number_of_participants";
    }
}
