import numpy

def luDecompose(matrix):
    """Decomposes a square matrix into two upper and lower triangular matrices.

    Utilizes the Doolittle Algorithm to iteratively decompose a given square
    matrix into a lower triangular matrix L, and an upper triangular matrix U.
    Returns a tuple of (L, U).

    Keyword arguments:
    matrix -- the square matrix to be decomposed (must be parse-able by numpy).
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
        for k in range(0, (dimensions[0])):
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
        #Make the next A, a semi-upper-triangular matrix
        A = swapToUpper(L*A, x)

    #Put together all the L matrices (turns it into an ndarray)
    lFinal = numpy.zeros((dimensions[0], dimensions[1]))
    for q in lMatrices:
        lFinal = q + lFinal;

    #Turn the lFinal ndarray into a numpy.matrix
    lFinal = numpy.matrix(lFinal)

    #Negate the values of the L matrix
    for w in range(0, dimensions[0]):
        for h in range(0, dimensions[0]):
            if lFinal[w,h] == 0:
                continue
            negate = -lFinal[w,h]
            numpy.put(lFinal, ((w * dimensions[0]) + h), negate)

    #Put 1s on the final L's diagonal
    for m in range(0, (dimensions[0])):
            numpy.put(lFinal, (m * (dimensions[0] + 1)), 1)

    return (lFinal, A)

def swapToUpper(matrix, to):
    """Swaps the rows of a matrix so that all first elements are on the diagonal

    Calculates whether or not a square matrix is upper triangular from the first
    row up to a given row.  If not, this will iterate over the selected rows
    and swap them.  Then, it will re-check, and re-swap if necessary.

    Keyword arguments:
    matrix -- the square matrix to be row-swapped into an up-triangular matrix
    to -- the row to swap up to (0 being the first row of the matrix)
    """

    #Quick note: if a matrix has the same number of elements on both the
    #to-swap row and the from-swap row, they will swap infinitely; thus, maybe
    #don't swap if they have equal number? I don't think this will happen in
    #LU decomposition, but maybe? To be safe?

    #Create the matrix
    A = numpy.matrix(matrix)

    #Numpy starts at 1, so accomodate (I will not forego starting at zero)
    to = to + 1;

    #Check if the matrix is square
    dimensions = A.shape
    if dimensions[0] != dimensions[1]:
        raise TypeError("The given matrix was not square")

    #If the to is greater than the number of rows, use the maximum row
    if to >= dimensions[0]:
        to = dimensions[0]

    #For the selected rows
    for x in range(1, (to + 1)):
        row = A[(x - 1):x]
