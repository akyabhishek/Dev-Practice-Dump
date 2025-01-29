a=[]
sum=0
row=int(input("Enter the number of row in matrix:"))
col=int(input("Enter the number of column in matrix:"))
for i in range(row):
    x=[]
    for j in range(col):
        print("Enter the value of a[",i+1,"][",j+1,"]:",sep='')
        x.append(int(input()))
    a.append(x)
print("Matrix=>")
for i in a:
    print(i)
    for j in i:
        sum+=j
print("Sum of elements of matrix will be: ",sum)