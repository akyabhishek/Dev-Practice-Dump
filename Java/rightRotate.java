import java.util.Scanner;
public class rightRotate {
    public static void main(String[] args){
        Scanner sc=new Scanner(System.in);
        int[] arr={2,3,9,1,7};
        
        int d=3;
        int n=arr.length;
        int[] temp=new int[n];
        for(int i=0;i<n;i++){
            temp[(i+d)%n]=arr[i];

        }
        for(int a:temp){
            System.out.println(a);
        }

    }
    
}
