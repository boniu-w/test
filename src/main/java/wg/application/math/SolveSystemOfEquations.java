package wg.application.math;

import org.apache.commons.math3.analysis.MultivariateMatrixFunction;
import org.apache.commons.math3.analysis.MultivariateVectorFunction;
import org.apache.commons.math3.fitting.leastsquares.*;
import org.apache.commons.math3.linear.RealVector;

/************************************************************************
 * author: wg
 * description: SolveSystemOfEquations
 * 解二元方程的经典案例
 * createTime: 15:02 2024/5/20
 * updateTime: 15:02 2024/5/20
 ************************************************************************/
public class SolveSystemOfEquations {
    public static void main(String[] args) {
        // Define the system of equations
        MultivariateVectorFunction functions = new MultivariateVectorFunction() {
            @Override
            public double[] value(double[] variables) {
                double x = variables[0];
                double y = variables[1];
                return new double[]{
                        x * x + y * y - 1, // f1(x, y) = x^2 + y^2 - 1
                        x * x - y          // f2(x, y) = x^2 - y
                };
            }
        };

        // Define the Jacobian matrix of the system of equations
        MultivariateMatrixFunction jacobian = new MultivariateMatrixFunction() {
            @Override
            public double[][] value(double[] variables) {
                double x = variables[0];
                double y = variables[1];
                return new double[][]{
                        {2 * x, 2 * y},  // ∂f1/∂x and ∂f1/∂y
                        {2 * x, -1}     // ∂f2/∂x and ∂f2/∂y
                };
            }
        };

        // Initial guess for the variables (x, y)
        double[] initialGuess = new double[]{0.5, 0.5};

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
