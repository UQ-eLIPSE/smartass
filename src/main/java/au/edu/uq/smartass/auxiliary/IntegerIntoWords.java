/**
 * @(#)IntegerIntoWords.java
 *
 * This file is part of SmartAss and describes class IntegerIntoWords for converting integer number 
 * into English.
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
 
public class IntegerIntoWords {
  private static final String[] majorNames = {
    "",
    " thousand",
    " million",
    " billion",
    " trillion",
    " quadrillion",
    " quintillion"
    };

  private static final String[] tensNames = {
    "",
    " ten",
    " twenty",
    " thirty",
    " fourty",
    " fifty",
    " sixty",
    " seventy",
    " eighty",
    " ninety"
    };

  private static final String[] numNames = {
    "",
    " one",
    " two",
    " three",
    " four",
    " five",
    " six",
    " seven",
    " eight",
    " nine",
    " ten",
    " eleven",
    " twelve",
    " thirteen",
    " fourteen",
    " fifteen",
    " sixteen",
    " seventeen",
    " eighteen",
    " nineteen"
    };

 private static String convertLessThanOneThousand(int number) {
    String soFar;

    if (number % 100 < 20){
        soFar = numNames[number % 100];
        number /= 100;
       }
    else {
        soFar = numNames[number % 10];
        number /= 10;

        soFar = tensNames[number % 10] + soFar;
        number /= 10;
       }
    if (number == 0) return soFar;
    return numNames[number] + " hundred" + soFar;
}

public static String convert(int number) {
    /* special case */
    if (number == 0) { return "zero"; }

    String prefix = "";

    if (number < 0) {
        number = -number;
        prefix = "negative";
      }

    String soFar = "";
    int place = 0;

    do {
      int n = number % 1000;
      if (n != 0){
         String s = convertLessThanOneThousand(n);
         soFar = s + majorNames[place] + soFar;
        }
      place++;
      number /= 1000;
      } while (number > 0);

    return (prefix + soFar).trim();
}
}
