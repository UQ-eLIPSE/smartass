/* @(#)Sim3EquationsModule.java
 *
 * This file is part of SmartAss and describes class Sim3EquationsModule for 
 * question of solving a system of three linear equations in three unknowns simulatenously using the matrix inverse. 
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
import java.util.*;
import java.math.*;

/**
* Class Sim3EquationsModule describes
* questions on solving three linear equations in three unknowns simultaneously using the matrix inverse.
*
* @version 1.0 31.3.2008
*/
public class Sim3EquationsModule extends MathsModule
{
	private String productEq;
	private String inverseEq;
	
	private String xSoln;
	private String ySoln;
	private String zSoln;
	
	private Vector<String> solution = new Vector<String>();
	private Vector<String> bSolution = new Vector<String>();
	
	private String simpleProduct;
	private String matrix1String;
	
	private String[] lhs;
	
	private int[][] rhs;
	
	private boolean rearranged;

	/**
	* Constructor Sim3EquationsModule generates the question
	* @params  Engine
	*/		
	public Sim3EquationsModule(Engine engine) 
	{
		super(engine);
		
		generate();
	} //constructor
	
	/**
	 * getSection method typesets a question and solution 
	 * @return a String containing Latex code for the section
	 */		
	public String getSection(String name) 
	{     
		if( name.equals("producteq") ) return productEq;
		else if( name.equals("inverseeq") ) return inverseEq;
		
		else if( name.equals( "simpleproduct" ) ) return simpleProduct;
		
		else if( name.equals( "matrix1string" ) ) return matrix1String;
		
		else if( name.equals( "rearranged" ) ) return (rearranged) ? "1" : "0";
		
		else if( name.equals("lhs1") ) return lhs[0];
		else if( name.equals("lhs2") ) return lhs[1];
		else if( name.equals("lhs3") ) return lhs[2];
		
		else if( name.equals("rhs1") ) return Integer.toString( rhs[0][0] );
		else if( name.equals("rhs2") ) return Integer.toString( rhs[1][0] );
		else if( name.equals("rhs3") ) return Integer.toString( rhs[2][0] );
		
		else if( name.equals("solution1") ) return solution.get(0);
		else if( name.equals("solution2") ) return solution.get(1);
		else if( name.equals("solution3") ) return solution.get(2);
		else if( name.equals("solution4") ) return solution.get(3);
		else if( name.equals("solution5") ) return solution.get(4);
		else if( name.equals("solution6") ) return solution.get(5);
		else if( name.equals("solution7") ) return solution.get(6);
		
		else if( name.equals("bsolution1") ) return bSolution.get(0);
		
		else if( name.equals("xsoln") ) return xSoln;
		else if( name.equals("ysoln") ) return ySoln;
		else if( name.equals("zsoln") ) return zSoln;
	
		return super.getSection(name);
	}
	
