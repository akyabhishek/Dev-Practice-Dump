# -*- coding: utf-8 -*-
"""
Created on Tue Jul 12 13:43:10 2022

@author: ADMIN
"""

import numpy as np
np.__version__

#the basic data structure in numpy is an 
#n-dimensional array

# point: 0d        scalar      array
# line : 1D       vector
# rec or square:2d  matrix
# cube or cuboid :3d  tensor

a = np.array([1,2,3,4,5])

b = np.array([1.1,2.2,3.3,4.4,5.5])

c = np.array([1.1,2.2,3.3,4.4,5.5], dtype = 'float32')  # typecasting


d = np.array([1.1,2.2,3.3,4.4,5.5], dtype = 'int32')




x1 = np.random.randint(10, size = 6)   # vector:1D

x2 = np.random.randint(60, size = (3,4))   # matrix 2d

x3 = np.random.randint(10, size = (3,4,5))   # tensor 3D


x2 = np.random.randint(10, size = (3,4))   # matrix 2d


# x2[rows,cols]


x2[:2,:3]

x2[1,:]

x2[:,3]


# note: when we invoke the slice of an array, it return a view and 
# not a copy

x2_sub = x2[:2,:3]

x2_sub[0,0] = 101

x2_s = x2[:2,:2]

x2_s[:] = 100

x2_sub_copy = x2[:2,:2].copy()
x2_sub_copy[:] = 7


# reshaping can only occur with factors, multiple

grid = np.arange(1,10)

grid.reshape((3,3))


x = np.array([1,2,3])

y = np.array([3,2,1])

# np.concatenate([x,y])

z = np.array([99,99,99])


np.concatenate([x,y,z])


grid = np.array([[1,2,3],[4,5,6]])

np.concatenate([grid,grid])    # column ke along concatenate hua h

#    0 1 2 3  4 5 6
x = [1,4,5,99,4,8,7]

x1,x2,x3 = np.split(x,[3,5])


# numpy is faster than python


# list(range(11))

my_list = list(range(100000))

my_arr = np.array(range(1000000))

%time for i in range(10): my_list2 = my_list * 2
%time for i in range(10): my_arr2 = my_arr * 2

# in case of very large data, it is recomended to 
# use numpy in place of normal python


[1,2,3] * 3

np.array([1,2,3]) * 3

mat = np.array([[1,2],[3,4]])

mat * mat    # multiplication

mat @ mat    # elementwise multiplication # dot product














