package Commands.ConcreteCommands;


import Commands.ACommand;
import Commands.CommandReceiver;

import java.io.IOException;

/**
 * ���������� ������� ������.
 */
public class Help extends ACommand {
    private final CommandReceiver commandReceiver;

    public Help (CommandReceiver commandReceiver) {
        this.commandReceiver = commandReceiver;
    }

    @Override
    protected void execute(String[] args) {
        if (args.length > 1) {
            System.out.println("������ �� ������ ��������. ������� ��������� � ������� ������� help.");
        }
        commandReceiver.help();
    }

    @Override
    protected String writeInfo() {
        return "������� help - �������� ������� �� ��������� ��������.";
    }
}