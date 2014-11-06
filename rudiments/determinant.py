import numpy

def laplaceDeterminant(matrix);
    """Finds the determinant of a given matrix with Laplace expansion

    Uses recursive calls to itself in order to find the determinant of a given
    square matrix.  Takes approximately O(n!) to complete, which is certainly 
    not optimal.  Use this function if and only if the method of finding
    determinants by LU Decomposition is not completed.  This is intended as a 
    backup

    Keyword arguments:
    matrix -- the matrix to find the determinant of
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

    #Base case: is the matrix a 2 X 2 matrix?
    if dimensions[0] == 2:
        #If so, get a, b, c, and d, and return their determinant
        a = A[0:1,0:1]
        b = A[0:1,1:2]
        c = A[1:2,0:1]
        d = A[1:2,1:2]
        return ((a*d) - (b*c))
    else:#The general case: perform the Laplace expansion
        #Compose a list of all minor matrices
        minors = []
        for x in range(0, dimensions[0]):
            coefficient = A[0:1,(x):(x+1)] * (-1)^x
            #Construct the minorMatrix
            rawMinor = numpy.matrix(A[1:dimensions[0])
            #Painfully inefficient, but the only way to get them
            thisMinor = []
            for i in range(1, rawMinor.shape[0] + 1):
                row = []
                for j in range(1, rawMinor.shape[1] + 1):
                    if ((j - 1) != x):
                        row.append(rawMinor[i-1:i,j-1,j].tolist().pop().pop())
                thisMinor.append(row);
            minorMatrix = numpy.matrix(thisMinor)
            val = coefficient * laplaceDeterminant(minorMatrix)
            minors.append(val)
        #Iterate through the determinants of the minors' determinants, adding
        determinant = 0
        for (det : minors):
            determinant += det
        return det
