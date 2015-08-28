package au.edu.uq.smartass.maths;

import au.edu.uq.smartass.maths.MathsOp;

public class IntegerNumber extends MathsOp  implements FloatCalculable{
	private int num;

	public IntegerNumber(int num) {
		setInt(num);
	}
	
	public IntegerNumber(Integer num) {
		setInt(num.intValue());
	}
	
	public IntegerNumber(int low, int high) {
		super();
		//here will be call to RandomChoice(low, high)?
	}	
		
	public IntegerNumber(String range) {
		super();
		//here will be call to RandomChoice(range)
	}	
		
	public String toString() {
		return  new Integer(num).toString();
	}
	
	public int getInt() {return num;}
	
	public void setInt(int num) {
		this.num = num;
	}
	
	public double calculate() {
		return num;
	}
}
