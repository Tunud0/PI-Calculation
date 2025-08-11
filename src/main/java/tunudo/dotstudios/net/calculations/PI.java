package tunudo.dotstudios.net.calculations;

import tunudo.dotstudios.net.utilities.MathUtilities;

import java.math.BigDecimal;
import java.math.MathContext;

public class PI extends Calculation {
    private final MathUtilities utilities;
    public enum Algorithms{
        Chudnovsky,
        Ramanujan,
        Gauss_Legendre,
        Madhava_De_Sangamagrama,
        Abraham_Sharp,
        Nilakantha,
        Madhava_Leibniz,
        Euler,
        Wallis,
        Zu_Chongzhi
    }
    public PI() {
        utilities = new MathUtilities();
    }

    @Override
    public long iterations(long precision, Algorithms algorithm) {
        long iterations = switch (algorithm) {
            case Chudnovsky -> precision / 14;
            case Ramanujan -> precision / 6L;
            case Gauss_Legendre -> utilities.sqrt(new BigDecimal(precision), new BigDecimal(1)).longValue();
            case Madhava_De_Sangamagrama,Abraham_Sharp -> precision * (long) 3;
            case Wallis -> precision; //This algorithm is too slow because of product instead of sum, even the precision might be >1000*precision for just some digits

            default -> precision* (long) 5;
        };
        //Add to iterations to prevent accidents in small numbers
        iterations = Math.max(iterations + 1, 1);
        return iterations;
    }

