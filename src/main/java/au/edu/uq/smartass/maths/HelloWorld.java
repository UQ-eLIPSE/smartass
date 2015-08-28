/**
 *
 */
 
package au.edu.uq.smartass.maths;

import au.edu.uq.smartass.engine.*;
 
public class HelloWorld extends MathsModule {
	public HelloWorld(Engine engine) 
	{
		super(engine);
	}
	public String printQuestion()
	{
		return "Hello, world?";
	};
	public String printSolution()
	{
		return "Hello, world!";
	};
	//public void generate(String param) {};
}
