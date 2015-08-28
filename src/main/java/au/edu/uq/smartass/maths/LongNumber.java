package au.edu.uq.smartass.maths;

import au.edu.uq.smartass.maths.MathsOp;

public class LongNumber extends MathsOp  implements FloatCalculable{

    private long num;

    public LongNumber(long num) {
        setLong(num);
    }

//    public LongNumber(String range) {
//        super();
        //here will be call to RandomChoice(range)
//    }

    public String toString() {
        return "" + num;
    }

    public long getLong() {
        return num;
    }

    public void setLong(long num) {
        this.num = num;
    }

    public double calculate() {
        return num;
    }
}
