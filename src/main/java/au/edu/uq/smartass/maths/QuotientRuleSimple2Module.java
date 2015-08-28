/** @(#)QuotientRuleSimple2Module.java
 *
 * This file is part of SmartAss and describes class QuotientRuleSimple2Module for 
 * question "Find the derivative of the function f(x)=(ax^2+bx+c)/(dx^2+ex+f) using quotient rule" 
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
import java.util.TreeSet;
import java.util.Iterator;

import au.edu.uq.smartass.auxiliary.RandomChoice;
import au.edu.uq.smartass.engine.Engine;

/**
 * Class QuotientRuleSimple2Module for 
 * question "Find the derivative 
 * of the function f(x)=(ax^2+bx+c)/(dx^2+ex+f)  using quotient rule"
 * 
 */
public class QuotientRuleSimple2Module extends MathsModule {
	final int MAX_NUM = 10;
	private String[] var_names={"x","r","z","t","h"};
	Variable vy = new Variable("y");  
	Variable vx = new Variable(var_names[RandomChoice.randInt(0,var_names.length-1)]);
	MathsOp uOfx, vOfx, du, dv; 
	int[][] cc=new int [2][3];
	int[][] pwrs=new int[2][3];
	int[][] places=new int[2][3];
	//boolean axFirst=true;
	//boolean cxFirst=true;

	//MathsOp usol[], ysol[];
	Vector<MathsOp> solution = new Vector<MathsOp>();

