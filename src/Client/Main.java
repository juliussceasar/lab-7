import ClientDealer.Receiver;
import ClientDealer.Sender;
import ClientDealer.Session;
import ClientDealer.Translating;
import Commands.CommandInvoker;
import Commands.CommandReceiver;
import Commands.ConcreteCommands.*;
import Commands.Utils.HashEncrypter;
import Commands.Utils.Readers.PrimitiveAndReferenceReaders.LoginPassReader;

import java.io.IOException;

public class Main {
    public static void main(String[] args) throws IOException {
        try {
            CommandInvoker commandInvoker = new CommandInvoker();
            //communication
            Session session = new Session();
            Sender sender = new Sender(session);
            Receiver receiver = new Receiver(new Translating());

            CommandReceiver commandReceiver = new CommandReceiver(commandInvoker, session, sender,
                    receiver);

            commandInvoker.register(new Add(commandReceiver), new Clear(commandReceiver),
                            new AddIfMin(commandReceiver), new ExecuteScript(commandReceiver),
                            new Exit(commandReceiver), new Help(commandReceiver),
                            new Info(commandReceiver), new MaxByCreationDate(commandReceiver),
                            new PrintFieldAscendingWeaponType(commandReceiver), new Register(commandReceiver),
                            new RemoveByImpactspeed(commandReceiver), new RemoveByID(commandReceiver),
                            new RemoveGreater(commandReceiver), new Show(commandReceiver),
                            new Shuffle(commandReceiver), new Update(commandReceiver));

            HashEncrypter hashEncrypter = new HashEncrypter();
            LoginPassReader loginPassReader = new LoginPassReader(commandReceiver, hashEncrypter);
            ConsoleManager consoleManager = new ConsoleManager(commandInvoker, session,
                    loginPassReader, commandReceiver);
            consoleManager.startInteractiveMode();
        } catch (InterruptedException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
}