/* @(#)TexPlot.java
 *
 * This file is part of SmartAss and describes class TexPlot for generating graphs using LaTeX.
 * Copyright (C) 2007 Department of Mathematics, The University of Queensland
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

/**
* Class TexPlot is the class that generates graphs using LaTeX.
*
* @version 1.0 4.4.2008
*/
public class TexPlot 
{
	private static final int fCentre = 0;
	
	private static final int fLeft = 1;
	private static final int fRight = 2;
	
	private static final int fBottom = 1;
	private static final int fTop = 2;
	
	public static final int fTiny = 0;
	public static final int fSmall = 1;
	
	/**
	* getInterval method takes two values and a increment and returns an array
	* of values evenly spaced in that interval between the given values that increases
	* delta
	* 
	* @param a - start of the interval
	* @param b - end of the interval
	* @param delta - the size of the increment
	* @return the points generated
	*/
	public static double[] getInterval( double a, double b, double delta )
	{
		int count = 0;
		int i;
		
		double x;
		
		for(x = a; x <= b; x += delta)
		{
			count++;
			
			if(x != b && x + delta > b)
			{
				count++;
			}
		}
		
		double[] interval = new double[count];
		
		x = a;
		
		for(i = 0; i < interval.length; i++)
		{
			interval[i] = x;
			
			x += delta;
			
			if(x > b) x = b;
		}
		
		return interval;
	}

	/**
	* getTex method takes a string of x and y values and generates a graph
	* 
	* @param x - the x values of the points to plot
	* @param y - the y values of the points to plot
	* @param width - the width of the graph (for LaTeX)
	* @param height - the height of the graph (for LaTeX)
	* @param textSize - the size of the text (e.g. numbering on the axis) either fTiny or fSmall
	* @param equalScaling - whether the x and y-axis have equal scaling
	* @return a string containing the LaTeX of the generated graph. The last character indicates whether
	* 		  the graph has equal scaling or not.
	*/	
	public static String getTex(double x[], double y[], double width, double height, int textSize ) 
	{
		String tex;
		
		int i;
		
		double minX;
		double maxX;
		double minY;
		double maxY;
		
		double xLength;
		double yLength;
		
		double offsetX;
		double offsetY;
		
		double scaleX;
		double scaleY;
		
		double axisScaleX;
		double axisScaleY;
		
		boolean equalScaling;
        
        // Write initial LaTeX stuff to the file    
		tex = getInitialTex(width, height);
        		
		// Get the range of the plot
		minX = x[0];
		maxX = x[0];
		minY = y[0];
		maxY = y[0];        
        
		for(i = 1; i < x.length; i++)
		{
			if( x[i] < minX ) minX = x[i];			
			if( x[i] > maxX ) maxX = x[i];
			if( y[i] < minY ) minY = y[i];			
			if( y[i] > maxY ) maxY = y[i];
		}
		
		// Adjust for purely vertical or horizontal lines
		if( minX == maxX )
		{
			if( minX == 0 )
			{
				minX = -2.0;
				maxX = 2.0;
			}
			else if( minX > 0 )
			{
				maxX = 2 * maxX;
				minX = -maxX;
			}
			else
			{
				minX = 2 * minX;
				maxX = -maxX;
			}
		}
		
		if( minY == maxY )
		{
			if( minY == 0 )
			{
				minY = -2.0;
				maxY = 2.0;				
			}
			else if( minY > 0 )
			{
				maxY = 2 * maxY;
				minY = -maxY;				
			}
			else
			{
				minY = 2 * minY;
				maxY = -maxY;				
			}			
		}
		
		// Get the scale of the notches on the axis and re-adjust the area of the plot				
		axisScaleX = getAxisScale(minX, maxX, 8);
		axisScaleY = getAxisScale(minY, maxY, 8);
		
		maxX = axisScaleX * Math.ceil(maxX/axisScaleX);
		maxY = axisScaleY * Math.ceil(maxY/axisScaleY);
		
		minX = axisScaleX * Math.floor(minX/axisScaleX);
		minY = axisScaleY * Math.floor(minY/axisScaleY);
		
		equalScaling = (maxX - minX == maxY - minY);
		
		// Now scale the points to the plotting areas coordinates
		offsetX = width / 15.0;
		offsetY = height / 15.0;
		
		scaleX = (width - 2.0 * offsetX) / (maxX - minX);
		scaleY = (height - 2.0 * offsetY) / (maxY - minY);
		
		for(i = 0; i < x.length; i++)
		{
			x[i] =  scaleX * (x[i] - minX) + offsetX;
			y[i] =  scaleY * (y[i] - minY) + offsetY;
		}	
		
		// Now plot the actual function
		for(i = 0; i < x.length - 1; i++)
		{				     
		    tex += drawLine(x[i],y[i],x[i+1],y[i+1]) + "\n";
		}	
		
		// Now plot the axis		
		tex += getXAxis(width, height, minX, maxX, minY, maxY, offsetY, offsetX, 
						scaleX, scaleY, axisScaleX, textSize);

		tex += getYAxis(width, height, minX, maxX, minY, maxY, offsetY, offsetX, 
					    scaleX, scaleY, axisScaleY, textSize);	    
		
		// Now the axis intercept label
		tex += getInterceptTex(minX, maxX, minY, maxY, scaleX, scaleY, offsetX, offsetY, textSize );
		tex += getAxisLabels(width, height, minX, maxX, minY, maxY, offsetX, offsetY, scaleX, scaleY );
		
		// Final TeX stuff
	    tex += getFinalTex();     
	    
	    // Does the graph have equal scaling?
	    if( equalScaling )
	    	tex += "1";
	    else
	    	tex += "0";
		
        return tex;
	}
	
