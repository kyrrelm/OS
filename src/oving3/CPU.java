package oving3;

public class CPU {

	Queue cpuQueue;
	Statistics statistics;
	private long maxCpuTime;
	
	public CPU(Queue cpuQueue, long maxCpuTime, Statistics statistics) {
		this.cpuQueue = cpuQueue;
		this.statistics = statistics;
		this.maxCpuTime = maxCpuTime;
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
