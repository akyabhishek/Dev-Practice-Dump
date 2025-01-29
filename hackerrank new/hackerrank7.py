def findNumber(arr, k):
    for i in arr:
        if i==k:
            result= "YES"
        else:
            result ="NO"
    return result
arr=[1,2,3]
k=3
print(findNumber(arr,k))