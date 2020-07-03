package Commands.Utils.Readers.PrimitiveAndReferenceReaders;

import java.util.Scanner;

public class RefFloatReader {
    public static float read(String messageForConsole, boolean canBeNull, int limit, String type) {
        System.out.print(messageForConsole);
        Scanner sc = new Scanner(System.in);
        String line = "";
        float result = 0;
        boolean end = false;
        while (!end) {
            try {
                line = sc.nextLine().trim();
                result = Float.parseFloat(line);
                switch (type) {
                    case ("MIN"):
                        if (result > limit) { end = true; }
                        else { System.out.print("Wrong value. " + "It should be bigger " + limit + ". Try again: ");}
                        break;
                    case ("MAX"):
                        if (result < limit) { end = true; }
                        else { System.out.print("Wrong value. " + "It should be less " + limit + ". Try again: ");}
                        break;
                }
            } catch (NumberFormatException ex) {
                System.out.print("You have to type a number, try again: ");
            } catch (NullPointerException e) {
                if (canBeNull && line.equals("")) {
                    return Float.parseFloat(null);
                }
            }
        }

        return result;
    }
}
