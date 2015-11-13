/**
 * This file is part of SmartAss and describes class MathsModule - parent to MathsModules -
 * modules , generating questions and solutions  
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

import au.edu.uq.smartass.engine.*;

public class MathsModule {
	protected Engine engine;

	public MathsModule(Engine engine)
	{
		this.engine = engine;
	}

	public MathsModule(Engine engine, String[] params)
	{
		this(engine); 
	}

	public String printQuestion() {
		return getSection("question");
	}

	public String printSolution() {
		return getSection("solution");
	}
	public String printShortAnswer() {
		return getSection("shortanswer");
	}

     /**
     * Composes section with given name
     * Usually name is something like "question", "answer" or "solution"
     * but may differ from Module to Module
     * 
     * @param name section name
     **/
	public String getSection(String name) {
		if (name.equals("shortanswer"))
			return getSection("solution");
		return "Section "+name+" not found!";
	}

	//This method should be called to generate all specific parts of question
//	abstract public void generate(String param);
}
