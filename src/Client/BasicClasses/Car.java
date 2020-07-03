package BasicClasses;

import java.io.Serializable;

public class Car implements Serializable {
    private String name; //Строка не может быть пустой, Поле не может быть null
    private Boolean cool; //Поле не может быть null

    public Car(String name, Boolean cool) {
        this.name = name;
        this.cool = cool;
    }

    public String getName() {
        return name;
    }

    public Boolean getCool() {
        return cool;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this) return true;
        if (!(obj instanceof Car)) {
            return false;
        }

        Car car = (Car) obj;
        return name.equals(car.getName()) &&
                cool.equals(car.getCool());
    }
}
