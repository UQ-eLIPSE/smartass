/* @(#)LinearGraphModule.java 
 *
 * This file is part of SmartAss and describes class LinearGraphModule for questions on Linear functions. 
 * Given a linear equation...
 * Draw line and calculate intercept points etc...
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
* Class LinearGraphModule describes the question on linear functions 
* Given a linear equation...
* Draw a line and calculate intercept points etc...
*
* @version 1.0 4.7.2008
*/
public class LinearGraphModule extends MathsModule
{
	private String linearEq;
	private String rearrangedLinearEq;
	private String graph;
	
	private String xIntercept;
	private String yIntercept;	
	
	private int[] coeffs;
	
	private String graphEqualScale;
 
	/**
	* Constructor LinearGraphModule initialises the question
	* @params  Engine
	*/
	public LinearGraphModule(Engine engine) 
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
 		if(name.equals("linear")) return linearEq;	
 		else if(name.equals("rearranged")) return rearrangedLinearEq;
 		else if(name.equals("coeff0")) return Integer.toString(coeffs[0]);
 		else if(name.equals("coeff1")) return Integer.toString(coeffs[1]);
 		else if(name.equals("coeff2")) return Integer.toString(coeffs[2]);
 		else if(name.equals("xintercept")) return xIntercept;
 		else if(name.equals("yintercept")) return yIntercept; 		
 		else if(name.equals("graph")) return graph; 		
 		else if(name.equals("equalscale")) return graphEqualScale;
 		
