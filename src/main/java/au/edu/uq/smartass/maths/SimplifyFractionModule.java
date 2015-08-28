/**
 *
 * This file is part of SmartAss and describes class SimplifyFracModule for  
 * question "simplify expression that consists of fractions combined by 
 * various arithmetical operations
 *  
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
 */
package au.edu.uq.smartass.maths;

import java.util.Vector;

import au.edu.uq.smartass.auxiliary.RandomChoice;
import au.edu.uq.smartass.engine.Engine;

/**
 * class SimplifyFracModule for  
 * question "simplify expression that consists of fractions combined by 
 * various arithmetical operations
 * 
 */
public class SimplifyFractionModule extends MathsModule {
	final int OP_ADDITION = 0;
    final int OP_SUBTRACTION = 1;
    final int OP_MULTIPLICATION = 2;
    final int OP_DIVISION = 3;

    final int NUM_FRACTIONS = 4;
	final int MAX_NUM = 10; 
	final int LIMIT = 60;

	FractionOp fr[] = new FractionOp[NUM_FRACTIONS];
	BinaryOp question, step;
	Vector<MathsOp> solution = new Vector<MathsOp>();

	/**
	 * @param engine an instance of SmartAss Engine
	 */
	public SimplifyFractionModule(Engine engine) {
		super(engine);

		//Get random integers and create fractions
		int n[][] = new int[NUM_FRACTIONS][];
		int opcodes[] = new int[NUM_FRACTIONS-1];
		for(int i=0;i<NUM_FRACTIONS-1;i++)
			opcodes[i] = RandomChoice.randInt(0, 3);
		
		n[0] = new int[]{RandomChoice.randInt(-MAX_NUM,MAX_NUM), RandomChoice.randInt(1,MAX_NUM)*RandomChoice.randSign()};
		fr[0] = createFraction(n[0]);
		
		n[1] = genGoodFraction(n[0], opcodes[0]);
		fr[1] = createFraction(n[1]);
		
		BinaryOp op[] = new BinaryOp[3];
		//0-Addition, 1-Subtraction, 2-Mustiplication, 3-Division
		op[0] = createBiOp(fr[0], fr[1], opcodes[0]);
			
		int[] r1 = calcFraction(n[0], n[1], opcodes[0]);
		n[2] = genGoodFraction(r1, opcodes[1]);
		int oporder = RandomChoice.randInt(1,2);
		if(opcodes[0]<opcodes[1] && oporder==1) {
			int[] t = genGoodFractions(n[2], opcodes[2]);
			fr[2] = createFraction(n[2] = new int[]{t[0], t[1]});
			fr[3] = createFraction(n[3] = new int[]{t[2], t[3]});
			op[1] = createBiOp(fr[2], fr[3], opcodes[2]);
			op[2] = createBiOp(op[0], op[1], opcodes[1]);
		} else {
			fr[2] = createFraction(n[2]);
			op[1] = createBiOp(op[0], fr[2], opcodes[1]);
			n[3] = calcFraction(r1, n[2], opcodes[1]);
			n[3] = genGoodFraction(n[3], opcodes[2]);
			fr[3] = createFraction(n[3]);
			op[2] = createBiOp(op[1], fr[3], opcodes[2]);
		}

		step = op[NUM_FRACTIONS-2];
		question = (BinaryOp) step.clone();
		if(!(step.getOp(0) instanceof FractionOp))
			simplify(step, 0);
		if(!(step.getOp(1) instanceof FractionOp))
			simplify(step, 1);
		Vector<MathsOp> part = simplify(step);
		for(int i=0;i<part.size();i++) {
			if(part.get(i) instanceof BinaryOp) 
				formatZeroFraction((BinaryOp)part.get(i));
			if(!solution.get(solution.size()-1).toString().equals(part.get(i).toString()))
				solution.add(part.get(i));
		}
	}

