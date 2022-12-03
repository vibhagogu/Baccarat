import java.io.*; 
import java.util.*;
public class GV_PD_BaccaratGame {
		static ArrayList<Card> playerHand = new ArrayList<Card>(); 
        static ArrayList<Card> bankerHand = new ArrayList<Card>(); 
		static Card player3rdCard = new Card("Hearts", -1) ; //initialize random and impossible value for card so it can be replaced later 
		static Card banker3rdCard;
		static Dealer dealer = new Dealer();
		static Scanner cin = new Scanner(System.in); 
		static int BankBalance = 500; 
		static int BetValue; 
		static String BetOn; 
		static int option; 
	public static void main (String[] args) throws FileNotFoundException {
		do {
			System.out.println("\n1.Rules\n2.Play a Round of Baccarat\n3.Check Bank Balance available to bet\n0.Exit");
			option = cin.nextInt(); 
			switch (option) {
				case 1: readRules(); break;
				case 2: if (checkGameOver()) {System.out.print("You no longer have enough money to bet and play."); break;} gamePlay(); break; 
				case 3: System.out.print("You have " + readBank() + " in the bank"); break;
				case 0: System.out.println("Thanks for playing! Your total earnings are: " + BankBalance); break; 
				default: System.out.print("Bad input");
			} //switch
		} while (option != 0); 

		System.out.println("Do you want to reset bank balance history for the next time you play? (Y/N)");
		String playagain = cin.next().toUpperCase(); 
		if (playagain.equals("Y")) {
			PrintWriter writer = new PrintWriter("BankAccount.txt");
			writer.print("500");
			writer.close();
			System.out.println("It's reset!");
		} //if 
	} //main 

	static void readRules() {  //Reads rules from .txt file 
		Scanner fin = null;
		String filename = "BaccaratRules.txt";
		try {
			fin = new Scanner(new File(filename));
		}
		catch(FileNotFoundException ex){System.out.println("File not found");}
		while (fin.hasNext()){
			String newLine = fin.nextLine(); 
			System.out.println(newLine);
		} //while 
		fin.close();
	} //readRules

	static int readBank () { //current amount of money in bank 
		Scanner fin = null;
		String filename = "BankAccount.txt";
		List<String> Lines=new ArrayList<>(); //Create List of all the lines in BankAccount.txt 
		try {
			fin = new Scanner(new File(filename));
		}
		catch(FileNotFoundException ex){System.out.println("File not found");}
		while (fin.hasNext()){
			Lines.add(fin.nextLine());
		} //while 
		fin.close();
		String LastLine = Lines.get(Lines.size()-1); //read last value in file, which is current balance 
		return (Integer.parseInt(LastLine));
	}

	static void BankDeposit() { //Updates BankAcount.txt by adding money 
		System.out.println("You won the bet");
		try (FileWriter f = new FileWriter("BankAccount.txt", true);
			BufferedWriter b = new BufferedWriter(f);
			PrintWriter p = new PrintWriter(b);) {

			p.println("\nYou won your bet! Deposit +" + BetValue);
			p.println("Bank Balance: ");
			p.println((BankBalance + BetValue));

		} catch ( IOException ex ) { System.out.print(ex);}
	} //BankDeposit

	static void BankTransaction() { //Updates BankAccount.txt by removing money 
		System.out.println("You lost the bet");
		try (FileWriter f = new FileWriter("BankAccount.txt", true);
			BufferedWriter b = new BufferedWriter(f);
			PrintWriter p = new PrintWriter(b);) {

			p.println("\nYou lost your bet. Transact -" + BetValue);
			p.println("Bank Balance: ");
			p.println((BankBalance - BetValue));

		} catch ( IOException ex ) { System.out.print(ex);}
	} //BankDeposit

	static void makeBets() { //The User makes bets on who they think will win and How much they want to bet
		BankBalance = readBank(); 
		System.out.println("Make a Bet: Player, Banker, Tie");
		BetOn = cin.next().toUpperCase();
		System.out.println("How much do you want to bet?");
		BetValue = cin.nextInt(); 
		if (BetValue > BankBalance) {
			System.out.println("Insufficient Funds. Bet a lower amount");
			BetValue = cin.nextInt(); 
		}//if 
	} //makeBets 

	static void checkBets(){ //Check if the User bet right and then call functions to either lose or win money
		if (BetOn.equals(dealer.theWinner(playerHand, bankerHand).toUpperCase())) {
			BankDeposit();
		} 
		else if (!(BetOn.equals(dealer.theWinner(playerHand, bankerHand).toUpperCase()))){
			BankTransaction();
		}	
		else {
			System.out.println("Can't compare properly");
		}
	} //checkBets

	static boolean checkGameOver() { // If there is no money left in bank account, user cannot play 
		BankBalance = readBank();
		if (BankBalance == 0) {
			return true;
		} 
		return false;
	} //checkGameOver


