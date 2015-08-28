/** @(#)ChainRuleSimpleModule.java
 *
 * This file is part of SmartAss and describes class ChainRuleSimpleModule for 
 * question "Find the derivative of the function f(x)=(ax^n+b)^k using chain rule" 
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
 * Class ChainRuleSimpleModule for 
 * question "Find the derivative 
 * of the function f(x)=(ax^n+b)^k using chain rule"
 * 
 */
public class ChainRuleSimpleModule extends MathsModule {
	final int MAX_POW = 8;
	final int MAX_NUM = 10;
	Variable vx = new Variable("x");
	Variable vu = new Variable("u");
	MathsOp question, uOfx, yOfu, yMod; 
	int n, a, k, b;
	boolean xFirst=true;

	MathsOp usol[], ysol[];
	Vector<MathsOp> solution = new Vector<MathsOp>();

	/**
	 * constructor
	 * 
	 * @param en	an instance of smartass Engine
	 * 
	 */
	public ChainRuleSimpleModule(Engine en) {
		super(en);
	    n=RandomChoice.randInt(1, MAX_POW)*RandomChoice.randSign();
		a=RandomChoice.randInt(1, MAX_NUM)*RandomChoice.randSign();
		k=RandomChoice.randInt(1, MAX_POW)*RandomChoice.randSign();	
		if (k==1) k=2;
	    
	    b=RandomChoice.randInt(1, MAX_NUM)*RandomChoice.randSign();	   	    	  	
	    if (RandomChoice.randInt(1,3)==3)
	    	xFirst=false;	    		
	    uOfx=(xFirst)? new Addition(unprintableNOne(a, NOnePower(vx, n)),
	    					new IntegerNumber(b)):
	    			   new Addition(new IntegerNumber(b),
	    			   				unprintableNOne(a, NOnePower(vx, n)));
	    yOfu=new Power(vu, new IntegerNumber(k));  			   				
	    if (k<0) {
	    	question=new FractionOp(new IntegerNumber(1), NOnePower(uOfx, -k));
	    	yMod=new Equality(new FractionOp(new IntegerNumber(1),NOnePower(vu, -k)), 
	    				yOfu);   	
	    } else {
	    	question=new Power(uOfx, new IntegerNumber(k));
	    	yMod=yOfu;
	    }
	    int sizeOfysol=2;
	    MathsOp temp;
	    if (k==2) sizeOfysol=3;
	    ysol=new MathsOp[sizeOfysol];
	    ysol[0]=new Multiplication(new IntegerNumber(k), new Power(vu, new Subtraction(new IntegerNumber(k), new IntegerNumber(1))));
	    ysol[1]=unprintableNOne(k, new Power(vu, new IntegerNumber(k-1)));
	    if (k==2){	
	    	ysol[2]=unprintableNOne(k, vu);
	  		temp=uOfx;
	    	}	
	    else 
	    	temp=new Power(uOfx,new IntegerNumber(k-1));	
	    usol=new MathsOp[2];
	    if (Math.abs(a)==1)
	    	usol[0]=new Multiplication(new IntegerNumber(n*a), new Power(vx, new Subtraction(new IntegerNumber(n), new IntegerNumber(1))));
	    else 
	    	usol[0]=new Multiplication(new Multiplication(new IntegerNumber(a),new IntegerNumber(n)), 
	    		new Power(vx, new Subtraction(new IntegerNumber(n), new IntegerNumber(1))));	    		
	    if (n==1)		
        	usol[1]= new IntegerNumber(a);
        else
        	usol[1]= new UnprintableMultiplication(new IntegerNumber(n*a), NOnePower(vx,n-1)); 
	    solution.add(new Multiplication(ysol[ysol.length-1], usol[usol.length-1]));
	    solution.add(new Multiplication(unprintableNOne(k,temp),usol[1]));
	  //  if (!((k==-1) && (a==1) && (n==1)))
	    
	   int numerator=k*a*n;
	   MathsOp inBracks;	
	  
	   	inBracks=uOfx;
	   	
	   temp=(n==1)? unprintableNOne(numerator, NOnePower(inBracks,k-1)) :
	   	new UnprintableMultiplication(unprintableNOne(numerator,NOnePower(vx,n-1)),NOnePower(inBracks,k-1));
	   solution.add(temp);
	   if ((k<0) || (n<0)){ //writing the answer as a fraction
	   	MathsOp numOp, denomOp=null;
	   	numOp=new IntegerNumber(Math.abs(numerator));
	    if(n<0)
	    	denomOp=NOnePower(vx,-n+1);
	    else
	    	if (n!=1) 
	    		numOp=new UnprintableMultiplication(numOp,NOnePower(vx,n-1)); 			
	   	if (k<0){
	   	 if(denomOp!=null)
	   	 	denomOp=new UnprintableMultiplication(denomOp,NOnePower(inBracks,-k+1));
	   	 else			
	   	    denomOp=NOnePower(inBracks, -k+1);
	   	} else 
	   		numOp=new UnprintableMultiplication(numOp, NOnePower(inBracks, k-1));
	    solution.add((numerator>0)?new FractionOp(numOp, denomOp): new UnaryMinus(new FractionOp(numOp, denomOp)));
	   }  
	    	
	} //constructor

		
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
			return "\\ensuremath{" + uOfx.toString() + "}";
		else if (name.equals("y")) 
			return "\\ensuremath{" + yMod.toString() + "}";
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
		} else if (name.equals("solution")){
			s="\\ensuremath{"+solution.get(0).toString();
			for(int i=1; i<solution.size(); i++) 
				s = s + " = " + solution.get(i).toString();
			return s + "}";	
		} else if(name.equals("shortanswer"))	
			 return "\\ensuremath{"+ solution.lastElement().toString() + "}";
	    else return super.getSection(name);
	}
	
// NOnePower does not return  the Power op if the power is one, i.e. it returns x instead of x^{1}	
private MathsOp NOnePower (MathsOp op1, int intP){
	if (intP==1)
		return op1;
	else return new Power(op1, new IntegerNumber(intP));	
} //NOnePower	

//unprintable multiplication that does not typeset 1 or -1
private MathsOp unprintableNOne(int firstNum, MathsOp second){
	if (firstNum==1)
		return second;
	else if (firstNum==-1)
		return new UnaryMinus(second);
	else return new UnprintableMultiplication(new IntegerNumber(firstNum), second);
}

}
