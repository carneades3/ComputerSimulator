/* 					 simulation of machine language programming
 */
 
package simpletron;
 
import java.io.PrintStream;
import java.util.Scanner;
 
public class ComputerSimulator {
   
   private final ProgramLoading programLoading;
   private final ProgramExecution programExecution;
   
   public static final int MEMORY_ELEMENTS = 1000;                                             
   private int[] memory = new int[MEMORY_ELEMENTS];
   
   public static final int OPERATION_CODE_LENGTH  = 3;
   public static final int MEMORY_LOCATION_LENGTH = 3;
   public static final int INSTRUCTION_LENGTH = OPERATION_CODE_LENGTH + MEMORY_LOCATION_LENGTH;
   
   public static final int MAX_DATA    = +999_999;
   public static final int MIN_DATA    = -999_999;
   
   public static final int READ_INT       = 0x00a;
   public static final int WRITE_INT      = 0x00b;
   public static final int READ_FLOAT     = 0x00c;
   public static final int WRITE_FLOAT    = 0x00d;
   public static final int READ_STRING    = 0x00e;
   public static final int WRITE_STRING   = 0x00f;
   public static final int WRITE_LINE     = 0x02f;
   
   public static final int ADD_INT              = 0x01a;
   public static final int SUBTRACT_INT         = 0x01b;
   public static final int DIVIDE_INT           = 0x01c;
   public static final int MODULO_INT           = 0x01d;
   public static final int MULTIPLY_INT         = 0x01e;
   public static final int EXPONENTIATION_INT   = 0x01f;
   
   public static final int LOAD           = 0x02a;
   public static final int STORE          = 0x02b;
   
   public static final int ADD_FLOAT            = 0x03a;
   public static final int SUBTRACT_FLOAT       = 0x03b;
   public static final int DIVIDE_FLOAT         = 0x03c;
   public static final int MODULO_FLOAT         = 0x03d;
   public static final int MULTIPLY_FLOAT       = 0x03e;
   public static final int EXPONENTIATION_FLOAT = 0x03f;
   
   public static final int BRANCH         = 0x04a;
   public static final int BRANCHNEG      = 0x04b;
   public static final int BRANCHZERO     = 0x04c;
   
   public static final int HALT           = 0x05f;
   
   public static final int FIRST_INSTRUCTION_LOCATION = 0;
   public static final int START_VALUE = 0;
   
   public ComputerSimulator(PrintStream loadingPrintStream, Scanner loadingScanner, PrintStream loadingErrorPrintStream,
                           PrintStream executingPrintStream, Scanner executingScanner, PrintStream executingErrorPrintStream) {
                           
      this.programLoading = new ProgramLoading(loadingPrintStream, loadingScanner, loadingErrorPrintStream);
      this.programExecution = new ProgramExecution(executingPrintStream, executingScanner, executingErrorPrintStream);
      
   }
   
   public ComputerSimulator(PrintStream printStream, Scanner scanner, PrintStream errorPrintStream) {
      this(printStream, scanner, errorPrintStream,
                        printStream, scanner, errorPrintStream);
   }
   
   public void run() {
      //loadExamplePrograms();

      setStartValue();
      programLoading.run(this.memory);
      programExecution.run(this.memory);
      
   }
   
   public static final int OPERATION_CODE_ORDER_OF_MAGNITUDE = Numbers.integerPower(10, (short)(MEMORY_LOCATION_LENGTH));
   
   public static int calculateInstruction(int operationCode, int memoryLocation) {
      return memoryLocation + operationCode * OPERATION_CODE_ORDER_OF_MAGNITUDE;
   }
   
   public void loadExamplePrograms() {
      loadFirstProgram(-4, -2);
      programExecution.run(this.memory);
      
      loadSecondProgram(999, 12);
      programExecution.run(this.memory);
      
      loadSecondProgram(9, 12);
      programExecution.run(this.memory);
      
      loadThirdProgram(-1, 10);
      programExecution.run(this.memory);
      
      loadFourthProgram(7);
      programExecution.run(this.memory);
      
      loadFifthProgram();
      programExecution.run(this.memory);
      
      loadSixthProgram();
      programExecution.run(this.memory);
      
      loadSeventhProgram();
      programExecution.run(this.memory);
      
      loadEighthProgram();
      programExecution.run(this.memory);
   }
   
