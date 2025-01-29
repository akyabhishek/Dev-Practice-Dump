# def webpages(count,l,start,end,visited):
#     drive=start
#     count+=1
#     print(end in l[drive-1])
#     if(end in l[drive-1]):
#         return count
#     else:
#         if(visited[drive-1]==False):
#             visited[drive-1]=True
#             i=0
#             x=webpages(count,l,l[drive-1][i],end,visited)
#             if x==-1 and i <len([drive-1]):
#                 i+=1
#                 webpages(count,l,l[drive-1][i],end,visited)

                    
#         else:
#             return -1


# # n=int(input())
# n=3
# l=[[3],[2,1],[1]]
# # for i in range(n):
# #     l.append(list(map(int, input().split())))
# startend=list(map(int, input().split()))
# visited= [False] * n
# print(webpages(0,l,startend[0],startend[1],visited))


from collections import deque
class Graph:
    def init(self, edges, n):
 
        self.adjList = [[] for _ in range(n)]
 
        for (src, dest) in edges:
            self.adjList[src].append(dest)
 
 
def isReachable(graph, src, dest, discovered, path):
 
    discovered[src] = True
 
    path.append(src)
 
    if src == dest:
        return True
 
    for i in graph.adjList[src]:
 
        if not discovered[i]:
            if isReachable(graph, i, dest, discovered, path):
                return True
 
    path.pop()
 
    return False
 
if __name__ == '__main__':
    n = int(input())
    edges = []
    for i in range(n):
        x = list(map(int,input().split(" ")))
    for j in x:
        edges.append((i,j-1))

      
    graph = Graph(edges, n)

    discovered = [False] * n

    (src, dest) = map(int,input().split(" "))
    src = src- 1
    dest = dest - 1

    path = deque()

    if isReachable(graph, src, dest, discovered, path):
        print(len(list(path))-1,end="")
    else:
        print(-1,end="")