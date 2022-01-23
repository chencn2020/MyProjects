#include "dict.h"
# include <string.h>

/*Global variable */
struct dictionary *dict;
int wordsNumber; //number of words


/*definitions of the functions*/
/* Read a file which contains words and their definitions into the array of structures, return the number of words.
If the file does not exist, return 0.
*/
int loadDictionary(char * fileName) {
	FILE * file;
	int cnt = 0;
	
	file = fopen(fileName,"r");
	if (file == NULL){
		printf("The file can't be opened!\n");
		exit(1);
	}
	/* Load the dictionary */
	while(fscanf(file,"%s	%s",dict[cnt].word, dict[cnt].definition) != EOF){
		cnt++;
	}
	fclose(file);
	
	return cnt;
}
/* look up the word in the dictionary, return the address of the structure if the word is found, return NULL if not found.
*/
struct dictionary * lookup(char * word) {
	int i;
	int whetherFound = 0;
	
	/* Find the word in the dictionary and print the meaning out is the word is existing */
	for (i=0; i<wordsNumber; i++){
		if (strcmp(dict[i].word, word) == 0){
			printf("The meaning of this word is %s\n", dict[i].definition);
			whetherFound = 1;
		}
	}
	if (whetherFound == 0){
		printf("Can't find the word!\n");
	} 
}
