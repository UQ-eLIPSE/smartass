/**
 * This file is part of SmartAss and describes class FindDomainModule for 
 * question "Find domain and/or range for a number of math operations (square root, fraction etc)
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

import java.math.BigDecimal;
import java.util.Vector;

import au.edu.uq.smartass.auxiliary.RandomChoice;
import au.edu.uq.smartass.engine.Engine;

/**
 * Class FindDomainModule <br> 
 * Find domain and/or range for a number of math operations (square root, fraction, etc)
 * 
 * This class has a number of restriction (mostly not because it is impossible to avoid them,
 * but due requirements to create good looking results and efforts/result value)<br>
 *
 * 1) Variable op (e.g. "x") should be a deepest op in equation <br>
 * 2) Addition allowed only at outer or at deepest level (but one level up to Variable) op.
 *    This is because putting it in the middle can lead to non-integer numbers in intervals
 *    that is hard to handle and display (though Interval ops store their margins as {@link BigDecimal}
 *    and can be adopted to operate with decimal frations)<br>
 * 2.1/2) With regard to restriction (2) domain and range calculation assumes that
 *    multiplication and division does not scale intervals (works pretty good if interval borders are 0 or infinities)<br>
 * 3) This class is designed to include each op only once in equation,
 *    so number of its levels can not be bigger than the number of op types, e.g. more than 5.<br>
 *    Less works good.
 *     
 */
