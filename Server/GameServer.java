package durakServer;
import java.io.*;
import java.nio.*;
import java.net.*;
import java.util.*;


class GameServer {
ArrayList<Card> serverHand;


    public static void main(String[] args){
    ArrayList<Card> serverHand = new ArrayList<Card>();
        
        try {
        
        int port = 1337;
        ServerSocket welcomeSocket = new ServerSocket(port);

        while (true){
            Socket clientSocket = welcomeSocket.accept();

            BufferedReader inFromClient = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));

            DataOutputStream outToClient = new DataOutputStream(clientSocket.getOutputStream());
            
            outToClient.writeBytes("hi" + "\n");
            String message = inFromClient.readLine();
            System.out.println(message);
            
            Deck gameDeck = new Deck();
            for (int i = 0; i<6; i++){
            serverHand.add(gameDeck.deal());
            }
            for (int j = 0; j<serverHand.size();j++){
            System.out.println(serverHand.get(j));
            }
            
            
            for (int j = 0; j<6; j++){
            Card tempCard = (gameDeck.deal());
            String cardMessage = tempCard.toString();
            outToClient.writeBytes(cardMessage + '\n');
            }
            outToClient.writeBytes(null);
            

            
            
            
            }
        }
            
            catch(Exception e){
                System.out.print("something went wrong");
            }

            /*TODO This is where the code for setting up the game and geting ready to play will go.
              Not sure how we want to implement it yet.*/

            //1. Generate a deck for use in the game

            //2. Send client its hand

            //3. while (inGame) {

                //4. Send client if attacking or defending

                //5. recieve card from client and compare to server's

                //6. ask client if they need cards and send them if they are needed

                //7. determine a round winner and looser

                //8. check to see if either player is out of cards, if yes inGame = false, else repeat
            
            //}


        
    }

}
