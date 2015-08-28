package au.edu.uq.smartass.python;

import au.edu.uq.smartass.engine.Engine;

/**
 * Describe class GravityModule here.
 *
 *
 * Created: Fri Jan 30 10:46:37 2009
 *
 * @author <a href="mailto:ilm@bart.maths.uq.edu.au">Ivan Miljenovic</a>
 * @version 1.0
 */
public class GravityModule extends PythonModule {

    private PythonNumber m, hKM, rKM;

    private PythonMaths r, mP, h, newton;

    private static final PythonMaths G = multPow(new PythonNumber(6.67),
                                                 new PythonNumber(-11));

    private PythonFile script;
    private PythonResultBuffer pb;

    private static final String P_NAME = "X";

    private static final PythonNumber TEN = new PythonNumber(10);

    private static final PythonNumber THREE = new PythonNumber(3);

    /**
     * Creates a new <code>GravityModule</code> instance.
     *
     */
    public GravityModule(Engine engine) {
        super(engine);

        generate();
    }

    private void generate() {

        script = new PythonFile();

        rKM = RandomPythonGen.randomNum(5500, 8000);
        r = multPow(rKM, THREE);

        PythonNumber mpF = divTen(RandomPythonGen.randomNum(20, 99));
        PythonNumber mpP = RandomPythonGen.randomNum(22, 29);
        mP = multPow(mpF, mpP);

        hKM = RandomPythonGen.randomNum(30000, 80000);
        h = multPow(hKM, THREE);
        m = RandomPythonGen.randomNum(60, 100);

        PythonMathsVariable ht = new PythonMathsVariable("height");
        PythonMathsVariable d = new PythonMathsVariable("dist");
        PythonMathsVariable rP = new PythonMathsVariable("radius");
        PythonMathsVariable mO = new PythonMathsVariable("mass");
        PythonMathsVariable frs = new PythonMathsVariable("F");
        PythonMathsVariable gS = new PythonMathsVariable("g");

        PythonMathsVariable grav = new PythonMathsVariable("G");
        PythonMathsVariable massP = new PythonMathsVariable("mass" + P_NAME);
        PythonMathsVariable mass = new PythonMathsVariable("mass");
        PythonMathsVariable dist = new PythonMathsVariable("distance");

        PythonMathsVariable m1 = new PythonMathsVariable("m1", "m_1");
        PythonMathsVariable m2 = new PythonMathsVariable("m2", "m_2");
        PythonMathsVariable dF = new PythonMathsVariable("d");
        PythonMathsVariable force = new PythonMathsVariable("F");


        PythonMathsVariable[] fArgs = {m1, m2, dF};

        PythonFunction f = new PythonFunction("newtonGravity", fArgs);
        f.add(comment("Newton's Law of Universal Gravitation"));
        f.add(assign(grav, G));
        //Have to be careful here when multiplying these terms: if you
        //have grav*(m1*m2) you get rounding issues that really mess
        //up the result.
        newton = div(mult(mult(grav, m1), m2), PythonPower.square(dF));
        f.add(assign(force, newton));
        f.add(f.funcReturn(force));

        script.add(f);

        PythonMathsVariable[] xArgs = {mass, dist};

        PythonFunction fX = new PythonFunction("gravity" + P_NAME, xArgs);

        fX.add(comment("Gravitational force for planet " + P_NAME));
        fX.add(assign(massP, mP));
        fX.add(fX.funcReturn(f.callFunction(new PythonMaths[] {massP, mass, dist})));

        script.add(fX);

        script.add(comment("Information about planet " + P_NAME));
        script.add(assign(rP, r));
        script.addBlank();

        script.add(comment("Marcus' mass (in kg) and height above the planet (in m)"));
        script.add(assign(mO, m));
        script.add(assign(ht, h));
        script.addBlank();

        script.add(comment("Calculate the distance from Marcus to the centre of the planet"));
        script.add(assign(d, new PythonAdd(ht, rP)));
        script.addBlank();

        script.add(comment("Calculation the gravitational force on Marcus"));
        script.add(assign(frs, fX.callFunction(new PythonMaths[] {mO, d})));
        script.add(print(new PythonOp[] {new PythonString("Marcus is experiencing a gravitational force of"),
                                         frs,
                                         new PythonString("N.")}));
        script.addBlank();

        script.add(comment("Calculating the gravitational acceleration on the surface"));
        script.add(comment("We can use our function by using a \"mass\" of 1kg"));
        script.add(assign(gS, fX.callFunction(new PythonMaths[] {PythonNumber.ONE, rP})));
        script.add(print(new PythonOp[] {new PythonString("The gravitational acceleration on the surface is"),
                                         gS,
                                         new PythonString("m/s^2.")}));

        pb = script.printOutput();
    }

    public String getSection(String name) {
        if (name.equals("planet")) {
            return P_NAME;
        }

        if (name.equals("massp")) {
            return massP();
        }

        if (name.equals("radius")) {
            return radius();
        }

        if (name.equals("mass")) {
            return mass();
        }

        if (name.equals("height")) {
            return height();
        }

        if (name.equals("newton")) {
            return newton();
        }

        if (name.equals("code")) {
            return code();
        }

        if (name.equals("answers")) {
            return answers();
        }

        return super.getSection(name);
    }

    private String massP() {
        return toMaths(mP);
    }

    private String radius() {
        return toMaths(rKM);
    }

    private String mass() {
        return toMaths(m);
    }

    private String height() {
        return toMaths(hKM);
    }

    private String newton() {
        PythonMathsVariable force = new PythonMathsVariable("F");
        return toMaths(force, newton);
    }

    private String code() {
        return toTex(script);
    }

    private String answers() {
        return toTex(pb);
    }

    private static PythonDivision div(PythonMaths m1, PythonMaths m2) {
        return new PythonDivision(m1, m2);
    }

    private static PythonMult mult(PythonMaths m1, PythonMaths m2) {
        return new PythonMult(m1, m2);
    }

    private static PythonNumber divTen(PythonNumber n) {
        return div(n, TEN).calculate();
    }

    private static PythonMaths multPow(PythonNumber b, PythonNumber p) {
        return mult(b, PythonPower.pTen(p));
    }

}
