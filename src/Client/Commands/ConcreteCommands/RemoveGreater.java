package Commands.ConcreteCommands;

import Commands.ACommand;
import Commands.CommandReceiver;

import java.io.IOException;

/**
 * Конкретная команда удаления объектов, превышающих заданный.
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
            System.out.println("Введен не нужный аргумент. Команда приведена к базовой команде remove_greater.");
        }
        commandReceiver.remove_greater();
    }

    @Override
    public String writeInfo() {
        return "Команда remove_greater : удалить из коллекции все элементы, превышающие заданный.";
    }
}
