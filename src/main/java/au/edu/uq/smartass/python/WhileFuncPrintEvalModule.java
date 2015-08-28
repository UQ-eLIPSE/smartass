package au.edu.uq.smartass.python;

import au.edu.uq.smartass.engine.Engine;
import au.edu.uq.smartass.auxiliary.RandomChoice;

/**
 * Describe class WhileFuncPrintEvalModule here.
 *
 *
 * Created: Fri Dec 19 14:17:30 2008
 *
 * @author <a href="mailto:ilm@morot.maths.uq.edu.au">Ivan Miljenovic</a>
 * @version 1.0
 */
public class WhileFuncPrintEvalModule extends PythonEvalModule {

    private static final int MIN_PRINT = 7;
    private static final int MAX_PRINT = 12;

    private static final int MIN_START = 1;
    private static final int MAX_START = 5;

    /**
     * Creates a new <code>WhileFuncPrintEvalModule</code> instance.
     *
     */
    public WhileFuncPrintEvalModule(Engine engine) {
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

        PythonMathsVariable iFunc = new PythonMathsVariable(i.name());

        PythonWhileLoop loop;

        do {
            clearScript();

            func = new PythonFunction("func", x);
            func.add(assign(iFunc, x.succ()));

            ifs = RandomPythonGen.randModIf(x);
            if (RandomChoice.randBool()) {
                ifs.addIf(RandomPythonGen.randomMsg(x));
            } else {
                ifs.addElse(RandomPythonGen.randomMsg(x));
            }

            ifs.addIf(RandomPythonGen.randomIncOp(iFunc));
            func.add(ifs);
            func.setReturn(iFunc);

            add(func);

            starter = RandomPythonGen.randomNum(MIN_START, MAX_START);
            add(assign(i, starter));
            start = (int)starter.intValue();
            addBlank();

            loop = new PythonWhileLoop(new PythonLT(i,
                                                    RandomPythonGen.randomNum(start + MIN_PRINT,
                                                                              start + MAX_PRINT)));
            loop.add(assign(i, func.callFunction(i)));
            loop.add(print(i));

            add(loop);

        } while (!isCalculable() || numPrinting() < MIN_PRINT || numPrinting() > MAX_PRINT);

    }

}
