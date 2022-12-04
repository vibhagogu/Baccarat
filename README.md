# Baccarat
Fun card game of Baccarat created to show my understanding of data structures for my CSC212 Data Structures class. 

Date: 5/11/21
Course: CSC212 Data Structures
Files: GV_PD_BaccaratGame.java, BankAccount.txt, BaccaratRules.txt
Software: Java, tested in Visual Studio Code on MacOS

Abstract: 
This is a casino card game called Baccarat. In this game, there are two participants: the Player and the Banker. The objective of the game is to get as close to the point value of 9. 
The cards 2-9 are equaivalent to their face value. The cards 10, J (11), Q (12), K (13), A (1), all have the value of 0. When adding up the points, you can only get a point total of single digits; so after 9 points, it goes back to zero again. 
Both participants are initially dealt 2 cards each. If either one of the players have a point total of 8 or 9, that is an automatic win. 
If Player has point total between 0-5, they will be dealt another card. Based on the Player's cards, the Banker may be dealt a third card.
Specially, a third card will be dealt to the Banker under one of these conditions:
    The Player did not get a third card and the Banker's two cards total is between 0-5.
    The Player's third card has a value of 2 or 3 and the Banker's two cards total is between 0-4.
    The Player's third card has a value of 4 or 5 and the Banker's two cards total is between 0-5.
    The Player's third card has a value of 6 or 7 and the Banker's two cards total is between 0-6.
    The Player's third card has a value of 8 and the Banker's two cards total is between 0-2.
    The Player's third card has a value of 1 or greater than 8 and the Banker's two cards total is between 0-3.
After all the cards are dealt for both the Player and the Banker, whoever has a higher point total is the winner.

In our program, the menu consists of three options. If the user chooses option 1, the instructions will be read from BaccaratRules.txt and displayed in the console. 
If the user chooses option 2, the program will start the game. It will first ask the user to choose who to bet on and how much they want to bet. If they bet more than the available amount in their bank account, they will be prompted to place another bet. 
After each round, their betting history will be recorded in BankAccount.txt, which also displays their current balance. 
Once the Bank Account has a balance of 0, the user will not be allowed to play another round of the game. 
If the user chooses option 3, they can see the amount available in their Bank Account. 
Upon exiting, they can choose to erase the history and reset their Bank Account balance to 500. 
