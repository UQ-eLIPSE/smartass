package au.edu.uq.smartass.maths;

import au.edu.uq.smartass.maths.*;
import au.edu.uq.smartass.engine.*;
import au.edu.uq.smartass.auxiliary.*;
import java.util.*;

public class BracketsModule extends MathsModule {
	BinaryOp op1[]={null, null}, op2[]={null, null}; 
	LinkedList<MathsOp> sol1, sol2;
	
	public BracketsModule(Engine engine) {
		super(engine);
		sol1 = new LinkedList<MathsOp>();
		sol2 = new LinkedList<MathsOp>();
		
		int ord1 = 0; //RandomChoice.randInt(0,1);
		int ord2 = (ord1+1) % 2;

		int nop1 = RandomChoice.randInt(2,3); //2 - multiplication, 3 - division
		int nop2; 
		if(nop1==3 && ord1==0) 
			//If an outer Op is division and inner Op is a second operand
			//then the brackets is applicable to all 4 arithmetic operator
			//0 - addition, 1 - subtraction
			nop2 = RandomChoice.randInt(0,3); 
		else
			//If an outer Op is multiplication or inner Op is a first operand
			//then brackets is applicable to Addition and Subtraction only
			nop2 = RandomChoice.randInt(0,1);
		
		int num1 = 0, num2 = 0, num3 = 0;
			//Use range [2;6] to avoid 1 as an Addition or Division operand in the brackets
			if(nop2>=2 || ord1==1)
			  num3 = RandomChoice.randInt(2,6); 
			else
			  num3 = RandomChoice.selectInt(new int[]{1,1,2,2,2,2,2}); 
		switch(nop2) {
			case 0: num2 = RandomChoice.randInt(1,5); 
				op2[1] = new Addition(new IntegerNumber(num2), new IntegerNumber(num3));
				op1[0] = new Addition(null, null);
			break;
			case 1: num2 = num3 + RandomChoice.randInt(1,5); 
				op2[1] = new Subtraction(new IntegerNumber(num2), new IntegerNumber(num3));
				op1[0] = new Subtraction(null, null);
			break;
			case 2: num2 = RandomChoice.randInt(1,5); 
				op2[1] = new Multiplication(new IntegerNumber(num2), new IntegerNumber(num3));
				op1[0] = new Multiplication(null, null);
			break;
			case 3: num2 = num3 * RandomChoice.randInt(1,5); 
				op2[1] = new Division(new IntegerNumber(num2), new IntegerNumber(num3));
				op1[0] = new Division(null, null);
			break;
		}
		if(ord1==0) 
			op1[0].setOp(1, new IntegerNumber(num3));
		else
			op1[0].setOp(0, new IntegerNumber(num2));

		//The first Op is Division
		if(nop1==3) {
			//The inner Op is a second operand
			if(ord1==0) {
				switch(nop2) {
					case 0: num1 = (num2+num3)*num2*RandomChoice.randInt(1,5); break;
					case 1: num1 = (num2-num3)*num2*RandomChoice.randInt(1,5); break;
					case 2: num1 = num2*num3*RandomChoice.randInt(1,5); break;
					case 3: num1 = num2*num3*RandomChoice.randInt(1,5); break;
				}
				op2[0] = new Division(new IntegerNumber(num1), op2[1]);
				op1[0].setOp(0,op1[1] = new Division(new IntegerNumber(num1),new IntegerNumber(num2)));
			} else {
			//The inner Op is a first operand
				num1 = RandomChoice.randInt(2,6);
				((IntegerNumber)op2[1].getOp(0)).setInt(num2*num1);
				((IntegerNumber)op2[1].getOp(1)).setInt(num3*num1);
				op2[0] = new Division(op2[1],new IntegerNumber(num1));
				op1[0].setOp(1,op1[1] = new Division(new IntegerNumber(num3*num1),new IntegerNumber(num1)));
				((IntegerNumber)op1[0].getOp(0)).setInt(num2*num1);
			}
		} else 	{
			num1 = RandomChoice.randInt(2,6);
			if(ord1==0) {
				op2[0] = new Multiplication(new IntegerNumber(num1), op2[1]);
				op1[0].setOp(0,op1[1]=new Multiplication(new IntegerNumber(num1),new IntegerNumber(num2)));
			}
			else {
				op2[0] = new Multiplication(op2[1], new IntegerNumber(num1));
				op1[0].setOp(1,op1[1]=new Multiplication(new IntegerNumber(num3),new IntegerNumber(num1)));
			}
		}

		//generate solution
		BinaryOp step = (BinaryOp)op1[0].clone();
		sol1.add((BinaryOp)op1[0].clone());
		step.setOp(ord1, new IntegerNumber((int)((FloatCalculable)op1[1]).calculate()));
		sol1.add((BinaryOp)step.clone());
		sol1.add(new IntegerNumber((int)((FloatCalculable)op1[0]).calculate()));

		step = (BinaryOp)op2[0].clone();
		sol2.add((BinaryOp)op2[0].clone());
		step.setOp(ord2, new IntegerNumber((int)((FloatCalculable)op2[1]).calculate()));
		sol2.add((BinaryOp)step.clone());
		sol2.add(new IntegerNumber((int)((FloatCalculable)op2[0]).calculate()));
	}	

	public String getSection(String name) {
		if(name.equals("question"))
			return "\\ensuremath{"+op1[0].toString()+"} and \\ensuremath{"+op2[0].toString()+"}";
		else if (name.equals("solution"))
			return "\\ensuremath{"+composeSolution(sol1)+"} and \\ensuremath{"+composeSolution(sol2)+"}";
		else if (name.equals("shortanswer"))
			return "\\ensuremath{"+(int)((FloatCalculable)op1[0]).calculate()+"} and \\ensuremath{"+(int)((FloatCalculable)op2[0]).calculate()+"}";
		return super.getSection(name);
	}
	
	private String composeSolution(LinkedList <MathsOp>solution) {
		String res = solution.getFirst().toString();
		System.out.println("cs111");
		ListIterator it = solution.listIterator(0);
		it.next();
		while(it.hasNext()) 
			res = res + " = " + it.next().toString();
		return res;
	}
	
	
}
