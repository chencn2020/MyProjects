#ifndef MODEL_H
#define MODEL_H 

typedef struct Student{
	char num[16]; 
	char name[32];
	char sex; // M for male, and F for female, X means NULL
	int age; //Ranges in value from 0 to 150
	float cscore; //Value ranges from 0 to 100.
	float mscore; //Value ranges from 0 to 100.
	float escore; //Value ranges from 0 to 100.
	struct Student *next;
} Student;


#endif

