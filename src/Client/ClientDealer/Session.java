package ClientDealer;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.SocketException;
import java.nio.channels.SocketChannel;
import java.util.NoSuchElementException;
import java.util.Scanner;

public class Session {
    private SocketChannel socketChannel;
    private String hostName;
    private int port;

    public Session() {
        FileReader fileReader= null;
        try {
            fileReader = new FileReader(System.getProperty("user.dir") + "/config.txt");
            Scanner scan = new Scanner(fileReader);
            String[] data = scan.nextLine().split(" ");
            hostName = data[0];
            port = Integer.parseInt(data[1]);
            fileReader.close();
        } catch (FileNotFoundException e) {
            System.out.println("Не найден файл config.txt, создайте его в директории " +
                    System.getProperty("user.dir") +" и напишите туда хост и порт через пробел");
            System.exit(0);
        } catch (IOException e) {
            System.out.println("Ошибка при чтении из конфигурационного файла: " + e);
            System.exit(0);
        } catch (NumberFormatException ex) {
            System.out.println("порт должен быть целым числом");
            System.exit(0);
        } catch (NoSuchElementException e){
            System.out.println("Напишите в файл config.txt хост и порт через пробел");
            System.exit(0);
        }


        for (int i = 0; true; i++){
            try {
                InetSocketAddress inetSocketAddress = new InetSocketAddress(hostName, port);
                socketChannel = SocketChannel.open(inetSocketAddress);
                socketChannel.configureBlocking(false);

                System.out.println(String.format("Подключение к удаленному адресу %s по порту %d", hostName, port));
                break;
            } catch (SocketException ex) {
                System.out.println("Не удалось подключиться к удаленному адресу...");
                if (i == 2) System.exit(0);
                System.out.println("Попробую снова");
                try {
                    Thread.sleep(2000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public void closeSession() throws IOException {
        if (socketChannel != null) { socketChannel.close(); }
    }

    public SocketChannel getSocketChannel() {
        return socketChannel;
    }
}