    @Override
    public BigDecimal calculate(Algorithms algorithm, BigDecimal precision,long customIterations) {
        if(precision.compareTo(BigDecimal.ZERO) < 0)
            return BigDecimal.ZERO;
        else if(precision.compareTo(BigDecimal.ONE) <= 0)
            //if it is 0 or 1, the for cycle will not start, it needs to be grater than 1
            precision = precision.add(BigDecimal.TWO.subtract(precision));
        else precision = precision.add(BigDecimal.ONE);
        MathContext mathContext = utilities.mathContext(precision);
        BigDecimal result = BigDecimal.ZERO,
                partialResult;
        //Iterations -> amount of times a for loop executes.
        long precisionLong = precision.longValue();
        long iterations;
        //Custom iterations, to test the algorithms.
        if(customIterations > 0)
            //If the iterations are more than 0, than they will be applied as the value of the variable.
            iterations = customIterations;
        //if the iterations are <= 0, they will be the result of the convergence rate.
        else iterations = iterations(precisionLong,algorithm);
        BigDecimal three = BigDecimal.valueOf(3),
                four = BigDecimal.valueOf(4);
        switch (algorithm) {
            case Chudnovsky: {
                for (long i = 0; i < iterations; i++) {
                    BigDecimal bigI = BigDecimal.valueOf(i);
                    BigDecimal first = new BigDecimal(6).multiply(bigI);
                    first = utilities.factorial(first);
                    BigDecimal second = new BigDecimal(545140134).multiply(bigI).add(new BigDecimal(13591409));

                    partialResult = first.multiply(second);

                    first = three.multiply(bigI);
                    first = utilities.factorial(first);
                    second = utilities.factorial(bigI);
                    second = utilities.pow(second, three,precision);
                    BigDecimal third = new BigDecimal("-262537412640768000");
                    third = utilities.pow(third, bigI, precision);

                    partialResult = partialResult.divide(first.multiply(second).multiply(third), mathContext);
                    result = result.add(partialResult);
                }
                result = new BigDecimal(426880).multiply(utilities.sqrt(new BigDecimal(10005),precision)).divide(result, mathContext);
                return result;
            }
            case Ramanujan: {
                /*
                Formula: π = 99^2 / 2sqrt(2) * Σ(k = 0,∞) ( 4k! * 26390k + 1103 / k!^4 * 396^4k )
                Time complexity:
                */
                for (long i = 0; i < iterations; i++) {
                    BigDecimal bigI = BigDecimal.valueOf(i);
                    BigDecimal first = four.multiply(bigI);
                    first = utilities.factorial(first);
                    BigDecimal second = new BigDecimal(26390).multiply(bigI);
                    second = second.add(new BigDecimal(1103));

                    partialResult = first.multiply(second);

                    first = bigI;
                    first = utilities.factorial(first);
                    first = utilities.pow(first, four,precision);
                    second = new BigDecimal(396);
                    second = utilities.pow(second, four.multiply(bigI),precision);

                    partialResult = partialResult.divide(first.multiply(second), mathContext);
                    result = result.add(partialResult);
                }
                result = result.multiply(BigDecimal.TWO.multiply(utilities.sqrt(BigDecimal.TWO,precision)));
                result = new BigDecimal(9801).divide(result, mathContext);
                return result;
            }
            case Gauss_Legendre: {
                //a0 = 1,b0 = 1/sqrt(2),p0 = 1,t0 = 1/4
                //a = a + b / 2
                //b = sqrt(a * b)
                //p = (2*p)
                //t = t - p(a - a)^2
                //π = (a + b)^2 / 4*t
                BigDecimal an = BigDecimal.ONE,
                        bn = BigDecimal.ONE.divide(utilities.sqrt(BigDecimal.TWO,precision), mathContext),
                        tn = BigDecimal.ONE.divide(four, mathContext),
                        pn = an;
                for (long i = 0; i < iterations; i++) {
                    BigDecimal an1 = an.add(bn);
                    an1 = an1.divide(BigDecimal.TWO, mathContext);
                    BigDecimal bn1 = an.multiply(bn);
                    bn1 = utilities.sqrt(bn1, precision);
                    BigDecimal tn1 = an.subtract(an1);
                    tn1 = utilities.pow(tn1, BigDecimal.TWO,precision);
                    tn1 = tn1.multiply(pn);
                    tn1 = tn.subtract(tn1);
                    BigDecimal pn1 = pn.multiply(BigDecimal.TWO);
                    an = an1;
                    bn = bn1;
                    tn = tn1;
                    pn = pn1;
                }
                result = an.add(bn);
                result = utilities.pow(result,BigDecimal.TWO,precision);
                result = result.divide(tn.multiply(four), mathContext);
                return result;
            }
            case Madhava_De_Sangamagrama: {
                for (long i = 0; i < iterations; i++) {
                    BigDecimal bigI = BigDecimal.valueOf(i);
                    BigDecimal first = utilities.pow(BigDecimal.ONE.negate(), bigI,precision);
                    partialResult = first;
                    first = BigDecimal.TWO.multiply(bigI).add(BigDecimal.ONE);
                    first = first.multiply(utilities.pow(three,bigI,precision));
                    partialResult = partialResult.divide(first, mathContext);
                    result = result.add(partialResult);
                }
                result = result.multiply(utilities.sqrt(new BigDecimal(12),precision), mathContext);
                return result;
            }
            case Abraham_Sharp: {
                for (long i = 0; i < iterations; i++) {
                    BigDecimal bigI = BigDecimal.valueOf(i);
                    BigDecimal first = utilities.pow(BigDecimal.ONE.negate(), bigI,precision);
                    first = first.multiply(utilities.pow(three,bigI.negate(),precision));
                    partialResult = first;
                    first = bigI.multiply(BigDecimal.TWO).add(BigDecimal.ONE);
                    partialResult = partialResult.divide(first, mathContext);
                    result = result.add(partialResult);
                }
                result = result.multiply(BigDecimal.TWO.multiply(utilities.sqrt(three,precision)),mathContext);
                return result;
            }
            case Nilakantha: {
                //π = 3 + 4*Σ(k = 0,∞) (-1)^n / (2n + 3)^3 - (2n + 3)
                for (long i = 0; i < iterations; i++) {
                    BigDecimal bigI = BigDecimal.valueOf(i);
                    partialResult = utilities.pow(BigDecimal.ONE.negate(),bigI,precision);
                    //2n + 3
                    BigDecimal TWOmNp3 = BigDecimal.TWO.multiply(bigI).add(three);
                    BigDecimal first = utilities.pow(TWOmNp3,three,precision);
                    first = first.subtract(TWOmNp3);
                    partialResult = partialResult.divide(first, mathContext);

                    result = result.add(partialResult);
                }
                result = result.multiply(four).add(three,mathContext);
                return result;
            }
            case Madhava_Leibniz: {
                //π = 4 * Σ(k = 0,∞) 1 / 2k + 1
                for (long i = 0; i < iterations; i++) {
                    BigDecimal bigI = BigDecimal.valueOf(i);
                    partialResult = utilities.pow(BigDecimal.ONE.negate(),bigI,precision);
                    partialResult = partialResult.divide(BigDecimal.TWO.multiply(bigI).add(BigDecimal.ONE), mathContext);
                    result = result.add(partialResult);
                }
                result = result.multiply(four,mathContext);
                return result;
            }
            case Euler: {
                //π = sqrt(6 * Σ(k = 1,∞) 1/(k^2)
                for (long i = 1; i < iterations; i++) {
                    BigDecimal bigI = BigDecimal.valueOf(i);
                    partialResult =  BigDecimal.ONE.divide(utilities.pow(bigI,BigDecimal.TWO,precision),mathContext);

                    result = result.add(partialResult);
                }
                result = result.multiply(new BigDecimal(6));
                result = utilities.sqrt(result,precision);
                return result;
            }
            case Wallis: {
                result = BigDecimal.ONE;
                for (long i = 1; i < iterations; i++) {
                    BigDecimal bigI = BigDecimal.valueOf(i);
                    BigDecimal twoN = BigDecimal.TWO.multiply(bigI);
                    BigDecimal first = twoN;
                    first = first.divide(twoN.subtract(BigDecimal.ONE),mathContext);
                    partialResult = first;
                    first = twoN;
                    first = first.divide(twoN.add(BigDecimal.ONE),mathContext);
                    partialResult = partialResult.multiply(first);
                    result = result.multiply(partialResult);
                }
                result = result.multiply(BigDecimal.TWO,mathContext);
                return result;
            }
            case Zu_Chongzhi: {
                return new BigDecimal(355).divide(BigDecimal.valueOf(113),mathContext);
            }
            default: return BigDecimal.ZERO;
        }
    }

    @Override
    public BigDecimal calculate(Algorithms algorithm, BigDecimal precision) {
        return calculate(algorithm, precision,0);
    }
}
