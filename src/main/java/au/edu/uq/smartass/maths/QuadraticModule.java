/**
 * This file is part of SmartAss and describes class QuadraticModule for 
 * question "Solve quadratic equation using the quadratic formula"
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
 * Class QuadraticModule generates question and solution for question 
 * "Solve quadratic equation using the quadratic formula"
 *
 */
public class QuadraticModule extends MathsModule {
	final static int MAX_NUMBER = 5;
	int[] coef = {0,0,0};
	int[] answ = {0,0};
	int solutions_num = 0;
	boolean approx = false;
	boolean realnum = false;
	MathsOp[] question=new MathsOp[2]; //question[0]= equation with randomly placed terms, question[1]= in a standard form ax^2+bx+c=0;
	Variable vx = MathsUtils.createRandomVar();
	Vector<MathsOp> solution = new Vector<MathsOp>();
	Vector<MathsOp> branch1 = new Vector<MathsOp>();
	Vector<MathsOp> branch2 = new Vector<MathsOp>();

	/**
	 * @param engine 	an instance of smartass {@link Engine} 
	 */
	public QuadraticModule(Engine engine) {
		super(engine);
		
		answ[0] = RandomChoice.randInt(1, MAX_NUMBER)*RandomChoice.randSign();
		answ[1] = RandomChoice.randInt(1, MAX_NUMBER)*RandomChoice.randSign();
		
		coef[0] = RandomChoice.randInt(1, MAX_NUMBER)*RandomChoice.randSign();
	
		coef[2] = coef[0]*answ[0]*answ[1];
		if ((RandomChoice.randInt(0,3)==0) && ((coef[0]*coef[2])>0))
			coef[1] = (int)(Math.sqrt(4*coef[0]*coef[2]-RandomChoice.randInt(1,4*coef[0]*coef[2])))*RandomChoice.randSign();
		else	
			coef[1] = coef[0]*(-answ[0] - answ[1]);		
				
				
		int[] tmpCoefs=new int [6]; //generating random form of equation for getSection("question")
		if (RandomChoice.randInt(0,2)==2)
			tmpCoefs[3]=RandomChoice.randInt(coef[0], MAX_NUMBER+1); 
		tmpCoefs[0]=coef[0]+tmpCoefs[3]; 
		if (RandomChoice.randInt(0,2)==2)
		{
			if (RandomChoice.randInt(0,4)==0)
				tmpCoefs[4]=-coef[1];
			else	
			    tmpCoefs[4]=RandomChoice.randInt(-MAX_NUMBER, MAX_NUMBER);
		} 
		tmpCoefs[1]=coef[1]+tmpCoefs[4];		
		if (RandomChoice.randInt(0,2)==2)
		{
			if (RandomChoice.randInt(0,3)==0)
				tmpCoefs[5]=-coef[2];
			else	
			    tmpCoefs[5]=RandomChoice.randInt(-MAX_NUMBER, MAX_NUMBER);
		} 
		tmpCoefs[2]=coef[2]+tmpCoefs[5];
	//	System.out.println("COEF:");
	//	System.out.println(coef[0]+" "+coef[1]+" "+coef[2]);
	//	System.out.println("TMP_COEFS:");
	//	System.out.println(tmpCoefs[0]+" "+tmpCoefs[1]+" "+tmpCoefs[2]+" "+tmpCoefs[3]+" "+tmpCoefs[4]+" "+tmpCoefs[5]);
		MathsOp[] termsOps=new MathsOp[6];
		termsOps[0]=MathsUtils.multiplyVarToConst(tmpCoefs[0], new Power(vx, new IntegerNumber(2)));
		termsOps[1]=MathsUtils.multiplyVarToConst(tmpCoefs[1], vx);
		termsOps[2]=new IntegerNumber(tmpCoefs[2]);
		termsOps[3]=MathsUtils.multiplyVarToConst(tmpCoefs[3], new Power(vx, new IntegerNumber(2)));
		termsOps[4]=MathsUtils.multiplyVarToConst(tmpCoefs[4], vx);
		termsOps[5]=new IntegerNumber(tmpCoefs[5]);
		int[] placesLeft=RandomChoice.randPerm(3);
		int[] placesRight=RandomChoice.randPerm(3);
		question[0]=new Equality(MathsUtils.addTwoTermsNoZeros(
						MathsUtils.addTwoTermsNoZeros(termsOps[placesLeft[0]], termsOps[placesLeft[1]]),
						termsOps[placesLeft[2]]),
						MathsUtils.addTwoTermsNoZeros(
						MathsUtils.addTwoTermsNoZeros(termsOps[placesRight[0]+3], termsOps[placesRight[1]+3]),
						termsOps[placesRight[2]+3]));
						
		generate();
	}

