/*
 * @(#)SmartGraph.java  1.00 2006/12/11
 *
 * This file is part of SmartAss and describes class SmartGraph for drawing pictures in Latex picture environment.
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
package au.edu.uq.smartass.auxiliary;
import java.math.*;
import java.util.*;
/**
* Class SmartGraph describes methods for building pictures using PICTURE environment in Latex 
*
* @version 1.0 14.12.2006
* @author Andriy Kvyatkovskyy
*/

public class SmartGraph {
/**
 * Returns a String containing Latex code for the interval marked on the number line.
 * Intended for use with SmartAss templates, tuned for SmartAss standard Latex
 * preamble. 
 * Arguments specify (in string representation) left and right boundaries 
 * of the interval.
 * <p>
 * If arguments' values are equal drawInterval will draw a point. 
 * If  left>right returns the message "Interval is empty."
 * If left<=-1000000 method treats the value as negative infinity;
 * if right >=1000000 method treats the value as positive infinity. 
 * For clear representation:
 * decimal values must be at least 0.001 apart (i.e. right-left>=0.001) and  
 * numbers must not contain more than 5 digits.
 *
 * @param  leftStr         	a String representation of the left boundary
 * @param  leftInclusive   	indicates whether left value included in the interval
 * @param  rightStr 		a String representation of the right boundary
 * @param  rightInclusive 	indicates whether right value included in the interval
 * @return          Latex code for the picture of the interval on the number line
 */

