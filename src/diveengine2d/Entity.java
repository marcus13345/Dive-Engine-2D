package diveengine2d;
import java.util.ArrayList;
import java.util.List;

public class Entity {
	public float x = 0, y = 0, rotation = 0;
	public boolean enabled = true;
	public String GUID = null;
	public String name = null;
	
	public List<DiveScript> components = new ArrayList<DiveScript>();
	
	public String toString() {
		return	"name: " + name + "\n" +
				"GUID: " + GUID +
				componentsToString();
				
	}
	private String componentsToString() {
		String _return = "";
		for(DiveScript c : components) {
			_return += "\nComponent " + c.name;
		}
		return _return;
	}

	public void addComponent(DiveScript component) {
		components.add(component);
	}
}
