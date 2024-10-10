#include <iostream>
using namespace std;

class ClasaB; 

class ClasaA {
private:
    int atributPrivatA;

public:
    ClasaA(int val) : atributPrivatA(val) {}

    friend class ClasaB;

    void afiseazaAtribut() {
        cout << "Atributul privat al clasei A: " << atributPrivatA << endl;
    }
};

class ClasaB {
private:
    int atributPrivatB;

public:
    ClasaB(int val) : atributPrivatB(val) {}

    friend class ClasaA;

    void afiseazaAtribut() {
        cout << "Atributul privat al clasei B: " << atributPrivatB << endl;
    }

    void acceseazaAtributA(ClasaA& objA) {
        cout << "Accesând atributul privat al clasei A din ClasaB: " << objA.atributPrivatA << endl;
    }
};

int main() {
    ClasaA obiectA(10);
    ClasaB obiectB(20);

    obiectA.afiseazaAtribut();
    obiectB.afiseazaAtribut();

    obiectB.acceseazaAtributA(obiectA);

    return 0;
}