	/**
	 * constructor
	 * 
	 * @param en	an instance of smartass Engine
	 * 
	 */
	public QuotientRuleSimple2Module(Engine en) {
		super(en);
		pwrs[0]=RandomChoice.randPerm(3);
		pwrs[1]=RandomChoice.randPerm(3);
		for ( int i=0; i<3 ; i++){
			places[0][pwrs[0][i]]=i;
			places[1][pwrs[1][i]]=i;
		}
		for (int i=0; i<2; i++){
	 	 cc[i][0]=RandomChoice.randInt(-MAX_NUM, MAX_NUM);
		 cc[i][1]= (cc[i][0]==0)? RandomChoice.randInt(1,MAX_NUM) : RandomChoice.randInt(-MAX_NUM, MAX_NUM);
		 cc[i][2]= (cc[i][0]*cc[i][1]==0)? RandomChoice.randInt(1,MAX_NUM) : RandomChoice.randInt(-MAX_NUM, MAX_NUM);
		}
		if (cc[0][places[0][2]]==0)
			cc[1][places[1][2]]=RandomChoice.randInt(1,MAX_NUM);
		//do not allow zero derivative (i.e.function of a kind f(x)=a, where a is a number)
        if (((cc[0][places[0][2]]*cc[1][places[1][1]])==(cc[0][places[0][1]]*cc[1][places[1][2]])) && 
        	((cc[0][places[0][1]]*cc[1][places[1][0]])==(cc[0][places[0][0]]*cc[1][places[1][1]])))
        	cc[1][places[1][1]]++; 	 
        		   	  	
	    uOfx=typeSetPolynom(cc[0], vx, pwrs[0]);
	    vOfx=typeSetPolynom(cc[1], vx, pwrs[1]);
	    int[][] tempcc=new int[2][2];
	    int[][] temppwrs=new int[2][2];
	    int k;
	    for (int i=0; i<2; i++){
	    	k=0;
	    	for (int j=0; j<3; j++){
	    	if (pwrs[i][j]!=0){
	    		tempcc[i][k]=cc[i][j]*pwrs[i][j];
	    		temppwrs[i][k]=pwrs[i][j]-1;
	    		k++;
	    	}	
	    }
	    }
		du=typeSetPolynom(tempcc[0], vx, temppwrs[0]);  
		dv=typeSetPolynom(tempcc[1], vx, temppwrs[1]);	
							    			   					
	    MathsOp temp;
	    temp=new FractionOp(new Subtraction(new Multiplication(du, vOfx), 
	    									new Multiplication(uOfx, dv)),
	    					new Power(vOfx, new IntegerNumber(2)));
	    solution.add(temp);
	    int [] temp2cc=new int [12];
	    int [] temp2pwrs=new int[12];
	    //expand the brackets:
	    temp2cc[0]=tempcc[0][0]*cc[1][0]; temp2cc[1]=tempcc[0][0]*cc[1][1]; temp2cc[2]=tempcc[0][0]*cc[1][2];
	    temp2cc[3]=tempcc[0][1]*cc[1][0]; temp2cc[4]=tempcc[0][1]*cc[1][1]; temp2cc[5]=tempcc[0][1]*cc[1][2]; 
	    temp2cc[6]=-cc[0][0]*tempcc[1][0]; temp2cc[7]=-cc[0][0]*tempcc[1][1]; 
	    temp2cc[8]=-cc[0][1]*tempcc[1][0]; temp2cc[9]=-cc[0][1]*tempcc[1][1]; 
	    temp2cc[10]=-cc[0][2]*tempcc[1][0]; temp2cc[11]=-cc[0][2]*tempcc[1][1]; 	
	    	
	    temp2pwrs[0]=temppwrs[0][0]+pwrs[1][0]; temp2pwrs[1]=temppwrs[0][0]+pwrs[1][1]; temp2pwrs[2]=temppwrs[0][0]+pwrs[1][2];
	    temp2pwrs[3]=temppwrs[0][1]+pwrs[1][0]; temp2pwrs[4]=temppwrs[0][1]+pwrs[1][1]; temp2pwrs[5]=temppwrs[0][1]+pwrs[1][2]; 
	    temp2pwrs[6]=pwrs[0][0]+temppwrs[1][0]; temp2pwrs[7]=pwrs[0][0]+temppwrs[1][1]; 
	    temp2pwrs[8]=pwrs[0][1]+temppwrs[1][0]; temp2pwrs[9]=pwrs[0][1]+temppwrs[1][1]; 
	    temp2pwrs[10]=pwrs[0][2]+temppwrs[1][0]; temp2pwrs[11]=pwrs[0][2]+temppwrs[1][1]; 	
	    
	    temp=new FractionOp(typeSetPolynom(temp2cc, vx, temp2pwrs), new Power(vOfx, new IntegerNumber(2)));
	    solution.add(temp);
	    tempcc=simplifyPolynom(temp2cc, temp2pwrs);
	    temp=new FractionOp(typeSetPolynom(tempcc[1], vx, tempcc[0]), new Power(vOfx, new IntegerNumber(2)));
	    solution.add(temp);
	  
	    // simplify fraction, if possible:
	    boolean toCancel=true;
	    for (int i=0; i<tempcc[0].length; i++)
	    	if ((tempcc[0][i]>2) && (tempcc[1][i]!=0))
	    		toCancel=false;  
	    if (toCancel){
	    	temppwrs= new int [1][3];
	    	for (int i=0; i<3; i++)
	    		for (int j=0; j<tempcc[0].length; j++)
	    			if (tempcc[0][j]==i) 
	    				temppwrs[0][i]+=tempcc[1][j];
	    
	    if ((temppwrs[0][0]==-cc[1][places[1][0]]) && (temppwrs[0][1]==-cc[1][places[1][1]]) && (temppwrs[0][2]==-cc[1][places[1][2]]))
	    {
	    	temp=new UnaryMinus(new FractionOp(new IntegerNumber(1),vOfx));
	    	solution.add(temp);
	    }
	    else if ((temppwrs[0][0]==cc[1][places[1][0]]) && (temppwrs[0][1]==cc[1][places[1][1]]) && (temppwrs[0][2]==cc[1][places[1][2]]))
	    {
	    	temp=new FractionOp(new IntegerNumber(1), vOfx);
	    	solution.add(temp);
	    }
	    }
	    	
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
			return "\\ensuremath{" + du.toString() + "}";
		else if (name.equals("dv"))
			return "\\ensuremath{" + dv.toString() + "}";	
		else if (name.equals("solution")){
			s="\\begin{align*} \n "+vy+"' &="+solution.get(0).toString()+"\\\\ \n";
			for(int i=1; i<solution.size()-2; i++) 
				s = s + " &= " + solution.get(i).toString()+"\\\\ \n";
			s+=" &= "+ solution.get(solution.size()-2)+" \n";	
			return s + "\\end{align*}";	
		} else if(name.equals("shortanswer"))	
			 return "\\ensuremath{"+ solution.lastElement().toString() + "}";
	    else return super.getSection(name);
	}
	
/*/unprintable multiplication that does not typeset 1 or -1
private MathsOp unprintableNOne(int firstNum, MathsOp second){
	if (firstNum==1)
		return second;
	else if (firstNum==-1)
		return new UnaryMinus(second);
	else return new UnprintableMultiplication(new IntegerNumber(firstNum), second);
}
*/
//create a polynom
protected MathsOp typeSetPolynom (int[] coefs, Variable v1, int[] powers ){
	MathsOp resPol=MathsUtils.multiplyConstToPower(coefs[0],v1, powers[0]);
	for (int i=1; i<powers.length; i++)
		resPol=MathsUtils.addTwoTermsNoZeros(resPol, MathsUtils.multiplyConstToPower(coefs[i],v1,powers[i]));
	return resPol;
		
}
//simplifies a polynom, for instance makes 3x^2+7+9x^2 into 12x^2+7
//returns int[0] - powers, int [1] -coefs

protected int[][] simplifyPolynom(int[] coefs, int[] powers){
	TreeSet<Integer> setOfPowers=new TreeSet<Integer>();
	for (int i=0; i<powers.length; i++)
		setOfPowers.add(new Integer(powers[i]));
	int[][] res=new int[2][setOfPowers.size()];
	Iterator itr = setOfPowers.iterator();
	int k=res[0].length;
    while(itr.hasNext()){
    	k--;
    	res[0][k]=((Integer)itr.next()).intValue();
    }
    for (int i=0; i<res[0].length; i++)
    	for (int j=0; j<coefs.length; j++)
    		if (powers[j]==res[0][i])
    			res[1][i]+=coefs[j];  
    return res;
  } 	
	
} 
 

