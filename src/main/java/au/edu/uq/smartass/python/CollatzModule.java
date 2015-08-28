package au.edu.uq.smartass.python;

import au.edu.uq.smartass.maths.MathsOp;
import au.edu.uq.smartass.engine.Engine;

/**
 * Describe class CollatzModule here.
 *
 *
 * Created: Fri Jan 23 10:57:56 2009
 *
 * @author <a href="mailto:ilm@gulrot.maths.uq.edu.au">Ivan Miljenovic</a>
 * @version 1.0
 */
public class CollatzModule extends PythonModule {

    private PythonNumber mod, start, maxCount;

    PythonMathsVariable x;

    private PythonMaths ifExpr, elseExpr;

    private PythonFile script;
    private PythonResultBuffer pb;

    private PythonFunction func;

    private static final PythonString succb
        = new PythonString("It took");
    private static final PythonString succa
        = new PythonString("iterations to reach 1 when starting with a value of");

    private static final PythonString failb
        = new PythonString("Could not reach 1 within");
    private static final PythonString faila
        = new PythonString("iterations when starting with a value of");

    /**
     * Creates a new <code>CollatzModule</code> instance.
     *
     */
    public CollatzModule(Engine engine) {
        super(engine);

        generate();
    }

    private void generate() {

        PythonMathsVariable n;

        do {
            n = RandomPythonGen.randomVar();
            x = RandomPythonGen.randomVar();
        } while (sameName(n,x));

        func = new PythonFunction("petersRule", x);

        PythonMathsVariable init = new PythonMathsVariable("init");
        PythonMathsVariable max = new PythonMathsVariable("maxCount");
        PythonMathsVariable count = new PythonMathsVariable("count");

        PythonIf ifF, ifA;
        PythonWhileLoop loop;

        do {
            script = new PythonFile();

            do {
                mod = RandomPythonGen.randomNum(2,4);
                start = RandomPythonGen.randomNum(5,10);
            } while (isPow());

            maxCount = RandomPythonGen.randomNum(20,30);

            ifF = new PythonIf(new PythonEq(new PythonMod(x, mod), PythonNumber.ZERO));

            ifExpr = new PythonDivision(x, mod);
            elseExpr = RandomPythonGen.randomIncExpr(x,2,2,4);

            ifF.addIf(func.funcReturn(ifExpr));
            ifF.addElse(func.funcReturn(elseExpr));

            func.add(comment("Implementing the rule for Peter's Conjecture"));
            func.add(ifF);

            script.add(func);

            script.add(comment("The parameters of our experiment"));
            script.add(assign(init, start));
            script.add(assign(max, maxCount));
            script.addBlank();

            script.add(comment("Initialising the parameters of our experiment"));
            script.add(assign(n, init));
            script.add(assign(count, PythonNumber.ZERO));
            script.addBlank();

            script.add(comment("Creating a table of values"));
            script.add(print("Sequence of values when evaluating Peter's Conjecture"));
            script.add(print("-----------------------------------------------------"));
            script.add(print(n));
            script.addBlank();

            loop = new PythonWhileLoop(new PythonAnd(new PythonNE(n, PythonNumber.ONE),
                                                     new PythonLT(count, max)));
            loop.add(assign(n, func.callFunction(n)));
            loop.add(print(n));
            loop.add(count.increment());

            script.add(loop);
            script.addBlank();

            script.add(comment("Print a blank line"));
            script.add(print());
            script.addBlank();

            script.add(comment("Determining whether we've reached the expected value of 1"));
            ifA = new PythonIf(new PythonEq(n, PythonNumber.ONE));
            ifA.addIf(print(new PythonOp[]{succb, count, succa, init}));
            ifA.addElse(print(new PythonOp[]{failb, max, faila, init}));

            script.add(ifA);

        } while (!script.isCalculable());

        pb = script.printOutput();
    }

    private boolean isPow() {
        double m = mod.value();
        double s = start.value();

        double p = Math.log(s)/Math.log(m);

        //Determining if the log base m is approximately an integer
        return Math.abs(Math.rint(p) - p) < 1e-5;
    }


    public String getSection(String name) {
        if (name.equals("var")) {
            return var();
        }

        if (name.equals("expr")) {
            return expr();
        }

        if (name.equals("mod")) {
            return mod();
        }

        if (name.equals("start")) {
            return start();
        }

        if (name.equals("max")) {
            return maxCount();
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

    private String var() {
        return toMaths(x);
    }

    private String expr() {
        return toMaths(elseExpr);
    }

    private String mod() {
        return toMaths(mod);
    }

    private String start() {
        return toMaths(start);
    }

    private String maxCount() {
        return toMaths(maxCount);
    }

    private String func() {
        return func.name();
    }

    private String code() {
        return toTex(script);
    }

    private String answer() {
        return toTex(pb.getLastPrint());
    }


}
