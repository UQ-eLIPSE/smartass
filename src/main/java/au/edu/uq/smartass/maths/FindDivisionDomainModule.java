package au.edu.uq.smartass.maths;

import java.util.Vector;

import au.edu.uq.smartass.auxiliary.RandomChoice;
import au.edu.uq.smartass.engine.Engine;

public class FindDivisionDomainModule extends MathsModule {
	final int MAX_NUM = 12;
	private final String ALL_NUMBERS="(-\\infty,\\infty)";
	//	0 - sqrt, 1 - mul, 2 - abs, 3 - sqr
	int optype = RandomChoice.randInt(0,3);
	int params[] = {
		RandomChoice.randInt(1, MAX_NUM)*RandomChoice.randSign(),	
		RandomChoice.randInt(1, MAX_NUM)*RandomChoice.randSign(),	
		RandomChoice.randInt(1, MAX_NUM)*RandomChoice.randSign()	
	};
	MathsOp ops[] = new MathsOp[3];
	MathsOp question;
	String answeri, answerc, answeri_r, answerc_r;
	final String[] varnames = {"x","z","w"};
    Variable vx = MathsUtils.createRandomVar(varnames);
	Vector<String> sol_d = new Vector<String>();
	Vector<String> sol_r = new Vector<String>();

	public FindDivisionDomainModule(Engine engine) {
		super(engine);
		Vector <MathsOp> sqrtOps;
		//Compose question MathsOp...
		switch (optype) {
		case 0:
			question = new Sqrt(vx);
			break;
		case 1:
			question = MathsUtils.multiplyVarToConst(params[0], vx);
			break;
		case 2:
			question = new AbsOp(vx);
			break;
		case 3:
			question = new Power(vx, new IntegerNumber(2));
		}
		ops[0] = question;
		if(RandomChoice.randInt(0, 1)==0)
			question = new Addition(question, new IntegerNumber(params[1]));
		else
			question = new Addition(new IntegerNumber(params[1]), question);
		ops[1] = question;
		question = ops[2] = new FractionOp(new IntegerNumber(params[2]), question);
		
		//Compose solution...
		//Domain:
		//Step 1 - division
		sol_d.add("\\mbox{denominator of a fraction cannot be 0, so } " + ops[1].toString() + "\\ne 0");
		//Step 2 - addition
		sol_d.add("\\mbox{so } " + //+ ((params[1]<0)?("adding "+(-params[1])):("subtracting "+(params[1])))+"} , " + 
				 ops[0].toString() + "\\ne " + Integer.toString(-params[1]));
		//Step 3 - inner op
		//	0 - sqrt, 1 - mul, 2 - abs, 3 - sqr
		switch (optype) {
		case 0:
			if(params[1]>0) {
				answeri = " [0,\\infty) ";
				answerc = "0 \\le "+vx.toString();
			//	sol_d.add("\\mbox{we can only take the square root of positive number or 0, so}"+answerc;
			} else {
				//sol_d.add("\\mbox{we can only take the square root of positive number or 0, so}");
				answeri = " [0," + Integer.toString(params[1]*params[1]) + ") \\cup (" + 
					Integer.toString(params[1]*params[1]) + ",\\infty) ";
				//answerc = "0 \\le " + vx.toString() + " < " + Integer.toString(params[1]*params[1]) + ", " + 
				//	Integer.toString(params[1]*params[1]) + " < " + vx.toString() + " < \\infty";
				answerc = vx.toString()+"\\ne"+Integer.toString(params[1]*params[1]) + "\\mbox{ and }"  + 
						"0 \\le " + vx.toString(); 				
			}
			sol_d.add("\\mbox{we can only take the square root of positive number or 0, so }"+answerc);
			break;
		case 1:
			MathsOp fr = composeFrac(-params[1], params[0]);
			answeri = "(-\\infty," + fr.toString() + ") \\cup (" + fr.toString() +  ",\\infty)";
			//answerc = "-\\infty < " + vx.toString() + " < " + fr.toString() + ", " + 
			//	fr.toString() + " < " + vx.toString() + " < \\infty ";
			answerc = vx.toString() + " \\ne " + fr.toString();
			if (params[0]!=1)
			sol_d.add("\\mbox{so }" + answerc);  
			break;
		case 2:
			if(params[1]>0) {
				answeri = ALL_NUMBERS;
				answerc = "-\\infty < " + vx.toString() + " < \\infty";
				sol_d.add("\\mbox{we can find the absolute value of any number. It will always be positive or 0}"); 
			} else {
				answeri = "(-\\infty," + Integer.toString(params[1]) + ") \\cup (" + 
					Integer.toString(params[1]) + "," + Integer.toString(-params[1]) + ") \\cup (" + 
					Integer.toString(-params[1]) + ",\\infty)";
				answerc = vx.toString() + " \\ne " + (new UnaryPlusMinus(new IntegerNumber(Math.abs(params[1])))).toString();
				sol_d.add("\\mbox{we can find the absolute value of any number, it will always give as a positive or 0, so }" + answerc); 
			}
			break;
		case 3:
			if(params[1]>0) {
				answeri = ALL_NUMBERS;
				answerc ="-\\infty < " + vx.toString() + " < \\infty";
				sol_d.add("\\mbox{we can square any number and result will always be a positive number or 0}");
			} else {
				String simplifySurd="";
				sqrtOps=composeSqrt(-params[1]);
				if (sqrtOps.size()==2)
					simplifySurd=vx.toString()+"\\ne"+(sqrtOps.get(0))+" \\mbox{, so }";
				answeri = "(-\\infty,-" + sqrtOps.lastElement().toString() + ") \\cup (-" + 
				sqrtOps.lastElement().toString() + "," + sqrtOps.lastElement().toString() + ") \\cup (" + 
					sqrtOps.lastElement().toString() + ",\\infty)";
				answerc = vx.toString() + " \\ne " + (new UnaryPlusMinus(sqrtOps.lastElement())).toString();
				sol_d.add("\\mbox{we can square any number and result will always be a positive number or 0, so }"+simplifySurd + answerc);	
				}
				
		}
		

		//Range:
		//Step 1 - iner op
		//	0 - sqrt, 1 - mul, 2 - abs, 3 - sqr
		switch (optype) {
		case 0:
			sol_r.add("\\mbox{square root is always positive or 0, so } 0\\le " + ops[0].toString()); //square root
			break;
		case 1:
			sol_r.add("\\mbox{there are no squares, square roots or absolute value signs} ");// + params[0]+"\\mbox{ , } -\\infty < " + ops[0].toString() + " < \\infty" );
			break;
		case 2:
			sol_r.add("\\mbox{absolute value is always positive or 0, so } 0\\le " + ops[0].toString());
			break;
		case 3:
			sol_r.add("\\mbox{squaring always gives a positive or 0, so } 0\\le " + ops[0].toString());
		}

		//Step 2 - addition, step 3 - fraction
		//String adds = "\\mbox{"+ ((params[1]>0)?("adding "+(params[1])):("subtracting "+(-params[1])))+"} , ";
		//String fracs = "\\mbox{fraction can be 0 only if numerator is 0, so } ";
		boolean posDenom=false;
	//	if (!((params[1]>0)&&(optype!=1)))
			sol_r.add("\\mbox{fraction can be 0 only if its numerator is 0 (which is not the case), denominator cannot be 0}");
		if(optype==1) {	//inner op is the multiplication 
		//	sol_r.add(adds + "-\\infty < " + ops[1].toString() + " < \\infty" );
		//	sol_r.add(fracs + "-\\infty < " + ops[2].toString() + " < 0, 0 < " + ops[2].toString() + " < \\infty" );
			answeri_r = "(-\\infty,0) \\cup (0,\\infty)";
		} else { //inner op is one of sqrt, abs or sqr, they all have range >=0
			sol_r.add("\\mbox{so }"+Integer.toString(params[1]) + " \\le " + ops[1].toString()+((params[1]>0)? "":
				 " \\mbox{ and }"+ ops[1].toString()+"\\ne 0"));
			MathsOp fr = composeFrac(params[2], params[1]);
			if(params[1]>0)
				if(params[2]>0) {
				//	sol_r.add("\\mbox{so }0 < " + ops[2].toString() + " \\le " + fr.toString() );
					answeri_r = "(0," + fr.toString() +"]";
				} else {
				//	sol_r.add("\\mbox{so }" +  fr.toString() + " \\le " + ops[2].toString() + " < 0");
					answeri_r = " [" +  fr.toString()	+ ",0)";
				}
			else {
				if(params[2]>0) {
				//	sol_r.add("\\mbox{so }" + 
				//			ops[2].toString() + " \\le "  + fr.toString() + " \\mbox{ or } 0 < " + ops[2].toString());
					answeri_r = "(-\\infty," + fr.toString() + ") \\cup (0,\\infty)";
				} else {
				//	sol_r.add("\\mbox{so }" + 
				//			ops[2].toString() + " < 0 \\mbox{ or } " + fr.toString()+" \\le " + ops[2].toString());
					answeri_r = "(-\\infty,0) \\cup ["+fr.toString() + ",\\infty]";
				}
			}
		}
	}
	
