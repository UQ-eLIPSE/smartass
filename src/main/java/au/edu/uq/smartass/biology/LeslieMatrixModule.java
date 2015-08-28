/* @(#)LeslieMatrixModule.java
 * 
 * This file is part of SmartAss and describes class LeslieMatrixModule for a
 * question on Leslie model. 
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
package au.edu.uq.smartass.biology;
import au.edu.uq.smartass.engine.*;
import au.edu.uq.smartass.auxiliary.*;
import au.edu.uq.smartass.maths.*;
import java.math.*;

/**
* Class LeslieMatrixModule describes the question on 
* Leslie model: Build the population on the time step t.   
*
* @version 1.0 15.08.2007
*/
public class LeslieMatrixModule extends MathsModule{
 private int number_of_ages=RandomChoice.randInt(2,4);
 private int generations;//=RandomChoice.randInt(2,6); //including first, 0 generation 
 private String [] limitsGenerations={"3","6"}; 
 private String [][] limitsSurvival= new String[number_of_ages-1][2];//probabilities of survival to the next age group
 private String [][] limitsFecundity=new String[number_of_ages][2];//fecundity of population in each age group 
 private String [][] limitsPopulation=new String[number_of_ages][2];//initial population
 
 private double[][] lm;
 private double[][][] population; //[generations][number_of_ages][1]
 
 private final int DEC_SCALE=1;
 private final RoundingMode DEC_ROUNDING=RoundingMode.HALF_UP; 
 
