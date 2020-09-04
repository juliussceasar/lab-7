package ServerSocket;

import Collection.CollectionManager;
import Exceptions.DatabaseException;
import Utils.CommandHandler.Translating;
import Utils.Database.DatabaseManager;

import java.io.EOFException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Controller {
    private ServerSocket server; // серверсокет
    private final CollectionManager collectionManager;
    private final DatabaseManager databaseManager;
    private final Translating translating;

    public Controller(CollectionManager collectionManager, DatabaseManager databaseManager, Translating translate) {
        this.collectionManager = collectionManager;
        this.databaseManager = databaseManager;
        this.translating = translate;
    }

    public void run(String strPort) {
        try {
            int port = 0;
            collectionManager.initList();
            try {
                System.out.println("Попытаемся загрузить коллекцию из базы...");
                databaseManager.loadCollectionFromDatabase(collectionManager);
            } catch (DatabaseException e) {
                System.out.println("Ошибка при выгрузке коллекции: " + e);
            }


            try {
                port = Integer.parseInt(strPort);
            } catch (NumberFormatException ex) {
                System.out.println("Ошибка! Неправильный формат порта.");
                System.exit(0);
            }

            server = new ServerSocket(port);
            System.out.println("Сервер запущен!");
            ExecutorService pool = Executors.newFixedThreadPool(2);
            while (true) {
                Socket clientSocket = server.accept();
                System.out.println("Присоединяется пользователь: " + clientSocket);
                //thread многопоточное чтение запросов --> Fixed Thread Pool

                Runnable task = () -> {
                    System.out.println("A thread is up and running.");
                    ObjectInputStream in = null;
                    try {
                        try {
                            while (true) {
                                in = new ObjectInputStream(clientSocket.getInputStream());
                                Object o = in.readObject();
                                System.out.println(o);
                                translating.translate(o, clientSocket);
                            }

                        } catch (EOFException | SocketException ex) {
                            System.out.println("Пользователь " + clientSocket + " отсоединился...");
                            Thread.currentThread().interrupt();
                        } catch (InterruptedException | DatabaseException e) {
                            System.out.println("Something happenned with a thread or database...");
                        } finally {
                            System.out.println("Закрываем тредик. И сервер.");
                            Thread.currentThread().interrupt();
                            clientSocket.close();
                            if (in != null) {
                                in.close();
                            }
                        }
                    } catch (IOException | ClassNotFoundException ex) {
                        System.out.println("Something is wrong with streams.");
                    }
                };
                pool.submit(task);
            }
        } catch (IOException e) {
            //;
        }
    }
}