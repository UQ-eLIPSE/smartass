/* @(#)Matrix3x3DetModule.java
 *
 * This file is part of SmartAss and describes class Matrix3x3DetModule for 
 * question on 3 by 3 matrix determinants. Given a 3 by 3 matrix calculates its determinant.
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

import au.edu.uq.smartass.engine.*;
import au.edu.uq.smartass.auxiliary.*;
import au.edu.uq.smartass.maths.*;
import java.util.*;
import java.math.*;

/**
* Class Matrix3x3DetModule describes
* questions on determinants of 3 by 3 matrices.
*
* @version 1.0 31.3.2008
*/
public class Matrix3x3DetModule extends MathsModule
{
	private int[][] matrix;
	
	private Vector<String> solution = new Vector<String>();
	
	private int det;
	
	private String matrixDet;
	
	private int nZeroes;
	private int index;
	private int isRow;
	
	boolean display4;
	boolean display3;

	/**
	* Constructor Matrix3x3DetModule initialises the question
	* with parameters passing.
	* @params  Engine
	* 9 parameters: 
	* @params params[0] - the enrty of the matrix in (1,1)
	*         params[1] - the enrty of the matrix in (1,2)
	*         params[2] - the enrty of the matrix in (1,3)
	*         params[3] - the enrty of the matrix in (2,1)
	*         params[4] - the enrty of the matrix in (2,2)
	*         params[5] - the enrty of the matrix in (2,3)
	*         params[6] - the enrty of the matrix in (3,1)
	*         params[7] - the enrty of the matrix in (3,2)
	*         params[8] - the enrty of the matrix in (3,3)
	*/	
	public Matrix3x3DetModule(Engine engine, String[] params) 
	{
		super(engine);
		
		try
		{
		    if (params.length==9)
		    {
		    	matrix = new int[3][3];
		    	
		    	matrix[0][0] = Integer.parseInt( params[0] );
		    	matrix[0][1] = Integer.parseInt( params[1] );
		    	matrix[0][2] = Integer.parseInt( params[2] );
		    	matrix[1][0] = Integer.parseInt( params[3] );
		    	matrix[1][1] = Integer.parseInt( params[4] );
		    	matrix[1][2] = Integer.parseInt( params[5] );
		    	matrix[2][0] = Integer.parseInt( params[6] );
		    	matrix[2][1] = Integer.parseInt( params[7] );
		    	matrix[2][2] = Integer.parseInt( params[8] );
		    	
		    	select();
		    } 
		    else	 
		    	throw new IllegalArgumentException();  	    						    		  
		}	
		catch (IllegalArgumentException e)
		{
			System.out.println("IllegalArgumentException while processing parameters passed into MatrixInverseModule");
			throw e;
		}
		
	} //constructor

	/**
	* Constructor Matrix3x3DetModule initialises the question by generating a random matrix
	* @params  Engine
	*/	
	public Matrix3x3DetModule(Engine engine) 
	{
		super(engine);
		
		int i;
		int j;
		
		int smallRow;
		int indepRow;
		int depRow;
		int factor;
		
		int max;
		
		matrix = new int[3][3];
		
		// Randomly generate the 3x3 matrix
		if( RandomChoice.randInt(0,9) < 2 )
		{
			smallRow = RandomChoice.randInt(0,2);
			
			for(j=0;j<3;j++)
				matrix[smallRow][j] = RandomChoice.randInt(-4,4);
				
			max = Math.abs(matrix[smallRow][0]);
			
			for(j=1;j<3;j++)
				if( max < Math.abs(matrix[smallRow][j]) )
					max = Math.abs(matrix[smallRow][j]);
			
			indepRow = RandomChoice.randInt(0,1);
			
			if(indepRow >= smallRow) indepRow++;
			
			for(j=0;j<3;j++)
				matrix[indepRow][j] = RandomChoice.randInt(-9,9);	
			
			depRow = 0;
			
			if( depRow == smallRow ) depRow++;
			if( depRow == indepRow ) depRow++;
			
			factor = RandomChoice.randInt( (int)(-9.0 / (double)max), (int)(9.0 / (double)max) );
			
			for(j=0;j<3;j++)
				matrix[depRow][j] = factor * matrix[smallRow][j];
		}
		else
		{
			for(i=0;i<3;i++)
				for(j=0;j<3;j++)
					matrix[i][j] = RandomChoice.randInt(-9,9);			
		}
		
		// 
		select();

		
	} //constructor

    /**
     * getSection method typesets a question and solution 
     * @return a String containing Latex code for the section
     */
	public String getSection(String name) 
	{     
		if(name.equals("solution1")) return solution.get(0);
		else if(name.equals("solution2")) return solution.get(1);
		else if(name.equals("solution3")) return solution.get(2);
		else if(name.equals("solution4")) return solution.get(3);
		else if(name.equals("det")) return Integer.toString(det);
		else if(name.equals("matrix")) return (MatrixAlgebra.makeIntegerMatrix(matrix)).toString();
		else if(name.equals("matrixdet")) return matrixDet;
		
		else if(name.equals("nzeroes")) 
		{
			switch(nZeroes)
			{
			case 0: return "0";
			case 1: return "one";
			case 2: return "two";
			case 3: return "three";
			}
		}		
		
		else if(name.equals("index"))
		{
			switch(index)
			{
			case 0: return "first";
			case 1: return "second";
			case 2: return "third";
			}		
		}		
		
		else if(name.equals("isrow")) return Integer.toString(isRow);
		else if(name.equals("display3")) return (display3)?"0":"1";
		else if(name.equals("display4")) return (display4)?"0":"1";
	
		return super.getSection(name);
	}

