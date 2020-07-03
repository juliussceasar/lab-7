package Commands.SerializedCommands;


import Commands.ACommand;
import Commands.SerializedCommand;

import java.io.Serializable;

public class SerializedCombinedCommand extends SerializedCommand implements Serializable {
    private Object object;
    private String arg;

    public SerializedCombinedCommand(ACommand command, Object object, String arg, String login, String password) {
        super(command, login, password);
        this.object = object;
        this.arg = arg;
    }

    public Object getObject() {
        return object;
    }

    public String getArg() {
        return arg;
    }
}