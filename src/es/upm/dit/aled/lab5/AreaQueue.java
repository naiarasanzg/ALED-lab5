package es.upm.dit.aled.lab5;

import java.util.LinkedList;
import java.util.Queue;

import es.upm.dit.aled.lab5.gui.Position2D;

/**
 * Extension of Area that maintains a strict queue of the Patients waiting to
 * enter in it. After a Patient exits, the first one in the queue will be
 * allowed to enter.
 * 
 * @author rgarciacarmona
 */
public class AreaQueue extends Area {
	
	private Queue<Patient> waitQueue;
	
	public AreaQueue(String name, int time, int capacity, Position2D position){
		super(name, time, capacity, position);
		this.waitQueue = new LinkedList<>();
	}
	
	@Override
	public synchronized void enter(Patient p) {
		System.out.println("Patient " + p.getNumber() + " trying to enter " + this.getName());
		
		waiting++;
		waitQueue.add(p);
		
		Patient primerPEnCola = waitQueue.peek();
		while(numPatients >= capacity || p != primerPEnCola) {
			try {
				wait();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		waitQueue.remove();
		waiting--;
		numPatients++;
		System.out.println("Patient " + p.getNumber() + " has entered " + this.name);
	}
}
