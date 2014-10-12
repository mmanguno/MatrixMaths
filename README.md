MatrixMaths
==========
_or_
###_The Rise and Fall of Matrix Stardust and the Vectors from Mars_

----

#####A project aiming to implement the Gauss-Newton method, find the convergence of the power method, and produce a rudimentary 2D animation (full explanations below).  This is a group project, with members (in alphabetical order) [Jayden Gardiner](), [Brenda Lin](), and [Mitchell Manguno](https://github.com/mmanguno) (author of the readme). So, here is a description of all three parts of the project, in their full glory.
----

###1. The Gauss-Newton Method

Read up on the Gauss-Newton method [here](http://en.wikipedia.org/wiki/Gauss%E2%80%93Newton_algorithm_).

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

In order to do this, the programs will use Householder Reflections and Givens Rotations to QR-factorize the input points, and then use a modified version of the Gauss-Newton algorithm.

###2. Convergence of the Power Method

_decription to come..._

###3. Animation in Two Dimensions

_description to come..._


