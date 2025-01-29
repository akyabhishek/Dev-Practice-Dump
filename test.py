def calcSteps(N,M,A,B):
    for i in range(M):
        x=B[i][0]
        y=B[i][1]
        x1=[]
        res=[]
        while(True):
            if(x<=N):
                x=x+A[x-1]+y
                x1.append(x)
            else:
                break
        res.append(len(x1))
    return res
N=3
M=2
A=[1,2,3]
B=[[2,3],[1,1]]
print(calcSteps(N,M,A,B))