	int[] calcFraction(int[] fr1, int[] fr2, int optype) {
		int hcf;
		int[] fr;
		if(fr1[1]<0) { 
			fr1[1] = -fr1[1]; fr1[0] = -fr1[0]; }
		if(fr2[1]<0) {
			fr2[1] = -fr2[1]; fr2[0] = -fr2[0]; }
		if(optype==OP_ADDITION || optype==OP_SUBTRACTION) { 
			hcf = HCFModule.hcf(fr1[1],fr2[1]);
			if(optype==OP_ADDITION)
				fr = new int[]{fr1[0]*(fr2[1]/hcf)+fr2[0]*(fr1[1]/hcf), fr1[1]*(fr2[1]/hcf)};
			else
				fr = new int[]{fr1[0]*(fr2[1]/hcf)-fr2[0]*(fr1[1]/hcf), fr1[1]*(fr2[1]/hcf)};
			if((hcf = HCFModule.hcf(Math.abs(fr[0]), Math.abs(fr[1])))>1) {
				fr[0] = fr[0] / hcf;
				fr[1] = fr[1] / hcf;
			}
			return fr;
		}
		//otherwise optype is OP_DIVISION or OP_MULTIPLICATION
		if(optype==OP_MULTIPLICATION)
			fr = new int[]{fr1[0]*fr2[0],fr1[1]*fr2[1]};
		else
			fr = new int[]{fr1[0]*fr2[1],fr1[1]*fr2[0]};
		hcf = HCFModule.hcf(Math.abs(fr[0]), Math.abs(fr[1]));
		return new int[]{fr[0]/hcf, fr[1]/hcf};
	}

	FractionOp createFraction(int[] fr) {
		return new FractionOp(
				new IntegerNumber(fr[0]),
				new IntegerNumber(fr[1]));
	}
	
	int[] genGoodFraction(int fr [], int opcode) {
		switch(opcode) {
		case 0: return FractionsGeneration.generateSecondAddend(fr[0], fr[1], LIMIT, LIMIT);
		case 2: return FractionsGeneration.generateMultiplier(fr[0], fr[1], LIMIT, LIMIT);
		case 3: return FractionsGeneration.generateDivisor(fr[0], fr[1], LIMIT, LIMIT);
		}
		return FractionsGeneration.generateSubtrahend(fr[0], fr[1], LIMIT, LIMIT);
	}
	
	int[] genGoodFractions(int fr [], int opcode) {
		switch(opcode) {
		case 0: return FractionsGeneration.generateFractionsToAdd(fr[0], fr[1], LIMIT, LIMIT);
		case 2: return FractionsGeneration.generateFractionsToMultiply(fr[0], fr[1], LIMIT, LIMIT);
		case 3: return FractionsGeneration.generateFractionsToDivide(fr[0], fr[1], LIMIT, LIMIT);
		}
		return FractionsGeneration.generateFractionsToSubtract(fr[0], fr[1], LIMIT, LIMIT);
	}

	private BinaryOp simplify(BinaryOp op, int nop) {
		if(!(((BinaryOp)op.getOp(nop)).getOp(0) instanceof FractionOp))
			simplify((BinaryOp)op.getOp(nop), 0);
		//here will be zero treating!
		if(!(((BinaryOp)op.getOp(nop)).getOp(1) instanceof FractionOp)) 
			simplify((BinaryOp)op.getOp(nop), 1);
		Vector<MathsOp> part = simplify((BinaryOp)op.getOp(nop));
		
		int noop = (nop + 1) % 2;
		MathsOp oop = op.getOp(noop);
		/*if(oop instanceof FractionOp && ((IntegerNumber)((FractionOp)oop).getOp(0)).getInt()==0)
			op.setOp(noop, new IntegerNumber(0));*/
		formatZeroFraction(op);
		formatZeroFraction(op.getOp(nop));
		
		for(int i=0;i<part.size()-1;i++) {
			op.setOp(nop, formatZeroFraction(part.get(i)));
//			System.out.println(i + step.toString());
			if(solution.size()==0) {
				if(!question.toString().equals(step.toString()))
					solution.add((MathsOp)step.clone());
			} else if(!solution.get(solution.size()-1).toString().equals(step.toString()))
				solution.add((MathsOp)step.clone());
		}
		op.setOp(noop, oop);
		MathsOp st = formatZeroFraction(part.get(part.size()-1)); 
		if(st instanceof FractionOp) {
			//solution.add((MathsOp)step.clone());
			op.setOp(nop, st);
		} else if(st instanceof IntegerNumber) {
			/*if(!(part.get(part.size()-2) instanceof FractionOp && (
					((IntegerNumber) ((BinaryOp)part.get(part.size()-2)).getOp(1)).getInt()==1
					||((IntegerNumber) ((BinaryOp)part.get(part.size()-2)).getOp(1)).getInt()==0)
					((IntegerNumber)st).getInt()==1
					||((IntegerNumber)st).getInt()==0
			))*/ {
				//solution.add((MathsOp)step.clone());
				op.setOp(nop, new FractionOp(st, new IntegerNumber(1)));
			}
		} else if(st instanceof UnaryMinus) {
			if(!(part.get(part.size()-2) instanceof FractionOp &&
					((BinaryOp)part.get(part.size()-2)).getOp(0) instanceof IntegerNumber &&
					((BinaryOp)part.get(part.size()-2)).getOp(1) instanceof IntegerNumber)) {
				//solution.add((MathsOp)step.clone());
				op.setOp(nop, new FractionOp(
						new IntegerNumber(-((IntegerNumber) ((BinaryOp) ((UnaryOp)st).getOp()).getOp(0)).getInt()), 
						((BinaryOp) ((UnaryOp)st).getOp()).getOp(1)));
			}
		}
		
		return op;
	}

