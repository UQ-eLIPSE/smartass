/** @(#)CarHireModule.java
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

import au.edu.uq.smartass.auxiliary.RandomChoice;
import au.edu.uq.smartass.engine.Engine;
import java.util.*;

public class CarHireModule extends MathsModule {

    final static private int MIN_LEASE = 15;
    final static private int MAX_LEASE = 30;

    final static private int MIN_LEASE_LUMP = 1600;
    final static private int MAX_LEASE_LUMP = 2400;

    final static private int MIN_LEASE_EXTRA = 30;
    final static private int MAX_LEASE_EXTRA = 40;

    //These are the cutoffs for a given hire period,
    //i.e. 3-6, 7-13, 14-20 and 21+ days
    final static private int[] HIRE_DAYS = {3,7,14,21,28,35};

    /*
      Upper/lower bounds on the first hire period.
      The second period will be discounted by $10/day,
      and the rest will be discounted by $3/day.
    */
    final static private int MIN_HIRE_FIRST = 70;
    final static private int MAX_HIRE_FIRST = 100;

    final static private int HIRE_DISCOUNT = 5;

    final static private int DEFAULT_NUM_RENTAL_PERIODS = 3;
    final static private int MIN_RENTAL_PERIOD = 5;
    final static private int MAX_RENTAL_PERIOD = 35;
    final static private int PERIOD_GAP = 20;

    //How many days we're interested in
    private int[] rental_periods;

    private int lease_period, lease_lump, lease_extra;

    private int[] hire_rates = new int[HIRE_DAYS.length];

    private int[] lease_cost;
    private int[] hire_cost;
    private String[] which_cheaper;

    //The second part features a second leasing company

    private int lease_period2, lease_lump2, lease_extra2, min_days, max_days, num_days;

    double ddays;

    boolean firstCheaper;


    /**
     * @param engine    engine instance
     */
    public CarHireModule(Engine engine) {
        super(engine);

        rental_periods = new int[DEFAULT_NUM_RENTAL_PERIODS];

        int prev_period = MIN_RENTAL_PERIOD;

        for (int i = 0; i < DEFAULT_NUM_RENTAL_PERIODS; i++) {

            prev_period = RandomChoice.randInt(prev_period,
                                               MAX_RENTAL_PERIOD + i * PERIOD_GAP);
            rental_periods[i] = prev_period;

            prev_period += PERIOD_GAP;
        }

        generate();
    }

    private void generate() {

        lease_period = RandomChoice.randInt(MIN_LEASE, MAX_LEASE);

        lease_lump = RandomChoice.randInt(MIN_LEASE_LUMP, MAX_LEASE_LUMP);

        lease_extra = RandomChoice.randInt(MIN_LEASE_EXTRA, MAX_LEASE_EXTRA);

        hire_rates[0] = RandomChoice.randInt(MIN_HIRE_FIRST, MAX_HIRE_FIRST);

        for (int i = 1; i < hire_rates.length; i++) {
            hire_rates[i] = hire_rates[i-1] - HIRE_DISCOUNT;
        }


        lease_cost = new int[rental_periods.length];
        hire_cost = new int[rental_periods.length];
        which_cheaper = new String[rental_periods.length];

        for (int i = 0; i < rental_periods.length; i++) {
            lease_cost[i] = getLeasePrice(rental_periods[i]);
            hire_cost[i] = getHirePrice(rental_periods[i]);
            which_cheaper[i] = compMessage(lease_cost[i], hire_cost[i]);
        }

        //For the second bit

        num_days = 0;

        do {

            lease_period2 = RandomChoice.randInt(MIN_LEASE, MAX_LEASE);

            lease_lump2 = RandomChoice.randInt(MIN_LEASE_LUMP, MAX_LEASE_LUMP);

            do {
                lease_extra2 = RandomChoice.randInt(MIN_LEASE_EXTRA, MAX_LEASE_EXTRA);
            } while (lease_extra2 == lease_extra);

            min_days = Math.min(lease_period, lease_period2);
            max_days = Math.max(lease_period, lease_period2);

            firstCheaper = getLeasePrice(max_days) < getLeasePrice2(max_days);

            //In case lease_extra == lease_extra2
            try {
                ddays = ((1.0 * (lease_lump2 - lease_lump) +
                          (lease_extra * lease_period - lease_extra2 * lease_period2)) /
                         (lease_extra - lease_extra2));

                num_days = (int) (Math.ceil(ddays));
            } catch (Exception e) {}

        } while (num_days <= max_days);

    }

    /**
     * Composes section with given name
     * "question", "shortanswer" and "solution" sections are recognised
     *
     * @param name  section name
     * @return the LaTeX representation of the required section
     **/
    public String getSection(String name) {

        if (name.equals("leaseperiod")) {
            return toTex(lease_period);
        }

        if (name.equals("leaselump")) {
            return toCurrency(lease_lump);
        }

        if (name.equals("leaseextra")) {
            return toCurrency(lease_extra);
        }

        if (name.matches("hireperiod([0-9]+)")) {
            try {
                int i = Integer.parseInt(name.substring(10));
                return toTex(HIRE_DAYS[i-1]);
            } catch (Exception e) {}
        }

        if (name.matches("hirerate([0-9]+)")) {
            try {
                int i = Integer.parseInt(name.substring(8));
                return toCurrency(hire_rates[i-1]);
            } catch (Exception e) {}
        }

        if (name.equals("hiretable")) {
            return hireTable();
        }

        if (name.equals("listperiods")) {
            return listPeriods();
        }

        if (name.matches("period([0-9]+)")) {
            try {
                int i = Integer.parseInt(name.substring(8));
                return toTex(rental_periods[i-1]);
            } catch (Exception e) {}
        }

        if (name.matches("leasecost([0-9]+)")) {
            try {
                int i = Integer.parseInt(name.substring(9));
                return toCurrency(lease_cost[i-1]);
            } catch (Exception e) {}
        }

        if (name.matches("hirecost([0-9]+)")) {
            try {
                int i = Integer.parseInt(name.substring(8));
                return toCurrency(hire_cost[i-1]);
            } catch (Exception e) {}
        }

        if (name.matches("whichcheaper([0-9]+)")) {
            try {
                int i = Integer.parseInt(name.substring(12));
                return which_cheaper[i-1];
            } catch (Exception e) {}
        }

        if (name.equals("comptable")) {
            return comparisonTable();
        }

        if (name.equals("allworking")) {
            return workAll();
        }

        if (name.matches("working([0-9]+)")) {
            try {
                int i = Integer.parseInt(name.substring(7));
                return work(rental_periods[i-1]);
            } catch (Exception e) {}
        }

        if (name.matches("solvefordays([0-9]+)")) {
            try {
                int i = Integer.parseInt(name.substring(13));
                return work(i);
            } catch (Exception e) {}
        }

        //section 2

        if (name.equals("leaseperiod2")) {
            return toTex(lease_period2);
        }

        if (name.equals("leaselump2")) {
            return toCurrency(lease_lump2);
        }

        if (name.equals("leaseextra2")) {
            return toCurrency(lease_extra2);
        }

        if (name.equals("mindays")) {
            return toTex(min_days);
        }

        if (name.equals("maxdays")) {
            return toTex(max_days);
        }

        if (name.equals("numleasedays")) {
            return toTex(num_days);
        }

        if (name.equals("compareleasing")) {
            return compLeasing();
        }

        if (name.equals("initialcheaper")) {
            return firstCheaper ? "first" : "second";
        }

        if (name.equals("latercheaper")) {
            return (!firstCheaper) ? "first" : "second";
        }

        return super.getSection(name);
    }

    private String toTex(Object m) {
        return "\\ensuremath{" + m.toString() + "}";
    }

    private String toTex(int m) {
        return "\\ensuremath{" + m + "}";
    }

    private String toCurrency(int m) {
        Currency c = new Currency(m);
        return toTex(c);
    }

    private String hireTable() {
        StringBuffer result = new StringBuffer("\\begin{tabular}{c|c}\n ");
        result.append("  Minimum Number of Days & Daily Rate \\\\ \n ");
        result.append("  \\hline \\hline \n ");

        for (int i = 0; i < hire_rates.length; i++) {
            result.append("  " + HIRE_DAYS[i] + " & " + toCurrency(hire_rates[i]) + " \\\\ \n ");
        }

        result.append("\\end{tabular} \n ");

        return result.toString();
    }

    private int getLeasePrice(int days) {
        int price = lease_lump;

        int extra = days - lease_period;

        if (extra > 0) {
            price += extra * lease_extra;
        }

        return price;
    }

    private int getLeasePrice2(int days) {
        int price = lease_lump2;

        int extra = days - lease_period2;

        if (extra > 0) {
            price += extra * lease_extra2;
        }

        return price;
    }

    private int getHirePrice(int days) {

        return hire_rates[getHireRate(days)] * days;
    }

    private int getHireRate(int days) {

        if (days < HIRE_DAYS[0]) {
            throw new NumberFormatException("Not enough days");
        }

        int i = 0;

        while ((i < HIRE_DAYS.length) && (days >= HIRE_DAYS[i])) {
            i++;
        }

        return i - 1;

    }

    private String comparisonTable() {

        StringBuffer result = new StringBuffer("\\begin{tabular}{c||c|c|c}\n ");
        result.append("  Number of Days & Leasing Cost & Hiring Cost & Which is Cheaper? \\\\ \n ");
        result.append("  \\hline \\hline \n ");

        for (int i = 0; i < rental_periods.length; i++) {
            result.append("  " + rental_periods[i] + " & " + toCurrency(lease_cost[i]));
            result.append(" & " + toCurrency(hire_cost[i]) + " & " + which_cheaper[i] + " \\\\ \n ");
        }

        result.append("\\end{tabular} \n ");

        return result.toString();
    }

    private static String compMessage(int lease, int hire) {
        int m = compareCosts(lease,hire);

        switch (m) {
        case -1: return "Leasing";
        case 1: return "Hiring";
        default: return "Same Price";
        }
    }

    private static int compareCosts(int lease, int hire) {

        if (lease < hire) {
            return -1;
        } else if (hire < lease) {
            return 1;
        } else {
            return 0;
        }
    }

    private String work(int days) {

        String[] oldEqTex;

        IntegerNumber numDays = new IntegerNumber(days);

        int hRate = getHireRate(days);
        String rPeriod = "" + (hRate + 1) + ending(hRate);
        Currency payRate = new Currency(hire_rates[hRate]);

        boolean leaseExtra = days > lease_period;
        int extra = days - lease_period;

        int leasePrice = getLeasePrice(days);
        Variable l = new Variable("l");
        l.setValue(new Currency(leasePrice));

        MathsOp lworking = l.getValue();

        if (leaseExtra) {
            lworking = new Equality(new Addition(new Currency(lease_lump),
                                                 new Multiplication(new Currency(lease_extra),
                                                                    new IntegerNumber(extra))),
                                    lworking);
            lworking = new Equality(new Addition(new Currency(lease_lump),
                                                 new Multiplication(new Currency(lease_extra),
                                                                    new Subtraction(new IntegerNumber(lease_period),
                                                                                    numDays))),
                                    lworking);
        }

        lworking = new Equality(l, lworking);

        int hirePrice = getHirePrice(days);
        Variable h = new Variable("h");
        h.setValue(new Currency(hirePrice));

        MathsOp hworking = new Equality(new Multiplication(payRate,
                                                           numDays),
                                        h.getValue());
        hworking = new Equality(h,hworking);

        //Leasing
        StringBuffer working = new StringBuffer("Since the car is required for " + numDays + " days, we ");
        working.append(leaseExtra ? "will " : "won't ");
        working.append("have to consider extra days for leasing above the standard ");
        working.append("leasing period of " + lease_period + " days.  ");

        working.append("Let the cost for leasing be $"+l+"$.  ");

        if (leaseExtra) {
            working.append("Since we have the extra days to take into account, ");
            working.append("the cost is the standard leasing amount as well as the extra rate.");
        } else {
            working.append("Since we don't have any extra days to worry about, ");
            working.append("the leasing cost is just the standard lump sum.");
        }

        working.append("  So the total cost for leasing is:\n");
        working.append("\\begin{displaymath}\n  " + lworking + "\n\\end{displaymath}\n\n");


        working.append("Hiring a car for " + numDays + " falls into the " + rPeriod);
        working.append(" payment bracket, so the daily hire rate will be " + payRate + ".\n\n");

        working.append("If we let the cost for hiring be $"+h+"$, we have that the cost for ");
        working.append("hiring will be:\n");
        working.append("\\begin{displaymath}\n  " + hworking + "\n\\end{displaymath}\n\n");

        working.append("Comparing these two costs, we can see that ");
        working.append(workMessage(leasePrice,hirePrice) + ".\n");

        return working.toString();
    }

    private String ending(int value) {
        int hundredRemainder = value % 100;
        int tenRemainder = value % 10;
        if(hundredRemainder - tenRemainder == 10) {
            return "th";
        }

        switch (tenRemainder) {
        case 1:
            return "st";
        case 2:
            return "nd";
        case 3:
            return "rd";
        default:
            return "th";
        }
    }

    private static String workMessage(int lease, int hire) {
        int m = compareCosts(lease,hire);

        switch (m) {
        case -1: return "\\emph{leasing} is the cheaper option";
        case 1: return "\\emph{hiring} is the cheaper option";
        default: return "there is no difference in price between the two options";
        }
    }

    private String listPeriods() {
        StringBuffer result = new StringBuffer("\\begin{enumerate}\n");

        for (int days : rental_periods) {
            result.append("  \\item " + days + " days\n");
        }

        result.append("\\end{enumerate}");

        return result.toString();
    }

    private String workAll() {
        StringBuffer result = new StringBuffer("\\begin{enumerate}\n");

        for (int wk : rental_periods) {
            result.append("  \\item\n " + work(wk) + " \n\n");
        }

        result.append("\\end{enumerate}");

        return result.toString();
    }

    private String compLeasing() {

        IntegerNumber maxDays = new IntegerNumber(max_days);

        String[] oldEqTex, oldPreTex;

        oldEqTex = MathsOp.getTex(Equality.class);
        MathsOp.setTex(Equality.class, new String[]{"& = &"});

        oldPreTex = MathsOp.getTex(Precedence.class);
        MathsOp.setTex(Precedence.class, new String[]{"\\\\\n    \\Longrightarrow &"});

        DecimalNumber dDays = new DecimalNumber(ddays,2);

        Variable d = new Variable("d");
        d.setValue(new IntegerNumber(num_days));

        Variable l1 = new Variable("l_1");
        l1.setValue(new Currency(lease_lump));

        Variable p1 = new Variable("p_1");
        p1.setValue(new IntegerNumber(lease_period));

        Variable e1 = new Variable("e_1");
        e1.setValue(new Currency(lease_extra));

        Variable c1 = new Variable("c_1");
        c1.setValue(new Addition(l1,
                                 new Multiplication(e1,
                                                    new Subtraction(d,p1))));


        Variable l2 = new Variable("l_2");
        l2.setValue(new Currency(lease_lump2));

        Variable p2 = new Variable("p_2");
        p2.setValue(new IntegerNumber(lease_period2));

        Variable e2 = new Variable("e_2");
        e2.setValue(new Currency(lease_extra2));

        Variable c2 = new Variable("c_2");
        c2.setValue(new Addition(l2,
                                 new Multiplication(e2,
                                                    new Subtraction(d,p2))));

        MathsOp solution = new Equality(c1.getValue(), c2.getValue());

        solution = new Precedence(solution,
                                  new Equality(new Subtraction(new Multiplication(e1,d),
                                                               new Multiplication(e2,d)),
                                               new Addition(new Subtraction(l2,
                                                                            l1),
                                                            new Subtraction(new Multiplication(e1,p1),
                                                                            new Multiplication(e2,p2)))));
        solution = new Precedence(solution,
                                  new Equality(new Multiplication(d,
                                                                  new Subtraction(e1,e2)),
                                               new Addition(new Brackets(new Subtraction(l2,l1)),
                                                            new Brackets(new Subtraction(new Multiplication(e1,p1),
                                                                                         new Multiplication(e2,p2))))));

        solution = new Precedence(solution,
                                  new Equality(d,
                                               new FractionOp(new Addition(new Brackets(new Subtraction(l2,l1)),
                                                                           new Brackets(new Subtraction(new Multiplication(e1,p1),
                                                                                                        new Multiplication(e2,p2)))),
                                                              new Subtraction(e1,e2))));

        solution = new Precedence(solution,
                                  new Equality(d,
                                               new FractionOp(new Addition(new Brackets(new Subtraction(l2.getValue(),
                                                                                                        l1.getValue())),
                                                                           new Brackets(new Subtraction(new Multiplication(e1.getValue(),
                                                                                                                           p1.getValue()),
                                                                                                        new Multiplication(e2.getValue(),
                                                                                                                           p2.getValue())))),
                                                              new Subtraction(e1.getValue(),
                                                                              e2.getValue()))));

        solution = new Precedence(solution,
                                  new Equality(d,dDays));

        StringBuffer working = new StringBuffer("Let $"+d+"$ represent ");
        working.append("the crossover point (i.e. the point when the ");
        working.append(firstCheaper ? "second" : "first");
        working.append(" leasing option becomes cheaper), and $"+l1+"$, $"+p1+"$, and $"+e1+"$ ");
        working.append("represent the standard leasing price, the standard leasing length and ");
        working.append("the rate per extra day respectively for the first company (similarly with ");
        working.append("$"+l2+"$, $"+p2+"$, and $"+e2+"$ representing those values for the second ");
        working.append("leasing company).  Knowing that $"+d+"$ is higher than both $"+p1+"$ and ");
        working.append("$"+p2+"$, we can find the costs for the two leasing companies:\n\n");

        working.append("\\begin{eqnarray*}\n ");
        working.append("  " + c1.varEquality() + " \\\\\n");
        working.append("  " + c2.varEquality() + " \\\\\n");
        working.append("\\end{eqnarray*}\n\n");

        working.append("Since we want to find the crossover point, we can let these two expressions ");
        working.append("equal each other and solve for $"+d+"$: \n\n");

        //Need the "      & " here as the first row has no precedence operator
        working.append("\\begin{displaymath}\n  \\begin{array}{lrcl}\n");
        working.append("      & " + solution);
        working.append("  \n\\end{array}\n\\end{displaymath}\n\n");

        MathsOp.setTex(Equality.class, oldEqTex);
        MathsOp.setTex(Precedence.class, oldPreTex);

        if (num_days != ddays) {
            working.append("However, we require an integer number of days.  Since we require the ");
            working.append("minimum number of days for which the ");
            working.append(firstCheaper ? "second" : "first");
            working.append(" leasing company is cheapest, we need to round this value up.  Hence: \n");
            working.append("\\begin{displaymath}\n  " + d.varEquality() + "\n\\end{displaymath}\n\n");
        }

        working.append("So the " + (firstCheaper ? "second" : "first") + " leasing company is ");
        working.append("becomes the cheaper option when the car is required for ");
        working.append((num_days == ddays) ? "at least" : "more than");
        working.append(" " + num_days + " days");


        return working.toString();
    }


    private class Currency extends UnaryOp {

        final String DOLLAR_SIGN = "\\$"; //default tex

        public Currency(int v) {
            super(new IntegerNumber(v));
            texSet();
        }

        public Currency(Integer v) {
            super(new IntegerNumber(v));
            texSet();
        }

        public Currency(MathsOp v) {
            super(v);
            texSet();
        }

        private void texSet() {
            if (getTex()==null)
                setTex(new String[]{DOLLAR_SIGN});
        }

        public String toString() {
            return getTex()[0] + op.toString();
        }
    }
}