   // printing sum of firstNumber + secondNumber
   private void loadFirstProgram(int firstNumber, int secondNumber) {
      setStartValue();
      
      memory[0] = calculateInstruction(READ_INT, 7);
      memory[1] = calculateInstruction(READ_INT, 8);
      memory[2] = calculateInstruction(LOAD, 7);
      memory[3] = calculateInstruction(ADD_INT, 8);
      memory[4] = calculateInstruction(STORE, 9);
      memory[5] = calculateInstruction(WRITE_INT, 9);
      memory[6] = calculateInstruction(HALT, 0);
      
      memory[7] = firstNumber;
      memory[8] = secondNumber;
   }
   
   // printing maximum of (firstNumber, secondNumber)
   private void loadSecondProgram(int firstNumber, int secondNumber) {
      setStartValue();
      
      memory[0] = calculateInstruction(READ_INT, 9);
      memory[1] = calculateInstruction(READ_INT, 0x00a);
      memory[2] = calculateInstruction(LOAD, 9);
      memory[3] = calculateInstruction(SUBTRACT_INT, 0x00a);
      memory[4] = calculateInstruction(BRANCHNEG, 7);
      memory[5] = calculateInstruction(WRITE_INT, 9);
      memory[6] = calculateInstruction(HALT, 0);
      memory[7] = calculateInstruction(WRITE_INT, 0x00a);
      memory[8] = calculateInstruction(HALT, 0);
      
      memory[9] = firstNumber;
      memory[0x00a] = secondNumber;
   }
      
   // printing sum of positive (more than zero)
   //(up to maxIngredients, otherwise until endOfDataMarker entered - excluding endOfDataMarker in sum) elements
   private void loadThirdProgram(int endOfDataMarker, int maxIngredients) {
      setStartValue();
      
      memory[0]     = calculateInstruction(LOAD, 0x028);
      memory[1]     = calculateInstruction(SUBTRACT_INT, 0x029);
      memory[2]     = calculateInstruction(BRANCHZERO, 0x010);
      memory[3]     = calculateInstruction(READ_INT, 0x02b);
      memory[4]     = calculateInstruction(LOAD, 0x02b);
      memory[5]     = calculateInstruction(SUBTRACT_INT, 0x027);
      memory[6]     = calculateInstruction(BRANCHZERO, 0x010);
      memory[7]     = calculateInstruction(LOAD, 0x02b);
      memory[8]     = calculateInstruction(BRANCHNEG, 3);
      memory[9]     = calculateInstruction(BRANCHZERO, 3);
      memory[0x00a] = calculateInstruction(ADD_INT, 0x02a);
      memory[0x00b] = calculateInstruction(STORE, 0x02a);
      memory[0x00c] = calculateInstruction(LOAD, 0x029);
      memory[0x00d] = calculateInstruction(ADD_INT, 0x02c);
      memory[0x00e] = calculateInstruction(STORE, 0x029);
      memory[0x00f] = calculateInstruction(BRANCH, 0);
      memory[0x010] = calculateInstruction(WRITE_INT, 0x02a);
      memory[0x011] = calculateInstruction(HALT, 0);
      
      memory[0x027] = endOfDataMarker;
      memory[0x028] = maxIngredients;
      memory[0x029] = 0;
      memory[0x02a] = 0;
      //memory[0x02b];
      memory[0x02c] = 1;
   }
   
