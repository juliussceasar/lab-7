package Collection;

import BasicClasses.HumanBeing;

/**
 * Класс, содержащий утилиты для работы с коллекцией.
 */
public class CollectionUtils {
    private final CollectionManager collectionManager;

    public CollectionUtils(CollectionManager collectionManager) {
        this.collectionManager = collectionManager;
    }
    
    public boolean checkExist(Long ID) {
        for (HumanBeing humanBeing:collectionManager.getLinkedList()) {
            if (humanBeing.getId().equals(ID)) {
                return true;
            }
        }
        return false;
    }

    public boolean checkExist(Float speed) {
        for (HumanBeing humanBeing:collectionManager.getLinkedList()) {
            if (humanBeing.getImpactSpeed().equals(speed)) {
                return true;
            }
        }
        return false;
    }

    static String display(HumanBeing humanBeing) {
        String info = "";
        info = String.format("ID элемента: %s\n" +
                        "Название группы: %s\n" +
                        "Координата X: %s\n" +
                        "Координата Y: %s\n" +
                        "Дата и время создания элемента: %s\n" +
                        "Скорость этого человека: %s\n" +
                        "Тип орудия:  %s\n" +
                        "Настроение: %s\n" +
                        "Тип тачки: %s\n" +
                        "Название тачки: %s\n" +
                        "Человек герой? %s\n" +
                        "У него есть зубочистка? %s\n" +
                        "_________________________________________________________\n",  humanBeing.getId(), humanBeing.getName(), humanBeing.getCoordinates().getX(),
                humanBeing.getCoordinates().getY(), humanBeing.getCreationDate(), humanBeing.getImpactSpeed(),
                humanBeing.getWeaponType(), humanBeing.getMood(), humanBeing.getCar().getCool(),
                humanBeing.getCar().getName(), humanBeing.getRealHero(), humanBeing.getHasToothpick());
        return info;
    }
}