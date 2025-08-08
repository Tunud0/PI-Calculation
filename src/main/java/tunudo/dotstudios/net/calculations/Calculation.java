package tunudo.dotstudios.net.calculations;

import java.math.BigDecimal;

public abstract class Calculation {
    public abstract BigDecimal calculate(PI.Algorithms algorithm, BigDecimal precision,long customIterations);
    public abstract BigDecimal calculate(PI.Algorithms algorithm, BigDecimal precision);
    public abstract long iterations(long precision, PI.Algorithms algorithm);
}
