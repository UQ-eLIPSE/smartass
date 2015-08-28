package au.edu.uq.smartass.python;

/**
 * Describe class EulersMethod here.
 *
 *
 * Created: Fri Dec  5 11:32:48 2008
 *
 * @author <a href="mailto:ilm@morot.maths.uq.edu.au">Ivan Miljenovic</a>
 * @version 1.0
 */
public class EulersMethod {

    private PythonMaths x, y;
    private PythonMathsVariable minX, maxX, dx;
    private PythonArrayVariable xs, ys;
    private PythonWhileLoop loop;
    private PythonMathsVariable n = new PythonMathsVariable("numSteps");
    private PythonMathsVariable i = new PythonMathsVariable("i");

    private PythonBlock code;

    /**
     * Creates a new <code>EulersMethod</code> instance.
     *
     */
    public EulersMethod(String xVar, String yVar, PythonFunction func,
                        PythonNumber xMin, PythonNumber xStep, PythonNumber xMax,
                        PythonMaths y0, boolean table) {
        super();

        generate(xVar, yVar, func, xMin, xStep, xMax, y0, table);
    }

    private void generate(String xVar, String yVar, PythonFunction func,
                          PythonNumber xMin, PythonNumber xStep, PythonNumber xMax,
                          PythonMaths y0, boolean table) {

        if (func.numVars() != 2) {
            throw new PythonException("Require exactly two parameters to function");
        }

        minX = new PythonMathsVariable(xVar + "Min");
        maxX = new PythonMathsVariable(xVar + "Max");
        dx = new PythonMathsVariable(xVar + "Step");

        xs = new PythonArrayVariable(xVar + "s");
        ys = new PythonArrayVariable(yVar + "s");

        code = new PythonBlock();

        code.add(new PythonComment("Defining the " + func.name() + " function"));
        code.add(func);

        code.add(new PythonComment("The minimum and maximum " + xVar + " values"));
        code.add(new PythonAssignment(minX, xMin));
        code.add(new PythonAssignment(dx, xStep));
        code.add(new PythonAssignment(maxX, xMax));
        code.addBlank();

        if (needsExtra(xMin, xStep, xMax)) {
            code.add(new PythonComment("Increasing the value of " + maxX.name() + " so that the array size is correct"));
            code.add(new PythonAssignment(maxX, new PythonAdd(maxX, extraInc(xMin, xStep, xMax))));
            code.addBlank();
        }

        code.add(new PythonComment("The " + xVar + " values"));
        code.add(new PythonAssignment(xs, new PythonARange(minX,maxX,dx)));

        code.add(new PythonComment("The number of " + xVar + " points"));
        code.add(new PythonAssignment(n, new PythonArraySize(xs)));
        code.addBlank();

        code.add(new PythonComment("Creating the array to store the " + yVar + " values"));
        code.add(new PythonAssignment(ys,new PythonZeros(n)));
        code.add(new PythonAssignment(ys.elem(PythonNumber.ZERO), y0));
        code.addBlank();

        code.add(new PythonComment("Indexing variable to traverse the arrays"));
        code.add(new PythonAssignment(i, PythonNumber.ONE));
        code.addBlank();

        if (table) {
            PythonString[] hdrs = {new PythonString(xVar), new PythonString(yVar)};

            code.add(new PythonComment("Adding headers for the table of results"));
            code.add(new PythonPrint(hdrs));
            code.addBlank();
        }

        code.add(new PythonComment("Looping through all the " + xVar + " values"));
        loop = new PythonWhileLoop(new PythonLT(i, n));

        x = xs.elem(new PythonSubtract(i, PythonNumber.ONE));
        y = ys.elem(new PythonSubtract(i, PythonNumber.ONE));

        PythonMaths[] args = {x,y};

        loop.add(new PythonComment("Applying Euler's method"));
        loop.add(new PythonAssignment(ys.elem(i),
                                             new PythonAdd(y,
                                                           new PythonMult(dx,
                                                                          func.callFunction(args)))));
        loop.addBlank();

        if (table) {
            loop.add(new PythonComment("Creating a table of values"));
            loop.add(new PythonPrint(new PythonOp[] {xs.elem(i), ys.elem(i)}));
            loop.addBlank();
        }

        loop.add(new PythonComment("Incrementing the indexing variable"));
        loop.add(new PythonAssignment(i, new PythonAdd(i, PythonNumber.ONE)));

        code.add(loop);

    }

    private static boolean needsExtra(PythonNumber xMin, PythonNumber xStep,
                                      PythonNumber xMax) {
        double min = xMin.value();
        double step = xStep.value();
        double max = xMax.value();

        return (max-min) % step == 0;
    }

    private static PythonMaths extraInc(PythonNumber xMin, PythonNumber xStep,
                                         PythonNumber xMax) {
        if (xMin.isInt() && xStep.isInt() && xMax.isInt()) {
            return PythonNumber.ONE;
        }

        return new PythonDivision(xStep, new PythonNumber(2));
    }

    public PythonConstruct code() {
        return code;
    }

    public PythonArrayVariable xs() {
        return xs;
    }

    public PythonArrayVariable ys() {
        return ys;
    }

    public PythonMathsVariable numSteps() {
        return n;
    }

}