	/**
	* getInitialTex method generates the initial TeX for the picture enviroment
	* 
	* @param width - the width of the graph (for LaTeX)
	* @param height - the height of the graph (for LaTeX)
	* @return a string containing the initial LaTeX for the graph
	*/		
	private static String getInitialTex(double width, double height)
	{
        //String tex = "\\begin{figure}[h]\n";                
        String tex = "\\begin{center}\n";        
        tex += "\\begin{picture}(" + Float.toString((float)width) + "," + Float.toString((float)height) + ")\n";
        
        return tex;
	}
	
	/**
	* getFinalTex method generates the final TeX for the picture enviroment
	* 
	* @return a string containing the final LaTeX for the graph
	*/			
	private static String getFinalTex()
	{	    
		String tex = "\\end{picture}\n";        
        tex += "\\end{center}\n";        
        //tex += "\\end{figure}\n";   
        
        return tex;
	}
	
	/**
	* getXAxis method generates the TeX to draw the x-axis of the graph
	* 
	* @param width - the width of the graph (for LaTeX)
	* @param height - the height of the graph (for LaTeX)
	* @param minX - describes the smallest point on the x-axis
	* @param maxX - describes the largest point on the x-axis
	* @param minY - describes the smallest point on the y-axis
	* @param maxY - describes the largest point on the y-axis
	* @param offsetY - how much buffer there is at the top and bottom of the graph
	* @param offsetX - how much buffer there is at the left and right of the graph
	* @param scaleX - the scaling value for the x-values of the points to fit in the graph
	* @param scaleY - the scaling value for the y-values of the points to fit in the graph
	* @param axisScaleX - the increment on the values displayed on the x-axis
	* @param textSize - the size of the text on the axis (either fTiny or fSmall)
	* @return a string containing the LaTeX to draw the x-axis of the graph
	*/			
	private static String getXAxis(double width, double height, double minX, double maxX, 
								   double minY, double maxY, double offsetY, double offsetX, 
								   double scaleX, double scaleY, double axisScaleX, int textSize)
	{
		boolean normal = true;
		
		String tex;
		
		int n;
		int i;
		
		double j;
		double temp;
		double originY;
		
		if( 0 <= minY )
		{
			originY = offsetY;
			normal = false;
		}
		else if( 0 > maxY )
			originY = scaleY * (maxY-minY) + offsetY;				
		else
			originY = scaleY * -minY + offsetY;
		
	    tex = drawLine(offsetX, originY, width - offsetX, originY) + "\n";
	    
	    n = (int)(Math.ceil(maxX/axisScaleX)) - (int)(Math.floor(minX/axisScaleX)) + 1;
	    
	    for( i = 0; i < n; i++ )
	    {
	    	j = minX + i * axisScaleX;
	    	
	    	temp = scaleX * (j - minX) + offsetX;
	    	
	    	if(normal)
	    		tex +=  drawLine(temp, originY, temp, originY-3) + "\n";
	    	else
	    		tex +=  drawLine(temp, originY, temp, originY+3) + "\n";
    		
	    	if( j == 0.0 )
	    		continue;
	    	
	    	if( i == 0 && j > 0.0 )
	    		continue;
	    	
	    	if( i == n - 1 && j < 0.0 )
	    		continue;
	    	
	    	if( normal )
	    		tex +=  drawFormattedText(temp, originY - 7, Float.toString((float)j), textSize, fCentre, fTop ) + "\n";
	    	else
	    		tex +=  drawFormattedText(temp, originY + 7, Float.toString((float)j), textSize, fCentre, fBottom ) + "\n";
	    }	
	    
	    return tex;
	}
	
