package ClientDealer;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;

public class Sender {
    private final SocketChannel socketChannel;

    public Sender(Session session) {
        this.socketChannel = session.getSocketChannel();
    }

    public void sendObject(Object serializedObject) throws IOException {
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        ObjectOutputStream oos = new ObjectOutputStream(bos);

        oos.writeObject(serializedObject);
        byte [] data = bos.toByteArray();
        socketChannel.write(ByteBuffer.wrap(data));
    }
}