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
            System.out.println("��������� ������ �� jar �����.");
            System.exit(0);
        }

        String type = console.readLine("������������! �� ��� ����������������? [��/���] ").trim().toUpperCase();
        if (type.equals("��")) {
            login = console.readLine("������� �����: ").trim();
            char[] passwordArray = console.readPassword("������� ��� ����� ������ ������: ");
            password = hashEncrypter.encryptString(new String(passwordArray)).trim();

        } else if (type.equals("���")) {
            login = console.readLine("������� �����: ").trim();
            char[] passwordArray = console.readPassword("������� ��� ����� ������ ������: ");
            char[] passwordArrayCheck;

            do {
                passwordArrayCheck = console.readPassword("������� ��� ����� ������ ������ ��� ���: ");
                password = hashEncrypter.encryptString(new String(passwordArray)).trim();
            } while (!Arrays.equals(passwordArray, passwordArrayCheck));

            commandReceiver.register(login, password);
        } else { System.out.println("������ �������� ���. � ��������� ��� ���� ����������. ����!"); System.exit(0);}

        return new String[] {login, password};
    }
}