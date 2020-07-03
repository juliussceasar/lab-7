package Commands.ConcreteCommands;


import Commands.ACommand;
import Commands.CommandReceiver;

import java.io.IOException;

public class AddIfMin extends ACommand {
    private static final long serialVersionUID = 1234L;
    transient private final CommandReceiver commandReceiver;

    public AddIfMin (CommandReceiver commandReceiver) {
        this.commandReceiver = commandReceiver;
    }

    @Override
    protected void execute(String[] args) throws ClassNotFoundException {
        if (args.length > 1) {
            System.out.println("������ �� ������ ��������. ������� ��������� � ������� ������� add_if_min.");
        }
        commandReceiver.add();
    }

    @Override
    protected String writeInfo() {
        return "������� add_if_min : �������� ����� ������� � ���������.";
    }
}