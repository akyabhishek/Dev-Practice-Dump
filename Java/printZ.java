public class printZ{
    public static void main(String[] args) {
        // Write your code here
        int n=8;
        char c='*';

        printChar(c,n);
        for(int i=n-2;i>=0;i--){
            System.out.println("");
            printChar(' ',i);
            System.out.print(c);
        }
        printChar(c,n);

    }
    public static void printChar(char c,int n){
        for(int i=0;i<n;i++)
            System.out.print(c);
    }
}