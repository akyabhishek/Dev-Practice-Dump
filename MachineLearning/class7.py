# -*- coding: utf-8 -*-
"""
Created on Wed Jul 13 14:24:46 2022

@author: ADMIN
"""

import numpy as np
import pandas as pd
pd.__version__

# a pandas Series is a one-dimensional array
# of indexed data. it can be created from a list
#or array as follows

# data = pd.Series([0.25,0.5,0.75,1.0])

# print(data)

data1 = pd.Series(["red","green","blue","yellow","black",
                  "white"])

print(data1)

data1.values


data1.index


data = pd.Series([0.25,0.5,0.75,1.0])

print(data)

data[1]


data[1:3]


data = pd.Series([0.25,0.5,0.75,1.0],index = ["a","b","c","d"])

# print(data)


# data['b']

# data[1]

data["e"] = 1.25
print(data)

data[1:3]    # slicing by implicit integer index.


data["a":"c"]   # slicing by explicit index


# masking 

data[(data>0.3) & (data<0.8)]


"a" in data

list(data.items())


data.keys()


data = pd.Series([0.25,0.5,0.75,1.0], index = [2,5,3,7])
print(data)

data[2]


population_dict = {"california":"38332521",
                   "texas":"26448193",
                   "new york":"19651127",
                   "florida":"19552860",
                   "illinois":"12882135"}

population = pd.Series(population_dict)

# print(population)

# population["california"]


area_dict = {"california":"423967",
                   "texas":"695662",
                   "new york":"141297",
                   "florida":"170312",
                   "illinois":"149995"}

area = pd.Series(area_dict)

print(area)



states = pd.DataFrame({"population":population,
                       "area":area})

print(states)


states = pd.DataFrame({"p":population,
                       "a":area})

print(states)



pd.Series(5,index=[100,200,300])



pd.Series({2:"a",1:"b",3:"c"})







