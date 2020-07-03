package ClientDealer;


import Commands.SerializedCommands.SerializedMessage;

public class Translating { //Переводит сообщение с сервера на русском в консоль
    static void translate(Object o) {
        if (o instanceof SerializedMessage) {
            System.out.println(((SerializedMessage) o).getMessage());
        }
    }
}