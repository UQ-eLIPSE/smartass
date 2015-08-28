/**
 * 
 */
package au.edu.uq.smartass.maths;

/**
 * @author nik
 *
 *  Two additional functions: -2 - a/b*x^(n/m), 5 - a * sqrt(x), 6 - a / (b * sqrt(x)) (e.g. a/b * x^(1/2) )   
 */
public class DerivateFunctionsEx extends DerivateFunctions {
	int denom, pow_denom;

	/**
	 * @param func
	 * @param cons
	 * @param vx
	 */
	public DerivateFunctionsEx(int func, int cons, MathsOp vx) {
		super(func, cons, vx);
		if(func==5)
			initSqrt(1);
		else if(func==6)
			initSqrt(-1);
	}

	/**
	 * @param func
	 * @param cons
	 * @param param
	 * @param vx
	 */
	public DerivateFunctionsEx(int func, int cons, int param, MathsOp vx) {
		super(func, cons, param, vx);
		if(func==5)
			initSqrt(1);
		else if(func==6)
			initSqrt(-1);
	}

	/**
	 * @param func
	 * @param cons
	 * @param param
	 * @param denom
	 * @param vx
	 */
	public DerivateFunctionsEx(int func, int cons, int param, int denom, MathsOp vx) {
		super(func, cons, param, vx);
		if(func==5)
			initSqrt(1);
		else if(func==6)
			initSqrt(-1);
		this.denom = denom;
	}
	
	protected void initSqrt(int nom) {
		param = nom;
		pow_denom = 2;
		denom = 1;
	}

	/*
	 * @param qn	if we have to transform question before start a solution,
	 * 				we shell tell toMathsOp a step in this transfoemation
	 * 
	 * @returns 	MathsOp representetion of DerivativesFunctions
	 */

	MathsOp toMathsOp(int qn) {
		MathsOp fop;
		switch (func) {
		case 6:
			if(qn==0)
				return new FractionOp(new IntegerNumber(param), 
					MathsUtils.multiplyVarToConst(denom, new Sqrt(vx)));
			else {
				fop = new Power(vx, new UnaryMinus(new FractionOp(new IntegerNumber(1), new IntegerNumber(2))));
				if(denom!=1) {
					MathsOp fr = new FractionOp(new IntegerNumber(cons), new IntegerNumber(denom));
					fr.setLocalTex(new String[]{"\\frac"});
					fop = new UnprintableMultiplication(fr,	fop);
				} else
					fop = MathsUtils.multiplyVarToConst(param, fop);
			}
			return fop;
		case 5: 
			if(qn==0)
				return MathsUtils.multiplyVarToConst(cons, new Sqrt(vx));
			else {
				MathsOp fr = new FractionOp(new IntegerNumber(1), new IntegerNumber(2));
				fr.setLocalTex(new String[]{"\\frac"});
				fop = new Power(vx, fr);
				if(cons==1)
					return fop;
				else
					if(cons==-1)
						return new UnaryMinus(fop);
					else
						return	MathsUtils.multiplyVarToConst(cons, fop);
			}
		default: //inherited from superclass
			return super.toMathsOp(qn);
		}
	}
	
	DerivateFunctions derivate() {
		switch (func) {
		case 5:
			int hcf = HCFModule.hcf(Math.abs(cons), 2);
			return new DerivateFunctionsEx(6, cons/hcf, 1, 2/hcf, vx);
		}	
		return super.derivate();
	}

	MathsOp[] solve() {
		MathsOp fr;
		switch (func) {
		case 5:
			MathsOp[] s = null;
			s = new MathsOp[3];
			//step 1
			fr = new FractionOp(new IntegerNumber(1), new IntegerNumber(2));
			fr.setLocalTex(new String[]{"\\frac"});
			s[0] = new Power(vx, new Subtraction(fr, new IntegerNumber(1)));
			if(Math.abs(cons)!=1) 
				s[0] =	new Multiplication(new IntegerNumber(Math.abs(cons)), s[0]); 
			s[0] = 	new Multiplication(
						new FractionOp(new IntegerNumber(1), new IntegerNumber(2)),
						s[0]);
			if(cons<0)
				s[0] = new UnaryMinus(s[0]);

			//step 2
			int hcf = HCFModule.hcf(Math.abs(cons), 2);
			s[1] = new Power(vx, new UnaryMinus(fr = new FractionOp(new IntegerNumber(1), new IntegerNumber(2))));
			fr.setLocalTex(new String[]{"\\frac"});
			if(hcf==1) {
				fr = new FractionOp(new IntegerNumber(Math.abs(cons)), new IntegerNumber(2));
				fr.setLocalTex(new String[]{"\\frac"});
				s[1] = new UnprintableMultiplication(fr, s[1]);
				if(cons<0)
					s[1] = new UnaryMinus(s[1]);
			} else
				s[1] = MathsUtils.multiplyVarToConst(cons/2, s[1]);
			
			//step 3
			s[2] = new Sqrt(vx);
			if(hcf==1)
				s[2] = MathsUtils.multiplyVarToConst(2, s[2]);
			s[2] = new FractionOp(new IntegerNumber(Math.abs(cons)/hcf), s[2]);
			if(cons<0)
				s[2] =  new UnaryMinus(s[2]);
			return s;
		}
		return super.solve();
	}
	
	int getQuestionStepsNum() {
		if(func==5)  
			return 2;
		else
			return super.getQuestionStepsNum();
	}
}
