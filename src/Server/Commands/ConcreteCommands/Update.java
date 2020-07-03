package Commands.ConcreteCommands;

import BasicClasses.HumanBeing;
import Commands.ACommand;
import Commands.CommandReceiver;
import Commands.SerializedCommands.SerializedCombinedCommand;
import Exceptions.DatabaseException;

import java.io.IOException;
import java.net.Socket;

public class Update extends ACommand {
    private static final long serialVersionUID = 1234L;

    @Override
    public void execute(Object argObject, Socket socket, CommandReceiver commandReceiver) throws IOException, DatabaseException {
        SerializedCombinedCommand combinedCommand = (SerializedCombinedCommand) argObject;
        String arg =  combinedCommand.getArg();
        HumanBeing humanBeing = (HumanBeing) combinedCommand.getObject();
        if (arg.split(" ").length == 1) {
            SerializedCombinedCommand serializedCombinedCommand = (SerializedCombinedCommand) argObject;
            commandReceiver.update(serializedCombinedCommand, socket);
        }
    }
}
