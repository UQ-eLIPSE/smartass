/* @(#)TrigDefiniteIntegrationModule.java
 *
 * This file is part of SmartAss and describes class TrigDefiniteIntegrationModule for 
 * questions on definite integration of trig functions.
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
import java.util.Vector;

/**
* Class TrigDefiniteIntegrationModule describes
* questions on definite integration of trig functions.
*
* @version 1.0 27.11.2007
*/
public class TrigDefiniteIntegrationModule extends MathsModule 
{
	private String question;
	private int answer;
	private Vector<String> solution = new Vector<String>();
	boolean showLine;
	
	private Variable vx = new Variable("x");

	/**
	* Constructor TrigDefiniteIntegrationModule initialises the question
	* @params  Engine
	*/
    public TrigDefiniteIntegrationModule(Engine engine)
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
		if(name.equals("question")) return question.toString();
		else if(name.equals("answer")) return Integer.toString(answer);
		else if(name.equals("solution1")) return solution.get(0);
		else if(name.equals("solution2")) return solution.get(1);
		else if(name.equals("solution3")) return solution.get(2);
		else if(name.equals("solution4")) return solution.get(3);
		else if(name.equals("solution5")) return solution.get(4);
		else if(name.equals("showline")) return (showLine)? "1" : "0";
		
