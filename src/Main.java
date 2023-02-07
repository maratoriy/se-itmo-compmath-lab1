import com.moratoium.compmath.lab1.math.linalg.systems.LinearSolverType;
import com.moratoium.compmath.lab1.math.linalg.systems.LinearSystem;
import com.moratoium.compmath.lab1.math.linalg.systems.LinearSystemSolver;
import com.moratoium.compmath.lab1.math.linalg.systems.LinearSystemSolverFactory;

import java.util.Scanner;

public class Main {
    static public void main(String... args) {
        final Scanner scanner = new Scanner(System.in);
        final int rows = scanner.nextInt(), columns = scanner.nextInt();
        final LinearSystem system = new LinearSystem(rows, columns);

        for(int row = 0; row < rows; row++) {
            for(int column = 0; column < columns; column++) {
                system.set(row, column, scanner.nextDouble());
            }
        }

        LinearSystemSolver solver = LinearSystemSolverFactory.getSolver(LinearSolverType.SIMPLE_ITERATION, "0.01");


        System.out.println(system);
    }
}
