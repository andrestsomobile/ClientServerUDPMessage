import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.*;
import java.net.DatagramSocket;

public class ClientMessage {

    public static void main(String[] args) throws Exception {
        ClientMessage e = new ClientMessage();
        e.sendEcho("messagetoserver");
    }

    private DatagramSocket socket;
    private InetAddress address;

    private byte[] buf;

    public ClientMessage() throws SocketException, UnknownHostException {
        socket = new DatagramSocket();
        address = InetAddress.getByName("hostserver");
    }

    public String sendEcho(String msg) throws Exception {
        buf = msg.getBytes();
        DatagramPacket packet
                = new DatagramPacket(buf, buf.length, address, 4445);
        socket.send(packet);
        packet = new DatagramPacket(buf, buf.length);
        socket.receive(packet);

        String received = new String(
                packet.getData(), 0, packet.getLength());

        System.out.print("return message from server " + received);
        return received;
    }

    public void close() {
        socket.close();
    }
}
