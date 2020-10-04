package Commands;

import Utilities.BandList;
import Utilities.SQLConnector;

import java.io.IOException;
import java.net.Socket;

public class FilterByNumberOfParticipants implements Commandable {
    @Override
    public String execute(Object o, Socket socket, String user) throws IOException {
        int nop;
        SQLConnector.loadAllBands();
        BandList mbList = new BandList();
        try {
            nop = Integer.parseInt((String)o);
        } catch (NumberFormatException ex) {
            return ("Некоректный ввод аргумента,попробуйте ещё раз.");
        }
        String ans = mbList.filterByNumberOfParticipants(nop);
       if (ans.equals("")) return "Таких групп не найдено";
       else return "Следующие группы подходят под условие:"+ans;
    }

    @Override
    public String getName() {
        return "filter_by_number_of_participants";
    }
}
