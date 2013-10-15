package oving2;

import java.util.ArrayList;
import java.util.Stack;

public class ServingArea {
	
	private int totalOrders = 0;
	private int numberOfEatingOrders = 0;
	private int numberOftakeawayOrders = 0;
	
	ArrayList<Customer> customers = new ArrayList<Customer>();
	
	public ServingArea() {
		SushiBar.write(
				"--- THE SHOP IS OPEN ---"
				);
	}
	
	public synchronized void leave(Customer c) {
		c.print(Action.LEAVE);
		customers.remove(c);
		SushiBar.write("\tNow there is a free seat in the shop.");
		notify();
		if(this.customers.size() == 0 && !SushiBar.isOpen) {
			SushiBar.write(
					"--- THE SHOP IS CLOSED ---"
					);
			statistics();	
		}
	}
	
	public synchronized boolean enter(Customer c) {
		c.print(Action.ENTER);
		c.print(Action.WAIT);
		while(!(customers.size() < SushiBar.capacity)) {
			try {
				wait();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		customers.add(c);
		return true;
	}
	
	private void statistics() {
		SushiBar.write("\nStatistics:\n"
				+ "Total number of orders: " + this.totalOrders + "\n"
				+ "Total number of takeaway orders: " + this.numberOftakeawayOrders + "\n"
				+ "Total number of orders eaten at serving area: " + this.numberOfEatingOrders);
	}
	
	public synchronized void eat(Customer c, int totalOrders, 
			int eatingOrders, int takeawayOrders) {
		c.print(Action.EAT);
		this.totalOrders += totalOrders;
		this.numberOfEatingOrders += eatingOrders;
		this.numberOftakeawayOrders += takeawayOrders;
	}
	
}
