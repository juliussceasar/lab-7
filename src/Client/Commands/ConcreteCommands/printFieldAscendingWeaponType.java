package Commands.ConcreteCommands;


import Commands.ACommand;
import Commands.CommandReceiver;

import java.io.IOException;

public class printFieldAscendingWeaponType extends ACommand {
    private static final long serialVersionUID = 1234L;
    transient private CommandReceiver commandReceiver;

    public printFieldAscendingWeaponType(CommandReceiver commandReceiver) {
        this.commandReceiver = commandReceiver;
    }

    public printFieldAscendingWeaponType() { }

    @Override
    public void execute(String[] args) throws InterruptedException, IOException, ClassNotFoundException {
        if (args.length == 1) {
            commandReceiver.print_field_ascending_weapon_type();
        } else {
            System.out.println("Некорректное количество аргументов. Для справки напишите help.");
        }
    }

    @Override
    public String writeInfo() {
        return "Команда print_field_ascending_weapon_type : Синтаксис: print_field_ascending_weapon_type: вывести значения поля WeaponType в порядке возрастания.";
    }
}
