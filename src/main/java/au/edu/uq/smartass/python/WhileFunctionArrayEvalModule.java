package au.edu.uq.smartass.python;

import au.edu.uq.smartass.engine.Engine;
import au.edu.uq.smartass.auxiliary.RandomChoice;

/**
 * Describe class WhileFunctionArrayEvalModule here.
 *
 *
 * Created: Fri Dec 19 14:17:30 2008
 *
 * @author <a href="mailto:ilm@morot.maths.uq.edu.au">Ivan Miljenovic</a>
 * @version 1.0
 */
public class WhileFunctionArrayEvalModule extends PythonEvalModule {

    private static final int MIN_LEN = 7;
    private static final int MAX_LEN = 12;

    /**
     * Creates a new <code>WhileFunctionArrayEvalModule</code> instance.
     *
     */
    public WhileFunctionArrayEvalModule(Engine engine) {
        super(engine);

        generate();
    }

    private void generate() {
        PythonMathsVariable xFunc = RandomPythonGen.randomVar();
        PythonFunction func;

        PythonIf ifs;

        PythonArrayVariable xs;
        PythonMathsVariable i;

        //Ensure the two variables don't have similar names
        do {
            xs = RandomPythonGen.randomAVar();
            i = RandomPythonGen.randomVar();
        } while (i.name().charAt(0) == xs.name().charAt(0));
        //Can't use sameName() above, as xs has an 's' on the end of its name.

        PythonString str = new PythonString(xs.name() + " =");

        PythonPrint pr = print(new PythonOp[] {str, xs});

        PythonWhileLoop loop;

        do {
            clearScript();

            func = new PythonFunction("func", xFunc);

            ifs = RandomPythonGen.randModIf(xFunc);
            ifs.addIf(RandomPythonGen.randReturn(func, xFunc, 1));
            ifs.addElse(RandomPythonGen.randReturn(func, xFunc, 2));
            func.add(ifs);
            add(func);

            add(RandomPythonGen.assignRandZeros(xs, MIN_LEN, MAX_LEN, true));
            addBlank();


            add(assign(i, PythonNumber.ZERO));
            addBlank();

            loop = new PythonWhileLoop(new PythonLT(i, new PythonArraySize(xs)));
            loop.add(assign(xs.elem(i), func.callFunction(i)));
            loop.add(i.increment());

            add(loop);
            addBlank();

            add(pr);

        } while (!isCalculable());

    }

}
