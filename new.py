def diagonalDifference(arr):
    # Write your code here
    a=0
    b=0
    for i in range(len(arr)):
       for j in range(len(arr[i])):
           if(i==j):
               a=a+arr[i][j]
           if(i+j==len(arr)-1):
               b=b+arr[i][j]
    return abs(a-b)

arr=[[11,2,4],[4,5,6],[10,8,-12]]
print(diagonalDifference(arr))

"""
a[0][2]
a[1][1]
a[2][0]

a[0][0]
a[1][1]
a[2][2]
"""