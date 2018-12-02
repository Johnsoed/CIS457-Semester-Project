package durakServer;
import java.io.*;
import java.nio.*;
import java.net.*;
import java.util.*;
import durakServer.Card.Suit;
import durakServer.Card.Rank;



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

            
            
            for (int j = 0; j<6; j++){
                Card tempCard = (gameDeck.deal());
                String cardMessage = tempCard.toString();
                outToClient.writeBytes(cardMessage + '\n');
            }
            outToClient.writeBytes("EOF\n");
           Card trumpCard = gameDeck.deal();
           Suit trumpSuit = trumpCard.suit();
            
           System.out.println("the trump card was " + trumpCard);
           System.out.println("the trump suit is " + trumpCard.suit());
            
           Boolean inGame = true;
           Boolean serverAttack = true;
           String cardCommand;
           Scanner s = new Scanner(System.in);
           String incomingCard;
           Card serverCard = generateCard("ACE of SPADES");
           Card clientCard = generateCard("ACE of SPADES");
           Boolean cardPicked;
           Boolean serverWin;
           Boolean attackerWin;
            while(inGame == true){
            
            for (int j = 0; j<serverHand.size();j++){
            System.out.println(serverHand.get(j));
            }
            
            
            if(serverAttack == true){
            cardPicked = false;
            System.out.println("you are attacking, select your card by entering in name exactly as displayed");
            cardCommand = s.nextLine();
            while(cardPicked == false){
                for (int j = 0; j<serverHand.size();j++){
                    if (cardCommand.equalsIgnoreCase(serverHand.get(j).toString())){
                        serverCard = serverHand.get(j);
                        
                        serverHand.remove(j);;
                        cardPicked = true;
                    }
                }
                if(cardPicked == false){
                System.out.println("wrong name or card not in hand");
                cardCommand = s.nextLine();
                }
            }
            
            outToClient.writeBytes(serverCard.toString() + "\n");
            incomingCard = inFromClient.readLine();
            clientCard = generateCard(incomingCard);
            System.out.println("client card received: " + clientCard);
            
            
            
            
            }
            
            
            
            if(serverAttack == false){
            System.out.println("you are defending");
            
            }
            
            
            serverWin = determineWinner(serverCard, clientCard, serverAttack, trumpSuit);
            System.out.println(serverWin);
            
            
            
            
            }
            

            
            
            
            }
        }
            
            catch(Exception e){
                System.out.println(e);
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
    
    
    private static Card generateCard(String cardString){
            StringTokenizer cardTokens = new StringTokenizer(cardString);
            String tempRank = cardTokens.nextToken();
            cardTokens.nextToken();
            String tempSuit = cardTokens.nextToken();
            Card tempCard = new Card(tempRank,tempSuit);
            return tempCard;
    
    }    
    
    
    private static Boolean determineWinner(Card servCard, Card clieCard, Boolean serverAttack, Suit trumpSuit){
        if(servCard.suit() == trumpSuit && clieCard.suit() == trumpSuit){
            if (servCard.getScore() == clieCard.getScore()){
                if (serverAttack == true){
                    return true;
                }
               else {
                    return false;
               }            
            }
            if(servCard.getScore()>clieCard.getScore()){
            return true;
            }
            else{
            return false;
            }
        
        }
        if(servCard.suit()==trumpSuit && clieCard.suit() != trumpSuit){
            return true;
        }
        if(clieCard.suit()==trumpSuit && servCard.suit()!=trumpSuit){
            return false;
        }
        
        if (servCard.getScore() == clieCard.getScore()){
                if (serverAttack == true){
                    return true;
                }
               else {
                    return false;
               }            
            }
        
        if(servCard.getScore()>clieCard.getScore()){
            return true;
        }
        else{
            return false;
            }
        
    

    
    }   
    
    
}
