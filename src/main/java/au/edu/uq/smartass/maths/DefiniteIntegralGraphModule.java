package au.edu.uq.smartass.maths;

import au.edu.uq.smartass.auxiliary.RandomChoice;
import au.edu.uq.smartass.auxiliary.TexGraph;
import au.edu.uq.smartass.auxiliary.TexPlot;
import au.edu.uq.smartass.engine.Engine;

public class DefiniteIntegralGraphModule extends MathsModule {
	final int POLYNOM = 0;
	final int SIN = 1;
	final int COS = 2;
	
	final int MAX_CONST = 5;
	final int MAX_X = 5;
	
	final int stepsX = 300;
	final int stepsY = 100;
	
	MathsOp formula, integral;
	MathsOp x1, x2;
	MathsOp result;
	MathsOp solution1, solution2;
	MathsOp solution; 
	double graph_x[], graph_y[];
	double nx1, nx2, ny1, ny2;
	
	double minX, minY, maxX, maxY;
	
	MathsOp createTerm(int constant, int power, int divider) {
		return createTerm(constant, power, divider, new Variable("x"));
	}
	
	MathsOp createTerm(int constant, int power, int divider, MathsOp operand) {
		MathsOp f = MathsUtils.multiplyConstToPower(constant, operand, power); 
		if(divider!=1) 
			f = new FractionOp(f, new IntegerNumber(divider));
		return f;
	}
	
	MathsOp createDefTerm(int constant, int power, int divider, MathsOp operand) {
		MathsOp f; 
		if(power==0)
			f = new IntegerNumber(constant);
		else if(power==1)
			f = new Multiplication(new IntegerNumber(constant), operand );
		else
			f = new Multiplication(new IntegerNumber(constant), 
					new Power(operand, new IntegerNumber(power)));
		if(divider!=1)
			f = new FractionOp(f, new IntegerNumber(divider));
		return f;
	}
	
	private static double polyFunc(int[] consts, double x) {
		double res = 0;
		for(int j=0; j<4;  j++)
			res = res + consts[j]*Math.pow(x, j);
		return res;
	}
	