	/**
	* getYAxis method generates the TeX to draw the y-axis of the graph
	* 
	* @param width - the width of the graph (for LaTeX)
	* @param height - the height of the graph (for LaTeX)
	* @param minX - describes the smallest point on the x-axis
	* @param maxX - describes the largest point on the x-axis
	* @param minY - describes the smallest point on the y-axis
	* @param maxY - describes the largest point on the y-axis
	* @param offsetY - how much buffer there is at the top and bottom of the graph
	* @param offsetX - how much buffer there is at the left and right of the graph
	* @param scaleX - the scaling value for the x-values of the points to fit in the graph
	* @param scaleY - the scaling value for the y-values of the points to fit in the graph
	* @param axisScaleY - the increment on the values displayed on the y-axis
	* @param textSize - the size of the text on the axis (either fTiny or fSmall)
	* @return a string containing the LaTeX to draw the y-axis of the graph
	*/			
	private static String getYAxis(double width, double height, double minX, double maxX, 
			   					   double minY, double maxY, double offsetY, double offsetX, 
			   					   double scaleX, double scaleY, double axisScaleY, int textSize)
	{
		boolean normal = true;
		
		String tex;
		
		int n;
		int i;
		
		double j;
		double temp;
		double originX;
	    
		if( 0 < minX )
			originX = offsetX;				
		else if( 0 >= maxX )
		{
			originX = scaleX * (maxX-minX) + offsetX;		
			normal = false;
		}
		else
			originX = scaleX * -minX + offsetX;	    
	    
		tex =  drawLine(originX, offsetY, originX, height - offsetY) + "\n";
	    
	    n = (int)(Math.ceil(maxY/axisScaleY)) - (int)(Math.floor(minY/axisScaleY)) + 1;
	    
	    for( i = 0; i < n; i++ )
	    {
	    	j = minY + i * axisScaleY;
	    	
	    	temp = scaleY * (j - minY) + offsetY;
	    	
	    	if(normal)
	    		tex += drawLine(originX, temp, originX-3, temp) + "\n";
	    	else
	    		tex += drawLine(originX, temp, originX+3, temp) + "\n";
	    	
	    	if( j == 0.0 )
	    		continue;
	    	
	    	if( i == 0 && j > 0.0 )
	    		continue;
	    	
	    	if( i == n - 1 && j < 0.0 )
	    		continue;
	    	
	    	if( normal )
	    		tex += drawFormattedText(originX - 6.0, temp, Float.toString((float)j), textSize, fRight, fCentre ) + "\n";
	    	else
	    		tex += drawFormattedText(originX + 6.0, temp, Float.toString((float)j), textSize, fLeft, fCentre ) + "\n";
	    }
		
		return tex;
	}
	
