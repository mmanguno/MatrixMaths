import numpy

def multiply(x, y):
	"""
	This function will multiply two matrices.

	This function to multiply two matrices will
	return a new matrix after iterating
	through the rows and columns of the
	matrices and calculating the new values.

	x, y: Matrices
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