	/**
	 * Generates the system of linear equations and the solution to the system.
	 */	
	private void generate()
	{		
		int[][] matrix1 = new int[3][3];		
		int[][] matrix2;
		
		int[][] product;
		
		int i;
		int j;	
		int det;		
		int hcf;	
		
		Matrix working = new Matrix(3,3);	
		
		MathsOp op;	
		MathsOp op2;	
		MathsOp[] terms = new MathsOp[3];
		MathsOp[][] vecOp = new MathsOp[3][1];
		MathsOp[] vars = { new Variable("x"), new Variable("y"), new Variable("z") };
		
		// Generate random matrix and make sure it is invertible
		do
		{
			for( i = 0; i < 3; i++ )
				for( j = 0; j < 3; j++ )
					matrix1[i][j] = RandomChoice.randInt(-9,9);
		}
		while( det3x3(matrix1) == 0 || Math.abs(det3x3(matrix1)) > 5 );
		
		det = det3x3(matrix1);
		matrix2 = getAdjugate( matrix1 );
		
		// Now generate the first question string
		op = MatrixAlgebra.makeIntegerMatrix(matrix1);
		op2 = MathsUtils.multiplyConstToPower(1, op, -1);
		op = new UnprintableMultiplication(op, MatrixAlgebra.makeIntegerMatrix(matrix2));
		
		productEq = op.toString();
		inverseEq = op2.toString();
		
		// Now put together the simultaneous equations
		rhs = new int[3][1];
		lhs = new String[3];		
		
		for( i = 0; i < 3; i++ )
		{
			for( j = 0; j < 3; j++ )
				terms[j] = MathsUtils.multiplyVarToConst( matrix1[i][j], vars[j] );
			
			lhs[i] = (MathsUtils.addTwoTermsNoZeros( terms[0], MathsUtils.addTwoTermsNoZeros( terms[1], terms[2] ) )).toString();
		}
		
		for( i = 0; i < 3; i++ )
			rhs[i][0] = RandomChoice.randInt(-9,9);
		
		// Put together the solution for part (a)
		matrix1String = (MatrixAlgebra.makeIntegerMatrix(matrix1)).toString();
		
		product = MatrixAlgebra.multiplyTwoIntegerMatrices(matrix1, matrix2, working);
		
		solution.add("&=&" + working.toString());

		op = MatrixAlgebra.makeIntegerMatrix(product);
		
		solution.add( op.toString() );
		
		op = new Variable( "{\\bf I}" );
		op = MathsUtils.multiplyVarToConst( product[0][0], op );
		
		simpleProduct = op.toString();
		
		// Start putting together the solution for part (b)
		if( product[0][0] == 1 )
		{
			rearranged = false;
			
			bSolution.add("");
		}
		else if( product[0][0] == -1 )
		{
			rearranged = true;
			
			op = MathsUtils.multiplyVarToConst( product[0][0], MatrixAlgebra.makeIntegerMatrix(matrix2) );
			op2 = MatrixAlgebra.makeIntegerMatrix(matrix1);
			
			bSolution.add( op2.toString() + "\\times" + op.toString() + "= {\\bf I}" );	
		}
		else
		{
			rearranged = true;
			
			op = new FractionOp( new IntegerNumber(1), new IntegerNumber(product[0][0]) );
			op = new UnprintableMultiplication( op, MatrixAlgebra.makeIntegerMatrix(matrix2) );
			op2 = MatrixAlgebra.makeIntegerMatrix(matrix1);
			
			bSolution.add( op2.toString() + "\\times" + op.toString() + "= {\\bf I}" );			
		}
		
		// Start putting together the solution for part (c)
		for( i = 0; i < 3; i++ )
			vecOp[i][0] = vars[i];
		
		op = MatrixAlgebra.makeIntegerMatrix( matrix1 );
		op = new UnprintableMultiplication( op, new Matrix( vecOp ) );
		op = new Equality( op, MatrixAlgebra.makeIntegerMatrix( rhs ) );
		
		solution.add( op.toString() );
		
		// ...
		if( det == 1 )
			op = MatrixAlgebra.makeIntegerMatrix( matrix2 );
		else if( det == -1)		
		{
			op = MatrixAlgebra.makeIntegerMatrix( matrix2 );
			op = MathsUtils.multiplyVarToConst(-1,op);
		}
		else
		{
			op = new FractionOp(new IntegerNumber(1), new IntegerNumber(det));		
			op = new UnprintableMultiplication(op, MatrixAlgebra.makeIntegerMatrix( matrix2 ) );
		}
		
		op = new Equality( MathsUtils.multiplyConstToPower(1,MatrixAlgebra.makeIntegerMatrix(matrix1),-1), op );
		
		solution.add( op.toString() );
		
		// ...
		if( det == 1 )
			op = MatrixAlgebra.makeIntegerMatrix( matrix2 );
		else if( det == -1)		
		{
			op = new UnprintableMultiplication(op, MatrixAlgebra.makeIntegerMatrix( matrix2 ) );
			op = MathsUtils.multiplyVarToConst(-1,op);
		}
		else
		{
			op = new FractionOp(new IntegerNumber(1), new IntegerNumber(det));		
			op = new UnprintableMultiplication(op, MatrixAlgebra.makeIntegerMatrix( matrix2 ) );
		}
		
		op = new UnprintableMultiplication( op, MatrixAlgebra.makeIntegerMatrix(rhs) );
		
		solution.add( op.toString() );
		
		// ...	
		product = MatrixAlgebra.multiplyTwoIntegerMatrices(matrix2, rhs, working);
		
		if( det == 1 )
		{
			solution.add( working.toString() );
			solution.add( (MatrixAlgebra.makeIntegerMatrix(product)).toString() );
		}
		else if( det == -1)		
		{
			op = MathsUtils.multiplyVarToConst(-1, working);			
			solution.add( op.toString() );
			
			op = MathsUtils.multiplyVarToConst(-1, MatrixAlgebra.makeIntegerMatrix(product) );
			solution.add( op.toString() );
		}
		else
		{
			op = new FractionOp(new IntegerNumber(1), new IntegerNumber(det));		
			op = new UnprintableMultiplication(op, working );
			solution.add( op.toString() );
			
			op = new FractionOp(new IntegerNumber(1), new IntegerNumber(det));		
			op = new UnprintableMultiplication(op, MatrixAlgebra.makeIntegerMatrix(product) );
			solution.add( op.toString() );
		}
		
		// x solution
		if(product[0][0] != 0)
		{
			hcf = HCFModule.hcf(product[0][0],det);
			
			if( hcf == Math.abs(det) )
				xSoln = (new IntegerNumber(product[0][0] / hcf)).toString();
			else
			{
				if( det < 0 )
					xSoln = (new FractionOp( new IntegerNumber(-product[0][0] / hcf), new IntegerNumber(-det / hcf))).toString();
				else
					xSoln = (new FractionOp( new IntegerNumber(product[0][0] / hcf), new IntegerNumber(det / hcf))).toString();
			}
		}
		else
			xSoln = (new IntegerNumber(0)).toString();
		
		// y solution
		if(product[1][0] != 0)
		{
			hcf = HCFModule.hcf(product[1][0],det);
			
			if( hcf == Math.abs(det) )
				ySoln = (new IntegerNumber(product[1][0] / hcf)).toString();
			else
			{
				if( det < 0 )
					ySoln = (new FractionOp( new IntegerNumber(-product[1][0] / hcf), new IntegerNumber(-det / hcf) ) ).toString();
				else
					ySoln = (new FractionOp( new IntegerNumber(product[1][0] / hcf), new IntegerNumber(det / hcf))).toString();
			}
		}
		else
			ySoln = (new IntegerNumber(0)).toString();
		
		// z solution
		if(product[2][0] != 0)
		{
			hcf = HCFModule.hcf(product[2][0],det);
			
			if( hcf == Math.abs(det) )
				zSoln = (new IntegerNumber(product[2][0] / hcf)).toString();
			else
			{
				if( det < 0 )
					zSoln = (new FractionOp( new IntegerNumber(-product[2][0] / hcf), new IntegerNumber(-det / hcf))).toString();
				else
					zSoln = (new FractionOp( new IntegerNumber(product[2][0] / hcf), new IntegerNumber(det / hcf))).toString();				
			}
		}
		else
			zSoln = (new IntegerNumber(0)).toString();
	}

