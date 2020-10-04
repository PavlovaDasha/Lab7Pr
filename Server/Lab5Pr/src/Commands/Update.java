package Commands;

import MusicBand.MusicBand;
import Utilities.BandList;
import Utilities.SQLConnector;
import Utilities.ServerReceiver;
import Utilities.ServerSender;

import java.io.IOException;
import java.net.Socket;

public class Update implements Commandable {
    @Override
    public String execute(Object o, Socket socket, String user) throws IOException {
        int id;
        ServerSender serverSender = new ServerSender();
        ServerReceiver serverReceiver = new ServerReceiver();
        SQLConnector.loadAllBands();
        BandList bandList = new BandList();
        try {
            id = Integer.parseInt((String)o);
        } catch (NumberFormatException ex) {
            return ("Некоректный ввод аргумента.");

        }

        MusicBand band = bandList.findById(id);
            if (!band.getUser().equals(user)) return "Группа не принадлежит вам";
        if (band == null) {
            return ("Группы с таким айди не существует.");

        }
        serverSender.send(socket,band,4);
        MusicBand musicBand = (MusicBand) serverReceiver.receive(socket);
        if (musicBand!=null) {
            musicBand.setId(band.getId());
            musicBand.setUser(user);
             bandList.removeById(id,user);
             bandList.add(musicBand,id);
            return ("Группа успешно обновлена");
        } else {
          return ("Что-то пошло не так,группа не обновлена.");
        }
    }

    @Override
    public String getName() {
        return "update";
    }
}
