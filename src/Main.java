import com.moratoium.compmath.lab1.model.linalg.system.LinearSystem;
import com.moratoium.compmath.lab1.model.linalg.system.SimpleIterationSolver;
import com.moratoium.compmath.lab1.view.ConsoleTable;

import java.io.File;
import java.util.Arrays;
import java.util.Locale;
import java.util.Scanner;

public class Main {
    static public void main(String... args) {
        try {
            enum Mode {
                CONSOLE,
                FILE
            }
            System.out.print("Would you like to enter matrix by file or console (c/f): ");
            char c = (char) System.in.read();
            Scanner sc = new Scanner(System.in);
            Mode mode = Mode.CONSOLE;
            if(c == 'f') {
                System.out.print("Please input file name: ");
                final String fileName = sc.next();
                sc = new Scanner(new File(fileName));
                mode = Mode.FILE;
            }
            if(mode == Mode.CONSOLE)
                System.out.print("Please enter matrix size: ");
            int size = sc.nextInt();
            if(mode == Mode.CONSOLE)
                System.out.print("Please enter accuracy: ");
            String accuracyStr = sc.next();
            double accuracy = Double.parseDouble(accuracyStr);

            LinearSystem system = new LinearSystem(size, size+1);

            for (int row = 0; row < system.getRows(); row++) {
                for (int column = 0; column < system.getColumns(); column++) {
                    system.set(row, column, sc.nextDouble());
                }
            }

            SimpleIterationSolver solver = new SimpleIterationSolver(accuracy);
            Double[] result = solver.solve(system);
        } catch (Exception e) {
            System.err.println(e.getMessage());
        }
    }
}