	MathsOp composeFrac(int num, int denom) {
		MathsOp fr; 
		int hcf = HCFModule.hcf(Math.abs(num), Math.abs(denom));
		num = num / hcf;
		denom = denom / hcf;
		if(Math.abs(denom)==1)
			fr = new IntegerNumber(num * denom);
		else {
			fr = new FractionOp(
				new IntegerNumber(Math.abs(num)), 
				new IntegerNumber(Math.abs(denom)));
			if(denom*num<0)
				fr = new UnaryMinus(fr);
		}
		return fr;
	}
	
	//form solution for sqrt(num), num>0. if sqrt(num) is surd, will return 1 or 2 values, first - sqrt(num), second - k*sqrt(m)
	//or just sqrt(num) if we can't simplify it
	Vector <MathsOp>  composeSqrt(int num){
		Vector<MathsOp> res= new Vector<MathsOp>();
		if(Math.sqrt(num)==(int)Math.sqrt(num)){
					res.add(new IntegerNumber((int)Math.sqrt(num)));
		} else {
			res.add(new Sqrt(new IntegerNumber(num)));
			for (int i=num-1; i>1; i--)
				if ((((double)num/i)==(num/i)) &&(Math.sqrt(i)==(int)Math.sqrt(i))){
					res.add(new UnprintableMultiplication(new IntegerNumber((int)Math.sqrt(i)),new Sqrt(new IntegerNumber(num/i))));
					i=0;	
				}		
		}
		return res;
	}//composeSqrt

