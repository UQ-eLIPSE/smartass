package au.edu.uq.smartass.python;

import au.edu.uq.smartass.engine.Engine;
import au.edu.uq.smartass.auxiliary.RandomChoice;

/**
 * Describe class CallFuncEvalModule here.
 *
 *
 * Created: Tue Dec 16 13:50:42 2008
 *
 * @author <a href="mailto:ilm@morot.maths.uq.edu.au">Ivan Miljenovic</a>
 * @version 1.0
 */
public class CallFuncEvalModule extends PythonEvalModule {

    /**
     * Creates a new <code>CallFuncEvalModule</code> instance.
     *
     */
    public CallFuncEvalModule(Engine engine) {
        super(engine);

        generate();
    }

    private void generate() {

        PythonFunction f, g;
        PythonMaths fg, gf, fx, gx;

        PythonMathsVariable x = new PythonMathsVariable("x");
        PythonMathsVariable xg = new PythonMathsVariable("x");
        PythonMathsVariable ans = new PythonMathsVariable("ans");

        do {
            clearScript();

            f = RandomPythonGen.randomIntFunc("f", "x", 1, true);

            g = new PythonFunction("g", xg);
            g.add(assign(ans, f.callFunction(xg)));
            g.setReturn(RandomPythonGen.randomExpr(ans, 1));

            add(f);
            add(g);

            fx = f.callFunction(x);
            gx = g.callFunction(x);

            fg = f.callFunction(gx);
            gf = g.callFunction(fx);

            add(RandomPythonGen.assignRandNum(x));
            add(print(fx));
            add(fg);
            add(print(gx));

        } while (!isCalculable());
    }

}
