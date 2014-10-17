import numpy

def luDecompose(matrix):
    """Decomposes a square matrix into two upper and lower triangular matrices.

    Utilizes the Doolittle Algorithm to iteratively decompose a given square
    numpy.matrix into a lower triangular matrix L, and an upper triangular
    matrix U.  Returns a tuple of (L, U).

    Keyword arguments:
    matrix -- the square, numpy.matrix to be decomposed.
    """

    #Create the matrix
    A = numpy.matrix(matrix)

    #Check if the matrix is square
    dimensions = A.shape

    if dimensions[0] != dimensions[1]:
        raise TypeError("The given matrix was not square")

    #A list of all the generated L matrices
    lMatrices = []

    #Set up the P array, which handles the order of the rows
    #P = numpy.zeros(dimensions[0], dimensions[1])

    #for p in range(0, (dimensions[0] - 1)):
    #        numpy.put(P, (k * (dimensions[0] + 1)), 1)

    #Iterate through the columns of A to calculate each L matrix
    for x in range(0, (dimensions[0] - 1)):
        column = A[:,x:(x+1)]
        L = numpy.zeros((dimensions[0], dimensions[1]))
		#Put 1s on the L's diagonal
        for k in range(0, (dimensions[0] - 1)):
            numpy.put(L, (k * (dimensions[0] + 1)), 1)
        i = 1
        if column[x] == 0:
            continue
        for y in range(x, dimensions[0] - 1):
            lValue = -((column[x+i]) / (column[x]))
            numpy.put(L, (x+(dimensions[0] * (y + 1))), lValue)
            i = (i + 1)

        numpy.put(L, ((dimensions[0]*dimensions[0]) - 1), 1)
        lMatrices.append(L)
        A = L*A

    #Put together all the L matrices (turns it into an ndarray)
    lFinal = numpy.zeros((dimensions[0], dimensions[1]))
    for q in lMatrices:
        lFinal = q + lFinal;

    #Turn the lFinal ndarray into a numpy.matrix
    lFinal = numpy.matrix(lFinal)

    #Negate the values of the L matrix
    for w in range(0, dimensions[0] - 1):
        for h in range(0, dimensions[0] - 1):
            if lFinal[w,h] == 0:
                continue
            negate = -lFinal[w,h]
            numpy.put(lFinal, (w+h), negate)

    #Put 1s on the final L's diagonal
    for m in range(0, (dimensions[0] - 1)):
            numpy.put(lFinal, (m * (dimensions[0] + 1)), 1)

    return lFinal
