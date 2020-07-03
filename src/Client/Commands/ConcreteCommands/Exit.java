package Commands.ConcreteCommands;

import Commands.ACommand;
import Commands.CommandReceiver;

import java.io.IOException;

public class Exit extends ACommand {
    private final CommandReceiver commandReceiver;

    public Exit (CommandReceiver commandReceiver) {
        this.commandReceiver = commandReceiver;
    }

    @Override
    protected void execute(String[] args) throws IOException {
        if (args.length > 1) {
            System.out.println("������ �� ������ ��������. ������� ��������� � ������� ������� exit.");
        }
        commandReceiver.exit();
    }

    @Override
    protected String writeInfo() {
        return "������� exit : ��������� ��������� (��� ���������� � ����).";
    }
}