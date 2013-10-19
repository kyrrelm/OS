package oving3;

/**
 * This class contains a lot of public variables that can be updated
 * by other classes during a simulation, to collect information about
 * the run.
 */
public class Statistics
{
	/** The number of processes that have exited the system */
	public long nofCompletedProcesses = 0;
	/** The number of processes that have entered the system */
	public long nofCreatedProcesses = 0;
	/** The total time that all completed processes have spent waiting for memory */
	public long totalTimeSpentWaitingForMemory = 0;
	/** The time-weighted length of the memory queue, divide this number by the total time to get average queue length */
	public long memoryQueueLengthTime = 0;
	/** The largest memory queue length that has occured */
	public long memoryQueueLargestLength = 0;
	
	public long numberOfProcessSwitches = 0;
	public long cpuTimeSpentProcessing = 0;
	public long cpuQueueLargestLength = 0;
	public long cpuQueueLengthTime = 0;
	public long ioQueueLengthTime = 0;
	public int ioQueueLargestLength = 0;
	public int totalNummerOfTimesInCpuQueue = 0;
	public int totalNummerOfTimesInIoQueue = 0;
	public long totalTimeSpentInCpuQueue = 0;
	public long totalTimeSpentInIoQueue = 0;
	public long totalTimeSpentInCpu = 0;
	public long totalTimeSpentInIo = 0;
	
    
	/**
	 * Prints out a report summarizing all collected data about the simulation.
	 * @param simulationLength	The number of milliseconds that the simulation covered.
	 */
	public void printReport(long simulationLength) {
		System.out.println();
		System.out.println("Simulation statistics:");
		System.out.println();
		System.out.println("Number of completed processes:                                          "+nofCompletedProcesses);
		System.out.println("Number of created processes:                                            "+nofCreatedProcesses);
		System.out.println("Number of processes switches:                                           "+numberOfProcessSwitches);
		System.out.println("Average throughput (processes per second):                              "+(double)nofCompletedProcesses*1000/simulationLength);
		System.out.println();
		System.out.println("Total CPU time spent processing:                                        "+cpuTimeSpentProcessing);
		System.out.println("Fraction of CPU time spent processing:                                  "+(double)100*cpuTimeSpentProcessing/simulationLength+" %");
		System.out.println("Total CPU time spent waiting:                                           "+(simulationLength-cpuTimeSpentProcessing));
		System.out.println("Fraction of CPU time spent waiting:                                     "+(double)(100-((double)100*cpuTimeSpentProcessing/simulationLength))+" %");
		System.out.println();
		System.out.println("Largest occuring memory queue length:                                   "+memoryQueueLargestLength);
		System.out.println("Average memory queue length:                                            "+(float)memoryQueueLengthTime/simulationLength);
		System.out.println("Largest occuring cpu queue length:                                      "+cpuQueueLargestLength);
		System.out.println("Average cpu queue length:                                               "+(float)cpuQueueLengthTime/simulationLength);
		System.out.println("Largest occuring I/O queue length:                                      "+ioQueueLargestLength);
		System.out.println("Average I/O queue length:                                               "+(float)ioQueueLengthTime/simulationLength);
		if(nofCompletedProcesses > 0) {
			System.out.println("Average # of times a process has been placed in memory queue:           "+1);
			System.out.println("Average # of times a process has been placed in cpu queue:              "+(float)totalNummerOfTimesInCpuQueue/nofCompletedProcesses);
			System.out.println("Average # of times a process has been placed in I/O queue:              "+(float)totalNummerOfTimesInIoQueue/nofCompletedProcesses);
			System.out.println();
			int tmp = (int) (totalTimeSpentWaitingForMemory+totalTimeSpentInCpuQueue+totalTimeSpentInCpu+totalTimeSpentInIoQueue+totalTimeSpentInIo);
			System.out.println("Average time spent in system per process:                               "+ tmp/nofCompletedProcesses+" ms");
			System.out.println("Average time spent waiting for memory per process:                      "+ totalTimeSpentWaitingForMemory/nofCompletedProcesses+" ms");
			System.out.println("Average time spent waiting for cpu per process:                         "+ totalTimeSpentInCpuQueue/nofCompletedProcesses+" ms");
			System.out.println("Average time spent processing per process:                              "+ totalTimeSpentInCpu/nofCompletedProcesses+" ms");
			System.out.println("Average time spent waiting for I/O per process:                         "+ totalTimeSpentInIoQueue/nofCompletedProcesses+" ms");
			System.out.println("Average time spent in I/O per process:                                  "+ totalTimeSpentInIo/nofCompletedProcesses+" ms");
		}
	}
}
