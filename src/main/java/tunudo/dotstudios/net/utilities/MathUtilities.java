package tunudo.dotstudios.net.utilities;

import java.math.BigDecimal;
import java.math.MathContext;

public class MathUtilities implements MathInterface {
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

        for (BigDecimal i = BigDecimal.TWO; i.compareTo(factorial) < 0; i = i.add(BigDecimal.ONE))
            result = result.multiply(i);
        return result;
    }
    /*
    Resource from: http://web.archive.org/web/20080319103147/http://www.java2s.com/Code/Java/Language-Basics/DemonstrationofhighprecisionarithmeticwiththeBigDoubleclass.html
    */
    @Override
    public BigDecimal sqrt(BigDecimal n, BigDecimal precision) {
        BigDecimal TWO = BigDecimal.valueOf(2);
        MathContext mc = mathContext(precision);
        BigDecimal x = n
                .divide(BigDecimal.valueOf(3), mc);
        BigDecimal lastX = BigDecimal.valueOf(0);
        for (int i = 0; i < 50; i++) {
            x = n.add(x.multiply(x)).divide(x.multiply(TWO), mc);
            if (x.compareTo(lastX) == 0)
                break;
            lastX = x;
        }
        return x;
    }
    @Override
    public BigDecimal pow(BigDecimal n, BigDecimal pow) {
        return n.pow(pow.intValue());
    }
}
