package durakClient;
import durakClient.Card.Rank;
import durakClient.Card.Suit;
import java.io.*;
import java.nio.*;
import java.net.*;
import java.util.*;

class GameClient{
ArrayList<Card> clientHand;

    public static void main(String[] args){
    ArrayList<Card> clientHand = new ArrayList<Card>();
    
    try {

        Scanner s = new Scanner(System.in);
        System.out.print("What is the IP for the Duran game you want to connect to?: ");
        String serverIP = s.nextLine();

        Socket server = new Socket(serverIP, 1337);

        BufferedReader inFromServer = new BufferedReader(new InputStreamReader(server.getInputStream()));
        DataOutputStream outToServer = new DataOutputStream(server.getOutputStream());
        
        String message = inFromServer.readLine();
        System.out.println(message);
        outToServer.writeBytes(message + "\n");
        String incomingCard;
        String tempRank;
        String tempSuit;
        for (int i = 0; i<6; i++) {
            incomingCard = inFromServer.readLine();
            System.out.println(incomingCard);
            if (incomingCard != null)
            {
                StringTokenizer cardTokens = new StringTokenizer(incomingCard);
                tempRank = cardTokens.nextToken();
                cardTokens.nextToken();
                tempSuit = cardTokens.nextToken();;
                clientHand.add(new Card(tempRank,tempSuit));
            }
        }
        
        
        System.out.println("hi");
        for (int j = 0; j<clientHand.size();j++){
            System.out.println(clientHand.get(j));
            }
            
            
        while(true){
        }
        
        
        }
        catch (Exception e){
        System.out.println(e);
        
        }

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