package Utils;

import BasicClasses.HumanBeing;
import BasicClasses.WeaponType;

import java.util.Arrays;

public class Validator {
    private static boolean checkExistWeapon(String toContains) {
        return Arrays.stream(WeaponType.values()).anyMatch((color) -> color.name().equals(toContains));
    }

    public static boolean validateHumanBeing(HumanBeing humanBeing) {
        return humanBeing.getId() != null &&
                ( humanBeing.getName() != null && !humanBeing.getName().equals("")) &&
                humanBeing.getCoordinates().getX() <= 531 &&
                humanBeing.getCoordinates().getY() > -653f;
    }
}