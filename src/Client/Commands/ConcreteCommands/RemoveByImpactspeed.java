package Commands.ConcreteCommands;


import Commands.ACommand;
import Commands.CommandReceiver;

import java.io.IOException;

public class RemoveByImpactspeed extends ACommand {
    private static final long serialVersionUID = 1234L;
    transient private CommandReceiver commandReceiver;

    public RemoveByImpactspeed(CommandReceiver commandReceiver) {
        this.commandReceiver = commandReceiver;
    }

    public RemoveByImpactspeed() {

    }

    @Override
    public void execute(String[] args) throws ClassNotFoundException {
        if (args.length == 2) { commandReceiver.removeByImpactspeed(args[1]); }
        else { System.out.println("������������ ���������� ����������. ��� ������� �������� help."); }
    }

    @Override
    public String writeInfo() {
        return "������� remove_by_impactspeed - remove_by_impactspeed speed - ������� ���� ����� ������� �� ��������� �� ��� impactSpeed.";
    }
}