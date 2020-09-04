package Commands.ConcreteCommands;


import Commands.ACommand;
import Commands.CommandReceiver;

import java.io.IOException;

public class PrintFieldAscendingWeaponType extends ACommand {
    private static final long serialVersionUID = 1234L;
    transient private CommandReceiver commandReceiver;

    public PrintFieldAscendingWeaponType(CommandReceiver commandReceiver) {
        this.commandReceiver = commandReceiver;
    }

    public PrintFieldAscendingWeaponType() { }

    @Override
    public void execute(String[] args) throws ClassNotFoundException {
        if (args.length == 1) {
            commandReceiver.print_field_ascending_weapon_type();
        } else {
            System.out.println("������������ ���������� ����������. ��� ������� �������� help.");
        }
    }

    @Override
    public String writeInfo() {
        return "������� print_field_ascending_weapon_type - ���������: print_field_ascending_weapon_type: ������� �������� ���� WeaponType � ������� �����������.";
    }
}
