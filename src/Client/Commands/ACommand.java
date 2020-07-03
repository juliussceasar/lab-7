package Commands;

import java.io.IOException;
import java.io.Serializable;

public abstract class ACommand implements Serializable {
    private static final long serialVersionUID = 1234L;
    protected abstract String writeInfo();
    protected abstract void execute(String[] args) throws IOException, ClassNotFoundException, InterruptedException;
}