		return super.getSection(name);
	}
 	
	/**
	 * generate method generates a random linear equation, all the answers and LaTeX to draw the graph
	 * aswell as to display the answer in LaTeX
	 */	
 	private void generate()
 	{
 		int i;
 		
 		double a;
 		double b;
 		
 		coeffs = new int[3];
 		
 		double[] x;
 		double[] y;
 		
 		MathsOp[] terms = new MathsOp[3];
 		
 		boolean finished;
 		
 		// Generate the linear equation	
 		finished = false;
 		
 		while( !finished )
 		{
 			finished = true;
 			
	 		for( i = 0; i < 3; i++)
	 			coeffs[i] = RandomChoice.randInt(-6,6);
	 		
	 		if( coeffs[0] == 0 && coeffs[1] == 0 )
	 			finished = false;
	 		else if( (coeffs[0] == 0 || coeffs[1] == 0) && coeffs[2] == 0 )
	 			finished = false;
 		}
 			
 		// Generate TeX 		 		
 		terms[0] = MathsUtils.multiplyVarToConst(coeffs[0], new Variable("y"));
 		terms[1] = MathsUtils.multiplyVarToConst(coeffs[1], new Variable("x"));
 		terms[2] = new IntegerNumber(coeffs[2]);
 		
 		linearEq = (MathsUtils.addTwoTermsNoZeros( terms[0], MathsUtils.addTwoTermsNoZeros( terms[1], terms[2] ) )).toString();
 		
 		if( coeffs[0] != 0 )
 		{ 			
 	 		terms[0] = new Variable("y");
 	 		
 	 		if( coeffs[1] != 0 )
 	 		{
 	 			if( Math.abs( coeffs[1] ) != Math.abs( coeffs[0] ) )
 	 			{
	 	 			terms[1] = formFraction( -coeffs[1], coeffs[0] );
	 	 			terms[1] = new UnprintableMultiplication( terms[1], new Variable( "x" ) );
 	 			}
 	 			else if( coeffs[1] ==  coeffs[0] )
 	 			{
 	 				terms[1] = new Variable( "x" );
 	 				terms[1] = new UnaryMinus( terms[1] );
 	 			}
 	 			else
 	 				terms[1] = new Variable( "x" );
 	 		}
 	 		else if( coeffs[1] == 0 )
 	 			terms[1] = new IntegerNumber( 0 );
 	 		
 	 		terms[2] = formFraction( -coeffs[2], coeffs[0] );
	 		
 	 		rearrangedLinearEq = (new Equality( terms[0], MathsUtils.addTwoTermsNoZeros( terms[1], terms[2] ) )).toString();
 		}
 		else
 		{
 			terms[1] = new Variable("x"); 	 		
 	 		terms[2] = formFraction( -coeffs[2], coeffs[1] );
	 		
 	 		rearrangedLinearEq = (new Equality( terms[1], terms[2] )).toString();			
 		}
 		
 		// Get intercepts
 		if( coeffs[1] != 0 )
 			xIntercept = ( formFraction( -coeffs[2], coeffs[1] ) ).toString();
 		else
 			xIntercept = "";
 			
 		if( coeffs[0] != 0 )
 			yIntercept = ( formFraction( -coeffs[2], coeffs[0] ) ).toString();
 		else
 			yIntercept = "";
 		
 		// Generate the graph 		
 		if( coeffs[0] == 0 )
 		{
 			a = -2.0;
 			b = 2.0;
 			
	 		y = TexPlot.getInterval( a, b, 1.0);
	 		x = new double[y.length];
	 		
	 		for(i=0; i < x.length; i++)
	 			x[i] = -1.0 * coeffs[2] / coeffs[1];			
 		}
 		else if( coeffs[1] == 0 )
 		{
 			a = -2.0;
 			b = 2.0;
 			
	 		x = TexPlot.getInterval( a, b, 1.0);
	 		y = new double[x.length];
	 		
	 		for(i=0; i < y.length; i++)
	 			y[i] = -1.0 * coeffs[2] / coeffs[0];
 		}
 		else
 		{
 			a = Math.min( (-1.0 * coeffs[2]) / coeffs[1], 0.0) - 1.0;
 			b = Math.max( (-1.0 * coeffs[2]) / coeffs[1], 0.0) + 1.0;
 			
	 		x = TexPlot.getInterval( a, b, 1.0);
	 		y = new double[x.length];
	 		
	 		for(i=0; i < y.length; i++)
	 			y[i] = (-1.0 * coeffs[1]) / coeffs[0] * x[i] + (-1.0 * coeffs[2]) / coeffs[0];
 		}
 		
		graph = TexPlot.getTex(x, y, 150, 150, TexPlot.fTiny);
		
		graphEqualScale = graph.substring(graph.length() - 1, graph.length() );		
		graph = graph.substring(0,graph.length() - 1);
 	}
 	
	/**
	 * formFraction method takes two integers creates the TeX to display them as a fraction using FractionOp
	 * @param  num is the numerator of the fracion
	 * @param  den is the denominator of the fracion
	 * @return a MathsOp variable that contains the fracion information
	 */	
 	private MathsOp formFraction(int num, int den)
 	{
 		MathsOp frac;
 		
 		int d = gcd( num, den );
 		
 		num = num / d;
 		den = den / d;
 		
 		if( den == 1 || den == -1 )
 		{
	 		if( num < 0 && den < 0 )
	 			frac = new IntegerNumber( -num );
	 		else if( num < 0 && den > 0 )
	 			frac = new IntegerNumber( num ); 			
	 		else if( num > 0 && den < 0 )
	 			frac = new IntegerNumber( -num );
	 		else if( num > 0 && den > 0 )
	 			frac = new IntegerNumber( num );	 			
	 		else
	 			frac = new IntegerNumber( 0 ); 				
 		}
 		else
 		{ 		
	 		if( num < 0 && den < 0 )
	 		{
	 			frac = new FractionOp( new IntegerNumber( -num ), new IntegerNumber( -den ) );
	 		}
	 		else if( num < 0 && den > 0 )
	 		{
	 			frac = new FractionOp( new IntegerNumber( -num ), new IntegerNumber( den ) );
	 			frac = new UnaryMinus( frac );	 			
	 		}
	 		else if( num > 0 && den < 0 )
	 		{
	 			frac = new FractionOp( new IntegerNumber( num ), new IntegerNumber( -den ) );
	 			frac = new UnaryMinus( frac );	 			
	 		}
	 		else if( num > 0 && den > 0 )
	 		{
	 			frac = new FractionOp( new IntegerNumber( num ), new IntegerNumber( den ) );	 			
	 		}
	 		else
	 			frac = new IntegerNumber( 0 ); 		
 		}
 		
 		return frac;
 	}
 	
 	/**
 	 * Return the greatest common divisor
 	 */ 	 
	/**
	 * gcd method takes two integers and gets their greatest common divisor
	 * @param  a - the frist integer
	 * @param  b - the second integer
	 * @return a positive integer which is the greatest common divisor
	 */	
 	 private int gcd(int a, int b) 
 	 {
 		 if( b < 0 )
 			 b = -b;
 		 
 		 if( a < 0 )
 			 a = -a; 		 
 		 
 		 if (b==0) 
 			 return a;
 		 else
 			 return gcd(b, a % b);
 	 } 
} 
