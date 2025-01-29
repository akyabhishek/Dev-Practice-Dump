import java.util.Scanner;

public class strings {
    public static void main(String[] args) {
        Scanner sc=new Scanner(System.in);
        // String name=sc.nextLine();
        // System.out.println("Your name is : "+name);

        String fname="Tony";
        String lname="Stark";
        String fullname=fname+" "+lname;
        System.out.println(fullname);
        System.out.println(fullname.length());
        for(int i=0;i<fullname.length();i++){
            System.out.println(fullname.charAt(i));
        }

        //compare strings
        String name1="Tony";
        String name2="Tony";
        System.out.println(name1.compareTo(name2));
        if(name1.compareTo(name2)==0){
            System.out.println("Equal");
        }
        else{
            System.out.println("Not equal");
        }
        System.out.println(fullname.substring(0, 4));
    }
}
