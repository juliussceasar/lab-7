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
    private ServerSocket server; // �����������
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
                System.out.println("���������� ��������� ��������� �� ����...");
                databaseManager.loadCollectionFromDatabase(collectionManager);
            } catch (DatabaseException e) {
                System.out.println("������ ��� �������� ���������: " + e);
            }


            try {
                port = Integer.parseInt(strPort);
            } catch (NumberFormatException ex) {
                System.out.println("������! ������������ ������ �����.");
                System.exit(0);
            }

            server = new ServerSocket(port);
            System.out.println("������ �������!");
            while (true) {
                Socket clientSocket = server.accept();
                System.out.println("�������������� ������������: " + clientSocket);
                //thread ������������� ������ �������� --> Fixed Thread Pool
                ExecutorService pool = Executors.newFixedThreadPool(2);

                Runnable task1 = () -> {
                    System.out.println("Task is running (a thread)");
                    ObjectInputStream in = null;
                    try {
                        try {
                            while (true) {
                                in = new ObjectInputStream(clientSocket.getInputStream());
                                Object o = in.readObject();
                                translating.translate(o, clientSocket);
                            }

                        } catch (EOFException | SocketException ex) {
                            System.out.println("������������ " + clientSocket + " ������������...");
                            Thread.currentThread().interrupt();
                        } catch (InterruptedException | DatabaseException e) {
                            System.out.println("Something happenned with a thread or database...");
                        } finally {
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
                pool.execute(task1);
            }
        } catch (IOException e) {
            //;
        }
    }
}