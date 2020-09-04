package Commands.ConcreteCommands;


import Commands.ACommand;
import Commands.CommandReceiver;

import java.io.IOException;

public class Shuffle extends ACommand {
    private static final long serialVersionUID = 1234L;
    transient private CommandReceiver commandReceiver;

    public Shuffle(CommandReceiver commandReceiver) {
        this.commandReceiver = commandReceiver;
    }

    public Shuffle() { }

    @Override
    public void execute(String[] args) throws ClassNotFoundException {
        if (args.length > 1) {
            System.out.println("Введен не нужный аргумент. Команда приведена к базовой команде show.");
        }
        commandReceiver.shuffle();
    }

    @Override
    public String writeInfo() {
        return "Команда shuffle - вывести все элементы коллекции в рандомно в строковом представлении.";
    }
}