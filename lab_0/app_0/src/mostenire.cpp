#include <iostream>
using namespace std;

class Animal {
public:
    void mananca() {
        cout << "Pote sa manance!" << endl;
    }
    void doarme() {
        cout << "Pote sa dorme!" << endl;
    }
};

class Caine : public Animal {
public:
    void latra() {
        cout << "Pote sa latre! " << endl;
    }
};

int main() {
    
    Caine caine1;

    caine1.mananca();
    caine1.doarme();

    caine1.latra();

    return 0;
}
