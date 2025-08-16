package tunudo.dotstudios.net.utilities;

import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;

public class MathUtilities implements MathInterface {
    final int MAX_POW = 999999999;
    @Override
    public MathContext mathContext(BigDecimal precision) {
        MathContext result;
        if(precision.compareTo(new BigDecimal(Integer.MAX_VALUE)) >= 0) {
            result = MathContext.UNLIMITED;
        } else result = new MathContext(precision.intValue());
        return result;
    }
    @Override
    public BigDecimal factorial(BigDecimal factorial) {
        BigDecimal result = factorial;
        if(factorial.compareTo(BigDecimal.ZERO) == 0 || factorial.compareTo(BigDecimal.ONE) == 0)
            return BigDecimal.ONE;

        long factorialLong = factorial.longValue();
        for (long i = 2; i < factorialLong; i++)
            result = result.multiply(BigDecimal.valueOf(i));
        return result;
    }
    /*
    Resource from: http://web.archive.org/web/20080319103147/http://www.java2s.com/Code/Java/Language-Basics/DemonstrationofhighprecisionarithmeticwiththeBigDoubleclass.html
    */
    @Override
    public BigDecimal sqrt(BigDecimal n, BigDecimal precision) {
        if(n.compareTo(BigDecimal.ZERO) < 0)
            throw new ArithmeticException("Cannot sqrt a negative number");
        if(n.compareTo(BigDecimal.ZERO) == 0)
            return BigDecimal.ZERO;
        precision = precision.add(BigDecimal.ONE).setScale(0, RoundingMode.HALF_UP);
        MathContext mc = mathContext(precision);
        BigDecimal x = n
                .divide(BigDecimal.valueOf(3), mc);
        BigDecimal lastX = BigDecimal.valueOf(0);
        for (int i = 0; i < 50; i++) {
            x = n.add(x.multiply(x)).divide(x.multiply(BigDecimal.TWO), mc);
            if (x.compareTo(lastX) == 0)
                break;
            lastX = x;
        }
        return x;
    }
    @Override
    public BigDecimal pow(BigDecimal n, BigDecimal pow, BigDecimal precision) {
        if(pow.compareTo(BigDecimal.ZERO) == 0)
            return BigDecimal.ONE;
        else if(pow.compareTo(BigDecimal.ZERO) < 0)
            return BigDecimal.ONE.divide(pow(n, pow.abs(), precision), mathContext(precision));

        if(pow.compareTo(BigDecimal.valueOf(MAX_POW)) >= 0)
            pow = BigDecimal.valueOf(MAX_POW - 1);
        return n.pow(pow.intValue());
    }
}
