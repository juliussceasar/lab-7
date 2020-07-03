package Commands.Utils.Readers.PrimitiveAndReferenceReaders;

import java.util.Scanner;

public class PrimitiveDoubleReader {
    public static double read(String messageForConsole, int limit, String type) {
        System.out.print(messageForConsole);
        Scanner sc = new Scanner(System.in);
        double result = 0;
        boolean end = false;
        while (!end) {
            try {
                result = Double.parseDouble(sc.nextLine().trim());
                switch (type) {
                    case ("MIN"):
                        if (result > limit) { end = true; }
                        else { System.out.print("Неверное число. " + "Оно должно превышать " + limit + ". Try again: ");}
                        break;
                    case ("MAX"):
                        if (result < limit) { end = true; }
                        else { System.out.print("Неверное число. " + "Оно не должно превышать " + limit + ". Try again: ");}
                        break;
                }
            } catch (NumberFormatException ex) {
                System.out.print("Вы должны вставить число, попробуйте снова: ");
            }
        }

        return result;
    }
}
