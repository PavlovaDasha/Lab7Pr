package Utilities;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.ConnectException;
import java.net.Socket;
import java.net.SocketException;

public class ClientSender {
    public static Boolean serverisconnected = false;

    public static void send(Object o) throws SocketException, ClassNotFoundException, InterruptedException {
        try {

            ObjectOutputStream objectOutputStream = new ObjectOutputStream(ClientReceiver.socket.getOutputStream());
            objectOutputStream.writeObject(o);

        } catch (IOException e) {
            serverisconnected = false;
            ClientSender.reconnect();
        }
    }

    public static void reconnect() throws InterruptedException, ClassNotFoundException {
        while (!serverisconnected)
            try {
                Socket socket = new Socket("localhost", 1928);
                socket.setSoTimeout(3000);
                serverisconnected = true;
                ClientReceiver.socket = socket;
                socket.setSoTimeout(0);

            } catch (ConnectException e) {
                System.out.println("Возникла некоторая ошибка,пробую переподключится.");
                Thread.sleep(2000);
            } catch (IOException e) {
                e.printStackTrace();
            }
    }
}
