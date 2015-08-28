/* @(#) FunctionGraphsCompositeModule.java
 *
 * This file is part of SmartAss and describes class FunctionGraphsCompositeModule.
 * Match each equation with its corresponding graph. Graphs provided in external file
 * and referenced from the template.
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

/**
* Class FunctionGraphsCompositeModule describes the question on 
* function graphs - match the equations (up to NUMBER_OF_GRAPHS=20
* equations provided by getSection) with its corresponding graph. Total number of 
* different graphs (and equation types) is 20.   
*
* @version 1.0 26.05.2007
*/
public class FunctionGraphsCompositeModule extends MathsModule{
 private final int NUMBER_OF_GRAPHS=20;
 private final char FIRST_CHAR='A';
 private final int MAX_NUMBER=15;
/////////////////////////////////////////////////////////////////////////////////////////////////////////////// 
 private final int[] SELECTION=RandomChoice.randPerm(NUMBER_OF_GRAPHS);	
/////////////////////////////////////////////////////////////////////////////////////////////////////////////// 
 private String[] questions=new String[NUMBER_OF_GRAPHS];
 private String[] solutions=new String[NUMBER_OF_GRAPHS];
 Variable x=new Variable("x");
 Variable y=new Variable("y");
 //private String[] shortanswers=new String[NUMBER_OF_GRAPHS]; 
 
 
 
/**
* Constructor FunctionGraphsCompositeModule initialises the question.
* 
*/
 public FunctionGraphsCompositeModule(Engine engine) 
	{
				super(engine);	
				generate();				
	} //constructor
	
/**
 * getSection method typesets a question and solution 
 * @return a String containing Latex code for the section
 */
 public String getSection(String name) { 		
 // user is supposed to number questions from 1 to NUMBER_OF_QUESTIONS
   	    try{   	    
 	    int num;  
 			 if (name.contains("question")){
 			    num=Integer.parseInt(name.substring(8));
 			    if ((num>0) && (num<=NUMBER_OF_GRAPHS))
 		 			return questions[SELECTION[num-1]];
 			 }	
 		 	  if (name.contains("solution")){
 			    num=Integer.parseInt(name.substring(8));
 			    if ((num>0) && (num<=NUMBER_OF_GRAPHS))
 		 			return solutions[SELECTION[num-1]];
 			 }	
 		 	  if (name.contains("shortanswer")){
 			    num=Integer.parseInt(name.substring(11));
 			    if ((num>0) && (num<=NUMBER_OF_GRAPHS))
 		 			return Character.toString((char)((int)FIRST_CHAR+SELECTION[num-1]));
 			 }	
   	    }
   	    catch (NumberFormatException e){
   	    }
		return super.getSection(name);
	}
	
/**
 * Method generates NUMBER_OF_EQUATIONS=20 simple equations and 
 * and Latex-code for questions/solutions.
 *
 */
 
// Generates sets
 protected void generate() {
 /*/////////////////////////TESTING/////////////////////////////////////////////////////	
 	for (int i=0; i<NUMBER_OF_GRAPHS; i++)
 	{
 		questions[i]="Q"+i;
 		solutions[i]="S"+i;
 			System.out.println(SELECTION[i]);
 	}
 
 /*//////////////////////////////////////////////////////////////////////////////	
 LinearEqn simplEqn;
 int [][][] coefs;
 Variable[] vars={y,x};
 MathsOp tmpEquation;
 int[] signs={0,1,1};
 int a,b,c;
 
//linear equations of the form coefs0*y + coefs1*x+coefs2=0;
// or  ay+bx=c;

 //GRAPH  1: x=-k, k - negative
 	  //Maximum 5 terms in equation.
 	   
 	    if (RandomChoice.randInt(0,1)==0)
 	        signs[1]=signs[2]=-1;
 	   	coefs=getCoefs(signs);	
 	   	simplEqn=new LinearEqn(coefs[0], coefs[1], vars);
 	    questions[0]="\\ensuremath{"+simplEqn.toString()+"}";
 	    b=coefs[0][0][1]-coefs[1][0][1];
 	    c=coefs[1][0][2]-coefs[0][0][2];
 	    solutions[0]=questions[0];
 	    if ((b<0) && (coefs[0][0][1]==0)){
 	    		b=-b; c=-c;
 	    }
 	    if (! ((coefs[0][0][0]==0)&&
 	    	 	(((coefs[1][0][1]==0)&&(coefs[0][0][2]==0))||((coefs[0][0][1]==0)&&(coefs[1][0][2]==0))))) {
 	    	tmpEquation=new Equality(MathsUtils.multiplyVarToConst(b,x),new IntegerNumber(c));
 	    	solutions[0]+=", so \\ensuremath{"+tmpEquation.toString()+"}";
 	    }	
 	    if (b!=1)
 	    		solutions[0]+=" , so \\ensuremath{"+(new Equality(x,fractionSimple(c,b))).toString()+"}";	   
 	    					
 	    solutions[0]+=". Hence this is a vertical line, with \\ensuremath{"+x.toString()+
 	    	"} negative. Hence the matching graph is Graph "+FIRST_CHAR; 		
 						     		 
 //GRAPH  2: x=k, k - positive
        signs[0]=0; 
 	    if (RandomChoice.randInt(0,1)==0)
 	        signs[2]=1;
 	    else
 	    	signs[2]=-1;
 	    signs[1]=-signs[2];	    
 	   	coefs=getCoefs(signs);
 	   	simplEqn=new LinearEqn(coefs[0], coefs[1], vars);
 	    questions[1]="\\ensuremath{"+simplEqn.toString()+"}";
 	    b=coefs[0][0][1]-coefs[1][0][1];
 	    c=coefs[1][0][2]-coefs[0][0][2];
 	    solutions[1]=questions[1];
 	    if ((b<0) && (coefs[0][0][1]==0)){
 	    		b=-b; c=-c;
 	    }
 	    if (! ((coefs[0][0][0]==0)&&
 	    	 	(((coefs[1][0][1]==0)&&(coefs[0][0][2]==0))||((coefs[0][0][1]==0)&&(coefs[1][0][2]==0))))) {
 	    	tmpEquation=new Equality(MathsUtils.multiplyVarToConst(b,x),new IntegerNumber(c));
 	    	solutions[1]+=", so \\ensuremath{"+tmpEquation.toString()+"}";
 	    }	
 	    if (b!=1)
 	    		solutions[1]+=" , so \\ensuremath{"+(new Equality(x,fractionSimple(c,b))).toString()+"}";	   
 	    solutions[1]+=". Hence this is a vertical line, with \\ensuremath{"+x.toString()+
 	    	"} positive. Hence the matching graph is Graph "+(char)((int)FIRST_CHAR+1); 
 	    				
 //GRAPH  3: y=k, k - positive
        signs[1]=0; 
 	    if (RandomChoice.randInt(0,1)==0)
 	        signs[2]=1;
 	    else
 	    	signs[2]=-1;
 	    signs[0]=-signs[2];	    
 	   	coefs=getCoefs(signs);
 	   	simplEqn=new LinearEqn(coefs[0], coefs[1], vars);
 	    questions[2]="\\ensuremath{"+simplEqn.toString()+"}";
 	    a=coefs[0][0][0]-coefs[1][0][0];
 	    c=coefs[1][0][2]-coefs[0][0][2];
 	    solutions[2]=questions[2];
 	    if ((a<0)&&(coefs[0][0][0]==0)){
 	    		a=-a; c=-c;
 	    	}
 	    if (!((coefs[0][0][1]==0)&&
 	        (((coefs[1][0][0]==0) && (coefs[0][0][2]==0)) || ((coefs[0][0][0]==0)&&(coefs[1][0][2]==0))))){
 	    	tmpEquation=new Equality(MathsUtils.multiplyVarToConst(a,y),new IntegerNumber(c));
 	    	solutions[2]+=", so \\ensuremath{"+tmpEquation.toString()+"}";
 	    }	
 	    if (a!=1)
 	    		solutions[2]+=" , so \\ensuremath{"+(new Equality(y,fractionSimple(c,a))).toString()+"}";	
 	    			
 	    solutions[2]+=". Hence this is a horizontal line, with \\ensuremath{"+y.toString()+
 	    	"} positive. Hence the matching graph is Graph "+(char)((int)FIRST_CHAR+2); 	
 	    
 //GRAPH  4: y=k, k - negative
        signs[1]=0; 
 	    if (RandomChoice.randInt(0,1)==0)
 	        signs[2]=1;
 	    else
 	    	signs[2]=-1;
 	    signs[0]=signs[2];	    
 	   	coefs=getCoefs(signs);
 	   	simplEqn=new LinearEqn(coefs[0], coefs[1], vars);
 	    questions[3]="\\ensuremath{"+simplEqn.toString()+"}";
 	    a=coefs[0][0][0]-coefs[1][0][0];
 	    c=coefs[1][0][2]-coefs[0][0][2];
 	    solutions[3]=questions[3];
 	    if ((a<0)&&(coefs[0][0][0]==0)){
 	    		a=-a; c=-c;
 	    	}
 	    if (!((coefs[0][0][1]==0)&&
 	        (((coefs[1][0][0]==0) && (coefs[0][0][2]==0)) || ((coefs[0][0][0]==0)&&(coefs[1][0][2]==0))))){
 	    	tmpEquation=new Equality(MathsUtils.multiplyVarToConst(a,y),new IntegerNumber(c));
 	    	solutions[3]+=", so \\ensuremath{"+tmpEquation.toString()+"}";
 	    }	
 	    if (a!=1)
 	    		solutions[3]+=" , so \\ensuremath{"+(new Equality(y,fractionSimple(c,a))).toString()+"}";	
 	    			
 	    solutions[3]+=". Hence this is a horizontal line, with \\ensuremath{"+y.toString()+
 	    	"} negative. Hence the matching graph is Graph "+(char)((int)FIRST_CHAR+3); 	
 
 //GRAPH  5: y=mx+n, m - positive, n - negative
 	    signs[0]=RandomChoice.randSign();
 	    signs[1]=-signs[0];
 	    signs[2]=signs[0];	    
 	   	coefs=getCoefs(signs);
 	   	simplEqn=new LinearEqn(coefs[0], coefs[1], vars);
 	    questions[4]="\\ensuremath{"+simplEqn.toString()+"}";
 	    a=coefs[0][0][0]-coefs[1][0][0];
 	    b=coefs[1][0][1]-coefs[0][0][1];
 	    c=coefs[1][0][2]-coefs[0][0][2];
 	    solutions[4]=questions[4];
 	    
 	    if ((a<0)){
 	    		a=-a; c=-c; b=-b;
 	    	}
 	    if (!( ((coefs[1][0][0]==0) && (coefs[0][0][1]==0) && (coefs[0][0][2]==0) && (coefs[0][0][0]>0)) || 
 	    	((coefs[0][0][0]==0)&& (coefs[1][0][1]==0) &&(coefs[1][0][2]==0) && (coefs[1][0][0]>0)))){
 	    	tmpEquation=new Equality(MathsUtils.multiplyVarToConst(a,y),
 	    					new Addition(MathsUtils.multiplyVarToConst(b,x),new IntegerNumber(c)));
 	    	solutions[4]+=", so \\ensuremath{"+tmpEquation.toString()+"}";
 	    }	
 	 
 	    			
 	    solutions[4]+=". Hence this is a straight line, with positive gradient and negative \\ensuremath{"+y.toString()+
 	    	"}-intercept. Hence the matching graph is Graph "+(char)((int)FIRST_CHAR+4); 	    
 	    
//GRAPH  6: y=mx, m - positive
 	    signs[0]=RandomChoice.randSign();
 	    signs[1]=-signs[0];
 	    signs[2]=0;	    
 	   	coefs=getCoefs(signs);
 	   	simplEqn=new LinearEqn(coefs[0], coefs[1], vars);
 	    questions[5]="\\ensuremath{"+simplEqn.toString()+"}";
 	    a=coefs[0][0][0]-coefs[1][0][0];
 	    b=coefs[1][0][1]-coefs[0][0][1];
 	    solutions[5]=questions[5];
 	    
 	    if ((a<0)){
 	    		a=-a; b=-b;
 	    	}
 	    if (!((coefs[1][0][2]==0) && (((coefs[1][0][0]==0) && (coefs[0][0][1]==0) && (coefs[0][0][0]>0)) || 
 	    	((coefs[0][0][0]==0)&& (coefs[1][0][1]==0) &&(coefs[1][0][0]>0))))){
 	    	tmpEquation=new Equality(MathsUtils.multiplyVarToConst(a,y),
 	    					MathsUtils.multiplyVarToConst(b,x));
 	    	solutions[5]+=", so \\ensuremath{"+tmpEquation.toString()+"}";
 	    }	
 	    			
 	    solutions[5]+=". Hence this is a straight line, with positive gradient and passing "+
 	    	"through the origin. Hence the matching graph is Graph "+(char)((int)FIRST_CHAR+5); 		    


 //GRAPH  7: y=mx+n, m - positive, n - positive
 	    signs[0]=RandomChoice.randSign();
 	    signs[1]=-signs[0];
 	    signs[2]=-signs[0];	    
 	   	coefs=getCoefs(signs);
 	   	simplEqn=new LinearEqn(coefs[0], coefs[1], vars);
 	    questions[6]="\\ensuremath{"+simplEqn.toString()+"}";
 	    a=coefs[0][0][0]-coefs[1][0][0];
 	    b=coefs[1][0][1]-coefs[0][0][1];
 	    c=coefs[1][0][2]-coefs[0][0][2];
 	    solutions[6]=questions[6];
 	    
 	    if ((a<0)){
 	    		a=-a; c=-c; b=-b;
 	    	}
 	    if (!( ((coefs[1][0][0]==0) && (coefs[0][0][1]==0) && (coefs[0][0][2]==0) && (coefs[0][0][0]>0)) || 
 	    	((coefs[0][0][0]==0)&& (coefs[1][0][1]==0) &&(coefs[1][0][2]==0) && (coefs[1][0][0]>0)))){
 	    	tmpEquation=new Equality(MathsUtils.multiplyVarToConst(a,y),
 	    					new Addition(MathsUtils.multiplyVarToConst(b,x),new IntegerNumber(c)));
 	    	solutions[6]+=", so \\ensuremath{"+tmpEquation.toString()+"}";
 	    }	
 	    			
 	    solutions[6]+=". Hence this is a straight line, with positive gradient and positive \\ensuremath{"+y.toString()+
 	    	"}-intercept. Hence the matching graph is Graph "+(char)((int)FIRST_CHAR+6); 	    
 
 //GRAPH  8: y=mx+n, m - negative, n - positive
 	    signs[0]=RandomChoice.randSign();
 	    signs[1]=signs[0];
 	    signs[2]=-signs[0];	    
 	   	coefs=getCoefs(signs);
 	   	simplEqn=new LinearEqn(coefs[0], coefs[1], vars);
 	    questions[7]="\\ensuremath{"+simplEqn.toString()+"}";
 	    a=coefs[0][0][0]-coefs[1][0][0];
 	    b=coefs[1][0][1]-coefs[0][0][1];
 	    c=coefs[1][0][2]-coefs[0][0][2];
 	    solutions[7]=questions[7];
 	    
 	    if ((a<0)){
 	    		a=-a; c=-c; b=-b;
 	    	}
 	    if (!( ((coefs[1][0][0]==0) && (coefs[0][0][1]==0) && (coefs[0][0][2]==0) && (coefs[0][0][0]>0)) || 
 	    	((coefs[0][0][0]==0)&& (coefs[1][0][1]==0) &&(coefs[1][0][2]==0) && (coefs[1][0][0]>0)))){
 	    	tmpEquation=new Equality(MathsUtils.multiplyVarToConst(a,y),
 	    					new Addition(MathsUtils.multiplyVarToConst(b,x),new IntegerNumber(c)));
 	    	solutions[7]+=", so \\ensuremath{"+tmpEquation.toString()+"}";
 	    }	
 	    			
 	    solutions[7]+=". Hence this is a straight line, with negative gradient and positive \\ensuremath{"+y.toString()+
 	    	"}-intercept. Hence the matching graph is Graph "+(char)((int)FIRST_CHAR+7); 	    
 	    		
 //GRAPH  9: y=mx, m - negative
 	    signs[0]=RandomChoice.randSign();
 	    signs[1]=signs[0];
 	    signs[2]=0;	    
 	   	coefs=getCoefs(signs);
 	   	simplEqn=new LinearEqn(coefs[0], coefs[1], vars);
 	    questions[8]="\\ensuremath{"+simplEqn.toString()+"}";
 	    a=coefs[0][0][0]-coefs[1][0][0];
 	    b=coefs[1][0][1]-coefs[0][0][1];
 	    solutions[8]=questions[8];
 	    
 	    if ((a<0)){
 	    		a=-a; b=-b;
 	    	}
 	    if (!((coefs[1][0][2]==0) && (((coefs[1][0][0]==0) && (coefs[0][0][1]==0) && (coefs[0][0][0]>0)) || 
 	    	((coefs[0][0][0]==0)&& (coefs[1][0][1]==0) &&(coefs[1][0][0]>0))))){
 	    	tmpEquation=new Equality(MathsUtils.multiplyVarToConst(a,y),
 	    					MathsUtils.multiplyVarToConst(b,x));
 	    	solutions[8]+=", so \\ensuremath{"+tmpEquation.toString()+"}";
 	    }	
 	    			
 	    solutions[8]+=". Hence this is a straight line, with negative gradient and passing "+
 	    	"through the origin. Hence the matching graph is Graph "+(char)((int)FIRST_CHAR+8); 		    

 //GRAPH  10: y=mx+n, m - negative, n - negative
 	    signs[0]=RandomChoice.randSign();
 	    signs[1]=signs[0];
 	    signs[2]=signs[0];	    
 	   	coefs=getCoefs(signs);
 	   	simplEqn=new LinearEqn(coefs[0], coefs[1], vars);
 	    questions[9]="\\ensuremath{"+simplEqn.toString()+"}";
 	    a=coefs[0][0][0]-coefs[1][0][0];
 	    b=coefs[1][0][1]-coefs[0][0][1];
 	    c=coefs[1][0][2]-coefs[0][0][2];
 	    solutions[9]=questions[9];
 	    
 	    if ((a<0)){
 	    		a=-a; c=-c; b=-b;
 	    	}
 	    if (!( ((coefs[1][0][0]==0) && (coefs[0][0][1]==0) && (coefs[0][0][2]==0) && (coefs[0][0][0]>0)) || 
 	    	((coefs[0][0][0]==0)&& (coefs[1][0][1]==0) &&(coefs[1][0][2]==0) && (coefs[1][0][0]>0)))){
 	    	tmpEquation=new Equality(MathsUtils.multiplyVarToConst(a,y),
 	    					new Addition(MathsUtils.multiplyVarToConst(b,x),new IntegerNumber(c)));
 	    	solutions[9]+=", so \\ensuremath{"+tmpEquation.toString()+"}";
 	    }	
 	    			
 	    solutions[9]+=". Hence this is a straight line, with negative gradient and negative \\ensuremath{"+y.toString()+
 	    	"}-intercept. Hence the matching graph is Graph "+(char)((int)FIRST_CHAR+9); 	 	     		 
 
 //GRAPH 11: y=x^{ne},  n - positive
 	    tmpEquation=new Equality(y, new Power(new Variable("e"), 
 	    	MathsUtils.multiplyVarToConst(RandomChoice.randInt(1,MAX_NUMBER/2),x)));
 	    questions[10]="\\ensuremath{"+tmpEquation.toString()+"}";
 	    solutions[10]=questions[10]+
 	    ", which is a graph of exponential growth. Hence the matching graph is Graph "+(char)((int)FIRST_CHAR+10); 	 	     		 
 
 //GRAPH 12: y=x^{ne},  n - negative
 	    tmpEquation=new Equality(y, new Power(new Variable("e"), 
 	    	MathsUtils.multiplyVarToConst(RandomChoice.randInt(-MAX_NUMBER/2,-1),x)));
 	    questions[11]="\\ensuremath{"+tmpEquation.toString()+"}";
 	    solutions[11]=questions[11]+
 	    ", which is a graph of exponential decay. Hence the matching graph is Graph "+(char)((int)FIRST_CHAR+11); 	 	 
 
 //GRAPH 13: y=k|nx|, k - negative
        a=RandomChoice.randInt(-MAX_NUMBER, -1);
        b=RandomChoice.randInt(1, MAX_NUMBER)*RandomChoice.randSign();
        tmpEquation=new Equality(y, MathsUtils.coefTimesNumber(a, new AbsOp(MathsUtils.multiplyVarToConst(b,x) )));
        questions[12]="\\ensuremath{"+tmpEquation.toString()+"}";
        solutions[12]=questions[12];
        if (b<0)
        	solutions[12]+=", so \\ensuremath{"+(new Equality(y, MathsUtils.coefTimesNumber(a, 
        		new AbsOp(MathsUtils.multiplyVarToConst(-b,x) ))) ).toString()+"}";
        solutions[12]+=", which is a graph of negative absolute value. Hence the matching graph is Graph "+(char)((int)FIRST_CHAR+12); 	  		 
 
 //GRAPH 14: y=k|nx|, k - positive
        a=RandomChoice.randInt(1, MAX_NUMBER);
        b=RandomChoice.randInt(1, MAX_NUMBER)*RandomChoice.randSign();
        tmpEquation=new Equality(y, MathsUtils.coefTimesNumber(a, new AbsOp(MathsUtils.multiplyVarToConst(b,x) )));
        questions[13]="\\ensuremath{"+tmpEquation.toString()+"}";
        solutions[13]=questions[13];
        if (b<0)
        	solutions[13]+=", so \\ensuremath{"+(new Equality(y, MathsUtils.coefTimesNumber(a, 
        		new AbsOp(MathsUtils.multiplyVarToConst(-b,x) ))) ).toString()+"}";
        solutions[13]+=", which is a graph of absolute value. Hence the matching graph is Graph "+(char)((int)FIRST_CHAR+13); 	
 
 //GRAPH  15: y=mx^2+n, m - positive, n - positive
        //vars[0]=y;
        vars[1]=new Variable((new Power(x,new IntegerNumber(2))).toString());
 	    signs[0]=RandomChoice.randSign();
 	    signs[1]=-signs[0];
 	    signs[2]=-signs[0];	    
 	   	coefs=getCoefs(signs);
 	   	simplEqn=new LinearEqn(coefs[0], coefs[1], vars);
 	    questions[14]="\\ensuremath{"+simplEqn.toString()+"}";
 	    a=coefs[0][0][0]-coefs[1][0][0];
 	    b=coefs[1][0][1]-coefs[0][0][1];
 	    c=coefs[1][0][2]-coefs[0][0][2];
 	    solutions[14]=questions[14];
 	    
 	    if ((a<0)){
 	    		a=-a; c=-c; b=-b;
 	    	}
 	    if (!( ((coefs[1][0][0]==0) && (coefs[0][0][1]==0) && (coefs[0][0][2]==0) && (coefs[0][0][0]>0)) || 
 	    	((coefs[0][0][0]==0)&& (coefs[1][0][1]==0) &&(coefs[1][0][2]==0) && (coefs[1][0][0]>0)))){
 	    	tmpEquation=new Equality(MathsUtils.multiplyVarToConst(a,y),
 	    					new Addition(MathsUtils.multiplyVarToConst(b,vars[1]),new IntegerNumber(c)));
 	    	solutions[14]+=", so \\ensuremath{"+tmpEquation.toString()+"}";
 	    }				
 	    solutions[14]+=". This equation includes an \\ensuremath{"+vars[1].toString()+"} term with a positive coefficient, so the graph"+
 	    	" is a parabola which turns upwards. Also, the \\ensuremath{"+y.toString()+
 	    	"}-intercept is positive. Hence the matching graph is Graph "+(char)((int)FIRST_CHAR+14); 	    
 	    		
 //GRAPH  16: y=mx^2, m - positive
        //vars[0]=y;
        //vars[1]=new Variable((new Power(x,new IntegerNumber(2))).toString());
 	    signs[0]=RandomChoice.randSign();
 	    signs[1]=-signs[0];
 	    signs[2]=0;	    
 	   	coefs=getCoefs(signs);
 	   	simplEqn=new LinearEqn(coefs[0], coefs[1], vars);
 	    questions[15]="\\ensuremath{"+simplEqn.toString()+"}";
 	    a=coefs[0][0][0]-coefs[1][0][0];
 	    b=coefs[1][0][1]-coefs[0][0][1];
 	    solutions[15]=questions[15]; 
 	    if ((a<0)){
 	    		a=-a; b=-b;
 	    	}
 	    if (!( ((coefs[1][0][0]==0) && (coefs[0][0][1]==0) && (coefs[0][0][2]==0) && (coefs[0][0][0]>0)) || 
 	    	((coefs[0][0][0]==0)&& (coefs[1][0][1]==0) &&(coefs[1][0][2]==0) && (coefs[1][0][0]>0)))){
 	    	tmpEquation=new Equality(MathsUtils.multiplyVarToConst(a,y),
 	    				     MathsUtils.multiplyVarToConst(b,vars[1]));
 	    	solutions[15]+=", so \\ensuremath{"+tmpEquation.toString()+"}";
 	    }				
 	    solutions[15]+=". This equation includes an \\ensuremath{"+vars[1].toString()+"} term with a positive coefficient, so the graph"+
 	    	" is a parabola which turns upwards. Also, the \\ensuremath{"+y.toString()+
 	    	"}-intercept is 0. Hence the matching graph is Graph "+(char)((int)FIRST_CHAR+15); 	   
 	    		
//GRAPH  17: y=mx^2+n, m - positive, n-negative
     //vars[0]=y;
      //vars[1]=new Variable((new Power(x,new IntegerNumber(2))).toString());
 	    signs[0]=RandomChoice.randSign();
 	    signs[1]=-signs[0];
 	    signs[2]=signs[0];	    
 	   	coefs=getCoefs(signs);
 	   	simplEqn=new LinearEqn(coefs[0], coefs[1], vars);
 	    questions[16]="\\ensuremath{"+simplEqn.toString()+"}";
 	    a=coefs[0][0][0]-coefs[1][0][0];
 	    b=coefs[1][0][1]-coefs[0][0][1];
 	    c=coefs[1][0][2]-coefs[0][0][2];
 	    solutions[16]=questions[16];
 	    
 	    if ((a<0)){
 	    		a=-a; c=-c; b=-b;
 	    	}
 	    if (!( ((coefs[1][0][0]==0) && (coefs[0][0][1]==0) && (coefs[0][0][2]==0) && (coefs[0][0][0]>0)) || 
 	    	((coefs[0][0][0]==0)&& (coefs[1][0][1]==0) &&(coefs[1][0][2]==0) && (coefs[1][0][0]>0)))){
 	    	tmpEquation=new Equality(MathsUtils.multiplyVarToConst(a,y),
 	    					new Addition(MathsUtils.multiplyVarToConst(b,vars[1]),new IntegerNumber(c)));
 	    	solutions[16]+=", so \\ensuremath{"+tmpEquation.toString()+"}";
 	    }				
 	    solutions[16]+=". This equation includes an \\ensuremath{"+vars[1].toString()+"} term with a positive coefficient, so the graph"+
 	    	" is a parabola which turns upwards. Also, the \\ensuremath{"+y.toString()+
 	    	"}-intercept is negative. Hence the matching graph is Graph "+(char)((int)FIRST_CHAR+16); 	    
 	    		  	
//GRAPH  18: y=mx^2+n, m - negative, n-positive
     //vars[0]=y;
      //vars[1]=new Variable((new Power(x,new IntegerNumber(2))).toString());
 	    signs[0]=RandomChoice.randSign();
 	    signs[1]=signs[0];
 	    signs[2]=-signs[0];	    
 	   	coefs=getCoefs(signs);
 	   	simplEqn=new LinearEqn(coefs[0], coefs[1], vars);
 	    questions[17]="\\ensuremath{"+simplEqn.toString()+"}";
 	    a=coefs[0][0][0]-coefs[1][0][0];
 	    b=coefs[1][0][1]-coefs[0][0][1];
 	    c=coefs[1][0][2]-coefs[0][0][2];
 	    solutions[17]=questions[17];
 	    
 	    if ((a<0)){
 	    		a=-a; c=-c; b=-b;
 	    	}
 	    if (!( ((coefs[1][0][0]==0) && (coefs[0][0][1]==0) && (coefs[0][0][2]==0) && (coefs[0][0][0]>0)) || 
 	    	((coefs[0][0][0]==0)&& (coefs[1][0][1]==0) &&(coefs[1][0][2]==0) && (coefs[1][0][0]>0)))){
 	    	tmpEquation=new Equality(MathsUtils.multiplyVarToConst(a,y),
 	    					new Addition(MathsUtils.multiplyVarToConst(b,vars[1]),new IntegerNumber(c)));
 	    	solutions[17]+=", so \\ensuremath{"+tmpEquation.toString()+"}";
 	    }				
 	    solutions[17]+=". This equation includes an \\ensuremath{"+vars[1].toString()+"} term with a negative coefficient, so the graph"+
 	    	" is a parabola which turns downwards. Also, the \\ensuremath{"+y.toString()+
 	    	"}-intercept is positive. Hence the matching graph is Graph "+(char)((int)FIRST_CHAR+17); 	 

//GRAPH  19: y=mx^2, m - negative
        //vars[0]=y;
        //vars[1]=new Variable((new Power(x,new IntegerNumber(2))).toString());
 	    signs[0]=RandomChoice.randSign();
 	    signs[1]=signs[0];
 	    signs[2]=0;	    
 	   	coefs=getCoefs(signs);
 	   	simplEqn=new LinearEqn(coefs[0], coefs[1], vars);
 	    questions[18]="\\ensuremath{"+simplEqn.toString()+"}";
 	    a=coefs[0][0][0]-coefs[1][0][0];
 	    b=coefs[1][0][1]-coefs[0][0][1];
 	    solutions[18]=questions[18]; 
 	    if ((a<0)){
 	    		a=-a; b=-b;
 	    	}
 	    if (!( ((coefs[1][0][0]==0) && (coefs[0][0][1]==0) && (coefs[0][0][2]==0) && (coefs[0][0][0]>0)) || 
 	    	((coefs[0][0][0]==0)&& (coefs[1][0][1]==0) &&(coefs[1][0][2]==0) && (coefs[1][0][0]>0)))){
 	    	tmpEquation=new Equality(MathsUtils.multiplyVarToConst(a,y),
 	    				     MathsUtils.multiplyVarToConst(b,vars[1]));
 	    	solutions[18]+=", so \\ensuremath{"+tmpEquation.toString()+"}";
 	    }				
 	    solutions[18]+=". This equation includes an \\ensuremath{"+vars[1].toString()+"} term with a negative coefficient, so the graph"+
 	    	" is a parabola which turns downwards. Also, the \\ensuremath{"+y.toString()+
 	    	"}-intercept is 0. Hence the matching graph is Graph "+(char)((int)FIRST_CHAR+18); 	   

//GRAPH  20: y=mx^2+n, m - negative, n-negative
     signs[0]=RandomChoice.randSign();
 	    signs[1]=signs[0];
 	    signs[2]=signs[0];	    
 	   	coefs=getCoefs(signs);
 	   	simplEqn=new LinearEqn(coefs[0], coefs[1], vars);
 	    questions[19]="\\ensuremath{"+simplEqn.toString()+"}";
 	    a=coefs[0][0][0]-coefs[1][0][0];
 	    b=coefs[1][0][1]-coefs[0][0][1];
 	    c=coefs[1][0][2]-coefs[0][0][2];
 	    solutions[19]=questions[19];
 	    
 	    if ((a<0)){
 	    		a=-a; c=-c; b=-b;
 	    	}
 	    if (!( ((coefs[1][0][0]==0) && (coefs[0][0][1]==0) && (coefs[0][0][2]==0) && (coefs[0][0][0]>0)) || 
 	    	((coefs[0][0][0]==0)&& (coefs[1][0][1]==0) &&(coefs[1][0][2]==0) && (coefs[1][0][0]>0)))){
 	    	tmpEquation=new Equality(MathsUtils.multiplyVarToConst(a,y),
 	    					new Addition(MathsUtils.multiplyVarToConst(b,vars[1]),new IntegerNumber(c)));
 	    	solutions[19]+=", so \\ensuremath{"+tmpEquation.toString()+"}";
 	    }				
 	    solutions[19]+=". This equation includes an \\ensuremath{"+vars[1].toString()+"} term with a negative coefficient, so the graph"+
 	    	" is a parabola which turns downwards. Also, the \\ensuremath{"+y.toString()+
 	    	"}-intercept is negative. Hence the matching graph is Graph "+(char)((int)FIRST_CHAR+19); 	 
   
	}//generate
	
