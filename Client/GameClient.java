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
        String winnerDisplay;
        String winString;
        int clientHandSize;
        String clientSizeString;
        Boolean clientWin = false;
        String deckEmptyString;
        Boolean deckEmpty = false;
        String switchString;
        String endGameString;
        String gameOverMessage;
        incomingCard = "";
        while(!"EOF".equals(incomingCard)){
            incomingCard = inFromServer.readLine();
            if (incomingCard != null && (!"EOF".equals(incomingCard))){
                clientHand.add(generateCard(incomingCard));
            }
        }
        
        
         System.out.println(inFromServer.readLine());
         Boolean inGame = true;
         Boolean clientAttack = false;
         Card clientCard = generateCard("ACE of SPADES");
         Card serverCard = generateCard("ACE of SPADES");
         Boolean cardPicked;;
         String cardCommand;
            
            
            
        while(inGame == true){
        for (int j = 0; j<clientHand.size();j++){
            System.out.println(clientHand.get(j));
            }
        
        
        
        if(clientAttack == false){
            cardPicked = false;
            incomingCard = inFromServer.readLine();
            serverCard = generateCard(incomingCard);
            System.out.println("Server played: " + incomingCard);
            System.out.println("you are defending, select your card by entering in name exactly as displayed");
            
            cardCommand = s.nextLine();
            while(cardPicked == false){
                for (int j = 0; j<clientHand.size();j++){
                    if (cardCommand.equalsIgnoreCase(clientHand.get(j).toString())){
                        clientCard = clientHand.get(j);
                        clientHand.remove(j);
                        cardPicked = true;
                    }
                }
                if(cardPicked == false){
                System.out.println("wrong name or card not in hand");
                cardCommand = s.nextLine();
                }
            }

        }
        
        if(clientAttack == true){
            cardPicked = false;
            System.out.println("you are attacking, select your card by entering in name exactly as displayed");
            cardCommand = s.nextLine();
            while(cardPicked == false){
                for (int j = 0; j<clientHand.size();j++){
                    if (cardCommand.equalsIgnoreCase(clientHand.get(j).toString())){
                        clientCard = clientHand.get(j);
                        
                        clientHand.remove(j);;
                        cardPicked = true;
                    }
                }
                if(cardPicked == false){
                System.out.println("wrong name or card not in hand");
                cardCommand = s.nextLine();
                }
            }
            
        }
        
        outToServer.writeBytes(clientCard.toString() + "\n");
        winnerDisplay = inFromServer.readLine();
        System.out.println(winnerDisplay);
        winString = inFromServer.readLine();
        if(winString.equals("Server wins")){
            clientWin = false;
        }
        if(winString.equals("Client wins")){
            clientWin = true;
        }
        
        if(clientWin == false && clientAttack == false){
            clientHand.add(clientCard);
            clientHand.add(serverCard);
        }
        
        clientHandSize = clientHand.size();
        clientSizeString = Integer.toString(clientHandSize);
        outToServer.writeBytes(clientSizeString + "\n");
        
        deckEmptyString = inFromServer.readLine();
        if (deckEmptyString.equals("yes") && deckEmpty == false){
            System.out.println("no more cards in deck");
            deckEmpty = true;
            }
        
        if(clientHandSize<6 && deckEmpty == false){
            incomingCard = inFromServer.readLine();
            clientHand.add(generateCard(incomingCard));
        }
        
        
        switchString = inFromServer.readLine();
        if(switchString.equals("switch")){
            System.out.println("switching");
            clientAttack = !clientAttack;
            }
            
            endGameString = inFromServer.readLine();
            
            if(endGameString.equals("game over")){
                inGame = false;
                }
        
        
        }
            gameOverMessage = inFromServer.readLine();
            System.out.println(gameOverMessage);
        
        
        }
        catch (Exception e){
        System.out.println(e);
        System.out.println(e.getStackTrace());
        
        }
        
        
        
        
    }
    
    private static Card generateCard(String cardString){
            StringTokenizer cardTokens = new StringTokenizer(cardString);
            String tempRank = cardTokens.nextToken();
            cardTokens.nextToken();
            String tempSuit = cardTokens.nextToken();
            Card tempCard = new Card(tempRank,tempSuit);
            return tempCard;
    
    }
    
    
}
