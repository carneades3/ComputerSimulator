/* 							 test of machine language programming
                                simulation                                                 
 */

package simpletron;
 
public class ComputerSimulatorTest {
   
   public static void main(String[] args) {
      java.io.PrintStream printStream = System.out;
      java.util.Scanner scannerToLoadProgram = new java.util.Scanner(System.in);
      java.io.PrintStream errorPrintStream = System.err;
      java.util.Scanner scannerToExecuteProgram = new java.util.Scanner(System.in);
      
      ComputerSimulator computerSimulator = new ComputerSimulator(printStream, scannerToLoadProgram, errorPrintStream,
                                                                  printStream, scannerToExecuteProgram, errorPrintStream);
      computerSimulator.loadExamplePrograms();
      
      computerSimulator.run();
   } 
   
} 
