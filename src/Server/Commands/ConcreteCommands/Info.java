package Commands.ConcreteCommands;


import Commands.ACommand;
import Commands.CommandReceiver;
import Commands.SerializedCommand;
import Exceptions.DatabaseException;

import java.io.IOException;
import java.net.Socket;

public class Info extends ACommand {
    private static final long serialVersionUID = 1234L;

    @Override
    public void execute(Object argObject, Socket socket, CommandReceiver commandReceiver) throws IOException, DatabaseException {
        SerializedCommand serializedCommand = (SerializedCommand) argObject;
        commandReceiver.info(serializedCommand, socket);
    }
}
