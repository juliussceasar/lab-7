package Commands.ConcreteCommands;

import Commands.ACommand;
import Commands.CommandReceiver;

import java.io.IOException;

public class Exit extends ACommand {
    private final CommandReceiver commandReceiver;

    public Exit (CommandReceiver commandReceiver) {
        this.commandReceiver = commandReceiver;
    }

    @Override
    protected void execute(String[] args) throws IOException {
        if (args.length > 1) {
            System.out.println("Введен не нужный аргумент. Команда приведена к базовой команде exit.");
        }
        commandReceiver.exit();
    }

    @Override
    protected String writeInfo() {
        return "Команда exit : завершить программу (без сохранения в файл).";
    }
}