    /**
 	 * getAdjugate method - gets the Adjugate of a matrix
     * 
     * @param matrix - the matrix of which you want the adjugate of.
     * @return the adjugate of the matrix.
     **/
	private int[][] getAdjugate( int[][] matrix )
	{
		int i;
		int j;
		
		int[][] adjugate = new int[3][3];
		
		int[][] cofactor = getCofactor( matrix );
		
		for( i = 0; i < 3; i++ )
			for( j = 0; j < 3; j++ )
				adjugate[i][j] = cofactor[j][i];
		
		return adjugate;
	}

    /**
 	 * getCofactor method - gets the cofactor matrix of a matrix
     * 
     * @param matrix - the matrix of which you want the cofactor matrix of.
     * @return the cofactor of the matrix.
     **/
	private int[][] getCofactor( int[][] matrix )
	{
		int i;
		int j;
		
		int[][] cofactor = new int[3][3];
		
		for(i = 0; i < 3; i++)
			for(j = 0; j < 3; j++)
			{
				if( (i + j) % 2 == 0 )
					cofactor[i][j] = getMinor(matrix,i,j);
				else
					cofactor[i][j] = -getMinor(matrix,i,j);
			}
		
		return cofactor;
	}

    /**
 	 * det3x3 method calculates the determinant of a three by three matrix.
     * 
     * @param matrix - the matrix of which you want the determinate of.
     * @return the determinate of the matrix.
     **/
	private int det3x3( int[][] matrix )
	{
		return matrix[0][0] * getMinor(matrix, 0, 0) - matrix[0][1] * getMinor(matrix, 0, 1) +
		       matrix[0][2] * getMinor(matrix, 0, 2);
	}

    /**
 	 * det3x3 method calculates a minor of a three by three matrix given which row and column to exclude.
     * 
     * @param matrix - the matrix of which you want the minor of.
     * @param i - the row you want to exclude.
     * @param j - the column you to exclude.
     * @return the appropriate minor.
     **/
	private int getMinor( int[][] matrix, int i, int j )
	{
		int row = 0;
		int col;
		
		int[][] minorMatrix = new int[2][2];
		
		int n;
		int m;
		
		for(n = 0; n < 3; n++)
		{
			if( n == i )
				continue;
			
			col = 0;
			
			for(m = 0; m < 3; m++)
			{
				if( m == j )
					continue;
				
				minorMatrix[row][col] = matrix[n][m];
				
				col++;
			}
			
			row++;
		}
		
		return det2x2( minorMatrix );
	}
	
    /**
 	 * det2x2 method calculates the determinant of a two by two matrix.
     * 
     * @param matrix - the matrix of which you want the determinate of.
     * @return the determinate of the matrix.
     **/
	private int det2x2( int[][] matrix )
	{
		return matrix[0][0] * matrix[1][1] - matrix[0][1] * matrix[1][0];
	}
 } 