#include<iostream>
using namespace std;
namespace space1{
    int a=10;
    void show(){
        cout<<"\nNamespace space 1 "<<a;
    }
}
namespace space2{
    int a=200;
    void show(){
        cout<<"\nNamespace space 2 "<<a;
    }
}
int main(){
    space1::show();
    space2::show();
    cout<<endl;
    return 0;
    
}