	/**
	* getInterceptTex method goes through severel cases to decide what text to write
	* at the intercept at the axis
	* 
	* @param minX - describes the smallest point on the x-axis
	* @param maxX - describes the largest point on the x-axis
	* @param minY - describes the smallest point on the y-axis
	* @param maxY - describes the largest point on the y-axis
	* @param scaleX - the scaling value for the x-values of the points to fit in the graph
	* @param scaleY - the scaling value for the y-values of the points to fit in the graph
	* @param offsetY - how much buffer there is at the top and bottom of the graph
	* @param offsetX - how much buffer there is at the left and right of the graph
	* @param textSize - the size of the text (either fTiny or fSmall)
	* @return a string containing LaTeX to draw the axis intercept TeX
	*/			
	private static String getInterceptTex(double minX, double maxX, double minY, double maxY,
									     double scaleX, double scaleY, double offsetX, double offsetY,
									     int textSize )
	{
		String tex = "";
		
		double originX;
		double originY;
		
		int xAxisPos;
		int yAxisPos;	
		
		// Get how the x-axis will be positioned
		if( 0 < minY )
			xAxisPos = -1;
		else if( 0 > maxY )
			xAxisPos = 1;		
		else
			xAxisPos = 0;
		
		// Get how the y-axis will be positioned
		if( 0 < minX )
			yAxisPos = -1;	
		else if( 0 > maxX )
			yAxisPos = 1;
		else
			yAxisPos = 0;
		
		// Now draw the intercept text
		switch( xAxisPos )
		{
		case -1:
			
			switch( yAxisPos )
			{
			case -1:
				
				tex += drawFormattedText(offsetX, offsetY - 7.0, Float.toString((float)minX), textSize, fCentre, fTop ) + "\n";	   
				tex += drawFormattedText(offsetX - 6, offsetY, Float.toString((float)minY), textSize, fRight, fCentre ) + "\n";
	    		break;
				
			case 0:
				
				originY = scaleY * -minY + offsetY;
				tex +=  drawFormattedText(offsetX, originY - 7.0, Float.toString((float)0.0), textSize, fCentre, fTop ) + "\n";	    		
	    		break;
				
			case 1:
				
				originX = scaleX * (maxX-minX) + offsetX;
				originY = scaleY * (maxY-minY) + offsetY;
				
				tex += drawFormattedText(offsetX, originY - 7, Float.toString((float)maxX), textSize, fCentre, fTop ) + "\n";
				tex += drawFormattedText(originX+6, offsetY, Float.toString((float)maxY), textSize, fLeft, fCentre ) + "\n";	    			
				break;
			}
			
			break;
			
		case 0:	
			
			switch( yAxisPos )
			{
			case -1:
				
				originY = scaleY * -minY + offsetY;
				tex += drawFormattedText(offsetX - 6.0, originY, Float.toString((float)0.0), textSize, fRight, fCentre ) + "\n";
	    		break;
				
			case 0:
				
				originX = scaleX * -minX + offsetX;
				originY = scaleY * -minY + offsetY;				

    			tex += drawFormattedText(originX - 2.0, originY - 2.0, Integer.toString(0), textSize, fRight, fTop ) + "\n";
    			break;
				
			case 1:
				
				originX = scaleX * (maxX-minX) + offsetX;
				originY = scaleY * -minY + offsetY;				
	    			
	    		tex += drawFormattedText(originX+6, originY, Float.toString((float)0.0), textSize, fLeft, fCentre ) + "\n";
	    			
	    		break;
			}
			
			break;
			
		case 1:
			
			originX = scaleX * (maxX-minX) + offsetX;
			
			switch( yAxisPos )
			{
			case -1:
				
	    		tex += drawFormattedText(originX, offsetY + 7, Float.toString((float)minX), textSize, fCentre, fBottom ) + "\n";
    			tex += drawFormattedText(originX - 6.0, offsetY, Float.toString((float)minY), textSize, fRight, fCentre ) + "\n";
	    		break;
				
			case 0:
				
				originY = scaleY * -minY + offsetY;
				
	    		tex += drawFormattedText(originX, originY + 7, Float.toString((float)0.0), textSize, fCentre, fBottom ) + "\n";
	    		break;

			case 1:
				
				originY = scaleY * (maxY-minY) + offsetY;

	    		tex += drawFormattedText(originX, originY + 7, Float.toString((float)maxX), textSize, fCentre, fBottom ) + "\n";
	    		tex += drawFormattedText(originX+6, originY, Float.toString((float)maxY), textSize, fLeft, fCentre ) + "\n";
	    		
	    		break;
			}
			
			break;
		}
		
		return tex;
	}
	
