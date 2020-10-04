package Commands;

import MusicBand.MusicBand;
import Utilities.*;

import java.io.IOException;
import java.net.Socket;

public class AddIfMax implements Commandable {
    @Override
    public String execute(Object o, Socket socket, String user) throws IOException {
        BandList bandList = new BandList();
        ServerSender serverSender = new ServerSender();
        ServerReceiver serverReceiver = new ServerReceiver();
        serverSender.send(socket,"",2);
        Pair<Boolean, MusicBand> res =(Pair<Boolean,MusicBand>) serverReceiver.receive(socket);
        if (res.getKey()) {
            res.getValue().setUser(user);
            res.getValue().setId(SQLConnector.getNewId());

            if (bandList.addIfMax(res.getValue())) {
                SQLConnector.uploadAllBands();
                return ("Музыкальная группа добавлена в коллекцию!");
            }
            else
                return ("Музыкальная группа меньше чем максимальная,группа не добавлена.");
        } else {
            return ("Что-то пошло не так,группа не добавлена.");
        }
    }

    @Override
    public String getName() {
        return "add_if_max";
    }
}
