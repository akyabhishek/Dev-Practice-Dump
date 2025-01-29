def func(listA,listB):
    if (len(listA)==listA.count(0)) or (len(listB)==listB.count(0)):
        return(-1)
    else:
        num=1
        while(True):
            for i in range(0,len(listA)):
                if listA[i]==0:
                    listA[i]=num
            for j in range(0,len(listB)):
                if listB[j]==0:
                    listB[j]=num
            if sum(listA)==sum(listB):
                return(num)
            else:
                num+=1
                print(num)

print(func([2,5,0,1,1],[2,1,0,0]))