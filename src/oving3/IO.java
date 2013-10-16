package oving3;

public class IO {

	Queue ioQueue;
	Statistics statistics;
	private long avgToTime;
	
	public IO(Queue ioQueue, long avgIoTime, Statistics statistics) {
		this.ioQueue = ioQueue;
		this.statistics = statistics;
		this.avgToTime = avgIoTime;
	}
	
}
