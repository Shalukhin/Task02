package service;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class Noda {
	
	private Lock lock = new ReentrantLock();
	
	private int value;
	
	private boolean access = true;
	
	public Noda() {
		super();
	}

	public Noda(int value) {
		super();
		this.value = value;
	}

	public int getValue() {
		try {
			lock.lock();
			return value;
		} finally {
			lock.unlock();
		}				
	}

	public boolean setValue(int value) {
		try {
			lock.lock();
			if (!access) {
				return false;
			}
			
			this.value = value;
			access = false;
			return true;
		} finally {
			lock.unlock();
		}		
	}
	
	public boolean isAccess() {
		return access;
	}

	public void setAccess(boolean access) {
		this.access = access;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + value;
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Noda other = (Noda) obj;
		if (value != other.value)
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Noda [value=" + value + "]";
	}
	
}
