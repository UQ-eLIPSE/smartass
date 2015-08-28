package au.edu.uq.smartass.python;

import au.edu.uq.smartass.auxiliary.RandomChoice;

import java.util.Vector;

/**
 * Describe class RandomPythonGen here.
 *
 *
 * Created: Mon Dec 15 13:36:04 2008
 *
 * @author <a href="mailto:ilm@morot.maths.uq.edu.au">Ivan Miljenovic</a>
 * @version 1.0
 */
public class RandomPythonGen {

    private static final int MIN = 2;
    private static final int MAX = 9;

    private static final String[] MESSAGES
        = {"shoeless monkeys", "bottles of beer on the wall", "mournful mathematicians",
           "successfull scientists", "playful physicists", "bored biologists",
           "crazy chemists", "drunk doctors", "paranoid programmers",
           "amazing academics"};

    /**
     * Creates a new <code>RandomPythonGen</code> instance.
     *
     */
    public static PythonFunction randomIntFunc(String funcName, String var, int numNums, boolean doPrint) {

        PythonMathsVariable x = new PythonMathsVariable(var);

        PythonMaths expr = randomExpr(x, numNums);

        PythonFunction func = new PythonFunction(funcName, x);

        if (doPrint) {
            func.add(new PythonPrint(x));
        }

        func.addFuncCommand(expr);

        return func;
    }

    public static PythonFunction randomIntFunc(String funcName, String var, int numNums) {
        return randomIntFunc(funcName, var, numNums, RandomChoice.randBool());
    }

    public static PythonFunction randomFunc(PythonMathsVariable x) {
        return new PythonFunction(randLetter(), x);
    }

    public static PythonMaths randomExpr(PythonMathsVariable x, int numNums) {
        return randomExpr(x, numNums, MIN, MAX, true);
    }

    public static PythonMaths randomExpr(PythonMathsVariable x, int numNums,
                                         int min, int max, boolean canSubtract) {

        Vector<PythonMaths> vals = createInts(numNums, min, max);

        vals.add(x);

        //Sometimes use the variable twice, just to make it interesting.
        if ((numNums < 1)  || RandomChoice.randInt(0,5) == 0) {
            vals.add(x);
        }

        PythonMaths left, right, op;

        //This isn't perfect: quite often we create nonsense expressions that
        //can be simplified, but it should suffice.
        while (vals.size() > 1) {
            left = chooseElem(vals);
            right = chooseElem(vals);

            if (left instanceof PythonNumber && right instanceof PythonNumber) {
                //if we're using only numbers, the op is going to be boring.
                vals.add(left);
                vals.add(right);
            } else {
                //Not both numbers, create an op using them.
                if (canSubtract) {
                    vals.add(randomOp(left, right));
                } else {
                    vals.add(randomIncOp(left, right));
                }
            }
        }

        return vals.elementAt(0);
    }

    public static PythonMaths randomIncExpr(PythonMathsVariable x, int numNums,
                                            int min, int max) {
        return randomExpr(x, numNums, min, max, false);
    }

    public static PythonMaths randomIncExpr(PythonMathsVariable x) {
        return randomIncExpr(x, 1, 2, 3);
    }

    public static PythonIf randModIf(PythonMathsVariable x) {
        PythonNumber mod = randomNum(2, 3);
        PythonNumber eq = randomNum(0, (int)mod.intValue()-1);

        PythonLogic check = new PythonEq(new PythonMod(x, mod), eq);

        return new PythonIf(check);
    }

    public static PythonConstruct randReturn(PythonFunction func,
                                             PythonMathsVariable x,
                                             int numNums) {
        PythonMaths expr = randomExpr(x, numNums);

        return func.funcReturn(expr);
    }

    private static char randChar() {
        return (char)RandomChoice.randInt((int)'a', (int)'z');
    }

    private static String randLetter() {
        return new String(new char[] {randChar()});
    }

    public static PythonMathsVariable randomVar() {
        return new PythonMathsVariable(randLetter());
    }

    public static PythonArrayVariable randomAVar() {
        char x;

        do {
            x = randChar();
        } while (x == 'a');

        String name = new String(new char[] {x, 's'});

        return new PythonArrayVariable(name);
    }

    public static PythonNumber randomNum(int min, int max) {
        return new PythonNumber(RandomChoice.randInt(min,max));
    }

    public static PythonNumber randomNum() {
        return randomNum(MIN, MAX);
    }

    public static PythonAssignment assignRandNum(PythonMathsVariable x,
                                                 int min, int max) {
        return new PythonAssignment(x, randomNum(min, max));
    }

    public static PythonAssignment assignRandNum(PythonMathsVariable x) {
        return assignRandNum(x, MIN, MAX);
    }

    public static PythonAssignment assignRandZeros(PythonArrayVariable xs,
                                                   int min, int max, boolean isInt) {
        return new PythonAssignment(xs, new PythonZeros(randomNum(min,max), isInt));
    }

    private static Vector<PythonMaths> createInts(int num_ints, int min, int max) {
        Vector<PythonMaths> nums = new Vector<PythonMaths>(num_ints);

        for (int i = 0; i < num_ints; i++) {
            nums.add(randomNum(min,max));
        }

        return nums;
    }

    private static PythonMaths chooseElem(Vector<PythonMaths> vals) {
        return vals.remove(RandomChoice.randInt(0,vals.size() - 1));
    }

    public static PythonBinaryOp randomOp(PythonMaths left, PythonMaths right) {

        switch (RandomChoice.randInt(1,8)) {
        case 1:
        case 2:
        case 3: return new PythonAdd(left, right);
        case 4:
        case 5: return new PythonSubtract(left, right);
        default: return new PythonMult(left,right); //6,7,8
        }
    }

    public static PythonBinaryOp randomIncOp(PythonMaths left, PythonMaths right) {
        PythonBinaryOp op;

        do {
            op = randomOp(left, right);
        } while (op instanceof PythonSubtract);

        return op;
    }

    public static PythonAssignment randomIncOp(PythonMathsVariable x) {

        return new PythonAssignment(x, randomIncExpr(x));
    }

    public static PythonPrint randomMsg(PythonMaths n) {
        int i = RandomChoice.randInt(0, MESSAGES.length-1);

        PythonString there = new PythonString("There are");
        PythonString msg = new PythonString(MESSAGES[i]);

        return new PythonPrint(new PythonOp[]{there,n,msg});
    }


}
