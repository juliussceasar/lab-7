package Collection;

import BasicClasses.HumanBeing;

/**
 * �����, ���������� ������� ��� ������ � ����������.
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
        info = String.format("ID ��������: %s\n" +
                        "�������� ������: %s\n" +
                        "���������� X: %s\n" +
                        "���������� Y: %s\n" +
                        "���� � ����� �������� ��������: %s\n" +
                        "�������� ����� ��������: %s\n" +
                        "��� ������:  %s\n" +
                        "����������: %s\n" +
                        "��� �����: %s\n" +
                        "�������� �����: %s\n" +
                        "������� �����? %s\n" +
                        "� ���� ���� ����������? %s\n" +
                        "_________________________________________________________\n",  humanBeing.getId(), humanBeing.getName(), humanBeing.getCoordinates().getX(),
                humanBeing.getCoordinates().getY(), humanBeing.getCreationDate(), humanBeing.getImpactSpeed(),
                humanBeing.getWeaponType(), humanBeing.getMood(), humanBeing.getCar().getCool(),
                humanBeing.getCar().getName(), humanBeing.getRealHero(), humanBeing.getHasToothpick());
        return info;
    }
}