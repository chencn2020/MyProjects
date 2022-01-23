/*
	Name: practice8-5.c
	Function: To be a dictionary.
	Author: Chen Zewen
	Student number: 18301154
	Date: 2019-5-12 21:34
*/

#include "dict.h"
# include <string.h> 

int main() {
	/* data structure for the dictionary
	assume less than 1000 words in the dictionary
	*/
	struct dictionary myDict[1000];
	/* File name*/
	char * filename ="dictionary.txt";
	/* initialize the global variable*/
	dict = myDict;
	
	char word[50];

	/* read from file, get the dictionary*/
	wordsNumber = loadDictionary(filename);
	
	/* The program will end until 0 is entered */
	do{
		printf("Please enter the word you want to search(0 to quit): ");
		scanf("%s",word);
		if(strcmp(word,"0") != 0){
			lookup(word);			
		}
	} while (strcmp(word,"0") != 0);
}

