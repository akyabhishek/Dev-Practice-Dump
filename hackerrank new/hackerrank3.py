def plusMinus(arr):
    l=len(arr)
    p=0
    n=0
    z=0
    for i in range(l):
        if(arr[i]>=1):
            p+=1
        elif(arr[i]==0):
            z+=1
        else:
            n+=1
    print(round(p/l,6))
    print(round(n/l,6))
    print(round(z/l,6))

arr=[-4,3,-9,0, 4, 1]
plusMinus(arr)