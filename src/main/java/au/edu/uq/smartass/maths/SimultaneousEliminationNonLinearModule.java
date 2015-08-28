/* @(#) SimultaneousEliminationNonLinearModule.java
 *
 * This file is part of SmartAss and describes class SimultaneousEliminationNonLinearModule.
 * Solve two non-linear simultaneous equations using elimination. Replacing non-linear term
 * with a new variable allows us to build simultaneous linear equations.
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
import au.edu.uq.smartass.engine.*;
import au.edu.uq.smartass.auxiliary.*;
import java.util.*;
import java.math.*;
import java.lang.reflect.*;
/**
* Class SimultaneousEliminationNonLinearModule generates the solution to the questions:  
* Solve two non-linear simultaneous equations using elimination.   
* Solution is to build linear equations replacing non-linear term with new variable.
*
* @version 1.0 15.05.2007
*/
public class SimultaneousEliminationNonLinearModule extends SimultaneousEliminationModule{

 private final int MAX_ELEMENT=10;
 private LinearEqn[] equations=new LinearEqn[2];
// char numberOfSolutions; //always 'o' in this module
 MathsOp[][]  xy= new MathsOp[2][]; //solution to the equations	
 private int v1x=RandomChoice.randInt(0,1);
 private String domain="";
 String solution;
 String check="Not defined";


/**
* Constructor SimultaneousEliminationLinearModule, just envoking super(engine).
* 
*/
 public SimultaneousEliminationNonLinearModule(Engine engine) 
	{
				super(engine);	
				generate();
								
	} //constructor
	
	
/**
 * getSection method typesets a question and solution 
 * @return a String containing Latex code for the section
 */
 public String getSection(String name) {
      
        if(name.equals("equation1"))
        	return "\\ensuremath{"+equations[0].toString()+" }";
        if (name.equals("equation2"))
        	return "\\ensuremath{"+equations[1].toString()+" }";	
 		if(name.equals("solution")) 
 		 return solution;		
 		if(name.equals("domain")) 
 		 return domain;	
 		if (name.equals("x")) {
 			String typeX=xy[v1x][0].toString();
 			for (int i=1; i<xy[v1x].length; i++)
 				typeX+=" ; "+xy[v1x][i].toString();
 				return typeX;
 		}		
 		if (name.equals("y")) {
 			String typeY=xy[(v1x+1)%2][0].toString();
 			for (int i=1; i<xy[(v1x+1)%2].length; i++)
 				typeY+=" ; "+xy[(v1x+1)%2][i].toString();
 				return typeY;
 		}			
 
	    if (name.equals("check"))
	    	return check;		    
		return super.getSection(name);
	}
	
/**
 * Method generates coefficient for the equation of a straight line:
 * y=mx+c, m - gradient, c - y-intercept and coordinates for the 
 * point (x1, y1).
 *
 * Then this method also generates Latex-code for solution.
 */
 

