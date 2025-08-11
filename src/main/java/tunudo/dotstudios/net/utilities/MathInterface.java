package tunudo.dotstudios.net.utilities;

import java.math.BigDecimal;
import java.math.MathContext;

public interface MathInterface {
    MathContext mathContext(BigDecimal precision);
    BigDecimal factorial(BigDecimal factorial);
    BigDecimal sqrt(BigDecimal n,BigDecimal precision);
    BigDecimal pow(BigDecimal n,BigDecimal pow, BigDecimal precision);
}
