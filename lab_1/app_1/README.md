# Student Management System

This program simulates a student management system for the Technical University of Moldova (TUM). It allows managing faculties and students, including operations such as creating faculties, enrolling and graduating students, and displaying information about faculties and students. The program is written in Java and employs basic object-oriented programming concepts.

## Project Structure

The project is organized as follows:

- **Student.java**: This file contains the `Student` class, which represents a student with attributes such as email, name, and date of birth.
- **Faculty.java**: This file contains the `Faculty` class, which represents a faculty with attributes such as name, abbreviation, field, and lists of enrolled and graduated students.
- **University.java**: This file contains the `University` class, which manages faculties and provides methods to handle student enrollment and graduation.
- **SaveManager.java**: This file contains the `SaveManager` class, which handles saving and loading the university state to and from a file.
- **StudentManagementSystem.java**: This file contains the `StudentManagementSystem` class, which is the entry point of the program. It allows users to interact with the system through a console-based interface.

## How to Run

1. **Compile the Java files**:
    ```sh
    javac *.java
    ```

2. **Run the StudentManagementSystem class**:
    ```sh
    java StudentManagementSystem
    ```

## Usage

1. When you run the program, it will display the main menu with options for general operations, faculty operations, and student operations.
2. You can create new faculties, enroll and graduate students, and display information about faculties and students using the respective commands.

## Example

Here is an example interaction with the program:

```sh
Welcome to TUM's student management system!
What do you want to do?
g - General operations
f - Faculty operations
s - Student operations
q - Quit Program
your input> g

General operations
What do you want to do?
nf/<faculty name>/<faculty abbreviation>/<field> - create faculty
ss/<student email> - search student and show faculty
df - display faculties
df/<field> - display all faculties of a field
b - Back
q - Quit Program
your input> nf/Media și Telecomunicații/MTC/SOFTWARE_ENGINEERING

Faculty created.
General operations
What do you want to do?
nf/<faculty name>/<faculty abbreviation>/<field> - create faculty
ss/<student email> - search student and show faculty
df - display faculties
df/<field> - display all faculties of a field
b - Back
q - Quit Program
your input> df

Media și Telecomunicații
General operations
What do you want to do?
nf/<faculty name>/<faculty abbreviation>/<field> - create faculty
ss/<student email> - search student and show faculty
df - display faculties
df/<field> - display all faculties of a field
b - Back
q - Quit Program
your input> b

Welcome to TUM's student management system!
What do you want to do?
g - General operations
f - Faculty operations
s - Student operations
q - Quit Program
your input> f

Faculty operations
What do you want to do?
ns/<faculty abbreviation>/<first name>/<last name>/<email>/<day>/<month>/<year> - create student
gs/<email> - (g)raduate (s)tudent
ds/<faculty abbreviation> - (d)isplay enrolled (s)tudents
dg/<faculty abbreviation> - (d)isplay (g)raduated students
bf/<faculty abbreviation>/<email> - check if student (b)elongs to (f)aculty
b - Back
q - Quit Program
your input> ns/MTC/Ionel/Gavrev/i.gavrev@isa.utm.md/1/4/2002

Student created.
Faculty operations
What do you want to do?
ns/<faculty abbreviation>/<first name>/<last name>/<email>/<day>/<month>/<year> - create student
gs/<email> - (g)raduate (s)tudent
ds/<faculty abbreviation> - (d)isplay enrolled (s)tudents
dg/<faculty abbreviation> - (d)isplay (g)raduated students
bf/<faculty abbreviation>/<email> - check if student (b)elongs to (f)aculty
b - Back
q - Quit Program
your input> ds/MTC

Student{email='i.gavrev@isa.utm.md', name='Ionel Gavrev', dateOfBirth='1/4/2002'}
Faculty operations
What do you want to do?
ns/<faculty abbreviation>/<first name>/<last name>/<email>/<day>/<month>/<year> - create student
gs/<email> - (g)raduate (s)tudent
ds/<faculty abbreviation> - (d)isplay enrolled (s)tudents
dg/<faculty abbreviation> - (d)isplay (g)raduated students
bf/<faculty abbreviation>/<email> - check if student (b)elongs to (f)aculty
b - Back
q - Quit Program
your input> gs/i.gavrev@isa.utm.md

Student graduated.
Faculty operations
What do you want to do?
ns/<faculty abbreviation>/<first name>/<last name>/<email>/<day>/<month>/<year> - create student
gs/<email> - (g)raduate (s)tudent
ds/<faculty abbreviation> - (d)isplay enrolled (s)tudents
dg/<faculty abbreviation> - (d)isplay (g)raduated students
bf/<faculty abbreviation>/<email> - check if student (b)elongs to (f)aculty
b - Back
q - Quit Program
your input> dg/MTC

Student{email='i.gavrev@isa.utm.md', name='Ionel Gavrev', dateOfBirth='1/4/2002'}
Faculty operations
What do you want to do?
ns/<faculty abbreviation>/<first name>/<last name>/<email>/<day>/<month>/<year> - create student
gs/<email> - (g)raduate (s)tudent
ds/<faculty abbreviation> - (d)isplay enrolled (s)tudents
dg/<faculty abbreviation> - (d)isplay (g)raduated students
bf/<faculty abbreviation>/<email> - check if student (b)elongs to (f)aculty
b - Back
q - Quit Program
your input> b

Welcome to TUM's student management system!
What do you want to do?
g - General operations
f - Faculty operations
s - Student operations
q - Quit Program
your input> q

Program exited.
