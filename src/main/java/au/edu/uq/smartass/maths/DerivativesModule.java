/** @(#)DerivativesModule.java
 *
 * This file is part of SmartAss and describes class DerivativesModule for 
 * questions with a derivative of different functions
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
 *
 */

package au.edu.uq.smartass.maths;

import java.util.Vector;

import au.edu.uq.smartass.auxiliary.RandomChoice;
import au.edu.uq.smartass.engine.Engine;

/**
 * Class DerivativesModule for 
 * questions with a derivative of different functions
 *
 */
public class DerivativesModule extends MathsModule {
	final int MAX_PARTS = 4;
	final int MAX_NUM = 8;
	final int[] levelMaxParts = {2, 3, MAX_PARTS, 2, 2, MAX_PARTS};
	
	int num_parts, level;
	Variable vx = new Variable("x");
	MathsOp question, q1;
	Vector<MathsOp> solution = new Vector<MathsOp>();

	/**
	 * @param engine	an Engine instance
	 */
	public DerivativesModule(Engine engine) {
		super(engine);
		
		num_parts = RandomChoice.randInt(1, MAX_PARTS);
		level = 6;
		generate();
	}

	/**
	 * @param engine
	 * @param params
	 */
	public DerivativesModule(Engine engine, String[] params) {
		super(engine, params);
		
		if(params.length>0)
			level = Integer.parseInt(params[0]);
		if(level<1 || level>6)
			level = 6; //incorrect level, use default value 6
		num_parts = RandomChoice.randInt(1, levelMaxParts[level-1]); 
		generate();
	}
	
	protected void generate() {
		DerivateFunctions f[];
		switch(level) {
		case 1:
			f = genQuestion(level, new int[]{0, 0}, new int[]{1, 1}, new int[]{1, 0}, null);
			break;
		case 2:
			f = genQuestion(level, new int[]{0, 0, 0}, new int[]{1, 1, 1}, new int[]{2, 1, 0}, new boolean[]{true});
			break;
		case 3:
			int[] fc = new int[RandomChoice.randInt(1, MAX_PARTS)];
			int[] fn = new int[fc.length];
			for(int i=0; i<fc.length; i++) {
				fn[i] = MAX_PARTS;
				fc[i] = 0;
			}
			f = genQuestion(level, fc, fn, null, null);
			break;
		case 4:
			f = genQuestion(level, new int[]{1, 2}, new int[]{1, 1}, null, null);
			break;
		case 5:
			f = genQuestion(level, new int[]{3, 4}, new int[]{1, 1}, null, null);
			break;
		default: //6
			f = genQuestion(level, new int[]{0, 1, 2, 3, 4, 5}, 
					new int[]{MAX_PARTS, MAX_PARTS, 1, 1, 1, 1, 1}, null, null);
		}
		num_parts = f.length;
		
		int max_q_num = 1;
		int max_a_num = 1;
		for(int i=0;i<f.length; i++) { 
			if(max_q_num<f[i].getQuestionStepsNum())
				max_q_num = f[i].getQuestionStepsNum();
			if(max_a_num<f[i].getAnswerStepsNum())
				max_a_num = f[i].getAnswerStepsNum();
		}

		question = f[0].toMathsOp(); 
		for(int i=1;i<num_parts;i++) 
			question = new Addition(question, f[i].toMathsOp());
		question = new Equality(new Variable("y"), question);
		
		if(max_q_num==2) {
			q1 = f[0].toMathsOp(1);
			for(int i=1;i<num_parts;i++) 
				q1 = new Addition(q1, f[i].toMathsOp(1));
			q1 = new Equality(new Variable("y"), q1);
		}

		MathsOp[][] answ = new MathsOp[num_parts][];
		int max_steps = 0;
		for(int i=0;i<num_parts;i++) 
			if((answ[i] = f[i].solve()).length>max_steps)
				max_steps = answ[i].length;
		for(int i=0;i<max_steps;i++) {
			MathsOp sol;
			if(i<answ[0].length)
				sol = answ[0][i];
			else
				sol = answ[0][answ[0].length-1];
			for(int j=1;j<num_parts;j++)
				if(i<answ[j].length)
					sol = MathsUtils.addTwoTermsNoZeros(sol, answ[j][i]);
				else
					sol = MathsUtils.addTwoTermsNoZeros(sol, answ[j][answ[j].length-1]);
			solution.add( sol);
		}
	}
	
