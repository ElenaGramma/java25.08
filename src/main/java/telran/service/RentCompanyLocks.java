package telran.service;

import java.io.BufferedOutputStream;
import java.io.FileOutputStream;
import java.io.ObjectOutputStream;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

import telran.dto.Car;
import telran.dto.CarsReturnCode;
import telran.dto.Driver;
import telran.dto.Model;
import telran.dto.RemovedCarData;
import telran.dto.RentRecord;

public class RentCompanyLocks {

	static final ReadWriteLock carsLock=new ReentrantReadWriteLock();
	static final ReadWriteLock modelsLock=new ReentrantReadWriteLock();
	static final ReadWriteLock driversLock=new ReentrantReadWriteLock();
	static final ReadWriteLock recordsLock=new ReentrantReadWriteLock();
	
	static final int cars_index=0;
	static final int models_index=1;
	static final int driver_index=2;
	static final int records_index=3;
	
	static final int write_index=0;
	static final int read_index=1;
	
	static Lock[][] locks;
	static {
		locks=new Lock[2][4];
		ReadWriteLock[] rwl= {carsLock,modelsLock,driversLock,recordsLock};
		for (int i = 0; i < rwl.length; i++) {
			locks[write_index][i]=rwl[i].writeLock();
			locks[read_index][i]=rwl[i].readLock();
		}
		}
	
	private static void lockUnlock(boolean flag,int typeLock,int ...indexes) {
		Arrays.sort(indexes);
		if(flag)
		customLock(typeLock,indexes);
		else
		costomUnlock(typeLock,indexes);
	}

	private static void costomUnlock(int typeLock, int[] indexes) {
		for (int i = 0; i < indexes.length; i++) {
			locks[typeLock][i].unlock();
		}
		
	}

	private static void customLock(int typeLock, int[] indexes) {
		for (int i = 0; i < indexes.length; i++) {
			locks[typeLock][i].lock();
		}
		
	}

	
	public static void lockAddModel(boolean flag) {
		lockUnlock(flag, write_index, models_index);
		
	}

	
	public static void lockAddCar(boolean flag) {
		lockUnlock(flag, write_index,cars_index);
	}

	
	public static void lockAddDriver(boolean flag) {
		lockUnlock(flag, write_index,driver_index);
	}

	
	public static void lockGetModel(boolean flag) {
		lockUnlock(flag, read_index,models_index);
	}

	
	public static void lockGetCar(boolean flag) {
		lockUnlock(flag, read_index,cars_index);
	}

	
	public static void lockGetDriver(boolean flag) {
		lockUnlock(flag, read_index,driver_index);
	}

	public static void lockRentCar(boolean flag) {
		lockUnlock(flag, read_index, driver_index);
		lockUnlock(flag, write_index, cars_index,records_index);
	}

	
	public static void lockGetCarsByDriver(boolean flag) {
		lockUnlock(flag, read_index,records_index );
	}


	public static void lockGetDriversByCar(boolean flag) {
		lockUnlock(flag, read_index,records_index );
	}

	
	public static void lockGetCarsByModel(boolean flag) {
		lockUnlock(flag, read_index,cars_index,models_index);
	}

	
	public static void lockGetRentRecordsAtDates(boolean flag) {
		lockUnlock(flag, read_index,records_index );
	}


	public static void lockRemoveCar(boolean flag) {
		lockUnlock(flag, write_index,cars_index,models_index,records_index );
	}

	
	public static void lockRemoveModel(boolean flag) {
		lockUnlock(flag, write_index,cars_index,models_index);
	}

	
	public static void lockReturnCar(boolean flag) {
		lockUnlock(flag, write_index,cars_index,models_index );
	}

	
	public static void lockGetMostPopularCarModels(boolean flag) {
		lockUnlock(flag, read_index, cars_index,models_index,records_index);
	}

	
	public static void lockGetMostProfitableCarModels(boolean flag) {
		lockUnlock(flag, read_index, records_index,cars_index,models_index);
	}

	
	public static void lockGetMostActiveDrivers(boolean flag) {
		lockUnlock(flag, read_index, driver_index,records_index);
	}

	
	public static void lockGetModelNames(boolean flag) {
		lockUnlock(flag, read_index, models_index,cars_index);
	}
	public static void lockSave(boolean flag) {
		lockUnlock(flag, read_index, 0,1,2,3);

	}
}
