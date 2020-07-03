package BasicClasses;


import Collection.IDGenerator;

import java.io.Serializable;
import java.time.ZonedDateTime;
import java.util.ArrayList;

public class HumanBeing implements Comparable<HumanBeing>, Serializable {
    private long id; //Значение поля должно быть больше 0, Значение этого поля должно быть уникальным, Значение этого поля должно генерироваться автоматически
    private String name; //Поле не может быть null, Строка не может быть пустой
    private Coordinates coordinates; //Поле не может быть null
    private ZonedDateTime creationDate; //Поле не может быть null, Значение этого поля должно генерироваться автоматически
    private Boolean realHero; //Поле не может быть null
    private boolean hasToothpick;
    private float impactSpeed; //Поле не может быть null
    private String soundtrackName; //Поле не может быть null
    private WeaponType weaponType; //Поле может быть null
    private Mood mood; //Поле может быть null
    private Car car; //Поле не может быть null

    //setters
    public void setId(long id) {
        this.id = id;
    }
    public void setName(String name) {
        this.name = name;
    }
    public void setCoordinates(Coordinates coordinates) { this.coordinates = coordinates; }
    public void setCoordinates(double parseDouble, Double parseDouble1) { new Coordinates(parseDouble, parseDouble1);}
    public void setCreationDate(ZonedDateTime creationDate) { this.creationDate = creationDate; }
    public void setRealHero(Boolean realHero) { this.realHero = realHero; }
    public void setHasToothpick(boolean hasToothpick) { this.hasToothpick = hasToothpick; }
    public void setImpactSpeed(float impactSpeed) { this.impactSpeed = impactSpeed; }
    public void setSoundtrackName(String soundtrackName) { this.soundtrackName = soundtrackName; }
    public void setWeaponType(WeaponType weaponType) {
        this.weaponType = weaponType;
    }
    public void setMood(Mood mood) { this.mood = mood; }
    public void setCar(Car car) {
        this.car = car;
    }

    //getters
    public Long getId() {
        return id;
    }
    public String getName() {
        return name;
    }
    public Coordinates getCoordinates() {
        return coordinates;
    }
    public ZonedDateTime getCreationDate() {
        return creationDate;
    }
    public Boolean getRealHero() {
        return realHero;
    }
    public boolean getHasToothpick() {
        return hasToothpick;
    }
    public Float getImpactSpeed() {
        return impactSpeed;
    }
    public String getSoundtrackName() {
        return soundtrackName;
    }
    public WeaponType getWeaponType() {
        return weaponType;
    }
    public Mood getMood() {
        return mood;
    }
    public Car getCar() {
        return car;
    }

    public HumanBeing(String name, Coordinates coordinates, Boolean realHero, Boolean hasToothpick, Float impactSpeed, String soundtrackName, WeaponType weaponType, Mood mood, Car car) {
        this.id = IDGenerator.generateID();
        this.name = name;
        this.coordinates = coordinates;
        this.creationDate = ZonedDateTime.now();
        this.hasToothpick = hasToothpick;
        this.realHero = realHero;
        this.impactSpeed = impactSpeed;
        this.soundtrackName = soundtrackName;
        this.weaponType = weaponType;
        this.mood = mood;
        this.car = car;
    }

    public HumanBeing(String name, Coordinates coordinates, ZonedDateTime time, Boolean realHero, Boolean hasToothpick, Float impactSpeed, String soundtrackName, WeaponType weaponType, Mood mood, Car car) {
        this.id = IDGenerator.generateID();
        this.name = name;
        this.coordinates = coordinates;
        this.creationDate = time;
        this.realHero = realHero;
        this.hasToothpick = hasToothpick;
        this.impactSpeed = impactSpeed;
        this.soundtrackName = soundtrackName;
        this.weaponType = weaponType;
        this.mood = mood;
        this.car = car;
        //name + coordinates + time + realHero + hasToothpick + impactSpeed + soundtrackName + weaponType + mood + car
    }

    @Override
    public int compareTo(HumanBeing humanBeing) {
        return (int) (this.id - humanBeing.getId());
    }

    public String[] toArray() {
        ArrayList<String> array = new ArrayList<>(getStringValues(
                name,
                coordinates.getX(),
                coordinates.getY(),
                creationDate,
                realHero,
                hasToothpick,
                impactSpeed,
                soundtrackName,
                weaponType,
                mood,
                car.getName(),
                car.getCool()
        ));
        String[] result = new String[array.size()];
        result = array.toArray(result);
        return result;
    }

    private ArrayList<String> getStringValues(Object ... objects) {
        ArrayList<String> array = new ArrayList<>();
        for (Object object : objects) {
            if (object == null) array.add("");
            else array.add(String.valueOf(object));
        }
        return array;
    }


}
