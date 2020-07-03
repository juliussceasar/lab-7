package Commands.Utils.Readers.PrimitiveAndReferenceReaders;

import Commands.CommandReceiver;
import Commands.Utils.HashEncrypter;

import java.io.Console;
import java.io.IOException;
import java.util.Arrays;

public class LoginPassReader {
    private final CommandReceiver commandReceiver;
    private final HashEncrypter hashEncrypter;

    public LoginPassReader(CommandReceiver commandReceiver, HashEncrypter hashEncrypter) {
        this.commandReceiver = commandReceiver;
        this.hashEncrypter = hashEncrypter;
    }

    public String[] tryAuthOrRegistration() throws InterruptedException, IOException, ClassNotFoundException {
        String login = "";
        String password = "";

        Console console = System.console();
        if (console == null){
            System.out.println("Запустите клиент из jar файла.");
            System.exit(0);
        }

        String type = console.readLine("Приветствуем! Вы уже зарегистрированы? [Да/Нет] ").trim().toUpperCase();
        if (type.equals("ДА")) {
            login = console.readLine("Введите логин: ").trim();
            char[] passwordArray = console.readPassword("Введите Ваш очень нужный пароль: ");
            password = hashEncrypter.encryptString(new String(passwordArray)).trim();

        } else if (type.equals("НЕТ")) {
            login = console.readLine("Введите логин: ").trim();
            char[] passwordArray = console.readPassword("Введите Ваш очень нужный пароль: ");
            char[] passwordArrayCheck;

            do {
                passwordArrayCheck = console.readPassword("Введите Ваш очень нужный пароль еще раз: ");
                password = hashEncrypter.encryptString(new String(passwordArray)).trim();
            } while (!Arrays.equals(passwordArray, passwordArrayCheck));

            commandReceiver.register(login, password);
        } else { System.out.println("Такого варианта нет. В следующий раз будь осторожнее. Пока!"); System.exit(0);}

        return new String[] {login, password};
    }
}