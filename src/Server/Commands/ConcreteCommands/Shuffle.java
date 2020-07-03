package Commands.ConcreteCommands;


import Commands.ACommand;
import Commands.CommandReceiver;
import Commands.SerializedCommand;

import java.io.IOException;
import java.net.Socket;

public class Shuffle extends ACommand {
    private static final long serialVersionUID = 1234L;


    @Override
    public void execute(Object argObject, Socket socket, CommandReceiver commandReceiver) throws IOException {
        SerializedCommand serializedCommand = (SerializedCommand) argObject;
        commandReceiver.shuffle(serializedCommand, socket);
    }
}