/**
 * This file is part of SmartAss and describes class QuadraticNoFormulaModule for 
 * question "Solve quadratic equations without using the quadratic formula"
 * where equation is one of
 * 
 * 1) ax(bx+c) = 0
 * 2) (ax+b)(cx+d)= 0
 * 3) e(ax+b)(cx+d)= 0
 * 4) (ax+x)^k = 0 (actually, this is not quadratic one)
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
 * Class QuadraticNoFormulaModule generates question and solution for
 * "Solve quadratic equations without using the quadratic formula"
 * where equation is one of
 * 
 * 1) ax(bx+c) = 0
 * 2) (ax+b)(cx+d)= 0
 * 3) e(ax+b)(cx+d)= 0
 * 4) (ax+x)^k = 0 (actually, this is not quadratic one)
 *
 */
public class QuadraticNoFormulaModule extends MathsModule {
	final static int MAX_NUMBER = 10;
	final static int MAX_POWER = 10;

	MathsOp question;
	Variable vx = MathsUtils.createRandomVar();
	Vector<MathsOp> solution1 = new Vector<MathsOp>(),
		solution2 = new Vector<MathsOp>();

	/**
	 * @param engine		SmartAss Engine
	 */
	public QuadraticNoFormulaModule(Engine engine) {
		super(engine);		
		generate(RandomChoice.randInt(1, 4));
	}

	/**
	 * @param engine
	 * @param params
	 */
	public QuadraticNoFormulaModule(Engine engine, String[] params) {
		super(engine, params);
	
		generate((new Integer(params[0])).intValue());
	}
	
	/**
	 * @param eq_type	type of equation (1-4)
	 */
	protected void generate(int eq_type) {
		int[] coef = {0,0,0,0};
		coef[0] = RandomChoice.randInt(1, MAX_NUMBER)*RandomChoice.randSign();
	
		coef[1] = RandomChoice.randInt(1, MAX_NUMBER)*RandomChoice.randSign();
		do{
		switch (eq_type) {
		case 3:
		case 2:
			
			coef[3] = RandomChoice.randInt(1, MAX_NUMBER)*RandomChoice.randSign();
		case 1:
			coef[2] = RandomChoice.randInt(1, MAX_NUMBER)*RandomChoice.randSign();
		}
		} while (((eq_type==2) || (eq_type==3))&& (coef[0]==coef[2]) && (coef[3]==coef[1]));

		//prepare first term of equation (we need it in all 4 kinds of equation)
		if(RandomChoice.randInt(0, 1)==0)
			question = new Addition(MathsUtils.multiplyVarToConst(coef[0], vx), new IntegerNumber(coef[1]));
		else
			question = new Addition(new IntegerNumber(coef[1]), MathsUtils.multiplyVarToConst(coef[0], vx));
		solution2.add(new Equality(question, new IntegerNumber(0)));
		composeSolution(solution2, coef[0], coef[1]);

		switch (eq_type) {
		case 1:
			question = new UnprintableMultiplication(MathsUtils.multiplyVarToConst(coef[2], vx), question);
			solution1.add(new Equality(MathsUtils.multiplyVarToConst(coef[2], vx), new IntegerNumber(0)));
			if (coef[2]!=1){
				solution1.add(new Equality(vx, new IntegerNumber(0)));
			}	
			break;
		case 3:
		case 2:
			if(RandomChoice.randInt(0, 1)==0)
				question = new UnprintableMultiplication(
						new Addition(MathsUtils.multiplyVarToConst(coef[2], vx), new IntegerNumber(coef[3])), 
						question);
			else
				question = new UnprintableMultiplication(
						new Addition(new IntegerNumber(coef[3]), MathsUtils.multiplyVarToConst(coef[2], vx)), 
						question);
			solution1.add(new Equality(((BinaryOp)question).getOp(0), new IntegerNumber(0)));
			if(eq_type==3)
				question = MathsUtils.multiplyVarToConst(RandomChoice.randInt(1, MAX_NUMBER), question);
			composeSolution(solution1, coef[2], coef[3]);
			break;
		case 4:
			question = new Power(question, new IntegerNumber(RandomChoice.randInt(1, MAX_POWER)));
		}
	}
	