	// ** Main Game Method ** //
	static void gamePlay() { 
		makeBets(); 
		playerHand = dealer.dealCard();
		bankerHand = dealer.dealCard();
		System.out.println("The Player's cards are: " + playerHand);
		System.out.println("The Banker's cards are: " + bankerHand);
		System.out.println("The Player's total is: " + dealer.cardTotal(playerHand)); //Sums up points 
		System.out.println("The Banker's total is: " + dealer.cardTotal(bankerHand));
		if (dealer.cardTotal(playerHand) == 8 || dealer.cardTotal(playerHand) == 9 || dealer.cardTotal(bankerHand) == 8 || dealer.cardTotal(bankerHand) == 9) {
			System.out.println("The Winner is: " + dealer.theWinner(playerHand, bankerHand)); //Automatic win if eithr participant gets 8 or 9
		} 
		else {
			
			if(dealer.drawPlayer(playerHand) == true) { //Check if Player should draw another card and adds it to their hand and calculates points
				player3rdCard = dealer.dealOne();
				playerHand.add(player3rdCard);
				System.out.println("The Player's final hand is: " + playerHand + " with a total of " + dealer.cardTotal(playerHand) + " points");
			}
			else 
				System.out.println("Player was not dealt a third card");

			if(dealer.drawBanker(bankerHand, player3rdCard) == true) { //Check if Banker should draw another card and adds it to their hand and calculates points 
				banker3rdCard = dealer.dealOne();
				bankerHand.add(banker3rdCard);
				System.out.println("The Banker's final hand is: " + bankerHand + " with a total of " + dealer.cardTotal(bankerHand) + " points");
			} 
			else {
				System.out.println("Banker was not dealt a third card.");
			}

			System.out.println("The Winner is: " + dealer.theWinner(playerHand, bankerHand)); //announces winner
		} //else
		checkBets();
	} //gamePlay()


} //class Baccarat

class Card{
    int val;
    String suit;

    Card(String suit, int val){
        this.val = val;
        this.suit = suit;
    } // constructor

    // method
    public String toString(){return val + " " + suit;}
} //class Card

class Dealer {
	ArrayList<Card> deck; //Create a deck of cards

	//this method populates the deck with 52 cards, in order
	public void generateDeck() { //Generates and shuffles a deck of cards 
		deck = new ArrayList<Card>();
		for(int i = 1; i <= 13; i ++) {
			deck.add(new Card("Hearts", i));
		}
		for(int i = 1; i <= 13; i ++) {
			deck.add(new Card("Spades", i));
		}
		for(int i = 1; i <= 13; i ++) {
			deck.add(new Card("Clubs", i));
		}
		for(int i = 1; i <= 13; i ++) {
			deck.add(new Card("Diamonds", i));
		} 
		Collections.shuffle(deck);
	}//generateDeck

	public ArrayList<Card> dealCard() {  //this method deals two cards for each Player
		ArrayList<Card> hand = new ArrayList<Card>();
		hand.add(deck.remove(0)); //take 1st card from Deck and add it to Player's list
		hand.add(deck.remove(0)); //2nd card
		return hand;
	} //dealCard

	public Card dealOne() {
		return deck.remove(0);
	} //remove card from deck, return 3rd card

	public int cardTotal(ArrayList<Card> hand) {  //Calculates points 
		int sum = 0;
		for (int i = 0;i < hand.size(); i++) {
			if (hand.get(i).val < 10) {
				sum += hand.get(i).val;
			} //if
		} //for
		if (sum > 9) {
			sum = sum - 10;
		} //make sure the total is not greater than 9
		return sum;
	} //cardTotal

	public boolean drawPlayer(ArrayList<Card> hand) { //evaluates if Player should draw
		if(cardTotal(hand) < 6) {
			return true;
		} 
		return false;
	} //drawPlayer


	public boolean drawBanker(ArrayList<Card> hand, Card thirdCard) { //evaluates if Banker should draw
		if (thirdCard.val == -1) { //Banker draws a third card if Player didn't draw a third card and Banker's total is from 0-5.
			if (cardTotal(hand) < 6) {
				return true;
			} //inner loop
			return false;
		} 
		else { //Other cases when the banker can be dealt another card 
			if(thirdCard.val == 2 || thirdCard.val == 3 && cardTotal(hand) < 5) {
				return true;
			} 
			else if (thirdCard.val == 4 || thirdCard.val == 5 && cardTotal(hand) < 6) {
				return true;
			}
			else if (thirdCard.val == 6 || thirdCard.val == 7 && cardTotal(hand) < 7) {
				return true;
			}
			else if (thirdCard.val == 8 && cardTotal(hand) < 3) {
				return true;
			}
			else if (thirdCard.val > 8 || thirdCard.val == 1 && cardTotal(hand) < 4) {
				return true;
			} //last else if
			else { return false; }
		} //else
	} //drawBanker

	public String theWinner(ArrayList<Card> player, ArrayList<Card> banker) {  //decides winner 
		String winner;
		if (cardTotal(player) > cardTotal(banker)) {
			winner = "Player"; 
		} else if (cardTotal(banker) > cardTotal(player)) {
			winner = "Banker";
		} else {
			winner = "Tie";
		} 
		return winner;
	} //theWinner

	Dealer() { //dealer constructer 
		generateDeck();
	} 
}
