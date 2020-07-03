package BasicClasses;

import java.io.Serializable;

public class Coordinates implements Serializable {
    private double x;
    private Double y; //Поле не может быть null

    public Coordinates(double x, Double y) {
        this.x = x;
        this.y = y;
    }

    public double getX() {
        return x;
    }
    public Double getY() {
        return y;
    }

    public void setX(double x) {
        this.x = x;
    }
    public void setY(Double y) {
        this.y = y;
    }
}
