package Commands;

import BasicClasses.HumanBeing;
import Collection.CollectionManager;
import Collection.CollectionUtils;
import Collection.IDGenerator;
import Commands.SerializedCommands.SerializedArgumentCommand;
import Commands.SerializedCommands.SerializedCombinedCommand;
import Commands.SerializedCommands.SerializedMessage;
import Commands.SerializedCommands.SerializedObjectCommand;
import Exceptions.DatabaseException;
import Utils.Database.DatabaseManager;
import Utils.Validator;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.List;
import java.util.concurrent.ForkJoinPool;

/**
 * Ресивер, описывает основную логику команд, при надобности делегирует ее коллекшн менеджеру.
 */
public class CommandReceiver {
    private final CollectionManager collectionManager;
    private final CollectionUtils collectionUtils;
    private final DatabaseManager databaseManager;
    private final Validator validator;
    private final ForkJoinPool forkJoinPool = new ForkJoinPool(2);
    //private final ThreadPoolExecutor executor = (ThreadPoolExecutor) Executors.newFixedThreadPool(5);

    public CommandReceiver(CollectionManager collectionManager, CollectionUtils collectionUtils, DatabaseManager databaseManager, Validator validator) {
        this.collectionManager = collectionManager;
        this.collectionUtils = collectionUtils;
        this.databaseManager = databaseManager;
        this.validator = validator;
    }

