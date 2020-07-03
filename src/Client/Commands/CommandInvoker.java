package Commands;

import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Set;

public class CommandInvoker { // Our Commands' Model
    private final HashMap<String, ACommand> commandMap = new HashMap<>();

    public CommandInvoker(){
    }

    public void register(ACommand... commands) {
        Arrays.stream(commands).forEach(command -> commandMap.put(command.writeInfo().split(" ")[1], command));
    }

    public void executeCommand(String[] commandName) {
        try {
            if (commandName.length > 0 && !commandName[0].equals("")) {
                ACommand command = commandMap.get(commandName[0]);
                command.execute(commandName);
            } else { System.out.println("Вы не ввели команду."); }
        } catch (NullPointerException ex) {
            System.out.println("Не существует команды " + commandName[0] + ". Для справки используйте – help");
        } catch (IllegalStateException | IOException | ClassNotFoundException | InterruptedException ex) {
            if (ex.getMessage().equals("Connection reset by peer")) {
                System.out.println("Сервер был отключен!");
                System.exit(0);
            }
            System.out.println(ex.getMessage());
        }
    }

    public HashMap<String, ACommand> getCommandMap() {
        return commandMap;
    }
}
