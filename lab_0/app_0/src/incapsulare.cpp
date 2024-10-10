#include <iostream>
using namespace std;

class ContBancar {
private:
    double sold;

public:
    
    ContBancar(double soldInitial) : sold(soldInitial) {}

    void depune(double suma) {
        if (suma > 0) {
            sold += suma;
            cout << "S-au depus " << suma << " lei. Sold actual: " << sold << " lei." << endl;
        } else {
            cout << "Suma de depunere trebuie să fie pozitivă." << endl;
        }
    }

    void retrage(double suma) {
        if (suma > 0 && suma <= sold) {
            sold -= suma;
            cout << "S-au retras " << suma << " lei. Sold actual: " << sold << " lei." << endl;
        } else {
            cout << "Suma de retragere este invalidă." << endl;
        }
    }

    double obtineSold() const {
        return sold;
    }
};

int main() {
    ContBancar cont(1000.0);

    cont.depune(500.0);
    cont.retrage(200.0);
    cout << "Soldul final este: " << cont.obtineSold() << " lei." << endl;

    return 0;
}
