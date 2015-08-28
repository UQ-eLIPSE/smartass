/* @(#) TrigFunctionGraphModule.java
 *
 * This file is part of SmartAss and describes class TrigFunctionGraphModule.
 * Sketch a graph of a trigonometrical function sin or cos. Graphs provided in external files
 * and referenced from this module and the template.
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
* Class TrigFunctionGraphModule describes the question on 
* trigonometrical functions graphs - scketch a graph of sin or cos.   
*
* @version 1.0 26.05.2007
*/
public class TrigFunctionGraphModule extends MathsModule{
 private final int NUMBER_OF_GRAPHS=13;  //total number of graphs == NUMBER_OF_GRAPHA*2, i.e. 26, 13 - sin, 13 - cos
 MathsOp baseFunction;
 MathsOp derivedFunction;
 Variable x=new Variable("x");
 String fileName;
/**
* Constructor TrigFunctionGraphModule initialises the question.
* 
*/
 public TrigFunctionGraphModule(Engine engine) 
	{
				super(engine);	
				generate();				
	} //constructor
	
/**
 * getSection method typesets a question and solution 
 */
 public String getSection(String name) { 		
 			 if (name.contains("basefunction")){
 		 			return "\\ensuremath{"+baseFunction.toString()+"}";
 			 }	
 		 	  if (name.contains("derivedfunction")){
 			   return "\\ensuremath{"+derivedFunction.toString()+"}";
 			 }	
 		 	  if (name.contains("filename")){
 		 			return fileName;
 			 }	
		return super.getSection(name);
	}
	
/**
 * Method generates NUMBER_OF_GRAPHS=24 simple trig functions and initializes 
 * baseFunction, derivedFunction and fileName.
 *
 */
 
// Generates sets
 protected void generate() {
 String trigFun;
 if (RandomChoice.randSign()==1) 
 	trigFun="sin";
 else 
 	trigFun="cos";	
 baseFunction=new SmartFunction("\\"+trigFun, x);		
 
 switch (RandomChoice.randInt(1,NUMBER_OF_GRAPHS)){	
 	case 1: //2 sin x, 2 cos x
 		derivedFunction=new UnprintableMultiplication(new IntegerNumber(2),baseFunction);
 		fileName="graph2"+trigFun+"x";
 		break;
 	case 2: // 2 sin 2x, 2 cos 2x
 		derivedFunction=new UnprintableMultiplication(new IntegerNumber(2), 
 			new SmartFunction("\\"+trigFun, new UnprintableMultiplication(new IntegerNumber(2), x)));
 		fileName="graph2"+trigFun+"2x";
 		break;	
    case 3: // 2 sin 0.5x, 2 cos 0.5x
 		derivedFunction=new UnprintableMultiplication(new IntegerNumber(2), 
 			new SmartFunction("\\"+trigFun, new FractionOp(x, new IntegerNumber(2))));
 		fileName="graph2"+trigFun+"0,5x";
 		break;						
 	case 4: // 0.5 sin x, 0.5 cos x
 		derivedFunction=new UnprintableMultiplication(new FractionOp(new IntegerNumber(1), new IntegerNumber(2)), 
 			baseFunction);
 		fileName="graph0,5"+trigFun+"x";
 		break;	
 	case 5: // 0.5 sin 2x, 0.5 cos 2x
 		derivedFunction=new UnprintableMultiplication(new FractionOp(new IntegerNumber(1), new IntegerNumber(2)), 
 			new SmartFunction("\\"+trigFun, new UnprintableMultiplication(new IntegerNumber(2), x)));
 		fileName="graph0,5"+trigFun+"2x";
 		break;						
 	case 6: // 0.5 sin 0.5x, 0.5 cos 0.5x
 		derivedFunction=new UnprintableMultiplication(new FractionOp(new IntegerNumber(1), new IntegerNumber(2)), 
 			new SmartFunction("\\"+trigFun, new FractionOp (x,new IntegerNumber(2))));
 		fileName="graph0,5"+trigFun+"0,5x";
 		break;	
 	case 7: // sin 2x, cos 2x
 		derivedFunction=new SmartFunction("\\"+trigFun, new UnprintableMultiplication(new IntegerNumber(2), x));
 		fileName="graph"+trigFun+"2x";
 		break;	
 	case 8: // sin x/2, cos x/2
 		derivedFunction=new SmartFunction("\\"+trigFun, new FractionOp(x, new IntegerNumber(2)));
 		fileName="graph"+trigFun+"0,5x";
 		break;	
 	case 9: // -2sin x, -2 cos x
 		derivedFunction=new UnprintableMultiplication(new IntegerNumber(-2),new SmartFunction("\\"+trigFun, x));
 		fileName="graph-2"+trigFun+"x";
 		break;		
 	case 10: // sin -2x, cos -2x
 		derivedFunction=new SmartFunction("\\"+trigFun, new UnprintableMultiplication(new IntegerNumber(-2),x));
 		fileName="graph"+trigFun+"-2x";
 		break;			
 	case 11: // 2 sin -x, 2 cos -x
 		derivedFunction=new UnprintableMultiplication(new IntegerNumber(2),
 			new SmartFunction("\\"+trigFun, new UnaryMinus(x)));
 		fileName="graph2"+trigFun+"-x";
 		break;																			
 	case 12: //  - sin 2x, - cos 2x
 		derivedFunction=new UnaryMinus(
 			new SmartFunction("\\"+trigFun, new UnprintableMultiplication(new IntegerNumber(2),x)));
 		fileName="graph-"+trigFun+"2x";
 		break;	
 	case 13: // sin -x, cos -x
 		derivedFunction=new SmartFunction("\\"+trigFun, new UnaryMinus(x));
 		fileName="graph"+trigFun+"-x";
 		break;											
 } 
 } //generate			
 } 
