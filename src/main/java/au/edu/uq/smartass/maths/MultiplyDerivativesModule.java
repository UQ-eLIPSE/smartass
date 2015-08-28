/**
 * 
 * This file is part of SmartAss and describes class MultiplyDerivativesModule for 
 * questions with a derivative of multiplication of functions
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
 * Class MultiplyDerivativesModule for 
 * questions with a derivative of multiplication of functions
 * 
 */
public class MultiplyDerivativesModule extends UVDerivativesModule {

	/**
	 * @param engine	an instance of smartass Engine
	 */
	public MultiplyDerivativesModule(Engine engine) {
		super(engine);

		question = new UnprintableMultiplication(
				u = new Addition(f[0].toMathsOp(), new IntegerNumber(c[0])),
				v = new Addition(f[1].toMathsOp(), new IntegerNumber(c[1])));
		solution.add(
				new Addition(
						new Multiplication(fs[0].toMathsOp(), v),
						new Multiplication(u, fs[1].toMathsOp() )) );
		solution.add(new Addition(
				new Brackets(new Addition(
						MathsUtils.multiplyConstToPower(fs[0].cons*f[1].cons, vx, fs[0].param+f[1].param),
						MathsUtils.multiplyConstToPower(fs[0].cons*c[1], vx, fs[0].param))),
				new Brackets(new Addition(
						MathsUtils.multiplyConstToPower(fs[1].cons*f[0].cons,vx, fs[1].param+f[0].param),
						MathsUtils.multiplyConstToPower(fs[1].cons*c[0],vx, fs[1].param)))) );
		MathsOp inner;
		if(fs[0].param==fs[1].param)
			inner = MathsUtils.multiplyConstToPower(
						fs[1].cons*c[0] + fs[0].cons*c[1], vx, fs[1].param);
		else if(fs[0].param>=fs[1].param)
			inner =	new Addition(
					MathsUtils.multiplyConstToPower(fs[0].cons*c[1], vx, fs[0].param),
					MathsUtils.multiplyConstToPower(fs[1].cons*c[0], vx, fs[1].param));
		else
			inner =	new Addition(
					MathsUtils.multiplyConstToPower(fs[1].cons*c[0], vx, fs[1].param),
					MathsUtils.multiplyConstToPower(fs[0].cons*c[1], vx, fs[0].param));
			
 		solution.add(
				new Addition(
						MathsUtils.multiplyConstToPower(
								fs[1].cons*f[0].cons + fs[0].cons*f[1].cons, 
								vx, fs[1].param+f[0].param),
						inner));
	}
}
