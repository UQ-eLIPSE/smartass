/* @(#) MatrixExpressionModule.java
 *
 * This file is part of SmartAss and describes class MatrixExpressionModule.
 * Solve coef1A op1 coef2B op2 coef3C , where op1 and op2 - multiplication, addition or subtraction,
 * coef1,2,3 - integer coefficients  
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
* MatrixExpressionModule generates the solution to the question:  
* Solve coef1A op1 coef2B op2 coef3C , where op1 and op2 - multiplication, addition or subtraction,
* coef1,2,3 - integer coefficients.  
*
* @version 1.0 15.08.2007
*/
public class MatrixExpressionModule extends MathsModule{

 ArrayList <MathsOp> solutionSteps; 
 private final int MAX_ELEMENT=6;
 private final int MAX_SCALAR=4;
/**
* Constructor MatrixExpressionModule, generates the questions, solution, shortanswer
* 
*/
 public MatrixExpressionModule(Engine engine) 
	{
				super(engine);	
				generate();	
	} //constructor

	
/**
 * getSection method typesets a question and solution 
 * @return a String containing Latex code for the section
 */
 public String getSection(String name) {
        
        if(name.equals("question"))
        	return "\\ensuremath{"+solutionSteps.get(0)+" }";
        if(name.equals("solution")){
        	String sol="\\begin{align*} \n"; 	
        	sol+="& "+solutionSteps.get(0).toString()+" \\\\[1.5mm] \n &= "+solutionSteps.get(1).toString()+" \\\\[1.5mm] \n";	
			for (int i=2; i<solutionSteps.size()-1; i++)
					sol+=" &= "+solutionSteps.get(i)+" \\\\[1.5mm] \n";
        	sol+=" &= "+solutionSteps.get(solutionSteps.size()-1).toString()+"\\end{align*}";
        	return sol;
        }	
        if (name.equals("shortanswer"))
        	return "\\ensuremath{"+solutionSteps.get(solutionSteps.size()-1)+" }";	
		return super.getSection(name);
	}
	
/**
 * Method generates  matrices, operations and solution
 * 
 */
 