	/** 
	 * generate question for givel level of complexity 
	 * 
	 * @param	level		complexity level
	 * @param 	fcodes		DerivateFunctions codes
	 * @param	fnumber		for each code there is a maximal number of times that function can appear in assigment
	 * @param	params		(maximal) param for DerivateFunction. used only for a*x^n function<br>
	 * 						leave set to 0 if param can be any number<br>
	 * 						safe to leave params empty, if there is no a*x^n function code in fcodes<br>
	 * @param	rq_parts	each position of this array describes if corresponding fcodes element is required or no
	 * 						safely leave parameter this null or shrink number of its elements to the last required function position  	 
	 */
	
	protected DerivateFunctions [] genQuestion(int level, int fcodes[], int fnumber[], int params[], boolean[] rq_parts) {
		int nparts = 0;
		for(int i=0;i<fcodes.length;i++) 
			nparts += fnumber[i]; 
		if(levelMaxParts[level-1]<nparts)
			nparts = levelMaxParts[level-1];
		int rq_parts_num = 0;
		if(rq_parts!=null)
			for(int i=0; i<rq_parts.length; i++)
				if(rq_parts[i]) rq_parts_num++;
		nparts = RandomChoice.randInt((rq_parts_num==0)?1:rq_parts_num, nparts);
		DerivateFunctions f[] = new DerivateFunctions[nparts];
		
		for(int i=0;i<nparts;i++) {
			int pos;
			if(rq_parts_num>0) {
				for(pos=0;i<fcodes.length && !rq_parts[pos];i++);
				rq_parts[pos] = false;
				rq_parts_num--;
			} else
				while(fnumber[pos=RandomChoice.randInt(0, fcodes.length-1)]==0);
			fnumber[pos]--;
			int der_f = fcodes[pos];
			int der_par = 1;  
			int der_cons = RandomChoice.randInt(1, MAX_NUM)*RandomChoice.randSign();

			if(der_f<=0) {
				boolean no_der_f = true;
				while(no_der_f) {
					no_der_f = false;
					if(params!=null)
						der_par = params[pos];
					else {
						der_par = RandomChoice.randInt(1, MAX_NUM)*RandomChoice.randSign();
						for(int j=0;j<i;j++)
							if(f[j].func+der_f<=0 &&
									(f[j].func==der_f && f[j].param==der_par
									|| f[j].func!=der_f && f[j].param==-der_par)) {
								no_der_f = true; 
								break;
							}
					}
				}
//				if(der_par!=0)
//					der_cons = der_cons * der_par;
			}
			
			if(der_f==5) 
				f[i] = new DerivateFunctionsEx(der_f, der_cons, der_par, vx);
			else
				f[i] = new DerivateFunctions(der_f, der_cons, der_par, vx);
		}
		return f;
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
			return composeSolution();
		else if(name.equals("shortanswer"))
			 return "\\ensuremath{y' = " + solution.lastElement().toString() + "}";
		else return super.getSection(name);	 
	}
	
	private String composeSolution() {
		String res = "\\ensuremath{" + question.toString() + "}";
		if(q1!=null)
			res = res + ", so \\ensuremath{" + q1.toString() + "}";
		res = res + ", so  \\begin{align*} y' &= " + 
				solution.get(0).toString();
		for(int i=1;i<solution.size();i++)
			res = res + "  \\\\  &= " + solution.get(i).toString();
		return res + "\\end{align*}";
	}
}

