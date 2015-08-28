/**
 * This file is part of SmartAss and describes class TrigonometricDefiniteIntegraModulel 
 * that genarates questions with definite integral of  sum of terms a*f(x)  
 * where f(x) is one of sin x, cos x, x^n, e^x, ln x and x is fraction of pi (e.g pi, pi/2, etc)   
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

import au.edu.uq.smartass.auxiliary.RandomChoice;
import au.edu.uq.smartass.engine.Engine;

/**
 * Class TrigonometricDefiniteIntegraModulel 
 * that genarates questions with definite integral of  sum of terms a*f(x)  
 * where f(x) is one of sin x, cos x, x^n, e^x, ln x 
 * and x is fraction of pi (e.g pi, pi/2, etc)
 *    
 */
public class TrigonometricDefiniteIntegraModulel extends DefiniteIntegralModule {
	MathsOp pi = new Variable("\\pi");
	int[] pim = {-1, -1, 0, 1, 1},
	      pid = {1, 2, 1, 2, 1},
	      sin = {0, -1, 0, 1, 0},
	      cos = {-1, 0, 1, 0, -1};
	int lo_id, hi_id;
	boolean was_power = false;
	
	private class FHolder {
		int nom, denom, pipow;
		FHolder(int nom, int denom, int pipow) {
			this.nom = nom;
			this.denom = denom;
			this.pipow = pipow;
		}
		
		MathsOp toMathsOp() {
			MathsOp res;

			if(nom==0)
				return new IntegerNumber(0);

			int nm;
			if(nom<0 && denom!=1)
				nm = -nom;
			else 
				nm = nom;
			if(pipow>0) {
				res = pi;
				if(pipow>1)
					res = MathsUtils.multiplyConstToPower(nm, pi, pipow);
				else
					res = MathsUtils.multiplyVarToConst(nm, pi);
			} else
				res = new IntegerNumber(nm);
			if(denom!=1) { 
				res = new FractionOp(res, new IntegerNumber(denom));
				if(nom<0)
					res = new UnaryMinus(res);
			}
			return res;
		}
	}

	/**
	 * @param engine 	an instance of SmartAss Engine
	 * 
	 */
	public TrigonometricDefiniteIntegraModulel(Engine engine) {
		super(engine);

		lo_id = RandomChoice.randInt(0, pim.length-2);
		hi_id = RandomChoice.randInt(lo_id+1, pid.length-1);
		lo = composeLimit(lo_id);
		hi = composeLimit(hi_id);
		
		boolean was_trig = false;

		for(int i=0;i<MAX_PARTS;i++) {
			int param = RandomChoice.randInt(0, MAX_NUM/2); 
			int func = RandomChoice.randInt(0, 2);

			int a = RandomChoice.randInt(1, MAX_NUM)*RandomChoice.randSign();
			if(func==0 && param!=-1)
				a *= (param+1);
			
			was_power |= (func==0);
			was_trig |= (func!=0);
			f[i] = new DerivateFunctions(func, a, param, vx);
		}
		if(!was_trig)
			f[0].func = RandomChoice.randInt(1, 2);

		generate();
	}
	
	MathsOp composeLimit(int lid) {
		if(pim[lid]==0)
			return new IntegerNumber(0);
		MathsOp res = pi; 
		if(pid[lid]==2)
			res = new FractionOp(res, new IntegerNumber(2));
		if(pim[lid]<0)
			res = new UnaryMinus(res);
		return res;
	}
	
	protected void generate() {
		super.generate();
		
		MathsOp slo, shi;
		slo = fi[0].toMathsOp(lo);
		shi = fi[0].toMathsOp(hi);
		
		slo = calc(fi[0], lo_id);
		for(int i=1;i<MAX_PARTS;i++)
			slo = new Addition(slo, calc(fi[i], lo_id));
		shi = calc(fi[0], hi_id);
		for(int i=1;i<MAX_PARTS;i++)
			shi = new Addition(shi, calc(fi[i], hi_id));
		solution.add(answer = new Subtraction(new Brackets(shi), new Brackets(slo)));
		
		int rpos = 0, tpos = 0;
		FHolder[] rf = new FHolder[MAX_PARTS*2]; 

		for(int i=0;i<MAX_PARTS;i++) {
			if(fi[i].func>0 && rf[0]==null) {
				rf[0] = new FHolder(0, 1, 0);
				rpos = tpos = 1;
			}
			if(fi[i].func==1) 
				rf[0].nom += fi[i].cons * sin[hi_id];
			else if(fi[i].func==2) 
				rf[0].nom += fi[i].cons * cos[hi_id];
		}
		for(int i=0;i<MAX_PARTS;i++) {
			if(fi[i].func==1) 
				rf[0].nom -= fi[i].cons * sin[lo_id];
			else if(fi[i].func==2) 
				rf[0].nom -= fi[i].cons * cos[lo_id];
		}

		for(int i=0;i<MAX_PARTS;i++) 
			if(fi[i].func==0 && pim[hi_id]!=0) { 
				int nom = fi[i].cons * (int) Math.pow(pim[hi_id], fi[i].param);
				int denom = (int) Math.pow(pid[hi_id], fi[i].param);
				int hcf = HCFModule.hcf(Math.abs(nom), denom);
				if(hcf>1) {
					nom = nom / hcf;
					denom = denom / hcf;
				}
				int j;
				for(j=tpos; j<rpos; j++)
					if(rf[j].pipow==fi[i].param && rf[j].denom==denom) {
						rf[j].nom += nom;
						break;
					}
	
				if(j==rpos) 
					rf[rpos++] = new FHolder(nom, denom, fi[i].param);
			}
		for(int i=0;i<MAX_PARTS;i++) 
			if(fi[i].func==0 && pim[lo_id]!=0) { 
				int nom = - fi[i].cons * (int) Math.pow(pim[lo_id], fi[i].param);
				int denom = (int) Math.pow(pid[lo_id], fi[i].param);
				int hcf = HCFModule.hcf(Math.abs(nom), denom);
				if(hcf>1) {
					nom = nom / hcf;
					denom = denom / hcf;
				}
				int j;
				for(j=tpos; j<rpos; j++)
					if(rf[j].pipow==fi[i].param && rf[j].denom==denom) {
						rf[j].nom += nom;
						break;
					}
	
				if(j==rpos) 
					rf[rpos++] = new FHolder(nom, denom, fi[i].param);
			}
		
		if(rf[0].nom!=0 || rpos==1)
			answer = rf[0].toMathsOp();
		else
			answer = rf[tpos++].toMathsOp();
		for(int i=tpos; i<rpos; i++)
			answer = new Addition(answer, rf[i].toMathsOp());
		solution.add(answer);
	}

	protected MathsOp calc(DerivateFunctions f, int lid) {
		switch(f.func) {
		case 0:
			if(pim[lid]==0 || f.cons==0)
				return new IntegerNumber(0);
			else {
				int nom = f.cons * (int)Math.pow(pim[lid], f.param);
				int nm;
				if(nom<0)
					nm = - nom;
				else
					nm = nom;
				MathsOp res = MathsUtils.multiplyConstToPower(nm, pi, f.param);
				if(pid[lid]==2)
					res = new FractionOp(res, new IntegerNumber((int)Math.pow(2, f.param)));
				if(nom<0)
					res = new UnaryMinus(res);
				return res;
			}
		case 1:
			return new IntegerNumber(f.cons * sin[lid]);
		default:
			return new IntegerNumber(f.cons * cos[lid]);
		}
	}
}
