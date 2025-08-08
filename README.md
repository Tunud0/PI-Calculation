# PI

A library for calculating the value of π.

## Features

- **Selectable type of algorithm you want to execute the calculation**
- **Selectable amount of digits you want to calculate**

## To instance the class and calculate:
```java
import java.math.BigDecimal;
import tunudo.dotstudios.net.calculations.PI;

public static void main(String[] args) {
    Pi pi = new PI();
    Algorithm algorithm = PI.Algorithms.Ramanujan; // = type of algorithm you want to make the calculation with;
    BigDecimal precision = new BigDecimal(1000); // = numbers after the decimal point you want;
    BigDecimal pi = new PI().calculate(algorithm,precision);
    
    System.out.println("The value of PI is: " + pi); //Returns: 3.1415926535
}
```

## You can choose from the following algorithms:
### Recent algorithms that are often used for massive computations.
### - Chudnovsky:

Extremely precise, but slow. It is used in world-record calculations of π digits.

$$π= {{426880\sqrt{10005}} \over {\sum_{k=1}^∞ {{{(6k)!(545140134k + 13591409)}} \over { (3k)!(k!)^3(-262537412640768000)^k } }}}$$

Time complexity: `O(n(log n)^3)`

Convergence rate: $$precision \over 14$$

### - Ramanujan:

A precise algorithm similar to the Chudnovsky, but a bit faster.

$$π= {{99^2} \over {{2\sqrt(2)}} \sum_{k=1}^∞ {{{(4k)!(26390k + 1103)}} \over { (k!)^4(396)^(4k) } }}$$

Time complexity: `O(n(log n)^2)`

Convergence rate: $$precision \over 6$$

### - Gauss Legendre:
Famous for its quadratic convergence, it is a fast and efficient algorithm.
Its formula:

Variable initialization:

$$a_{0} = 1,b_{0} = 1,t_{0} = {{1} \over {4}},p_{0} = 1$$

Iterations:

$$a = {{a_{0} + b_{0}} \over {2}}$$

$$b = \sqrt(a_{0}b_{0})$$

$$t = t_{0} - p_{0}(a_{0} - a)^2$$

$$p = 2p$$

Final formula:

$$π = {{(a + b)^2} \over 4t}$$

Time complexity: `O(n(logn)^2)`

Convergence rate: $$\sqrt{precision}$$
### Formulas that are from 15th to 19th centuries, and that are not suitable for out computational standards, because of their slow convergence.
### - Madhava De Sangamagrama:
An algorithm from 14th century

$$π= \sqrt{12} \cdot \sum_{k = 0}^∞ {(-1)^k \over (2k + 1)3^k}$$

Time complexity: `O(n)`

Convergence rate: $$3 \cdot precision$$
### - Nilakantha:
An algorithm from 15th century

$$π= \sum_{k \geq 0} {{(-1)^k} \over {(2k + 3)^3 - (2k + 3)}}$$

Time complexity: `O(n)`

Convergence rate: $$> 1000$$
### - Madhava Leibniz:
An old algorithm from 1674 simple to implement.

$$π= {4}\cdot\sum_{k=1}^∞ {(-1)^k \over { 2k + 1 } }$$

Time complexity: `O(n)`

Convergence rate: $$> 1000$$
### - Euler:
An algorithm from the Euler sequence.

$$π= \sqrt{6\cdot \sum_{k \geq 1} {1 \over k^2}}$$

Time complexity: `O(n)`

Convergence rate: `Too high`
### - Wallis:
An algorithm discovered 1675.

$$π= 2 \cdot \prod_{k=1}^∞ {2k \over 2k - 1} \cdot {2k \over 2k + 1}$$

Time complexity: `O(n)`

Convergence rate: $$> 1000$$