package lab_0.app_0.src;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        Biblioteca biblioteca = new Biblioteca();

        System.out.print("Introduceti numarul de carti: ");
        int numarCarti = scanner.nextInt();
        scanner.nextLine(); 

        for (int i = 0; i < numarCarti; i++) {
            System.out.print("Introduceti titlul cartii: ");
            String titlu = scanner.nextLine();

            System.out.print("Introduceti autorul cartii: ");
            String autor = scanner.nextLine();

            System.out.print("Introduceti ISBN-ul cartii: ");
            String isbn = scanner.nextLine();

            Carte carte = new Carte(titlu, autor, isbn);
            biblioteca.adaugaCarte(carte);
        }

        System.out.println("Carti in biblioteca:");
        biblioteca.afiseazaCarti();

        System.out.print("Introduceti ISBN-ul cartii pe care doriti sa o eliminati: ");
        String isbnDeSters = scanner.nextLine();
        biblioteca.eliminaCarte(isbnDeSters);

        System.out.println("Carti in biblioteca dupa eliminare:");
        biblioteca.afiseazaCarti();

        scanner.close();
    }
}
