# def vowel_count(s):
#     vowels='aeiouAEIOU'
#     count=0
#     for char in s:
#        if char in vowels:
#            count += 1
#     return count
# x=input("Enter a string to count vowels:")
# print("Number of vowels in string '",x,"' :",vowel_count(x))

try:
    a = 8
    b = 0
    result=a/b
    print("Result of Division: " ,result)
except ZeroDivisionError:
    print("Division of a number by zero is not allowed")