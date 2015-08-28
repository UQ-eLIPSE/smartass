/* @(#)FractionEquation3Module.java
 *
 * This file is part of SmartAss and describes class FractionEquation3Module for 
 * Find value of x , if x=a/b + (-, *, /) c/d 
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

/**
 * class FractionEquation3Module generates equation of x=a/b + c/d type
 */
public class FractionEquation3Module extends MathsModule {
    MathsModule module;
    Variable vx;
	/**
	 * constructor FractionEquation3Module
	 * initializes a question and solution 
	 * @ param Engine engine instance 
	 */
    public FractionEquation3Module(Engine engine) {
    	super(engine);	
    	vx = new Variable(RandomChoice.makeChoice("[x]/1;[y]/1;[z]/1")[0]);
        int operation=RandomChoice.randInt(0,3);
        switch (operation) {
        	case 0: 
        		    module= new FractionAddFractionModule(engine);
        	        break;
        	case 1: 
        			module= new FractionSubtractFractionModule(engine);
        			break;
        	case 2:
        			module= new FractionTimesFractionModule(engine);
        			break;
        	case 3: 
        			module= new FractionDividedByFractionModule(engine);		
        }
        	
    }
   	public String getSection(String name) {
   		
	  if (name.equals("varname"))
			return "\\ensuremath{" + vx.toString() + "}";
	  else 
	      return module.getSection(name);
	}
    
}