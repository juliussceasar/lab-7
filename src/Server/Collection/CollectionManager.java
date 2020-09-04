package Collection;

import BasicClasses.HumanBeing;

import java.time.ZonedDateTime;
import java.util.*;
import java.util.stream.Collectors;

/*
* ���������� ����������, ��� ������ �����������
 */

public class CollectionManager {
    private LinkedList<HumanBeing> linkedList;
    private ZonedDateTime creationDate;
    private List res = new ArrayList();

    public void initList() {
        if (linkedList == null) { linkedList = new LinkedList<>(); creationDate = ZonedDateTime.now(); }
    }

    public  LinkedList<HumanBeing> getLinkedList() { return linkedList; }

    public String add(HumanBeing humanBeing) {
        linkedList.add(humanBeing);
        return "������� ��������.";
    }

    //������� ���� � ���������
    public  String getInfo() {
        String info = "";
        info += "��� ���������: " + linkedList.getClass().getName()  + "\n";
        info += "���� ������������� ���������: " + creationDate + "\n";
        info += "���-�� ��������� � ���������: " + linkedList.size() + "\n";
        info += "- - - - - - - - - - - - - - - - - - - - - - - -\n";
        return info;
    }

    public  String show() {
        linkedList.sort(Comparator.comparing(HumanBeing::getName));  // ���������� ��������� �� ��������.

        String info = linkedList.stream().map(CollectionUtils::display).collect(Collectors.joining(", ")    );
        if (info.equals("")) { info = "��������� �����."; }
        return info;
    }

    //�������� �������� �������� ���������, id �������� ����� ���������
    public void update(HumanBeing personToUpdate, Long elementId) {
        Collection<HumanBeing> syncList = Collections.synchronizedCollection(linkedList);
        syncList.forEach(HumanBeing -> {
            if (HumanBeing.getId().equals(elementId)) {
                HumanBeing.setName(personToUpdate.getName());
                HumanBeing.setCoordinates(personToUpdate.getCoordinates());
                HumanBeing.setRealHero(personToUpdate.getRealHero());
                HumanBeing.setHasToothpick(personToUpdate.getHasToothpick());
                HumanBeing.setImpactSpeed(personToUpdate.getImpactSpeed());
                HumanBeing.setSoundtrackName(personToUpdate.getSoundtrackName());
                HumanBeing.setWeaponType(personToUpdate.getWeaponType());
                HumanBeing.setMood(personToUpdate.getMood());
                HumanBeing.setCar(personToUpdate.getCar());
            }
        });
    }

    //������� ������� �� ��������� �� ��� id
    public void remove_by_id(long personId) {
        String info = linkedList.stream().filter(human -> human.getId() == personId).toString(); //����������
        for (int i = 0; i < linkedList.size(); i++) {
            if (linkedList.get(i).getId() == personId) {
                linkedList.remove(linkedList.get(i));
            }
        }
    }

    //�������� ���������
    public void clear() {
        linkedList.clear();
    }

    //������� �� ��������� ��� ��������, ����������� �������� (�� ��� ID)
    public List<Long> remove_greater(HumanBeing humanBeing, List<Long> ids) {
        Collection<HumanBeing> syncList = Collections.synchronizedCollection(linkedList);
        res.clear();
        Iterator<HumanBeing> iterator = syncList.iterator();
        while (iterator.hasNext()){
            HumanBeing listHumans = iterator.next();
            if (ids.contains(listHumans.getId()) && listHumans.compareTo(humanBeing) > 0) {
                appendToList(listHumans.getId());
                iterator.remove();
            }
        }

        return res;
    }

    //�������� ������� ���� �� ������, ��� ����������� ������� � ���������
    public String add_if_min(HumanBeing humanBeing) {
        final int counter = 0;
        //String info = linkedList.stream().filter(human -> human.getId().compareTo())

        if (counter == linkedList.size()) {
            linkedList.add(humanBeing);
            return "������� ������ ��������� � ��� ��������";
        } else {
            return "������� �� ���������.";
        }
    }

    //���������� �������� ��������� � ��������� �������
    public String shuffle() {
        if (linkedList.size() > 0) {
            Collections.shuffle(linkedList);

            String info = linkedList.stream().map(CollectionUtils::display).collect(Collectors.joining(", ")    );
            if (info.equals("")) { info = "�� ������ ������ ��������� �����."; }
            return info + "\n ��������� ������������.";
        } else { return "��������� �����."; }
    }

    //�������� ������ ������ �� �������� ��������
    public String remove_any_by_impact_speed(float impactSpeed) {
        //String info = linkedList.stream().filter(human -> human.getImpactSpeed().compareTo(human) == 0 )
        for (int i = 0; i < linkedList.size(); i++) {
            if (linkedList.get(i).getImpactSpeed().compareTo(impactSpeed) == 0) {
                linkedList.remove(linkedList.get(i));
                return "������� �����.";
            }
        }
        return "��������� � �������� ��������� ���.";
    }

    //������� ����� ������ �� ���������, �������� ���� creationDate �������� �������� ������������
    public String max_by_creation_date() {
        if (linkedList.size() > 0) {
            return CollectionUtils.display(Collections.max(linkedList,
                    Comparator.comparing(HumanBeing::getCreationDate))) + "��� ������������ ������� � creationDate";
        } else { return "��������� �����."; }
    }

    //������� �������� ���� weaponType ���� ��������� � ������� �����������
    public String print_field_ascending_weapon_type() {
        linkedList.sort(Comparator.comparing(HumanBeing::getWeaponType));  // ���������� ��������� �� ��������.

        String info = linkedList.stream().map(CollectionUtils::display).collect(Collectors.joining(", ")    );
        if (info.equals("")) { info = "�� ������ ������ ��������� �����."; }
        return info;
    }

    public void appendToList(Object o){
        res.add(o);
    }

}
