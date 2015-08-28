package au.edu.uq.smartass.python; 

import au.edu.uq.smartass.engine.Engine;
import au.edu.uq.smartass.auxiliary.RandomChoice;

/**
 * Describe class WhileFunctionEvalModule here.
 *
 *
 * Created: Fri Dec 19 14:17:30 2008
 *
 * @author <a href="mailto:ilm@morot.maths.uq.edu.au">Ivan Miljenovic</a>
 * @version 1.0
 */
public class WhileFunctionEvalModule extends PythonEvalModule {

    private static final int MIN_PRINT = 7;
    private static final int MAX_PRINT = 10;

    private static final int MIN_START = -5;
    private static final int MAX_START = 5;

    /**
     * Creates a new <code>WhileFunctionEvalModule</code> instance.
     *
     */
    public WhileFunctionEvalModule(Engine engine) {
        super(engine);

        generate();
    }

    private void generate() {
        PythonMathsVariable x;
        PythonFunction func;

        PythonIf ifs;

        PythonMathsVariable i;
        PythonNumber starter;
        int start;

        //Ensure the two variables don't have similar names
        do {
            x = RandomPythonGen.randomVar();
            i = RandomPythonGen.randomVar();
        } while (sameName(x,i));

        PythonWhileLoop loop;

        do {
            clearScript();

            func = new PythonFunction("func", x);

            ifs = RandomPythonGen.randModIf(x);
            ifs.addIf(RandomPythonGen.randReturn(func, x, 1));
            ifs.addElse(RandomPythonGen.randReturn(func, x, 2));
            func.add(ifs);
            add(func);

            starter = RandomPythonGen.randomNum(MIN_START, MAX_START);
            add(assign(i, starter));
            start = (int)starter.intValue();
            addBlank();

            loop = new PythonWhileLoop(new PythonLT(i,
                                                    RandomPythonGen.randomNum(start + MIN_PRINT,
                                                                              start + MAX_PRINT)));
            loop.add(print(func.callFunction(i)));
            loop.add(i.increment());

            add(loop);

        } while (!isCalculable());

    }

}
