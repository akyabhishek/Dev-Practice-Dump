import java.util.*;
import java.util.List;
import java.util.Scanner;
import java.util.Arrays;

public class fascinatingnumber {
    public static void main (String[] args){
        int b=0;
        Scanner sc= new Scanner(System.in);
        System.out.println("Enter the number");

        int n= sc.nextInt();

        for(int i=2 ;i<=2;i++){
            int a=n*i;
            for (int x=3;x<=3;x++){
                int d=n*x;
          b= Integer.parseInt(Integer.toString(n).concat(Integer.toString(a)).concat(Integer.toString(d)));
            // System.out.println("Number " + b);}

        }
        String num=Integer.toString(b);
        List<String> sepname = Arrays.asList(num.split(""));
        int flag=0;
        for(int i=1;i<=9;i++){
         if(Collections.frequency(sepname, Integer.toString(i))!=1){
            System.out.println(i);
            flag=1;
            break;
        }
        }
        if(flag==1){
            System.out.println("Not a fascinating number");
        }
        else{
            System.out.println("Fascinating Number");
        }
        
        }
    }