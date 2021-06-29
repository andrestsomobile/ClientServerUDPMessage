
import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;


import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.util.HashMap;
import java.util.Map;

public class ReceiverListener extends Thread {

    protected DatagramSocket socket = null;
    protected boolean running;
    protected byte[] buf = new byte[256];

    public static void main(String[] args) throws Exception {
        ReceiverListener e = new ReceiverListener();
        e.run();
    }

    public ReceiverListener() throws IOException {
        System.out.println("INIT LISTENER SERVER");
        socket = new DatagramSocket(4445);
    }

    public void run() {
        running = true;

        while (running) {
            try {
                DatagramPacket packet = new DatagramPacket(buf, buf.length);
                socket.receive(packet);
                InetAddress address = packet.getAddress();
                int port = packet.getPort();
                packet = new DatagramPacket(buf, buf.length, address, port);
                String received = new String(packet.getData(), 0, packet.getLength());
                System.out.println("messageReceivedFromClient " + received);
                
                String messageToClient = "ServerMessage";
                byte[] buf = messageToClient.getBytes();
                packet
                        = new DatagramPacket(buf, buf.length, address, port);
                System.out.println("result " + result + " address client " + address + " port " + port);
                socket.send(packet);
            } catch (IOException e) {
                e.printStackTrace();
                running = false;
            }
        }
        socket.close();
    }
}
