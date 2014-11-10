import numpy

def luDecompose(matrix):
    """Decomposes a square matrix into two upper and lower triangular matrices.

    Utilizes the Doolittle Algorithm to iteratively decompose a given square
    matrix into a lower triangular matrix L, and an upper triangular matrix U.
    Returns a tuple of (L, U).

    Keyword arguments:
    matrix -- the square matrix to be decomposed (must be parse-able by numpy).
    """

    #Create the matrix if it is not already a numpy matrix
    if matrix !(isinstance(matrix, numpy.matrix)):
        A = numpy.matrix(matrix)
    else:
        A = matrix

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
        A = _swapToUpper(L*A, x)

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

def _swapToUpper(matrix, to):
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
        #Grab the relevant row
        row = A[(x - 1):x]
        #Iterate through the row to find where the first non-zero number resides
        firstOccurrence = 0
        for y in range(0, dimensions[0]):
            value = row[:,y]
            if (value != 0):
                firstOccurrence++
        #If the first integer is not on the diagonal
        if (firstOccurrence != (x-1)):
            swapRowPos = _findSwapRow(A, (x - 1), (x-1))
            rowPos = ((x - 1),x))
            _swap(A, rowPos, swapRowPos);

def _findSwapRow(matrix, startRow, expectedOccurrence):
    """Finds the row with a their first nonzero integer at the expected location

    Goes through the matrix, starting at the given "start" row, looking for a 
    row whose first occurrence of a non-zero value is at the expected
    occurrence.  This will be used for finding rows to swap.  If the appropriate
    row cannot be found, then try to find the row with the next "most-full" row.
    The whole idea is to have the row be swapped into some form that will
    produce a valid LU decomposition, not so much so to swap into a perfect
    form.

    Keyword arguments:
    matrix -- the given matrix to inspect
    expectedOccurrence -- the first expected occurrence of a nonzero value
    limit -- the limit of the error
    """
    
    #Get the dimensions of the matrix
    dimensions = matrix.shape

    nextBestRow = ((0, 1), dimensions[0])

    #For all rows in the matrix
    for x in range(startRow, (dimensions[0] + 1)):
        #Grab the relevent row
        row = matrix[(x - 1):x]
        #Iterate through the row to find where the first non-zero number resides
        firstOccurrence = 0
        for y in range(0, dimensions[1]):
            value = row[:,y]
            if (value != 0):
                firstOccurrence++
        #If the first occurrence is equal to the expected first occurrence
        if (firstOccurrence == expectedOccurrence):
            #We've found the row, return it
            return ((x-1), x)
        #Otherwise, if this is the next best position we've found, replace the
        #nextBestRow with this one
        if (firstOccurrence <= nextBestRow[1]):
            nextBestRow = (((x-1), x), firstOccurrence)
        

    #If we've made it out here, then the row was not found; hand back the next
    #best row we've got. For LU, this will be fine.
    return nextBestRow[0];

    #Otherwise, throw StopIterationException
    raise StopIteration("The swap row was not found")

def _swap(matrix, row1Pos, row2Pos):
    """Swaps two rows in a given matrix

    Swaps two rows, given the position of these two rows.

    Keyword arguments:
    matrix -- the numpy matrix whose rows will be swapped
    row1Pos -- the position of the first row to be swapped
    row2Pos -- the position of the second row to be swapped
    """

    
