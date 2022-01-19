#include <stdio.h>
#include <stdlib.h>
#include <string.h>
# include "../头文件/global.h"
# include "../头文件/model.h"
# include "../头文件/stu_file.h"

Student *load_by_file(char* path, Student *head)
{
	FILE * file;
	char info[100];
	
	file = fopen(path, "r");
	if(file!=0){
		/* Read the message line by lin */
		while (fgets(info,100,file) != NULL){
			head = parse(info, head);	
		}
		fclose(file);
		return head;
	} else {
		fclose(file);
		return NULL;
	}
}

Student *parse(char* info, Student *head)
{
	int i, j, k, l, out;
	int cnt, num;
	float sum;
	char tmp[32];
	
	Student *p = (Student*) malloc(sizeof(Student));
	i=0;
	l=0;
	j=1;
	cnt=1;
	/* Change the string into char, int and float */
	do{
		tmp[l] = info[i];
		if(info[i+1] == '#'||cnt==7){
			tmp[l+1] = '\0';
			if(cnt==1){
				strcpy(p->num, tmp);
				cnt++;
				j=0;
				l=-1;
			} else if (cnt==2){
				strcpy(p->name, tmp);
				cnt++;
				j=0;
				l=0;
			} else if (cnt==3){
				p->sex = info[i];
				cnt++;
				j=0;
				l=0;
			} else if (cnt==4){
				num = 0;
				for(k=j-1; k>=0; k--){
					num *= 10;
					num = num + (info[i-k]-'0'); 
				}
				p->age = num;
				cnt++;
				j=0;
				l=0;
			} else if (cnt==5){
				p->cscore = getNum(i, j, info);
				cnt++;
				j=0;
				l=0;
			} else if (cnt==6){
				p->mscore = getNum(i, j, info);
				cnt++;
				j=0;
				l=0;
			} else if (cnt==7){
				sum = 0;
				out = 0;
				for(k=j-1; k>=0; k--){
					if (out == 0){
						do {
							sum *= 10;
							sum = sum + (info[i-k]-'0'); 
							k--;
							out = 1;	
						} while (info[i-k] != '.');
					}	
					sum = 1.0*sum + 0.1*(info[i-k+1]-'0');	
				}
				p->escore = sum;
				cnt++;
				j=0;
				l=0;
			}
			i+=2;
		}
		j++;
		i++;
		l++;
	} while (info[i] != '\0');
	p->next = NULL;
	Student *read = head;
	if (head == NULL){
		head = p;
	} else {
		while (read->next != NULL){
			read = read->next;	
		}
		read->next = p;
	}
	return head;
}

int save_to_file (char* path, Student *head)
{
	FILE *file;
	
	/* Save the information into a file */
	file = fopen(path,"w");
	Student *search = head;
	if (search!=NULL){
		while (search!=NULL){
			fprintf(file,"%s##%s##%c##%d##%.1f##%.1f##%.1f\n", 
			search->num, search->name, search->sex, search->age, search->cscore, search->mscore, search->escore);
			search = search->next;
		}
		fclose(file);
		return 1;
	} else {
		fclose(file);
		return 0;
	}
}

float getNum(int i, int j, char *info)
{
	double sum;
	int out, k;
	
	/* Get the decimals from this string */
	sum = 0;
	out = 0;
	for(k=j-1; k>0; k--){
		if (out == 0){
			do {
				sum *= 10;
				sum = sum + (info[i-k]-'0'); 
				k--;
				out = 1;	
			} while (info[i-k] != '.');
		}	
		sum = 1.0*sum + 0.1*(info[i-k+1]-'0');	
	}
	return sum;
 } 
