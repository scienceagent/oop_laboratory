#include <iostream>
using namespace std;

class Persoana {
private:
    string nume;
    int varsta;

public:
    Persoana(string n, int v) : nume(n), varsta(v) {
        cout << "Constructorul a fost apelat pentru " << nume << endl;
    }

    ~Persoana() {
        cout << "Destructorul a fost apelat pentru " << nume << endl;
    }

    void afiseazaInfo() {
        cout << "Nume: " << nume << ", Varsta: " << varsta << endl;
    }
};

int main() {
    Persoana p1("Ion", 30);
    p1.afiseazaInfo();

    return 0;
}
