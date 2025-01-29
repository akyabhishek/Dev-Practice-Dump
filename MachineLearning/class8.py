# -*- coding: utf-8 -*-
"""
Created on Mon Jul 18 13:46:41 2022

@author: ADMIN
"""

import numpy as np
import pandas as pd
import matplotlib.pyplot as plt
import seaborn as sns

ipl = pd.read_csv("matches.csv")

top5 = ipl.head()

top10 = ipl.head(10)

ipl["player_of_match"].value_counts()

ipl["player_of_match"].value_counts().head(10)


ipl["player_of_match"].value_counts()[0:10]

ipl["player_of_match"].value_counts()[0:5]


list(ipl["player_of_match"].value_counts()[0:5].keys())


ipl["player_of_match"].value_counts()[0:5]


plt.bar(list(ipl["player_of_match"].value_counts()[0:5].keys()),
        ipl["player_of_match"].value_counts()[0:5],
        color = "red")

a = list(ipl["player_of_match"].value_counts()[0:5].keys())

b = ipl["player_of_match"].value_counts()[0:5]


plt.bar(a,b,color = "green")
plt.show()


ipl["result"].value_counts()

ipl["toss_winner"].value_counts()


ipl["winner"].value_counts()


batting_first = ipl[ipl["win_by_runs"]!=0]

plt.hist(batting_first["win_by_runs"])
plt.title("distribution of runs")
plt.xlabel("runs")
plt.ylabel("match")
plt.show()


batting_first["winner"].value_counts()


c = list(batting_first["winner"].value_counts()[0:3].keys())

d = batting_first["winner"].value_counts()[0:3]


plt.bar(c,d,color = ["blue","red","green"])


