	void composeSolution(Vector<MathsOp> solution, int coef1, int coef2) {
	
		if(Math.abs(coef1)==1)
			solution.add(new Equality(vx, new IntegerNumber(-coef2/coef1)));
		else {
			solution.add(new Equality(MathsUtils.multiplyVarToConst(coef1, vx), new IntegerNumber(-coef2)));
			int hcf = HCFModule.hcf(coef2, coef1);
			if (hcf==1)
			 solution.add( (coef2*coef1>0)? new Equality(vx, new UnaryMinus(new FractionOp(
							new IntegerNumber(Math.abs(coef2)),	new IntegerNumber(Math.abs(coef1))))) :
							new Equality(vx, new FractionOp(
							new IntegerNumber(Math.abs(coef2)),	new IntegerNumber(Math.abs(coef1)))));
			else {								
			solution.add(new Equality(vx, new FractionOp(
					new IntegerNumber(-coef2),
					new IntegerNumber(coef1))));
					
			
			if ((-coef2%coef1)==0)
			 solution.add(new Equality(vx, new IntegerNumber(-coef2/coef1)));
			else {
			int fr[] = { (-coef2/hcf), (coef1/hcf)}; 	
			 solution.add( (fr[0]*fr[1]<0)? new Equality(vx, new UnaryMinus(new FractionOp(
							new IntegerNumber(Math.abs(fr[0])),	new IntegerNumber(Math.abs(fr[1]))))) :
							new Equality(vx, new FractionOp(
							new IntegerNumber(Math.abs(fr[0])),	new IntegerNumber(Math.abs(fr[1])))));
			}
			
		}
	 }
	}
    /**
     * Composes section with given name
     * "varname", "question", "shortanswer", "solution", "solution1" and "solution2"  sections is recognized
     * if question has only one branch of solution (type 4 equation)
     * getSection will return an empty string for "solution1"
     * 
     * @param name	section name
     **/
    public String getSection(String name) {
    	if(name.equals("question")) 
    		return "\\ensuremath{" + (new Equality(question, new IntegerNumber(0))).toString() + "}";
    	else if (name.equals("varname"))
    		return "\\ensuremath{" + vx.toString() + "}";
    	else if (name.equals("solution1"))
    		return solutionToStr(solution1);
    	else if (name.equals("solution2"))
    		return solutionToStr(solution2);
    	else if (name.equals("solution"))
    		if(solution1.size()>0)
    		{
    			String alignedSolution="\\begin{alignat*}{3} \n";
 	                alignedSolution+="\\hspace{1mm}  "+solution1.get(0).toString().replace("=","&=")+
 	                " \\hspace{15mm} or &  \\hspace{15mm}  "+solution2.get(0).toString().replace("=","&=");
 	                int mm=Math.max(solution1.size(), solution2.size());
 	                
 	            	for (int i=1; i<mm; i++) {
 	            		alignedSolution+="\\\\ \n ";
 	            		if (solution1.size()>i)
 	              		   alignedSolution+=solution1.get(i).toString().replace("=","&=");
 	              		else alignedSolution+=" & ";   
 	              		alignedSolution+=" & ";
 	              		if (solution2.size()>i)
 	              			alignedSolution+=solution2.get(i).toString().replace("=","&=");
 	            	}		   
 	            	alignedSolution+="\\end{alignat*}";	
 	            	return alignedSolution;	
    		}		
    		else
    			return solutionToStr(solution2);
    	else if (name.equals("shortanswer"))
    		if(solution1.size()>0)
    			return "\\ensuremath{" + solution1.get(solution1.size()-1) +
    				"} or \\ensuremath{" + solution2.get(solution2.size()-1) + "}";
    		else
    			return "\\ensuremath{" + solution2.get(solution2.size()-1) + "}";
    	if (name.equals("part1"))
    		return (solution1.size()>0) ? "\\ensuremath{"+solution1.get(0)+"} " : "";
    	if (name.equals("part2"))
    		return "\\ensuremath{"+solution2.get(0)+"} ";			
    	return super.getSection(name);
    }
    
    String solutionToStr(Vector<MathsOp> solution) {

    	if(solution.size()==0)
    		return "";
		String str = "\\ensuremath{" + solution.get(solution.size()-1) + "}";
		if(solution.size()>1) 
			return MathsUtils.composeSolution(solution, ", so ") + ", so " + str;
		return str;
    }
}