	/**
	 * @param engine 	an instance of smartass {@link Engine} 
	 * @param params
	 */
	public QuadraticModule(Engine engine, String[] params) {
		super(engine);
		for(int i=0;i<params.length && i<3;i++) 
			coef[i] = Integer.parseInt(params[i]);
		if(params.length==4)
			vx = new Variable(params[3]);
		generate();
	}

	void generate() {
		int under_root;
		double real_under_root;
		MathsOp tmpOp;
	
		question[1] = new Equality( 
			 MathsUtils.addTwoTermsNoZeros(MathsUtils.addTwoTermsNoZeros(
					MathsUtils.multiplyVarToConst(coef[0], new Power(vx, new IntegerNumber(2))),
					MathsUtils.multiplyVarToConst(coef[1], vx)), new IntegerNumber(coef[2])),
			new IntegerNumber(0));
		if(coef[1]==0) { //b=0, so we need no quadratic formula
		
		solution.add(
					new Equality(
							MathsUtils.multiplyVarToConst(coef[0], 
									new Power(vx, new IntegerNumber(2))),
							new IntegerNumber(-coef[2])));
							
		    if (coef[2]%coef[0]==0)
		    	tmpOp=new IntegerNumber(-coef[2]/coef[0]);
		    else 
		        tmpOp= ((coef[2]*coef[0])>0) ? 
		        	new UnaryMinus(new FractionOp(new IntegerNumber(Math.abs(coef[2])), new IntegerNumber(Math.abs(coef[0])))) :
		        	new FractionOp(new IntegerNumber(Math.abs(coef[2])), new IntegerNumber(Math.abs(coef[0])));

			solution.add(new Equality(vx,new UnaryPlusMinus( new Sqrt(tmpOp))));
									
			if(coef[2]*coef[0]>0) {
				solutions_num = 0;					
			} else	{
				if(coef[2]==0) {
					solutions_num = 1;
					branch1.add(new IntegerNumber(0));
				} else {
					real_under_root=-(double)coef[2]/coef[0];
					if(Math.sqrt(real_under_root)!=(int)Math.sqrt(real_under_root)) {
						branch1.add(new DecimalNumber(Math.sqrt(real_under_root)));
						branch2.add(new DecimalNumber(-Math.sqrt(real_under_root)));
						if (Double.valueOf(branch1.lastElement().toString())!=Math.sqrt(real_under_root))
							approx=true;
					} else{
					int root = (int)Math.sqrt(real_under_root);
					branch1.add(new IntegerNumber(root));
					branch2.add(new IntegerNumber(-root));
					}
					solutions_num = 2;
				
				}
			}
		}
		else { //use quadratic formula
			int mb = -coef[1];
			solution.add(
					new FractionOp(
							new PlusMinus(   
									new IntegerNumber(mb),
									new Sqrt(new Subtraction(
											new Power(new IntegerNumber(coef[1]), new IntegerNumber(2)),
											new Multiplication(
											new Multiplication(new IntegerNumber(4),new IntegerNumber(coef[0])),
															new IntegerNumber(coef[2]))))),
							new Multiplication(new IntegerNumber(2), new IntegerNumber(coef[0]))));
			solution.add(
					new FractionOp(
							new PlusMinus(   
									new IntegerNumber(mb),
									new Sqrt(new Subtraction(
											new IntegerNumber(coef[1]*coef[1]),
											new IntegerNumber(4*coef[0]*coef[2])))),
							new IntegerNumber(2*coef[0])) );
			under_root = coef[1]*coef[1] - 4*coef[0]*coef[2];
			solution.add(
					new FractionOp(
							new PlusMinus(   
									new IntegerNumber(mb),
									new Sqrt(new IntegerNumber(under_root))),
							new IntegerNumber(2*coef[0])) );
			if(under_root>=0) {
				//one or two roots
				if(under_root==0)
					solutions_num = 1;
				else
					solutions_num = 2;
				
				if(Math.sqrt(under_root)==(int)Math.sqrt(under_root)) {
					int root = (int)Math.sqrt(under_root);
					solution.add(
							new FractionOp(
									new PlusMinus(   
											new IntegerNumber(mb),
											new IntegerNumber(root)),
									new IntegerNumber(2*coef[0])) );
					if(solutions_num==2) {
					branch1.add(
								new FractionOp(
										new Addition(   
												new IntegerNumber(mb),
												new IntegerNumber(root)),
										new IntegerNumber(2*coef[0])) );
						branch2.add(
								new FractionOp(
										new Subtraction(   
												new IntegerNumber(mb),
												new IntegerNumber(root)),
										new IntegerNumber(2*coef[0])) );
					}
					branch1.add(
							new FractionOp(
									new IntegerNumber(mb + root),
									new IntegerNumber(2*coef[0])) );
					branch2.add(
							new FractionOp(
									new IntegerNumber(mb - root),
									new IntegerNumber(2*coef[0])) );
					int hcf;
					if((mb+root)%(2*coef[0])==0)
						branch1.add(new IntegerNumber((mb + root) / (2*coef[0])) );
					else if((hcf = HCFModule.hcf(mb+root, 2*coef[0]))>1)
						branch1.add(
								new FractionOp(
										new IntegerNumber((mb + root) / hcf),
										new IntegerNumber((2*coef[0]) / hcf)) );
					if((mb-root)%(2*coef[0])==0)
						branch2.add(new IntegerNumber((mb - root) / (2*coef[0])) );
					else if((hcf = HCFModule.hcf(mb+root, 2*coef[0]))>1)
						branch1.add(
								new FractionOp(
										new IntegerNumber((mb - root) / hcf),
										new IntegerNumber((2*coef[0]) / hcf)) );
						
				} else {
				
					double root = Math.sqrt(under_root);
					branch1.add(new DecimalNumber((mb + root) / (2*coef[0])) );
					branch2.add(new DecimalNumber((mb - root) / (2*coef[0])) );
					if (Double.valueOf(branch1.lastElement().toString())!=(mb+root)/(2*coef[0]))
							approx=true;
				}
			} else 
				solutions_num = 0;
		}
	}

