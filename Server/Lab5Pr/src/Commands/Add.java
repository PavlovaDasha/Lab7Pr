package Commands;

import MusicBand.MusicBand;
import Utilities.*;

import java.io.IOException;
import java.net.Socket;

public class Add implements CommandWithoutArg {
    @Override
    public String execute(Object o, Socket socket, String user) throws IOException {
        ServerSender serverSender = new ServerSender();
        ServerReceiver serverReceiver = new ServerReceiver();
        SQLConnector.loadAllBands();
        BandList bandList = new BandList();
        serverSender.send(socket,"",2);
        Pair<Boolean, MusicBand> res =(Pair<Boolean,MusicBand>) serverReceiver.receive(socket);
        if (res.getKey()) {
            res.getValue().setUser(user);
            res.getValue().setId(SQLConnector.getNewId());
            bandList.add(res.getValue());
            SQLConnector.uploadAllBands();
            return ("Музыкальная группа добавлена в коллекцию!");
        } else {
            return ("Что-то пошло не так,группа не добавлена.");
        }
    }

    @Override
    public String getName() {
        return "add";
    }
}
