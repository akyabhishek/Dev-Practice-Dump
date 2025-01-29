def checkGift(arr):
    count=0
    for i in range(len(arr)):
        for j in range(len(arr)):
            if i!=j:
                if((arr[i] & arr[j])==((arr[i]+arr[j])/2)):
                    count+=1
    return int(count/2)

n=int(input())
A=[]
for i in range(n):
	A.append(int(input()))
print(checkGift(A))

