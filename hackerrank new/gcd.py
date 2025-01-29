def find_gcd(x, y):
    while(y):
        x, y = y, x % y
  
    return x
num1=int(input("Enter the first number:"))
num2=int(input("Enter the second number:"))
print("GCD of the entered three numbers is:",find_gcd(num1,num2))

