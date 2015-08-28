/* @(#) MatrixTimesMatrixModule.java
 *
 * This file is part of SmartAss and describes class MatrixTimesMatrixModule.
 * Multiply matrix by another matrix.
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
* MatrixTimesMatrixModule generates the solution to the question:  
* Multiply matrix by another matrix.   
*
* @version 1.0 15.08.2007
*/
public class MatrixTimesMatrixModule extends MathsModule{

 char defined=(RandomChoice.randInt(1,5)==5)? 'n': 'y';
 int m1, n1, m2, n2; //m1, m2 - numbers of rows, n1, n2 - numbers of columns	
 Matrix firstMatrix, secondMatrix, productMatrix, workingMatrix;
 
 private final int MAX_ELEMENT=6;
/**
* Constructor MatrixTimesMatrixModule, generates the questions, solution, shortanswer
* two ways to pass the parameters: pass defined ('y' or 'n')
* or to pass dimensions and elements in the format
*(m1, n1, m2, n2, matrix1[1][1], matrix1[1][2],...matrix1[2][1],... matrix1[m1][n1], matrix2[1][1], ...matrix2[m2][n2]) 
*/ 
 public MatrixTimesMatrixModule(Engine engine, String[] params) 
	{
				super(engine);
				
				try{
			    if (params.length==1){
			    	defined=params[0].charAt(0);
			    	if ((defined!='y') && (defined!='n'))	
			    		throw new IllegalArgumentException(); 
			    	else generate();		
			    } else {
			    	m1=Integer.parseInt(params[0]);
			    	n1=Integer.parseInt(params[1]);
			    	m2=Integer.parseInt(params[2]);
			    	n2=Integer.parseInt(params[3]); 	
			    	if (params.length!=(m1*n1+m2*n2+4))	
			    		throw new IllegalArgumentException();
			    	int k=4;
			    	int[][] intArray1=new int[m1][n1];
 			    	int[][] intArray2=new int[m2][n2];
			    	for(int i=0; i<m1;i++)
			    		for (int j=0; j<n1; j++)
			    		{
			    			intArray1[i][j]=Integer.parseInt(params[k]);
			    			k++;
			    		}	
			    	for(int i=0; i<m2;i++)
			    		for (int j=0; j<n2; j++)
			    		{
			    			intArray2[i][j]=Integer.parseInt(params[k]);
			    			k++;
			    		}
			    	firstMatrix=MatrixAlgebra.makeIntegerMatrix(intArray1);
 			   		secondMatrix=MatrixAlgebra.makeIntegerMatrix(intArray2);	 					
			    	if (n1!=m2)	
			    		defined='n';
			    	else{
			    		defined='y';		    			
			    		workingMatrix=new Matrix(m1,n2);	
			    		productMatrix=MatrixAlgebra.makeIntegerMatrix(MatrixAlgebra.multiplyTwoIntegerMatrices(intArray1, intArray2 , workingMatrix));	
			    				
			    	}	
 			    }					    		  
				}	catch (IllegalArgumentException e){
					System.out.println("IllegalArgumentException while processing parameters passed into MatrixTimesMatrixModule");
					throw e;
				}

	} //constructor
/**
* Constructor MatrixTimesNumberModule, generates the questions, solution, shortanswer
* 
*/
 public MatrixTimesMatrixModule(Engine engine) 
	{
		
				super(engine);	
				generate();
								
	} //constructor
		
	
/**
 * getSection method typesets a question and solution 
 * @return a String containing Latex code for the section
 */
 public String getSection(String name) {
        
        if(name.equals("matrix1"))
        	return "\\ensuremath{"+firstMatrix+" }";
        if(name.equals("matrix2"))
        	return "\\ensuremath{"+secondMatrix.toString()+" }";
        if (name.equals("working"))
        	return (defined=='y')?("\\ensuremath{"+workingMatrix.toString()+"}"):("not defined");
 		if (name.equals("product")) 
 		 return (defined=='y')?("\\ensuremath{"+productMatrix.toString()+"}"):("not defined");	
 		if (name.equals("isdefined")) 
 				return Character.toString(defined);		 	
		return super.getSection(name);
	}
	
/**
 * Method generates matrices and product
 * 
 */
 
 protected void generate() {
 			   
 			    m1=RandomChoice.randInt(1,3);
 			    n1=(m1==1)? (RandomChoice.randInt(2,3)):(RandomChoice.randInt(1,3));
 			    if (defined=='y'){
 			    	m2=n1;
 			    	n2=RandomChoice.randInt(1,3);
 			    	workingMatrix=new Matrix(m1, n1);	
 			    } else {
 			    	n2=RandomChoice.randInt(1,3);
 			    	m2=(n1+RandomChoice.randInt(1,2)-1)%3+1;
 			    }
 			    
 			    int[][] intArray1=new int[m1][n1];
 			    int[][] intArray2=new int[m2][n2];
 			    
 			    for (int i=0; i<m1; i++)
 			    	for (int j=0; j<n1; j++)
 			    		intArray1[i][j]=RandomChoice.randInt(-MAX_ELEMENT, MAX_ELEMENT);
 			    firstMatrix=MatrixAlgebra.makeIntegerMatrix(intArray1);
 			    for (int i=0; i<m2; i++)
 			    	for (int j=0; j<n2; j++)
 			    		intArray2[i][j]=RandomChoice.randInt(-MAX_ELEMENT, MAX_ELEMENT);
 			    secondMatrix=MatrixAlgebra.makeIntegerMatrix(intArray2);	
 			    if (defined=='y')
 			    	productMatrix=MatrixAlgebra.makeIntegerMatrix(MatrixAlgebra.multiplyTwoIntegerMatrices(intArray1, intArray2 , workingMatrix));	
 			    	 
         } //generate 
 
 } 