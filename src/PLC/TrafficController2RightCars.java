package PLC;

class TrafficController2RightCars implements TrafficController {
    private final TrafficRegistrar registrar;
    private boolean notEmptyBridge = false;
    private int carsRight = 0;
    private Boolean carsLeft = false;
    public TrafficController2RightCars(TrafficRegistrar registrar) {
        this.registrar = registrar;
    }

    @Override
    public synchronized void enterRight(Vehicle v) {
    	
        try {
        	
            while((notEmptyBridge == true && carsLeft == true) || carsRight >= 2) {
            	wait();
            }
            carsRight++;
            notEmptyBridge = true;
            this.registrar.registerRight(v);
        }
        catch (InterruptedException e) {}
    }

    @Override
    public synchronized void enterLeft(Vehicle v) {
        try {
            while(notEmptyBridge == true)
                wait();
            carsLeft = true;
            notEmptyBridge = true;
            this.registrar.registerLeft(v);
        }
        catch (InterruptedException e) {}
    }

    @Override
    public synchronized void leaveLeft(Vehicle v) {
    	if (carsRight == 1)
    		notEmptyBridge = false;
        carsRight--;
        this.registrar.deregisterLeft(v);
        notifyAll();
    }

    @Override
    public synchronized void leaveRight(Vehicle v) {
    	notEmptyBridge = false;
        carsLeft = false;
        this.registrar.deregisterRight(v);
        notifyAll();
    }
}
