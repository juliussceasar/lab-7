package Commands;

import Exceptions.DatabaseException;

import java.io.IOException;
import java.io.Serializable;
import java.net.Socket;


public abstract class ACommand implements Serializable {
    private static final long serialVersionUID = 1234L;
    public abstract void execute(Object argObject, Socket socket, CommandReceiver commandReceiver) throws IOException, ClassNotFoundException, InterruptedException, DatabaseException;
}