# -*- coding: utf-8 -*-
"""
Created on Tue Jul  5 11:03:44 2022

@author: ADMIN
"""

import numpy as np
import pandas as pd
import matplotlib.pyplot as plt
import seaborn as sns

dataset = pd.read_csv("student.csv")

dataset.columns = ["gender",
                  "race",
                  "ped",
                  "lunch",
                  "test",
                  "math",
                  "reading",
                  "writing"]

dataset.describe()

# histogram


plt.hist(dataset["math"])
plt.show()

plt.hist(dataset["reading"])
plt.show()

dataset["gender"].value_counts()

values = dataset["gender"].value_counts().values

index = dataset["gender"].value_counts().index

sns.barplot(index,values, hue = ["female","male"])

sns.barplot(dataset["gender"],dataset["math"],
            hue = dataset["gender"])

sns.barplot(dataset["gender"],dataset["reading"],
            hue = dataset["gender"])


sns.barplot(dataset["gender"],dataset["writing"],
            hue = dataset["gender"])


# insights
#1. males are better on average than females at math
#2. females are better on average than males at reading
# and writing
#3. females tend to fail more than males irrespective of the subject.

sns.barplot(dataset["race"],dataset["math"],
            hue = dataset["gender"])


sns.barplot(dataset["ped"],dataset["math"],
            hue = dataset["gender"])

sns.boxplot(dataset["math"])

sns.boxplot(dataset["reading"])


sns.boxplot(dataset["gender"],dataset["math"])


sns.boxplot(dataset["gender"],dataset["reading"])

sns.boxplot(dataset["gender"],dataset["writing"])


# plt.scatter([1,2,3],[3,2,1])


# plt.plot([1,2,3],[3,2,1])

plt.scatter(dataset["math"],dataset["reading"])

plt.show()

plt.scatter(dataset["math"],dataset["writing"])

plt.show()


pd.plotting.scatter_matrix(dataset)

sns.pairplot(dataset)


# summarry 
# histogram
# barplot
# boxplot
# scatter
# scatter_matrix
# pairplot