	/**
	* getAxisLabels method gets the TeX that writes the axis labels on the graph
	* 
	* @param width - the width of the graph (for LaTeX)
	* @param height - the height of the graph (for LaTeX)
	* @param minX - describes the smallest point on the x-axis
	* @param maxX - describes the largest point on the x-axis
	* @param minY - describes the smallest point on the y-axis
	* @param maxY - describes the largest point on the y-axis
	* @param offsetY - how much buffer there is at the top and bottom of the graph
	* @param offsetX - how much buffer there is at the left and right of the graph
	* @param scaleX - the scaling value for the x-values of the points to fit in the graph
	* @param scaleY - the scaling value for the y-values of the points to fit in the graph
	* @return a String containing the TeX to draw the axis labels on the graph
	*/			
	private static String getAxisLabels(double width, double height, double minX, double maxX,
										double minY, double maxY, double offsetX, double offsetY,
										double scaleX, double scaleY)
	{
		String tex;
		
		// Get how the x-axis will be positioned
		if( 0 < minY )
			tex = drawFormattedText(width / 2.0, offsetY - 15, "x", fSmall, fCentre, fTop) + "\n";
		else if( 0 > maxY )
			tex = drawFormattedText(width / 2.0, scaleY * (maxY-minY) + offsetY + 15, "x", fSmall, fCentre, fBottom) + "\n";
		else
		{
			if( 0 == maxY)
				tex = drawFormattedText(scaleX * -minX + offsetX, offsetY - 10, "y", fSmall, fCentre, fTop) + "\n";
			else
				tex = drawFormattedText(scaleX * -minX + offsetX, scaleY * (maxY-minY) + offsetY + 10, "y", fSmall, fCentre, fBottom) + "\n";
		}
		
		// Get how the y-axis will be positioned
		if( 0 < minX )
			tex += drawFormattedText(offsetX - 15, height / 2.0, "y", fSmall, fRight, fCentre) + "\n";
		else if( 0 > maxX )
			tex += drawFormattedText(scaleX * (maxX-minX) + offsetX + 15, height / 2.0, "y", fSmall, fLeft, fCentre) + "\n";
		else
			tex += drawFormattedText(scaleX * (maxX-minX) + offsetX + 10, scaleY * -minY + offsetY, "x", fSmall, fLeft, fCentre) + "\n";
		
		return tex;		
	}
	
	/**
	* drawLine method generates the LaTeX to draw a single line element
	* 
	* @param x0 - x-value of the first point
	* @param y0 - y-value of the first point
	* @param x1 - x-value of the second point
	* @param y1 - y-value of the second point
	* @return a string containing the LaTeX to draw a single line element
	*/		
	private static String drawLine(double x0, double y0, double x1, double y1) 
	{			
		if( Math.abs(x0-x1) < Math.pow(10.0, -4.0) && Math.abs(y0-y1) < Math.pow(10.0, -4.0) )
			return "";
		
		double xMid = (x0 + x1) / 2.0;
		double yMid = (y0 + y1) / 2.0;
		
		// Now save the LaTeX output	
		return "\\qbezier(" + Double.toString(x0) + "," + Double.toString(y0) + ")(" +
						      Double.toString(xMid) + "," + Double.toString(yMid) + ")(" +
						      Double.toString(x1) + "," + Double.toString(y1) + ")";
	}
	
	/**
	* drawText method generates the LaTeX to draw text on the graph
	* 
	* @param x - x position of the text
	* @param y - y position of the text
	* @param text - the text to write
	* @param xOffset - how much to offset the x position of the text
	* @param yOffset - how much to offset the y position of the text
	* @return a string containing the LaTeX to draw text on the graph
	*/		
	private static String drawText(double x, double y, String text, double xOffset, double yOffset) 
	{	
		return "\\put(" + Float.toString((float)(x + xOffset)) + "," + Float.toString((float)(y + yOffset)) + "){$" + text + "$}";
	}
	
	/**
	* getAxisScale method calculates the increment in values it displays on the axis
	* 
	* @param min - the smallest value on the axis
	* @param max - the largest value on the axis
	* @param maxN - the maximum number of notches on the axis
	* @return a double containing the increment in value is displays on the axis
	*/			
	private static double getAxisScale(double min, double max, int maxN)
	{
		double scale = 1.0;
		
		int i;
		
		int n = (int)(Math.ceil(max/scale)) - (int)(Math.floor(min/scale)) + 1;
		
		if( n > maxN )
		{
			for(i=0; n > maxN; i++)
			{
				switch(i%3)
				{
				case 0:
					scale = Math.pow( 10, (double)(i / 3) );
					break;
					
				case 1:
					scale = 2 * Math.pow( 10, (double)(i / 3) );
					break;
					
				case 2:
					scale = 4 * Math.pow( 10, (double)(i / 3) );
					break;
				}
				
				n = (int)(Math.ceil(max/scale)) - (int)(Math.floor(min/scale)) + 1;
			}	
		}
		else
		{
			for(i=0; n < maxN; i++)
			{
				switch(i%3)
				{
				case 0:
					scale = Math.pow( 0.1, (double)(i / 3) );
					break;
					
				case 1:
					scale = 0.5 * Math.pow( 0.1, (double)(i / 3) );
					break;
					
				case 2:
					scale = 0.25 * Math.pow( 0.1, (double)(i / 3) );
					break;
				}
				
				n = (int)(Math.ceil(max/scale)) - (int)(Math.floor(min/scale)) + 1;
			}	
			
			i -= 2;
			
			switch(i%3)
			{
			case 0:
				scale = Math.pow( 0.1, (double)(i / 3) );
				break;
				
			case 1:
				scale = 0.5 * Math.pow( 0.1, (double)(i / 3) );
				break;
				
			case 2:
				scale = 0.25 * Math.pow( 0.1, (double)(i / 3) );
				break;
			}
		}
		
		return scale;
	}

