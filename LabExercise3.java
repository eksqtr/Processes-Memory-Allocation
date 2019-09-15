/*
 *	
 *	Process Memory Allocation  (First fit - Best Fit - Worst Fit)
 *	Hard Coded by: Eubie Jay Hernandez Clemente
 *	
 *	Insights: The main memory was divided into 25 blocks (block 0 - block 24). 
 *			  A process occupies a contiguous blocks.
*/
import java.util.*;
public class LabExercise3 {

	// Initialize the series as the process
	static Process[] series;
	static String dialogMsg = null;
	
	// MAIN METHOD
	public static void main(String arg[]) {
		int userChoice = 0, index = 0;
		
		
		
		// Initialize the Array of the Process
		series = new Process[25];
		
		for(int j = 0; j < series.length; j++)
		{
			series[j] = new Process();
		}
		
		int[][] freeMemory = new int[series.length][2];
		
		
		// Declaring the Main Menu of the Process Menu
		String mainMenu[] = {
			"Add Process (First Fit)",
			"Add Process (Best Fit)",
			"Add Process (Worst Fit)",
			"Remove Process",
			"Exit"
		};
			
		
		
		Scanner userInput = new Scanner(System.in);
		
		
		// While Loop if User choice is not equal to 6 (exit) then continue;
		while(userChoice != 5)
		{
			int inputId, inputSize;
			inputId = inputSize = 0;
			int temp = 0, temp2 = 0;
			int counterEx = 0;
			int freeMemoryIndex = 0;
			boolean isMemoryFit = false;
			
			System.out.println("[Main Menu]");
			for(int x = 0; x < mainMenu.length; x++ )
			{
				System.out.println((x+1) + ": "+ mainMenu[x]);
			}
			int seriesIndex, y, count = 0;
			
			System.out.println("");
			for(seriesIndex = 0; seriesIndex < series.length; seriesIndex++)
			{
	
				if(series[seriesIndex].getprocessId() != 0) 
				{
					System.out.print(" #");
				}
				else System.out.print(" -");
				
				
			}
			if(dialogMsg != null){
				System.out.print(dialogMsg);
				dialogMsg = null;
			}
			
			while(true)
			{
				System.out.print("\n\nChoose from the menu ( 1 - 5 ): ");
				try {
					userChoice = userInput.nextInt();
					break;
				}
				catch (InputMismatchException e) {
					System.out.println("ERROR: Invalid input must be integer!");
					userInput = new Scanner(System.in);
				}
			}
			
			System.out.println("=========================================");	
			
			if(userChoice > mainMenu.length || userChoice < 1)
			{
				error("Invalid Choices! Choices must be (1 - 5) base on the menu above.");
			}
			/* Conditional Statement  */
			switch(userChoice)
			{
				case 1: // Add Process (First Fit)
					if(!IsThereFreeMemory())
					{
						error("There's not enough memory for you to add a process! Remove a process first! (4 - Remove Process)");
						break;
					}
					System.out.println("New Process (First Fit)");
					while(true)
					{
						System.out.print("ID: ");	
						try {
	
			                inputId = userInput.nextInt();
							break;			
			               
			            } catch (InputMismatchException e) {
			                System.out.println("ERROR: Invalid input must be integer!");
			                userInput = new Scanner(System.in);
			            } 
						
					}
					if(inputId < 1)
					{
						error("Process ID must not be below 1");
						break;
					}
					if(isMemoryIdInvalid(inputId)) // Checking if the Process ID is taken
					{
						error("Process " + inputId + " ID is already taken. Please enter another input");
						break;
					}
					while(true)
					{
						System.out.print("Size: ");	
						try {
	
			                inputSize = userInput.nextInt();
							break;			
			               
			            } catch (InputMismatchException e) {
			                System.out.println("ERROR: Invalid input must be integer!");
			                userInput = new Scanner(System.in);
			            } 
						
					}
					if(inputSize > series.length || inputSize < 1) // Checks if user input process size is > 24 or < 0
					{
						error("Process Size must not be greater than " + series.length + " and less than 1!");
						break;
					}
					
					
					if(getFreeMemorySlot() < inputSize) // Returning to its Free Memory Sizes checking if it sufficient
					{
						error("Insufficient space! Process Size is too large.");
						break;
					}
					
					// ============ Checking for Free Memory Starts here ===================
					for(int j = 0; j < freeMemory.length; j++)
					{
						freeMemory[j][0] = -1; // We need to set this to -1 by default because the index id of the memory block array starts on 0
						freeMemory[j][1] = 0;
					}
					
					counterEx = 0;
					freeMemoryIndex = 0;
					isMemoryFit = false;
					for(int j = 0; j < series.length; j++)
					{
					
						if(series[j].getprocessId() == 0) 
						{
							if(counterEx == 0)
							{
								freeMemory[freeMemoryIndex][0] = j;	
							}
							counterEx = 1;
							freeMemory[freeMemoryIndex][1]++; 
						}
						else { 
							if(counterEx == 1)
							{
								counterEx = 0;
								freeMemoryIndex++;
							}
							
						}
					}
					// ============ Checking for Free Memory Ends here ===================
					// ============== Inserting the Memory Size =============
				   	isMemoryFit = false;
					for(int e = 0; e < freeMemoryIndex + 1; e++)
					{
						if(freeMemory[e][0] != -1)
						{
							if(freeMemory[e][1] >= inputSize) 
							{
								for(int j = freeMemory[e][0]; j <( freeMemory[e][0] + inputSize); j++)
								{
									if(series[j].getprocessId() == 0)
									{
										if(count++ != inputSize) series[j] = new Process(inputId);	
									}
									
								}
								isMemoryFit = true;
								break; // We need to end the looping here once the memory size fits to the  free memory available		
							}
						}
					}
					if(!isMemoryFit) error("Insufficient space! Process Size is too large.");
					else message("Process ID " + inputId + " and Size " + inputSize + " has successfully inserted to the first fits");
					
					System.out.println("=========================================\n");
					
					break;
				case 2: // Add Process (Best Fit)
					if(!IsThereFreeMemory())
					{
						error("There's not enough memory for you to add a process! Remove a process first! (4 - Remove Process)");
						break;
					}
					System.out.println("New Process (Best Fit)");
					while(true)
					{
						System.out.print("ID: ");	
						try {
	
			                inputId = userInput.nextInt();
							break;			
			               
			            } catch (InputMismatchException e) {
			                System.out.println("ERROR: Invalid input must be integer!");
			                userInput = new Scanner(System.in);
			            } 
						
					}
					if(inputId < 1)
					{
						error("Process ID must not be below 1");
						break;
					}
					if(isMemoryIdInvalid(inputId)) // Checking if the Process ID is taken
					{
						error("Process " + inputId + " ID is already taken. Please enter another input");
						break;
						
					}
					while(true)
					{
						System.out.print("Size: ");	
						try {
	
			                inputSize = userInput.nextInt();
							break;			
			               
			            } catch (InputMismatchException e) {
			                System.out.println("ERROR: Invalid input must be integer!");
			                userInput = new Scanner(System.in);
			            } 
						
					}
					if(inputSize > series.length || inputSize < 1) // Checks if user input process size is > 24 or < 0
					{
						error("Process Size must not be greater than " + series.length + " and less than 1!");
						break;
					}
					
					
					if(getFreeMemorySlot() < inputSize) // Returning to its Free Memory Sizes checking if it sufficient
					{
						error("Insufficient space! Process Size is too large.");
						break;
					}
					
					
					// ============ Checking for Free Memory Starts here ===================
					for(int j = 0; j < freeMemory.length; j++)
					{
						freeMemory[j][0] = -1; // We need to set this to -1 by default because the index id of the memory block array starts on 0
						freeMemory[j][1] = 0;
					}
					
					counterEx = 0;
					freeMemoryIndex = 0;
					
					for(int j = 0; j < series.length; j++)
					{
					
						if(series[j].getprocessId() == 0) 
						{
							if(counterEx == 0)
							{
								freeMemory[freeMemoryIndex][0] = j;	
							}
							counterEx = 1;
							freeMemory[freeMemoryIndex][1]++; 
						}
						else { 
							if(counterEx == 1)
							{
								counterEx = 0;
								freeMemoryIndex++;
							}
							
						}
					}
					// ============ Checking for Free Memory Ends here ===================
					
					// ============== Sorting the Free memory from Smallest to Largest =============
				    temp = 0;
				    temp2 = 0;
				    for (int i = 0; i < freeMemoryIndex; i++) 
				    {
				        for (int j = 1; j < freeMemoryIndex + 1; j++) 
				        {
				            if (freeMemory[j - 1][1] > freeMemory[j][1]) 
				            { 
				                temp = freeMemory[j - 1][1];
				                freeMemory[j - 1][1] = freeMemory[j][1];
				                freeMemory[j][1] = temp;
				                
				                temp2 = freeMemory[j - 1][0];
				                freeMemory[j - 1][0] = freeMemory[j][0];
				                freeMemory[j][0] = temp2;

				            }
				        }
				    }
				    // ============== Inserting the Memory Size =============
				    isMemoryFit = false;
				    for(int e = 0; e < freeMemoryIndex + 1; e++)
					{
						if(freeMemory[e][0] != -1)
						{
							if(freeMemory[e][1] >= inputSize) 
							{
								for(int j = freeMemory[e][0]; j <( freeMemory[e][0] + inputSize); j++)
								{
									if(series[j].getprocessId() == 0)
									{
										if(count++ != inputSize) series[j] = new Process(inputId);	
									}
									
								}
								isMemoryFit = true;
								break; // We need to end the looping here once the memory size fits to the  free memory available		
							}
						}
					}
					
	
					if(!isMemoryFit) error("Insufficient space! Process Size is too large.");
					else message("Process ID " + inputId + " and Size " + inputSize + " has successfully inserted to the best fits");
					System.out.println("=========================================\n");
					
					break;
				case 3: // Add Process (Worst Fit)
					if(!IsThereFreeMemory())
					{
						error("There's not enough memory for you to add a process! Remove a process first! (4 - Remove Process)");
						break;
					}
					System.out.println("New Process (Worst Fit)");
					while(true)
					{
						System.out.print("ID: ");	
						try {
	
			                inputId = userInput.nextInt();
							break;			
			               
			            } catch (InputMismatchException e) {
			                System.out.println("ERROR: Invalid input must be integer!");
			                userInput = new Scanner(System.in);
			            } 
						
					}
					if(inputId < 1)
					{
						error("Process ID must not be below 1");
						break;
					}
					if(isMemoryIdInvalid(inputId)) // Checking if the Process ID is taken
					{
						error("Process " + inputId + " ID is already taken. Please enter another input");
						break;
						
					}
					while(true)
					{
						System.out.print("Size: ");	
						try {
	
			                inputSize = userInput.nextInt();
							break;			
			               
			            } catch (InputMismatchException e) {
			                System.out.println("ERROR: Invalid input must be integer!");
			                userInput = new Scanner(System.in);
			            } 
						
					}
		
					if(inputSize > series.length || inputSize < 1) // Checks if user input process size is > 24 or < 0
					{
						error("Process Size must not be greater than " + series.length + " and less than 1!");
						break;
					}
					
					
					if(getFreeMemorySlot() < inputSize) // Returning to its Free Memory Sizes checking if it sufficient
					{
						error("Insufficient space! Process Size is too large.");
						break;
					}
					
					
					// ============ Checking for Free Memory Starts here ===================
					for(int j = 0; j < freeMemory.length; j++)
					{
						freeMemory[j][0] = -1; // We need to set this to -1 by default because the index id of the memory block array starts on 0
						freeMemory[j][1] = 0;
					}
					
					counterEx = 0;
					freeMemoryIndex = 0;
					
					for(int j = 0; j < series.length; j++)
					{
					
						if(series[j].getprocessId() == 0) 
						{
							if(counterEx == 0)
							{
								freeMemory[freeMemoryIndex][0] = j;	
							}
							counterEx = 1;
							freeMemory[freeMemoryIndex][1]++; 
						}
						else { 
							if(counterEx == 1)
							{
								counterEx = 0;
								freeMemoryIndex++;
							}
							
						}
					}
					// ============ Checking for Free Memory Ends here ===================
					
					// ============== Sorting the Free memory from Largest to Smallest =============
				    temp = 0;
				    temp2 = 0;
				    for (int i = 0; i < freeMemoryIndex; i++) 
				    {
				        for (int j = 1; j < freeMemoryIndex + 1; j++) 
				        {
				            if (freeMemory[j - 1][1] < freeMemory[j][1]) 
				            { 
				                temp = freeMemory[j - 1][1];
				                freeMemory[j - 1][1] = freeMemory[j][1];
				                freeMemory[j][1] = temp;
				                
				                temp2 = freeMemory[j - 1][0];
				                freeMemory[j - 1][0] = freeMemory[j][0];
				                freeMemory[j][0] = temp2;

				            }
				        }
				    }
				    // ============== Inserting the Memory Size =============
				    isMemoryFit = false;
					for(int e = 0; e < freeMemoryIndex + 1; e++)
					{
						if(freeMemory[e][0] != -1)
						{
							if(freeMemory[e][1] >= inputSize) 
							{
								for(int j = freeMemory[e][0]; j <( freeMemory[e][0] + inputSize); j++)
								{
									if(series[j].getprocessId() == 0)
									{
										if(count++ != inputSize) series[j] = new Process(inputId);	
									}
									
								}
								isMemoryFit = true;
								break; // We need to end the looping here once the memory size fits to the  free memory available		
							}
						}
					}
					if(!isMemoryFit) error("Insufficient space! Process Size is too large.");
					else message("Process ID " + inputId + " and Size " + inputSize + " has successfully inserted to the worst fits");
					System.out.println("=========================================\n");
				
					break;
				case 4: // Remove Process
					while(true)
					{
						System.out.print("Enter ID of process to remove: ");	
						try {
	
			                inputId = userInput.nextInt();
							break;			
			               
			            } catch (InputMismatchException e) {
			                System.out.println("ERROR: Invalid input must be integer!");
			                userInput = new Scanner(System.in);
			            } 
						
					}
					if(!isMemoryIdInvalid(inputId))
					{
						error("That process ID does not exists!");
						break;
					}
					for(int x = 0; x  < series.length; x ++ )
					{
						if(series[x] != null)
						{
							if(series[x].getprocessId() == inputId) 
							{
								series[x] = new Process();
							}
						}
					}
					message("Process " + inputId + " has been removed from the block");
					System.out.println("=========================================\n");
					
					break;
			}
			index++;
		}
		
	}
	// METHODS ( FUNCTIONS )
	public static boolean isMemoryIdInvalid(int id)
	{
		for(int j = 0; j < series.length; j++)
		{
			if(series[j].getprocessId() != 0)
			{
				if(series[j].getprocessId() == id) {
					return true;
				}
			}
			
		}
		return false;		
	}

	public static boolean  IsThereFreeMemory()
	{
		for(int j = 0; j < series.length; j++)
		{
			if(series[j].getprocessId() == 0)
			{
				return true; 
			}
			
		}
		return false;
			
	}
	public static int getFreeMemorySlot()
	{
		int count = 0;
		for(int j = 0; j < series.length; j++)
		{
			if(series[j].getprocessId() == 0)
			{
				count++;
			}
			
		}
		return count;
	}
	
	public static void error(String error)
	{
		dialogMsg = "\n==================================================================================";
		dialogMsg += "\nAN ERROR OCCURED: " + error;
		dialogMsg += "\n==================================================================================";
	}
	
	public static void message(String msg)
	{
		dialogMsg = "\n==================================================================================";
		dialogMsg += "\nEXECUTION SUCCESS: " + msg;
		dialogMsg += "\n==================================================================================";
	}
	
}

class Process {
	public int processId;
	

	Process()
	{
		processId = 0;
	}
	
	public Process(int id)
	{
		this.processId = id;
	}	
	public int getprocessId()
	{
		 return this.processId;
	}
	
	
	public void setprocessId(int value)
	{
		this.processId = value;
	}
	
}

