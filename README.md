MatrixMaths
==========
_or_
###_The Rise and Fall of Matrix Stardust and the Vectors from Mars_


#####A project aiming to implement the Gauss-Newton method, find the convergence of the power method, and produce a rudimentary 2D animation (full explanations below).  This is a group project, with members (in alphabetical order) [Jayden Gardiner][1], [Brenda Lin][2], and [Mitchell Manguno][3]. So, here is a description of all three parts of the project, in their full glory.


##Methods

###1. The Gauss-Newton Method

Read up on the Gauss-Newton method [here][4].

For this portion of the project, four executable programs will be written (one for each of the curves listed below) that, when run, do the following:

1. prompt the user for a text file containing a list of points,
2. prompt the user for initial guesses for the parameters _a_, _b_, and _c_,
3. prompt the user for a number of iterations to run the Gauss-Newton algorithm, and
4. output the parameters giving the best approximation for the appropriate curve matching the given points.

The curves to be approximated will be the following.

* Quadratic
* Exponential
* Logarithmic
* Rational

These programs will be named `gn_qua`, `gn_exp`, `gn_log`, and `gn_rat`.

In order to do this, the programs will use [Householder Reflections][5] and [Givens Rotations][6] to [QR-factorize][7] the [Jacobian][8] of the input points, and then use a modified version of the Gauss-Newton algorithm to minimize the [residual][9]).

###2. Convergence of the Power Method

For this part of the project, over 1000 2x2 matrices with its values in the boundaries [-2, 2] will be randomly generated. The power method will then be run on all these matrices, which is used to find the doiminant eigenvalue for a matrix. The method will also be run for the matrices' inverses, and also the matrices' determinants will be calculated. This information is graphed using Java FX and written to a txt file, for study and analysis of trends between eigenvalues and determinants.

##External Libraries Used

###[JAMA][10], _A Java Matrix Package_

>JAMA is a basic linear algebra package for Java. It provides user-level classes for constructing and manipulating real, dense matrices. It is meant to provide sufficient functionality for routine problems, packaged in a way that is natural and understandable to non-experts. It is intended to serve as _the_ standard matrix class for Java, and will be proposed as such to the Java Grande Forum and then to Sun. A straightforward public-domain reference implementation has been developed by the MathWorks and NIST as a strawman for such a class.

>> <cite>_JAMA website:_ Background _section_</cite>.

JAMA is a software package in the public domain, and thus may be used freely in this project. Visit [their website][11] for more information on the JAMA software package, or their [licensing section][12] for more information on public use.


[1]: https://github.com/jaydengardiner
[2]: https://github.com/brendaxlin
[3]: https://github.com/mmanguno
[4]: http://en.wikipedia.org/wiki/Gauss%E2%80%93Newton_algorithm
[5]: http://en.wikipedia.org/wiki/Householder_transformation
[6]: http://en.wikipedia.org/wiki/Givens_rotation
[7]: http://en.wikipedia.org/wiki/QR_decomposition
[8]: http://en.wikipedia.org/wiki/Jacobian_matrix_and_determinant
[9]: http://en.wikipedia.org/wiki/Residual_(numerical_analysis)
[10]: http://math.nist.gov/javanumerics/jama/
[11]: http://math.nist.gov/javanumerics/jama/
[12]: http://math.nist.gov/javanumerics/jama/#license
