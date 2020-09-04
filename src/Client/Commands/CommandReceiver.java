package Commands;

import BasicClasses.HumanBeing;
import ClientDealer.Receiver;
import ClientDealer.Sender;
import ClientDealer.Session;
import Commands.SerializedCommands.SerializedArgumentCommand;
import Commands.SerializedCommands.SerializedCombinedCommand;
import Commands.SerializedCommands.SerializedObjectCommand;
import Commands.Utils.Creators.ElementCreator;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.PortUnreachableException;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;
import java.util.ArrayList;
import java.util.Iterator;

public class CommandReceiver { // Commands' controller в нем лежат изначально логин и пароль
    private final CommandInvoker commandInvoker;
    private final Sender sender;
    private final SocketChannel socketChannel;
    private final Receiver receiver;
    private final Selector selector;
    private String login;
    private String password;
    public CommandReceiver(CommandInvoker commandInvoker, Session session,
                              Sender sender, Receiver receiver) throws IOException {
        this.commandInvoker = commandInvoker;
        socketChannel = session.getSocketChannel();
        this.sender = sender;
        this.receiver = receiver;
        selector = Selector.open();
    }

    public void setAuthorizationData(String login, String password){
        this.login = login;
        this.password = password;
    }

    public void help() {
        commandInvoker.getCommandMap().forEach((name, command) -> System.out.println(command.writeInfo()));
        System.out.println("_________________________________________________________");
    }

    public void info() throws ClassNotFoundException {
        if (login.equals("") || password.equals("")) {
            System.out.println("Вы не авторизированы");
            return;
        }
        requestHandler(new SerializedCommand(commandInvoker.getCommandMap().get("info"), login, password));
    }

    public void show() throws ClassNotFoundException{
        if (login.equals("") || password.equals("")) {
            System.out.println("Вы не авторизированы");
            return;
        }
        requestHandler(new SerializedCommand(commandInvoker.getCommandMap().get("show"), login, password));
    }

    public void add() throws ClassNotFoundException {
        if (login.equals("") || password.equals("")) {
            System.out.println("Вы не авторизированы");
            return;
        }
        requestHandler(new SerializedObjectCommand(commandInvoker.getCommandMap().get("add"),
                ElementCreator.createHumanBeing(), login, password));
    }

    public void update(String ID) throws  ClassNotFoundException {
        if (login.equals("") || password.equals("")) {
            System.out.println("Вы не авторизированы");
            return;
        }
        requestHandler(new SerializedCombinedCommand(commandInvoker.getCommandMap().get("update"),
                ElementCreator.createHumanBeing(), ID, login, password));
    }

    public void remove_by_id(String ID) throws  ClassNotFoundException {
        if (login.equals("") || password.equals("")) {
            System.out.println("Вы не авторизированы");
            return;
        }
        requestHandler(new SerializedArgumentCommand(commandInvoker.getCommandMap().get("remove_by_id"), ID, login, password));
    }

    public void clear() throws ClassNotFoundException {
        if (login.equals("") || password.equals("")) {
            System.out.println("Вы не авторизированы");
            return;
        }
        requestHandler(new SerializedCommand(commandInvoker.getCommandMap().get("clear"), login, password));
    }

    public void exit() throws IOException {
        socketChannel.close();
        System.out.println("Завершение работы клиента.");
        System.exit(0);
    }


    public void remove_greater() throws  ClassNotFoundException {
        if (login.equals("") || password.equals("")) {
            System.out.println("Вы не авторизированы");
            return;
        }
        requestHandler(new SerializedObjectCommand(commandInvoker.getCommandMap().get("remove_greater"),
                ElementCreator.createHumanBeing(), login, password));
    }

    public void maxByCreationDate() throws ClassNotFoundException {
        if (login.equals("") || password.equals("")) {
            System.out.println("Вы не авторизированы");
            return;
        }
        requestHandler(new SerializedCommand(commandInvoker.getCommandMap().get("max_by_creation_date"), login, password));
    }

    public void register(String login, String password) throws ClassNotFoundException {
        requestHandler(new SerializedCommand(commandInvoker.getCommandMap().get("register"), login, password));
    }

