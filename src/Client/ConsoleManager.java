

import ClientDealer.Sender;
import ClientDealer.Session;
import Commands.CommandInvoker;
import Commands.CommandReceiver;
import Commands.Utils.Readers.PrimitiveAndReferenceReaders.LoginPassReader;

import java.io.IOException;
import java.util.Scanner;

class ConsoleManager { // Our View
    private final CommandInvoker commandInvoker;
    private final Session session;
    private final LoginPassReader loginPassReader;
    private final CommandReceiver commandReceiver;

    ConsoleManager(CommandInvoker commandInvoker,
                   Session session, LoginPassReader loginPassReader, CommandReceiver commandReceiver) {
        this.commandInvoker = commandInvoker;
        this.session = session;
        this.loginPassReader = loginPassReader;
        this.commandReceiver = commandReceiver;
    }

    public void startInteractiveMode() throws IOException, ClassNotFoundException, InterruptedException {
        String login;
        String password;

        String[] data = loginPassReader.tryAuthOrRegistration();
        login = data[0];
        password = data[1];
        commandReceiver.setAuthorizationData(login, password);


        try(Scanner scanner = new Scanner(System.in)) {
            while (scanner.hasNextLine()) {
                commandInvoker.executeCommand(scanner.nextLine().trim().split(" "));
            }
        }

        session.closeSession();
    }
}