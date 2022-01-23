/*
	Name: practice7-1.c
	Function: A game called tic-tac-toe
	Author: Chen Zewen
	Student number: 18301154
	Date: 2019-4-19 17£º12 
*/

# include <stdio.h>
# include <stdlib.h>

void playGame(char array[]); /* Make the player to enter the number in turn */
int play(char XorO, char array[]); /* Ask player to enter the number */
void displayArray(char array[]); /* Print out the game */
int score(char array[]); /* Judge whether some one has won */
int judgement (int number, char array[]); /* Judge whether the place has benn taken */

int main() {
	char array[10];
	int i;
	//initialize array prior to playing
	for (i = 0; i < 10; i++)
		array[i] = (char) ( '1' + i );
	array[9] = '\0';   
	playGame(array);
	getchar();
	getchar();
	
	return 0;
}

void playGame(char array[])
{
	int i;
	int player=0; /* 0 is player X and 1 is palyer O */
	char XorO;
	int whetherEnd = 0;
	
	displayArray(array);
	/* The loop will not end until the game is over */
	do{
		if (player == 0){
			whetherEnd = play('X', array);
			player = 1;
		} else {
			whetherEnd = play('O', array);
			player = 0;
		}	
	} while (whetherEnd == 0);
	if (whetherEnd != 'T'){
		printf("\nCongratulations! Player %c wins.\n", whetherEnd);
	} else {
		printf("\nDogfall!\n");
	}
}

int play(char XorO, char array[])
{
	int i, numberInput;
	int whetherEnd = 0;
	
	printf ("\nPlayer %c, please enter the number(1-9) to place your mark: ",XorO);
	scanf ("%d", &numberInput);
	/* Judge whether the place has benn taken */
	numberInput = judgement (numberInput, array);
	/* Mark the palce */
	array [numberInput-1] = XorO;
	whetherEnd = score(array);
	/* Clear the screen and print out the game again */
	system("cls");
	displayArray(array);
	
	return whetherEnd;
}

void displayArray(char array[])
{
	int i;
	int cnt = 0;
	
	/* Print out the game */
	printf("	       TIC-TAC-TOE\n");
	for (i=0; i<9; i++){
		if (cnt < 3){
			printf("%10c", array[i]);
			cnt ++;
		} else {
			printf ("\n\n");
			i -= 1;
			cnt = 0;
		}
	}
	printf("\n");
}

int score(char array[]) 
{
	int whoWins = 0;
	int sum = 0;
	int i;
	int result1, result2, result3, result4;
	
	/*Judge whether the game is over */
	for (i = 0; i < 3; i++){
		result2 = array[i] + array[i+3] + array[i+6];
		result3 = array[i] + array[i+4] + array[i+8];
		result4 = array[i] + array[i+2] + array[i+4];
		sum = sum + array[i] + array[i+3] + array[i+6];
		if (i==0){
			result1 = array[i] + array[i+1] + array[i+2];	
		} else {
			result1 = array[3*i] + array[3*i+1] + array[3*i+2];
		} 
		if (result1 == 3*'X' || result2 == 3*'X'){
			whoWins = 'X';
			break;
		} else if (result1 == 3*'O' || result2 == 3*'O'){
			whoWins = 'O';
			break;
		} else if ((i == 0 && result3 == 3*'X') || (i == 2 && result4 == 3*'X')){
			whoWins = 'X';
			break;
		} else if ((i == 0 && result3 == 3*'O') || (i == 2 && result4 == 3*'O')){
			whoWins = 'O';
			break;
		} else if (sum == 5*'X' + 4*'O'){
			whoWins = 'T';
			break;
		}
	}
	
	return whoWins;
}

int judgement (int number, char array[])
{
	while ((number < 1 || number > 9) || array[number-1] == 'X' || array[number-1] == 'O' ){
		printf ("Please enter the right number to place your mark: ");
		/* Delect the other characters that were inputed */
		fflush (stdin);
		scanf ("%d", &number);
	}
	
	return number;
}
