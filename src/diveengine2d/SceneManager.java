package diveengine2d;
import java.awt.Color;
import java.awt.Graphics2D;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

public class SceneManager {
	private static ArrayList<Entity> entities = new ArrayList<Entity>();
	
	/**
	 * load scene by filename without the .scene extension.
	 * @param scene
	 * @return 
	 */
	public static boolean loadScene(String scene) {
		synchronized(entities) {
			entities = getScene(scene + ".scene");
			System.out.println("Loaded Scene File...");
			return true;
		}
	}
	
	private static Entity getTemplate(String name) {
		return getScene(name + ".template").get(0);
	}
	
	private static ArrayList<Entity> getScene(String scene) {
		ArrayList<Entity> entities = new ArrayList<Entity>();
		try{
			List<String> lines = Files.readAllLines(Paths.get(Engine.gameFolder, scene), Charset.forName("UTF-8"));
			Stack<Object> scopeObject = new Stack<Object>();
			for (String line : lines) {
				//System.out.println(line);
				line = line.trim();
				if(line.startsWith("#")) continue;
				if(line.startsWith("[") && line.endsWith("]")) {
					String newLine = line.substring(1, line.length() - 1);
					Class<?> entityClass = null;
					try{
						entityClass = Class.forName(newLine);
					}catch(ClassNotFoundException e) {
						try{
							entityClass = Class.forName("diveengine2d." + newLine);
						}catch(ClassNotFoundException ex) {
							System.out.println(newLine + " class not found!");
							continue;
						}
					}
					Object object = entityClass.newInstance();
					if(!(object instanceof Entity)) {
						System.out.println(entityClass + " is not an entity");
						continue;
					}
					entities.add((Entity)object);
					scopeObject.push((Entity)object);
				}else if(line.startsWith("$")) {
					//scope variable
					String[] parts = line.substring(1).split("=");
					if(parts.length != 2) {
						System.out.println("line: " + line + " is incorrect.\nignoring...");
						continue;
					}
					parts[0] = parts[0].trim();
					parts[1] = parts[1].trim();
					String key = parts[0], value = parts[1];
					
					if(scopeObject.isEmpty()) {
						System.out.println("no object in scope to bind " + key + " to");
						continue;
					}
					
					if(value.startsWith("\"") && value.endsWith("\"")) {
						value = value.substring(1, value.length() - 1);
						scopeObject.peek().getClass().getField(key).set(scopeObject.peek(), value);
					}else if(value.startsWith("#")) {
						value = value.substring(1, value.length());
						
						//TODO CHECK IF 6 characters
						int r = Integer.parseInt(value.substring(0, 2), 16);
						int g = Integer.parseInt(value.substring(2, 4), 16);
						int b = Integer.parseInt(value.substring(4, 6), 16);
						
						Color color = new Color(r, g, b);
						
						scopeObject.peek().getClass().getField(key).set(scopeObject.peek(), color);
					}else if(value.endsWith("I") || value.endsWith("i")) {
						value = value.substring(0, value.length() - 1);
						
						int intValue;
						try{
							intValue = Integer.parseInt(value);
							scopeObject.peek().getClass().getField(key).set(scopeObject.peek(), intValue);
						}catch(NoSuchFieldException e) {
							System.out.println(value + " isnt a property of " + ((DiveScript)scopeObject.peek()).name + "(" + scopeObject.peek() + ")");
							continue;
						}catch(NumberFormatException  e) {
							System.out.println(value + " is not an int");
							continue;
						}

					}else if(value.endsWith("F") || value.endsWith("f")) {
						value = value.substring(0, value.length() - 1);
						
						float intValue;
						try{
							intValue = Float.parseFloat(value);
						}catch(Exception e) {
							System.out.println(value + " is not a float");
							continue;
						}
						
						scopeObject.peek().getClass().getField(key).set(scopeObject.peek(), intValue);
					}
					
				}else if(line.startsWith("Component")) {
					//component
					if(!(scopeObject.peek() instanceof Entity)) {
						System.out.println("Con not apply a component to " + scopeObject.peek());
						continue;
					}
					String[] parts = line.substring(1).split(" ");
					if(parts.length != 2) {
						System.out.println("line: " + line + " is incorrect.\nignoring...");
						continue;
					}
					//we really only care about this part anyways so...
					String componentClass = parts[1].trim();
					DiveScript component = null;
					try {
						Object maybeComponent = Class.forName(componentClass).newInstance();
						if(maybeComponent instanceof DiveScript) {
							component = (DiveScript)maybeComponent;
						}else {
							System.out.println("" + componentClass + " is not of type component!");
							continue;
						}
					} catch( ClassNotFoundException e) {
						try {
							Object maybeComponent = Class.forName("diveengine2d.components." + componentClass).newInstance();
							if(maybeComponent instanceof DiveScript) {
								component = (DiveScript)maybeComponent;
							}else {
								System.out.println("" + componentClass + " is not of type component!");
								continue;
							}
						} catch( ClassNotFoundException ex) {
							System.out.println("Component " + componentClass + " not found!");
							continue;
						}
					}
					component.entity = (Entity) scopeObject.peek();
					((Entity)scopeObject.peek()).addComponent(component);
					scopeObject.push(component);
				}else if (line.equals("End Component")) {
					scopeObject.pop();
				}else if (line.startsWith("Template")) {
					String[] parts = line.split(" ");
					String template = parts[1].trim();
					Entity entity = getTemplate(template);
					entities.add(entity);
				}
			}
			for(Entity e : entities) {
				for(DiveScript script : e.components) {
					script.create();
				}
			}
		}catch(Exception e) {
			System.out.println(e.getMessage());

		}
		
		return entities;
	}
	
	public static void entityDump() {
		synchronized(entities) {
			System.out.println("--------------------ENTITY DUMP--------------------\n");
			for(Entity entity : entities) {
				System.out.println("" + entity + "\n");
			}
		}
	}

	public static void render() {
		for(Entity e : entities) {
			for(DiveScript script : e.components) {
				script.render();
			}
			e.render();
		}
	}

	public static void updateAll() {
		for(Entity e : entities) {
			for(DiveScript script : e.components) {
				script.update();
			}
		}
	}

	public static int entityCount() {
		return entities.size();
	}
}
