package ClientDealer;


import Commands.SerializedCommands.SerializedMessage;

public class Translating { //��������� ��������� � ������� �� ������� � �������
    static void translate(Object o) {
        if (o instanceof SerializedMessage) {
            System.out.println(((SerializedMessage) o).getMessage());
        }
    }
}