package diveengine2d;
import java.awt.Graphics2D;
import java.util.ArrayList;
import java.util.List;

public class Entity {
	public float x = 0, y = 0, rotation = 0;
	public boolean enabled = true;
	public String GUID = null;
	public String name = null;
	
	public List<DiveScript> components = new ArrayList<DiveScript>();
	
	@SuppressWarnings("unchecked") //HACK because it is checked, java is just duuuumb
	public <T extends DiveScript> T getComponent(Class<T> componentType) {
		for(DiveScript script : components) {
			if(script.getClass().isAssignableFrom(componentType)) {
				return (T) script;
			}
		}
		return null;
	}
	
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
	
	public void render(Graphics2D g) {
		if(DebugSettings.debugLevel > 0) g.drawString("" + x + ", " + y, x, y - 2);
	}
}
