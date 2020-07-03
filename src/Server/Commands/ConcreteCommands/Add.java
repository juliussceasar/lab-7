package Commands.ConcreteCommands;


import Commands.ACommand;
import Commands.CommandReceiver;
import Commands.SerializedCommands.SerializedObjectCommand;
import Exceptions.DatabaseException;

import java.io.IOException;
import java.net.Socket;

/**
 * Конкретная команда добавления в коллекцию.
 */
public class Add extends ACommand {
    private static final long serialVersionUID = 1234L;

    @Override
    public void execute(Object argObject, Socket socket, CommandReceiver commandReceiver) throws IOException, DatabaseException {
        SerializedObjectCommand serializedObjectCommand = (SerializedObjectCommand) argObject;
        commandReceiver.add(serializedObjectCommand, socket);
    }


}