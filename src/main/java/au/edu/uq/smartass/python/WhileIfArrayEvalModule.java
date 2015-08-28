package au.edu.uq.smartass.python;

import au.edu.uq.smartass.engine.Engine;
import au.edu.uq.smartass.auxiliary.RandomChoice;

/**
 * Describe class WhileIfArrayEvalModule here.
 *
 *
 * Created: Fri Dec 19 14:17:30 2008
 *
 * @author <a href="mailto:ilm@morot.maths.uq.edu.au">Ivan Miljenovic</a>
 * @version 1.0
 */
public class WhileIfArrayEvalModule extends PythonEvalModule {

    private static final int MIN_LEN = 7;
    private static final int MAX_LEN = 12;

    /**
     * Creates a new <code>WhileIfArrayEvalModule</code> instance.
     *
     */
    public WhileIfArrayEvalModule(Engine engine) {
        super(engine);

        generate();
    }

    private void generate() {

        PythonArrayVariable xs;
        PythonMathsVariable i;

        //Ensure the two variables don't have similar names
        do {
            xs = RandomPythonGen.randomAVar();
            i = RandomPythonGen.randomVar();
        } while (i.name().charAt(0) == xs.name().charAt(0));

        PythonNumber l;

        PythonString str = new PythonString(xs.name() + " =");

        PythonPrint pr = print(new PythonOp[] {str, xs});

        PythonWhileLoop loop;

        PythonIf ifs;

        do {
            clearScript();

            l = RandomPythonGen.randomNum(MIN_LEN, MAX_LEN);
            add(assign(xs, new PythonZeros(l, true)));
            addBlank();


            add(assign(i, PythonNumber.ZERO));
            addBlank();

            loop = new PythonWhileLoop(new PythonLT(i, new PythonArraySize(xs)));

            ifs = new PythonIf(randRange(i, 2, l, 4, 8));

            ifs.addIf(assign(xs.elem(i), RandomPythonGen.randomExpr(i, 2)));
            loop.add(ifs);
            loop.add(i.increment());

            add(loop);
            addBlank();

            add(pr);

        } while (!isCalculable());

    }

}