	/**
	* drawText method generates the LaTeX to draw text on the graph with 
	* some extra control of the formatting of the text
	* 
	* @param x - x position of the text
	* @param y - y position of the text
	* @param text - the text to write
	* @param textSize - the size of the text (either fTiny or fSmall)
	* @param hFormat - the horizontal alignment of the text (either fLeft, fCentre or fRight)
	* @param vFormat - the vertical alignment of the text (either fBottom, fCentre or tTop)
	* @return a string containing the LaTeX to draw text on the graph
	*/	
	private static String drawFormattedText(double x, double y, String text, int textSize, int hFormat, int vFormat)
	{
		int i;
		
		double width = 0.0;
		double height = 0.0;
		
		double xOffset = 0.0;
		double yOffset = 0.0;
		
		String tex = "";
		
		// Get the width of the string
		switch(textSize)
		{
		case fTiny:
			
			for(i = 0; i < text.length(); i++)
				switch( (text.toCharArray())[i] )
				{
				case '.': 	width += 1.0; break;	
				case '-':	width += 5.5; break;
				default:	width += 4.0; break;
				}
			
			break;
			
		case fSmall:
			
			for(i = 0; i < text.length(); i++)
				switch( (text.toCharArray())[i] )
				{
				case '.': 	width += 1.0; break;	
				case '-':	width += 6.5; break;
				default:	width += 5.0; break;
				}
			
			break;			
		}
		
		// Get the height of the string
		switch(textSize)
		{
		case fTiny:
			
			height = 4.0;			
			break;
			
		case fSmall:
			
			height = 6.0;			
			break;			
		}
		
		// Now do the actual TeX
		switch(textSize)
		{
		case fTiny:
			
			tex += "{\\tiny ";			
			break;
			
		case fSmall:
			
			tex += "{\\small ";			
			break;			
		}		
		
		switch(hFormat)
		{
		case fLeft:
			
			xOffset = 0.0;
			break;
			
		case fCentre:
			
			xOffset = width / 2.0;
			break;
			
		case fRight:
			
			xOffset = width;
			break;
		}
		
		switch(vFormat)
		{
		case fBottom:
			
			yOffset = 0.0;
			break;
			
		case fCentre:
			
			yOffset = height / 2.0;
			break;
			
		case fTop:
			
			yOffset = height;
			break;
		}
		
		tex += drawText(x, y, text, -xOffset, -yOffset);
		
		if( textSize == fTiny || textSize == fSmall )
			tex += "}";
		
		return tex;
	}
	
