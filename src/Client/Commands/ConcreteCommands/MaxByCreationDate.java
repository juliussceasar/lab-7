package Commands.ConcreteCommands;


import Commands.ACommand;
import Commands.CommandReceiver;

import java.io.IOException;

public class MaxByCreationDate extends ACommand {
    private static final long serialVersionUID = 1234L;
    transient private final CommandReceiver commandReceiver;

    public MaxByCreationDate(CommandReceiver commandReceiver) {
        this.commandReceiver = commandReceiver;
    }

    @Override
    protected void execute(String[] args) throws ClassNotFoundException {
        if (args.length > 1) {
            System.out.println("������ �� ������ ��������. ������� ��������� � ������� ������� max_by_creation_date.");
        }
        commandReceiver.maxByCreationDate();
    }

    @Override
    protected String writeInfo() {
        return "������� max_by_creation_date - ������� ����� ������ �� ���������, �������� ���� groupAdmin �������� �������� ������������.";
    }
}