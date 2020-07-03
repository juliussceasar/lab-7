package Commands.ConcreteCommands;


import Commands.ACommand;
import Commands.CommandReceiver;

import java.io.IOException;

/**
 * ���������� ������� ��������� ���������� � ���������.
 */
public class Info extends ACommand {
    private static final long serialVersionUID = 1234L;
    transient private final CommandReceiver commandReceiver;

    public Info (CommandReceiver commandReceiver) {
        this.commandReceiver = commandReceiver;
    }

    @Override
    protected void execute(String[] args) throws IOException, ClassNotFoundException, InterruptedException {
        if (args.length > 1) {
            System.out.println("������ �� ������ ��������. ������� ��������� � ������� ������� info.");
        }
        commandReceiver.info();
    }

    @Override
    protected String writeInfo() {
        return "������� info : ������� � ����������� ����� ������ ���������� � ���������.";
    }
}