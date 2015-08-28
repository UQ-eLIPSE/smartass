/* @(#)NormVectorModule.java
 *
 * This file is part of SmartAss and describes class NormVectorModule for 
 * question of normalizing a vector. Given a vector with three elements normalize the vector.
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
* Class NormVectorModule describes
* questions on normalizing vectors.
*
* @version 1.0 31.3.2008
*/
public class NormVectorModule extends MathsModule
{
	private String vectorString;
	
	private Vector<String> norm = new Vector<String>();
	
	private Vector<String> normalizedVec = new Vector<String>();
	
	private int sqrtNorm;
	private int nonSqrtNorm;
	
	private boolean sqrtSimplifies;
	private boolean normSimplifies;

	/**
	* Constructor NormVectorModule generates the question
	* @params  Engine
	*/	
	public NormVectorModule(Engine engine, String[] params) 
	{
		super(engine);
		
		try
		{
	    						    		  
		}	
		catch (IllegalArgumentException e)
		{
			System.out.println("IllegalArgumentException while processing parameters passed into MatrixInverseModule");
			throw e;
		}
		
	} //constructor

	/**
	 * getSection method typesets a question and solution 
	 * @return a String containing Latex code for the section
	 */			
	public NormVectorModule(Engine engine) 
	{
		super(engine);
		
		generate();
	} //constructor
	
	public String getSection(String name) 
	{     
		if(name.equals("vec")) return vectorString;
		else if(name.equals("norm1")) return norm.get(0);
		else if(name.equals("norm2")) return norm.get(1);
		else if(name.equals("norm3")) return norm.get(2);
		else if(name.equals("normalizedvec1")) return normalizedVec.get(0);
		else if(name.equals("normalizedvec2")) return normalizedVec.get(1);
		else if(name.equals("normalizedvec3")) return normalizedVec.get(2);
		else if(name.equals("sqrtsimplifies")) return (sqrtSimplifies) ? "1" : "0";
		else if(name.equals("normsimplifies")) return (normSimplifies) ? "1" : "0";
		
		return super.getSection(name);
	}
	
	/**
	 * generate method generates the vector and the solution
	 */	
	private void generate()
	{
		int[][] vector = new int[3][1];
		
		MathsOp[][] vectorOp = new MathsOp[3][1];
		
		MathsOp op;
		
		int i;	
		int p;
		int normSq;
		int temp;
		
		int hcf;
		
		String[] localTex = {"\\; / \\;"};
		
		boolean finished;
		
		MathsOp[] terms = new MathsOp[3];
		
		int[] primes = {2,3,5,7,11,13};
		
		// First randomly generate the vector
		do
		{
			normSq = 0;
			
			for(i = 0; i < 3; i++)
			{
				vector[i][0] = RandomChoice.randInt(-9,9);
				normSq += vector[i][0] * vector[i][0];
			}
		}
		while( normSq == 0 );
		
		vectorString = (MatrixAlgebra.makeIntegerMatrix( vector )).toString();
		
		// Now create an expression for the norm
		for(i = 0; i < 3; i++)
			terms[i] = MathsUtils.multiplyConstToPower(1, new IntegerNumber(vector[i][0]), 2);
		
		terms[0] = MathsUtils.addTwoTermsNoZeros( terms[0], MathsUtils.addTwoTermsNoZeros( terms[1], terms[2] ) );
		
		norm.add( "\\sqrt{ " + (terms[0]).toString() + " }" );
		norm.add( "\\sqrt{ " + Integer.toString(normSq) + " }" );
		
		// Now simplify the square root
		sqrtNorm = normSq;
		nonSqrtNorm = 1;
		
		sqrtSimplifies = false;
		
		finished = false;
		
		while( !finished )
		{
			finished = true;
			
			for( p = 0; p <= 5; p++ )
			{
				i = primes[p];
				
				temp = HCFModule.hcf(i*i, sqrtNorm);
				
				if(temp == i*i)
				{
					sqrtNorm /= i * i;
					nonSqrtNorm *= i;
					
					sqrtSimplifies = true;
					
					finished = false;
					
					p--;
				}
			}
		}
		
		if(sqrtNorm > 1)
			norm.add( (MathsUtils.coefTimesNumber( nonSqrtNorm, new Variable("\\sqrt{ " + Integer.toString(sqrtNorm) + " }") )).toString() );
		else
			norm.add( Integer.toString(nonSqrtNorm) );
		
		// Now build up an expression for the normalized vector
		op = new FractionOp( new IntegerNumber(1), new Variable("|{\\bf a}|") );
		op = new UnprintableMultiplication(op, MatrixAlgebra.makeIntegerMatrix( vector ) );
		
		normalizedVec.add( op.toString() );
		
		op = new FractionOp( new IntegerNumber(1), new Variable(norm.get(2)) );
		op = new UnprintableMultiplication(op, MatrixAlgebra.makeIntegerMatrix( vector ) );
		
		normalizedVec.add( op.toString() );			
		
		hcf = HCFModule.hcf( vector[0][0], HCFModule.hcf( vector[1][0], vector[2][0] ) );
		
		normSimplifies = (hcf > 1);
		
		for( i = 0; i < 3; i++ )
			vector[i][0] /= hcf;
		
		if(sqrtNorm > 1)
		{
			op = MathsUtils.coefTimesNumber( nonSqrtNorm / hcf, new Variable( "\\sqrt{" + Integer.toString(sqrtNorm) + "}" ) );
			op = new FractionOp( new IntegerNumber(1), op);
		}
		else
			op = new FractionOp( new IntegerNumber(1), new IntegerNumber( nonSqrtNorm / hcf ) );
		
		op = new UnprintableMultiplication(op, MatrixAlgebra.makeIntegerMatrix( vector ) );
		
		normalizedVec.add( op.toString() );		
	}
  
 } 