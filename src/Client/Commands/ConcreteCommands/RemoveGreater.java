package Commands.ConcreteCommands;

import Commands.ACommand;
import Commands.CommandReceiver;

import java.io.IOException;

/**
 * ���������� ������� �������� ��������, ����������� ��������.
 */
public class RemoveGreater extends ACommand {
    private static final long serialVersionUID = 1234L;
    transient private CommandReceiver commandReceiver;

    public RemoveGreater (CommandReceiver commandReceiver) {
        this.commandReceiver = commandReceiver;
    }

    public RemoveGreater() {

    }

    @Override
    public void execute(String[] args) throws InterruptedException, IOException, ClassNotFoundException {
        if (args.length > 1) {
            System.out.println("������ �� ������ ��������. ������� ��������� � ������� ������� remove_greater.");
        }
        commandReceiver.remove_greater();
    }

    @Override
    public String writeInfo() {
        return "������� remove_greater : ������� �� ��������� ��� ��������, ����������� ��������.";
    }
}