   // printing integer average of nonzero ingredients
   private void loadFourthProgram(int ingredients) {
      setStartValue();
      
      memory[0]     = calculateInstruction(LOAD, 0x028);
      memory[1]     = calculateInstruction(SUBTRACT_INT, 0x029);
      memory[2]     = calculateInstruction(BRANCHZERO, 0x00c);
      memory[3]     = calculateInstruction(READ_INT, 0x02b);
      memory[4]     = calculateInstruction(LOAD, 0x02b);
      memory[5]     = calculateInstruction(BRANCHZERO, 3);
      memory[6]     = calculateInstruction(ADD_INT, 0x02a);
      memory[7]     = calculateInstruction(STORE, 0x02a);
      memory[8]     = calculateInstruction(LOAD, 0x029);
      memory[9]     = calculateInstruction(ADD_INT, 0x02c);
      memory[0x00a] = calculateInstruction(STORE, 0x029);
      memory[0x00b] = calculateInstruction(BRANCH, 0);
      memory[0x00c] = calculateInstruction(LOAD, 0x02a);
      memory[0x00d] = calculateInstruction(DIVIDE_INT, 0x029);
      memory[0x00e] = calculateInstruction(STORE, 0x02a);
      memory[0x00f] = calculateInstruction(WRITE_INT, 0x02a);
      memory[0x010] = calculateInstruction(HALT, 0);
      
      memory[0x028] = ingredients;
      memory[0x029] = 0;
      memory[0x02a] = 0;
      //memory[0x02b];
      memory[0x02c] = 1;
   }
   
   // printing max of n-elements (n is entered as first number, n include to elemets if n is greater than zero)
   private void loadFifthProgram() {
      setStartValue();
      
      memory[0]     = calculateInstruction(READ_INT, 0x028);
      memory[1]     = calculateInstruction(LOAD, 0x028);
      memory[2]     = calculateInstruction(STORE, 0x02a);
      memory[3]     = calculateInstruction(BRANCHNEG, 0x013);
      memory[4]     = calculateInstruction(BRANCHZERO, 0x013);
      memory[5]     = calculateInstruction(LOAD, 0x028);
      memory[6]     = calculateInstruction(SUBTRACT_INT, 0x029);
      memory[7]     = calculateInstruction(BRANCHZERO, 0x012);
      memory[8]     = calculateInstruction(READ_INT, 0x02b);
      memory[9]     = calculateInstruction(LOAD, 0x02b);
      memory[0x00a] = calculateInstruction(SUBTRACT_INT, 0x02a);
      memory[0x00b] = calculateInstruction(BRANCHNEG, 0x00e);
      memory[0x00c] = calculateInstruction(LOAD, 0x02b);
      memory[0x00d] = calculateInstruction(STORE, 0x02a);
      memory[0x00e] = calculateInstruction(LOAD, 0x029);
      memory[0x00f] = calculateInstruction(ADD_INT, 0x02c);
      memory[0x010] = calculateInstruction(STORE, 0x029);
      memory[0x011] = calculateInstruction(BRANCH, 3);
      memory[0x012] = calculateInstruction(WRITE_INT, 0x02a);
      memory[0x013] = calculateInstruction(HALT, 0);
      
      //memory[0x028] 
      memory[0x029] = 1;
      //memory[0x02a];
      //memory[0x02b];
      memory[0x02c] = 1;
   }
   
   // test of exponentiation
   private void loadSixthProgram() {
      setStartValue();
      
      memory[0]     = calculateInstruction(READ_INT, 0x111);
      memory[1]     = calculateInstruction(LOAD, 0x111);
      memory[2]     = calculateInstruction(READ_INT, 0x112);
      memory[3]     = calculateInstruction(MULTIPLY_INT, 0x112);
      memory[4]     = calculateInstruction(STORE, 0x113);
      memory[5]     = calculateInstruction(WRITE_INT, 0x113);
      memory[6]     = calculateInstruction(EXPONENTIATION_INT, 0x112);
      memory[7]     = calculateInstruction(STORE, 0x114);
      memory[8]     = calculateInstruction(WRITE_INT, 0x114);
      memory[9]     = calculateInstruction(READ_INT, 0x115);
      memory[0x00a] = calculateInstruction(MODULO_INT, 0x115);
      memory[0x00b] = calculateInstruction(STORE, 0x116);
      memory[0x00c] = calculateInstruction(WRITE_INT, 0x116);
      memory[0x00d] = calculateInstruction(HALT, 0x029);
   }
   
