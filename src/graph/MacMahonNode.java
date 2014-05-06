package graph;

public class MacMahonNode {
	public enum ObjectType{
		barstool(0), chair(1), easel(2), hatrack(3), lamp(4), sofa(5), none(6);
		
		private final int value;
	    private ObjectType(int value) {
	        this.value = value;
	    }

	    public int getValue() {
	        return value;
	    }
	    
	    public static String objectTypeFeatureString(ObjectType objType){
	    	String featString = "";
	    	
	    	for(int i = 0; i < ObjectType.values().length; i++)
	    		featString += objType == ObjectType.values()[i] ? "1 " : "0 ";
	    	
	    	return featString.trim();
	    }
	}
	
	private ObjectType objectType;
	private int x;
	private int y;
	
	public MacMahonNode(int x, int y, ObjectType objType){
		this.x = x;
		this.y = y;
		this.objectType = objType;
	}
	
	public ObjectType getObjectType() {
		return objectType;
	}

	public int getX() {
		return x;
	}

	public int getY() {
		return y;
	}
	
	public boolean hasObject(){
		return objectType == ObjectType.none;
	}
	
	public boolean equals(Object obj){
		if(obj == null)
			return false;
		if(obj == this)
			return true;
		if(!(obj instanceof MacMahonNode))
			return false;
		
		MacMahonNode rhs = (MacMahonNode)obj;
		return this.x == rhs.getX() && this.y == rhs.getY()
				&& this.objectType == rhs.getObjectType();
	}

}
