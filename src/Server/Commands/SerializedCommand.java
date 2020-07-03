package Commands;

import java.io.Serializable;

public class SerializedCommand implements Serializable {
    private ACommand command;
    private String login;
    private String password;

    public SerializedCommand(ACommand command, String login, String password) {
        this.command = command;
        this.login = login;
        this.password = password;
    }

    public ACommand getCommand() {
        return command;
    }

    public String getPassword() {
        return password;
    }

    public String getLogin() {
        return login;
    }
}