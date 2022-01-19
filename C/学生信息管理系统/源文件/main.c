/*
	文件名: StuManagement.dev
	功能: 学生信息管理系统 
	作者: 陈泽文
	学号: 18301154
	日期: 2019-5-17 16：38 
*/

# include <stdio.h>
# include <stdlib.h>
# include <string.h>
# include <unistd.h>
# include "../头文件/global.h"
# include "../头文件/model.h"
# include "../头文件/stu_service.h"

void print_menu (); // Print out the menu
Student *add (Student *head); // Add student information
void show (Student *head); // Show all students infomation
void search (Student *head); // Search a student's infomation
Student *deleteStu(Student *head); // Delete a student's infomation
Student *modify(Student *head); // Change a student's infomation
Student * sort (Student *head); // Sort student information by student number, from small to large 
Student *Read(Student *head, char* path); // Read students' information from a file
void save(Student *head, char* path); // Save students' information to a file 
void freeLink(Student *head); // Free internal storage

int main(void) 
{
	Student *head = NULL;
	int select = -1;
	char path[100];
	
    getcwd(path,sizeof(path));
   	strcat(path,"\\student.txt"); // Get the absolute path
   	if(head = read_student(path, head)); // Read students' information from a file if the file exists
	do{
		if (select!=0){
			print_menu(); // Print out the menu
			printf("Please enter you option<0~8>：");
			scanf("%d", &select);
			while(select<0 || select>8){
				fflush(stdin);
				printf("Please enter the right option<0~8>：");
				scanf("%d", &select);
			}
		}
		switch (select){
			case 1: 
				printf("You select to Add student info\n\n");
				head = add (head);	
				break;
			case 2: 
				printf("You select to Display student info\n\n");
				show(head);
				break;
			case 3: 
				printf("You select to Search student info\n\n");
				search(head);
				break;
			case 4: 
				printf("You select to Delete student info\n\n");
				head = deleteStu(head);
				break;
			case 5: 
				printf("You select to Modify student info\n\n");
				head = modify(head);
				break;	
			case 6: 
				printf("You select to Sort student info\n\n");
				head = sort(head);
				break;			
			case 7: 
				printf("You select to Save student info\n\n");
				save(head, path);
				break;
			case 8: 
				printf("You select to Read student info\n\n");
				head = Read(head, path);
				break;
			case 0:
				printf("You select to Exit student info\n\n");
				break;
		} 	
	} while (select!=0); // The loop will end unitl 0 is entered
	freeLink(head);
	getchar();
	getchar();
	
	return 0;
}

void print_menu ()
{
	printf("********************************\n");
	printf("Student Information Management\n");
	printf("********************************\n\n");
	printf("***************Menu*************\n");
	printf("1 Add student info\n");
	printf("2 Display student info\n");
	printf("3 Search student info\n");
	printf("4 Delete student info\n");
	printf("5 Modify student info\n");
	printf("6 Sort student info\n");
	printf("7 Save student info\n");
	printf("8 Read student info\n");
	printf("0 Exit system\n");
	printf("********************************\n");
}

Student *add (Student *head)
{
	Student message;
	/* Get the message user enters */
	printf("Please enter the student number: ");
	scanf("%s", &message.num);
	printf("Please enter the student's name: ");
	scanf("%s", &message.name);
	getchar(); 
	printf("Please enter the student's sex <m or w>: ");	
	scanf("%c", &message.sex);
	getchar();
	printf("Please enter the student's age: ");
	scanf("%d", &message.age);
	printf("Please enter the student's C language grade : ");
	scanf("%f", &message.cscore);
	printf("Please enter the student's math grade: ");
	scanf("%f", &message.mscore);
	printf("Please enter the student's English grade: ");
	scanf("%f", &message.escore);
	/* Call function from stu_service.c */
	if(head = add_student(head, &message)){
		printf("Add successfully\n\n");
	} else {
		printf("Add failed!\n\n");
	}
	return head;
}

void show (Student *head)
{
	int i=0;
	Student *search = head; 
	
	printf("************************Display student info************************\n");
	printf("Student ID\tName\tGender\tAge\tCScore\tMathScore\tEnglishScore\n");
	/* Print out all students information */
	while(search != NULL){
		printf("%s\t%s\t%c\t%d\t%.1f\t%.1f\t\t%.1f\n", 
		search->num, search->name, search->sex, search->age, search->cscore, search->mscore, search->escore);
		search = search->next;
	}
	printf("\n");
}

void search (Student *head)
{
	char stuName[32];
	Student *message;
	
	printf("************************Search student info************************\n");
	printf("Please enter student name you want to search: ");
	scanf("%s", stuName);
	message = search_student(head, stuName); // Call function from stu_service.c
	if (message != 0){	
		printf("The student info is as follows:\n");
		printf("Student ID\tName\tGender\tAge\tCScore\tMathScore\tEnglishScore\n");
		printf("%s	%s		%c	%d	%.1f		%.1f		%.1f\n\n", 
		message->num, message->name, message->sex, message->age, message->cscore, message->mscore, message->escore);
	} else {
		printf("No related student info!\n\n");
	}
}

Student *deleteStu(Student *head)
{
	char stuName[32];
	Student *tmp;
	
	printf("Please enter student name you want to search: ");
	scanf("%s", stuName);
	tmp = delete_student(head, stuName); // Call function from stu_service.c
	if (tmp != NULL){	
		head = tmp;
		printf("Delete successfully\n\n");
		return head;
	} else {
		printf("No related student info!\n\n");
	}
}

Student *modify(Student *head)
{
	char stuName[32];
	Student *tmp;
	
	printf("Please enter student name you want to modify: ");
	scanf("%s", stuName);
	tmp = modify_student(head, stuName); // Call function from stu_service.c
	if (tmp != NULL){	
		head = tmp;
		printf("Modify successfully\n\n");
		return head;
	} else {
		printf("No related student info!\n\n");
		return head;
	}
}
 
Student * sort (Student *head)
{
	Student *tmp;
	
	tmp = sort_student(head); // Call function from stu_service.c
	if (tmp != NULL){	
		head = tmp;
		printf("Sort successfully\n\n");
		return head;
	} else {
		printf("Sort failed!\n\n");
	}
}

Student *Read(Student *head, char* path)
{
	Student *tmp = NULL;
	head = NULL;
		
	tmp = read_student(path, head); // Call function from stu_file.c
	if(tmp!=NULL){
		head = tmp;
		printf("File has been read successfully\n\n");
		return head;
	} else {
		printf("Read failed\n\n");
	}
}

void save(Student *head, char* path)
{
	/* Call function from stu_file.c */
	if (save_student(path, head)){
		printf("Saving of student data succeeded\n\n");
	} else {
		printf("Saving of student data failed\n\n");
	}
}

void freeLink(Student *head)
{
	Student *last = head;
	Student *cur = head;
	
	/* Free the internal storage */
	while (last != NULL){
		cur = last->next;
		free(cur);
		last = cur;
	}
}
