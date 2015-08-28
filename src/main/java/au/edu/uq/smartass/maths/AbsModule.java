/**
 * @(#)AbsModule.java
 * 
 *
 * @author 
 * @version 1.00 2006/11/11
 */

package au.edu.uq.smartass.maths;

import au.edu.uq.smartass.engine.*;
import au.edu.uq.smartass.auxiliary.*;
import java.lang.Math;
import java.math.BigDecimal;

public class AbsModule extends MathsModule {
    public AbsModule(Engine engine) {
    	super(engine);
    	generate(); 
    }
	
	final int DEFAULT_MAX_NUMBER=50;
	final int DEFAULT_MIN_NUMBER=-50;
	MathsOp abs_op, abs_answer, abs_step;

	private void generate() {
		MathsOp inner_op=null;
		IntegerNumber int_op=null;
		int aa[]={1,1,1};
		int choice = RandomChoice.selectInt(new int[]{1,1,1});
		switch(choice) { 
			case 0: //int+int
				 
				int i1 = RandomChoice.randInt(DEFAULT_MIN_NUMBER, DEFAULT_MAX_NUMBER),
					i2 = RandomChoice.randInt(DEFAULT_MIN_NUMBER, DEFAULT_MAX_NUMBER);
				inner_op = new Addition(new IntegerNumber(i1), new IntegerNumber(i2)); 
				abs_answer = new IntegerNumber(Math.abs(i1+i2));
				abs_step = new AbsOp(new IntegerNumber(i1+i2));
				break;
			case 1: //decimal fraction
				String sel[] = RandomChoice.makeChoice("[-50.0..50.0]/1");
				BigDecimal bigd =  new BigDecimal(sel[0]);
				inner_op = new DecimalNumber(bigd);
				abs_answer = new DecimalNumber(bigd.abs());
				break;
			case 2: //int
				int_op = new IntegerNumber(RandomChoice.randInt(DEFAULT_MIN_NUMBER, DEFAULT_MAX_NUMBER));
				inner_op = int_op;
				abs_answer = new IntegerNumber(Math.abs(int_op.getInt()));
		}
		abs_op = new AbsOp(inner_op);
		if(RandomChoice.randInt(0,1)==1)
		{
			abs_op=new UnaryMinus(abs_op);
			abs_answer=new UnaryMinus(abs_answer);
			if(abs_step!=null)
				abs_step=new UnaryMinus(abs_step);
		}		
	}

	public String getSection(String name) {
		if(name.equals("question"))
			return "\\ensuremath{"+abs_op.toString()+"}";
		else if (name.equals("solution")) {
			if(abs_step!=null) 
				return "\\ensuremath{" + abs_op.toString() + " = " + abs_step.toString() + " = " + abs_answer.toString()+"}";
			else
				return "\\ensuremath{" + abs_op.toString() + " = " + abs_answer.toString()+"}";
		} else if (name.equals("shortanswer"))
			return "\\ensuremath{"+abs_answer.toString()+"}";
		return super.getSection(name);
	}
}