	// returns solution, fraction num/denom or integer if num=kdenom, denom!=0!
	private MathsOp fractionSimple(int num, int denom){
		int resnum, resdenom, hcf1;
		MathsOp res;
		if  ((num%denom)==0)
			return new IntegerNumber(num/denom);
		else {
			res=new FractionOp(new IntegerNumber(Math.abs(num)),new IntegerNumber(Math.abs(denom)));
			if ((num*denom)<0)
				res=new UnaryMinus(res);
			return res;	
		}
	} //fractionSimple	
/*			
		MathsOp[] res= new MathsOp[2];
			if (! Primes.areCoprime(num, denom) ) {       
						hcf1=HCFModule.hcf(num,denom);		
	  					resnum=num/hcf1;
	  					resdenom=denom/hcf1;
						res[0]=new Equality (new FractionOp(new IntegerNumber(num),new IntegerNumber(denom)), 
							 (new FractionOp(new IntegerNumber(resnum), new IntegerNumber(resdenom) )));
						res[1]= new FractionOp(new IntegerNumber(resnum), new IntegerNumber(resdenom) ); 	 
		
			} else {
				res[0]=new FractionOp(new IntegerNumber(num),new IntegerNumber(denom)); 
				res[1]= new FractionOp(new IntegerNumber(num), new IntegerNumber(denom) ); 	
			}
		
		return res; 
	} //fractionSimple 
*/

//the following method generates coefficients for LinearEqn of the type y=mx+c, signs determines
//the signs (-1, 0, 1) for y (first variable), x(second variable), and intercept.  
 private int[][][] getCoefs(int[] signs)	{
 //	int nc=RandomChoice.randInt(1,3);
 //	int [] nonZeros=RandomChoice.randPerm(3);
 	int[][][] coefs=new int[2][3][3];
 	
 		coefs[0][1][0]=0; coefs[0][1][1]=1; coefs[0][1][2]=-1;
 	    coefs[0][2][0]=0; coefs[0][2][1]=1; coefs[0][2][2]=2;
 	    for (int i=0; i<3; i++) {
 	    	coefs[1][1][i]=coefs[0][1][i];
 	    	coefs[1][2][i]=coefs[0][2][i];
 	    }

 	for (int i=0; i<3; i++){
 		 coefs[0][0][i]=RandomChoice.randInt(-MAX_NUMBER, MAX_NUMBER)*Integer.signum(RandomChoice.randInt(0,3));
 		 if (signs[i]==0)
 		 	coefs[0][0][i]*=RandomChoice.randInt(0,1);			
 		 if (signs[i]<0){
 		  if ((coefs[0][0][i]<0)&&(RandomChoice.randInt(0,2)==0))
 		  		coefs[1][0][i]=0;
 		  else 
 		  		coefs[1][0][i]=RandomChoice.randInt(coefs[0][0][i]+1, MAX_NUMBER+1);		
 		 } else 
 		 if (signs[i]>0) {
 		   if ((coefs[0][0][i]>0)&&(RandomChoice.randInt(0,2)==0))
 		  		coefs[1][0][i]=0;
 		   else 
 		  		coefs[1][0][i]=RandomChoice.randInt(-MAX_NUMBER-1,coefs[0][0][i]-1);	
 		 } else  //signs[i]==0
 		 	coefs[1][0][i]=coefs[0][0][i];
 	}
    return coefs;
 } //getCoeffs
 
 } 