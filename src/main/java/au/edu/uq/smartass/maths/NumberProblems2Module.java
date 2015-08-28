/** @(#)NumberProblems1Module.java
 * This file is part of SmartAss and describes class PowerFractionModule for
 * multiplication and division of powers of to different variables
 * Copyright (C) 2006 Department of Mathematics, The University of Queensland
 * SmartAss is free software; you can redistribute it and/or modify it under the terms of the
 * GNU General Public License as published by the Free Software Foundation; either version 2, or
 * (at your option) any later version.
 * GNU program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY;
 * without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.
 * See the GNU General Public License for more details.
 * You should have received a copy of the GNU General Public License along with program;
 * see the file COPYING. If not, write to the
 * Free Software Foundation, Inc., 51 Franklin St, Fifth Floor, Boston, MA 02110-1301 USA
 *
 */
package au.edu.uq.smartass.maths;

import au.edu.uq.smartass.auxiliary.RandomChoice;
import au.edu.uq.smartass.engine.Engine;
import java.util.Vector;
import java.util.Iterator;

public class NumberProblems2Module extends MathsModule {
    final static int MIN_N = 4;
    final static int MAX_N = 10;

    String default_var = "n";

    String working;

    Variable v1, v2;

    IntegerNumber diff;

    MathsOp ans;


    /**
     * @param engine    engine instance
     */
    public NumberProblems2Module(Engine engine) {
        super(engine);

	generate(RandomChoice.randInt(MIN_N,MAX_N), 
		 RandomChoice.randInt(MIN_N,MAX_N), 
		 default_var);
    }

    public NumberProblems2Module(Engine engine, int n1, int d) {
	super(engine);

	generate(n1, d, default_var);
    }

    public NumberProblems2Module(Engine engine, String var) {
	super(engine);

	generate(RandomChoice.randInt(MIN_N,MAX_N), 
		 RandomChoice.randInt(MIN_N,MAX_N), 
		 var);
    }

    public NumberProblems2Module(Engine engine, int n1, int d, String var) {
	super(engine);

	generate(n1,d,var);
    }

    private void generate(int num, int d, String var) {

	v1 = new Variable(var);
	v1.setValue(new IntegerNumber(num));

	v2 = new Variable("(" + var + " + " + d + ")");
	v2.setValue(new IntegerNumber(num + d));

	diff = new IntegerNumber(d);

	int m = num + (num + d);

	ans = new IntegerNumber(m);
	
	String begin = "\n \\begin{array}{lrcl}\n";
	String end = "\n \\end{array}";
	String[] oldEqTex, oldPreTex;

	MathsOp solution = new Equality(new Addition(v1, v2),
					ans);

	Vector<MathsOp> lines = new Vector<MathsOp>(3);

	lines.add(new Equality(new Addition(new Multiplication(new IntegerNumber(2), v1),
					    diff),
			       ans));

	lines.add(new Equality(new Multiplication(new IntegerNumber(2), v1),
			       new IntegerNumber(m - d)));

	lines.add(new Equality(v1, v1.getValue()));

	Iterator<MathsOp> it = lines.iterator();

	while (it.hasNext()) {
	    solution = new Precedence(solution,
				      it.next());
	}

	oldEqTex = MathsOp.getTex(Equality.class);
	MathsOp.setTex(Equality.class, new String[]{"& = &"});

	oldPreTex = MathsOp.getTex(Equality.class);
	MathsOp.setTex(Precedence.class, new String[]{"\\\\\n  \\Longrightarrow &"});

	//Need an extra "&" here, as it's blank for the first column
	working = begin + "    & " + solution + end;

	MathsOp.setTex(Equality.class, oldEqTex);
	MathsOp.setTex(Precedence.class, oldPreTex);
    }
	


    /**
     * Composes section with given name
     * "question", "shortanswer" and "solution" sections are recognised
     *
     * @param name  section name
     * @return the LaTeX representation of the required section
     **/
    public String getSection(String name) {

        if(name.equals("num1")) {
            return toTex(v1.getValue());
	}
	
	if (name.equals("num2")) {
	    return toTex(v2.getValue());
	}

        if (name.equals("solution")) {
	    return toTex(ans);
	}

	if (name.equals("var1")) {
            return toTex(v1);
	}

	if (name.equals("var2")) {
	    return toTex(v2);
	}

	if (name.equals("working")) {
	    return toTex(working);
	}

	if (name.equals("diff")) {
	    return toTex(diff);
	}

        return super.getSection(name);
    }

    private String toTex(Object m) {
	return "\\ensuremath{" + m + "}";
    }

    private String toTex(int m) {
	return "\\ensuremath{" + m + "}";
    }

}
