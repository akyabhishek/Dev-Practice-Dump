x=int(input("Enter first number:"))
y=int(input("Enter Second number:"))
print("Sum of both numbers: ",x+y)

# a,b,c=1,2,3
# print(a,b,c)

# a,b,c,d,e=1,2,3,4,5
# print(a,b,c,sep=",",end="\t")
# print(d,e)

# import math
# r= math.log(8,2)
# print(r)
# print(math.factorial(5))
# print(math.pi)

# import random
# r=random.random()
# s=random.randint(1,6)
# print(r,s)

# for i in range(5):
#     for j in range(i+1):
#         print("*",end="")
#     print("")

# for i in range(4):
#     print(5)
#     break
# else:
#     print(4)

# n= int(input("Enter a number: "))
# for i in range(2,n):
#     if(n%i==0):
#         print("Number is not prime")
#         break
# else:
#     print("Number is prime")

# import math
# print(pow(2,5))

# a="Abhishek Kumar Yadav"
# print(a[::2])

# import datetime as dt
# x=dt.datetime.now()
# print(x.day)

# import sys
# print(sys.version)

# a= "Hello"
# print(a[::2])

# def square(n):
#     return n*n
#
# def sum(n,s):
#     sum=0
#     sum=s(n)
#     print(sum)
#
# sum(4,square)

# l=[x**2 for x in range (1,101) if x%2 ==1]
# print(l)

# sume=0
# sumo=0
# for i in range (1,101):
#     if i%2==0:
#         sume+=i
#     elif i%2==1:
#         sumo+=i
# print("Sum of even numbers:",sume)
# print("Sum of odd numbers:",sumo)
#
# even=[x for x in range(1,101) if x%2==0]
# odd=[x for x in range(1,101) if x%2==1]
# print(sum(even))
# print(sum(odd))

# class wish:
#     def __init__(self, name):
#         self.name = name
#     def morning(self):
#         print('Good Morning', self.name)
#     def evening(self):
#         print('Good Evening', self.name)
# a1 = wish('John')
# a2 = wish('Martin')
# a1.morning()
# a2.evening()

# d={'A':1,'B':2,'C':3}
# key=input("Enter key to check:")
# if key in d.keys():
#       print("Key is present and value of the key is:",d[key])
# else:
#       print("Key not present")
#
# def linearSearch(arr, target):
#     n=len(arr)
#     for i in range(n):
#         if (arr[i] == target):
#             return i
#     return -1
# arr = [8,15,20,11,13]
# target = int(input("Enter a number to search:"))
# result = linearSearch(arr, target)
# if (result == -1):
#     print("Element is not present in array")
# else:
#     print("Element is present at index:", result)