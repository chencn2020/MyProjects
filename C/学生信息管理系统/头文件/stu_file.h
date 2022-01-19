#ifndef STU_FILE_H
#define STU_FILE_H 

Student *load_by_file(char* path, Student *head); // Read students' information from a file line by line
Student *parse(char* info, Student *head); // Change the string into char, int and float
int save_to_file (char* path, Student *head); // Save students' information into a file 
float getNum(int i, int j, char *info);  // Get decimals 

#endif
