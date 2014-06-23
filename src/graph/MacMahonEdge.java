package graph;


public class MacMahonEdge {
	public enum WallType{
		butterfly(0), fish(1), tower(2), none(3);
		
		private final int value;
	    private WallType(int value) {
	        this.value = value;
	    }

	    public int getValue() {
	        return value;
	    }
	    
	    public static String wallTypeFeatureString(WallType wallType){
	    	String featString = "";
	    	
	    	for(int i = 0; i < WallType.values().length; i++)
	    		featString += wallType ==WallType.values()[i] ? "1 " : "0 ";
	    	
	    	return featString.trim();
	    }
	}
	
	public enum FloorType{
		blue(0), brick(1), concrete(2), flower(3), grass(4), gravel(5), wood(6), yellow(7), none(8);
		
		private final int value;
	    private FloorType(int value) {
	        this.value = value;
	    }

	    public int getValue() {
	        return value;
	    }
	    
	    public static String floorTypeFeatureString(FloorType floorType){
	    	String featString = "";
	    	
	    	for(int i = 0; i < FloorType.values().length; i++)
	    		featString += floorType == FloorType.values()[i] ? "1 " : "0 ";
	    	
	    	return featString.trim();
	    }
	}
	
	private WallType wallType;
	private FloorType floorType;
	private MacMahonNode source;
	private MacMahonNode target;
	
	public MacMahonEdge(MacMahonNode source, MacMahonNode target, WallType wallType, FloorType floorType){
		this.source = source;
		this.target = target;
		this.wallType = wallType;
		this.floorType = floorType;
	}
	
	public WallType getWallType() {
		return wallType;
	}
	
	public FloorType getFloorType() {
		return floorType;
	}
	
	public MacMahonNode getSource(){
		return this.source;
	}
	
	public MacMahonNode getTarget(){
		return this.target;
	}
	
	public boolean equals(Object obj){
		if(obj == null)
			return false;
		if(obj == this)
			return true;
		if(!(obj instanceof MacMahonEdge))
			return false;
		
		MacMahonEdge rhs = (MacMahonEdge)obj;
		return this.source.equals(rhs.getSource()) &&
				this.target.equals(rhs.getTarget()) &&
				this.floorType == rhs.getFloorType() &&
				this.wallType == rhs.getWallType();
	}

}
