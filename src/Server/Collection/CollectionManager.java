package Collection;

import BasicClasses.HumanBeing;

import java.time.ZonedDateTime;
import java.util.*;
import java.util.stream.Collectors;

/*
* Управление коллекцией, все методы статические
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
        return "Элемент добавлен.";
    }

    //вывести инфу о коллекции
    public  String getInfo() {
        String info = "";
        info += "Тип коллекции: " + linkedList.getClass().getName()  + "\n";
        info += "Дата инициализации коллекции: " + creationDate + "\n";
        info += "Кол-во элементов в коллекции: " + linkedList.size() + "\n";
        info += "- - - - - - - - - - - - - - - - - - - - - - - -\n";
        return info;
    }

    public  String show() {
        linkedList.sort(Comparator.comparing(HumanBeing::getName));  // Сортировка коллекции по алфавиту.

        String info = linkedList.stream().map(CollectionUtils::display).collect(Collectors.joining(", ")    );
        if (info.equals("")) { info = "Коллекция пуста."; }
        return info;
    }

    //обновить значение элемента коллекции, id которого равен заданному
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

    //удалить элемент из коллекции по его id
    public void remove_by_id(long personId) {
        String info = linkedList.stream().filter(human -> human.getId() == personId).toString(); //переделано
        for (int i = 0; i < linkedList.size(); i++) {
            if (linkedList.get(i).getId() == personId) {
                linkedList.remove(linkedList.get(i));
            }
        }
    }

    //очищение коллекции
    public void clear() {
        linkedList.clear();
    }

    //удалить из коллекции все элементы, превышающие заданный (по его ID)
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

    //Добавить элемент если он меньше, чем минимальный элемент в коллекции
    public String add_if_min(HumanBeing humanBeing) {
        final int counter = 0;
        //String info = linkedList.stream().filter(human -> human.getId().compareTo())

        if (counter == linkedList.size()) {
            linkedList.add(humanBeing);
            return "Элемент меньше остальных и был добавлен";
        } else {
            return "Элемент не минимален.";
        }
    }

    //перемешать элементы коллекции в случайном порядке
    public String shuffle() {
        if (linkedList.size() > 0) {
            Collections.shuffle(linkedList);

            String info = linkedList.stream().map(CollectionUtils::display).collect(Collectors.joining(", ")    );
            if (info.equals("")) { info = "На данный момент коллекция пуста."; }
            return info + "\n Коллекция перетасована.";
        } else { return "Коллекция пуста."; }
    }

    //удаление одного любого по заданной скорости
    public String remove_any_by_impact_speed(float impactSpeed) {
        //String info = linkedList.stream().filter(human -> human.getImpactSpeed().compareTo(human) == 0 )
        for (int i = 0; i < linkedList.size(); i++) {
            if (linkedList.get(i).getImpactSpeed().compareTo(impactSpeed) == 0) {
                linkedList.remove(linkedList.get(i));
                return "Элемент удалён.";
            }
        }
        return "Элементов с заданной скоростью нет.";
    }

    //вывести любой объект из коллекции, значение поля creationDate которого является максимальным
    public String max_by_creation_date() {
        if (linkedList.size() > 0) {
            return CollectionUtils.display(Collections.max(linkedList,
                    Comparator.comparing(HumanBeing::getCreationDate))) + "Это максимальный элемент с creationDate";
        } else { return "Коллекция пуста."; }
    }

    //вывести значения поля weaponType всех элементов в порядке возрастания
    public String print_field_ascending_weapon_type() {
        linkedList.sort(Comparator.comparing(HumanBeing::getWeaponType));  // Сортировка коллекции по алфавиту.

        String info = linkedList.stream().map(CollectionUtils::display).collect(Collectors.joining(", ")    );
        if (info.equals("")) { info = "На данный момент коллекция пуста."; }
        return info;
    }

    public void appendToList(Object o){
        res.add(o);
    }

}
