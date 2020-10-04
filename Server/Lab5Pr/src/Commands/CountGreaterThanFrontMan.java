package Commands;

import MusicBand.MusicBand;
import MusicBand.Person;
import Utilities.*;

import java.io.IOException;
import java.net.Socket;

public class CountGreaterThanFrontMan implements CommandWithoutArg {
    @Override
    public String execute(Object o, Socket socket, String user) throws IOException {
        BandList bandList = new BandList();
        ServerSender serverSender = new ServerSender();
        ServerReceiver serverReceiver = new ServerReceiver();
        SQLConnector.loadAllBands();
        serverSender.send(socket,"",3);
        Pair<Boolean, Person> fmp =  (Pair<Boolean, Person>) serverReceiver.receive(socket);
        if (!fmp.getKey() || fmp.getValue() == null) {
            return ("Что-то пошло не так,попробуйте ещё раз.");
        }
        Person frontMan = fmp.getValue();
        return "Количество групп,значение поля frontMan которых больше заданного ="+ bandList.countGreaterThanFrontMan(frontMan);
    }

    @Override
    public String getName() {
        return "count_greater_than_front_man";
    }
}