	Vector<MathsOp> simplify(BinaryOp op) {
		Vector<MathsOp> part = null;
		
		if(op instanceof Addition) 
			part = FractionArithmetics.addFractions(
					(IntegerNumber)((BinaryOp)op.getOp(0)).getOp(0), 
					(IntegerNumber)((BinaryOp)op.getOp(0)).getOp(1), 
					(IntegerNumber)((BinaryOp)op.getOp(1)).getOp(0), 
					(IntegerNumber)((BinaryOp)op.getOp(1)).getOp(1));
		else if(op instanceof Subtraction) 
			part = FractionArithmetics.subtractFractions(
					(IntegerNumber)((BinaryOp)op.getOp(0)).getOp(0), 
					(IntegerNumber)((BinaryOp)op.getOp(0)).getOp(1), 
					(IntegerNumber)((BinaryOp)op.getOp(1)).getOp(0), 
					(IntegerNumber)((BinaryOp)op.getOp(1)).getOp(1));
		else if(op instanceof Multiplication) 
			part = FractionArithmetics.multiplyFractions(
					(IntegerNumber)((BinaryOp)op.getOp(0)).getOp(0), 
					(IntegerNumber)((BinaryOp)op.getOp(0)).getOp(1), 
					(IntegerNumber)((BinaryOp)op.getOp(1)).getOp(0), 
					(IntegerNumber)((BinaryOp)op.getOp(1)).getOp(1));
		else if(op instanceof Division) 
			part = FractionArithmetics.divideFractions(
					(IntegerNumber)((BinaryOp)op.getOp(0)).getOp(0), 
					(IntegerNumber)((BinaryOp)op.getOp(0)).getOp(1), 
					(IntegerNumber)((BinaryOp)op.getOp(1)).getOp(0), 
					(IntegerNumber)((BinaryOp)op.getOp(1)).getOp(1));
		return part;
	}
	
	MathsOp formatZeroFraction(MathsOp op) {
		if(op instanceof BinaryOp) {
			BinaryOp bop = (BinaryOp)op;
				if(bop.getOp(0) instanceof FractionOp
						&& ((FractionOp)bop.getOp(0)).getOp(0) instanceof IntegerNumber && ((IntegerNumber)((FractionOp)bop.getOp(0)).getOp(0)).getInt()==0
						&& ((FractionOp)bop.getOp(0)).getOp(1) instanceof IntegerNumber && ((IntegerNumber)((FractionOp)bop.getOp(0)).getOp(1)).getInt()==1)
					bop.setOp(0, new IntegerNumber(0));
				if(bop.getOp(1) instanceof FractionOp
						&& ((FractionOp)bop.getOp(1)).getOp(0) instanceof IntegerNumber && ((IntegerNumber)((FractionOp)bop.getOp(1)).getOp(0)).getInt()==0
						&& ((FractionOp)bop.getOp(1)).getOp(1) instanceof IntegerNumber && ((IntegerNumber)((FractionOp)bop.getOp(1)).getOp(1)).getInt()==1)
					bop.setOp(1, new IntegerNumber(0));
		}
		return op;
	}
	
	/**
	 * 
	 * @param arg1 	first parameter for construction of BinaryOp descendant  
	 * @param arg2	second parameter for construction of BinaryOp descendant
	 * @return
	 */
	static BinaryOp randomBiOp(MathsOp arg1, MathsOp arg2) {
		return createBiOp(arg1,arg2,RandomChoice.randInt(0,3));
	}
	
