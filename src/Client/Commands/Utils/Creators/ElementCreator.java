package Commands.Utils.Creators;


import BasicClasses.*;
import Commands.Utils.Readers.EnumReaders.MoodReader;
import Commands.Utils.Readers.EnumReaders.WeaponTypeReader;
import Commands.Utils.Readers.PrimitiveAndReferenceReaders.*;

import java.time.ZonedDateTime;
import java.util.ArrayList;

public class ElementCreator {
    public static HumanBeing createHumanBeing() {
        String name = StringReader.read("Введите имя человека: ", false);
        double x = PrimitiveDoubleReader.read("Введите X: ", 0, "MIN");
        Double y = RefDoubleReader.read("Введите Y: ", false, 0, "MIN");
        Boolean realHero = RefBooleanReader.read("Человек герой?: ", false);
        boolean hasToothPick = PrimitiveBooleanReader.read("У него есть зубочистка? ", false);
        Float impactSpeed = RefFloatReader.read("Введите скорость человека: ", true, 0, "MIN");
        String soundtrackName = StringReader.read("Введите любимую песню: ", false);
        WeaponType weaponType = WeaponTypeReader.read(true);
        Mood mood = MoodReader.read(false);

        return new HumanBeing(name, new Coordinates(x, y), realHero, hasToothPick, impactSpeed, soundtrackName, weaponType, mood, createCar());
    }

    private static Car createCar() {
        String carName = StringReader.read("Введите название машины: ", false);
        boolean cool = PrimitiveBooleanReader.read("Введите крутая ли тачка?: ", false);

        return new Car(carName, cool);
    }

    /**
     * Метод для создания HumanBeing из массива String[] для CSV
     * @param args массив полей
     * @return созданный HumanBeing
     */
    public static HumanBeing createHumanBeingFromString(String[] args) throws NumberFormatException, ArrayIndexOutOfBoundsException {
        if (validateArray(args)) {
            return new HumanBeing(args[0],
                    new Coordinates(Double.parseDouble(args[1]), Double.parseDouble(args[2])),
                    ZonedDateTime.parse(args[3]),
                    Boolean.parseBoolean(args[4]),
                    Boolean.parseBoolean(args[5]),
                    Float.parseFloat(args[6]),
                    args[7],
                    WeaponType.valueOf(args[8]),
                    Mood.valueOf(args[9]),
                    new Car(args[10], Boolean.parseBoolean(args[11])));
        } else { System.out.println("Один из параметров не соответствует требованиям..."); return null; }
    }

    private static boolean validateArray(String[] args) {
        try {
            return  !args[0].isEmpty()
                    && Double.parseDouble(args[1]) < Double.MAX_VALUE
                    && Double.parseDouble(args[2]) < Double.MAX_VALUE
                    && !args[3].isEmpty()
                    && (WeaponTypeReader.checkExist(args[8]))
                    && !args[5].isEmpty()
                    && !args[6].isEmpty()
                    && !args[7].isEmpty();
        } catch (NumberFormatException ex) { return false; }
    }

    /**
     * Метод для создания HumanBeing из эррейлиста для execute_script
     * @param parameters эррейлист
     * @return созданный HumanBeing
     */
    public static HumanBeing createHumanBeing(ArrayList<String> parameters) {
        if (validateArray(parameters)) {
            return new HumanBeing(parameters.get(0),
                    new Coordinates(Double.parseDouble(parameters.get(1)), Double.parseDouble(parameters.get(2))),
                    Boolean.parseBoolean(parameters.get(3)),
                    Boolean.parseBoolean(parameters.get(4)),
                    Float.parseFloat(parameters.get(5)),
                    parameters.get(6),
                    WeaponType.valueOf(parameters.get(7)),
                    Mood.valueOf(parameters.get(8)),
                    new Car(parameters.get(9), Boolean.parseBoolean(parameters.get(10)))
                    );
        } else { System.out.println("Один из параметров не соответствует требованиям."); return null; }
    }

    private static boolean validateArray(ArrayList<String> parameters) {
        try {
            return !parameters.get(0).isEmpty()
                    && !parameters.get(1).isEmpty()
                    && !parameters.get(2).isEmpty()
                    && Double.parseDouble(parameters.get(1)) < Double.MAX_VALUE
                    && Double.MIN_VALUE < Double.parseDouble(parameters.get(1))
                    && Double.parseDouble(parameters.get(2)) < Double.MAX_VALUE
                    && Double.MIN_VALUE < Double.parseDouble(parameters.get(2))
                    && !parameters.get(3).isEmpty()
                    && Float.parseFloat(parameters.get(6)) < Float.MAX_VALUE
                    && Float.MIN_VALUE < Float.parseFloat(parameters.get(6))
                    && !parameters.get(6).isEmpty()
                    && !parameters.get(9).isEmpty()
                    && !parameters.get(10).isEmpty();

        } catch (NumberFormatException ex) { return false; }
    }
}
