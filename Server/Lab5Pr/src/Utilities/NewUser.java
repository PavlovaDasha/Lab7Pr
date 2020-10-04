package Utilities;

import Commands.Commandable;

import java.io.IOException;
import java.net.Socket;
import java.util.Map;

public class NewUser implements Runnable {
    private Socket clientSocket;
    private String newuser = "";
    public NewUser(Socket client) {
        this.clientSocket = client;
    }

    @Override
    public void run() {
        SQLConnector sqlConnector = new SQLConnector();
        ServerSender serverSender = new ServerSender();
        ServerReceiver serverReceiver = new ServerReceiver();
        try {
            Map<Commandable, String> commandStringMap;
            while (newuser.equals("")) {
                Object o = serverReceiver.receive(clientSocket);
                commandStringMap = (Map<Commandable, String>) o;
                System.out.println("Выполняю команду регистрации/логгирования от клиента с адресом: " + clientSocket.getLocalAddress() + clientSocket.getPort());
                String result = commandStringMap.entrySet().iterator().next().getKey().execute(commandStringMap.entrySet().iterator().next().getValue(), clientSocket, null);
                if (result.equals("false"))
                    serverSender.send(clientSocket, "Не удалось выполнить авторизацию,попробуйте ещё раз", 0);
                else {
                    serverSender.send(clientSocket, "true", 0);
                    newuser = result;
                }

            }

            while (true) {

                Object o = serverReceiver.receive(clientSocket);
                commandStringMap = (Map<Commandable, String>) o;
                System.out.println("Выполняю команду " + commandStringMap.entrySet().iterator().next().getKey().getClass().getName() + " от <username:" + newuser+"> с адресом: " + clientSocket.getLocalAddress() + clientSocket.getPort());
                serverSender.send(clientSocket, commandStringMap.entrySet().iterator().next().getKey().execute(commandStringMap.entrySet().iterator().next().getValue(), clientSocket, newuser), 0);
            }
        } catch (IOException | NullPointerException e) {
            System.out.println("Клиент с адресом:" + clientSocket.getLocalAddress() + clientSocket.getPort() + " отключился");
        }
    }
}

