#!/usr/bin/env python
"""Contains functions that multiply matrices together"""

import numpy

__author__ = "Jayden Gardiner"
__version__ = "1.0"
__date__ = "2014 November 7"
__credits__ = ["Mitchell Manguno"]

__maintainer__ = "Jayden Gardiner"
__email__ = "jgardiner@gatech.edu"
__status__ = "Prototype"

def multiply(x, y):
	"""
	This function will multiply two matrices.

	This function to multiply two matrices will return a new matrix after
    iterating through the rows and columns of the matrices and calculating the 
    new values.

    Keyword arguments:
    x -- the first matrix to multiply
	y -- the second matrix to multiply
	"""
    #Type checking x and y as numpy matrices
    if x !(isInstance(matrix, numpy)):
        x = numpy.matrix(matrix)
    if y !(isInstance(matrix, numpy)):
        y =  numpy.matrix(matrix)
    #Creates z new matrix to return
    z = numpy.matrix(matrix)
    #iterate through rows of x
    for i in range(len(x)):
        #iterate through columns of y
        for j in range(len(y[0])):
            #iterate through rows of y
            for k in range(len(y)):
                z[i][j] += x[i][k] * y[k][j]
    #return new matrix
    return z
