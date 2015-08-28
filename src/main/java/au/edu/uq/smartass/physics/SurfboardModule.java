package au.edu.uq.smartass.physics;

import java.math.*;
import au.edu.uq.smartass.auxiliary.RandomChoice;
import au.edu.uq.smartass.engine.Engine;
import au.edu.uq.smartass.maths.*;
/**
 * Module to generate questions where a block of buoyant material
 * with a random density has to support a random mass, with a given
 * percentage of the block underwater (defaults to 1) with the
 * question being to find the minimum mass of the block required to
 * support the mass in equilibrium (i.e. if the mass weighed any more,
 * then it would start to sink) to 2 decimal places.
 *
 *
 * Created: Wed Jun 27 10:51:36 2007
 *
 * @author <a href="mailto:ilm@lisa.maths.uq.edu.au">Ivan Miljenovic</a>
 * @version 1.0
 */

public class SurfboardModule extends MathsModule {

    private final static int MIN_PERCENT = 0;
    private final static int MAX_PERCENT = 100;

    private final static int MIN_UNDERWATER = 40;
    private final static int MAX_UNDERWATER = 85;

    private final static int MIN_DENS = 10;
    private final static int MAX_DENS = 20;

    private final static int MIN_MASS = 80;
    private final static int MAX_MASS = 150;

    //Mass is in kg, volume is in m^3 underwaterA and underwaterR are
    //values specifying the percentage of the block that is underwater
    //for the whole block being underwater and a random percentage
    //being underwater.

    private int underwaterA = MAX_PERCENT;

    private int underwaterR, density, mass;

    // 1000 L = 1 m^3, so density of water is 1000

    private double densWater = 1000.0;


    public SurfboardModule(Engine engine) {
	super(engine);
	density = RandomChoice.randInt(MIN_DENS,MAX_DENS);
	mass = RandomChoice.randInt(MIN_MASS,MAX_MASS);
	underwaterR = RandomChoice.randInt(MIN_UNDERWATER,MAX_UNDERWATER);
    }

    private DecimalNumber solve(int p) {

	double percentage = 1.0*p/MAX_PERCENT;
	
	double blockmass = mass/(percentage*densWater/density - 1);
	
	DecimalNumber block = new DecimalNumber(blockmass);
	
	block.setRoundingMode(RoundingMode.CEILING);
	
	return block;
    }
    /**
     * @param engine 	an instance of smartass {@link Engine} 
     * @param params    array of Strings representing passing parameters, 
     * params[0] - string representing integer density of the block material,
     * params[1] - mass of the person,
     * params[2] - part of the block, that will remain underwater,
     * params[3] (optional) - water density
     */
    public SurfboardModule(Engine engine, String[] params){
    	super(engine);
    	density = Integer.parseInt(params[0]); 
        mass = Integer.parseInt(params[1]);
	underwaterR = Integer.parseInt(params[2]);
	if(params.length==4) {
	    densWater = Double.parseDouble(params[3]);
	}
    }
    
    
    public String getSection(String name) {
	if (name.equals("blockdensity")) {
	    return toTex(density);
	}
	
	if (name.equals("objectmass")) {
	    return toTex(mass);
	}

	if (name.equals("blockmassall")) {
	    return toTex(solve(underwaterA));
	}

	if (name.equals("blockmassrandom")) {
	    return toTex(solve(underwaterR));
	}

	if (name.equals("liquiddensity")) {
	    return toTex(densWater);
	}

	if (name.equals("percentunderwaterall")) {
	    return toTex(underwaterA);
	}

	if (name.equals("percentabovewaterall")) {
	    return toTex(percentAbove(underwaterA));
	}

	if (name.equals("percentunderwaterrandom")) {
	    return toTex(underwaterR);
	}

	if (name.equals("percentabovewaterrandom")) {
	    return toTex(percentAbove(underwaterR));
	}

	if (name.equals("decimalunderwaterall")) {
	    return toTex(asDecimal(underwaterA));
	}

	if (name.equals("decimalabovewaterall")) {
	    return toTex(asDecimal(percentAbove(underwaterA)));
	}

	if (name.equals("decimalunderwaterrandom")) {
	    return toTex(asDecimal(underwaterR));
	}

	if (name.equals("dec")) {
	    return toTex(asDecimal(underwaterR));
	}

	if (name.equals("decimalabovewaterrandom")) {
	    return toTex(asDecimal(percentAbove(underwaterR)));
	}

        return super.getSection(name);
    }

    private String toTex(int m) {
	return "\\ensuremath{" + m + "}";
    }

    private String toTex(double m) {
	return "\\ensuremath{" + m + "}";
    }

    private String toTex(Object m) {
	return "\\ensuremath{" + m + "}";
    }

    private int percentAbove(int p) {
	return MAX_PERCENT - p;
    }

    private double asDecimal(int p) {
	return p/100.0;
    }


}
