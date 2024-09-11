package lab_0.app_0.src;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

class Carte {
    private String titlu;
    private String autor;
    private String isbn;

    public Carte(String titlu, String autor, String isbn) {
        this.titlu = titlu;
        this.autor = autor;
        this.isbn = isbn;
    }

    public String getTitlu() {
        return titlu;
    }

    public void setTitlu(String titlu) {
        this.titlu = titlu;
    }

    public String getAutor() {
        return autor;
    }

    public void setAutor(String autor) {
        this.autor = autor;
    }

    public String getIsbn() {
        return isbn;
    }

    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }

    @Override
    public String toString() {
        return "Carte [Titlu=" + titlu + ", Autor=" + autor + ", ISBN=" + isbn + "]";
    }
}

class Biblioteca {
    private List<Carte> carti;

    public Biblioteca() {
        this.carti = new ArrayList<>();
    }

    public void adaugaCarte(Carte carte) {
        carti.add(carte);
    }

    public void eliminaCarte(String isbn) {
        carti.removeIf(carte -> carte.getIsbn().equals(isbn));
    }

    public void afiseazaCarti() {
        if (carti.isEmpty()) {
            System.out.println("Biblioteca este goala.");
        } else {
            for (Carte carte : carti) {
                System.out.println(carte);
            }
        }
    }
}

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        Biblioteca biblioteca = new Biblioteca();

        System.out.print("Introduceti numarul de carti:");
        int numarCarti = scanner.nextInt();
        scanner.nextLine();

        for (int i = 0; i < numarCarti; i++) {
            System.out.print("Introduceti titlul cartii:");
            String titlu = scanner.nextLine();

            System.out.print("Introduceti autorul cartii:");
            String autor = scanner.nextLine();

            System.out.print("Introduceti ISBN-ul cartii:");
            String isbn = scanner.nextLine();

            Carte carte = new Carte(titlu, autor, isbn);
            biblioteca.adaugaCarte(carte);
        }

        System.out.println("Carti in biblioteca:");
        biblioteca.afiseazaCarti();

        System.out.print("Introduceti ISBN-ul cartii pe care doriti sa o eliminati:");
        String isbnDeSters = scanner.nextLine();
        biblioteca.eliminaCarte(isbnDeSters);

        System.out.println("Carti in biblioteca dupa eliminare:");
        biblioteca.afiseazaCarti();

        scanner.close();
    }
}
