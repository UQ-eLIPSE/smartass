package au.edu.uq.smartass.python;

import au.edu.uq.smartass.maths.MathsOp;
import au.edu.uq.smartass.maths.Variable;
import au.edu.uq.smartass.engine.Engine;
import au.edu.uq.smartass.auxiliary.RandomChoice;

/**
 * Describe class ExponentialGrowthModule here.
 *
 *
 * Created: Thu Dec 11 12:51:59 2008
 *
 * @author <a href="mailto:ilm@morot.maths.uq.edu.au">Ivan Miljenovic</a>
 * @version 1.0
 */
public class ExponentialGrowthModule extends PythonModule {

    private final static double MIN_S = 1.3;
    private final static double MAX_S = 4.0;

    private final static int NUM_DECIMALS = 2;
    private final static double SCALER = Math.pow(10.0, 1.0*NUM_DECIMALS);

    private final static int MIN_G = 10;
    private final static int MAX_G = 20;

    private final static int MIN_B = 5;
    private final static int MAX_B = 42;

    private final static String VAR = "y";
    private final static String OTHER_VAR = "t";

    private final static String FUNC_NAME = VAR + "dash";
    private final static String FUNC_TEX = VAR + "'";

    private PythonNumber start;
    private PythonNumber growth;
    private PythonNumber gens;
    private PythonFunction eqn;
    private PythonMaths expr;

    private EulersMethod euler;

    /**
     * Creates a new <code>ExponentialGrowthModule</code> instance.
     *
     */
    public ExponentialGrowthModule(Engine engine) {
        super(engine);

        generate();
    }

    private static int d2I(double d) {
        return (int)Math.round(d*SCALER);
    }

    private static double i2D(int i) {
        return i/SCALER;
    }

    private void generate() {
        start = new PythonNumber(RandomChoice.randInt(MIN_B, MAX_B));

        int sc = RandomChoice.randInt(d2I(MIN_S), d2I(MAX_S));
        growth = new PythonNumber(i2D(sc));

        gens = new PythonNumber(RandomChoice.randInt(MIN_G, MAX_G));

        PythonMathsVariable t = new PythonMathsVariable(OTHER_VAR);
        PythonMathsVariable y = new PythonMathsVariable(VAR);

        eqn = new PythonFunction(FUNC_NAME, FUNC_TEX,
                                 new PythonMathsVariable[] {t, y},
                                 new boolean[] {false, true});
        expr = new PythonMult(growth, y);
        eqn.addFuncCommand(expr);

        euler = new EulersMethod(OTHER_VAR, VAR, eqn,
                                 PythonNumber.ZERO, PythonNumber.ONE, gens,
                                 start, true);
    }

    private String start() {
        return toTex(start.toMathsOp());
    }

    private String scale() {
        return toTex(growth.toMathsOp());
    }

    private String numGens() {
        return toTex(gens.toMathsOp());
    }

    private String func() {
        Variable yd = eqn.toMathsOp();

        yd.setValue(expr.toMathsOp());

        return toTex(yd.varEquality());
    }

    private String code() {
        PythonFile script = new PythonFile();
        script.add(euler.code());

        PythonArrayVariable ys = euler.ys();

        script.addBlank();
        script.add(comment("Print a blank line to separate the values"));
        script.add(print());

        PythonString str = new PythonString("The final population size is:");

        PythonMaths yLast = ys.elem(new PythonSubtract(euler.numSteps(),
                                                       PythonNumber.ONE));

        script.add(print(new PythonOp[] {str, yLast}));

        return toTex(script);
    }

    private String answer() {
        euler.code().calculate();

        PythonArrayVariable ys = euler.ys();
        PythonNumber lastY = ys.elem(new PythonSubtract(euler.numSteps(),
                                                        PythonNumber.ONE)).calculate();

        return toTex(lastY.toMathsOp());
    }

    public String getSection(String name) {
        if (name.equals("start")) {
            return start();
        }

        if (name.equals("scale")) {
            return scale();
        }

        if (name.equals("numgens")) {
            return numGens();
        }

        if (name.equals("func")) {
            return func();
        }

        if (name.equals("code")) {
            return code();
        }

        if (name.equals("answer")) {
            return answer();
        }

        return super.getSection(name);
    }

}
