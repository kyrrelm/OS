package oving2;

import java.util.Random;

public class Customer implements Runnable {
	private Random generator;
	private int ID;
	private int numberOfOrders, takeawayOrders, eatenOrders;
	
	
	private ServingArea servingArea;
	
	Customer(int id, ServingArea servingArea) {
		ID = id;
		generator = new Random();
		this.servingArea = servingArea;
		numberOfOrders = generator.nextInt(SushiBar.maxOrder) + 1;
		eatenOrders = generator.nextInt(numberOfOrders);
		takeawayOrders = numberOfOrders - eatenOrders;
	}



	@Override
	public void run() {
		print(Action.CREATE);
		while(true) {
			if(this.servingArea.enter(this)) {
				break;
			}
		}
		
		// Adding order to the SushiBar
		this.servingArea.eat(this, numberOfOrders, eatenOrders, takeawayOrders);
		
		// Time spent eating
		try {
			Thread.sleep(SushiBar.customerWait*this.eatenOrders);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		
		// Leaving the SushiBar
		this.servingArea.leave(this);
	}
	
	public void print(Action c) {
		String detail = "";
		switch(c) {
		case LEAVE:
			detail = "\thas left the shop.";
			break;
		case ENTER:
			detail = "\thas a seat now.";
			break;
		case EAT:
			detail = "\tis eating sushi.";
			break;
		case CREATE:
			detail = "\tis now created.";
			break;
		case WAIT:
			detail = "\tis waiting for free seat.";
		default:
			break;
		}
		
		SushiBar.write(Thread.currentThread().getName()
				+ ":\tCustomer " + this.ID + detail);
	}
	
	public int getID() {
		return this.ID;
	}
	
}
