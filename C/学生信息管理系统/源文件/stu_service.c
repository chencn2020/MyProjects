# include <stdio.h>
# include <string.h>
# include <stdlib.h>
# include "../头文件/global.h"
# include "../头文件/model.h"
# include "../头文件/stu_file.h"
# include "../头文件/stu_service.h"


Student *add_student(Student *head, Student *sStu)
{	
	/* Add the information to the link */
	Student *stu =(Student*) malloc (sizeof(Student));
	strcpy(stu->num, sStu->num);
	strcpy(stu->name, sStu->name);
	stu->sex = sStu->sex;
	stu->age = sStu->age;
	stu->cscore = sStu->cscore;
	stu->escore = sStu->escore;
	stu->mscore = sStu->mscore; 
	stu->next = NULL;
	Student *last = head;
	/* Get the tail */
	if (last != NULL){			
		while (last->next != NULL){
			last = last->next;
		}
		last->next = stu;
	} else {
		head = stu;
	}
	return head;
}

Student* search_student(Student *head, char *input_name)
{
	Student* search = head;
	/* Compare the information and find out this student */
	while (search != NULL){
		if(strcmp(search->name, input_name) == 0){
			return search;
		}
		search = search->next;
	}
	return NULL;
}

Student* delete_student(Student *head, char *input_name)
{
	Student* search = head;
	Student* tmp = head;
	
	while (search != NULL){
		if(strcmp(search->name, input_name) == 0){
			if(search = head){
				head = head->next;
				return head;
			} else {
				tmp->next = search->next;
				free(search); // Free the useless internal storage
				return head;
			}
			
		}
		tmp = search;
		search = search->next;
	}
	return NULL;
}

Student* modify_student(Student *head, char *input_name)
{
	Student* search = head;
	Student message;
	
	while (search != NULL){
		/* Change the information if this students exists */
		if(strcmp(search->name, input_name) == 0){
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
			strcpy(search->num, message.num);
			strcpy(search->name, message.name);
			search->sex = message.sex;
			search->age = message.age;
			search->cscore = message.cscore;
			search->escore = message.escore;
			search->mscore = message.mscore; 
			return head;
		}
		search = search->next;
	}
	return NULL;
}

Student* sort_student(Student *head)
{
	Student *cur = head;
	Student *tail = NULL;
	
	/* Sort student information by student number, from small to large */
	if(cur==NULL){
		return NULL;
	} else if( cur->next == NULL ) {
		return head;
	} 
	/* Bubble sort */
	while(cur != tail){
		while(cur->next != tail){
			if(strcmp(cur->num,cur->next->num)>0){
				Student tmp;
				
				strcpy(tmp.num, cur->num);
				strcpy(tmp.name, cur->name);
				tmp.sex = cur->sex;
				tmp.age = cur->age;
				tmp.cscore = cur->cscore;
				tmp.escore = cur->escore;
				tmp.mscore = cur->mscore;

				strcpy(cur->num, cur->next->num);
				strcpy(cur->name, cur->next->name);
				cur->sex = cur->next->sex;
				cur->age = cur->next->age;
				cur->cscore = cur->next->cscore;
				cur->escore = cur->next->escore;
				cur->mscore = cur->next->mscore; 
				
				strcpy(cur->next->num, tmp.num);
				strcpy(cur->next->name, tmp.name);
				cur->next->sex = tmp.sex;
				cur->next->age = tmp.age;
				cur->next->cscore = tmp.cscore;
				cur->next->escore = tmp.escore;
				cur->next->mscore = tmp.mscore;
			}
			cur = cur->next;		
		}
		tail = cur;
		cur = head;
	}
	return head;
}

Student *read_student(char* path, Student *head)
{
	Student *tmp = NULL;
	
	/* Read students' information from a file */
	tmp = load_by_file(path, head);
	if (tmp==NULL){
		return NULL;
	} else {
		head = load_by_file(path, head);
		return head;
	}
}

int save_student(char* path, Student *head)
{
	int judge;
	
	judge = save_to_file (path, head);
	
	return judge;
}
