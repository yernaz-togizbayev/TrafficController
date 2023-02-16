package PLC;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

public class TrafficControllerFair implements TrafficController {

    private final TrafficRegistrar registrar;
    private final ReentrantLock lock = new ReentrantLock(true);
    private final Condition notEmptyBridge = lock.newCondition();
    private  boolean emptyBridge = true;


    public TrafficControllerFair(TrafficRegistrar registrar) {
        this.registrar = registrar;
    }
    @Override
    public void enterRight(Vehicle v) {
        lock.lock();
        try {
            while(emptyBridge == false)
                notEmptyBridge.await();
            emptyBridge = false;
            this.registrar.registerRight(v);
        }
        catch (InterruptedException e) {}
        finally { lock.unlock(); }
    }

    @Override
    public void enterLeft(Vehicle v) {
        lock.lock();
        try {
            while(emptyBridge == false)
                notEmptyBridge.await();
            emptyBridge = false;
            this.registrar.registerLeft(v);
        }
        catch (InterruptedException e) {}
        finally { lock.unlock(); }
    }

    @Override
    public void leaveLeft(Vehicle v) {
        lock.lock();
        try {
            emptyBridge = true;
            this.registrar.deregisterLeft(v);
            notEmptyBridge.signalAll();
        }
        finally { lock.unlock(); }
    }

    @Override
    public void leaveRight(Vehicle v) {
        lock.lock();
        try {
            emptyBridge = true;
            this.registrar.deregisterRight(v);
            notEmptyBridge.signalAll();
        }
        finally { lock.unlock(); }
    }
}
