package oving3;

public class IO {

	private Gui gui;
	private CPU cpu;
	private EventQueue eventQueue;
	private Queue ioQueue;
	private Statistics statistics;
	private long avgToTime;
	private Process currentProcess;
	
	public IO(Gui gui, CPU cpu, EventQueue eventQueue, Queue ioQueue, long avgIoTime, Statistics statistics) {
		this.cpu = cpu;
		this.gui = gui;
		this.eventQueue = eventQueue;
		this.ioQueue = ioQueue;
		this.statistics = statistics;
		this.avgToTime = avgIoTime;
		currentProcess = null;
	}

	public void timePassed(long timePassed) {
		statistics.ioQueueLengthTime += ioQueue.getQueueLength()*timePassed;
		if (ioQueue.getQueueLength() > statistics.ioQueueLargestLength) {
			statistics.ioQueueLargestLength = ioQueue.getQueueLength(); 
		}
	}
	public void processRequest(long clock) {
		Process tmp = cpu.stopAndReturnCurrentProcess();
		ioQueue.insert(tmp);
		tmp.setNofTimesInIoQueue(tmp.getNofTimesInIoQueue()+1);
		tmp.enteredIoQueue(clock);
		processIO(clock);
	}
	
	public void processIO(long clock){
		if (currentProcess == null && !ioQueue.isEmpty()) {
			currentProcess = (Process) ioQueue.removeNext();
			currentProcess.leftIoQueue(clock);
			gui.setIoActive(currentProcess);
			long timeInIo = getTimeInIo();
			eventQueue.insertEvent(new Event(Simulator.END_IO, clock + timeInIo));
			currentProcess.timeInIo += timeInIo;
		}
	}
	public void endIo(long clock) {
		currentProcess.setTimeToNextIoOperation(currentProcess.generateTimeToNextIoOperation());
		cpu.getCpuQueue().insert(currentProcess);
		currentProcess.setNofTimesInCpuQueue(currentProcess.getNofTimesInCpuQueue()+1);
		currentProcess.enteredCpuQueue(clock);
		currentProcess = null;
		gui.setIoActive(null);
		processIO(clock);
		eventQueue.insertEvent(new Event(Simulator.NEW_PROCESS, clock));
	}
	private long getTimeInIo() {
		return (long) ( 1+(2 * Math.random() * avgToTime));
	}
	public boolean isIdle() {
		if(currentProcess == null)
			return true;
		return false;
	}


}
