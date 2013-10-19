package oving3;

public class CPU {

	private Gui gui; 
	private Memory memory;
	private Queue cpuQueue;
	private EventQueue eventQueue;
	private Statistics statistics;
	private long maxCpuTime;
	private Process currentProcess;
	
	
	public CPU(Gui gui, Memory memory, EventQueue eventQueue, Queue cpuQueue, long maxCpuTime, Statistics statistics) {
		this.memory = memory;
		this.gui = gui;
		this.eventQueue = eventQueue;
		this.cpuQueue = cpuQueue;
		this.statistics = statistics;
		this.maxCpuTime = maxCpuTime;
		this.currentProcess = null;
	}
	
	//executes processes after the round robin principal
	public void executeProcess(long clock){
		if (!cpuQueue.isEmpty()) {
			 currentProcess = (Process) cpuQueue.removeNext();
			 currentProcess.leftCpuQueue(clock);
			this.gui.setCpuActive(currentProcess);
			if (currentProcess.getCpuTimeNeeded() <= maxCpuTime && currentProcess.getCpuTimeNeeded() <= currentProcess.getTimeToNextIoOperation()) {
				eventQueue.insertEvent(new Event(Simulator.END_PROCESS, currentProcess.getCpuTimeNeeded() + clock));
				statistics.cpuTimeSpentProcessing += currentProcess.getCpuTimeNeeded();
			}else if(currentProcess.getCpuTimeNeeded() > maxCpuTime && currentProcess.getTimeToNextIoOperation() > maxCpuTime){
				eventQueue.insertEvent(new Event(Simulator.SWITCH_PROCESS, maxCpuTime + clock));
				currentProcess.setCpuTimeNeeded(currentProcess.getCpuTimeNeeded() - maxCpuTime);
				currentProcess.setTimeToNextIoOperation(currentProcess.getTimeToNextIoOperation()-maxCpuTime);
				statistics.cpuTimeSpentProcessing += maxCpuTime;
			}else{
				eventQueue.insertEvent(new Event(Simulator.IO_REQUEST, currentProcess.getTimeToNextIoOperation()  + clock));
				currentProcess.setCpuTimeNeeded(currentProcess.getCpuTimeNeeded() - currentProcess.getTimeToNextIoOperation());
				statistics.cpuTimeSpentProcessing += currentProcess.getTimeToNextIoOperation();
			}
		}
		
	}

	public void timePassed(long timePassed) {
		statistics.cpuQueueLengthTime += cpuQueue.getQueueLength()*timePassed;
		if (cpuQueue.getQueueLength() > statistics.cpuQueueLargestLength) {
			statistics.cpuQueueLargestLength = cpuQueue.getQueueLength(); 
		}
    }
	public void switchProcess(long clock) {
		if (currentProcess != null) {
			statistics.numberOfProcessSwitches++;
			currentProcess.setNofTimesInCpuQueue(currentProcess.getNofTimesInCpuQueue()+1);
			currentProcess.enteredCpuQueue(clock);
			cpuQueue.insert(currentProcess);
			currentProcess = null;
			gui.setCpuActive(null);
			executeProcess(clock);	
		}
	}
	public void endProcess(long clock) {
		gui.setCpuActive(null);
		currentProcess.updateStatistics(statistics);
		memory.processCompleted(currentProcess);
		currentProcess = null;
		executeProcess(clock);
		
	}
	public Process stopAndReturnCurrentProcess() {
		gui.setCpuActive(null);
		Process tmp = currentProcess;
		currentProcess = null;
		return tmp;
	}
	public boolean isIdle() {
		if(currentProcess == null)
			return true;
		return false;
	}

	public Queue getCpuQueue() {
		return cpuQueue;
	}

	public void setCpuQueue(Queue cpuQueue) {
		this.cpuQueue = cpuQueue;
	}

	public Statistics getStatistics() {
		return statistics;
	}

	public void setStatistics(Statistics statistics) {
		this.statistics = statistics;
	}
}
