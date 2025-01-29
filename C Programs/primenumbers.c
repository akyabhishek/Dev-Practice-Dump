#include<conio.h>
#include<stdio.h>

int main(){
    int num,i,count=0;
    printf("Enter a number to check whether it is prime or not:");
    scanf("%d",&num);
    for(i=2;i<=num;i++){
        if(num%i==0){
            count++;
        }
    }
    if(count>1){
        printf("%d is not a prime number",num);
    }
    else{
        printf("%d is a prime number",num);
    }
    return 0;
}