/* @(#) MatrixInverseModule.java
 *
 * This file is part of SmartAss and describes class MatrixInverseModule.
 * Find an inverse of 2 x 2 matrix.
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
* MatrixInverseModule generates the solution to the question:  
* Find an inverse of a given matrix (4 x 4).   
*
* @version 1.0 15.08.2007
*/
public class MatrixInverseModule extends MathsModule{

 char invertible; //(RandomChoice.randInt(1,5)==5)? 'n': 'y';	
 Matrix initialMatrix, workingMatrix, inverseMatrix; //find inverse of a matrix
 MathsOp workingDet; //expression for determinant
 IntegerNumber det; //determinant
 
 private final int MAX_ELEMENT=6;


/**
* Constructor MatrixInverseModule, generates the questions, solution, shortanswer
* parameters are  dimensions and elements in the format
*(num, m1, n1, matrix[1][1], matrix[1][2],...matrix[2][1],... matrix[m1][n1]) 
*/
 public MatrixInverseModule(Engine engine, String[] params) 
	{
				super(engine);
				
				try{
			    	if (params.length!=4)	
			    		throw new IllegalArgumentException();
			    	int[][] intArray=new int[2][2];
			    	int k=0;
			    	for(int i=0; i<2;i++)
			    		for (int j=0; j<2; j++)
			    		{
			    			intArray[i][j]=Integer.parseInt(params[k]);
			    			k++;
			    		}
			    	solve(intArray);				    						    		  
				}	catch (IllegalArgumentException e){
					System.out.println("IllegalArgumentException while processing parameters passed into MatrixInverseModule");
					throw e;
				}

	} //constructor

/**
* Constructor MatrixInverseModule, generates the questions, solution, shortanswer
* 
*/
 public MatrixInverseModule(Engine engine) 
	{
				super(engine);	
				int [][] intArray= new int[2][2];
				for (int i=0; i<2; i++)
 			    	for (int j=0; j<2; j++)
 			    		intArray[i][j]=RandomChoice.randInt(-MAX_ELEMENT, MAX_ELEMENT);	
				solve(intArray);
								
	} //constructor

		
	
/**
 * getSection method typesets a question and solution 
 * @return a String containing Latex code for the section
 */
 public String getSection(String name) {
        
        if(name.equals("matrix"))
        	return "\\ensuremath{"+initialMatrix+" }";
        if(name.equals("adjoint"))
        	return "\\ensuremath{"+workingMatrix.toString()+" }";
        if (name.equals("determinant"))
        	return "\\ensuremath{"+det.toString()+" }";	
 		if(name.equals("determinantexpression")) 
 		 	return "\\ensuremath{"+workingDet.toString()+"}";	
 		if(name.equals("inverse")) 
 		 return (invertible=='y')?("\\ensuremath{"+inverseMatrix.toString()+"}"):("the matrix is not invertible");	
 		if (name.equals("invertible")) 
 				return Character.toString(invertible);		
		return super.getSection(name);
	}
	
/**
 * Method generates  matrix, inverse (if invertible) and solution
 * 
 */
 

 protected void solve(int[][] initArray) {
 			   	initialMatrix=MatrixAlgebra.makeIntegerMatrix(initArray);
 			   	int [][] workArray=new int[2][2];
 			   	
 			   	int intDet=initArray[0][0]*initArray[1][1]-initArray[0][1]*initArray[1][0];
 			   	workingDet=new Subtraction(new Multiplication(new IntegerNumber(initArray[0][0]), new IntegerNumber(initArray[1][1])),
 			   		new Multiplication(new IntegerNumber(initArray[0][1]), new IntegerNumber(initArray[1][0])));
 			   	det=new IntegerNumber(intDet);
 			   	workArray[0][0]=initArray[1][1];
 			   	workArray[1][1]=initArray[0][0];
 			   	workArray[0][1]=-initArray[0][1];
 			   	workArray[1][0]=-initArray[1][0];
 			   	workingMatrix=MatrixAlgebra.makeIntegerMatrix(workArray);
 			   	if (intDet==0)
 			   		invertible= 'n';
 			   	else{
 			   		invertible= 'y';
 			   		MathsOp[][] inverseArray=new MathsOp[2][2];
 					for (int i=0; i<2; i++)
 						for (int j=0; j<2; j++)
 							inverseArray[i][j]=divide(workArray[i][j],intDet);			   		
 			   		inverseMatrix=new Matrix(inverseArray);
 			   	}		
 			   				 
         } //solve 
         
 //create an Op like -3/2        
 private MathsOp divide(int num, int denom){
 	if ((num%denom)==0)
 		return new IntegerNumber(num/denom);
    int hcf1=HCFModule.hcf(num, denom);
    int a, b;
    a=Math.abs(num/hcf1);
    b=Math.abs(denom/hcf1);
    MathsOp res=new Division(new IntegerNumber(a), new IntegerNumber(b));
    String[] tex={"/"};
    res.setLocalTex(tex);
    if ((num*denom)<0)
    	res=new UnaryMinus(res);
    return res;
  }
  
 } 