    /**
     * select method looks at the matrix and selects which row/column to expand solution about 
     * @return a String containing Latex code for the section
     */
	private void select()
	{
		int i;
		int j;
		
		int[] rowZeroes = {0,0,0};
		int[] colZeroes = {0,0,0};
		
		int maxRowZeroes;
		int maxColZeroes;
		
		int indRowZeroes;
		int indColZeroes;
		
		boolean order;
		
		int[][][] sub = new int[3][2][2];
		
		int[] factors = new int[3];
		
		// Now count the number of zeros in each row and column
		for(i=0;i<3;i++)
			for(j=0;j<3;j++)
				if(matrix[i][j]==0)
				{
					rowZeroes[i]++;
					colZeroes[j]++;
				}
		
		// Get max number of zeroes in the rows and columns
		maxRowZeroes = rowZeroes[0];
		indRowZeroes = 0;
		
		for(i=1;i<3;i++)
			if( rowZeroes[i] > maxRowZeroes )
			{
				maxRowZeroes = rowZeroes[i];
				indRowZeroes = i;
			}
		
		maxColZeroes = colZeroes[0];
		indColZeroes = 0;
		
		for(i=1;i<3;i++)
			if( colZeroes[i] > maxColZeroes )
			{
				maxColZeroes = colZeroes[i];
				indColZeroes = i;
			}
		
		// Now generate the solution
		if( maxRowZeroes >= maxColZeroes )
		{
			nZeroes = maxRowZeroes;
			isRow = 1;
			index = indRowZeroes;
			
			order = (indRowZeroes!=1);
			
			// Generate the sub matrices and factors
			for(i=0;i<3;i++)
			{
				factors[i] = matrix[indRowZeroes][i];
				sub[i] = getSubMatrix(indRowZeroes,i);			
			}
		}
		else
		{
			nZeroes = maxColZeroes;
			isRow = 0;
			index = indColZeroes;
			
			order = (indColZeroes!=1);
			
			// Generate the sub matrices and factors
			for(i=0;i<3;i++)
			{
				factors[i] = matrix[i][indColZeroes];
				sub[i] = getSubMatrix(i,indColZeroes);			
			}
		}
		
		generate( factors, sub[0], sub[1], sub[2], order );
	}
	
    /**
     * getSubMatrix method returns a 2x2 sub-matrix from your original matrix
     * @param row - which row to ignore
     * @param col - which column to ignore 
     * @return the sub matrix
     */	
	private int[][] getSubMatrix(int row, int col)
	{
		int i;
		int j;
		
		int cRow = 0;
		int cCol = 0;
		
		int[][] sub = new int[2][2];
		
		for(i=0;i<3;i++)
		{
			if(i==row)
				continue;
			
			cCol = 0;
			
			for(j=0;j<3;j++)
			{
				if(j==col)
					continue;
				
				sub[cRow][cCol] = matrix[i][j];
				
				cCol++;
			}
			
			cRow++;
		}
		
		return sub;
	}

