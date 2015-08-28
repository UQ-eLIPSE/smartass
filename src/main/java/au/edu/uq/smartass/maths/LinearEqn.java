/* @(#) LinearEqn.java
 *
 * This file is part of SmartAss and describes MathsOp LinearEqn for typesetting
 * linear equations with given coefficients and variables.
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

/**
 * Class LinearEqn
 * composes equation from the given coefficients 
 * in the format 
 * leftSide[0][0]vs[leftSide[1][0]]+leftSide[0][1]vs[leftSide[1][1]]+
 * leftSide[0][2](vs[leftSide[1][2]==-1)=
 * rightSide[0][0]vs[rightSide[1][0]]+rightSide[0][1]vs[rightSide[1][1]]+
 * rightSide[0][2](vs[rightSide[1][2]==-1)
 *
 * left(right)Side[0]-array of coefficients;
 * left(right)Side[1]-array of numbers of variables (or -1)- no variable, free 
 * coefficient
 * left(right)Side[2]-places of the terms in the equation
 * 
 * The places of the terms are determined by rightSide[2](rightSide[3]) array of 
 * integers, permutation of numbers (0, 1, 2, 3, ... k), where k - number
 * of terms in the rightSide of equation 
 * Same for leftSide.
 *
 * @param int[][] leftSide   coefficients
 * @param int[][] rightSide  coefficients
 * @param Variable[] vs      variables 
 *
 * @return MathsOp equation
 */
	
public class LinearEqn extends MathsOp {
	   int [][]left;
	   int [][]right;
	   Variable [] variables;
	    //constructor
	    
        public LinearEqn(int[][] leftSide, int [][] rightSide, Variable[] vs ){
         setLeft(leftSide);
         setRight(rightSide);
         setVars(vs);	
        } //constructor
        
        
        public void setLeft (int[][] leftSide){
        	 left=new int [leftSide.length][];
             for (int i=0; i<left.length; i++){
             	left[i]=new int[leftSide[i].length];
             	for (int k=0; k<left[i].length; k++)
             		left[i][k]=leftSide[i][k]; 					
        	}
        }
        
        public  void setRight (int[][] rightSide){
         	 right=new int [rightSide.length][];
             for (int i=0; i<right.length; i++){
             	right[i]=new int[rightSide[i].length];
             	for (int k=0; k<right[i].length; k++)
             		right[i][k]=rightSide[i][k]; 	
        	}
        }
        
        public void setVars (Variable[] vs){
        	variables=new Variable[vs.length];
        	for (int i=0; i<vs.length; i++)
        		variables[i]=(Variable)(vs[i].clone());
        }
        
        public int[][] getLeft (){
        	return left;
        }
        
        public int[][] getRight(){
        	return right;
        }
        
        public Variable[] getVars(){
        	return variables;
        }
        
 /**
 * toString()
 * typesets composes equation from the given coefficients 
 * in the format 
 * leftSide[0][0]vs[leftSide[1][0]]+leftSide[0][1]vs[leftSide[1][1]]+
 * leftSide[0][2](vs[leftSide[1][2]==-1)=
 * rightSide[0][0]vs[rightSide[1][0]]+rightSide[0][1]vs[rightSide[1][1]]+
 * rightSide[0][2](vs[rightSide[1][2]==-1)
 *
 * left(right)Side[0]-array of coefficients;
 * left(right)Side[1]-array of numbers of variables (or -1)- no variable, free 
 * coefficient
 * left(right)Side[2]-places of the terms in the equation
 * 
 * The places of the terms are determined by rightSide[2] array of 
 * integers, permutation of numbers (0, 1, 2, 3, ... k), where k - number
 * of terms in the rightSide of equation 
 * Same for leftSide.
 *
 */      
        
       public String toString(){
	  
	    int size=left[0].length;
	      
 		MathsOp [] solSteps=new MathsOp[size];
 		MathsOp leftOp;

 		for (int i=0; i<size; i++){
 			if (left[1][i]==-1)
 				solSteps[i]=new IntegerNumber(left[0][i]);
 			else
 				solSteps[i]=MathsUtils.multiplyVarToConst(left[0][i],variables[left[1][i]]);
 		}	
 	    leftOp=solSteps[left[2][0]];
 
 	    for (int i=1; i<size; i++)
 	    	leftOp=MathsUtils.addTwoTermsNoZeros(leftOp,solSteps[left[2][i]]);
 	 	
 	    size=right[0].length;
 	    MathsOp rightOp;
 	    solSteps=new MathsOp[size];
 		for (int i=0; i<size; i++){
 			if (right[1][i]==-1)
 				solSteps[i]=new IntegerNumber(right[0][i]);
 			else
 				solSteps[i]=MathsUtils.multiplyVarToConst(right[0][i],variables[right[1][i]]);
 		}	
 	    rightOp=solSteps[right[2][0]];
 	    for (int i=1; i<size; i++)
 	    	rightOp=MathsUtils.addTwoTermsNoZeros(rightOp,solSteps[right[2][i]]);
 	            	        
 	    return (new Equality(leftOp, rightOp)).toString(); 	
 	   
 	
        } //toString()   		 
        
 	public Object clone() {
		LinearEqn res = (LinearEqn) super.clone();
		if(left!=null)
			res.setLeft(left);

		if (right!=null)
			res.setRight(right);	        
		
		if (variables!=null)
			res.setVars(variables);
			
		return (Object)res;
	}//clone 	
		
 } //linearEqn
