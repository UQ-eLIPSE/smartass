/** @(#)QuotientRuleSimpleModule.java
 *
 * This file is part of SmartAss and describes class QuotientRuleSimpleModule for 
 * question "Find the derivative of the function f(x)=(ax+b)/(cx+d) using quotient rule" 
 *   
 * Copyright (C) 2007 Department of Mathematics, The University of Queensland
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
 * Class QuotientRuleSimpleModule for 
 * question "Find the derivative 
 * of the function f(x)=(ax+b)/(cx+d) using quotient rule"
 * 
 */
public class QuotientRuleSimpleModule extends MathsModule {
	final int MAX_NUM = 10;
	private String[] var_names={"x","r","z","t","h"};
	Variable vy = new Variable("y");  
	Variable vx = new Variable(var_names[RandomChoice.randInt(0,var_names.length-1)]);
	MathsOp uOfx, vOfx; 
	int a,b,c,d;
	boolean axFirst=true;
	boolean cxFirst=true;

	Vector<MathsOp> solution = new Vector<MathsOp>();

	/**
	 * constructor
	 * 
	 * @param en	an instance of smartass Engine
	 * 
	 */
	public QuotientRuleSimpleModule(Engine en) {
		super(en);
	    a=RandomChoice.randInt(1, MAX_NUM)*RandomChoice.randSign();
		b=RandomChoice.randInt(0, MAX_NUM)*RandomChoice.randSign();
		c=RandomChoice.randInt(1, MAX_NUM)*RandomChoice.randSign();	
		d=RandomChoice.randInt(1, MAX_NUM)*RandomChoice.randSign();
        if ((a*d)==(b*c))
        	b++; 	    	  	
	    if (RandomChoice.randInt(1,2)==2)
	    	axFirst=false;	    		
	    if (RandomChoice.randInt(1,2)==2)
	    	cxFirst=false;		
	    uOfx=(axFirst)? MathsUtils.addTwoTermsNoZeros(unprintableNOne(a,vx),
	    					new IntegerNumber(b)):
	    			   MathsUtils.addTwoTermsNoZeros(new IntegerNumber(b),
	    			   				unprintableNOne(a,vx));
	    vOfx=(cxFirst)? MathsUtils.addTwoTermsNoZeros(unprintableNOne(c,vx),
	    					new IntegerNumber(d)):
	    			   MathsUtils.addTwoTermsNoZeros(new IntegerNumber(d),
	    			   				unprintableNOne(c,vx));			   				
	    			   					
	    MathsOp temp;
	    temp=new FractionOp(new Subtraction(new Multiplication(new IntegerNumber(a), vOfx), 
	    									new Multiplication(uOfx, new IntegerNumber(c))),
	    					new Power(vOfx, new IntegerNumber(2)));
	    solution.add(temp);
	    temp=(cxFirst) ? MathsUtils.addTwoTermsNoZeros(MathsUtils.multiplyVarToConst(a*c,vx), new IntegerNumber(d*a)):
	    						MathsUtils.addTwoTermsNoZeros(new IntegerNumber(d*a), MathsUtils.multiplyVarToConst(a*c,vx));
        temp=(axFirst) ? MathsUtils.addTwoTermsNoZeros(new Addition(temp,MathsUtils.multiplyVarToConst(-c*a,vx)),new IntegerNumber(-b*c)):
        	new Addition(MathsUtils.addTwoTermsNoZeros(temp,new IntegerNumber(-b*c)),MathsUtils.multiplyVarToConst(-c*a,vx));
        temp=new FractionOp(temp, new Power(vOfx, new IntegerNumber(2)));	
        solution.add(temp);
        temp=new FractionOp(new IntegerNumber(Math.abs(a*d-c*b)),new Power(vOfx, new IntegerNumber(2)));
        if ((a*d-c*b)<0) temp=new UnaryMinus(temp);
        solution.add(temp);	
	    	
	} //constructor

		
	/**
     * Composes section with given name
     * "shortanswer", "y","u", "v", "du", "dv", "solution" sections is recognized
     * 
     * @param name	section name
     **/
	public String getSection(String name) {
		String s;
		if(name.equals("u"))
			return "\\ensuremath{"+uOfx.toString() + "}";
		else if (name.equals("v"))
			return "\\ensuremath{" + vOfx.toString() + "}";
		else if (name.equals("y")) 
			return "\\ensuremath{" + (new FractionOp(uOfx,vOfx)).toString() + "}";
		if(name.equals("du"))
			return Integer.toString(a);
		else if (name.equals("dv"))
			return Integer.toString(c);	
		else if (name.equals("solution")){
			s="\\ensuremath{"+solution.get(0).toString();
			for(int i=1; i<solution.size(); i++) 
				s = s + " = " + solution.get(i).toString();
			return s + "}";	
		} else if(name.equals("shortanswer"))	
			 return "\\ensuremath{"+ solution.lastElement().toString() + "}";
	    else return super.getSection(name);
	}
	
//unprintable multiplication that does not typeset 1 or -1
private MathsOp unprintableNOne(int firstNum, MathsOp second){
	if (firstNum==1)
		return second;
	else if (firstNum==-1)
		return new UnaryMinus(second);
	else return new UnprintableMultiplication(new IntegerNumber(firstNum), second);
}

}
