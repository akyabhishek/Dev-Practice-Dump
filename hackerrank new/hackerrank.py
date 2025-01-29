def compareTriplets(a, b):
    c=[0,0]
    for i in range(len(a)):
        if(a[i]>b[i]):
            c[0]=c[0]+1
        elif(a[i]<b[i]):
            c[1]=c[1]+1
    print(c)


a=(10,20,30)
b=(2,3,4)
compareTriplets(a,b)