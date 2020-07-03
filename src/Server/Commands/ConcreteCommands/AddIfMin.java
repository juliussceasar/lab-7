package Commands.ConcreteCommands;


import Commands.ACommand;
import Commands.CommandReceiver;
import Commands.SerializedCommands.SerializedObjectCommand;

import java.io.IOException;
import java.net.Socket;

public class AddIfMin extends ACommand {
    private static final long serialVersionUID = 1234L;

    @Override
    public void execute(Object argObject, Socket socket, CommandReceiver commandReceiver) throws IOException {
        SerializedObjectCommand serializedObjectCommand = (SerializedObjectCommand) argObject;
        commandReceiver.add_if_min(serializedObjectCommand, socket);
    }
}
