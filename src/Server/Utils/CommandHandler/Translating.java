package Utils.CommandHandler;

import Commands.ACommand;
import Commands.CommandReceiver;
import Commands.SerializedCommand;
import Exceptions.DatabaseException;

import java.io.IOException;
import java.net.Socket;

public class Translating {
    private final CommandReceiver commandReceiver;

    public Translating(CommandReceiver commandReceiver) {
        this.commandReceiver = commandReceiver;
    }

    public void translate(Object o, Socket socket) throws IOException, InterruptedException, ClassNotFoundException, DatabaseException {
        if (o instanceof SerializedCommand) {
            SerializedCommand serializedCommand = (SerializedCommand) o;
            ACommand command = serializedCommand.getCommand();
            command.execute(serializedCommand, socket, commandReceiver);
            System.out.println("“ут всЄ ок4");
        } else {
            System.out.println("Command not identified on the server.");
        }
    }
}