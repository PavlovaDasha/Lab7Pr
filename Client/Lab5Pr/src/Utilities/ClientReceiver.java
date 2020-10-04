package Utilities;

import MusicBand.MusicBand;
import MusicBand.Person;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.net.Socket;
import java.net.SocketException;
import java.net.SocketTimeoutException;
import java.util.Map;


public class ClientReceiver {
    public static Socket socket;

    public static Object receive() throws IOException, ClassNotFoundException, SocketTimeoutException, InterruptedException, SocketException {
        ObjectInputStream objectInputStream = new ObjectInputStream(socket.getInputStream());
        Object obj = objectInputStream.readObject();
        Map<Object, Integer> answer = (Map<Object, Integer>) obj;
        if (answer.entrySet().iterator().next().getValue() == 0) {
            return ("Ответ с сервера: " + answer.entrySet().iterator().next().getKey());
        } else if (answer.entrySet().iterator().next().getValue() == 1) {
            System.out.println("Ответ с сервера: " + answer.entrySet().iterator().next().getKey());
            System.out.print("$ ");
            BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
            String s = reader.readLine();
            ClientSender.send(s);
            return ClientReceiver.receive();
        } else if (answer.entrySet().iterator().next().getValue() == 2) {
            Pair<Boolean, MusicBand> res = MusicBand.input();
            ClientSender.send(res);
            return ClientReceiver.receive();
        } else if (answer.entrySet().iterator().next().getValue() == 3) {
            Pair<Boolean, Person> fmp = Person.input("front man");
            ClientSender.send(fmp);
            return ClientReceiver.receive();

        } else if (answer.entrySet().iterator().next().getValue() == 4) {
            MusicBand b = (MusicBand) answer.entrySet().iterator().next().getKey();
            boolean updateRes = b.edit();
            if (updateRes) ClientSender.send(b);
            else ClientSender.send(null);
            return ClientReceiver.receive();
        }
        return null;
    }
}

