/**
 * 
 */
package au.edu.uq.smartass.maths;

import au.edu.uq.smartass.auxiliary.RandomChoice;
import au.edu.uq.smartass.engine.Engine;

/**
 * @author carnivore
 *
 */
public class ComplexIndefiniteIntegralModule extends IndefiniteIntegralModule {

	/**
	 * @param engine	an instance of SmartAss engine 
	 */
	public ComplexIndefiniteIntegralModule(Engine engine) {
		super(engine);
		
		for(int i=0;i<MAX_PARTS;i++) {
			int param;
			int func = RandomChoice.randInt(0, 3);
			param = RandomChoice.randInt(-MAX_NUM/2, MAX_NUM/2); 
			if(func==0 && param!=-1)
				f[i] = new DerivateFunctions(func, 
						(param+1) * RandomChoice.randInt(1, MAX_NUM)*RandomChoice.randSign(),
						param,
						vx);
			else
				f[i] = new DerivateFunctions(func, 
						RandomChoice.randInt(1, MAX_NUM)*RandomChoice.randSign(),
						param,
						vx);
		}
		
		generate();
	}
}
