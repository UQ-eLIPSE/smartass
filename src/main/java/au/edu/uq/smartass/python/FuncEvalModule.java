package au.edu.uq.smartass.python;

import au.edu.uq.smartass.engine.Engine;
import au.edu.uq.smartass.auxiliary.RandomChoice;

/**
 * Describe class FuncEvalModule here.
 *
 *
 * Created: Tue Dec 16 13:50:42 2008
 *
 * @author <a href="mailto:ilm@morot.maths.uq.edu.au">Ivan Miljenovic</a>
 * @version 1.0
 */
public class FuncEvalModule extends PythonEvalModule {

    private static final int MIN_PRINT = 2;

    //Ensure that x is re-assigned at most once
    private boolean canAssign;

    /**
     * Creates a new <code>FuncEvalModule</code> instance.
     *
     */
    public FuncEvalModule(Engine engine) {
        super(engine);

        generate();
    }

    private void generate() {

        PythonFunction f, g;
        PythonMaths fg, gf, fx, gx;

        PythonMathsVariable x = new PythonMathsVariable("x");

        do {
            clearScript();
            canAssign = true;

            f = RandomPythonGen.randomIntFunc("f", "x", 1);
            g = RandomPythonGen.randomIntFunc("g", "x", 2);

            add(f);
            add(g);

            fx = f.callFunction(x);
            gx = g.callFunction(x);

            fg = f.callFunction(gx);
            gf = g.callFunction(fx);

            add(RandomPythonGen.assignRandNum(x));

            add(maybePrint(fg));
            maybeAssign(x, fx, gx);
            add(maybePrint(gf));
            maybeAssign(x, fx, gx);
            add(print(x));

        } while (!isCalculable() || numPrinting() < MIN_PRINT);
    }

    protected static PythonConstruct maybePrint(PythonOp op) {
        if (RandomChoice.randBool()) {
            return print(op);
        } else {
            return op;
        }
    }

    private void maybeAssign(PythonMathsVariable x, PythonMaths fg, PythonMaths gf) {

        if (!canAssign) {
            return;
        }

        canAssign = false;

        switch (RandomChoice.randInt(0,3)) {
        case 0:
            add(assign(x, fg));
            break;
        case 1:
            add(assign(x, gf));
            break;
        default:
            //We didn't re-assign x, so let it be assigned after all.
            canAssign = true;
            break;
        }
    }

}