    /**
     * Composes section with given name
     * "varname", "question","quadraticform", "shortanswer", "solution"
     * ,"a", "b", "c" (coefficients for ax^2+bx+c=0), "numberofsolutions"
     * sections is recognized
     * if question has only one branch of solution (type 4 equation)
     * getSection will return an empty string for "solution2"
     * 
     * @param name	section name
     **/
    public String getSection(String name) {
    	if(name.equals("question")) 
    		return "\\ensuremath{" +((question[0]==null)? question[1].toString():question[0].toString()) + "}";
    	else if (name.equals("quadraticform"))
    		return "\\ensuremath{"+question[1].toString()+"}";		
    	else if (name.equals("varname"))
    		return "\\ensuremath{" + vx.toString() + "}";
    	if (name.equals("a"))
    		return Integer.toString(coef[0]);
    	if (name.equals("b"))
    		return Integer.toString(coef[1]);
    	if (name.equals("c"))
    		return Integer.toString(coef[2]);	
    	if (name.equals("numberofsolutions"))
    		return Integer.toString(solutions_num);	
    	else if (name.equals("solution")) {
    		String sol;
    		String appStr=(approx)? " \\approx " : " = ";
    		if(coef[1]==0) {
    			sol = " \\begin{align*}" + solution.get(0).toString().replace("=","&=");
    			for (int is=1; is<solution.size(); is++) 
    				sol+="\\\\ \n "+solution.get(is).toString().replace("=","&=");
		
    			if(solutions_num>0) {
 		
		    		sol +=  "\\\\ \n" + vx +" &"+appStr + branch1.lastElement().toString();
		    		if(solutions_num>1)
		    			
	    				sol += " \\hspace{2mm} or \\hspace{2mm} " + branch2.lastElement().toString();
    			}
    			
    		} else {
	    		sol = "\\begin{align*}" + vx + " &= " +MathsUtils.composeSolution(solution, "\\\\ &= ");
	    		if(solutions_num==0 || approx)
	    				sol = sol + "\\\\ &= " + solution.lastElement().toString();
	    		if(solutions_num>0) {
		    		for(int i=0;i<branch1.size();i++) {
		    			if(approx)
		    				sol = sol + "\\\\ &\\approx";
		    			else
		    				sol = sol + "\\\\ &=";
		    			sol = sol + " \\ensuremath{" + branch1.get(i).toString() + "}";
		    			if(solutions_num==2)
		    				sol = sol + " \\hspace{2mm} or  \\hspace{2mm} \\ensuremath{" + branch2.get(i).toString() +"}";
		    		}
	    		}
    		}
    		return sol + "\\end{align*}";
    	}
    	else if(name.equals("shortanswer")) {
    		String ans;
    		switch(solutions_num) {
    		case 0:
    			return("no solution");
    		case 1:
    			ans="\\ensuremath{"+vx;
    		if(approx)
    				ans = ans + "\\approx";
    			else
    				ans = ans + " = ";
        		return ans + branch1.get(branch1.size()-1).toString() + "}";
    		case 2:
    			ans = "\\ensuremath{" + vx;
    			if(approx)
    				ans = ans + "\\approx";
    			else
    				ans = ans + " = ";
        		return ans + branch1.get(branch1.size()-1).toString() + 
    					"} or \\ensuremath{" + branch2.get(branch2.size()-1).toString() +"}";
    		}
    	}
    	return super.getSection(name);
    }
}