    public void executeScript(String path) {
        String line;
        String command;
        ArrayList<String> parameters = new ArrayList<>();
        try (BufferedReader bufferedReader = new BufferedReader(new InputStreamReader((new FileInputStream(path))))) {
            while ((line = bufferedReader.readLine()) != null) {
                if (line.split(" ")[0].matches("add|update|remove_greater|add_if_min|" +
                        "remove_by_impactspeed")) {
                    try {
                        command = line;
                        for (int i = 0; i < 11; i++) {
                            if (line != null) {
                                line = bufferedReader.readLine();
                                parameters.add(line);
                            } else {
                                System.out.println("Не хватает параметров для создания объекта.");
                                break;
                            }
                        }
                        HumanBeing humanBeing = ElementCreator.createHumanBeing(parameters);
                        if (humanBeing == null) {
                            System.out.println("Остановка execute_script");
                            break;
                        }
                        switch (command.split(" ")[0]) {
                            case "add":
                                requestHandler(new SerializedObjectCommand(commandInvoker.getCommandMap().get("add"),
                                        humanBeing, login, password));
                                break;
                            case "update":
                                requestHandler(new SerializedCombinedCommand(commandInvoker.getCommandMap().get("update"),
                                        ElementCreator.createHumanBeing(), command.split(" ")[1], login, password));
                                break;
                            case "remove_greater":
                                requestHandler(new SerializedObjectCommand(commandInvoker.getCommandMap().get("remove_greater"),
                                        humanBeing, login, password));
                                break;
                            case "add_if_min":
                                requestHandler(new SerializedObjectCommand(commandInvoker.getCommandMap().get("add_if_min"),
                                        humanBeing, login, password));
                                break;
                            case "remove_any_by_impact_speed":
                                requestHandler(new SerializedObjectCommand(commandInvoker.getCommandMap().get("remove_by_impactspeed"),
                                        humanBeing, login, password));
                                break;
                        }
                    } catch (Exception e) {
                        System.out.println("Неверные аргументы при создании коллекции.");
                    }

                } else if (line.split(" ")[0].equals("execute_script"))
                //&& line.split(" ")[1].equals(ExecuteScript.getPath())
                {
                    System.out.println("Пресечена попытка рекурсивного вызова скрипта.");
                    break;
                }
                else {commandInvoker.executeCommand(line.split(" ")); }
            }
        } catch (IOException e) {
            System.out.println("Ошибка! " + e.getMessage());
        }
    }

    public void add_if_min() throws ClassNotFoundException {
        if (login.equals("") || password.equals("")) {
            System.out.println("Вы не авторизированы");
            return;
        }
        requestHandler(new SerializedCommand(commandInvoker.getCommandMap().get("add_if_min"), login, password));
    }

    public void print_field_ascending_weapon_type() throws ClassNotFoundException {
            if (login.equals("") || password.equals("")) {
                System.out.println("Вы не авторизированы");
                return;
            }
            requestHandler(new SerializedCommand(commandInvoker.getCommandMap().get("print_field_ascending_type"), login, password));
    }

    public void removeByImpactspeed(String parseFloat) throws ClassNotFoundException {
        if (login.equals("") || password.equals("")) {
            System.out.println("Вы не авторизированы");
            return;
        }
        requestHandler(new SerializedArgumentCommand(commandInvoker.getCommandMap().get("remove_by_impactspeed"), parseFloat, login, password));
    }

    public void shuffle() throws ClassNotFoundException {
        if (login.equals("") || password.equals("")) {
            System.out.println("Вы не авторизированы");
            return;
        }
        requestHandler(new SerializedCommand(commandInvoker.getCommandMap().get("shuffle"), login, password));
    }

    private void requestHandler(Object serializedObject) throws ClassNotFoundException {
        try {
            socketChannel.configureBlocking(false);
            socketChannel.register(selector, SelectionKey.OP_WRITE);
            while (selector.select() > 0) {
                Iterator<SelectionKey> iterator = selector.selectedKeys().iterator();
                while (iterator.hasNext()) {
                    SelectionKey selectionKey = iterator.next();
                    iterator.remove();
                    if (selectionKey.isWritable()) {
                        socketChannel.register(selector, SelectionKey.OP_READ);
                        sender.sendObject(serializedObject);
                    }
                    if (selectionKey.isReadable()) {
                        receiver.receive(socketChannel);
                        return;
                    }
                }
            }
        } catch (PortUnreachableException e) {
            System.out.println("Не удалось получить данные по указанному порту/сервер не доступен");
            System.exit(0);
        } catch (IOException e) {
            System.out.println("Ответ от сервера пуст...");
            //e.printStackTrace();
        }
    }
}