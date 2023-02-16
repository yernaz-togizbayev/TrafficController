package PLC;

public class TrafficControllerEmpty implements TrafficController {
	private TrafficRegistrar registrar;
	
	public TrafficControllerEmpty(TrafficRegistrar r) {
		this.registrar = r;
	}
	
	@Override
	public void enterRight(Vehicle v) { 
		registrar.registerRight(v);
	}
	
	@Override
	public void enterLeft(Vehicle v) {
		registrar.registerLeft(v);   
	}
	
	@Override
	public void leaveLeft(Vehicle v) {  
		registrar.deregisterLeft(v);      
	}
	
	@Override
	public void leaveRight(Vehicle v) { 
		registrar.deregisterRight(v); 
	}
}
