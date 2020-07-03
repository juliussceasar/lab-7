package Commands.Utils.Readers.PrimitiveAndReferenceReaders;

import java.util.Scanner;

public class StringReader {
    public static String read(String messageForConsole, boolean canBeNull) {
        Scanner in = new Scanner(System.in);
        System.out.print(messageForConsole);
        String readString = in.nextLine().trim();

        while(!canBeNull && readString.equals("")) {
            System.out.print("This field cannot be empty. " + messageForConsole);
            readString = in.nextLine().trim();
        }

        if(canBeNull && readString.equals("")) { readString = null; }
        return readString;
    }
}
