#ifndef STUDENT_SERVICE_H
#define STUDENT_SERVICE_H

//Declare function
Student *add_student(Student *head, Student *sStu); // Add student information
Student *search_student(Student *head, char *input_name); // Search a student's infomation
Student* delete_student(Student *head, char *input_name); // Delete a student's infomation
Student* modify_student(Student *head, char *input_name); // Change a student's infomation
Student* sort_student(Student *head); // Sort student information by student number, from small to large 
Student *read_student(char* path, Student *head); // Read students' information from a file
int save_student(char* path, Student *head); // Save students' information to a file 

#endif
