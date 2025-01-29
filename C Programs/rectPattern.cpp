#include<iostream>
using namespace std;
int main(){
    int row,col;
    cout<<"Enter no. of rows:\t";
    cin>>row;
    cout<<"Enter no. of column:\t";
    cin>>col;
    for(int i=0;i<row;i++){
        for(int j=0;j<=col;j++){
            cout<<"*";
        }
        cout<<endl;
    }
    return 0;
}