    public static String drawInterval(String leftStr, boolean leftInclusive, 
    	String rightStr, boolean rightInclusive ) {
    		
    		String interval;
    		int llimit, rlimit, position, d, scale, width, height=300, lpos, rpos, delta;
    	//	double start, step;
    		BigDecimal left, right, start, step;
    		boolean markl=true, markr=true;
    		interval="{ \\sffamily \\setlength{\\unitlength}{0.08mm} \\begin{picture}(2000,380) \n "+ //draw a number line
    		    "\\put(0,50){ \\thicklines\\line(1,0){1800}} "+
                "\\put(20,50){\\thicklines\\vector(-1,0){20}}  \n \\put(1800,50){\\thicklines\\vector(1,0){20}} \n"+
    		    "\\multiput(50,40)(170,0){11}{\\line(0,1){20}} \n";
    		try {
    	    	left=(new BigDecimal(leftStr)).stripTrailingZeros(); right=(new BigDecimal(rightStr)).stripTrailingZeros();	
    		} catch(NumberFormatException nfe) {
    			return "\\\\Can not draw an interval - numbers are not valid for SmartGraph.drawInterval() method!\\\\";
    		}    
    		if ((left.compareTo(new BigDecimal(-1000000))<=0) && (right.compareTo(new BigDecimal(1000000))>=0)) 
    			return "\\\\Interval includes all numbers.\\\\"; 
    		if (left.scale()<=0 && right.scale()<=0) { //both numbers are integer
    			int leftInt=left.intValue();
    			int rightInt=right.intValue();
                if (leftInt==rightInt)              //mark a point on a number line
                {
                	llimit=rightInt-6;
                	for (int i=llimit; i<llimit+11; i++)
                	{
                		position=(i-llimit)*170+50;
                		interval+="\\put("+position+",10){\\makebox(0,0){"+i+"}}\n";
                			//%\put(800,-40){\makebox(0,0){0}}
                	}
                	position=50+6*170;
                	interval+="\\put("+position+",50){\\circle*{35}}\n";
                		
                }//if 
          		   else { //two different integer numbers
             	   if  (leftInt>rightInt) return "\\\\Interval is empty.\\\\"; //empty interval            	   
                   if (leftInt<=-1000000) { //negative infinity
                     if (rightInt==0) scale=0; else
                     scale=(int)Math.log10(Math.abs(rightInt));                     
            	     d=(int)Math.pow(10,scale); //1 unit=d            	        	     
            	     if (Math.abs(rightInt)<5*d) //in the centre
            	   	 llimit=-5*d;
            	     else {              	      	  
            	      if (rightInt<0)
            	      	llimit=(int)(rightInt/(int)Math.pow(10,scale))*d-2*d;
            	      else
            	      	llimit=(int)(rightInt/(int)Math.pow(10,scale))*d-8*d;            	                 	   	  
            	     }
					 for (int i=0; i<11; i++)
                	 {
                		position=i*170+50;
                	    rlimit=llimit+i*d;
                		interval+="\\put("+position+",10){\\makebox(0,0){"+rlimit+"}}\n";                			
                	 }  
                	 position=50+(int)(rightInt-llimit)*170/d;	           	   
            	     interval+="\\put("+position+",50){\\circle";
            	     if (rightInclusive) interval+="*";
            	     interval+="{35}}\n";
            	                 	    
            	     if ((rightInt%Math.pow(10,scale))!=0)            	     	
            	      {
            	     	interval+="\\put("+position+",100){\\makebox(0,0){"+rightInt+"}}\n";
            	      }
            	     width=(int)(position*0.7);           	 
            	     //building Bezier curve:
            	     interval+="\\qbezier(10,"+height+")("+width+","+height+")("+position
            	     	+",50)\\put(10,"+height+"){\\vector(-1,0){10}}";         
                   } //end of negative infinity procedure
                   else 	
             	   if (rightInt>=1000000) { //positive infinity
                     if (leftInt==0) scale=0; else
                     scale=(int)Math.log10(Math.abs(leftInt));                     
            	     d=(int)Math.pow(10,scale); //1 unit=d            	        	     
            	     if (Math.abs(leftInt)<5*d) //in the centre
            	   	 llimit=-5*d;
            	     else {              	      	  
            	      if (leftInt<0)
            	      	llimit=(int)(leftInt/(int)Math.pow(10,scale))*d-2*d;
            	      else
            	      	llimit=(int)(leftInt/(int)Math.pow(10,scale))*d-8*d;            	                 	   	  
            	     }
					 for (int i=0; i<11; i++)
                	 {
                		position=i*170+50;
                	    rlimit=llimit+i*d;
                		interval+="\\put("+position+",10){\\makebox(0,0){"+rlimit+"}}\n";                			
                	 }  
                	 position=50+(int)(leftInt-llimit)*170/d;	           	   
            	     interval+="\\put("+position+",50){\\circle";
            	     if (leftInclusive) interval+="*";
            	     interval+="{35}}\n";           	                 	    
            	     if ((leftInt%Math.pow(10,scale))!=0)            	     	
            	      {
            	     	interval+="\\put("+position+",100){\\makebox(0,0){"+leftInt+"}}\n";
            	      }
            	      
            	     width=(int)(540+0.7*position);
            	     //building Bezier curve:
            	     interval+="\\qbezier(1790,"+height+")("+width+","+height+")("+position
            	     	+",50)\\put(1800,"+height+"){\\vector(1,0){10}}";          
             	   } //end of positive infinity
             	   else { 
             	   // no infinity, just whole numbers interval:
             	   delta=rightInt-leftInt;
             	   scale=(int)Math.log10(delta);              	   	                    
            	   d=(int)Math.pow(10,scale); //1 unit=d        
            	   if (delta>9*d) {d=d*10; scale++;}    	     	     
            	   if  ((rightInt<5*d)&&(leftInt>-5*d)) 
            	   	 llimit=-5*d;  // in the centre
            	     else {              	      	            	      
            	      	llimit=(int)(leftInt/(int)Math.pow(10,scale))*d-2*d;            	      
            	        if((rightInt)>(llimit+10*d))            	        	
            	      	llimit+=d;            	                 	   	  
            	     }
					 for (int i=0; i<11; i++)
                	 {
                		position=i*170+50;
                	    rlimit=llimit+i*d;
                		interval+="\\put("+position+",10){\\makebox(0,0){"+rlimit+"}}\n";                			
                	 }  
                	position=50+(int)(leftInt-llimit)*170/d;	    
                	lpos=position;	       	   
            	    interval+="\\put("+position+",50){\\circle";
            	    if (leftInclusive) interval+="*";
            	    interval+="{35}}\n";           	                 	    
            	    if ((leftInt%Math.pow(10,scale))!=0)            	     	
            	     	interval+="\\put("+position+",100){\\makebox(0,0){"+leftInt+"}}\n";            	      
            	    position=50+(int)(rightInt-llimit)*170/d;   
            	    rpos=position;         	    	           	   
            	    interval+="\\put("+position+",50){\\circle";
            	    if (rightInclusive) interval+="*";
            	    interval+="{35}}\n";           	                 	    
            	    if ((rightInt%Math.pow(10,scale))!=0)            	     	
            	     	interval+="\\put("+position+",100){\\makebox(0,0){"+rightInt+"}}\n";
            	    position=lpos+(rpos-lpos)/2;   
            	    //building Bezier curve:
            	    interval+="\\qbezier("+lpos+","+50+")("+position+","+height+")("+rpos
            	     	+",50)\n";      	
             	   } //no infinity
             	   
                } //different integer
                	    				
    		}// if integer numbers    		
    	  else { //numbers are double , difference between them must be >=0.001 (unless equal)
    	  		double leftDouble=left.doubleValue();
    			double rightDouble=right.doubleValue();  
                if (leftDouble==rightDouble)              //mark a point on a number line                
                {           	
                	start=right.setScale(0,RoundingMode.HALF_EVEN);   	
                	scale=0;
                   	while ((start.subtract(right).movePointRight(scale).abs().doubleValue()<0.1) && scale<3)  //zoom in if too close to the number on a number line
                	  scale++;                                   		
                	start=right.setScale(scale,RoundingMode.HALF_EVEN).subtract(new BigDecimal(5).movePointLeft(scale)); 	
                    step=start;                             	            	
                	for (int i=0; i<11; i++)
                	{
                		position=i*170+50;
                		interval+="\\put("+position+",10){\\makebox(0,0){"+step+"}}\n";
                			//%\put(800,-40){\makebox(0,0){0}}
                		step=step.add(new BigDecimal(1).movePointLeft(scale));
                	}                
                	position=50+(int)((right.subtract(start)).movePointRight(scale).doubleValue()*170);
                   	interval+="\\put("+position+",50){\\circle*{35}}\n"
                	 +"\\put("+position+",100){\\makebox(0,0){"+left+"}}\n"; 
                } //end of equal doubles	
                
                else { //two different doubles
                if  (leftDouble>rightDouble) return "\\\\Interval is empty.\\\\"; //empty interval                                            
                if (leftDouble<=-1000000) { //negative infinity
                    start=right.setScale(0,RoundingMode.HALF_EVEN);   	
                	scale=0;
                   	while ((start.subtract(right).movePointRight(scale).abs().doubleValue()<0.1) && scale<3)  //zoom in if too close to the number on a number line
                	  scale++;                    	  	    
                	if (Math.abs(rightDouble)*Math.pow(10,scale)<4.5) //in the centre
            	   	start=new BigDecimal(-5).movePointLeft(scale); 	
            	     else {              	      	  
            	      if (rightDouble<0)
            	      		start=right.setScale(scale,RoundingMode.HALF_EVEN).subtract(new BigDecimal(3).movePointLeft(scale)); 
            	      else
            	      	start=right.setScale(scale,RoundingMode.HALF_EVEN).subtract(new BigDecimal(7).movePointLeft(scale));      	                 	   	  
            	     }  	     	
                    step=start;                             	            	
                	for (int i=0; i<11; i++)
                	{
                		position=i*170+50;
                		interval+="\\put("+position+",10){\\makebox(0,0){"+step+"}}\n";
                			//%\put(800,-40){\makebox(0,0){0}}
                		step=step.add(new BigDecimal(1).movePointLeft(scale));
                	}                
                	position=50+(int)((right.subtract(start)).movePointRight(scale).doubleValue()*170);       
                	interval+="\\put("+position+",50){\\circle";
                	if (rightInclusive) interval+="*";
                	interval+="{35}}\n\\put("+position+",100){\\makebox(0,0){"+right+"}}\n"; 
          			width=(int)(position*0.7);           	 
            	     //building Bezier curve:
            	    interval+="\\qbezier(10,"+height+")("+width+","+height+")("+position
            	     	+",50)\\put(10,"+height+"){\\vector(-1,0){10}}";  
                   } //end of negative infinity procedure for doubles
                   else
             	    if (rightDouble>=1000000) {  //positive infinity
             	   	start=left.setScale(0,RoundingMode.HALF_EVEN);   	
                	scale=0;
                   	while ((start.subtract(left).movePointRight(scale).abs().doubleValue()<0.1) && scale<3)  //zoom in if too close to the number on a number line
                	  scale++;                    	  	    
                	if (Math.abs(leftDouble)*Math.pow(10,scale)<4.5) //in the centre
            	   	start=new BigDecimal(-5).movePointLeft(scale); 	
            	     else {              	      	  
            	      if (leftDouble<0)
            	      		start=left.setScale(scale,RoundingMode.HALF_EVEN).subtract(new BigDecimal(3).movePointLeft(scale)); 
            	      else
            	      	start=left.setScale(scale,RoundingMode.HALF_EVEN).subtract(new BigDecimal(7).movePointLeft(scale));      	                 	   	  
            	     }  	     	
                    step=start;                             	            	
                	for (int i=0; i<11; i++)
                	{
                		position=i*170+50;
                		interval+="\\put("+position+",10){\\makebox(0,0){"+step+"}}\n";
                			//%\put(800,-40){\makebox(0,0){0}}
                		step=step.add(new BigDecimal(1).movePointLeft(scale));
                	}                
                	position=50+(int)((left.subtract(start)).movePointRight(scale).doubleValue()*170);               
                	interval+="\\put("+position+",50){\\circle";
                	if (leftInclusive) interval+="*";
                	interval+="{35}}\n\\put("+position+",100){\\makebox(0,0){"+left+"}}\n";                 		
             	    width=(int)(540+0.7*position);
             	   //building Bezier curve:
            	    interval+="\\qbezier(1790,"+height+")("+width+","+height+")("+position
            	     	+",50)\\put(1800,"+height+"){\\vector(1,0){10}}";                    	                	                	                	    
             	    } //end of positive infinity
             	    else {	//no infinity just different boubles                	                 	    
             	    start=right.subtract(left); 
             	    scale=(int)Math.floor(Math.log10(start.doubleValue()));                 
            	   if (start.compareTo((new BigDecimal(9)).movePointRight(scale))==1) scale++;             	       	        	     
            	   if ( (right.movePointLeft(scale).compareTo(new BigDecimal(5))<=0)
            	   		&& (left.movePointLeft(scale).compareTo(new BigDecimal(-5))>=0)) {
            	   		
            	    	start=new BigDecimal(-5).movePointRight(scale); 	 // in the centre
             	    }
            	     else { 
            	     	     	     	           	     
              	 		start=left.setScale(-1*scale,RoundingMode.HALF_EVEN).subtract(new BigDecimal(2).movePointRight(scale));               	 			 			
            	    	if (right.subtract(start).movePointLeft(scale+1).compareTo(new BigDecimal(1))==1)
            	    		start=start.add(new BigDecimal(1).movePointRight(scale));
            	     }           	     
            	      //else            	   
      				 step=start;                             	            	
                	for (int i=0; i<11; i++)
                	{
                		position=i*170+50;
                		interval+="\\put("+position+",10){\\makebox(0,0){"+step+"}}\n";
                		if (step.compareTo(left)==0) markl=false;
                		if (step.compareTo(right)==0) markr=false;
                		step=step.add(new BigDecimal(1).movePointRight(scale));                	
                	}         
       				position=50+(int)((left.subtract(start)).movePointLeft(scale).doubleValue()*170);       
                	interval+="\\put("+position+",50){\\circle";
                	if (leftInclusive) interval+="*";
                	interval+="{35}}\n";
                    if (markl) interval+="\\put("+position+",100){\\makebox(0,0){"+left+"}}\n"; 	
                    lpos=position;
                	position=50+(int)((right.subtract(start)).movePointLeft(scale).doubleValue()*170);       
                	interval+="\\put("+position+",50){\\circle";
                	if (rightInclusive) interval+="*";
                	interval+="{35}}\n";
                	if (markr)	interval+="\\put("+position+",100){\\makebox(0,0){"+right+"}}\n"; 	
                	rpos=position;	 	           	   
            	    position=lpos+(rpos-lpos)/2;   
            	    //building Bezier curve:
            	    interval+="\\qbezier("+lpos+","+50+")("+position+","+height+")("+rpos
            	     	+",50)\n";      	             	    
             	    } //end of different numbers without infinity		    
                } //end of different numbers                              	                	
    	  } //end of doubles    			
    	interval+="\\end{picture}}";    	
//    	interval="\\input{smartass.tex} \\begin{document}"+interval;
//    	interval+="\\end{document}";    	
        return interval;
    } // drawInterval


