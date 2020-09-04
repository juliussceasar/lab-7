package Commands.ConcreteCommands;


import Commands.ACommand;
import Commands.CommandReceiver;

import java.io.IOException;

/**
 * Конкретная команда помощи.
 */
public class Help extends ACommand {
    private final CommandReceiver commandReceiver;

    public Help (CommandReceiver commandReceiver) {
        this.commandReceiver = commandReceiver;
    }

    @Override
    protected void execute(String[] args) {
        if (args.length > 1) {
            System.out.println("Введен не нужный аргумент. Команда приведена к базовой команде help.");
        }
        commandReceiver.help();
    }

    @Override
    protected String writeInfo() {
        return "Команда help - получить справку по доступным командам.";
    }
}