    /**
     * generate method generate the LaTeX
     * @param factors - are the factors we multiply each of the sub-matrices by
     * @param sub1 - the first sub-matrix
     * @param sub2 - the second sub-matrix
     * @param sub3 - the third sub-matrix
     * @param order - the terms which are turned negative in the expansion of the determinant
     */	
	private void generate(int[] factors, int[][] sub1, int[][] sub2, int[][] sub3, boolean order)
	{
		int i;
		
		MathsOp soln;
		
		MathsOp[] subs = new MathsOp[3];
		
		int nNotZero;

		// Generate solution...		
		// Step 1
		String[] tex = {"\\left|","\\begin{array}","{r","r","r}\n"," & ", " \\\\ \n","\\end{array}", "\\right|" };	
		
		subs[0] = MatrixAlgebra.makeIntegerMatrix(sub1);
		subs[1] = MatrixAlgebra.makeIntegerMatrix(sub2);
		subs[2] = MatrixAlgebra.makeIntegerMatrix(sub3);
		
		for(i=0;i<3;i++)
			subs[i].setLocalTex(tex);
		
		if(order)
		{
			soln = new Multiplication(new IntegerNumber(factors[0]), subs[0] );
			soln = new Subtraction( soln, new Multiplication( new IntegerNumber(factors[1]), subs[1] ) );
			soln = new Addition( soln, new Multiplication( new IntegerNumber(factors[2]), subs[2] ) );
		}
		else
		{
			soln = MathsUtils.multiplyVarToConst( -1, new Multiplication(new IntegerNumber(factors[0]), subs[0] ) );
			soln = new Addition( soln, new Multiplication( new IntegerNumber(factors[1]), subs[1] ) );
			soln = new Subtraction( soln, new Multiplication( new IntegerNumber(factors[2]), subs[2] ) );
		}
		
		solution.add(soln.toString());
		
		// Step 2
		if(order)
		{
			soln = MathsUtils.multiplyVarToConst( factors[0], DetExpr(sub1) );
			soln = new Subtraction( soln, MathsUtils.multiplyVarToConst(factors[1], DetExpr(sub2) ) );
			soln = new Addition( soln, MathsUtils.multiplyVarToConst(factors[2], DetExpr(sub3) ) );
		}
		else
		{
			soln = MathsUtils.multiplyVarToConst( -1, MathsUtils.multiplyVarToConst( factors[0], DetExpr(sub1) ) );
			soln = new Addition( soln, MathsUtils.multiplyVarToConst(factors[1], DetExpr(sub2) ) );
			soln = new Subtraction( soln, MathsUtils.multiplyVarToConst(factors[2], DetExpr(sub3) ) );			
		}
		
		solution.add(soln.toString());
		
		// Step 3
		if(order)
		{
			soln = MathsUtils.multiplyVarToConst(factors[0], new Brackets(new IntegerNumber( getDet(sub1) )) );
			soln = MathsUtils.addTwoTermsNoZeros( soln, MathsUtils.multiplyVarToConst(-factors[1],new Brackets(new IntegerNumber( getDet(sub2)) ) ) );
			soln = MathsUtils.addTwoTermsNoZeros( soln, MathsUtils.multiplyVarToConst(factors[2], new Brackets(new IntegerNumber( getDet(sub3) ))) );
		}
		else
		{
			soln = MathsUtils.multiplyVarToConst( -1, MathsUtils.multiplyVarToConst(factors[0], new Brackets(new IntegerNumber( getDet(sub1) )) ) );
			soln = MathsUtils.addTwoTermsNoZeros( soln, MathsUtils.multiplyVarToConst(factors[1],new Brackets(new IntegerNumber( getDet(sub2) )) ) );
			soln = MathsUtils.addTwoTermsNoZeros( soln, MathsUtils.multiplyVarToConst(-factors[2], new Brackets(new IntegerNumber( getDet(sub3)) )) );			
		}
		
		display3 = !( (factors[0]==0) && (factors[1]==0) && (factors[2]==0) );
		
		solution.add(soln.toString());
		
		// Step 4
		if(order)
		{
			soln = new IntegerNumber( factors[0] * getDet(sub1) );
			soln = MathsUtils.addTwoTermsNoZeros( soln, new IntegerNumber( -factors[1] * getDet(sub2) ) );
			soln = MathsUtils.addTwoTermsNoZeros( soln, new IntegerNumber( factors[2] * getDet(sub3) ) );
		}
		else
		{
			soln = new IntegerNumber( -factors[0] * getDet(sub1) );
			soln = MathsUtils.addTwoTermsNoZeros( soln, new IntegerNumber( factors[1] * getDet(sub2) ) );
			soln = MathsUtils.addTwoTermsNoZeros( soln, new IntegerNumber( -factors[2] * getDet(sub3) ) );
		}
		
		nNotZero = 0;
		
		if(factors[0] * getDet(sub1)!=0) nNotZero++;
		if(factors[1] * getDet(sub2)!=0) nNotZero++;
		if(factors[2] * getDet(sub3)!=0) nNotZero++;
		
		display4 = (nNotZero > 1);
		
		solution.add(soln.toString());
		
		// Calculate the determinent
		if(order)
		{
			det = factors[0] * getDet(sub1) - factors[1] * getDet(sub2) + 
				  factors[2] * getDet(sub3);
		}
		else
		{
			det = -factors[0] * getDet(sub1) + factors[1] * getDet(sub2) - 
			  	  factors[2] * getDet(sub3);		
		}
		
		// Generate some other stuff
		MathsOp temp = MatrixAlgebra.makeIntegerMatrix(matrix);
		temp.setLocalTex(tex);
		matrixDet = temp.toString();
	}

    /**
     * DetExpr method generates in LaTeX the expression for calculating the determinant of a 2x2 matrix
     * @param m - 2x2 matrix
     * @return a MathsOp which contains the expression for the determinant of the 2x2 matrix m
     */	
	private MathsOp DetExpr( int[][] m )
	{
		MathsOp expr;
		
		expr = new Multiplication( new IntegerNumber(m[0][0]), new IntegerNumber(m[1][1]) );
		expr = new Subtraction( expr, new Multiplication( new IntegerNumber(m[1][0]), new IntegerNumber(m[0][1]) ) );
		
		return expr;
	}
	
    /**
     * getDet method calculates the determinant of a 2x2 matrix
     * @param m - the 2x2 matrix
     * @return the determinant of the 2x2 matrix m
     */	
	private int getDet( int[][] m )
	{
		return m[0][0] * m[1][1] - m[0][1] * m[1][0];
	}
  
 } 