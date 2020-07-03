package Commands.ConcreteCommands;


import Commands.ACommand;
import Commands.CommandReceiver;

import java.io.IOException;

public class RemoveAnyByImpactSpeed extends ACommand {
    private static final long serialVersionUID = 1234L;
    transient private CommandReceiver commandReceiver;

    public RemoveAnyByImpactSpeed(CommandReceiver commandReceiver) {
        this.commandReceiver = commandReceiver;
    }

    public RemoveAnyByImpactSpeed() {

    }

    @Override
    public void execute(String[] args) throws InterruptedException, IOException, ClassNotFoundException {
        if (args.length == 2) { commandReceiver.remove_by_impact_speed(args[1]); }
        else { System.out.println("Некорректное количество аргументов. Для справки напишите help."); }
    }

    @Override
    public String writeInfo() {
        return "Команда remove_any_by_impact_speed : remove_any_by_impact_speed id: удалить любой элемент из коллекции по его impactSpeed.";
    }
}