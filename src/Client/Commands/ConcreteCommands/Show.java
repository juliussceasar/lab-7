package Commands.ConcreteCommands;


import Commands.ACommand;
import Commands.CommandReceiver;

import java.io.IOException;

/**
 * ���������� ������� ������ ���������� ���������.
 */
public class Show extends ACommand {
    private static final long serialVersionUID = 1234L;
    transient private CommandReceiver commandReceiver;

    public Show (CommandReceiver commandReceiver) {
        this.commandReceiver = commandReceiver;
    }

    public Show() { }

    @Override
    public void execute(String[] args) throws ClassNotFoundException {
        if (args.length > 1) {
            System.out.println("������ �� ������ ��������. ������� ��������� � ������� ������� show.");
        }
        commandReceiver.show();
    }

    @Override
    public String writeInfo() {
        return "������� show - ������� ��� �������� ��������� � ��������� �������������.";
    }
}
