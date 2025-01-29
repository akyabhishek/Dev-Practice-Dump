# #task 1
# a=int(input("Enter a number :"))
# if(a%2==0):
#     print("Entered number is Even")
# else:
#     print("Entered number is odd")

# #task 2
# a = int(input("Enter a number :"))
# if(a>0):
#     print("Number is positive")
# elif(a<0):
#     print("Number is negative")
# else:
#     print("Number is Zero")

# #task 3
# a = input("Enter a something :")
# if(a>'a' and a<'z') or (a>'A' and a<'Z'):
#     print("Alphabets")
# elif(a>'0' and a<'9'):
#     print("Numbers")

# #task 4
# a=input("Enter a Character:")
# if(a<'z'):
#     print(a.upper())
# else:
#     print(a)

# # Task 5
# a=float(input("Enter first number: "))
# b=float(input("Enter second number: "))
# choice=input("Enter your choice: ")
# if(choice=='+'):
#     print(a+b)
# elif(choice=='-'):
#     print(a-b)
# elif(choice=='/'):
#     print(a/b)
# elif(choice=='*'):
#     print(a*b)
# else:
#     print("Invalid Choice")

#task 6
a=int(input("Enter first number: "))
b=int(input("Enter second number: "))
c=int(input("Enter third number: "))
d=int(input("Enter fourth number: "))
if(a>b and a>c and a>d):
    print("Number",a,"is the greatest")
elif(b>a and b>c and b>d):
    print("Number", b, "is the greatest")
elif(c>a and c>b and c>d):
    print("Number", c, "is the greatest")
elif (d > a and d > b and d > c):
    print("Number", d, "is the greatest")
else:
    print("Invalid Choices of numbers")

#task 6 using list
l1=[]
n=int(input("Enter the how many numbers you want to compare:"))
for i in range(n):
    element=int(input("Enter the number : "))
    l1.append(element)
print(max(l1)," is the biggest number")

# l=[5,3,2,56,45,3,0]
# print(l[::-1])
# l.sort()
# # print(l)
