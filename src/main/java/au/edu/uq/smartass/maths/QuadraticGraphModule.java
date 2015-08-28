/* @(#)QuadraticGraphModule.java
 *
 * This file is part of SmartAss and describes class QuadraticGraphModule for questions on Quadratic functions. 
 * Given a quadratic...
 * Draw quadratic and calculate intercept points etc...
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
* Class QuadraticGraphModule describes the question on quadratic functions 
* Given a quadratic...
* Draw quadratic and calculate intercept points etc...
*
* @version 1.0 4.4.2008
*/
public class QuadraticGraphModule extends MathsModule
{
	private String quadratic;
	private String normQuadratic;
	private String factQuadratic;
	private String graph;
	
	private int[] roots;
	private int yIntercept;	
	
	int[] coeffs;
 
	/**
	* Constructor QuadraticGraphModule initialises the question
	* @params  Engine
	*/
	public QuadraticGraphModule(Engine engine) 
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
 		if(name.equals("quadratic")) return quadratic;
 		else if(name.equals("normquadratic")) return normQuadratic;
 		else if(name.equals("factquadratic")) return factQuadratic;
 		else if(name.equals("root1")) return Integer.toString(roots[0]);
 		else if(name.equals("root2")) return Integer.toString(roots[1]);
 		else if(name.equals("yintercept")) return Integer.toString(yIntercept);
 		else if(name.equals("graph")) return graph;
 		else if(name.equals("coeff2")) return Integer.toString(coeffs[2]);
 		
		return super.getSection(name);
	}
 	
	/**
	 * generate method generates a random quadratic, all the answers and LaTeX to draw the graph
	 * aswell as to display the answer in LaTeX
	 */	
 	private void generate()
 	{
 		int i;
 		
 		int temp;
 		
 		roots = new int[2];
 		
 		coeffs = new int[3];
 		
 		double[] x;
 		double[] y;
 		
 		double a;
 		double b;
 		double c;
 		
 		double left;
 		double right;
 		
 		double centre;
 		double dist;
 		
 		double xMin;
 		double yMax;
 		double yMin;
 		
 		boolean finished;
 		
 		// Generate the question
 		MathsOp[] terms = new MathsOp[3];
 		
 		int factor = RandomChoice.randInt(-4,3);
 		
 		if(factor >= 0) factor++;
 		
 		finished = false;
 		
 		// Generate the roots
 		while( !finished )
 		{
 			finished = true;
 			
 			roots[0] = RandomChoice.randInt(-10,10);
 			roots[1] = RandomChoice.randInt(-10,10);
 			
 			// If the roots are too far apart of too close together the graph could be potentially
 			// misleading to a student so we prune out these cases. Note: it is alright if the
 			// roots are equal i.e. a double root.
 			if( Math.abs(roots[0] * roots[1]) > 30.0 )
 				finished = false;
 			else if( roots[0] != roots[1] )
 			{
 		 		c = roots[0] * roots[1];
 		 		b = -(roots[0] + roots[1]);
 		 		
 		 		if(roots[0] > roots[1])
 		 		{
 		 			right = Math.max(roots[0], 0.0) + 1.0;
 		 			left = Math.min(roots[1], 0.0) - 1.0;
 		 		} 		
 		 		else
 		 		{
 		 			left = Math.min(roots[0], 0.0) - 1.0;
 		 			right = Math.max(roots[1], 0.0) + 1.0;		 			
 		 		} 	
 		 		
 		 		yMax = Math.max( left * left + b * left + c, right * right + b * right + c );
 		 		
 		 		xMin = -0.5 * b;
 		 		yMin = xMin * xMin + b * xMin + c;
 		 		
 		 		if( Math.abs(yMin) / Math.abs(yMax) < 0.1 )
 		 			finished = false;
 			}
 		}
 		
 		if(roots[0] > roots[1])
 		{
 			temp = roots[0];
 			roots[0] = roots[1];
 			roots[1] = temp;
 		}
 		
 		// Generate the LaTeX for the question
 		coeffs[0] = factor * roots[0] * roots[1];
 		coeffs[1] = -(roots[0] + roots[1]) * factor;
 		coeffs[2] = factor;
 		
 		for(i = 2; i >= 0; i--)
 			terms[i] = MathsUtils.multiplyConstToPower(coeffs[i], new Variable("x"), i);
 		
 		quadratic = (MathsUtils.addTwoTermsNoZeros( terms[2], MathsUtils.addTwoTermsNoZeros( terms[1], terms[0] ) )).toString();
 		
 		for(i = 2; i >= 0; i--)
 			terms[i] = MathsUtils.multiplyConstToPower(coeffs[i]/coeffs[2], new Variable("x"), i);
 		
 		normQuadratic = (MathsUtils.addTwoTermsNoZeros( terms[2], MathsUtils.addTwoTermsNoZeros( terms[1], terms[0] ) )).toString();
 		
 		if( roots[0] == roots[1] )
 		{
 			terms[0] = MathsUtils.addTwoTermsNoZeros( new Variable("x"), new IntegerNumber( -roots[0] ) );
 			terms[0] = MathsUtils.multiplyConstToPower(1, terms[0], 2);
 		}
 		else
 		{
 			terms[0] = MathsUtils.addTwoTermsNoZeros( new Variable("x"), new IntegerNumber( -roots[0] ) );
 			terms[1] = MathsUtils.addTwoTermsNoZeros( new Variable("x"), new IntegerNumber( -roots[1] ) );
 			
 			terms[0] = new UnprintableMultiplication( terms[0], terms[1] );
 		}
 		
 		factQuadratic = terms[0].toString();
 		
 		yIntercept = coeffs[0];
 		
 		// Generate the graph
 		a = Math.min(roots[0], 0.0) - 1.0;
 		b = Math.max(roots[1], 0.0) + 1.0;
 		
 		centre = -(double)(coeffs[1]) / (2.0 * (double)(coeffs[2]) );
 		
 		dist = Math.max( Math.abs(centre-a), Math.abs(centre-b) );
 		
 		x = TexPlot.getInterval( centre - dist, centre + dist, 0.1);
 		y = new double[x.length];
 		
 		for(i=0; i < x.length; i++)
 			y[i] = coeffs[0] + coeffs[1] * x[i] + coeffs[2] * x[i] * x[i];
 		
 		graph = TexPlot.getTex(x, y, 150, 150, TexPlot.fTiny);
 		
 		graph = graph.substring(0,graph.length() - 2);
 	}
} 