	public DefiniteIntegralGraphModule(Engine engine) {
		super(engine);

		int func = RandomChoice.randInt(0, 2);
		int num_points = 40;
		double r = 0;
		graph_x = new double[num_points+1];
		graph_y = new double[num_points+1];
		switch(func) {
		case POLYNOM: {
			int[] consts = new int[4];
			consts[0] = 5; RandomChoice.randInt(0, MAX_CONST);
			consts[1] = RandomChoice.randInt(0, MAX_CONST);
			consts[2] = RandomChoice.randInt(0, MAX_CONST);
			if(consts[2]==0)
				consts[3] = RandomChoice.randInt(1, MAX_CONST/2);
			else
				consts[3] = RandomChoice.randInt(0, MAX_CONST/2);

			
			int i1;
			if(polyFunc(consts, MAX_X)/(polyFunc(consts, 0)+0.0001)<=10)
				i1 = RandomChoice.randInt(0, MAX_X-1);
			else
				i1 = RandomChoice.randInt(MAX_X/2, MAX_X-1);
			int	i2 = RandomChoice.randInt(i1+1, MAX_X);
			x1 = new IntegerNumber(i1);
			x2 = new IntegerNumber(i2);

			int i=0;
			while(consts[i]==0) i++;
			formula = createTerm(consts[i], i, 1);
			integral = createTerm(consts[i], i+1, i+1);
			solution1 = createDefTerm(consts[i], i+1, i+1, x1);
			solution2 = createDefTerm(consts[i], i+1, i+1, x2);
			i++;
			while(i<4) {
				if(consts[i]!=0) {
					formula = new Addition(createTerm(consts[i], i, 1), formula);
					integral = new Addition(createTerm(consts[i], i+1, i+1), integral);
					solution1 = new Addition(createDefTerm(consts[i], i+1, i+1, x1), solution1);
					solution2 = new Addition(createDefTerm(consts[i], i+1, i+1, x2), solution2);
				}
				i++;
			}
			
			r = 0;
			for(i=0; i<4;  i++)
				r = r + (consts[i]*Math.pow(i2, i+1)/(i+1)) - (consts[i]*Math.pow(i1, i+1)/(i+1));
			result = new DecimalNumber(r);
			
			nx1 = i1; nx2 = i2; ny1 = 0; ny2 = 0;
			for(int j=0; j<4;  j++) {
				ny1 = ny1 + consts[j]*Math.pow(nx1, j);
				ny2 = ny2 + consts[j]*Math.pow(nx2, j);
			}
			
			minX = -1;
			maxX = i2+1;
			for(i=0;i<=num_points;i++) {
				graph_x[i] = minX + (maxX-minX)/num_points*i;
				graph_y[i] = 0;
				for(int j=0; j<4;  j++)
					graph_y[i] = graph_y[i] + consts[j]*Math.pow(graph_x[i], j);
			}
			break;
		}
		case SIN: {
			formula = new Sin(new Variable("x"));
			integral = new UnaryMinus(new Cos(new Variable("x")));
			
			int[] muls = {0, 1, 1, 3, 1};
			int[] divs = {1, 4, 2, 4, 1};

			int i1 = RandomChoice.randInt(0, divs.length-2);
			int i2 = RandomChoice.randInt(i1+1, divs.length-1);
			nx1 = Math.PI*muls[i1]/divs[i1];
			nx2 = Math.PI*muls[i2]/divs[i2];
			ny1 = Math.sin(nx1);
			ny2 = Math.sin(nx2);
			
			x1 = MathsUtils.createPiDivision(muls[i1], divs[i1]);
			x2 = MathsUtils.createPiDivision(muls[i2], divs[i2]);

			//use division for integral limits and fraction for cosine parameter
			solution1 = new UnaryMinus(new Cos(MathsUtils.createPiFraction(muls[i1], divs[i1])));
			solution2 = new UnaryMinus(new Cos(MathsUtils.createPiFraction(muls[i2], divs[i2])));
			
			r = - Math.cos(muls[i2]*Math.PI/divs[i2]) + Math.cos(muls[i1]*Math.PI/divs[i1]); 
			result = new DecimalNumber(r);

			double nx1 = -Math.PI;
			double nx2 = Math.PI;
			
			for(int i=0;i<=num_points;i++) {
				graph_x[i] = nx1 + (nx2-nx1)/num_points*i;
				graph_y[i] = Math.sin(graph_x[i]); 
			}
			break;
		}
		case COS:
			formula = new Cos(new Variable("x"));
			integral = new Sin(new Variable("x"));

			int[] muls = {-1, -1, 0, 1, 1};
			int[] divs = {2, 4, 1, 4, 2};
			int i1 = RandomChoice.randInt(0, divs.length-2);
			int i2 = RandomChoice.randInt(i1+1, divs.length-1);
			nx1 = Math.PI*muls[i1]/divs[i1];
			nx2 = Math.PI*muls[i2]/divs[i2];
			ny1 = Math.cos(nx1);
			ny2 = Math.cos(nx2);

			//use division for integral limits and fraction for sine parameter
			x1 = MathsUtils.createPiDivision(muls[i1], divs[i1]);
			x2 = MathsUtils.createPiDivision(muls[i2], divs[i2]);
			
			solution1 = new Sin(MathsUtils.createPiFraction(muls[i1], divs[i1]));
			solution2 = new Sin(MathsUtils.createPiFraction(muls[i2], divs[i2]));

			r = Math.sin(Math.PI*muls[i2]/divs[i2]) - Math.sin(Math.PI*muls[i1]/divs[i1]); 
			result = new DecimalNumber(r);
			
			double nx1 = -Math.PI/2;
			double nx2 = 3*Math.PI/2;
			
			for(int i=0;i<=num_points;i++) {
				graph_x[i] = nx1 + (nx2-nx1)/num_points*i;
				graph_y[i] = Math.cos(graph_x[i]); 
			}
			break;
		}

		solution = formula;
		solution = new DefiniteIntegral(x2, x1, formula, new Variable("x"));
		solution = new Equality(solution, new DeltaAntiderivative(x1, x2, integral));
		MathsOp solution_subs = null;
		if(func==COS)
			solution_subs = new Subtraction(solution2, solution1);
		else
			solution_subs = new Subtraction(new Brackets(solution2), new Brackets(solution1));
			
		if((func==POLYNOM))
			solution = new LineBreakEqualityOp(solution, solution_subs);
		else
			solution = new Equality(solution, solution_subs);
		if(Math.round(r)*100!=Math.round(r*100))
			solution = new Approx(solution, result);
		else
			solution = new Equality(solution, result);
			
		minY = 0;
		maxY = 0;
		
		for(int i=0;i<=num_points;i++) {
			if(minY>graph_y[i])
				minY = graph_y[i];
			if(maxY<graph_y[i])
				maxY = graph_y[i];
		}
		minY = Math.floor(minY);
		maxY = Math.ceil(maxY);
	}

	@Override
	public String getSection(String name) {
		if(name.toUpperCase().equals("QUESTION"))
			return formula.toString();
		else if(name.toUpperCase().equals("SHORTANSWER"))
			return result.toString();
		else if(name.toUpperCase().equals("X1"))
			return x1.toString();
		else if(name.toUpperCase().equals("X2"))
			return x2.toString();
		else if(name.toUpperCase().equals("SOLUTION")) 
			return "\\ensuremath{" + solution.toString() + "}";
		else if(name.toUpperCase().equals("GRAPH")) { 
			TexGraph graph = new TexGraph(graph_x, graph_y, 300, 150);
			return  graph.encloseGraph(300, 100, graph.drawAxes() + "\n" + graph.drawGraph() +
					graph.drawRegionUnderGraph(nx1, ny1, nx2, ny2));
		}
		
		return super.getSection(name);
	}
}
