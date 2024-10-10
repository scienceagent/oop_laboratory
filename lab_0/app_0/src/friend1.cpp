#include <iostream>
using namespace std;

class Clasa {
private:
    int atributPrivat;

public:
    Clasa(int val) : atributPrivat(val) {}

    friend void afiseazaAtributPrivat(const Clasa& obj);
};

void afiseazaAtributPrivat(const Clasa& obj) {
    cout << "Atributul privat este: " << obj.atributPrivat << endl;
}

int main() {
    Clasa obiect(42);
    afiseazaAtributPrivat(obiect);

    return 0;
}
