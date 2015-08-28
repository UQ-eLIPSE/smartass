package au.edu.uq.smartass.python;

import au.edu.uq.smartass.engine.Engine;
import au.edu.uq.smartass.auxiliary.RandomChoice;

/**
 * Describe class WhileModEvalModule here.
 *
 *
 * Created: Fri Dec 19 14:17:30 2008
 *
 * @author <a href="mailto:ilm@morot.maths.uq.edu.au">Ivan Miljenovic</a>
 * @version 1.0
 */
public class WhileModEvalModule extends PythonEvalModule {

    private static final int MAX = 50;
    private static final int MIN_PRINT = 4;
    private static final int MAX_PRINT = 8;

    /**
     * Creates a new <code>WhileModEvalModule</code> instance.
     *
     */
    public WhileModEvalModule(Engine engine) {
        super(engine);

        generate();
    }

    private void generate() {
        PythonMathsVariable x = RandomPythonGen.randomVar();
        PythonString str = new PythonString(x.name() + " =");

        PythonPrint pr = print(new PythonOp[] {str, x});

        PythonNumber maxNum;

        PythonIf ifs;

        do {
            clearScript();

            add(RandomPythonGen.assignRandNum(x));

            maxNum = RandomPythonGen.randomNum(20,MAX);

            PythonWhileLoop loop = new PythonWhileLoop(new PythonLT(x, maxNum));

            ifs = RandomPythonGen.randModIf(x);
            ifs.addIf(RandomPythonGen.randomMsg(x));

            loop.add(ifs);
            loop.addBlank();
            loop.add(RandomPythonGen.randomIncOp(x));

            add(loop);

        } while (!isCalculable() || numPrinting() < MIN_PRINT || numPrinting() > MAX_PRINT);
    }

}
