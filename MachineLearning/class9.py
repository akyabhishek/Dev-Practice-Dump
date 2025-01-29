# -*- coding: utf-8 -*-
"""
Created on Tue Jul 19 13:37:17 2022

@author: ADMIN
"""

import seaborn as sns
import matplotlib.pyplot as plt

from sklearn.datasets import load_iris

dataset = load_iris()

x = dataset.data

y= dataset.target

x[0,0]

x[:,1]

x[1,:]


# get the data hogya ab hm krenge data visualiasation


plt.scatter([1,2,3],[3,2,1])
plt.show()

plt.plot([1,2,3],[3,2,1])
plt.show()


# syntax hm samajh gye h ab iris mae plot bnate h


plt.scatter(x[:,0],x[:,1])
plt.xlabel("sepal length")
plt.ylabel("sepal width")
plt.title('analysis on iris dataset')
plt.show()

plt.scatter(x[y==0,0],x[y==0,1])   # y==0 ka mtlb h sirf setosa vla aye
plt.xlabel("sepal length")
plt.ylabel("sepal width")
plt.title('analysis on iris dataset')
plt.show()



plt.scatter(x[y==0,0],x[y==0,1]) 
plt.scatter(x[y==1,0],x[y==1,1]) 
plt.scatter(x[y==2,0],x[y==2,1])       
plt.xlabel("sepal length")
plt.ylabel("sepal width")
plt.title('analysis on iris dataset')
plt.show()


plt.scatter(x[y==0,0],x[y==0,1], c= "red", label = "setosa") 
plt.scatter(x[y==1,0],x[y==1,1], c = "green", label = "versicolor") 
plt.scatter(x[y==2,0],x[y==2,1], c= "blue",label = "virginica")       
plt.xlabel("sepal length")
plt.ylabel("sepal width")
plt.title('analysis on iris dataset')
plt.show()


plt.scatter(x[y==0,0],x[y==0,1], c= "red", label = "setosa") 
plt.scatter(x[y==1,0],x[y==1,1], c = "green", label = "versicolor") 
plt.scatter(x[y==2,0],x[y==2,1], c= "blue",label = "virginica")       
plt.xlabel("sepal length")
plt.ylabel("sepal width")
plt.legend()
plt.title('analysis on iris dataset')
plt.show()



# insights: sepal length and sepal width are not good predictors.
# as they cannot differentiate b/w versicolor and verginica flowers





plt.scatter(x[y==0,2],x[y==0,3], c= "red", label = "setosa") 
plt.scatter(x[y==1,2],x[y==1,3], c = "green", label = "versicolor") 
plt.scatter(x[y==2,2],x[y==2,3], c= "blue",label = "virginica")       
plt.xlabel("petal length")
plt.ylabel("petal  width")
plt.legend()
plt.title('analysis on iris dataset')
plt.show()


# petal length and petal width are good predictors










