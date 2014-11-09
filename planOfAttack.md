Plan of Attack
==

###Part 1: The Gauss-Newton Method

Taking in a list of *n* pairs of floating point numbers of the form
[(*x*<sub>1</sub>,*y*<sub>1</sub>),(*x*<sub>2</sub>,*y*<sub>2</sub>),...,(*x*<sub>n</sub>,*y*<sub>n</sub>)],
a triple of floating point numbers (*a*<sub>0</sub>, *b*<sub>0</sub>, *c*<sub>0</sub>), and a number of iterations *N*,

Solve for *β*, where *β<sub>n+1</sub> = β<sub>n</sub> - x*, where *x* is defined by the equation *Rx = Q<sup>T</sup>r*, such that:

+ *r* is the vector of residuals, a vector with *n* coordinates, such that the i<sup>th</sup> coordinate is defined

+ *Q* and *R* come from the QR Factorization of the Jacobian of *r*

The Jacobian of r is defined as an *n x 3* matrix so that the *ij*-entry is

J<sub>*i j*</sub> = *<sup>δr<sub>i</sub>(β)</sup> / <sub>δβ<sub>j</sub></sub>*

Initialize *r*, the vector of residuals, to be a vector with *n* coordinates so that the i<sup>th</sup> coordinate is *r<sub>i</sub> = y<sub>i</sub> − f<sub>β<sub>1</sub>,β<sub>2</sub>,β<sub>3</sub></sub>
( x<sub>i</sub> )*.  

The function *f<sub>β<sub>1</sub>,β<sub>2</sub>,β<sub>3</sub></sub>
( x<sub>i</sub> )* refers to one of the following:

+  Quadratic: *f<sub>a,b,c</sub>(x) = ax<sup>2</sup> + bx + c*

+  Exponential: *f<sub>a,b,c</sub>(x) = ae<sup>bx</sup> + c*

+  Logarithmic: *f<sub>a,b,c</sub>(x) = a log(x+b) + c*

+  Rational: *f<sub>a,b,c</sub>(x) = <sup>ax</sup>/<sub>x+b</sub> + c*
