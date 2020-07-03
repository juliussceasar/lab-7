package Commands.SerializedCommands;

import java.io.Serializable;

public class SerializedMessage implements Serializable {
    private static final long serialVersionUID = 1234L;

    private String message;

    public SerializedMessage(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}