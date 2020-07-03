import ClientDealer.Receiver;
import ClientDealer.Sender;
import ClientDealer.Session;
import ClientDealer.Translating;
import Commands.ACommand;
import Commands.CommandInvoker;
import Commands.CommandReceiver;
import Commands.ConcreteCommands.*;
import Commands.Utils.Creators.ElementCreator;
import Commands.Utils.HashEncrypter;
import Commands.Utils.Readers.PrimitiveAndReferenceReaders.LoginPassReader;

import java.io.IOException;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

public class Main {
    public static void main(String[] args) throws IOException {
        try {
            CommandInvoker commandInvoker = new CommandInvoker();
            //communication
            Session session = new Session();
            Sender sender = new Sender(session);
            Receiver receiver = new Receiver(new Translating());

            //utils
            ElementCreator elementCreator = new ElementCreator();

            CommandReceiver commandReceiver = new CommandReceiver(commandInvoker, session, sender,
                    elementCreator, receiver);

            commandInvoker.register(new Add(commandReceiver), new Clear(commandReceiver),
                            new AddIfMin(commandReceiver), new ExecuteScript(commandReceiver),
                            new Exit(commandReceiver), new Help(commandReceiver),
                            new Info(commandReceiver), new MaxByCreationDate(commandReceiver),
                            new printFieldAscendingWeaponType(commandReceiver), new Register(commandReceiver),
                            new RemoveAnyByImpactSpeed(commandReceiver), new RemoveByID(commandReceiver),
                            new RemoveGreater(commandReceiver), new Show(commandReceiver),
                            new Shuffle(commandReceiver), new Update(commandReceiver));

            HashEncrypter hashEncrypter = new HashEncrypter();
            LoginPassReader loginPassReader = new LoginPassReader(commandReceiver, hashEncrypter);
            ConsoleManager consoleManager = new ConsoleManager(commandInvoker, sender, session,
                    loginPassReader, commandReceiver);
            consoleManager.startInteractiveMode();
        } catch (InterruptedException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
}