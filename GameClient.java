import java.io.*;
import java.nio.*;
import java.net.*;
import java.util.*;

public class GameClient{

    public static void main(String[] args){

        Scanner s = new Scanner(System.in);
        System.out.println("What is the IP for the Duran game you want to connect to?\n");
        String serverIP = s.nextLine();

        Socket server = new Socket(serverIP, 1337);

        BufferedReader inFromServer = new BufferedReader(new InputStreamReader(server.getInputStream()));
        DataOutputStream outToServer = new DataOutputStream(server.getOutputStream());

        /* TODO client game logic*/

        //1. Get hand from server

        //while(true){

        //2. get turn command from the server to determine who is attacking and who is defending

        //3. send chosen card back to the server

        //4. draw card from server deck if hand is less than 6

        //4. wait for round won or lost

        //} (repeat)
    }
}