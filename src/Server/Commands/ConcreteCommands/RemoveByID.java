package Commands.ConcreteCommands;


import Commands.ACommand;
import Commands.CommandReceiver;
import Commands.SerializedCommand;
import Commands.SerializedCommands.SerializedArgumentCommand;
import Exceptions.DatabaseException;

import java.io.IOException;
import java.net.Socket;

/**
 * Конкретная команда удаления по ID.
 */
public class RemoveByID extends ACommand {
    private static final long serialVersionUID = 1234L;

    @Override
    public void execute(Object argObject, Socket socket, CommandReceiver commandReceiver) throws IOException, DatabaseException {
        String arg = argObject.toString();
        if (arg.split(" ").length == 1) {
            SerializedArgumentCommand serializedArgumentCommand = (SerializedArgumentCommand) argObject;
            commandReceiver.remove_by_id(serializedArgumentCommand, socket);
        }
        else { System.out.println("Некорректное количество аргументов. Для справки напишите help."); }
    }
}
