a,b,result=[],[],[]
row=int(input("Enter the number of row in A and B matrix:"))
col=int(input("Enter the number of column in  A and B matrix:"))
for i in range(row):
    x=[]
    for j in range(col):
        x.append(0)
    result.append(x)
print("--Input values of Matrix A--")
for i in range(row):
    x=[]
    for j in range(col):
        print("Enter the value of A[",i+1,"][",j+1,"]:",sep='')
        x.append(int(input()))
    a.append(x)
print("--Input values of Matrix B--")
for i in range(row):
    x=[]
    for j in range(col):
        print("Enter the value of B[",i+1,"][",j+1,"]:",sep='')
        x.append(int(input()))
    b.append(x)

for i in range(row):
    for j in range(col):
        for k in range(len(b)):
            result[i][j] += a[i][k] * b[k][j]
print("The Resultant Matrix Is =>")
for i in result:
    print(i)