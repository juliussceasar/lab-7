package Commands.ConcreteCommands;

import Commands.ACommand;
import Commands.CommandReceiver;

import java.io.IOException;

public class Register extends ACommand {
    private static final long serialVersionUID = 1234L;
    transient private final CommandReceiver commandReceiver;

    public Register (CommandReceiver commandReceiver) {
        this.commandReceiver = commandReceiver;
    }

    @Override
    protected void execute(String[] args) throws ClassNotFoundException {
        if (args.length == 3) {
            //�� ��������� ������� ������ ������������ � ���� ������ �� ������� +
            //������� �������� ������������, ������� �������� �������!
            commandReceiver.register(args[1], args[2]);
            commandReceiver.setAuthorizationData(args[1], args[2]);
        }
        else { System.out.println("������������ ���������� ����������. ��� ������� �������� help."); }
    }

    @Override
    protected String writeInfo() {
        return "������� register - register login password - ����������� ������������";
    }
}