 private void generate() {
 	            int[] ops=new int[2]; //0 - addition, 1 - subtraction, 2 - multiplication
 	            int[] coefs={1,1,1};
 	            boolean presentCoef=false; //are there scalar coefficients present?
 	            for (int i=0; i<3; i++)        	
 	            	if (RandomChoice.randInt(16,20)==20){ //chance of a scalar present
 	            	coefs[i]=RandomChoice.randInt(2,MAX_SCALAR)*((RandomChoice.randInt(1,10)==1) ? 0: 1); //one in 10 chance that it's 0
 	            	presentCoef=true;	 
 	            } 		 
 	            if(coefs[0]!=1)
 	            	coefs[0]*=RandomChoice.randSign();	 
 	            for (int i=0; i<2; i++)
 	            	ops[i]=RandomChoice.randInt(0,2);
 	            	
 	            int option=ops[0]*10+ops[1];
 	
 	            solutionSteps=new ArrayList<MathsOp>(); 
 	            int m, n, n2, m2;
 	            int[][] array1, array2, array3;
 	            Matrix workingM=new Matrix("multiplication");
 	            MathsOp[][] terms;  	  	
 	            	
 	      switch (option)	{
 	         case 0: //A + B + C	              
 	                  m=RandomChoice.randInt(1,3);
 	                  n=(m==1)? RandomChoice.randInt(2,3) : RandomChoice.randInt(1,3);
 	                  array1=randomIntArray(m,n); 	
 	              	  array2=randomIntArray(m,n);
 	              	  array3=randomIntArray(m,n);	                 	
 	              	  if (presentCoef){     	  	
 	              	  	terms=updateTerms(coefs,array1, array2, array3);	              	  	
 	              	  	solutionSteps.add(new Addition(new Addition(terms[0][0], terms[0][1]), terms[0][2]));	              	  	
 	              	  	solutionSteps.add(new Addition(new Addition(terms[1][0], terms[1][1]), terms[1][2]));            	  	
 	              	  } else 
 	              	  	solutionSteps.add(new Addition ( 
 	              	  		new Addition(MatrixAlgebra.makeIntegerMatrix(array1), MatrixAlgebra.makeIntegerMatrix(array2)),
 	              	  		MatrixAlgebra.makeIntegerMatrix(array3)));
 	              	  array1=arrayAddArray(array1,array2);
 	              	  solutionSteps.add(new Addition (MatrixAlgebra.makeIntegerMatrix(array1), MatrixAlgebra.makeIntegerMatrix(array3)));
 	              	  array1=arrayAddArray(array1,array3);
 	              	  solutionSteps.add(MatrixAlgebra.makeIntegerMatrix(array1));	          
 	              break;	
 	        case 1: //A + B - C	              
 	                  m=RandomChoice.randInt(1,3);
 	                  n=(m==1)? RandomChoice.randInt(2,3) : RandomChoice.randInt(1,3);
 	                  array1=randomIntArray(m,n); 	
 	              	  array2=randomIntArray(m,n);
 	              	  array3=randomIntArray(m,n);            	
 	              	  if (presentCoef){     	  	
 	              	  	terms=updateTerms(coefs,array1, array2, array3);	              	  	
 	              	  	solutionSteps.add(new Subtraction(new Addition(terms[0][0], terms[0][1]), terms[0][2]));	              	  	
 	              	  	solutionSteps.add(new Subtraction(new Addition(terms[1][0], terms[1][1]), terms[1][2]));            	  	
 	              	  } else 
 	              	  	solutionSteps.add(new Subtraction ( 
 	              	  		new Addition(MatrixAlgebra.makeIntegerMatrix(array1), MatrixAlgebra.makeIntegerMatrix(array2)),
 	              	  		MatrixAlgebra.makeIntegerMatrix(array3)));
 	              	  array1=arrayAddArray(array1,array2);
 	              	  solutionSteps.add(new Subtraction (MatrixAlgebra.makeIntegerMatrix(array1), MatrixAlgebra.makeIntegerMatrix(array3)));
 	              	  array1=arraySubtractArray(array1,array3);
 	              	  solutionSteps.add(MatrixAlgebra.makeIntegerMatrix(array1));	          
 	              break;
 	        case 10: //A - B + C	              
 	                  m=RandomChoice.randInt(1,3);
 	                  n=(m==1)? RandomChoice.randInt(2,3) : RandomChoice.randInt(1,3);
 	                  array1=randomIntArray(m,n); 	
 	              	  array2=randomIntArray(m,n);
 	              	  array3=randomIntArray(m,n);            	
 	              	  if (presentCoef){     	  	
 	              	  	terms=updateTerms(coefs,array1, array2, array3);	              	  	
 	              	  	solutionSteps.add(new Addition(new Subtraction(terms[0][0], terms[0][1]), terms[0][2]));	              	  	
 	              	  	solutionSteps.add(new Addition(new Subtraction(terms[1][0], terms[1][1]), terms[1][2]));            	  	
 	              	  } else 
 	              	  	solutionSteps.add(new Addition ( 
 	              	  		new Subtraction(MatrixAlgebra.makeIntegerMatrix(array1), MatrixAlgebra.makeIntegerMatrix(array2)),
 	              	  		MatrixAlgebra.makeIntegerMatrix(array3)));
 	              	  array1=arraySubtractArray(array1,array2);
 	              	  solutionSteps.add(new Addition (MatrixAlgebra.makeIntegerMatrix(array1), MatrixAlgebra.makeIntegerMatrix(array3)));
 	              	  array1=arrayAddArray(array1,array3);
 	              	  solutionSteps.add(MatrixAlgebra.makeIntegerMatrix(array1));	          
 	              break;	     	
 	         case 11: //A - B - C	              
 	                  m=RandomChoice.randInt(1,3);
 	                  n=(m==1)? RandomChoice.randInt(2,3) : RandomChoice.randInt(1,3);
 	                  array1=randomIntArray(m,n); 	
 	              	  array2=randomIntArray(m,n);
 	              	  array3=randomIntArray(m,n);            	
 	              	  if (presentCoef){     	  	
 	              	  	terms=updateTerms(coefs,array1, array2, array3);	              	  	
 	              	  	solutionSteps.add(new Subtraction(new Subtraction(terms[0][0], terms[0][1]), terms[0][2]));	              	  	
 	              	  	solutionSteps.add(new Subtraction(new Subtraction(terms[1][0], terms[1][1]), terms[1][2]));            	  	
 	              	  } else 
 	              	  	solutionSteps.add(new Subtraction ( 
 	              	  		new Subtraction(MatrixAlgebra.makeIntegerMatrix(array1), MatrixAlgebra.makeIntegerMatrix(array2)),
 	              	  		MatrixAlgebra.makeIntegerMatrix(array3)));
 	              	  array1=arraySubtractArray(array1,array2);
 	              	  solutionSteps.add(new Subtraction (MatrixAlgebra.makeIntegerMatrix(array1), MatrixAlgebra.makeIntegerMatrix(array3)));
 	              	  array1=arraySubtractArray(array1,array3);
 	              	  solutionSteps.add(MatrixAlgebra.makeIntegerMatrix(array1));	          
 	              break;	   
 	         case 2: //A + B * C	              
 	                  m=RandomChoice.randInt(1,3);
 	                  n=(m==1)? RandomChoice.randInt(2,3) : RandomChoice.randInt(1,3);
 	                  array1=randomIntArray(m,n);
 	                  n2=(m==1 || n==1)? RandomChoice.randInt(2,3) : RandomChoice.randInt(1,3); 	
 	              	  array2=randomIntArray(m,n2);	
 	              	  array3=randomIntArray(n2,n);            	
 	              	  if (presentCoef){     	  	
 	              	  	terms=updateTerms(coefs,array1, array2, array3);	              	  	
 	              	  	solutionSteps.add(new Addition(terms[0][0],new Multiplication(terms[0][1], terms[0][2])));	              	  	
 	              	  	solutionSteps.add(new Addition(terms[1][0],new Multiplication(terms[1][1], terms[1][2])));            	  	
 	              	  } else 
 	              	  	solutionSteps.add(new Addition ( 
 	              	  		MatrixAlgebra.makeIntegerMatrix(array1), 
 	              	  		new Multiplication(MatrixAlgebra.makeIntegerMatrix(array2),	MatrixAlgebra.makeIntegerMatrix(array3))));
 	              	  array2=MatrixAlgebra.multiplyTwoIntegerMatrices(array2, array3, workingM);
 	              	  solutionSteps.add(new Addition (MatrixAlgebra.makeIntegerMatrix(array1), MatrixAlgebra.makeIntegerMatrix(array2)));
 	              	  array1=arrayAddArray(array1,array2);
 	              	  solutionSteps.add(MatrixAlgebra.makeIntegerMatrix(array1));	          
 	              break;	
 	          case 20: //A * B + C	              
 	                  m=RandomChoice.randInt(1,3);
 	                  n=(m==1)? RandomChoice.randInt(2,3) : RandomChoice.randInt(1,3);
 	                  array1=randomIntArray(m,n);
 	                  n2=(m==1 || n==1)? RandomChoice.randInt(2,3) : RandomChoice.randInt(1,3);  	
 	              	  array2=randomIntArray(n,n2);
 	              	  array3=randomIntArray(m,n2);            	
 	              	  if (presentCoef){     	  	
 	              	  	terms=updateTerms(coefs,array1, array2, array3);	              	  	
 	              	  	solutionSteps.add(new Addition( new Multiplication(terms[0][0],terms[0][1]), terms[0][2]));	              	  	
 	              	  	solutionSteps.add(new Addition( new Multiplication(terms[1][0],terms[1][1]), terms[1][2]));            	  	
 	              	  } else 
 	              	  	solutionSteps.add(new Addition 
 	              	  	(new Multiplication(MatrixAlgebra.makeIntegerMatrix(array1), MatrixAlgebra.makeIntegerMatrix(array2)),
 	              	  		MatrixAlgebra.makeIntegerMatrix(array3)));
 	              	  array2=MatrixAlgebra.multiplyTwoIntegerMatrices(array1, array2, workingM);
 	              	  solutionSteps.add(new Addition (MatrixAlgebra.makeIntegerMatrix(array2), MatrixAlgebra.makeIntegerMatrix(array3)));
 	              	  array1=arrayAddArray(array2,array3);
 	              	  solutionSteps.add(MatrixAlgebra.makeIntegerMatrix(array1));	          
 	              break;
 	          case 12: //A - B * C	              
 	                  m=RandomChoice.randInt(1,3);
 	                  n=(m==1)? RandomChoice.randInt(2,3) : RandomChoice.randInt(1,3);
 	                  array1=randomIntArray(m,n);
 	                  n2=(m==1 || n==1)? RandomChoice.randInt(2,3) : RandomChoice.randInt(1,3); 	
 	              	  array2=randomIntArray(m,n2);
 	              	  array3=randomIntArray(n2,n);            	
 	              	  if (presentCoef){     	  	
 	              	  	terms=updateTerms(coefs,array1, array2, array3);	              	  	
 	              	  	solutionSteps.add(new Subtraction(terms[0][0],new Multiplication(terms[0][1], terms[0][2])));	              	  	
 	              	  	solutionSteps.add(new Subtraction(terms[1][0],new Multiplication(terms[1][1], terms[1][2])));            	  	
 	              	  } else 
 	              	  	solutionSteps.add(new Subtraction ( 
 	              	  		MatrixAlgebra.makeIntegerMatrix(array1), 
 	              	  		new Multiplication(MatrixAlgebra.makeIntegerMatrix(array2),	MatrixAlgebra.makeIntegerMatrix(array3))));
 	              	  array2=MatrixAlgebra.multiplyTwoIntegerMatrices(array2, array3, workingM);
 	              	  solutionSteps.add(new Subtraction (MatrixAlgebra.makeIntegerMatrix(array1), MatrixAlgebra.makeIntegerMatrix(array2)));
 	              	  array1=arraySubtractArray(array1,array2);
 	              	  solutionSteps.add(MatrixAlgebra.makeIntegerMatrix(array1));	          
 	              break;
 	          case 21: //A * B - C	              
 	                  m=RandomChoice.randInt(1,3);
 	                  n=(m==1)? RandomChoice.randInt(2,3) : RandomChoice.randInt(1,3);
 	                  array1=randomIntArray(m,n);
 	                  n2=(m==1 || n==1)? RandomChoice.randInt(2,3) : RandomChoice.randInt(1,3); 	
 	              	  array2=randomIntArray(n,n2);
 	              	  array3=randomIntArray(m,n2);            	
 	              	  if (presentCoef){     	  	
 	              	  	terms=updateTerms(coefs,array1, array2, array3);	              	  	
 	              	  	solutionSteps.add(new Subtraction( new Multiplication(terms[0][0],terms[0][1]), terms[0][2]));	              	  	
 	              	  	solutionSteps.add(new Subtraction( new Multiplication(terms[1][0],terms[1][1]), terms[1][2]));            	  	
 	              	  } else 
 	              	  	solutionSteps.add(new Subtraction 
 	              	  	(new Multiplication(MatrixAlgebra.makeIntegerMatrix(array1), MatrixAlgebra.makeIntegerMatrix(array2)),
 	              	  		MatrixAlgebra.makeIntegerMatrix(array3)));
 	              	  array2=MatrixAlgebra.multiplyTwoIntegerMatrices(array1, array2, workingM);
 	              	  solutionSteps.add(new Subtraction (MatrixAlgebra.makeIntegerMatrix(array2), MatrixAlgebra.makeIntegerMatrix(array3)));
 	              	  array1=arraySubtractArray(array2,array3);
 	              	  solutionSteps.add(MatrixAlgebra.makeIntegerMatrix(array1));	          
 	              break;    
 	          case 22: //A * B * C	   
 	          
 	     			  m=RandomChoice.randInt(1,3);
 	                  n=(m==1)? RandomChoice.randInt(2,3) : RandomChoice.randInt(1,3);
 	                  array1=randomIntArray(m,n);
 	                  n2=(n==1)? RandomChoice.randInt(2,3) : RandomChoice.randInt(1,3);	
 	              	  array2=randomIntArray(n,n2);
 	              	  m2=(n2==1)? RandomChoice.randInt(2,3) : RandomChoice.randInt(1,3);
 	              	  array3=randomIntArray(n2, m2);              	
 	              	  if (presentCoef){     	  	
 	              	  	terms=updateTerms(coefs,array1, array2, array3);	              	  	
 	              	  	solutionSteps.add(new Multiplication( new Multiplication(terms[0][0],terms[0][1]), terms[0][2]));	              	  	
 	              	  	solutionSteps.add(new Multiplication( new Multiplication(terms[1][0],terms[1][1]), terms[1][2]));            	  	
 	              	  } else 
 	              	  	solutionSteps.add(new Multiplication 
 	              	  	(new Multiplication(MatrixAlgebra.makeIntegerMatrix(array1), MatrixAlgebra.makeIntegerMatrix(array2)),
 	              	  		MatrixAlgebra.makeIntegerMatrix(array3)));
 	              	  array2=MatrixAlgebra.multiplyTwoIntegerMatrices(array1, array2, workingM);
 	              	  solutionSteps.add(new Multiplication (MatrixAlgebra.makeIntegerMatrix(array2), MatrixAlgebra.makeIntegerMatrix(array3)));
 	              	  array1=MatrixAlgebra.multiplyTwoIntegerMatrices(array2,array3, workingM);
 	              	  solutionSteps.add(MatrixAlgebra.makeIntegerMatrix(array1));	          
 	              break;    	   	         		   	     	     	   	     	     	     	   	  	     	
 	            }
 	            		
 	       
 			   				 
         } //generate 
         
