import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.net.ServerSocket;
import java.net.Socket;

public class serverTest implements Serializable {

    public static  final  int port = 3090;

    public static void main(String[] args) throws IOException, ClassNotFoundException {

        new serverTest();
    }

    public serverTest() throws IOException, ClassNotFoundException {
        ServerSocket serverSocket = new ServerSocket(port);
        System.out.println("waiting for client..");
        Socket socket = serverSocket.accept();
        System.out.println("Client connected");

        ObjectInputStream in = new ObjectInputStream(socket.getInputStream());
        ObjectOutputStream out = new ObjectOutputStream(socket.getOutputStream());

        Packet recPacket = (Packet)in.readObject();
//        Packet.PlayerDetails playerDetails = recPacket.new PlayerDetails();
//        if(playerDetails == null){
//            System.out.println("details null");
//        }
//        else{
//            System.out.println("Client " + playerDetails.getPlayerName() + "is here");
//        }
//        System.out.println("packet recieved");
//
//        System.out.println(recPacket.getIpAddress() + recPacket.getPortNum());

        if(recPacket.getIpAddress().equals("127.0.0.1")  && recPacket.getPortNum() == 3090){

//            System.out.println("Client " + playerDetails.getPlayerName() + "is here");
            Packet packet = new Packet("127.0.0.1", port);
                packet.setWinnerMsg("Banker wins ");
                out.writeObject(packet);
                System.out.println("Packet sent to client");

        }
        serverSocket.close();

    }
}
