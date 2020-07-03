package Commands.Utils.Readers.EnumReaders;


import BasicClasses.WeaponType;

import java.util.Arrays;
import java.util.Scanner;

public class WeaponTypeReader {
    public static boolean checkExist(String toContains) {
        return Arrays.stream(WeaponType.values()).anyMatch((weaponType) -> weaponType.name().equals(toContains));
    }

    public static WeaponType read(boolean canBeNull) {
        Scanner in = new Scanner(System.in);
        System.out.print("Enter the weapon from presented ones(" + Arrays.asList(WeaponType.values()) + "): ");
        String toContains = in.nextLine().trim();

        if ((!checkExist(toContains)) && !canBeNull && !toContains.equals("") || !canBeNull && toContains.equals("") || canBeNull && !toContains.equals("")) {
            while (!checkExist(toContains)) {
                System.out.print("You entered wrong value. Try again: ");
                toContains = in.nextLine().trim();
                checkExist(toContains);
            }
        }

        if (canBeNull && toContains.equals("")) { return null; }

        return Enum.valueOf(WeaponType.class, toContains);
    }
}