   // test of float: reading, writing, adding, subtracting
   private void loadSeventhProgram() {
      setStartValue();
      
      memory[0]     = calculateInstruction(READ_FLOAT, 0x111);
      memory[1]     = calculateInstruction(READ_FLOAT, 0x112);
      memory[2]     = calculateInstruction(LOAD, 0x111);
      memory[3]     = calculateInstruction(ADD_FLOAT, 0x112);
      memory[4]     = calculateInstruction(STORE, 0x113);
      memory[5]     = calculateInstruction(WRITE_FLOAT, 0x113);
      
      memory[6]     = calculateInstruction(LOAD, 0x111);
      memory[7]     = calculateInstruction(SUBTRACT_FLOAT, 0x112);
      memory[8]     = calculateInstruction(STORE, 0x113);
      memory[9]     = calculateInstruction(WRITE_FLOAT, 0x113);
      
      memory[0x00a] = calculateInstruction(LOAD, 0x112);
      memory[0x00b] = calculateInstruction(SUBTRACT_FLOAT, 0x111);
      memory[0x00c] = calculateInstruction(STORE, 0x113);
      memory[0x00d] = calculateInstruction(WRITE_FLOAT, 0x113);
      
      memory[0x00e] = calculateInstruction(HALT, 0x029);
      
   }
   
   // test of float: reading, writing, multiplying, dividing, modulo
   private void loadEighthProgram() {
      setStartValue();
      
      memory[0]     = calculateInstruction(READ_FLOAT, 0x111);
      memory[1]     = calculateInstruction(READ_FLOAT, 0x112);
      memory[2]     = calculateInstruction(LOAD, 0x111);
      memory[3]     = calculateInstruction(MULTIPLY_FLOAT, 0x112);
      memory[4]     = calculateInstruction(STORE, 0x113);
      memory[5]     = calculateInstruction(WRITE_FLOAT, 0x113);
      
      memory[6]     = calculateInstruction(LOAD, 0x111);
      memory[7]     = calculateInstruction(DIVIDE_FLOAT, 0x112);
      memory[8]     = calculateInstruction(STORE, 0x113);
      memory[9]     = calculateInstruction(WRITE_FLOAT, 0x113);
      
      memory[0x00a] = calculateInstruction(LOAD, 0x112);
      memory[0x00b] = calculateInstruction(DIVIDE_FLOAT, 0x111);
      memory[0x00c] = calculateInstruction(STORE, 0x113);
      memory[0x00d] = calculateInstruction(WRITE_FLOAT, 0x113);
      
      memory[0x00e]     = calculateInstruction(LOAD, 0x111);
      memory[0x00f]     = calculateInstruction(MODULO_FLOAT, 0x112);
      memory[0x010]     = calculateInstruction(STORE, 0x113);
      memory[0x011]     = calculateInstruction(WRITE_FLOAT, 0x113);
      
      memory[0x012] = calculateInstruction(LOAD, 0x112);
      memory[0x013] = calculateInstruction(MODULO_FLOAT, 0x111);
      memory[0x014] = calculateInstruction(STORE, 0x113);
      memory[0x015] = calculateInstruction(WRITE_FLOAT, 0x113);
      
      memory[0x016] = calculateInstruction(LOAD, 0x111);
      memory[0x017] = calculateInstruction(EXPONENTIATION_FLOAT, 0x112);
      memory[0x018] = calculateInstruction(STORE, 0x113);
      memory[0x019] = calculateInstruction(WRITE_FLOAT, 0x113);
      
      memory[0x01a] = calculateInstruction(LOAD, 0x112);
      memory[0x01b] = calculateInstruction(EXPONENTIATION_FLOAT, 0x111);
      memory[0x01c] = calculateInstruction(STORE, 0x113);
      memory[0x01d] = calculateInstruction(WRITE_FLOAT, 0x113);
      
      memory[0x01e] = calculateInstruction(HALT, 0x029);
      
   }
   
   private void setStartValue() {
      for (int index = 0; index < MEMORY_ELEMENTS; index++) {
         memory[index] = START_VALUE;
      }
   }
} 
