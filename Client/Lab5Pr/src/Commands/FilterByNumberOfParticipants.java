package Commands;

import java.io.IOException;
import java.net.Socket;

public class FilterByNumberOfParticipants implements Commandable {
    @Override
    public String execute(Object o, Socket socket, String user) throws IOException {
        return null;
    }

    @Override
    public String getName() {
        return "filter_by_number_of_participants";
    }
}