 protected void generate() {
 			    int funct=RandomChoice.randInt(0,6);
 			   
 			    int rpl=RandomChoice.randInt(0,1);
 				Variable[] vars=new Variable[2]; 
		
 				int [] vs=new int[2];
 				int value;	
 				String[] names=new String[3];
 				names[v1x]="x";
 				names[(v1x+1)%2]="y";
 				names[2]="z";
 				Variable third=new Variable(names[2]);							
 				vars[(rpl+1)%2]=new Variable(names[(rpl+1)%2]);
 			  	    	
 			    switch (funct) {
 			    
 			    	case 0: //square
 			    	        xy[0]=new MathsOp[1];
 			    	        xy[1]=new MathsOp[1];
 			    	        value=RandomChoice.randInt(0, MAX_ELEMENT);
 			    	        vs[rpl]=value*value;
 			    	        vs[(rpl+1)%2]=RandomChoice.randInt(-MAX_ELEMENT, MAX_ELEMENT);
 			    	        vars[rpl]=new Variable(names[2]); 
 			    	        if (value!=0){
                           	 		xy[rpl][0]=new UnaryPlusMinus(new IntegerNumber(value));
                            		xy[(rpl+1)%2][0]=new IntegerNumber(vs[(rpl+1)%2]);
                            } else {
                            	xy[rpl][0]=new IntegerNumber(0);
                                xy[(rpl+1)%2][0]=new IntegerNumber(vs[(rpl+1)%2]);
                            }	 		 	
                         	vars[rpl].setValue(new Power(xy[rpl][0], new IntegerNumber(2)));
                         	third.setValue(new Power(new Variable(names[rpl]), new IntegerNumber(2)));				 			    	
                            break;
                            
                     case 1: //cube
 			    	        xy[0]=new MathsOp[1];
 			    	        xy[1]=new MathsOp[1];
 			    	        value=RandomChoice.randInt(0, MAX_ELEMENT/3)*RandomChoice.randSign();
 			    	        vs[rpl]=value*value*value;
 			    	        vs[(rpl+1)%2]=RandomChoice.randInt(-MAX_ELEMENT, MAX_ELEMENT);
 			    	        vars[rpl]=new Variable(names[2]);  			    	       
                            xy[rpl][0]=new IntegerNumber(value);
                            xy[(rpl+1)%2][0]=new IntegerNumber(vs[(rpl+1)%2]);                        	 			
                         	vars[rpl].setValue(new Power(xy[rpl][0], new IntegerNumber(3)));
                         	third.setValue(new Power(new Variable(names[rpl]), new IntegerNumber(3)));				 			    	          	
                            break;    
                            	
                    case 2: //ln
                    		xy[0]=new MathsOp[1];
                    		xy[1]=new MathsOp[1];
                    		vs[rpl]=RandomChoice.randInt(0, 1);
                    		vs[(rpl+1)%2]=RandomChoice.randInt(-MAX_ELEMENT, MAX_ELEMENT);
                    		vars[rpl]=new Variable(names[2]);  	
                    		xy[(rpl+1)%2][0]=new IntegerNumber(vs[(rpl+1)%2]);    		    	       
                    		if (vs[rpl]==0) 
                    			xy[rpl][0]=new IntegerNumber(1);	
                    		else
                    			xy[rpl][0]=new Variable("e");
                    		vars[rpl].setValue(new Ln(xy[rpl][0]));
                    		third.setValue(new Ln(new Variable(names[rpl])));	 
                    		break;
                    
                    case 3: //sin
                    		xy[(rpl+1)%2]=new MathsOp[1];
                    		
                    		vs[rpl]=RandomChoice.randInt(-1, 1);
                    		vs[(rpl+1)%2]=RandomChoice.randInt(-MAX_ELEMENT, MAX_ELEMENT);
                    		vars[rpl]=new Variable(names[2]);  	
                    		xy[(rpl+1)%2][0]=new IntegerNumber(vs[(rpl+1)%2]);    		    	       
                    		switch (vs[rpl]){
                    			case -1:
                    				xy[rpl]=new MathsOp[1];
                    				xy[rpl][0]=new FractionOp(new UnprintableMultiplication(new IntegerNumber(3),
                    				new Variable("\\pi")), new IntegerNumber(2));
                    				break;
                    			case 0:
                    				xy[rpl]=new MathsOp[2];
                    				xy[rpl][0]=new IntegerNumber(0);
                    				xy[rpl][1]=new Variable("\\pi");
                    				break;
                    			case 1:		
                    				xy[rpl]=new MathsOp[1];
                    				xy[rpl][0]=new FractionOp(new Variable("\\pi"), new IntegerNumber(2));
                    				break;
                    		}	
                    			  			
                    		vars[rpl].setValue(new SmartFunction("\\sin", xy[rpl][0]));
                    		third.setValue(new SmartFunction("\\sin",new Variable(names[rpl])));
                	                    			
                    		domain="given \\ensuremath{0 \\le "+ names[rpl]+" < 2\\pi }"; 	
                    		break;
                    				
                    case 4: //cos			
                    		xy[(rpl+1)%2]=new MathsOp[1];
                    		vs[rpl]=RandomChoice.randInt(-1, 1);
                    		vs[(rpl+1)%2]=RandomChoice.randInt(-MAX_ELEMENT, MAX_ELEMENT);
                    		vars[rpl]=new Variable(names[2]);  	
                    		xy[(rpl+1)%2][0]=new IntegerNumber(vs[(rpl+1)%2]);    		    	       
                    		switch (vs[rpl]){
                    			case -1:
                    				xy[rpl]=new MathsOp[1];
                    				xy[rpl][0]=new Variable("\\pi");
                    				break;
                    			case 0:
                    				xy[rpl]=new MathsOp[2];
                    				xy[rpl][0]=new FractionOp(new Variable("\\pi"), new IntegerNumber(2));
                    				xy[rpl][1]=new FractionOp(new UnprintableMultiplication(new IntegerNumber(3),
                    				new Variable("\\pi")), new IntegerNumber(2));
                    				break;
                    			case 1:		
                    				xy[rpl]=new MathsOp[1];
                    				xy[rpl][0]=new IntegerNumber(0);
                    				break;
                    		}	
                    			  			
                    		vars[rpl].setValue(new SmartFunction("\\cos", xy[rpl][0]));
                    		third.setValue(new SmartFunction("\\cos",new Variable(names[rpl])));
                	                    			
                    		domain="given \\ensuremath{0 \\le "+ names[rpl]+" < 2\\pi }"; 	
                    		break;	        
                    			
                    case 5: //tan			
                    		xy[(rpl+1)%2]=new MathsOp[1];
                    		vs[rpl]=RandomChoice.randInt(-1, 1);
                    		vs[(rpl+1)%2]=RandomChoice.randInt(-MAX_ELEMENT, MAX_ELEMENT);
                    		vars[rpl]=new Variable(names[2]);  	
                    		xy[(rpl+1)%2][0]=new IntegerNumber(vs[(rpl+1)%2]);    		    	       
                    		switch (vs[rpl]){
                    			case -1:
                    				xy[rpl]=new MathsOp[2];
                    				xy[rpl][0]=new FractionOp(new UnprintableMultiplication(new IntegerNumber(3),
                    				new Variable("\\pi")), new IntegerNumber(4));
                    				xy[rpl][1]=new FractionOp(new UnprintableMultiplication(new IntegerNumber(7),
                    				new Variable("\\pi")), new IntegerNumber(4));
                    				break;
                    			case 0:
                    				xy[rpl]=new MathsOp[2];
                    				xy[rpl][0]=new IntegerNumber(0);
                    				xy[rpl][1]=new Variable("\\pi");
                    				break;
                    			case 1:		
                    				xy[rpl]=new MathsOp[2];
                    				xy[rpl][0]=new FractionOp(new Variable("\\pi"), new IntegerNumber(4));
                    				xy[rpl][1]=new FractionOp(new UnprintableMultiplication(new IntegerNumber(5),
                    				new Variable("\\pi")), new IntegerNumber(4));
                    				break;
                    		}	
                    			  			
                    		vars[rpl].setValue(new SmartFunction("\\tan", xy[rpl][0]));
                    		third.setValue(new SmartFunction("\\tan",new Variable(names[rpl])));
                	                    			
                    		domain="given \\ensuremath{0 \\le "+ names[rpl]+" < 2\\pi }"; 	
                    		break;	   
                    			            
                    case 6: //square root
 			    	        xy[0]=new MathsOp[1];
 			    	        xy[1]=new MathsOp[1]; 
 			    	        vs[rpl]=RandomChoice.randInt(0, MAX_ELEMENT/2);
 			    	        value=vs[rpl]*vs[rpl];
 			    	        vs[(rpl+1)%2]=RandomChoice.randInt(-MAX_ELEMENT, MAX_ELEMENT);
 			    	        vars[rpl]=new Variable(names[2]);  			    	       
                            xy[rpl][0]=new IntegerNumber(value);
                            xy[(rpl+1)%2][0]=new IntegerNumber(vs[(rpl+1)%2]);                        	 			
                         	vars[rpl].setValue(new Sqrt(xy[rpl][0]));
                         	third.setValue(new Sqrt(new Variable(names[rpl])));				 			    	          	
                            break;    		                   			
 
 			    } //switch         
                         	                        	
 			    solution=" Let \\ensuremath{"+(new Equality(third, third.getValue())).toString()+
 			    	"}. Now we have two linear simultaneous equations, which we also number for convenience: "+
 			    		solve(vars,'o', equations, vs) + 
 			    			"\n Now we can find the value of \\ensuremath{"+names[rpl]+"}: \\hspace{3mm} \n \\ensuremath{"+
 			    		(new Equality(third.getValue(),new IntegerNumber(vs[rpl]))).toString()+" \\mbox{, \\hspace{3mm} so  } "+
 			    		(new Equality(new Variable(names[rpl]), xy[rpl][0])).toString();
 			    		for (int i=1; i<xy[rpl].length; i++)
 							solution+=" ; "+xy[rpl][i].toString();
 			    		solution+=" }";
 			    		 	 
  	            	
 	            	check=" \\begin{alignat*}{3} \n";
 	            	
 	            	Vector <MathsOp> checkEq1= checkEquation(equations[0],vs);
 	            	Vector <MathsOp> checkEq2= checkEquation(equations[1],vs);
 	            	
 	            	vars[rpl]=new Variable(vars[rpl].getValue().toString());
 	            	vars[(rpl+1)%2]=new Variable(Integer.toString(vs[(rpl+1)%2]));
 	            	
 	                check+="(1)\\hspace{2mm}  "+
 	                	substituteValues(equations[0], vars).replace(MathsOp.EQUALITY_SIGN,"&=")+
 	                	
 	                	
 	                
 	                " \\hspace{30 mm} & (2)\\hspace{2mm}  "+substituteValues(equations[1], vars).replace(MathsOp.EQUALITY_SIGN,"&=");
 	                int mm=Math.max(checkEq1.size(), checkEq2.size());
 	            	for (int i=0; i<mm; i++) {
 	            		check+="\\\\ \n ";
 	            		if (checkEq1.size()>i)
 	              		   check+=checkEq1.get(i).toString().replace(MathsOp.EQUALITY_SIGN,"&=");
 	              		else check+=" & ";   
 	              		check+=" & ";
 	              		if (checkEq2.size()>i)
 	              			check+=checkEq2.get(i).toString().replace(MathsOp.EQUALITY_SIGN,"&=");
 	            	}		   
 	            	
 	            	check+="\\end{alignat*}";
 	            	if (xy[rpl].length>1) 
 	            	 check+="We have checked one value of \\ensuremath{"+names[rpl]+"}, you do the other! ";
 	            	else
 	            	 check+="Both equations turned into true statements, as required. Hence the answer is correct.";				
 	            	vars[rpl]=new Variable(third.getValue().toString());
 				    vars[(rpl+1)%2]=new Variable(names[(rpl+1)%2]);
 	            	equations[0].setVars(vars);
 	            	equations[1].setVars(vars);	
         } //generate
public String substituteValues(LinearEqn eqn, MathsOp[] variables){
	    int [][] left= eqn.getLeft();
	    int [][] right=eqn.getRight();
	    int size=left[0].length;
	      
 		MathsOp [] solSteps=new MathsOp[size];
 		MathsOp leftOp;

 		for (int i=0; i<size; i++){
 			if (left[1][i]==-1)
 				solSteps[i]=new IntegerNumber(left[0][i]);
 			else
 			{
 			  if (left[0][i]==1)
 			  	solSteps[i]=variables[left[1][i]];
 			   else 
 			  if (left[0][i]==-1)
 			  	solSteps[i]=new UnaryMinus(variables[left[1][i]]);	
 			   else	
 			  if (left[0][i]==0)
 			     solSteps[i]=new IntegerNumber(0);
 			   else	 	
 				solSteps[i]=new Multiplication(new IntegerNumber(left[0][i]),variables[left[1][i]]);
 			}	
 		}	
 	    leftOp=solSteps[left[2][0]];
 
 	    for (int i=1; i<size; i++)
 	    	leftOp=MathsUtils.addTwoTermsNoZeros(leftOp,solSteps[left[2][i]]);
 	 	
 	    size=right[0].length;
 	    MathsOp rightOp; 
 	    solSteps=new MathsOp[size];
 		for (int i=0; i<size; i++){
 		    if (right[1][i]==-1)
 				solSteps[i]=new IntegerNumber(right[0][i]);
 			else
 			{
 			  if (right[0][i]==1)
 			  	solSteps[i]=variables[right[1][i]];
 			   else 
 			  if (right[0][i]==-1)
 			  	solSteps[i]=new UnaryMinus(variables[right[1][i]]);	
 			   else	
 			  if (right[0][i]==0)
 			     solSteps[i]=new IntegerNumber(0);
 			   else	 	
 				solSteps[i]=new Multiplication(new IntegerNumber(right[0][i]),variables[right[1][i]]);
 			}	
 		}	
 	    rightOp=solSteps[right[2][0]];
 	    for (int i=1; i<size; i++)
 	    	rightOp=MathsUtils.addTwoTermsNoZeros(rightOp,solSteps[right[2][i]]);
 	            	        
 	    return (new Equality(leftOp, rightOp)).toString(); 	
 	
        } //substituteValues()   		 
 
 } 