 /**
 * Returns a String containing elements of three sets,
 * each set is split into parst, proportional to the numbers
 * in int[] shares.
 * Intended for use with SmartAss templates, for dispaying 
 * elements of sets on Venn diagrams. 
 *
 * @param  int[]        		numbers proportional to shares of the numbers of 
 *								elements in the output lines
 * @param  HashSet[] sets 	    sets for the first, second and third row of the output
 *								array of strings
 * @return String[3][]      	lines for outputting parts of 2 sets
 */
 public static String[][] break2Sets (HashSet[] sets, int[] shares){
 	Vector vs;
 	int sum=0;
 	int []totals=new int[shares.length-1];
 	int cum, index;
 	String[][] output= new String[3][shares.length];
 	Iterator itr;
 	for (int i=0; i<shares.length; i++)
 		sum+=shares[i];
    totals[0]=shares[0];
    for (int i=1; i<totals.length; i++)
    	totals[i]=totals[i-1]+shares[i];
 	for (int i=0; i<3; i++){
 		vs=new Vector(sets[i]); 		
 		index=0;
 		for (int k=1; k<shares.length; k++)
 		{
 		cum=(int)((double)totals[k-1]/sum*vs.size());
 			output[i][k-1]="";
 			while (index<cum){
 				
 				output[i][k-1]+=vs.get(index).toString()+" ";
 				index++;
 		 }
 		   output[i][k-1]=output[i][k-1].trim(); 		   		
 		}
 			
 		output[i][shares.length-1]="";	
 		while (index<vs.size()){ 		
 			output[i][shares.length-1]+=vs.get(index).toString()+" ";	
 			index++; 			
 		}
 		output[i][shares.length-1]=output[i][shares.length-1].trim();

 	} 
 		
    return output; 	
 } //break2Sets  
} //SmartGraph