public class FindDomainModule extends MathsModule {
	final int MAX_NUM = 10;
	final int OP_NUM = 3;
	MathsOp question;
	CompositeInterval domain, range;
	Vector<String> sol_d = new Vector<String>();
	Vector<String> sol_r = new Vector<String>();
    boolean rootOrAbs=false;
    final String[] varnames = {"x","z","w"};
    Variable vx = MathsUtils.createRandomVar(varnames);
	/**
	 * @param engine
	 */
	public FindDomainModule(Engine engine) {
		super(engine);
		int[] optypes = new int[OP_NUM];
		MathsOp[] ops = new MathsOp[OP_NUM +1];
		optypes[0] = RandomChoice.randInt(1, 6); //1 - sqrt, 2 - frac, 3 - mul, 4 - abs, 5 - sqr, 6 - add
		for(int i=1;i<optypes.length;i++) 
			while(optypes[i]==0) {
				if(i==optypes.length-1)
					optypes[i] = RandomChoice.randInt(1, 6);
				else
					optypes[i] = RandomChoice.randInt(1, 5);
				for(int j=0;j<i;j++)
					if(optypes[j]==optypes[i]) {
						optypes[i] = 0;
						break;
					}
			}

//		optypes = new int[]{4,3,1,5}; ////DEBUG!!!
		
		int[] coef = new int[optypes.length];
		coef[0] = RandomChoice.randInt(1, MAX_NUM)*RandomChoice.randSign();
		for(int i=1;i<optypes.length-1;i++)
		{
			coef[i] = RandomChoice.randInt(1, MAX_NUM);
			if(RandomChoice.randInt(0, 1)==0 && (optypes[i+1]!=1 || (optypes[i-1]!=4 && optypes[i-1]!=6)))
				coef[i] = - coef[i];
		}
		coef[optypes.length-1] = RandomChoice.randInt(1, MAX_NUM)*RandomChoice.randSign();

		//compose question using optypes and constants from coef
		question = vx;
		for(int i=0;i<optypes.length;i++)
			question = genOp(optypes[i], coef[i], ops[i] = question);
		ops[OP_NUM] = question;
		
		CompositeInterval inter = new CompositeInterval();
		for(int i=optypes.length-1;i>=0;i--) {
			if ((optypes[i]==1) || (optypes[i]==4))
				rootOrAbs=true;
			switch (optypes[i]) {
			case 1:
				inter.subtract(new SingleInterval(SingleInterval.NEGATIVE_INF.intValue(),0,false, false));
				sol_d.add("\\mbox{we can only take the square root of positive numbers or 0, so } " +inter.asComparison(ops[i]));
				break;
			case 2:
				inter.subtract(new SingleInterval(0, 0, true, true));
				if(coef[i]<0)
					inter.mirror();
				sol_d.add("\\mbox{denominator of a fraction cannot be 0, so } " + inter.asComparison(ops[i]));
				break;
			case 3:
				if(coef[i]<0)
					inter.mirror();
			//	sol_d.add("\\mbox{dividing by }"+coef[i]+"\\mbox{ , }" + inter.asComparison(ops[i])); //inter.toString() 
				break;
			case 4:
			case 5:
				inter.subtract(new SingleInterval(SingleInterval.NEGATIVE_INF.intValue(),0,false,false));
				inter.add(((CompositeInterval)inter.clone()).mirror());
				if(optypes[i]==5)
					sol_d.add("\\mbox{we can square any number}"); // + inter.asComparison(ops[i]));
				else
					sol_d.add("\\mbox{we can find the absolute value of any number}");// + inter.asComparison(ops[i]));
				break;
			case 6:
				inter.shift(new BigDecimal(-coef[i]));
				if (!((inter.intervals.size()==1) && (inter.intervals.get(0).l.compareTo(SingleInterval.NEGATIVE_INF)==0) &&
					(inter.intervals.get(0).r.compareTo(SingleInterval.POSITIVE_INF)==0)))	
				sol_d.add("\\mbox{so }"+inter.asComparison(ops[i]));		
				//sol_d.add("\\mbox{"+ ((coef[i]<0)?("adding "+(-coef[i])):("subtracting "+(coef[i])))+"} , " + inter.asComparison(ops[i]));
                								
				break;
			}
		}
		domain = inter;

		range = new CompositeInterval();
		for(int i=0;i<optypes.length;i++) {
			switch (optypes[i]) {
			case 1:
				range.subtract(new SingleInterval(SingleInterval.NEGATIVE_INF.intValue(),0,false, false));
				sol_r.add("\\mbox{square root is always positive or 0, so }"  + range.asComparison(ops[i+1])); //square root
				break;
			case 2:
				range.subtract(new SingleInterval(0, 0, true, true));
				if(coef[i]<0) {
					range.mirror();
					sol_r.add( ((i>0)?"\\mbox{negative numerator usually reverse the inequality, and also this ":
						"\\mbox{numerator is not 0, ")+"fraction can't be 0, so }" + range.asComparison(ops[i+1]));  //fraction
				} else
					sol_r.add("\\mbox{fraction can be 0 only if numerator is 0, so }"  + range.asComparison(ops[i+1]));
				break;
			case 3:
				if(coef[i]<0) {
					range.mirror();
					if (!((range.intervals.size()==1) && (range.intervals.get(0).l.compareTo(SingleInterval.NEGATIVE_INF)==0) &&
					(range.intervals.get(0).r.compareTo(SingleInterval.POSITIVE_INF)==0)))	
			 		 	sol_r.add("\\mbox{multiplying by a negative number usually reverses the inequality }\\mbox{, so }"  + range.asComparison(ops[i+1]));
				//	sol_r.add("\\mbox{multiplying by } "+coef[i]+((i>0)?"\\mbox{usually reverse the inequality, ":
				//		"\\mbox{")+", so }"  + range.asComparison(ops[i+1]));
				};
				//} else 
					//sol_r.add("\\mbox{multiplying by } "+coef[i]+"\\mbox{ , }" + range.asComparison(ops[i+1]));	
				break;
			case 4:
			case 5:
				range.add(((CompositeInterval)range.clone()).mirror());
				range.subtract(new SingleInterval(SingleInterval.NEGATIVE_INF.intValue(),0,false, false));
				if(optypes[i]==5)
					sol_r.add("\\mbox{squaring always gives a positive or 0, so }" + range.asComparison(ops[i+1]));
				else
					sol_r.add("\\mbox{absolute value is always positive or 0, so }" + range.asComparison(ops[i+1]));
				break;
			case 6:
				range.shift(new BigDecimal(coef[i]));
				if (!((range.intervals.size()==1) && (range.intervals.get(0).l.compareTo(SingleInterval.NEGATIVE_INF)==0) &&
					(range.intervals.get(0).r.compareTo(SingleInterval.POSITIVE_INF)==0)))	
						sol_r.add("\\mbox{so }"+ range.asComparison(ops[i+1]));
				//sol_r.add("\\mbox{"+ ((coef[i]>0)?("adding "+coef[i]):("subtracting "+(-coef[i])))+"} , " + range.asComparison(ops[i+1]));
				break;
			}

		}
	}