    public void sendObject(Socket socket, SerializedMessage serializedMessage) throws IOException {
        forkJoinPool.submit(() -> {
            try {
                ObjectOutputStream out = new ObjectOutputStream(socket.getOutputStream());
                out.writeObject(serializedMessage);
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
    }

    public boolean checkUser(String login, String password, Socket socket) throws IOException, DatabaseException {
        boolean exist = databaseManager.validateUserData(login, password);

        if (exist) {
            System.out.println("Пользователь %s с адресом %s:%s - прошел проверку" + login + socket.getInetAddress() + socket.getPort());
            return true;
        } else {
            sendObject(socket, new SerializedMessage("Юзер не зарегистрирован!"));
            System.out.println(String.format("Пользователь %s:%s не прошёл проверку.", socket.getInetAddress(), socket.getPort()));
        }
        return false;
    }

    public void info(SerializedCommand command, Socket socket) throws IOException, DatabaseException {
        if (checkUser(command.getLogin(), command.getPassword(), socket)) {
            sendObject(socket, new SerializedMessage(collectionManager.getInfo()));
            System.out.println(String.format("Клиенту %s:%s отправлен результат работы команды INFO", socket.getInetAddress(), socket.getPort()));
        }
    }


    public void show(SerializedCommand command, Socket socket) throws IOException, DatabaseException {
        if (checkUser(command.getLogin(), command.getPassword(), socket)) {
            sendObject(socket, new SerializedMessage(collectionManager.show()));
            System.out.println(String.format("Клиенту %s:%s отправлен результат работы команды SHOW", socket.getInetAddress(), socket.getPort()));
        }
    }

    public void add(SerializedObjectCommand command, Socket socket) throws IOException, DatabaseException {
        if (checkUser(command.getLogin(), command.getPassword(), socket)) {
            try {
                HumanBeing humanBeing = (HumanBeing) command.getObject();
                humanBeing.setId(IDGenerator.generateID());
                databaseManager.addElement(humanBeing, command.getLogin());
                //здесь почему-то NullPointerException
                collectionManager.add(humanBeing);

                sendObject(socket, new SerializedMessage("Элемент добавлен в коллекцию."));
            } catch (Exception e) {
                sendObject(socket, new SerializedMessage("Полученный элемент не добавлен."));
                e.printStackTrace();
            }
            System.out.println(String.format("Клиенту %s:%s отправлен результат работы команды ADD", socket.getInetAddress(), socket.getPort()));
        }
    }

    public void update(SerializedCombinedCommand command, Socket socket) throws IOException, DatabaseException {
        if (checkUser(command.getLogin(), command.getPassword(), socket)) {
            Long humanID;
            try {
                humanID = Long.parseLong(command.getArg());
                if (collectionUtils.checkExist(humanID)) {
                    HumanBeing humanBeing = (HumanBeing) command.getObject();
                    if (databaseManager.updateById(humanBeing, humanID, command.getLogin())) {
                        collectionManager.update(humanBeing, humanID);

                    sendObject(socket, new SerializedMessage("Элемент с ID" + humanID + "успешно обновлен.")); }
                    else {
                        sendObject(socket, new SerializedMessage("Элемент с ID" + humanID + " создан другим пользователем."));
                    }
                    sendObject(socket, new SerializedMessage("Элемент не обновлен."));
                } else {
                    sendObject(socket, new SerializedMessage("Элемента с таким ID нет в коллекции."));
                }
            } catch (NumberFormatException e) {
                sendObject(socket, new SerializedMessage("Команда не выполнена. Вы ввели некорректный аргумент."));
            }
            System.out.println(String.format("Клиенту %s:%s отправлен результат работы команды UPDATE", socket.getInetAddress(), socket.getPort()));
        }
    }

    public void remove_by_id(SerializedArgumentCommand command, Socket socket) throws IOException, DatabaseException {
        if (checkUser(command.getLogin(), command.getPassword(), socket)) {
            Long humanID;

            /*
            List<Integer> deleteID = databaseManager.clear(command.getLogin());
            deleteID.forEach(collectionManager::remove_by_id);
             */
            try {
                humanID = Long.parseLong(command.getArg());
                if (collectionUtils.checkExist(humanID)) {
                    if (databaseManager.removeById(humanID, command.getLogin())) {
                        collectionManager.remove_by_id(humanID);
                        sendObject(socket, new SerializedMessage("Элемент с ID " + humanID + " успешно удален из коллекции."));
                    } else sendObject(socket, new SerializedMessage("Элемент с таким ID " + humanID + " создан другим пользователем."));
                } else {
                    sendObject(socket, new SerializedMessage("Элемента с таким ID нет в коллекции."));
                }
            } catch (NumberFormatException e) {
                sendObject(socket, new SerializedMessage("Команда не выполнена. Вы ввели некорректный аргумент."));
            }
            System.out.println(String.format("Клиенту %s:%s отправлен результат работы команды REMOVE_BY_ID", socket.getInetAddress(), socket.getPort()));
        }
    }

    public void clear(SerializedCommand command, Socket socket) throws IOException, DatabaseException {
        if (checkUser(command.getLogin(), command.getPassword(), socket)) {
            List<Integer> deleteID = databaseManager.clear(command.getLogin());
            deleteID.forEach(collectionManager::remove_by_id);

            sendObject(socket, new SerializedMessage("Ваши элементы колекции удалены."));
            System.out.println(String.format("Клиенту %s:%s отправлен результат работы команды CLEAR", socket.getInetAddress(), socket.getPort()));
        }
    }


    public void remove_greater(SerializedObjectCommand command, Socket socket) throws IOException {
        forkJoinPool.submit(() -> {
            try {
                if (checkUser(command.getLogin(), command.getPassword(), socket)) {
                    HumanBeing human = (HumanBeing) command.getObject();
                    if (Validator.validateHumanBeing(human)) {
                        List<Long> ids = collectionManager.remove_greater(human, databaseManager.getIdOfUserElements(command.getLogin()));
                        if (ids.isEmpty()) sendObject(socket, new SerializedMessage("Элементов больше введенного не найдено."));
                        else sendObject(socket, new SerializedMessage("Из коллекции удалены элементы с ID: " +
                                ids.toString().replaceAll("[\\[\\]]", "")));

                        ids.forEach(id -> {
                            try {
                                databaseManager.removeById(id, command.getLogin());
                            } catch (DatabaseException e) {
                                try {
                                    sendObject(socket, new SerializedMessage("Ошибка при удалении из бд элемента с id="+ id + "\n" + e));
                                } catch (IOException ex) {
                                    System.out.println("Ошибка при отправке команды...");
                                }
                            }
                        });
                    } else {
                        sendObject(socket, new SerializedMessage("Полученный элемент не прошел валидацию на стороне сервера."));
                    }
                    System.out.println(String.format("Клиенту %s:%s отправлен результат работы команды REMOVE_GREATER", socket.getInetAddress(), socket.getPort()));
                }
            } catch (IOException | DatabaseException e) {
                e.printStackTrace();
            }
        });
    }

    public void maxByCreationDate(SerializedCommand command, Socket socket) throws IOException {
        forkJoinPool.submit(() -> {
            try {
                if (checkUser(command.getLogin(), command.getPassword(), socket)) {
                    sendObject(socket, new SerializedMessage(collectionManager.max_by_creation_date()));
                    System.out.println(String.format("Клиенту %s:%s отправлен результат работы команды MAX_BY_CREATION_DATE", socket.getInetAddress(), socket.getPort()));
                }
            } catch (IOException | DatabaseException e) {
                System.out.println("Ошибка при работе с командой maxByCreationDate");
            }
        });
    }


    public void add_if_min(SerializedObjectCommand command, Socket socket) throws IOException {
        forkJoinPool.submit(() -> {
            try {
                if (checkUser(command.getLogin(), command.getPassword(), socket)) {
                    HumanBeing humanBeing = (HumanBeing) command.getObject();
                    sendObject(socket, new SerializedMessage(collectionManager.add_if_min(humanBeing)));
                    sendObject(socket, new SerializedMessage("Элемент добавлен в коллекцию."));
                }
            } catch (IOException | DatabaseException e) {
                e.printStackTrace();
            }
        });
    }


    public void print_field_ascending_weapon_type(SerializedCommand command, Socket socket) throws IOException {
        ObjectOutputStream out = new ObjectOutputStream(socket.getOutputStream());

        out.writeObject(new SerializedMessage(collectionManager.print_field_ascending_weapon_type()));
        System.out.println(String.format("Клиенту %s:%s отправлен результат работы команды PRINT_FIELD_ASCENDING", socket.getInetAddress(), socket.getPort()));
    }

    public void remove_by_impact_speed(SerializedArgumentCommand command, Socket socket) throws IOException, DatabaseException {
        forkJoinPool.submit(() -> {
            try {
                if (checkUser(command.getLogin(), command.getPassword(), socket)) {
                    Float humanSpeed;
                    try {
                        humanSpeed = Float.parseFloat(command.getArg());
                        if (collectionUtils.checkExist(humanSpeed)) {
                            if (databaseManager.removeBySpeed(humanSpeed, command.getLogin())) {
                                collectionManager.remove_any_by_impact_speed(humanSpeed);
                                sendObject(socket, new SerializedMessage("Элемент с speed " + humanSpeed + " успешно удален из коллекции."));
                            } else
                                sendObject(socket, new SerializedMessage("Элемент с speed " + humanSpeed + " создан другим пользователем."));
                        } else {
                            sendObject(socket, new SerializedMessage("Элемента с таким speed нет в коллекции."));
                        }
                    } catch (NumberFormatException e) {
                        sendObject(socket, new SerializedMessage("Команда не выполнена. Вы ввели некорректный аргумент."));
                    }
                    System.out.println(String.format("Клиенту %s:%s отправлен результат работы команды REMOVE_BY_IMPACT_SPEED", socket.getInetAddress(), socket.getPort()));
                }
            } catch (IOException | DatabaseException e) {
                System.out.println("Что-то пошло не так...");
            }
        });
    }

    public void shuffle(SerializedCommand command, Socket socket) throws IOException {
        forkJoinPool.submit(() -> {
            try {
                if (checkUser(command.getLogin(), command.getPassword(), socket)) {
                    sendObject(socket, new SerializedMessage(collectionManager.shuffle()));
                    sendObject(socket, new SerializedMessage("Элемент добавлен в коллекцию."));
                }
            } catch (IOException | DatabaseException e) {
                e.printStackTrace();
            }
        });
    }

    public void register(SerializedCommand command, Socket socket) throws IOException, DatabaseException {
        if (!databaseManager.doesUserExist(command.getLogin())) {
            databaseManager.addUser(command.getLogin(), command.getPassword());
            sendObject(socket, new SerializedMessage("Пользователь с логином " + command.getLogin() + " успешно создан!"));
            System.out.println(String.format("Пользователь %s успешно зарегистрирован!", command.getLogin()));
        } else { sendObject(socket, new SerializedMessage("Пользователь с таким логином уже существует!")); }
        System.out.println(String.format("Клиенту %s:%s отправлен результат попытки регистрации", socket.getInetAddress(), socket.getPort()));
    }
}
