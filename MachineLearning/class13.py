# -*- coding: utf-8 -*-
"""
Created on Tue Jul 26 10:56:39 2022

@author: ADMIN
"""

import pandas as pd
from matplotlib import pyplot as plt

df = pd.read_csv("insurance_data.csv")


plt.scatter(df.age,df.bought_insurance,marker = "+", color="red")


from sklearn.model_selection import train_test_split
x_train,x_test,y_train,y_test = train_test_split(df[["age"]],
                                 df.bought_insurance,train_size=0.75)


from sklearn.linear_model import LogisticRegression
model = LogisticRegression()

model.fit(x_train,y_train)

y_predicted = model.predict(x_test)


model.score(x_test,y_test)


