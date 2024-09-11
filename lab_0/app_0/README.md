# Library Program

This program simulates a simple library management system where you can add, display, and remove books from a library. The program is written in Java and uses basic object-oriented programming concepts.

## Project Structure

The project is organized as follows:


- **Carte.java**: This file contains the `Carte` class, which represents a book with attributes such as title, author, and ISBN.
- **Biblioteca.java**: This file contains the `Biblioteca` class, which represents a library that manages a list of books and provides methods to add, remove, and display books.
- **Main.java**: This file contains the `Main` class, which is the entry point of the program. It allows users to interact with the library by adding books, displaying all books, and removing a book by ISBN.

## How to Run

1. **Compile the Java files**:
    ```sh
    javac lab_0/app_0/src/*.java
    ```

2. **Run the Main class**:
    ```sh
    java lab_0.app_0.src.Main
    ```

## Usage

1. When you run the program, it will prompt you to enter the number of books you want to add.
2. For each book, you need to provide the title, author, and ISBN.
3. After adding the books, the program will display all the books in the library.
4. You will then be prompted to enter the ISBN of the book you want to remove.
5. The program will display the remaining books in the library after the removal.

## Example

Here is an example interaction with the program:

```sh 
Introduceti numarul de carti: 2 
Introduceti titlul cartii: Carte1 
Introduceti autorul cartii: Autor1 
Introduceti ISBN-ul cartii: 1234567890 

Introduceti titlul cartii: Carte2 
Introduceti autorul cartii: Autor2 
Introduceti ISBN-ul cartii: 0987654321

Carti in biblioteca: 
Carte [Titlu=Carte1, Autor=Autor1, ISBN=1234567890] 
Carte [Titlu=Carte2, Autor=Autor2, ISBN=0987654321]

Introduceti ISBN-ul cartii pe care doriti sa o eliminati: 1234567890

Carti in biblioteca dupa eliminare: 
Carte [Titlu=Carte2, Autor=Autor2, ISBN=0987654321] 
```