	/**
	 * 
	 * @param arg1 	first parameter for construction of BinaryOp descendant  
	 * @param arg2	second parameter for construction of BinaryOp descendant
	 * @param opcode	0-Addition, 1-Subtraction, 2-Mustiplication, 3-Division
	 * @return
	 */
	static BinaryOp createBiOp(MathsOp arg1, MathsOp arg2, int opcode) {
		switch(opcode) {
			case 0: return new Addition(arg1, arg2);
			case 1: return new Subtraction(arg1, arg2);
			case 2: return new Multiplication(arg1, arg2);
			case 3: return checkDivision(new Division(arg1, arg2));
		}
		return null;
	}
	
	/**
	 * If op is Division and its denominator is zero
	 * we have to find and correct the cause of zero appearance
	 */
	static BinaryOp checkDivision(BinaryOp op) {
		if((op instanceof Division) && ((FloatCalculable)op.getOp(1)).calculate()==0) {
			checkZero(op.getOp(1));
		}
		return op;
	}
	
	/**
	 * Check and correct different circuimstances when zero appears 
	 * as the result of expression evaluation
	 */
	static void checkZero(MathsOp op) {
		if(op instanceof Multiplication || op instanceof FractionOp)
			if( ((FloatCalculable)((BinaryOp)op).getOp(0)).calculate()==0)
				checkZero(((BinaryOp)op).getOp(0));
			else
				checkZero(((BinaryOp)op).getOp(1));
		else if(op instanceof Division) {
			checkZero(((BinaryOp)op).getOp(0));
		} else if(op instanceof Addition || op instanceof Subtraction) {
			IntegerNumber numop = findNumberOp(op);
			numop.setInt(numop.getInt()+1);
		} else if(op instanceof IntegerNumber) {
			IntegerNumber numop = (IntegerNumber)op;
			numop.setInt(numop.getInt()+1);
		}
	}

	static IntegerNumber findNumberOp(MathsOp op) {
		if(op instanceof IntegerNumber)
			return (IntegerNumber) op;
		else 
			if(op instanceof BinaryOp)
				return findNumberOp(((BinaryOp)op).getOp(0));
			else
				return findNumberOp(((UnaryOp)op).getOp());
	}

	/**
     * Composes section with given name
     * "question", "shortanswer", "solution" sections is recognized
     * 
     * @param name	section name
     **/
	public String getSection(String name) {
		if(name.equals("question"))
			return "\\ensuremath{"+question.toString()+"}";
		else if (name.equals("solution"))
			return "\\begin{align*}\\ensuremath{" +
				question + "&= " + composeSolution() +"}\\end{align*}";
		else if (name.equals("shortanswer"))
		{
			String lastStr=composeShortanswer(solution.lastElement());
			if (lastStr.length()==0)
			 return "\\ensuremath{"+solution.lastElement().toString()+"}";
			else return "\\ensuremath{"+lastStr+"}";
		}
				
			
		return super.getSection(name);
	}
	
	private String composeSolution() {
		String res = solution.get(0).toString();
		String lastStr;
		for(int i=1;i<solution.size();i++)
			res = res + "\\\\ &= " + solution.get(i).toString();
		lastStr=composeShortanswer(solution.lastElement());	
		if (lastStr.length()!=0)
			res+="\\\\ &="+lastStr; 	
		return res;
	}
	
	//working out shortanswer, returns "" if lastOp is already appropriately formatted
	private String composeShortanswer(MathsOp lastOp) {
		FractionOp fraction;
		String addition="";
		if (lastOp instanceof FractionOp){ 
				fraction=(FractionOp)(lastOp.clone());
				addition=FractionArithmetics.presentableFraction((IntegerNumber)fraction.getOp(0),(IntegerNumber)fraction.getOp(1));			
			if (addition.length()!=0)
				return addition;
			}	else {
				
			if ((lastOp instanceof UnaryMinus) && (((UnaryMinus)lastOp).getOp() instanceof FractionOp)) {
				fraction=(FractionOp)(((UnaryMinus)lastOp).getOp().clone());
				addition=FractionArithmetics.presentableFraction((IntegerNumber)fraction.getOp(0),(IntegerNumber)fraction.getOp(1));
				if (addition.length()!=0) {	
					if (addition.startsWith("-")) return addition.substring(1);
					else return "-"+addition;
				}	
			}
		}
		return addition;
	}
}
