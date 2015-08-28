package au.edu.uq.smartass.maths;

import java.math.*;

public class DecimalNumber extends MathsOp {
    private BigDecimal num;
    private int scale = 2;
    private RoundingMode mode = RoundingMode.HALF_UP;

    //This set of constructors uses default scale and RoundingMode
    public DecimalNumber(double num) {
        super();
        this.num = new BigDecimal(num).setScale(scale, mode);
    }

    public DecimalNumber(String num) {
        super();
        this.num = new BigDecimal(num).setScale(scale, mode);
    }

    public DecimalNumber(BigDecimal num) {
        super();
        this.num = num;
        scale=num.scale();
    }

    //This set of constructors sets scale of DecimalNumber using default RoundingMode
    public DecimalNumber(double num, int scale) {
        super();
        this.num = new BigDecimal(num).setScale(this.scale=scale, mode);
    }

    public DecimalNumber(String num, int scale) {
        super();
        this.num = new BigDecimal(num).setScale(this.scale=scale, mode);
    }

    public DecimalNumber(BigDecimal num, int scale) {
        super();
        this.num = num.setScale(this.scale=scale, mode);
    }

    //The set of constructors with full control of scale and RoundingMode
    public DecimalNumber(double num, int scale, RoundingMode mode) {
        super();
        this.num = new BigDecimal(num).setScale(this.scale=scale, this.mode = mode);
    }

    public DecimalNumber(String num, int scale, RoundingMode mode) {
        super();
        this.num = new BigDecimal(num).setScale(this.scale=scale, this.mode = mode);
    }

    public DecimalNumber(BigDecimal num, int scale, RoundingMode mode) {
        super();
        this.num = num.setScale(this.scale=scale, this.mode = mode);
    }

    //Use a MathContext instead of a scale or rounding mode
    public DecimalNumber(double num, MathContext mc) {
        this(new BigDecimal(num, mc));
    }
    
    public DecimalNumber(String num, MathContext mc) {
        this(new BigDecimal(num, mc));
    }
    public String toString() {
        return num.toString();
    }

    public void setScale(int new_scale) {
        num = num.setScale(this.scale=new_scale, mode);
    }

    public int getScale() {
        return num.scale();
    }

    public void setRoundingMode(RoundingMode new_mode) {
        mode = new_mode;
    }

    public RoundingMode getRoundingMode() {
        return mode;
    }

    public DecimalNumber trimZeros() {
        DecimalNumber res;
        BigDecimal n= num;
        try {
            while (n.scale()>0) {
                n = n.setScale(n.scale()-1);
            }
        } catch (ArithmeticException e) {
            // no more trailing zeroes so exit.
        }
        res= new DecimalNumber(n);
        res.setRoundingMode(mode);
        return res;
    }

}