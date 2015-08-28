package au.edu.uq.smartass.python;

import au.edu.uq.smartass.engine.Engine;
import au.edu.uq.smartass.auxiliary.RandomChoice;

/**
 * Describe class SimpleFuncEvalModule here.
 *
 *
 * Created: Tue Dec 16 13:50:42 2008
 *
 * @author <a href="mailto:ilm@morot.maths.uq.edu.au">Ivan Miljenovic</a>
 * @version 1.0
 */
public class SimpleFuncEvalModule extends PythonEvalModule {

    /**
     * Creates a new <code>SimpleFuncEvalModule</code> instance.
     *
     */
    public SimpleFuncEvalModule(Engine engine) {
        super(engine);

        generate();
    }

    private void generate() {

        PythonFunction f, g;
        PythonMaths fg, gf, fx, gx;

        PythonMathsVariable x = new PythonMathsVariable("x");

        do {
            clearScript();

            f = RandomPythonGen.randomIntFunc("f", "x", 1, false);
            g = RandomPythonGen.randomIntFunc("g", "x", 2, true);

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
