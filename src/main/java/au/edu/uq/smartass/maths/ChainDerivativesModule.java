/** @(#)ChainDerivativesModule.java
 *
 * This file is part of SmartAss and describes class ChainDerivativesModule for 
 * question "Find the derivative using chaine rule" 
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
 * 
 */
package au.edu.uq.smartass.maths;

import java.util.Vector;

import au.edu.uq.smartass.auxiliary.RandomChoice;
import au.edu.uq.smartass.engine.Engine;

/**
 * Class ChainDerivativesModule for 
 * question "Find the derivative using chaine rule"
 * 
 */
public class ChainDerivativesModule extends MathsModule {
	final int MAX_NUM = 8;
	DerivateFunctions yx, u, yu, ys, us;
	int cu = RandomChoice.randInt(-MAX_NUM, MAX_NUM);
	Variable vx = new Variable("x");
	Variable vu = new Variable("u");
	MathsOp question, qu, qyu;
	MathsOp usol[], ysol[];
	Vector<MathsOp> solution = new Vector<MathsOp>();

	/**
	 * constructor
	 * 
	 * @param en	an instance of smartass Engine
	 * 
	 */
	public ChainDerivativesModule(Engine en) {
		super(en);
		
		//generate y(u)
		yu = new DerivateFunctionsEx(
				1, //RandomChoice.randInt(0, 5),
				RandomChoice.randInt(1, MAX_NUM)*RandomChoice.randSign(),
				RandomChoice.randInt(2, MAX_NUM)*RandomChoice.randSign(),
				vu);
		 
		//generate u(x) 
		if(yu.func==1 || yu.func==2 || yu.func==4)
			//if y is sin, cos or ln, u is a power 
			u = new DerivateFunctionsEx(
					0, //0 - x power n, 1 - sin, 2 - cos, 3 - e power x, 4 - ln x
					RandomChoice.randInt(1, MAX_NUM)*RandomChoice.randSign(),
					RandomChoice.randInt(1, MAX_NUM)*RandomChoice.randSign(),
					vx);
		else //u is any possible function 
			u = new DerivateFunctionsEx(
					RandomChoice.randInt(0, 5), //0 - x power n, 1 - sin, 2 - cos, 3 - e power x, 4 - ln x
					RandomChoice.randInt(1, MAX_NUM)*RandomChoice.randSign(),
					RandomChoice.randInt(1, MAX_NUM)*RandomChoice.randSign(),
					vx);

		if(cu!=0)
			qu = new Addition(
					u.toMathsOp(),
					new IntegerNumber(cu));
		else
			qu = u.toMathsOp();
		qyu = yu.toMathsOp();
		question = (yx = new DerivateFunctionsEx(yu.func, yu.cons, yu.param, qu)).toMathsOp();
		
	//	System.out.println(question.toString());
		
		//get solutions for y(u) and u(x)
		ysol = yu.solve();
		usol = u.solve();
		
		//first step of dy/du * du/dx
		solution.add(new Multiplication(ysol[ysol.length-1], usol[usol.length-1]));
		
		//second step of dy/du * du/dx
		us = u.derivate();
		ys = yu.derivate();

		if(ys.func==0 && ys.param==0 && us.func==0 && us.param==0)
			//y'=u^0  and u=x^0 
			solution.add(new IntegerNumber(ys.cons*us.cons)	);
		else { 
			boolean minus = false;
			//it is possible that result is a fraction, so we prepare numerator and denominator
			int nom_c = 1, denom_c = 1;
			MathsOp nom_op[] = {null, null, null}, denom_op[] = {null, null, null},
				// dy/du
				y_op[] = new MathsOp[]{
					// dy/du, u is represented as variable "u"
					(new DerivateFunctionsEx(ys.func, 1, ys.param, vu)).toMathsOp(),
					// dy/du, u is represented as function u(x)
					(new DerivateFunctionsEx(ys.func, 1, ys.param, qu)).toMathsOp()},
				// du/dx
				u_op = (new DerivateFunctionsEx(us.func, 1, us.param, vx)).toMathsOp();
			nom_c *= ys.cons;
			if(ys.func==6) 
				denom_c = ((DerivateFunctionsEx) ys).denom;
			if(ys.func==0 && ys.param<0 || ys.func==6) {
				denom_op[0] = ((BinaryOp)y_op[0]).getOp(1);
				denom_op[1] = ((BinaryOp)y_op[1]).getOp(1);
				if(ys.func==6 && (denom_op[1] instanceof BinaryOp))
					denom_op[1] = ((BinaryOp)denom_op[1]).getOp(1);
			} else if(ys.func!=0 || ys.param>0) {
				nom_op[0] = y_op[0];
				nom_op[1] = y_op[1];
			}
			
			nom_c *= us.cons;
			if(nom_c<0) {
				nom_c = - nom_c;
				minus = true;
			}
			if(us.func==6) 
				denom_c = denom_c * ((DerivateFunctionsEx) us).denom;
			if(us.func==0 && us.param<0 || us.func==6) {
				MathsOp t = ((BinaryOp)u_op).getOp(1);
				if(us.func==6 && (t instanceof BinaryOp))
					t = ((BinaryOp)t).getOp(1);
				if(denom_op[1]==null) {
					denom_op[0] = t;
					denom_op[1] = t;
				} else {
					if(ys.func==6) {
						denom_op[0] = new UnprintableMultiplication(t, denom_op[0]);
						denom_op[1] = new UnprintableMultiplication(t, denom_op[1]);
					} else {
						denom_op[0] = new UnprintableMultiplication(denom_op[0], t);
						denom_op[1] = new UnprintableMultiplication(denom_op[1], t);
					}
				}
			} else if(us.func!=0 || us.param>0) {
				if(nom_op[1]==null) {
					nom_op[0] = u_op;
					nom_op[1] =  u_op;
				} else {
					if(ys.func==1 || ys.func==2) {
						nom_op[0] =  new UnprintableMultiplication(u_op, nom_op[0]);
						nom_op[1] =  new UnprintableMultiplication(u_op, nom_op[1]);
					} else {
						nom_op[0] =  new UnprintableMultiplication(nom_op[0], u_op);
						nom_op[1] =  new UnprintableMultiplication(nom_op[1], u_op);
					}
				}
			}

			if(ys.func==0 && ys.param==-1 && us.func==0 && (cu==0 || us.param<0)) {
				denom_op[2] = vx;
			}
			
			//if nom_c and denom_c has common denominator?
			int hcf = HCFModule.hcf(nom_c, denom_c);
			nom_c = nom_c / hcf;
			denom_c = denom_c / hcf;
			
			if(nom_op[1]==null)
				nom_op = new MathsOp[]{new IntegerNumber(nom_c),new IntegerNumber(nom_c), nom_op[2]};
			else
				nom_op = new MathsOp[]{
					MathsUtils.multiplyVarToConst(nom_c, nom_op[0]),
					MathsUtils.multiplyVarToConst(nom_c, nom_op[1]),
					nom_op[2]};
			if(denom_op[1]!=null) {
				nom_op[0] = new FractionOp(
						nom_op[0],
						MathsUtils.multiplyVarToConst(denom_c, denom_op[0]));
				nom_op[1] = new FractionOp(
						nom_op[1],
						MathsUtils.multiplyVarToConst(denom_c, denom_op[1]));
			}
			
			if(minus) {
				nom_op[0] = new UnaryMinus(nom_op[0]);
				nom_op[1] = new UnaryMinus(nom_op[1]);
			}
			
			solution.add(nom_op[0]);
			
			if(!(ys.func==0 && ys.param==0))
				solution.add(nom_op[1]);
			
			if(denom_op[2]!=null) {
				if(cu==0) {
					denom_c = denom_c*u.cons;
					if(denom_c<0) {
						minus = !minus;
						denom_c = - denom_c;
					}
					hcf = HCFModule.hcf(nom_c, denom_c);
					nom_c = nom_c / hcf;
					denom_c = denom_c / hcf;
					nom_op[2] = new FractionOp(
							new IntegerNumber(nom_c),
							MathsUtils.multiplyVarToConst(denom_c, denom_op[2])); 
					if(minus) 
						nom_op[2] = new UnaryMinus(nom_op[2]);
				} else {
					int a = denom_c * u.cons;
					int b = denom_c * cu;
					hcf = HCFModule.hcf(a, b);
					if(hcf>1) {
						int hcf1 = HCFModule.hcf(nom_c, hcf);
						if(hcf1>1) {
							a = a / hcf1;
							b = b / hcf1;
							nom_c = nom_c / hcf1;
						}
					}
					nom_op[2] = new FractionOp(
							new IntegerNumber(nom_c),
							new Addition(
									MathsUtils.multiplyVarToConst(a, denom_op[2]),
									MathsUtils.multiplyConstToPower(b, vx, -u.param + 1))); 
					if(minus) 
						nom_op[2] = new UnaryMinus(nom_op[2]);
				}
				solution.add(nom_op[2]);
			}
			
/*			if(ys.func==0 && (ys.param==1 || ys.param==-1 && us.func==0 && us.param==-1)) {
				if(ys.param==1) {
					nom_op[3] = 
					nom_op[3] = new Addition(
							new Multiplication());
				}
					
				
			} else if(ys.func==0 && u.func==0 && cu==0) {
				
			}*/
			
		}
	}

	
	MathsOp composeSolutionStep(MathsOp vu) {
		if(ys.func==0 && ys.param==0 && us.func==0 && us.param==0)
			return new IntegerNumber(ys.cons*us.cons);
		else if(ys.func==0 && ys.param==0)
			return MathsUtils.multiplyVarToConst(ys.cons*us.cons, 
							(new DerivateFunctionsEx(us.func, 1, us.param, vx)).toMathsOp());
		else if(us.func==0 && us.param==0)
			return MathsUtils.multiplyVarToConst(ys.cons*us.cons, 
					(new DerivateFunctionsEx(ys.func, 1, ys.param, qu)).toMathsOp());
		else
			return MathsUtils.multiplyVarToConst(ys.cons*us.cons, 
					new UnprintableMultiplication(
							(new DerivateFunctionsEx(ys.func, 1, ys.param, vu)).toMathsOp(),
							(new DerivateFunctionsEx(us.func, 1, us.param, vx)).toMathsOp()));
	}
	
	/**
     * Composes section with given name
     * "question", "shortanswer", "u", "y", "du", "dy", "solution" sections is recognized
     * du and dy sections is not derivatives itself but an expression representing its calculation 
     * 
     * @param name	section name
     **/
	public String getSection(String name) {
		String s;
		if(name.equals("question"))
			return "\\ensuremath{"+question.toString() + "}";
		else if (name.equals("u"))
			return "\\ensuremath{" + qu.toString() + "}";
		else if (name.equals("y")) 
			return "\\ensuremath{" + qyu.toString() + "}";
		else if (name.equals("du")) {
			s = "\\ensuremath{"+usol[0].toString();
			for(int i=1;i<usol.length;i++)
				s = s + " = " + usol[i].toString();
			return s + "}";
		} else if (name.equals("dy")) {
			s = "\\ensuremath{"+ysol[0].toString();
			for(int i=1; i<ysol.length; i++) 
			s = s + " = " + ysol[i].toString();
			return s + "}";
		} else if (name.equals("solution"))
			return "\\ensuremath{" + MathsUtils.composeSolution(solution) + "}";
		else if(name.equals("shortanswer"))
			 return "\\ensuremath{" + solution.lastElement().toString() + "}";
	    else return super.getSection(name);
	}
}