		return super.getSection(name);		
	}

    /**
     * generate method randomly generates parameters and calculates the solution strings
     */
	private void generate() 
	{
		int[] C = {0,0};
		int[] pC = {0,0};
		int[] aC = {0,0};
		
		int[] pUp = {0,0};
		int[] pLo = {0,0};
		
		int a = 0;
		int b = 0;
		
		int num;
		int den;
		
		String temp;
		
		MathsOp[] term = new MathsOp[2];
		MathsOp soln;
		
		int upTerm;
		int loTerm;
		
		boolean order = (RandomChoice.randInt(0,1) == 0);
		
		// Generate appropriate constants		
		while( pC[0] == 0 || pC[1] == 0 )
		{
			pC[0] = RandomChoice.randInt(-4, 4);
			pC[1] = RandomChoice.randInt(-4, 4);
		}

		while( C[0] == 0 && C[1] == 0 )
		{
			C[0] = pC[0] * RandomChoice.randInt(0, 20 / Math.abs(pC[0]));
			C[1] = pC[1] * RandomChoice.randInt(0, 20 / Math.abs(pC[1]));
		}
		
		a = RandomChoice.randInt(-8, 7);
		b = RandomChoice.randInt(a+1, 8);
	
		// Now build the expression and question
		if( order )
		{
			term[0] = new SmartFunction( "\\cos", MathsUtils.multiplyVarToConst( pC[0], vx ) );
			term[0] = MathsUtils.multiplyVarToConst( C[0], term[0] );
			
			term[1] = new SmartFunction( "\\sin", MathsUtils.multiplyVarToConst( pC[1], vx ) );
			term[1] = MathsUtils.multiplyVarToConst( C[1], term[1] );	
		}
		else
		{
			term[0] = new SmartFunction( "\\sin", MathsUtils.multiplyVarToConst( pC[0], vx ) );
			term[0] = MathsUtils.multiplyVarToConst( C[0], term[0] );
			
			term[1] = new SmartFunction( "\\cos", MathsUtils.multiplyVarToConst( pC[1], vx ) );
			term[1] = MathsUtils.multiplyVarToConst( C[1], term[1] );				
		}
		
		soln = MathsUtils.addTwoTermsNoZeros(term[0], term[1]);

		question = (new DefiniteIntegral( getBound( b ), getBound( a ), soln, vx)).toString();
		
		// Now calculate the answer
		aC[0] = C[0] / pC[0] * ((order)?1:-1);
		aC[1] = -C[1] / pC[1] * ((order)?1:-1);
		
		pUp[0] = pC[0] * b;
		pLo[0] = pC[0] * a;
		
		pUp[1] = pC[1] * b;
		pLo[1] = pC[1] * a;	
		
		if( order )
		{
			upTerm = aC[0] * getSin(pUp[0]) + aC[1] * getCos(pUp[1]);
			loTerm = aC[0] * getSin(pLo[0]) + aC[1] * getCos(pLo[1]);
		}
		else
		{
			upTerm = aC[0] * getCos(pUp[0]) + aC[1] * getSin(pUp[1]);
			loTerm = aC[0] * getCos(pLo[0]) + aC[1] * getSin(pLo[1]);
		}
	
		answer = upTerm - loTerm;
		
		// Now build up the solution
		// First step...	
		if( order )
		{
			term[0] = new SmartFunction( "\\sin", MathsUtils.multiplyVarToConst( pC[0], vx ) );
			term[0] = MathsUtils.multiplyVarToConst( aC[0], term[0] );		
			
			term[1] = new SmartFunction( "\\cos", MathsUtils.multiplyVarToConst( pC[1], vx ) );
			term[1] = MathsUtils.multiplyVarToConst( aC[1], term[1] );
		}
		else
		{
			term[0] = new SmartFunction( "\\cos", MathsUtils.multiplyVarToConst( pC[0], vx ) );
			term[0] = MathsUtils.multiplyVarToConst( aC[0], term[0] );		
			
			term[1] = new SmartFunction( "\\sin", MathsUtils.multiplyVarToConst( pC[1], vx ) );
			term[1] = MathsUtils.multiplyVarToConst( aC[1], term[1] );		
		}
		
		soln = MathsUtils.addTwoTermsNoZeros(term[0], term[1]);	
		
		solution.add( (new DeltaAntiderivative(getBound(a), getBound(b), soln) ).toString() );
		
		// Second step...
		showLine = false;
		
		if( order )
		{
			if( Math.abs(pC[0]) == 1 )
				term[0] = new SmartFunction( "\\sin",  MathsUtils.multiplyVarToConst( pC[0], getBound(b) ) );
			else
			{
				showLine = true;
				term[0] = new SmartFunction( "\\sin",  MathsUtils.multiplyVarToConst( pC[0], new Brackets( getBound(b) ) ) );
			}
				
			term[0] = MathsUtils.multiplyVarToConst( aC[0], term[0] );		
			
			if( Math.abs(pC[1]) == 1 )
				term[1] = new SmartFunction( "\\cos", MathsUtils.multiplyVarToConst( pC[1], getBound(b) ) );
			else
			{
				showLine = true;
				term[1] = new SmartFunction( "\\cos", MathsUtils.multiplyVarToConst( pC[1], new Brackets( getBound(b) ) ) );
			}
				
			term[1] = MathsUtils.multiplyVarToConst( aC[1], term[1] );
		}
		else
		{
			if( Math.abs(pC[0]) == 1 )
				term[0] = new SmartFunction( "\\cos", MathsUtils.multiplyVarToConst( pC[0], getBound(b) ) );
			else
			{
				showLine = true;
				term[0] = new SmartFunction( "\\cos", MathsUtils.multiplyVarToConst( pC[0], new Brackets( getBound(b) ) ) );
			}
				
			term[0] = MathsUtils.multiplyVarToConst( aC[0], term[0] );		
			
			if( Math.abs(pC[1]) == 1 )
				term[1] = new SmartFunction( "\\sin", MathsUtils.multiplyVarToConst( pC[1], getBound(b) ) );
			else
			{
				showLine = true;
				term[1] = new SmartFunction( "\\sin", MathsUtils.multiplyVarToConst( pC[1], new Brackets( getBound(b) ) ) );
			}
				
			term[1] = MathsUtils.multiplyVarToConst( aC[1], term[1] );
		}
		
		soln = MathsUtils.addTwoTermsNoZeros(term[0], term[1]);	
		
		temp = soln.toString();
		
		if( order )
		{
			if( Math.abs(pC[0]) == 1 )
				term[0] = new SmartFunction( "\\sin",  MathsUtils.multiplyVarToConst( pC[0], getBound(a) ) );
			else
			{
				showLine = true;
				term[0] = new SmartFunction( "\\sin",  MathsUtils.multiplyVarToConst( pC[0], new Brackets( getBound(a) ) ) );
			}
				
			term[0] = MathsUtils.multiplyVarToConst( aC[0], term[0] );		
			
			if( Math.abs(pC[1]) == 1 )
				term[1] = new SmartFunction( "\\cos", MathsUtils.multiplyVarToConst( pC[1], getBound(a) ) );
			else
			{
				showLine = true;
				term[1] = new SmartFunction( "\\cos", MathsUtils.multiplyVarToConst( pC[1], new Brackets( getBound(a) ) ) );
			}
				
			term[1] = MathsUtils.multiplyVarToConst( aC[1], term[1] );
		}
		else
		{
			if( Math.abs(pC[0]) == 1 )
				term[0] = new SmartFunction( "\\cos", MathsUtils.multiplyVarToConst( pC[0], getBound(a) ) );
			else
			{
				showLine = true;
				term[0] = new SmartFunction( "\\cos", MathsUtils.multiplyVarToConst( pC[0], new Brackets( getBound(a) ) ) );
			}
				
			term[0] = MathsUtils.multiplyVarToConst( aC[0], term[0] );		
			
			if( Math.abs(pC[1]) == 1 )
				term[1] = new SmartFunction( "\\sin", MathsUtils.multiplyVarToConst( pC[1], getBound(a) ) );
			else
			{
				showLine = true;
				term[1] = new SmartFunction( "\\sin", MathsUtils.multiplyVarToConst( pC[1], new Brackets( getBound(a) ) ) );
			}
				
			term[1] = MathsUtils.multiplyVarToConst( aC[1], term[1] );
		}
		
		soln = MathsUtils.addTwoTermsNoZeros(term[0], term[1]);	
		
		solution.add( "\\biggl(" + temp + "\\biggr) - \\biggl(" + soln.toString() + "\\biggr)" );	
		
		// Third step...
		if( order )
		{
			term[0] = new SmartFunction( "\\sin", new Brackets( getBound(pC[0]*b) ) );
			term[0] = MathsUtils.multiplyVarToConst( aC[0], term[0] );		
			
			term[1] = new SmartFunction( "\\cos", new Brackets( getBound(pC[1]*b) ) );
			term[1] = MathsUtils.multiplyVarToConst( aC[1], term[1] );
		}
		else
		{
			term[0] = new SmartFunction( "\\cos", new Brackets( getBound(pC[0]*b) ) );
			term[0] = MathsUtils.multiplyVarToConst( aC[0], term[0] );		
			
			term[1] = new SmartFunction( "\\sin", new Brackets( getBound(pC[1]*b) ) );
			term[1] = MathsUtils.multiplyVarToConst( aC[1], term[1] );			
		}
		
		soln = MathsUtils.addTwoTermsNoZeros(term[0], term[1]);	
		
		temp = soln.toString();
		
		if( order )
		{
			term[0] = new SmartFunction( "\\sin", new Brackets( getBound(pC[0]*a) ) );
			term[0] = MathsUtils.multiplyVarToConst( aC[0], term[0] );		
			
			term[1] = new SmartFunction( "\\cos", new Brackets( getBound(pC[1]*a) ) );
			term[1] = MathsUtils.multiplyVarToConst( aC[1], term[1] );
		}
		else
		{
			term[0] = new SmartFunction( "\\cos", new Brackets( getBound(pC[0]*a) ) );
			term[0] = MathsUtils.multiplyVarToConst( aC[0], term[0] );		
			
			term[1] = new SmartFunction( "\\sin", new Brackets( getBound(pC[1]*a) ) );
			term[1] = MathsUtils.multiplyVarToConst( aC[1], term[1] );			
		}
		
		soln = MathsUtils.addTwoTermsNoZeros(term[0], term[1]);	
		
		solution.add( "\\biggl(" + temp + "\\biggr) - \\biggl(" + soln.toString() + "\\biggr)" );
		
		// Fourth step...
		if( order )
		{
			term[0] = new Brackets( new IntegerNumber( getSin(pUp[0]) ) );
			term[0] = MathsUtils.multiplyVarToConst( aC[0], term[0] );		
			
			term[1] = new Brackets( new IntegerNumber( getCos(pUp[1]) ) );
			term[1] = MathsUtils.multiplyVarToConst( aC[1], term[1] );
		}
		else
		{
			term[0] = new Brackets( new IntegerNumber( getCos(pUp[0]) ) );
			term[0] = MathsUtils.multiplyVarToConst( aC[0], term[0] );		
			
			term[1] = new Brackets( new IntegerNumber( getSin(pUp[1]) ) );
			term[1] = MathsUtils.multiplyVarToConst( aC[1], term[1] );		
		}
		
		soln = MathsUtils.addTwoTermsNoZeros(term[0], term[1]);	
		
		temp = soln.toString();
		
		if( order )
		{
			term[0] = new Brackets( new IntegerNumber( getSin(pLo[0]) ) );
			term[0] = MathsUtils.multiplyVarToConst( aC[0], term[0] );		
			
			term[1] = new Brackets( new IntegerNumber( getCos(pLo[1]) ) );
			term[1] = MathsUtils.multiplyVarToConst( aC[1], term[1] );
		}
		else
		{
			term[0] = new Brackets( new IntegerNumber( getCos(pLo[0]) ) );
			term[0] = MathsUtils.multiplyVarToConst( aC[0], term[0] );		
			
			term[1] = new Brackets( new IntegerNumber( getSin(pLo[1]) ) );
			term[1] = MathsUtils.multiplyVarToConst( aC[1], term[1] );		
		}
		
		soln = MathsUtils.addTwoTermsNoZeros(term[0], term[1]);	
		
		solution.add( "\\biggl(" + temp + "\\biggr) - \\biggl(" + soln.toString() + "\\biggr)" );
		
		// Fifth step...		
		solution.add( "(" + Integer.toString(upTerm) + ") - (" + Integer.toString(loTerm) + ")" );
	}
	
    /**
     * getCos method returns the value of cosine
     * 
     * @param x - is the multiple of pi/2 you are putting into cosine.  
     * @return is the value of cosine.
     */
	private int getCos(int x)
	{
		switch( Math.abs(x) % 4 )
		{
		case 0: return 1;		
		case 1: return 0;			
		case 2: return -1;			
		case 3: return 0;	
		}	
		
		return 0;
	}

    /**
     * getSin method returns the value of sine
     * 
     * @param x - is the multiple of pi/2 you are putting into sine.   
     * @return is the value of sine.
     */
	private int getSin(int x)
	{
		switch( Math.abs(x) % 4 )
		{
		case 0: return 0;			
		case 1: return ((x<0.0)?-1:1);		
		case 2: return 0;			
		case 3: return -1 * ((x<0.0)?-1:1);	
		}
		
		return 0;
	}

    /**
     * getBound method formats the bound into a MathsOp
     * 
     * @param x - is the multiple of pi/2 that one of your bounds of integration is.   
     * @return MathsOp of the bound.
     */
	private MathsOp getBound(int bound)
	{	
		MathsOp op;
		
		int num = bound;
		int den = 2;
		
		int factor = HCFModule.hcf(num, den);
		
		boolean pos;
		
		num /= factor;
		den /= factor;
		
		String[] tex = {"\\frac"};
		
		if( num == 0 )
			op = new IntegerNumber(0);
		else
		{
			pos = (num > 0.0);
			op = MathsUtils.multiplyVarToConst( Math.abs(num), new Variable("\\pi") );
			
			if( den != 1 )
			{
				op = new UnprintableMultiplication( new UnprintableMultiplication( op, new Variable("/") ), new IntegerNumber(den) );
				op.setLocalTex(tex);
			}
			
			if( !pos )
				op = MathsUtils.multiplyVarToConst( -1, op );
		}
		
		return op;
	}
	
}