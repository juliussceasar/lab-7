package Commands.SerializedCommands;


import Commands.ACommand;
import Commands.SerializedCommand;

import java.io.Serializable;

public class SerializedObjectCommand extends SerializedCommand implements Serializable {
    private Object object;

    public SerializedObjectCommand(ACommand command, Object object, String login, String password) {
        super(command, login, password);
        this.object = object;
    }

    public Object getObject() {
        return object;
    }
}