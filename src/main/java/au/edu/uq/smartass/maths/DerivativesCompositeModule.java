/**
 * 
 * This file is part of SmartAss and describes class DerivativesCompositeModule:  
 * with given f(x) = ax^3+bx^2+cx+d (some parts may be absent) answer a number of questions
 * 1) find f'(x)
 * 2) find f''(x)
 * 3) find x if f'(x)=0
 * 4) find f'(x) if x=0
 *   
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
*/
package au.edu.uq.smartass.maths;

import java.util.Vector;

import au.edu.uq.smartass.auxiliary.RandomChoice;
import au.edu.uq.smartass.engine.Engine;

/**
 * Class DerivativesCompositeModule -   
 * with given f(x) = ax^3+bx^2+cx+d (some parts may be absent) answer a number of questions
 * 1) find f'(x)
 * 2) find f''(x)
 * 3) find x if f'(x)=0
 * 4) find f'(x) if x=0
 */
public class DerivativesCompositeModule extends MathsModule {
	final int NUM_PARTS = 4;
	final int MAX_NUM = 6;
	int[] coef = {0,0,0};
	int[] answ = {0,0};
	DerivateFunctions f[] = new DerivateFunctions[3];
	DerivateFunctions df[] = new DerivateFunctions[3];
	DerivateFunctions ddf[] = new DerivateFunctions[2];
	int f_coef = RandomChoice.randInt(-MAX_NUM, +MAX_NUM);
	int random_x = RandomChoice.randInt(-MAX_NUM, +MAX_NUM);
	
	Variable vx = new Variable("x");
	MathsOp fx, dfx, ddfx, df0;
	Vector<MathsOp> solution = new Vector<MathsOp>();
	QuadraticModule qm;

	/**
	 * @param engine
	 */
	public DerivativesCompositeModule(Engine engine) {
		super(engine);

		coef[0] = 3 * RandomChoice.randInt(1, MAX_NUM % 3 +1) * RandomChoice.randSign();

		answ[0] = RandomChoice.randInt(-MAX_NUM, MAX_NUM)*RandomChoice.randSign();
		answ[1] = RandomChoice.randInt(1, MAX_NUM)*RandomChoice.randSign();
		if((coef[0]*(-answ[0] - answ[1])) % 2 != 0)
			answ[0]++;
		
		coef[1] = coef[0]*(-answ[0] - answ[1]);
		coef[2] = coef[0]*answ[0]*answ[1];

		for(int i=0;i<3;i++)
			if(coef[i]!=0) {
				//first derivative - quadratic equation
				df[i] = new DerivateFunctions(0, coef[i], 2-i, vx);
				//function
				f[i] = new DerivateFunctions(0, coef[i]/(3-i), 3-i, vx);
			}

		//second derivative
		ddf[0] = df[0].derivate();
		if(df[1]!=null)
			ddf[1] = df[1].derivate();
		
		//MathsOp representations
		//function
		fx = f[0].toMathsOp();
		for(int i=1;i<3;i++)
			if(f[i]!=null)
				fx = new Addition(fx, f[i].toMathsOp());
		
		//first derivative
		dfx = df[0].toMathsOp();
		if(df[1]!=null)
			dfx = new Addition(dfx, df[1].toMathsOp());
		if(coef[2]!=0)
			dfx = new Addition(dfx, new IntegerNumber(coef[2]));
			
		//second derivative
		ddfx = ddf[0].toMathsOp();
		if(ddf[1]!=null)
			ddfx = new Addition(ddfx, ddf[1].toMathsOp());
		
		//solution of f'(x)=0
		qm = new QuadraticModule(engine, 
				new String[]{Integer.toString(coef[0]), Integer.toString(coef[1]), Integer.toString(coef[2]), "x"});
		
		//solution of f'(random_x)
		df0 = new Multiplication(new IntegerNumber(coef[0]), 
				new Power(new IntegerNumber(random_x), new IntegerNumber(2)));
		if(coef[1]!=0)
			df0 = new Addition(df0, new Multiplication(new IntegerNumber(coef[1]),new IntegerNumber(random_x)));
		if(coef[2]!=0)
			df0 = new Addition(df0, new IntegerNumber(coef[2]));
		df0 = new Equality(df0, new IntegerNumber(coef[0]*random_x*random_x+coef[1]*random_x+coef[2]));
		
	}

	/**
     * Composes section with given name
     * "question", "f", "df", ddf", "quadratic", "df_x", "df_x_shortanswer", "quadratic_shortanswer" 
     * sections is recognized
     * 
     * @param name	section name
     **/
	public String getSection(String name) {
		if(name.equals("question") || name.equals("f"))
			return "\\ensuremath{"+fx.toString()+"}";
		else if (name.equals("df"))
			return "\\ensuremath{"+dfx.toString()+"}";
		else if (name.equals("ddf"))
			return "\\ensuremath{"+ddfx.toString()+"}";
		else if (name.equals("quadratic"))
		{   String quadraticSol;
			if (qm.getSection("c").equals("0") && qm.getSection("b").equals("0"))
			 quadraticSol="\\ensuremath{"+(new Equality(vx, new IntegerNumber(0))).toString()+"}";
			else 
			 if (qm.getSection("c").equals("0"))
			 	quadraticSol="\\ensuremath{"+(new Equality(new UnprintableMultiplication(MathsUtils.multiplyVarToConst(coef[0],vx),
			 				new Addition(vx, new IntegerNumber(coef[1]/coef[0]))),new IntegerNumber(0))).toString()+"}, so "+
			 					"\\ensuremath{"+(new Equality(vx, new IntegerNumber(0))).toString()+"} or \\ensuremath{"+
			 					(new Equality(vx, new IntegerNumber(-coef[1]/coef[0]))).toString()+"}";		  
			  else {
				if (coef[1]==0) //b==0, c!=0
					quadraticSol =" rearranging this equation gives \n";
				else	
					quadraticSol = " we use \\ensuremath{a="+coef[0]+", b="+coef[1]+", c="+coef[2]+
				"} in the quadratic formula. Hence \n";	
				quadraticSol+=qm.getSection("solution");
			  }	
		 return	quadraticSol;	
		}
			
		else if (name.equals("quadratic_shortanswer"))
			return qm.getSection("shortanswer");
		else if (name.equals("df_x"))
			 return "\\ensuremath{" + 
			 		df0.toString() + "}";
		else if (name.equals("df_x_shortanswer"))
			 return "\\ensuremath{" + 
			 new IntegerNumber(coef[0]*random_x*random_x+coef[1]*random_x+coef[2]).toString() + "}";
		else if (name.equals("xvalue"))
			return Integer.toString(random_x);
		else	 
			return super.getSection(name);
	}
}

