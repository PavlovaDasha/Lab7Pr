import Commands.*;
import Utilities.ClientReceiver;
import Utilities.ClientSender;
import Utilities.IoHelper;

import java.io.IOException;
import java.net.SocketException;
import java.net.SocketTimeoutException;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Scanner;

/**
 * класс для исполнения программы
 */
public class Main {
    /**
     * метод для исполнения программы
     *
     * @param args входные значения
     */
    public static void main(String[] args)  {
        Commands commands = new Commands();
    Login login = new Login();
    Register register = new Register();
        commands.regist(login,register,new Show(), new Exit(), new Help(), new Info(), new Clear(), new RemoveById(),
                new ExecuteScript(), new Add(), new Sort(), new Update(), new AddIfMax(),
                new CountGreaterThanFrontMan(), new CountLessThanNumber(), new FilterByNumberOfParticipants(), new RemoveAt(), new Sort());
        IoHelper ioHelper = new IoHelper();
        IoHelper.in = new Scanner(System.in);


        System.out.println("Клиент запущен, подключась к серверу.");
        while (true) {
            while (!Commands.isLogged){
                try {
                    ClientSender.reconnect();
                    System.out.println("Введите login,чтоб авторизоваться | Введите register,чтоб зарегистрироваться | Доступ к другим командам ограничен.");
                    System.out.print("$ ");
                    String s = IoHelper.in.nextLine();
                    if (s.equals(""));
                    else {
                        Map<Commandable, String> commandparamMap = commands.executeCommand(s);
                        if (commandparamMap != null) {
                        if (commandparamMap.entrySet().iterator().next().getKey().getName().equals(login.getName()) || commandparamMap.entrySet().iterator().next().getKey().getName().equals(register.getName())) {

                                ClientSender.send(commandparamMap);
                                try {
                                    String a = (String) ClientReceiver.receive();
                                    if (a.equals("Ответ с сервера: true")) {
                                        System.out.println("Вы успешно авторизованы.");
                                        Commands.isLogged = true;
                                    }else System.out.println(a);
                                } catch (SocketTimeoutException e) {
                                    System.out.println("Сервер не отвечает или занят,попробуйте ещё раз и убедитесь,что сервер работает.");
                                } catch (IOException e) {
                                    e.printStackTrace();
                                }
                            }
                        } else System.out.println("Другие команды запрещены,авторизуйтесь.");
                    }
                } catch (InterruptedException | ClassNotFoundException | SocketException e) {

                }

            }
            while (ClientSender.serverisconnected) {
                try {
                    System.out.println("Введите команду для отправки на сервер: ");
                    System.out.print("$ ");
                    String s = IoHelper.in.nextLine();
                    Map<Commandable, String> commandparamMap = commands.executeCommand(s);
                    if (commandparamMap != null) {
                        ClientSender.send(commandparamMap);
                        try {
                            System.out.println(ClientReceiver.receive());
                        } catch (SocketTimeoutException e) {
                            System.out.println("Сервер не отвечает или занят,попробуйте ещё раз и убедитесь,что сервер работает.");
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                } catch (NoSuchElementException | ClassNotFoundException | InterruptedException | SocketException e) {
                    System.out.println("Программа прервана пользователем.");
                    System.exit(0);
                }
            }
        }
    }
}
