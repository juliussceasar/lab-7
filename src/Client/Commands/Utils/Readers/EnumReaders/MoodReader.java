package Commands.Utils.Readers.EnumReaders;


import BasicClasses.Mood;

import java.util.Arrays;
import java.util.Scanner;

public class MoodReader {
    private static boolean checkExist(String toContains) {
        return Arrays.stream(Mood.values()).anyMatch((mood) -> mood.name().equals(toContains));
    }

    public static Mood read(boolean canBeNull) {
        Scanner in = new Scanner(System.in);
        System.out.print("Enter mood from presented ones: (" + Arrays.asList(Mood.values()) + "): ");
        String toContains = in.nextLine().trim();

        if ((!checkExist(toContains)) && !canBeNull && !toContains.equals("") || !canBeNull && toContains.equals("") || canBeNull && !toContains.equals("")) {
            while (!checkExist(toContains)) {
                System.out.print("You entered the wrong value. Try again: ");
                toContains = in.nextLine().trim();
                checkExist(toContains);
            }
        }

        if (canBeNull && toContains.equals("")) { return null; }

        return Enum.valueOf(Mood.class, toContains);
    }
}