 //generate an intArray        
 private int[][] randomIntArray(int m, int n){
 		int [][] res=new int[m][n];
 		for (int i=0; i<m; i++)
 			for (int j=0; j<n; j++)
 				res[i][j]=RandomChoice.randInt(-MAX_ELEMENT, MAX_ELEMENT);
    return res;
  }
  
  //returns MathsOp - initial terms in the first row (coef1A,coef2B,coef3C)
  //and updated matrices in the second row
  private MathsOp[][] updateTerms(int[] cc, int[][] arr1 , int[][] arr2, int[][] arr3){
  	MathsOp[][] res=new MathsOp[2][3];
  	Matrix tempOp;
  	if (cc[0]==1){
  		tempOp=MatrixAlgebra.makeIntegerMatrix(arr1);
  		res[0][0]=tempOp;
        res[1][0]=tempOp;
  	}	
  	else {
  		res[0][0]=new UnprintableMultiplication(new IntegerNumber(cc[0]), MatrixAlgebra.makeIntegerMatrix(arr1));
  		for (int i=0; i<arr1.length; i++)
  			for (int j=0; j<arr1[0].length; j++)
  				arr1[i][j]*=cc[0];
  		res[1][0]=MatrixAlgebra.makeIntegerMatrix(arr1);		
  	}
  	if (cc[1]==1){
  		tempOp=MatrixAlgebra.makeIntegerMatrix(arr2);
  		res[0][1]=tempOp;
  		res[1][1]=tempOp;
  	}	
  	else {
  		res[0][1]=new UnprintableMultiplication(new IntegerNumber(cc[1]), MatrixAlgebra.makeIntegerMatrix(arr2));
  		for (int i=0; i<arr2.length; i++)
  			for (int j=0; j<arr2[0].length; j++)
  				arr2[i][j]*=cc[1];
  		res[1][1]=MatrixAlgebra.makeIntegerMatrix(arr2);		
  	}	
 	if (cc[2]==1){
 		tempOp=MatrixAlgebra.makeIntegerMatrix(arr3);
  		res[0][2]=tempOp;
  		res[1][2]=tempOp;
  	}	
  	else {
  		res[0][2]=new UnprintableMultiplication(new IntegerNumber(cc[2]), MatrixAlgebra.makeIntegerMatrix(arr3));
  		for (int i=0; i<arr3.length; i++)
  			for (int j=0; j<arr3[0].length; j++)
  				arr3[i][j]*=cc[2];
  		res[1][2]=MatrixAlgebra.makeIntegerMatrix(arr3);		
  	}	
  	return res;	
  }
  	
 //add two arrays - add matrices  	
  private int[][] arrayAddArray(int[][] first, int[][] second){
  	int [][]res=new int[first.length][first[0].length];
  	for (int i=0; i<first.length; i++)
  		for (int j=0; j<first[0].length; j++)
  		  res[i][j]=first[i][j]+second[i][j];
  	return res;	  
  }	
 //array subtract array (matrix subtract matrix) 		
  private int[][] arraySubtractArray(int[][] first, int[][] second){
  	int [][]res=new int[first.length][first[0].length];
  	for (int i=0; i<first.length; i++)
  		for (int j=0; j<first[0].length; j++)
  		  res[i][j]=first[i][j]-second[i][j];
  	return res;	  
  }		
 } 