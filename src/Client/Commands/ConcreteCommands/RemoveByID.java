package Commands.ConcreteCommands;


import Commands.ACommand;
import Commands.CommandReceiver;

import java.io.IOException;

public class RemoveByID extends ACommand {
    private static final long serialVersionUID = 1234L;
    transient private CommandReceiver commandReceiver;

    public RemoveByID (CommandReceiver commandReceiver) {
        this.commandReceiver = commandReceiver;
    }

    public RemoveByID() {

    }

    @Override
    public void execute(String[] args) throws ClassNotFoundException {
        if (args.length == 2) { commandReceiver.remove_by_id(args[1]); }
        else { System.out.println("Некорректное количество аргументов. Для справки напишите help."); }
    }

    @Override
    public String writeInfo() {
        return "Команда remove_by_id - remove_by_id id - удалить элемент из коллекции по его id.";
    }
}
