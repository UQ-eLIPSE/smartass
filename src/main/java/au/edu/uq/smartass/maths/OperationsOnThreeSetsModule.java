/* @(#)OperationsOnThreeSetsModule.java
 *
 * This file is part of SmartAss and describes class OperationsOnThreeSetsModule.
 * Operations used are intersection, union, set difference.
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
import au.edu.uq.smartass.engine.*;
import au.edu.uq.smartass.auxiliary.*;
import java.util.*;
import java.math.*;

/**
* Class OperationsOnThreeSetsModule describes the question on 
* sets intersection, union and set differences.   
*
* @version 1.0 26.01.2007
*/
public class OperationsOnThreeSetsModule extends MathsModule{
 private final int MIN_ELEMENT=-3;
 private final int MAX_ELEMENT=9;
 private final int MAX_SET_SIZE=10;
 
 MathsSet set[]=new MathsSet[3];
 private String[] questions=new String[9];
 private String[] solutions=new String[9];
 private String[] shortanswers=new String[9];
 private String interval;
 private int intervalNum=-1;
 private String question5diagram; 

 
/**
* Constructor OperationsOnThreeSetsModule initialises the question.
* 
*/
 public OperationsOnThreeSetsModule(Engine engine) 
	{
				super(engine);	
				this.generate();				
	} //constructor
/**
* Constructor OperationsOnThreeSetsModule initialises the question with parameters.
* @param      params  Sets to intersect in format {el, el, el ... el}{el, el, el ... el}{el, el, el ... el}
*					quotation marks are not allowed
* 
*/
 public OperationsOnThreeSetsModule(Engine engine, String[] params) 
	{
				super(engine, params);	
				this.generate(params);
	} //constructor

	
/**
 * getSection method typesets a question and solution 
 * @return a String containing Latex code for the section
 */
 public String getSection(String name) {
 	String section[]=new String[2];
		if(name.equals("name1")) {		
			return "\\ensuremath{"+set[0].getName()+" }";									
 		}	 	
 		if(name.equals("name2")) {		
			return "\\ensuremath{"+set[1].getName()+" }";													
 		}
 		if(name.equals("name3")){
 			return "\\ensuremath{"+set[2].getName()+" }";
 		}
 		if(name.equals("set1")) {		
			return "\\ensuremath{"+set[0].toString()+" }";									
 		}	 	
 		if(name.equals("set2")) {		
			return "\\ensuremath{"+set[1].toString()+" }";									
 		}	 				
 		if(name.equals("set3")) {		
			return "\\ensuremath{"+set[2].toString()+" }";									
 		}	
 			if(name.equals("set1")) {		
			return "\\ensuremath{"+set[0].toString()+" }";									
 		}	 	
 		if(name.equals("set2")) {		
			return "\\ensuremath{"+set[1].toString()+" }";									
 		}	 				
 		if(name.equals("set3")) {		
			return "\\ensuremath{"+set[2].toString()+" }";									
 		}	
 		if(name.equals("setrepresentation1")){
 			if (intervalNum==0)
 				return interval;
 			else return getSection("set1");
 		}
 		if(name.equals("setrepresentation2")){
 			if (intervalNum==1)
 				return interval;
 			else return getSection("set2");
 		}
 		if(name.equals("setrepresentation3")){
 			if (intervalNum==2)
 				return interval;
 			else return getSection("set3");
 		}	 		
 		
 // user is supposed to number questions from 1 to 9
 		section[0]=name.substring(0,name.length()-1);
 		section[1]=name.substring(name.length()-1); 
   	    try{   	    
 	    int num=Integer.valueOf(section[1]).intValue();
 	    if ((num<=questions.length) && (num>0)){ 	     
 			 if (section[0].equals("question"))
 		 		return questions[num-1];
 		 	 if (section[0].equals("solution"))
 		 	 	return solutions[num-1];
 		 	 if (section[0].equals("shortanswer"))
 		 	 	return shortanswers[num-1];
 		}
   	    }
   	    catch (NumberFormatException e){
   	    }
   	    
 		if (name.equals("question5diagram"))
 			return question5diagram;
 	
	
		return super.getSection(name);
	}
	
/**
 * Method generates two sets of random size
 * with random integers (from -3 to 9) as their elements. 
 * Max. size of set is 10 elements.
 */
 
// Generates sets
 protected void generate() {
 	            int size;
 	            int ind1, ind2, ind3;
 	           // String name1, name2, name3;
 	            String[] names={"A", "B", "C", "D", "E", "F", "G", "H"};
 	            String[] setsNames=new String[3];
 	            ind1=RandomChoice.randInt(0,7);
 	            setsNames[0]=names[ind1];
 	            ind2=(ind1+RandomChoice.randInt(1,7))%8;
 	            setsNames[1]=names[ind2];
 	            ind3=RandomChoice.randInt(0,7);
 	            while ((ind3==ind2) || (ind3==ind1))
 	            	ind3=(++ind3)%8;
 	            setsNames[2]=names[ind3];	 
  	
 	          	ind1=RandomChoice.randInt(0,4);  	          	        	 	         		
 	         	if (ind1<3)
 	         	
 	         		{
 	         		set[ind1]=new MathsSet(setsNames[ind1]);		
                	int leftNum, rightNum;
                	leftNum=RandomChoice.randInt(MIN_ELEMENT,MAX_ELEMENT-1);
                    rightNum=RandomChoice.randInt(leftNum, MAX_ELEMENT);
                    if ((rightNum-leftNum+1)>MAX_SET_SIZE)
                    	rightNum=leftNum+MAX_SET_SIZE-1;
                    boolean leftInclusive= (RandomChoice.randSign()==1) ? true: false;
                    boolean rightInclusive= (RandomChoice.randSign()==1) ? true: false;
                    Variable v=MathsUtils.createRandomVar();
                    interval="\\ensuremath{ \\{"+v.getName()+" \\hspace{1mm} \\vert \\hspace{1mm} "+v.getName()+"\\in \\mathbb{N}, ";
                    if (leftInclusive) interval+=leftNum+"\\leq "+v.getName();
                       else interval+=(leftNum-1)+"<"+v.getName();
                    if (rightInclusive) interval+=" \\leq "+rightNum;
                       else interval+="<"+(rightNum+1);
                    interval+=" \\} }";
        	        for (int i=leftNum; i<=rightNum; i++)
 	         			set[ind1].add(Integer.toString(i)); 	         			
 	         		intervalNum=ind1;		
 	         	} else
 	         	{
 	         		ind1=RandomChoice.randInt(0,2);
 	         		size=RandomChoice.randInt(1,MAX_SET_SIZE-1);	
 	         		set[ind1]=new MathsSet(setsNames[ind1]);		
 	         		for (int i=0; i<=size; i++)
 	         			set[ind1].add(Integer.toString(RandomChoice.randInt(MIN_ELEMENT, MAX_ELEMENT)));	
 	         	} 	 
 	         	ind2=(ind1+1)%3;
 	         	ind3=(ind1+2)%3; 			                   
 	         	size=RandomChoice.randInt(1,MAX_SET_SIZE-1);	
 	         	set[ind2]=new MathsSet(setsNames[ind2]);
 	         	for (int i=0; i<=size; i++)
 	         		set[ind2].add(Integer.toString(RandomChoice.randInt(MIN_ELEMENT, MAX_ELEMENT)));	
 	         	size=RandomChoice.randInt(1,MAX_SET_SIZE-1);	
 	         	set[ind3]=new MathsSet(setsNames[ind3]);
 	         	for (int i=0; i<=size; i++)
 	         		set[ind3].add(Integer.toString(RandomChoice.randInt(MIN_ELEMENT, MAX_ELEMENT))); 	         					
 	         	solve();
 	         	return;
 	     
	}//generate 
	
/**
 * Method generates two sets of random size
 * with random integers as their elements.
 * @param      sets   Two sets in format name1, name2, name3, {el, el, el ... el},{el, el, el ... el},{el, el, el ... el}
 *					  quotation marks are not allowed 
 */	
protected void generate(String[] sets) {
	            if (sets.length<6) {
	            	generate();
	            	return;
	            }
	            ListIterator iter;
	            Vector[] stringSets=new Vector[3];	      
	            String tempStr;
	            
	            int l=0;
	            for (int k=3; k<sets.length; k++)
	            {	
	               stringSets[l]=new Vector<String>();	
	               tempStr=sets[k].replaceAll("[ {}]","");
	               if (tempStr.length()!=0){	            	
	            	stringSets[l].add(tempStr);	              	
	               }	              
	               if (sets[k].contains("}") &&(l<2)) l++;
	            }					
	        
 	         	set[0]=new MathsSet(new String(sets[0]));
 	         	iter=stringSets[0].listIterator();
 	         	while (iter.hasNext())
 	         		set[0].add(iter.next());
 	         	set[1]=new MathsSet(new String(sets[1]));
 	         	iter=stringSets[1].listIterator();
 	         	while (iter.hasNext())
 	         		set[1].add(iter.next());
 	         	set[2]=new MathsSet(new String(sets[2]));
 	         	iter=stringSets[2].listIterator();
 	         	while (iter.hasNext())
 	         		set[2].add(iter.next());
                 solve();
 	            return;
	}//generate 	

private void solve(){
	int k=RandomChoice.randInt(0,2);
	int[] indexes;
	Union un;
	Intersection intrsect;
	SetDifference diff;
	SetBinaryOp[] ops=new SetBinaryOp[2];
	MathsSet nullSet=new MathsSet("\\emptyset ");
	MathsSet S1;
	boolean flag1;
	//String question, solution, answer;
	//Q1: Write down the elements of the single set
	questions[0]="\\ensuremath{"+set[k].getName()+" }";	
    solutions[0]="\\ensuremath{"+set[k].getName()+"="+set[k].toString()+" }";
    shortanswers[0]="\\ensuremath{"+set[k].toString()+" }";

    //Q2: Write down the elements of the set S1 U S2
    indexes=RandomChoice.randPerm(3);   
    un=new Union(set[indexes[0]], set[indexes[1]]);	  	 
    questions[1]="\\ensuremath{"+un.getName()+"}";        
    solutions[1]= "\\ensuremath{"+un.getName()+" = "+un.toString()+" = "+(new MathsSet(un.calculate())).toString()+" }";
    shortanswers[1]="\\ensuremath{"+(new MathsSet(un.calculate())).toString()+" }";

    //Q3: Write down the elements of the set S1 intersect S2
    indexes=RandomChoice.randPerm(3);   
    intrsect=new Intersection(set[indexes[0]], set[indexes[1]]);	 
    questions[2]="\\ensuremath{"+intrsect.getName()+"}";        
    solutions[2]= "\\ensuremath{"+intrsect.getName()+" = "+intrsect.toString()+" = "+(new MathsSet(intrsect.calculate())).toString()+" }";
    shortanswers[2]="\\ensuremath{"+(new MathsSet(intrsect.calculate())).toString()+" }";

    
    //Q4: Write down the elements of the set S1 minus S2
    indexes=RandomChoice.randPerm(3);   
    diff=new SetDifference(set[indexes[0]], set[indexes[1]]);	 
    questions[3]="\\ensuremath{"+diff.getName()+"}";        
    solutions[3]= "\\ensuremath{ "+diff.getName()+" = "+diff.toString()+" = "+(new MathsSet(diff.calculate())).toString()+" }";
    shortanswers[3]="\\ensuremath{"+(new MathsSet(diff.calculate())).toString()+" }";
    
    //Q5: Write down the elements of the set S1\(S2 U S3) and shade the corresponding region on the Venn diagram.
    //Venn diagrams are provided in the corresponding tex-templates. 
	indexes=RandomChoice.randPerm(3);
	question5diagram=Integer.toString(indexes[0]+1);
	un=new Union(set[indexes[1]], set[indexes[2]]);
	diff=new SetDifference(set[indexes[0]], un);
	questions[4]="\\ensuremath{ "+diff.getName()+" }";
	solutions[4]="\\begin{align*} "+diff.getName()+" &= "+diff.toString()+" \\\\ &= " +	
		(new SetDifference(set[indexes[0]], new MathsSet(un.calculate()))).toString()+ " \\\\ &= "+
			(new MathsSet(diff.calculate())).toString()+" \\end{align*}";
    shortanswers[4]="\\ensuremath{"+(new MathsSet(diff.calculate())).toString()+" }";
    
    //Q6: Write down the elements of the set (S1 op0 S2) op1 S3 
    indexes=RandomChoice.randPerm(3);
    switch(RandomChoice.randInt(0,2)){
    		case 0:
    			ops[0]=new Union(set[indexes[0]],set[indexes[1]]);
    			break;
    		case 1: 
    			ops[0]=new Intersection(set[indexes[0]], set[indexes[1]]);
    			break;
    		case 2:
    			ops[0]=new SetDifference(set[indexes[0]], set[indexes[1]]);
    			break;	    		    
    }
     switch(RandomChoice.randInt(0,2)){
    		case 0:
    			ops[1]=new Union(ops[0],set[indexes[2]]);
    			break;
    		case 1: 
    			ops[1]=new Intersection(ops[0], set[indexes[2]]);
    			break;
    		case 2:
    			ops[1]=new SetDifference(ops[0], set[indexes[2]]);
    			break;	    		    
    }
    questions[5]="\\ensuremath{ "+ops[1].getName()+" }";	
    solutions[5]="\\begin{align*} "+ops[1].getName()+" &= "+ops[1].toString()+" \\\\ &= ";
    ops[1].setOp(0,new MathsSet(((SetCalculable)ops[0]).calculate()));	
	solutions[5]+=ops[1].toString()+" \\\\ &= "+
		(new MathsSet(((SetCalculable)ops[1]).calculate())).toString()+" \\end{align*}";
    shortanswers[5]="\\ensuremath{"+(new MathsSet(((SetCalculable)ops[1]).calculate())).toString()+" }";
    	
    //Q7: Write down the elements of the set S1 op1 (S2 op0 S3)
    indexes=RandomChoice.randPerm(3);
    switch  (RandomChoice.randInt(0,2)){
    		case 0:
    			ops[0]=new Union(set[indexes[1]],set[indexes[2]]);
    			break;
    		case 1: 
    			ops[0]=new Intersection(set[indexes[1]], set[indexes[2]]);
    			break;
    		case 2:
    			ops[0]=new SetDifference(set[indexes[1]], set[indexes[2]]);
    			break;	    		    
    }
     switch(RandomChoice.randInt(0,2)){
    		case 0:    			   			
    				ops[1]=new Union(set[indexes[0]], ops[0]);
    			    break;     			
    		case 1: 
    			ops[1]=new Intersection(set[indexes[0]], ops[0]);
    			break;   			
    		case 2:	
    			if (ops[0] instanceof Union)		
    			 ops[1]=new Intersection(set[indexes[0]], ops[0]);
    			else 
    			 ops[1]=new SetDifference(set[indexes[0]], ops[0]);
    			break;	    		    
    }
    questions[6]="\\ensuremath{ "+ops[1].getName()+" }";	
    solutions[6]="\\begin{align*} "+ops[1].getName()+" &= "+ops[1].toString()+" \\\\ &= ";
    ops[1].setOp(1,new MathsSet(((SetCalculable)ops[0]).calculate()));	
	solutions[6]+=ops[1].toString()+" \\\\ &= "+
		(new MathsSet(((SetCalculable)ops[1]).calculate())).toString()+" \\end{align*}";
    shortanswers[6]="\\ensuremath{"+(new MathsSet(((SetCalculable)ops[1]).calculate())).toString()+" }";
    
    //Q8: Write down the elements of the set {} op1 S1 (or S1 op1 {})
    S1= (MathsSet)(set[RandomChoice.randInt(0,2)].clone());
    if (RandomChoice.randInt(0,1)==1)
    	switch  (RandomChoice.randInt(0,2)){
    		case 0:
    			ops[0]=new Union(S1,nullSet);
    			break;
    		case 1: 
    			ops[0]=new Intersection(S1, nullSet);
    			break;
    		case 2:
    			ops[0]=new SetDifference(S1, nullSet);
    			break;	    		    
    } else
     switch(RandomChoice.randInt(0,2)){
    		case 0:    			   			
    				ops[0]=new Union(nullSet, S1);
    			    break;     			
    		case 1: 
    			ops[0]=new Intersection(nullSet, S1);
    			break;   			
    		case 2:	
    			 ops[0]=new SetDifference(nullSet, S1);
    			break;	    		    
    }
    questions[7]="\\ensuremath{ "+ops[0].getName()+" }";
    solutions[7]="\\ensuremath{ "+ops[0].getName()+" = "+ops[0].toString()+" = "+(new MathsSet(((SetCalculable)ops[0]).calculate())).toString()+" }";	
	shortanswers[7]="\\ensuremath{ "+(new MathsSet(((SetCalculable)ops[0]).calculate())).toString()+" }";	
   
   //Q9: Write down the elements of the set (S0 op0 S1) op2 (S2 op1 S3), one of the sets could be empty set
   MathsSet[] temps=new MathsSet[4];
   MathsSet[] fourSets=new MathsSet[4];
   flag1=true;
   ops=new SetBinaryOp[3];
   
   for (int i=0; i<3; i++){
   	if ((RandomChoice.randInt(0,100)==100) && flag1){   	
   		 temps[i]=nullSet;
   		 flag1=false;
   	}
    else 	
   		temps[i]=set[i];
   }
   
   temps[3] = (RandomChoice.randInt(0,3)==3) ? nullSet : set[RandomChoice.randInt(0,2)];    
   indexes=RandomChoice.randPerm(4);  
   for (int i=0; i<4; i++)
   	fourSets[i]=(MathsSet)(temps[indexes[i]].clone());	
  				
   for (int i=0; i<2; i++)
   	switch  (RandomChoice.randInt(0,2)){
    		case 0:
    			ops[i]=new Union(fourSets[i*2],fourSets[i*2+1]);
    			break;
    		case 1: 
    			ops[i]=new Intersection(fourSets[i*2], fourSets[i*2+1]);
    			break;
    		case 2:
    			ops[i]=new SetDifference(fourSets[i*2], fourSets[i*2+1]);
    			break;	   	
   	}
   
   switch  (RandomChoice.randInt(0,2)){
    		case 0:
    			ops[2]=new Union(ops[0],ops[1]);
    			break;
    		case 1: 
    			ops[2]=new Intersection(ops[0], ops[1]);
    			break;
    		case 2:
    			ops[2]=new SetDifference(ops[0], ops[1]);
    			break;	   	 					
   }
  
   questions[8]="\\ensuremath{ "+ops[2].getName()+" }";	
   	
   solutions[8]="\\begin{align*} "+ops[2].getName()+" &= "+ops[2].toString()+" \\\\ &= ";
   ops[2].setOp(0, new MathsSet(((SetCalculable)ops[0]).calculate()));
   ops[2].setOp(1, new MathsSet(((SetCalculable)ops[1]).calculate()));   
   solutions[8]+=ops[2].toString()+" \\\\ &= "+
		(new MathsSet(((SetCalculable)ops[2]).calculate())).toString()+" \\end{align*}";
   shortanswers[8]="\\ensuremath{"+(new MathsSet(((SetCalculable)ops[2]).calculate())).toString()+" }";
   
   return; 
}
} 
