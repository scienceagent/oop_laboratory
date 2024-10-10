package lab_0.app_0.src;

import java.util.ArrayList;
import java.util.List;

public class Biblioteca {
    private List<Carte> carti;

    public Biblioteca() {
        this.carti = new ArrayList<>();
    }

    public void adaugaCarte(Carte carte) {
        carti.add(carte);
    }

    public void eliminaCarte(String isbn) {
        carti.removeIf(carte -> carte.isbn.equals(isbn));
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
