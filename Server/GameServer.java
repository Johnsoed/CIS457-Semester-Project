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
     Card tempCard;   
        try {
        
        int port = 1337;
        ServerSocket welcomeSocket = new ServerSocket(port);

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
                tempCard = (gameDeck.deal());
                String cardMessage = tempCard.toString();
                outToClient.writeBytes(cardMessage + '\n');
            }
            outToClient.writeBytes("EOF\n");
           Card trumpCard = gameDeck.deal();
           Suit trumpSuit = trumpCard.suit();
            
           System.out.println("the trump card was " + trumpCard);
           System.out.println("the trump suit is " + trumpCard.suit());
           outToClient.writeBytes("the trump suit is " + trumpCard.suit() + "\n");
           
            
           Boolean inGame = true;
           Boolean serverAttack = true;
           String cardCommand;
           Scanner s = new Scanner(System.in);
           String incomingCard;
           Card serverCard = generateCard("ACE of SPADES");
           Card clientCard = generateCard("ACE of SPADES");
           Boolean cardPicked;
           Boolean attackerWin = false;
           String winnerString = "";
           String winnerDisplay;
           Boolean deckHasCards;
           String clientSizeString;
           Boolean firstTime = true;
           Boolean serverWin = false;
           String gameOverString;
           int clientHandSize = 0;
           

           
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

            cardPicked = false;
            incomingCard = inFromClient.readLine();
            System.out.println("Client played: " + incomingCard);
            clientCard = generateCard(incomingCard);
            System.out.println("you are defending, select your card by entering in name exactly as displayed");
            
            cardCommand = s.nextLine();
            while(cardPicked == false){
                for (int j = 0; j<serverHand.size();j++){
                    if (cardCommand.equalsIgnoreCase(serverHand.get(j).toString())){
                        serverCard = serverHand.get(j);
                        serverHand.remove(j);
                        cardPicked = true;
                    }
                }
                if(cardPicked == false){
                System.out.println("wrong name or card not in hand");
                cardCommand = s.nextLine();
                }
            }
            
            }
            
            
            serverWin = determineWinner(serverCard, clientCard, serverAttack, trumpSuit);

            
            
            
            attackerWin = checkAttackWin(serverWin, serverAttack);
            
            if(serverWin == true){
                winnerString = "Server wins";
            }
            else{
                winnerString = "Client wins";
            }
            
            winnerDisplay = ("Server's " + serverCard + " vs " + "Client's " + clientCard + 
            ": " + winnerString);
            System.out.println(winnerDisplay);
            outToClient.writeBytes(winnerDisplay + "\n");
            outToClient.writeBytes(winnerString + "\n");
            
            if(serverWin == false && serverAttack == false){
                serverHand.add(serverCard);
                serverHand.add(clientCard);
            
            }
            
            
            deckHasCards = (gameDeck.deckCount() > 0);
            
            if (deckHasCards==false && firstTime == true){
                System.out.println("no more cards in deck");
                firstTime = false;
            }
            
            
            if(serverHand.size()<6 && deckHasCards){
                tempCard = gameDeck.deal();
                serverHand.add(tempCard);
            }
            
            
            clientSizeString = inFromClient.readLine();
            clientHandSize = Integer.parseInt(clientSizeString);
            
            
            deckHasCards = (gameDeck.deckCount() > 0);
            
            
            if (deckHasCards==true){
                outToClient.writeBytes("no\n");
            }
            else{
                outToClient.writeBytes("yes\n");
            }
            
            if(clientHandSize<6 && deckHasCards == true){
                tempCard = gameDeck.deal();
                outToClient.writeBytes(tempCard.toString()+"\n");
            }
            
        
            if(attackerWin == false){
                System.out.println("switching");
                outToClient.writeBytes("switch\n");
                serverAttack = !serverAttack;
            }
            else{
                outToClient.writeBytes("no switch\n");
            }
            
            System.out.println(gameDeck.deckCount());
            
            
            if(serverHand.size()== 0 || clientHandSize == 0){
                inGame = false;
                outToClient.writeBytes("game over\n");
                }
            else{
                outToClient.writeBytes("game continues\n");
                }
                
            
            }
            
            System.out.println(serverHand.size());
            System.out.println(clientHandSize);
            gameOverString = findWinner(serverHand.size(), clientHandSize, serverWin);
            outToClient.writeBytes(gameOverString + "\n");
            System.out.println(gameOverString);
        
    }
            
            catch(Exception e){
                System.out.println(e);
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
    
  private static Boolean checkAttackWin(Boolean serverWin, Boolean serverAttack){
        if(serverWin == true){
            if (serverAttack == true){
                return true;
                }
            else{
                return false;
                }
            }
        if(serverWin == false){
            if (serverAttack == true){
                return false;
                }
            else{
                return true;
                }
            }
        return false;
    }
    
    
    
    private static String findWinner(int serverHandSize, int clientHandSize, Boolean serverWin){
        if(serverHandSize ==0 && clientHandSize == 0){
            if (serverWin == true){
                return "Server wins the game!";
                }
            else{
                return "Client wins the game!";
                }
            }
            if(serverHandSize == 0 && clientHandSize > 0){
                return "Server wins the game!";
                }
            if(clientHandSize == 0 && serverHandSize >0){
                return "Client wins the game!";
                }
            return "something went wrong";
    }
    
    
    
    
}
