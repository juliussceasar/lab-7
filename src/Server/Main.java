

import Collection.CollectionManager;
import Collection.CollectionUtils;
import Commands.CommandReceiver;
import ServerSocket.Controller;
import Utils.CommandHandler.Translating;
import Utils.Database.DatabaseManager;

import Utils.HashEncrypter;
import Utils.Validator;

import java.io.IOException;


public class Main {
    public static void main(String[] args) throws IOException {
        CollectionManager collectionManager = new CollectionManager();
        CollectionUtils collectionUtils = new CollectionUtils(collectionManager);
        Validator validator = new Validator();
        HashEncrypter hashEncrypter = new HashEncrypter();

        DatabaseManager databaseManager = new DatabaseManager(hashEncrypter);
        CommandReceiver commandReceiver = new CommandReceiver(collectionManager, collectionUtils,
                databaseManager, validator);
        Controller controller = new Controller(collectionManager, databaseManager, new Translating(commandReceiver));
        try {
            controller.run(args[0]);
        } catch (ArrayIndexOutOfBoundsException ex) {
            System.out.println("Введено некорректное количество аргументов.\n" +
                    "Требуются 1 аргумент: порт");
        }
    }
}