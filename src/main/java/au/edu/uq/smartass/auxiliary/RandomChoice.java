/* This file is part of SmartAss and describes class RandomChoice for making a biased choice.
 * Copyright (C) 2006 Andriy Kvyatkovskyy, The University of Queensland
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
package au.edu.uq.smartass.auxiliary;
import java.util.*;
import java.math.*;


public class RandomChoice {
    private static Random generator=new Random(); // random number generator

    public static boolean randBool() {
        return generator.nextBoolean();
    }


    //  public static Object selectFromArray(Object[] arr) {
    //          return arr[randInt(0, arr.length-1)];
    //  }

    /**
     * Returns a choice randomly picked from -1 or 1
     *
     * @return      int
     */
    public static int randSign () { // generates random integer between bottom and top inclusive
        if(randBool())
            return -1;
        else
            return 1;
    } // randSign

    /**
     * Returns a random integer in the range from bottom to top (inclusive)
     * @param       bottom  lower boundary
     * @param       top     upper boundary
     * @return      int
     */
    public static int randInt (int bottom, int top) { // generates random integer between bottom and top inclusive
        if(bottom>top) System.out.print("Bottom " + bottom +" greater then top " +top );
        return (generator.nextInt(top-bottom+1)+bottom);
    } // randInt

    /**
     * Returns a random double in the range from bottom to top (inclusive)
     * @param       bottom  lower boundary
     * @param       top     upper boundary
     * @return      double
     */
    public static double randDouble (double bottom, double top) {
        //generates random double between bottom (inclusive) and top (exclusive)
        return (generator.nextDouble()*(top-bottom)+bottom);
    } // randDouble

    /**
     * Returns a random integer from 0 to weights.size()-1
     * if weigth == 0 , it will not be choosen
     * @param       weights are corresponding to probabilities
     * @return      int
     */
    public static int selectInt(int[] weights) {  //implementing roulette method
        int numOfCh=weights.length;
        int sumOfWeights=0;
        int [] cumNorm = new int[numOfCh];
        int cn=0, x;
        // String madeChoice;
        for (int i=0; i<numOfCh; i++)
            sumOfWeights+=weights[i]; //calculating sum of all weights
        for (int i=0; i<numOfCh-1; i++) {
            cumNorm[i]=cn+weights[i];
            cn=cumNorm[i];
        }
        cumNorm[numOfCh-1]=sumOfWeights;
        x=generator.nextInt(sumOfWeights)+1;
        int j=0;
        while (x>cumNorm[j]) j++;
        return j;

    } //selectInt

    /**
     * Returns a random permutation of n numbers in array.
     * Numbers are in a range [0..n-1]
     * @param       n
     * @return      int[] random permutation of n numbers
     */
    public static int[] randPerm(int n){
        int r;
        int permuted[] = new int[n];
        Vector<Integer> v=new Vector<Integer>();
        for (int i=0; i < n; i++)
            v.add(new Integer(i));

        for (int i2 = n; i2 > 0; i2--)
            {
                r=randInt(0,i2-1);
                permuted[i2-1]=v.get(r);
                v.remove(r);
            }
        return permuted;
    }

    /**
     * Returns user's choice, randomly picked from a number of options. User defines possible options in
     * inString in the following format:
     * "OPTION/WEIGHT;OPTION/WEIGHT;... ;OPTION/WEIGHT",
     * WEIGHT is an integer in the range from 0 to 100. WEIGHT defines the probability with wich the OPTION
     * will be choosen. If WEIGHT ==0 it will not be selected.
     * OPTION has the following format OpeningBracket number|string|<word character>[..number|<word character>] ClosingBracket
     * OpeningBracket and ClosingBracket could be "(",")"-indicate exclusive and "[","]"-inclusive limit.
     * If clause [..number|<word character>] present, method will return any of the item in the range (probability uniformly
     * distributed).
     * Method returns 2-element array of strings, element 0 contains option, element 1 contains a type of returning option
     * (possible values of element 1 are : "int", "double","str","char".
     * Example of parameter inString: "(4..15]/6;[a..f]/11;[G]/4;[5.0..7.99]/10"
     */
    public static String[] makeChoice(String inString) throws IllegalArgumentException,IndexOutOfBoundsException { // generating random choice (integers, doubles or strings)
        String[] parts, atoms;
        String[] result=new String[2];
        Choice newChoice;
        String pick, first, second, letters;
        int topInt, bottomInt, maxScale;
        BigDecimal big1, big2, randBig, resBig;
        Vector <Choice> chs = new Vector <Choice> ();

        parts=inString.split(";");
        for (int i=0; i<parts.length; i++)
            {
                atoms=parts[i].split("/");
                newChoice= new Choice(Integer.valueOf(atoms[1]),atoms[0]);
                chs.add(newChoice);
            }
        pick= roulette(chs);
        atoms=pick.split("\\.\\.");
        switch (atoms.length) {
        case 1:
            first=atoms[0].substring(1,(atoms[0].length()-1));
            try {
                Integer.parseInt(first); // is it an integer?
                result[0]=first;
                result[1]="int";
                return result;
            } catch (NumberFormatException e)
                {
                    try { Double.parseDouble(first); // is it a double?
                    result[0]=first;
                    result[1]="double";
                    return result;
                    } catch (NumberFormatException e2) { // it's a string!
                        // do nothing
                    }
                }
            break;
        case 2:
            first=atoms[0].substring(1,atoms[0].length());
            second=atoms[1].substring(0,(atoms[1].length()-1));
            try {
                bottomInt=Integer.valueOf(first); topInt=Integer.valueOf(second); // are they integers?
                if (pick.charAt(0)=='(')
                    bottomInt++;
                if (pick.charAt(pick.length()-1)==')')
                    topInt--;
                result[0]=String.valueOf(randInt(bottomInt,topInt));
                result[1]="int";
                return result;
            } catch (NumberFormatException e3)
                {
                    try {
                        big1=new BigDecimal(first); big2=new BigDecimal(second); // are they decimals?
                        maxScale=Math.max(big1.scale(),big2.scale());
                        randBig=new BigDecimal(randDouble(big1.doubleValue(),big2.doubleValue()));
                        resBig=randBig.setScale(maxScale,BigDecimal.ROUND_HALF_UP);
                        if ((pick.charAt(0)=='(') && (resBig.equals(big1)))
                            resBig=randBig.setScale(maxScale,BigDecimal.ROUND_CEILING);
                        else
                            if ((pick.charAt(pick.length()-1)==')') && (resBig.equals(big2)))
                                resBig=randBig.setScale(maxScale,BigDecimal.ROUND_FLOOR);
                        result[0]=resBig.toString();
                        result[1]="double";
                        return result;
                    } catch (NumberFormatException e4) {      //are they characters?
                        letters=pick.substring(1,(pick.length()-1));
                        if (letters.matches("\\w\\.\\.\\w"))
                            {
                                bottomInt=(int)letters.charAt(0);
                                topInt=(int)letters.charAt(letters.length()-1);
                                if (pick.charAt(0)=='(')
                                    bottomInt++;
                                if (pick.charAt(pick.length()-1)==')')
                                    topInt--;
                                result[0]=String.valueOf((char)randInt(bottomInt,topInt));
                                result[1]="char";
                                return result;
                            } //if
                    }
                }
        } //case

        result[0]=pick.substring(1,(pick.length()-1));
        if (result[0].matches("\\w"))
            result[1]="char";
        else  result[1]="str";
        return result;
    } //makeChoice

    private static String roulette(Vector <Choice> choices) {     //roulette wheel selection method
        int numOfCh=choices.size();
        int sumOfWeights=0;
        int [] cumNorm = new int[numOfCh];
        int cn=0, x;
        String madeChoice;
        for (int i=0; i<numOfCh; i++)
            sumOfWeights+=choices.get(i).chance; //calculating sum of all weights
        for (int i=0; i<numOfCh-1; i++) {
            cumNorm[i]=cn+choices.get(i).chance;
            cn=cumNorm[i];
        }
        cumNorm[numOfCh-1]=sumOfWeights;
        x=generator.nextInt(sumOfWeights)+1;
        int j=0;
        while (x>cumNorm[j]) j++;
        return choices.get(j).option;
    } //roulette

    private static class Choice {
        int chance;
        String option;
        Choice (int c, String s) {
            chance=c; option=s;
        }
    } //choice
}
