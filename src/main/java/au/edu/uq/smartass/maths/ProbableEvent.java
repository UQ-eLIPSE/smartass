/**
 * @(#)ProbableEvent.java
 *
 * This file is part of SmartAss and describes class ProbableEvent.
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
 * @version 1.00 2007/1/15
 */

package au.edu.uq.smartass.maths;

/**
* Class ProbableEvent describes MathsOp for simple operations
* on probabilities.   ProbableEvent has three properties:
* protected String clause;
* protected MathsOp probability;
* private String[] texString={"\\ensuremath{Prob}"}; and methods for 
* manipulation those properties.
*
*/

public class ProbableEvent extends MathsOp {
	protected String clause;
    protected MathsOp probability;
    private String[] texString={"\\ensuremath{Prob}"}; //default tex

/**
 * Constructor ProbableEvent, initialises clause.
 *
 * @param  clause_string	clause of the event 
 */  
    public ProbableEvent (String clause_string) {
    	super();
    	if (getTex()==null)
  			setTex(texString);
    	setClause(clause_string);
    }
    
 /**
 * Constructor ProbableEvent initialises clause and probability.
 *
 * @param  String clause_string	clause of  this probable event 
 * @param  MathsOp prob			probability of this probable event 
 */     
    public ProbableEvent (String clause_string, MathsOp prob){
    	super();
    	if (getTex()==null)
  			setTex(texString);
    	setClause(clause_string);
    	setProbability(prob);
    }
    
 /**
 * Method setClause set a clause of this probable event.
 * 
 * @param  String new_clause  new clause of this probable event 
 */         
    public void setClause(String new_clause) {
    	clause = new_clause;
    }

/**
 * Method getClause() returns a clause of this probable event.
 * 
 * @return  String  clause of this probable event 
 */     
    public String getClause() {
    	return clause;
    }
    
    public void setProbability(MathsOp prob){
    	probability=prob;
    }
    
/**
 * Method getProbability() returns a probability of this probable event.
 * 
 * @return  MathOp   probability of this probable event 
 */       
    public MathsOp getProbability(){
    	return probability; 
    }
    
/**
 * Method toString() returns string representation of this probable event.
 * Returned string depends on tex, set for ProbableEvent. By default -  
 * "\\ensuremath{Prob} + +LEFT_BRACKET+clause+RIGHT_BRACKET"
 * 
 * @return  String  string representation of this ProbableEvent  
 */     
	public String toString() { //careful - usage of tex-constant brackets
		return getTex()[0]+LEFT_BRACKET+clause+RIGHT_BRACKET; 
	}
	
/**
 * Method clone() returns a clone of this probable event.
 *
 * @return  Object   clone of this ProbableEvent  
 */     
    public Object clone() {
		ProbableEvent res = (ProbableEvent) super.clone();
		if(probability!=null)
			res.probability = (MathsOp) ((MathsOp)probability).clone();
		return res;
	} 
 
}