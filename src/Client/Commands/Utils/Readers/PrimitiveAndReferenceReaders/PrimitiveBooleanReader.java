package Commands.Utils.Readers.PrimitiveAndReferenceReaders;

import java.util.Scanner;

public class PrimitiveBooleanReader {
    public static boolean read(String messageForConsole, boolean canBeNull) {
        Scanner in = new Scanner(System.in);
        System.out.print(messageForConsole);
        String line = in.nextLine().trim();
        Boolean readBool = Boolean.parseBoolean(line);

        while (!line.equals("true") && !line.equals("false")) {
            System.out.println("¬ведите true или false. " + messageForConsole);
            line = in.nextLine().trim();
            readBool = Boolean.parseBoolean(line);
        }

        while (!canBeNull && readBool.equals("")) {
            System.out.print("Ёто поле не может быть пустым. " + messageForConsole);
            readBool = Boolean.parseBoolean(in.nextLine().trim());
        }

        if (canBeNull && readBool.equals("")) {
            readBool = null;
        }
        return readBool;
    }
}