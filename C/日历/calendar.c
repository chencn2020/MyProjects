/*
	Name: practice5-4.c
	Function: To generate a calendar for a year.
	Author: Chen Zewen
	Student number: 18301154
	Date: 2019-3-31 12:33 
*/

# include <stdio.h>
#define Sunday 0
#define Monday 1
#define Tuesday 2
#define Wednesday 3
#define Thursday 4
#define Friday 5
#define Saturday 6

void GiveInstructions(); /* Print out the instructions */
int GetYearFromUser(); /* Read the year entered */
void PrintCalendar( int year ); /* Print out the calendar */
int IsLeapYear( int year ); /* Judge whether the year is a leap year */ 
void printCalendarOfmonth ( int *weekday, int month, int year ); /* Print out the calendar month by month */
int daysOfMonth ( int year, int month ); /* Calculate how many days in this month */
void nameOfMonth ( int month, int year ); /* Print out the title of each calendar */

int main ()
{
	int year;
	 
	GiveInstructions(); /* Prints out instructions to the user */
	year = GetYearFromUser(); /* Reads in a year from the user */
	PrintCalendar( year ); /* Prints a calendar for an entire year */
	
	return 0;
}

void GiveInstructions()
{	
	printf ("This program can generate a calendar for a year.\n");
	printf ("But the year should not be before 1900\n");
	printf ("Please enter a year: ");
}

int GetYearFromUser()
{
	int year;
	scanf ("%d", &year);
	/* Judege whether the year is more than 1900 */
	while ( year < 1900 ){
		/* Delete the character inputed. To prevent the project from endless loop. */
		fflush ( stdin );
		printf ("The year should not be before 1900.\n");
		printf ("Please enter a year: ");
		/* Another chance for users to enter a year */
		scanf ("%d", &year);
	}
	return year;	
}

void PrintCalendar( int year )
{
	int numberOfDaysAYear; /* Declear a value of the number of days in a year */
	int nDays; /* Declear a value of the number of days between the year entered and 1900.01.01 */
	int numberOfleapYears; /* Declear a value of the number of leap year */
	int weekday; /* Declear a value of the weekday. 0 means Sunday, 1 means Monday etc. */
	int i; /* For loops */
	
	weekday = 1; /* 1900.01.01 is Monday */
	nDays = -365;
	numberOfDaysAYear = IsLeapYear( year );
	for ( i = 1899; i <= ( year - 1 ); i++ ){
		/* Calculate the number of days between the year entered and 1900.01.01 */
		nDays += IsLeapYear( i );
	}
	weekday = ( weekday + nDays ) % 7;
	
	for ( i = 1; i <= 12; i++){
		printf ("\n\n");
		nameOfMonth ( i, year );
		printf ("\n");
		/* Print out the structure about the calendar */
		printf ("Su Mo Tu We Th Fr Sa\n");
		printCalendarOfmonth ( &weekday, i, year );

	}
}

int IsLeapYear( int year )
{
	int numberOfDaysAYear;
	/* A leap year should be divided evenly by 4 and can't be divided evenly by 100.
		Or it can't be divided evenly 400. */
	if (( year % 4 == 0 && year % 100 != 0 ) || ( year % 400 == 0 )){
		numberOfDaysAYear = 366;
	} else {
		numberOfDaysAYear = 365;
	}
	
	return numberOfDaysAYear;
}

void printCalendarOfmonth ( int *weekday, int month, int year )
{	
	int weeks[6][7]; /* The array is used to reserve the dates. */
	int i, j, numberOfDaysAMonth;
	int line = 0;
	for ( i = 0; i < 6; i++){
		for ( j = 0; j < 7; j++){
			weeks[i][j] = 0;
		}
	}
	numberOfDaysAMonth = daysOfMonth ( year, month );
	/* Reserve the dates in the array */
	for ( i = 1; i <= numberOfDaysAMonth; i++){
		weeks[line][*weekday] = i;
		*weekday += 1;
		if ( *weekday >= 7 ){
			*weekday = 0;
			line += 1;
		}
	}
	/* Print out the calendar */
	for ( i = 0; i < 6; i++){
		for ( j = 0; j < 7; j++){
			if ( weeks[i][j] == 0){
				/* 0 means space */
				printf ("   ");
			} else {
				printf ("%2d ", weeks[i][j]);
				if ( j == 6 && weeks[i][j] != numberOfDaysAMonth ){
					printf ("\n");
				}
			}			
		}
		/* It will break out the loop until the last line has been printed completely */
		if ( weeks[i][j] == 0 ){
			break;
		}
	}
}

int daysOfMonth ( int year, int month )
{
	int numberOfDaysAMonth;
	
	/* Each month has constant days except Feburary */
	if ( month != 2){
		if ( month == 4 || month ==6 || month == 9 || month == 11){
			numberOfDaysAMonth = 30;
		} else {
			numberOfDaysAMonth = 31;
		}
	} else {	
		/* Feburary has 29 days in a leap year but only has 28 days in a non-leap year */
		if (( year % 4 == 0 && year % 100 != 0 ) || ( year % 400 == 0 )){
			numberOfDaysAMonth = 29;
		} else {
			numberOfDaysAMonth = 28;
			}
		}
		
	return numberOfDaysAMonth;
}

void nameOfMonth ( int month, int year )
{	
	switch ( month )
	{
		case 1:
			printf ("    January %d", year);
			break;
		case 2:
			printf ("    February %d", year);
			break;
		case 3:
			printf ("    March %d", year);
			break;	
		case 4:
			printf ("    April %d", year);
			break;	
		case 5:
			printf ("    May %d", year);
			break;	
		case 6:
			printf ("    June %d", year);
			break;	
		case 7:
			printf ("    July %d", year);
			break;	
		case 8:
			printf ("    August %d", year);
			break;
		case 9:
			printf ("    September %d", year);
			break;	
		case 10:
			printf ("    October %d", year);
			break;
		case 11:
			printf ("    November %d", year);
			break;	
		case 12:
			printf ("    December %d", year);
			break;				
	}
}
