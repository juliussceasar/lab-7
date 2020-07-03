package Commands.ConcreteCommands;


import Commands.ACommand;
import Commands.CommandReceiver;

import java.io.IOException;

/**
 * ���������� ������� ���������� � ���������.
 */
public class Add extends ACommand {
    private static final long serialVersionUID = 1234L;
    transient private final CommandReceiver commandReceiver;

    public Add (CommandReceiver commandReceiver) {
        this.commandReceiver = commandReceiver;
    }

    @Override
    protected void execute(String[] args) throws IOException, ClassNotFoundException, InterruptedException {
        if (args.length > 1) {
            System.out.println("������ �� ������ ��������. ������� ��������� � ������� ������� add.");
        }
        commandReceiver.add();
    }

    @Override
    protected String writeInfo() {
        return "������� add : �������� ����� ������� � ���������.";
    }
}