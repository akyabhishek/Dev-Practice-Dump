def miniMaxSum(arr):
    sum=[]
    for i in range(len(arr)):
        sumarr=0
        for j in range(len(arr)):
            if i!=j:

                sumarr=sumarr+arr[j]
        sum.append(sumarr)
    print(min(sum),max(sum))
arr=[1,4,2,3]
miniMaxSum(arr)