	public static String getIntegralInterval(double x1, double y1, double x2, double y2,
			double x[], double y[], double width, double height, int textSize ) {
		
		double minX = x[0], maxX = x[0], minY = y[0], maxY = y[0];
		for(int i = 1; i < x.length; i++)
		{
			if( x[i] < minX ) minX = x[i];			
			if( x[i] > maxX ) maxX = x[i];
			if( y[i] < minY ) minY = y[i];			
			if( y[i] > maxY ) maxY = y[i];
		}
		
		double axisScaleX = getAxisScale(minX, maxX, 8); 
		double axisScaleY = getAxisScale(minY, maxY, 8); 

		maxX = axisScaleX * Math.ceil(maxX/axisScaleX);
		maxY = axisScaleY * Math.ceil(maxY/axisScaleY);
		minX = axisScaleX * Math.floor(minX/axisScaleX);
		minY = axisScaleY * Math.floor(minY/axisScaleY);
		
		double offsetX = width / 15.0;
		double offsetY = height / 15.0;
		double scaleX = (width - 2.0 * offsetX) / (maxX - minX);
		double scaleY = (height - 2.0 * offsetY) / (maxY - minY);
		
		System.out.println("before scale x1: " + x1 + ", y1: " + y1 + ", x2: " + x2 + ", y2: " + y2);

		x1 =  scaleX * (x1 - minX) + offsetX;
		y1 =  scaleY * (y1 - minY) + offsetY;
		x2 =  scaleX * (x2 - minX) + offsetX;
		y2 =  scaleY * (y2 - minY) + offsetY;
		double zeroY;
		if(minY<0)
			zeroY = - scaleY * minY + offsetY;
		else
			zeroY = offsetY;
		double zeroX = scaleX * minX + offsetX;

		String tex = getTex(x, y, width, height, textSize);
		int endpos = tex.indexOf("\\end{picture}");
		String head = tex.substring(0, endpos);
		String tail = tex.substring(endpos);
		tail = tail.substring(0, tail.indexOf("0"));
		
		System.out.println("after scale x1: " + x1 + ", y1: " + y1 + ", x2: " + x2 + ", y2: " + y2);
		StringBuffer s = new StringBuffer(
				"\\qbezier(" + Double.toString(x1) + "," + Double.toString(zeroY) + ")(" +
			      Double.toString(x1) + "," + Double.toString(y1) + ")(" +
			      Double.toString(x1) + "," + Double.toString(y1) + ")\n" 
				+ "\\qbezier(" + Double.toString(x2) + "," + Double.toString(zeroY) + ")(" +
			      Double.toString(x2) + "," + Double.toString(y2) + ")(" +
			      Double.toString(x2) + "," + Double.toString(y2) + ")"
			    + "\n");

		double d = x[1]-x[0];
		for(int i=0; i<x.length; i++)
			if(x[i]>=x1 && x[i]<=x2) {
				int i1 = 0;
				double X2 = x2;
				for(int j=i; j<x.length; j++) 
					if(y[j]<zeroY + x[j] - x[i]) {
//						double xx1 = x[i] - zeroX;
//						double xx2 = x[j] - zeroX;
//						double yy1 = y[i] - zeroY;
//						double yy2 = y[j] - zeroY;
						double xx1 = x[j-1] - zeroX;
						double xx2 = x[j] - zeroX;
						double yy1 = y[j-1] - zeroY;
						double yy2 = y[j] - zeroY;
//						X2 = zeroX + (xx1 + yy1 - (yy2-yy1)/(xx2-xx1)*xx1)*(xx2-xx1)
//								/ (xx2 - xx1 - yy2 + yy1);

						X2 = zeroX + ((-(x[i]-zeroX) - yy1)*(xx1 - xx2) + xx1*(yy1-yy2)) / (xx2 - xx1 - yy2 + yy1);

						X2 = X2>x2?x2:X2;
						break;
					} else if(x[j]>x2)
						break;
				s.append("\\qbezier(" + Double.toString(x[i]) + "," + Double.toString(zeroY) + ")(" +
						Double.toString(X2) + "," + Double.toString(X2 - x[i] + zeroY) + ")(" +
					    Double.toString(X2) + "," + Double.toString(X2 - x[i] + zeroY) + ")\n");
			}

		double dX1 = 0; 
		int i1 = 0;
		for(int i=0; i<x.length; i++)
			if(x[i]>=x1) {
				dX1 = x[i] - x1;
				i1 = i;
				break;
			}
		double Yi = d-dX1;
		while(zeroY+Yi<y1) {
			double X2 = x2;
			for(int i=i1;i<x.length;i++) 
				if(y[i]<zeroY + (x[i] - x1) + Yi) {
					X2 = x[i-1];

					double xx1 = x[i-1] - zeroX;
					double xx2 = x[i] - zeroX;
					double yy1 = y[i-1] - zeroY;
					double yy2 = y[i] - zeroY;
					
					X2 = zeroX + ((Yi - (x1-zeroX) - yy1)*(xx1 - xx2) + xx1*(yy1-yy2)) / (xx2 - xx1 - yy2 + yy1);
					
					break;
				} else if(x[i]>x2)
					break;
			if(X2>=x1)
				s.append("\\qbezier(" + Double.toString(x1) + "," + Double.toString(zeroY+Yi) + ")(" +
						Double.toString(X2) + "," + Double.toString(zeroY + X2 - x1 + Yi) + ")(" +
					      Double.toString(X2) + "," + Double.toString(zeroY + X2 - x1 + Yi) + ")\n");
			Yi += d;
		}
		
		tex = head + s.toString() + tail;
		return tex;
	}
}

