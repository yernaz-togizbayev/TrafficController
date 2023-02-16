package PLC;

public class TrafficControllerSimple implements TrafficController {
    private final TrafficRegistrar registrar;
    private boolean emptyBridge = true;

    public TrafficControllerSimple(TrafficRegistrar registrar) {
        this.registrar = registrar;
    }

    @Override
    public synchronized void enterRight(Vehicle v) {
        try {
            while(emptyBridge == false)
                wait();
            emptyBridge = false;
            this.registrar.registerRight(v);
        }
        catch (InterruptedException e) {}
    }

    @Override
    public synchronized void enterLeft(Vehicle v) {
        try {
            while(emptyBridge == false)
                wait();
            emptyBridge = false;
            this.registrar.registerLeft(v);
        }
        catch (InterruptedException e) {}
    }

    @Override
    public synchronized void leaveLeft(Vehicle v) {
        emptyBridge = true;
        this.registrar.deregisterLeft(v);
        
        notifyAll();
    }

    @Override
    public synchronized void leaveRight(Vehicle v) {
        emptyBridge = true;
        this.registrar.deregisterRight(v);
        notifyAll();
    }
}
