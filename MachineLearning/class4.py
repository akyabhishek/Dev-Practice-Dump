# -*- coding: utf-8 -*-
"""
Created on Sat Jul  9 10:49:21 2022

@author: ADMIN
"""

import numpy as np
np.__version__

#the basic data structure in numpy is an 
#n-dimensional array

# point: 0d        scalar      array
# line : 1D       vector
# rec or square:2d  matrix
# cube or cuboid   tensor

a = np.array([1,2,3,4,5])

b = np.array([1.1,2.2,3.3,4.4,5.5])

c = np.array([1.1,2.2,3.3,4.4,5.5], dtype = 'float32')  # typecasting


d = np.array([1.1,2.2,3.3,4.4,5.5], dtype = 'int32')



e = np.array([1])
f = np.array([[1]])
g = np.array([[[1]]])

e.ndim
f.ndim
g.ndim

h = np.array([range(i,i+3) for i in [2,4,6]])



# for i in range(2,5):
#     print(i)

# for i in range(4,7):
#     print(i)

# for i in range(6,9):
#     print(i)


# i = np.zeros(10)

i = np.zeros(10,dtype = 'int')

ii = np.zeros((2,5))

# np.ones(10)

j = np.ones((3,5), dtype = 'int')

k = np.full((3,5), 3.14)

# range.......numpy mae arange  bnadiya h...

np.arange(1,11)

np.arange(0,20,3)

np.arange(0,20)     # upper limit 19 h

m = np.linspace(0,20)   # upper limit 20 h

np.linspace(0,10,5)

np.linspace(0,10,3)

# for Da,ml,ai,nlp,......yeh sbhi probailistic
# random things are very important

n=np.random.randn(4,4)    # standard normal distribution
o= np.random.rand(4,4)   # uniform distribution
p = np.random.randint(4)  # discrete uniform distribution

q = np.random.randint(0,10,(4,4))

r = np.eye(4)

s = np.empty((3,3))    # garbage value

# random value kisi distribution se ari h.....to vo puri trhe random
# nhi h
# garbage mae kuch bhi ho skta h











