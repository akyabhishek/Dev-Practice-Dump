# -*- coding: utf-8 -*-
"""
Created on Thu Jul 21 13:27:41 2022

@author: ADMIN
"""
"""
python
da(data analyse , visualisation,data preprocessing)
ml(product, model)

  x    y
co2   temp
 1      1
 2      4
 3      9
 4     16
 5     25
 6     36
 7     49

x =y**2



classification of ml
1. supervised learning(x,y) (labelled data)
2. unsupervised learning(x,) (unlabelled data)
3. reinforcement learning.



variables
1. continous variable:  float, integer
2. categorical variable: string


families
1. regression family of algo.(continous in nature)
     - linear 
     
2. classification family of algo.(categorical in nature)

supervised learning
  1. regression family of algo.(continous in nature)
      - linear regression algorithm
      
 2. classification family of algo.(categorical in nature)
   - logistic regression

"""

# Q. i want to predict temperature?
# A. regression family of algo(continous in nature)

# Q. i want to predict whether it is cat or dog
# A. classification family of algo (categorical in nature)

# Q. i want to predict blood pressure
# A. regression family

# Q. i want to predict has person a cancer or not ?
# A. classification family


"""
 x       Y
area   price
100     20000
200     32000
300     44000
400     38000
500     60000
"""

import numpy as np
import pandas as pd
from sklearn import linear_model
import matplotlib.pyplot as plt

d = {"area":[100,200,300,400,500],
     "price":[20000,32000,44000,38000,60000]}

data = pd.DataFrame(d)
print(data)

# data = pd.read_csv("dataset")

plt.scatter(data.area,data.price, color = "red")
plt.xlabel("area")
plt.ylabel("price")
plt.show()


area = data[["area"]]
print(area)


price = data[["price"]]
print(price)


# y = mx +c
# x area
# y price
# m slope
# c intercept


# create linear regression object.

reg = linear_model.LinearRegression()

reg.fit(area,price)


reg.predict([[320]])

reg.predict([[600]])

reg.coef_

reg.intercept_



plt.scatter(data.area,data.price, color = "red")
plt.plot(data.area,reg.predict(area), color = "blue")
plt.xlabel("area")
plt.ylabel("price")
plt.show()













