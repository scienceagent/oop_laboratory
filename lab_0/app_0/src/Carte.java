package lab_0.app_0.src;

public class Carte {
    public String titlu;
    public String autor;
    public String isbn;

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