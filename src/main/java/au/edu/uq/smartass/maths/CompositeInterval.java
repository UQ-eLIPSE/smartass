package au.edu.uq.smartass.maths;

import java.math.BigDecimal;
import java.util.ArrayList;

public class CompositeInterval extends MathsOp {
	ArrayList<SingleInterval> intervals = new ArrayList<SingleInterval>();

	public CompositeInterval(SingleInterval interval) {
		super();
		intervals.add(interval);
	}

	public CompositeInterval() {
		super();
		intervals.add(new SingleInterval(SingleInterval.NEGATIVE_INF, SingleInterval.POSITIVE_INF, false, false));
	}

	public CompositeInterval add(SingleInterval interval) {
		int found = -1;
		SingleInterval t = null;
		//Find first existing interval (local t) that intersects with new interval 
		for(int i=0; i<intervals.size();i++) {
			t = intervals.get(i);
			if(interval.l.compareTo(t.r)==-1 || interval.l.compareTo(t.r)==0 && interval.l_inclusive) { 
				//Is all interval at left of t?  
				if(interval.r.compareTo(t.l)==-1 || 
						interval.r.compareTo(t.l)==0 && !interval.r_inclusive && !t.l_inclusive) {
					intervals.add(i, interval); //add to this CompositeInterval
					return this; 
				} else //no, interval is really intersects with t
					if(interval.l.compareTo(t.l)==-1) { //left margin of interval is less then left margin of t 
						t.l = interval.l; //set left margin of t to interval left margin
						t.l_inclusive = interval.l_inclusive;
					} 
					else if(interval.l.compareTo(t.l)==0)
						t.l_inclusive |= interval.r_inclusive;
					//otherwise do nothing - left margin of interval is inside of t
				found = i;
				break;
			}
		}
		if(found>-1) { //yes, such interval is found
			if(interval.r.compareTo(t.r)==1) {
				int i=found+1;
				while(i<intervals.size()) {
					SingleInterval tt = intervals.get(i);
						if(interval.r.compareTo(tt.l)==-1 || 
								interval.r.compareTo(tt.l)==0 && !interval.r_inclusive && !tt.l_inclusive) {
							t.r = interval.r;
							t.r_inclusive = interval.r_inclusive;
							break;
						} else {
							if(interval.r.compareTo(tt.r)<1) {
								t.r = tt.r;
								if(interval.r.compareTo(tt.r)==0)
									t.r_inclusive = (tt.r_inclusive || interval.r_inclusive);
								else
									t.r_inclusive = tt.r_inclusive;
								intervals.remove(tt);
								return this;
							} else 
								intervals.remove(tt);
							
						}
				}
				if(i==intervals.size()) {
					t.r = interval.r;
					t.r_inclusive = interval.r_inclusive;
				}
			} else if(t.r==interval.r)
				t.r_inclusive |= interval.r_inclusive; 
		} else //no, put new interval at the end of the list
			intervals.add(interval);
		return this;	
	}

	public CompositeInterval subtract(SingleInterval interval) {
		SingleInterval t = null;
		//Find first existing interval (local t) that intersects with interval subtracted 
		for(int i=0; i<intervals.size();i++) {
			t = intervals.get(i);
			if(interval.l.compareTo(t.r)==-1
					|| interval.l.compareTo(t.r)==0 && (interval.l_inclusive && t.r_inclusive)) {
				if(interval.l.compareTo(t.l)==-1 //left margin of interval is below left margin of t
						|| interval.l.compareTo(t.l)==0 && (interval.l_inclusive || !t.l_inclusive)) {
					if(interval.r.compareTo(t.r)==1  //right margin of interval is above rigth margin of t 
							|| interval.r.compareTo(t.r)==0 && (interval.r_inclusive || !t.r_inclusive))
						intervals.remove(i--); //remove t - it is completely covered by interval 
					else if(interval.r.compareTo(t.l)==1) { //crop t from left
						t.l = interval.r;
						t.l_inclusive = !interval.r_inclusive;
					} else if(interval.r.compareTo(t.l)==0)
						t.l_inclusive &= !interval.r_inclusive;
					else //otherwise interval does not intersects with t and lay at left of it 
						//there is nothing more to do, so leave subtract 
						break; 
				} else { //interval crops a part from inside of t
					if(interval.r.compareTo(t.r)==-1)  //split t to 2 subintervals
						intervals.add(++i, new SingleInterval(interval.r, t.r,!interval.r_inclusive,t.r_inclusive));
					//crop t from right
					t.r = interval.l;
					t.r_inclusive &= !interval.l_inclusive;
				}
			}
		}
		return this;	
	}

	public CompositeInterval shift(BigDecimal val) {
		for(int i=0;i<intervals.size();i++) 
			intervals.get(i).shift(val);
		return this;	
	}
	
	public CompositeInterval scale(BigDecimal val) {
		for(int i=0;i<intervals.size();i++) 
			intervals.get(i).scale(val);
		return this;	
	}
	
	public CompositeInterval mirror() {
		ArrayList<SingleInterval> t = new ArrayList<SingleInterval>();
		for(int i=intervals.size()-1;i>=0;i--) {
			intervals.get(i).mirror();
			t.add(intervals.get(i));
		}
		intervals = t;
		return this;	
	}

	public CompositeInterval add(CompositeInterval interval) {
		for(int i=0;i<interval.intervals.size();i++)
			add(interval.intervals.get(i));
		return this;	
	}
	
	public CompositeInterval subtract(CompositeInterval interval) {
		for(int i=0;i<interval.intervals.size();i++)
			subtract(interval.intervals.get(i));
		return this;	
	}
	
	public String toString() {
		if(intervals.size()==0)
			return " empty ";
		String s = intervals.get(0).toString();
		for(int i=1; i<intervals.size();i++) 
			s += " \\cup "+ intervals.get(i).toString();
		return s;
	}
	
	public String asComparison(MathsOp var) { 
		if(intervals.size()==0)
			return " empty ";
		if(intervals.size()==2 && (intervals.get(0).r.equals(intervals.get(1).l)))
			return var.toString() + "\\ne" + intervals.get(0).r;
		String s = intervals.get(0).asComparison(var).toString();
		for(int i=1; i<intervals.size();i++) 
			s += ", "+ intervals.get(i).asComparison(var).toString();
		return s;
	}
	
	public Object clone() {
		CompositeInterval ci = (CompositeInterval) super.clone();
		ci.intervals = new ArrayList<SingleInterval>();
		for(int i=0;i<intervals.size();i++)
			ci.intervals.add((SingleInterval)intervals.get(i).clone());
		return ci;
	}
}
