package wg.application.math;

import org.apache.commons.math3.analysis.MultivariateMatrixFunction;
import org.apache.commons.math3.analysis.MultivariateVectorFunction;
import org.apache.commons.math3.fitting.leastsquares.LeastSquaresBuilder;
import org.apache.commons.math3.fitting.leastsquares.LeastSquaresOptimizer;
import org.apache.commons.math3.fitting.leastsquares.LeastSquaresProblem;
import org.apache.commons.math3.fitting.leastsquares.LevenbergMarquardtOptimizer;
import org.apache.commons.math3.linear.RealVector;
import org.apache.commons.math3.special.Gamma;


/************************************************************************
 * author: wg
 * description: 解二元方程
 * createTime: 14:21 2024/5/20
 * updateTime: 14:21 2024/5/20
 ************************************************************************/
public class SolvingEquations {

    public static void main(String[] args) {
        // Define the system of equations
        MultivariateVectorFunction functions = new MultivariateVectorFunction() {
            @Override
            public double[] value(double[] variables) {
                double x = variables[0];
                double y = variables[1];
                double f1 = 1.0 / y + 1;
                double f2 = 2.0 / y + 1;
                return new double[]{
                        x * Gamma.gamma(f1) - 0.10287,
                        x * x * (Gamma.gamma(f2) - Gamma.gamma(f1) * Gamma.gamma(f1)) - 0.10287 * 0.45        // f2(x, y) = x^2 - y
                };
            }
        };

        MultivariateMatrixFunction jacobian = new MultivariateMatrixFunction() {
            @Override
            public double[][] value(double[] variables) {
                double x = variables[0];
                double y = variables[1];
                double f1 = 1.0 / y + 1;
                double f2 = 2.0 / y + 1;
                double gammaF1 = Gamma.gamma(f1);
                double gammaF2 = Gamma.gamma(f2);
                double gammaF1Prime = Gamma.digamma(f1) * gammaF1; // γ'(f) = ψ(f) * γ(f)
                double gammaF2Prime = Gamma.digamma(f2) * gammaF2;

                return new double[][] {
                        { gammaF1, x * gammaF1Prime * (-1 / (y * y)) }, // ∂g1/∂x and ∂g1/∂y
                        { 2 * x * (gammaF2 - gammaF1 * gammaF1),
                                x * x * (gammaF2Prime * (-2 / (y * y)) - 2 * gammaF1 * gammaF1Prime * (-1 / (y * y))) } // ∂g2/∂x and ∂g2/∂y
                };
            }
        };

        // Initial guess for the variables (x, y)
        double[] initialGuess = new double[]{2, 1};

        // Create the least squares problem
        LeastSquaresProblem problem = new LeastSquaresBuilder()
                .start(initialGuess)
                .model(functions, jacobian)
                .target(new double[]{0, 0})
                .lazyEvaluation(false)
                .maxEvaluations(1000)
                .maxIterations(1000)
                .build();

        // Use LevenbergMarquardtOptimizer to solve the problem
        LevenbergMarquardtOptimizer optimizer = new LevenbergMarquardtOptimizer();

        LeastSquaresOptimizer.Optimum optimum = optimizer.optimize(problem);

        // Get the solution
        RealVector solution = optimum.getPoint();

        // Print the solution
        System.out.println("Solution:");
        System.out.println("x = " + solution.getEntry(0));
        System.out.println("y = " + solution.getEntry(1));
    }
}