 Matrix lMatrix; 
 Matrix[] populVectors;
 Matrix[] workings; 

/**
* Constructor LeslieMatrixModule initialises the question
* with parameters passing.
* In case of "values" as a first parameter:
* @params  params[0] - string "values", indicates types of parameters passing,
*		   params[1] - number of age groups,
*          params[2] - number of generations,
*          params[from 3 to number number_of_ages+2] - the numbers of individuals in each age group in initial population,
*          params[from number_of_ages+3 to 2number_of_ages+2] - the fecundity of the corresponding age groups,
*          params[from 2number_of_ages+3 to 3number_of_ages+1] - the fraction of individuals that survives from the corresponding age group,   
* or
* @params  params[0] - string "limits", indicating that there are limits being passed
*		   params[1] - number of age groups,
*          parmas[2-3] - min and max limits for number of generations (including initial generation) required, 
*          params[from 4 to 2number_of_ages+3] - limits for numbers of individuals in each age group in initial population,
*		   params[from 2number_of_ages+4 to 4number_of_ages+3] - limits for fecundity of each age group,
*          params[from 4number_of_ages+4 to 6number_of_ages+1] - limits for the fraction of individuals that survives from the corresponding age group.
*/
 public LeslieMatrixModule(Engine engine, String[] params) 
	{
				super(engine);
				
				try{
			    if (params[0].equals("values")){		    	
			    	number_of_ages=Integer.parseInt(params[1]); 	
			    	if (params.length!=(3*number_of_ages+2))
			    		throw new IllegalArgumentException();			    	
			    	generations=Integer.parseInt(params[2]);			    				    		
					population=new double[generations][number_of_ages][1];					
					populVectors=new Matrix[generations];			
					lm=new double[number_of_ages][number_of_ages];		
					
					for (int i=0; i<number_of_ages; i++){
						population[0][i][0]=Double.parseDouble(params[3+i]);					
						lm[0][i]=Double.parseDouble(params[number_of_ages+3+i]);									
					}		
					for (int i=1; i<number_of_ages; i++) //survival rates
						lm[i][i-1]=Double.parseDouble(params[2*number_of_ages+2+i]);	 								
					lMatrix=MatrixAlgebra.makeDecimalMatrix(lm,DEC_SCALE, DEC_ROUNDING, false);		
					populVectors[0]=MatrixAlgebra.makeDecimalMatrix(population[0], DEC_SCALE, DEC_ROUNDING, false);		
					workings=new Matrix[generations-1];							
			    	for (int i=1; i<generations; i++){
			    		workings[i-1]=new Matrix(number_of_ages,1);
			    		population[i]=MatrixAlgebra.multiplyTwoDecimalMatrices(lm, population[i-1], workings[i-1], 
 																				DEC_SCALE, DEC_ROUNDING, false);
 						populVectors[i]=MatrixAlgebra.makeDecimalMatrix(population[i], DEC_SCALE, DEC_ROUNDING, false);															
			    	}										  
		
			    } else{
			    	if (params[0].equals("limits")){
			    	   number_of_ages=Integer.parseInt(params[1]);
			    	   if (params.length!=(6*number_of_ages+2))
			    		throw new IllegalArgumentException();	
			    			
			    	   limitsGenerations[0]=params[2]; limitsGenerations[1]=params[3]; 
			    	   for (int i=0; i<number_of_ages; i++){
			    	   	limitsPopulation[i][0]=params[4+2*i]; limitsPopulation[i][1]=params[5+2*i];
			    	   	limitsFecundity[i][0]=params[4+2*number_of_ages+2*i]; limitsFecundity[i][1]=params[5+2*number_of_ages+2*i];
			    	   }		    	   
			    	   for (int i=0; i<number_of_ages-1; i++) {
			    	   	limitsSurvival[i][0]=params[4+4*number_of_ages+2*i]; limitsSurvival[i][1]=params[5+4*number_of_ages+2*i]; 	
			    	   }	   
			    	 generate();  
			    	} else
			    		throw new IllegalArgumentException();      
				}	 
				}	catch (IllegalArgumentException e){
					System.out.println("IllegalArgumentException while processing parameters passed into LeslieMatrixModule");
					throw e;
				}
	} //constructor
 
/**
* Constructor LeslieMatrixModule initialises the question
*/
 public LeslieMatrixModule(Engine engine) 
	{
				super(engine);		
				switch(number_of_ages){
				    case 2: {
						 String [][]tls = {{"0.2","0.6"}}; //probabilities of survival to the next age group
						 String [][]tlf={{"1","3"},{"5","10"}}; //fecundity of population in each age group 
						 String [][]tlp={{"6","20"},{"2","5"}};
						 limitsSurvival=tls;//probabilities of survival to the next age group
		 				 limitsFecundity=tlf;//fecundity of population in each age group 
 						 limitsPopulation=tlp; 
				    	} 	
						break;
					case 4:{
						String [][]tls = {{"0.2","0.6"},{"0.6","0.9"},{"0.2","0.5"}}; //probabilities of survival to the next age group
						String [][]tlf={{"0","1"},{"3","8"},{"2","5"},{"0","1"}}; //fecundity of population in each age group 
						String [][]tlp={{"8","20"},{"1","5"},{"0","2"},{"0","1"}};
						limitsSurvival=tls;//probabilities of survival to the next age group
		 				limitsFecundity=tlf;//fecundity of population in each age group 
 						limitsPopulation=tlp;
						}
						break;
					case 3: {
						String [][]tls = {{"0.2","0.6"},{"0.6","0.9"}}; //probabilities of survival to the next age group
						String [][]tlf ={{"0","1"},{"3","8"},{"1","2"}}; //fecundity of population in each age group 
						String [][]tlp ={{"6","20"},{"0","5"},{"0","1"}};
						limitsSurvival=tls;//probabilities of survival to the next age group
		 				limitsFecundity=tlf;//fecundity of population in each age group 
 						limitsPopulation=tlp;					
					}
					break;	
				}							
				generate();            
	} //constructor
	
/**
 * getSection method typesets a question and solution 
 * @return a String containing Latex code for the section
 */
 public String getSection(String name) {

		if (name.equals("number_of_ages"))
			return String.valueOf(number_of_ages);
		if (name.equals("number_of_generations"))
			return String.valueOf(generations);
		if (name.equals("number_of_steps"))
			return String.valueOf(generations-1);
		if (name.equals("last_population_string")){
			String ins="group 1: "+populVectors[generations-1].getElement(0,0);
			for (int i=1; i<number_of_ages; i++)
				ins+=", group "+(i+1)+": "+populVectors[generations-1].getElement(i,0);
			return ins;	
		}
		if (name.equals("last_population_vector"))
			return populVectors[generations-1].toString();
		if (name.equals("fecundity_string")){
			String ins="for group 1: "+lMatrix.getElement(0,0);
			for (int i=1; i<number_of_ages; i++)
				ins+=", for group "+(i+1)+": "+lMatrix.getElement(0,i);
			return ins;		
		}			
		if (name.equals("survival_string")){
			String ins="for group 1: "+lMatrix.getElement(1,0);
			for (int i=1; i<number_of_ages-1; i++)
				ins+=", for group "+(i+1)+": "+lMatrix.getElement(i+1,i);
			return ins;		
		}				
		if (name.equals("initial_population_string")){
			String ins="group 1: "+populVectors[0].getElement(0,0);
			for (int i=1; i<number_of_ages; i++)
				ins+=", group "+(i+1)+": "+populVectors[0].getElement(i,0);
			return ins;	
		}
		if (name.equals("leslie_matrix"))
			return lMatrix.toString();	
		if (name.equals("working")){
			String sol="";
			for (int i=1; i<generations; i++){
				sol+="At time \\ensuremath{t="+i+"} the population \\ensuremath{P_{"+i+"}} is given by: \\\\[2mm] \\ensuremath{"+
					(new Equality
						(new Equality(new Multiplication(lMatrix, populVectors[i-1]),workings[i-1]),populVectors[i])).toString()+
							"} \\vspace{5mm} \\\\ \n";
				
			}	
			return sol;				
		}		
		if (name.startsWith("population")){
			try{
				int num=Integer.valueOf(name.substring(10)).intValue();
				if  ((num<populVectors.length) && (num>=0))
					return populVectors[num].toString();
			}
			catch (NumberFormatException e) {
			}
		}
		return super.getSection(name);
	}
	
// Generates the generations :)
 public void generate() {
 	
 				generations=Integer.parseInt(RandomChoice.makeChoice("["+limitsGenerations[0]+".."+limitsGenerations[1]+"]/1")[0]);	
				population=new double[generations][number_of_ages][1];
				populVectors=new Matrix[generations];
				lm=new double[number_of_ages][number_of_ages];
				try{
				for (int i=0; i<number_of_ages; i++){
					population[0][i][0]=
						Double.parseDouble(RandomChoice.makeChoice("["+limitsPopulation[i][0]+".."+limitsPopulation[i][1]+"]/1")[0]);	
					lm[0][i]=Double.parseDouble(RandomChoice.makeChoice("["+limitsFecundity[i][0]+".."+limitsFecundity[i][1]+"]/1")[0]);		
				}			
				for (int i=1; i<number_of_ages; i++)
					lm[i][i-1]=Double.parseDouble(RandomChoice.makeChoice("["+limitsSurvival[i-1][0]+".."+limitsSurvival[i-1][1]+"]/1")[0]);
				}	catch (IllegalArgumentException e){
					System.out.println("IllegalArgumentException while processing parameters passed into LeslieMatrixModule");
					throw e;
				}
								
				lMatrix=MatrixAlgebra.makeDecimalMatrix(lm,DEC_SCALE, DEC_ROUNDING, false);		
				populVectors[0]=MatrixAlgebra.makeDecimalMatrix(population[0], DEC_SCALE, DEC_ROUNDING, false);		
				workings=new Matrix[generations-1];	
			    for (int i=1; i<generations; i++){
			    	workings[i-1]=new Matrix(number_of_ages,1);
			    	population[i]=MatrixAlgebra.multiplyTwoDecimalMatrices(lm, population[i-1], workings[i-1], 
 																				DEC_SCALE, DEC_ROUNDING, false);
 					populVectors[i]=MatrixAlgebra.makeDecimalMatrix(population[i], DEC_SCALE, DEC_ROUNDING, false);															
			    }																		
						
	}//generate
	
 
 
 
} 