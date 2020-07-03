package Commands.Utils.Readers.PrimitiveAndReferenceReaders;

import java.util.Scanner;

public class RefDoubleReader {
    public static Double read(String messageForConsole, boolean canBeNull, int limit, String type) {
        System.out.print(messageForConsole);
        Scanner sc = new Scanner(System.in);
        String line = "";
        double result = 0;
        boolean end = false;
        while (!end) {
            try {
                line = sc.nextLine().trim();
                result = Double.parseDouble(line);
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
                    return Double.parseDouble(null);
                }
            }
        }
        return result;
    }
}