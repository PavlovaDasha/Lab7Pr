import Commands.Commandable;
import Utilities.*;

import java.io.IOException;
import java.net.Socket;
import java.sql.SQLException;
import java.util.Map;
import java.util.Scanner;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ForkJoinPool;

/**
 * класс для исполнения программы
 */
public class Main {
    /**
     * метод для исполнения программы
     *
     * @param args входные значения
     */
    public static void main(String[] args) throws IOException, SQLException {
        IoHelper.in = new Scanner(System.in);
        CreateServer.create();
        try {
            SQLConnector.ConnectionToDB();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        ForkJoinPool forkJoinPool = new ForkJoinPool();
        while (!CreateServer.server.isClosed()) {
            Socket socket = CreateServer.server.accept();
            forkJoinPool.execute(new NewUser(socket));
            System.out.println("Новое подключение: "+socket.getLocalAddress()+socket.getPort());

        }
    }

}