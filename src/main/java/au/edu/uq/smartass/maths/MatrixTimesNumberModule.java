/* @(#) MatrixTimesNumberModule.java
 *
 * This file is part of SmartAss and describes class MatrixTimesNumberModule.
 * Multiply matrix by a number.
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
* MatrixTimesNumberModule generates the solution to the question:  
* Multiply a given integer matrix by an integer number.   
*
* @version 1.0 15.08.2007
*/
public class MatrixTimesNumberModule extends MathsModule{

 int m1, n1, num; //m1 - number of rows, n1 - number of columns, num - multiplier	
 Matrix matrixToMultiply, productMatrix, workingMatrix; //multiply matrix by a number
 private final int MAX_ELEMENT=6;
 

/**
* Constructor MatrixTimesNumberModule, generates the questions, solution, shortanswer
* parameters are  dimensions and elements in the format
*(num, m1, n1, matrix[1][1], matrix[1][2],...matrix[2][1],... matrix[m1][n1]) 
*/
 public MatrixTimesNumberModule(Engine engine, String[] params) 
	{
				super(engine);
				
				try{
			        num=Integer.parseInt(params[0]);
			    	m1=Integer.parseInt(params[1]);
			    	n1=Integer.parseInt(params[2]);	
			    	if (params.length!=(m1*n1+3))	
			    		throw new IllegalArgumentException();
			    	int k=3;
			    	int[][] intArray1=new int[m1][n1];
			    	for(int i=0; i<m1;i++)
			    		for (int j=0; j<n1; j++)
			    		{
			    			intArray1[i][j]=Integer.parseInt(params[k]);
			    			k++;
			    		}
			    	workingMatrix=new Matrix(m1, n1);				    			
			    	matrixToMultiply=MatrixAlgebra.makeIntegerMatrix(intArray1);	  	
 			        productMatrix=MatrixAlgebra.makeIntegerMatrix(MatrixAlgebra.multiplyMatrixByNumber(num, intArray1, workingMatrix));					    						    		  
				}	catch (IllegalArgumentException e){
					System.out.println("IllegalArgumentException while processing parameters passed into MatrixTimesNumberModule");
					throw e;
				}

	} //constructor

/**
* Constructor MatrixTimesNumberModule, generates the questions, solution, shortanswer
* 
*/
 public MatrixTimesNumberModule(Engine engine) 
	{
		
				super(engine);	
				generate();
								
	} //constructor
		
	
/**
 * getSection method typesets a question and solution 
 * @return a String containing Latex code for the section
 */
 public String getSection(String name) {
        
        if(name.equals("number"))
        	return "\\ensuremath{"+num+" }";
        if(name.equals("matrix"))
        	return "\\ensuremath{"+matrixToMultiply.toString()+" }";
        if (name.equals("working"))
        	return "\\ensuremath{"+workingMatrix.toString()+" }";	
 		if(name.equals("product")) {
 		 return "\\ensuremath{"+productMatrix.toString()+"}";	
 		}			
		return super.getSection(name);
	}
	
/**
 * Method generates multiplier, matrix and product
 * 
 */
 
 protected void generate() {
 			   
 			    m1=RandomChoice.randInt(1,3);
 			    n1=(m1==1)? (RandomChoice.randInt(2,3)):(RandomChoice.randInt(1,3));
 			    workingMatrix=new Matrix(m1, n1);	
 		        num=RandomChoice.randInt(-MAX_ELEMENT, MAX_ELEMENT);
 			    int[][] intArray1=new int[m1][n1];	    
 			    for (int i=0; i<m1; i++)
 			    	for (int j=0; j<n1; j++)
 			    		intArray1[i][j]=RandomChoice.randInt(-MAX_ELEMENT, MAX_ELEMENT);	    		
 			    matrixToMultiply=MatrixAlgebra.makeIntegerMatrix(intArray1);
 			    productMatrix=MatrixAlgebra.makeIntegerMatrix(MatrixAlgebra.multiplyMatrixByNumber(num, intArray1, workingMatrix));
 			    	 
         } //generate 
 
 } 