	MathsOp genOp(int optype, int coef, MathsOp prev_op) {
		switch (optype) {
		case 1:
			return new Sqrt(prev_op);
		case 2:
			return new FractionOp(new IntegerNumber(coef), prev_op);
		case 3:	
			return MathsUtils.multiplyVarToConst(coef, prev_op);
		case 4:
			return new AbsOp(prev_op);
		case 5:
			return new Power(prev_op, new IntegerNumber(2));
		case 6:
			if(RandomChoice.randInt(0, 1)==0)
				return new Addition(new IntegerNumber(coef), prev_op);
			else
				return new Addition(prev_op, new IntegerNumber(coef));
		}
		return prev_op;
	}
	
    /**
     * Composes section with given name
     * "varname", "question", "domain", "range" sections is recognized
     * 
     * @param name	section name
     **/
    public String getSection(String name) {
	if(name.equals("question")) 
		return "\\ensuremath{" + question.toString() + "}";
	else if (name.equals("varname"))
		return "\\ensuremath{" + vx.toString() + "}";
	else if (name.equals("solution_d")) {
		String sol = "When determining the domain of this function, we need to keep in mind the following: \n"+
			"\\begin{itemize} \n";
		if (!rootOrAbs)
			sol+="\\item there are no square roots or absolute value signs; \n"; 	
		for(int i=0;i<sol_d.size()-1;i++)
			sol = sol + "\\item \\ensuremath{" + sol_d.get(i) + "}; \n";
		sol+="\\item \\ensuremath{"+sol_d.get(sol_d.size()-1)+"}. \n"+
			"\\end{itemize}";	
		return sol;
	} else if (name.equals("inequality_d"))
		return "\\ensuremath{"+domain.asComparison(vx).toString()+"}";
	else if (name.equals("solution_r")) {
		String sol = "When evaluating the range, we need to keep in mind the following (starting with variable \\ensuremath{"+vx.toString()+")}: \n"+
			"\\begin{itemize} \n";
		if (!rootOrAbs)
			sol+="\\item there are no square roots or absolute value signs; \n"; 		
		for(int i=0;i<sol_r.size()-1;i++)
			sol = sol + "\\item \\ensuremath{" + sol_r.get(i) + "}; \n";
			sol+="\\item \\ensuremath{"+sol_r.get(sol_r.size()-1)+"}. \n"+
				"\\end{itemize}";
		return sol;
	} //else if (name.equals("inequality_r"))
//		return "\\ensuremath{"+range.asComparison(vx).toString()+"}";
	else if (name.equals("solution"))
		return getSection("solution_d") + "\\\\" + getSection("solution_r");
	else if (name.equals("range"))
		return "\\ensuremath{" + range.toString() + "}";
	else if (name.equals("domain"))
		return "\\ensuremath{" + domain.toString() + "}";
		
	if (name.equals("anyrange")) {
		if ( (range.intervals.size()==1) && (range.intervals.get(0).l.compareTo(SingleInterval.NEGATIVE_INF)==0) &&
		(range.intervals.get(0).r.compareTo(SingleInterval.POSITIVE_INF)==0))
			return "y";
		else 
			return "n";	
	}
	if (name.equals("anydomain")) {
		if ( (domain.intervals.size()==1) && (domain.intervals.get(0).l.compareTo(SingleInterval.NEGATIVE_INF)==0) &&
		(domain.intervals.get(0).r.compareTo(SingleInterval.POSITIVE_INF)==0))
			return "y";
		else 
			return "n";	
	}		
	return super.getSection(name);
    }
}
