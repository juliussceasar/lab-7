package Commands.ConcreteCommands;


import Commands.ACommand;
import Commands.CommandReceiver;

import java.io.IOException;

/**
 * Конкретная команда обновления объекта.
 */
public class Update extends ACommand {
    private static final long serialVersionUID = 1234L;
    transient private CommandReceiver commandReceiver;

    public Update (CommandReceiver commandReceiver) {
        this.commandReceiver = commandReceiver;
    }

    public Update() {
    }

    @Override
    public void execute(String[] args) throws InterruptedException, IOException, ClassNotFoundException {
        if (args.length == 2) { commandReceiver.update(args[1]); }
        else { System.out.println("Некорректное количество аргументов. Для справки напишите help."); }
    }

    @Override
    public String writeInfo() {
        return "Команда update : update id обновить значение элемента коллекции, id которого равен заданному.";
    }
}