    /**
     * Composes section with given name
     * "varname", "question", "domain", "range", "solution_d"(for domain) and "solution_r" (for range)
     *  sections is recognized
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
		for(int i=0;i<sol_d.size()-1;i++)
			sol = sol + "\\item \\ensuremath{" + sol_d.get(i) + "}; \n";
		sol+="\\item \\ensuremath{"+sol_d.get(sol_d.size()-1)+"}. \n"+
			"\\end{itemize}";	
		return sol;
	}
	else if (name.equals("solution_r")) {
		String sol = "When evaluating the range, we need to keep in mind the following (starting with variable \\ensuremath{"+vx.toString()+"}): \n"+
			"\\begin{itemize} \n";
		for(int i=0;i<sol_r.size()-1;i++)
			sol = sol + "\\item \\ensuremath{" + sol_r.get(i) + "} ; \n";
		sol+="\\item \\ensuremath{"+sol_r.get(sol_r.size()-1)+"} . \n"+
			"\\end{itemize}";
		return sol;
	}
	else if (name.equals("solution"))
		return getSection("solution_d") + "\\\\" + getSection("solution_r");
	else if (name.equals("range"))
		return "\\ensuremath{" +  answeri_r +"}";
	else if (name.equals("domain"))
		return "\\ensuremath{" + answeri + "}";
	else if (name.equals("inequality_d"))
		return "\\ensuremath{"+answerc+"}";
	else if (name.equals("shortanswer"))
		return "Domain is \\ensuremath{" + answeri + "} \\\\ Range is \\ensuremath{" + answeri_r + "}";
	if (name.equals("anydomain")) {
		if (answeri.equals(ALL_NUMBERS))
			return "y";
		else 
			return "n";	
	}			
	return super.getSection(name);
    }
}
