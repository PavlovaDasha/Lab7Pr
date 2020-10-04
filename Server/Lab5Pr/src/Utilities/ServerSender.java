package Utilities;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;

import java.net.Socket;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ForkJoinPool;

public class ServerSender {
    ForkJoinPool forkJoinPool = new ForkJoinPool();

    public void send(Socket client, Object message, Integer needAnswer) {
        forkJoinPool.execute(new Sender(client, message, needAnswer));
        System.out.println("Отправляю данные клиенту: " + client.getLocalAddress() + client.getPort());
    }

    public class Sender implements Runnable {
        private Socket client;
        private Object message;
        private Integer needAnswer;

        public Sender(Socket client, Object message, Integer needAnswer) {
            this.client = client;
            this.message = message;
            this.needAnswer = needAnswer;

        }

        @Override
        public void run() {

            Map<Object, Integer> answer = new HashMap<>();
            answer.put(message, needAnswer);
            Object o = answer;
            try {
                ObjectOutputStream objectOutputStream = new ObjectOutputStream(client.getOutputStream());
                objectOutputStream.writeObject(o);
                answer.clear();

            } catch (IOException e) {
            }
        }
    }
}