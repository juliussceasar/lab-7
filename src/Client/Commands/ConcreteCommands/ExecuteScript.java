package Commands.ConcreteCommands;


import Commands.ACommand;
import Commands.CommandReceiver;

/**
 * Конкретная команда выполнения скрипта.
 */
public class ExecuteScript extends ACommand {
    private final CommandReceiver commandReceiver;
    private String path;

    public ExecuteScript(CommandReceiver commandReceiver) {
        this.commandReceiver = commandReceiver;
    }

    @Override
    protected void execute(String[] args) throws StackOverflowError {
        try {
            if (args.length == 2) { path = args[1]; commandReceiver.executeScript(args[1]); }
            else { System.out.println("Некорректное количество аргументов. Для справки напишите help."); }
        } catch (StackOverflowError ex) {
            System.out.println("Ошибка! Произошел выход за пределы стека.");
        }
    }

    @Override
    protected String writeInfo() {
        return "Команда execute_script - execute_script file_name – считать и исполнить скрипт из указанного файла. ";
    }

    public String getPath() {
        return path;
    }
}