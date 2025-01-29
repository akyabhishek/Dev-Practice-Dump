def nprime(arr,n):
    primenums=[]
    for i in range(len(arr)):
        for j in range(2,arr[i]):
            if arr[i]%j==0:
                break
        else:
            if len(primenums)<n:
                primenums.append(arr[i])
    return primenums
arr=[20,13,21,22,23,24,25,27,12,15,16,17,18,11,19]
n=5
print(nprime(arr,n))

