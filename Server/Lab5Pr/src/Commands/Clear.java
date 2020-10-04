package Commands;

import MusicBand.MusicBand;
import Utilities.BandList;
import Utilities.SQLConnector;

import java.io.IOException;
import java.net.Socket;
import java.util.ArrayList;

public class Clear implements CommandWithoutArg {
    @Override
    public String execute(Object o, Socket socket, String user) throws IOException {
        BandList bandList = new BandList();
        SQLConnector.loadAllBands();
        ArrayList <MusicBand> arraytosave = new ArrayList<MusicBand>();
        if (bandList.getSize()>0) {
            BandList.getMusicBandList().stream().filter(x->!x.getUser().equals(user)).forEach(arraytosave::add);
            BandList.setMusicBandList(arraytosave);
            SQLConnector.uploadAllBands();
            return "Коллекция очищена от ваших элементов.";
        }
        else return "Коллекция и так пустая.";
    }

    @Override
    public String getName() {
        return "clear";
    }
}
