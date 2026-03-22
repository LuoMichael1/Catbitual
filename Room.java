import java.awt.*;
import java.util.ArrayList;
import javax.swing.ImageIcon;
import java.awt.event.*;

// originally the room was supposed to be isometric and I anticipated the need to be able to move and scale it and the items inside it
// this is no longer the plan, but this structure could still allow for more rooms in the future
public class Room {
    
    static ImageIcon roomImage = new ImageIcon("Assets/Images/roombg.png");
    static final int SPRITE_WIDTH = 640;
    static final int SPRITE_HEIGHT = 360;
    static final int floorHeight = (int)(440*Main.scaleY);   // the distance from the top of the screen to the start of the floor in pixels

    private Entities currentEntity = null;
    private Entities temp; // used during mouse dragging to sort entities based on y-height
    private static ArrayList<Entities> entities = new ArrayList<Entities>();
    private PetStoreDB petStoreDB = null;

    public Room() {
        // load owned furniture from DB
        try {
            petStoreDB = new PetStoreDB();
            ArrayList<PetStoreDB.FurnitureRecord> owned = petStoreDB.getOwnedItems();
            for (PetStoreDB.FurnitureRecord rec : owned) {
                try {
                    ImageIcon img = new ImageIcon("Assets/Images/Furniture/" + rec.filepath);
                    Furniture f = new Furniture(img, rec.filepath, rec.id, rec.type);
                    if (rec.x >= 0 && rec.y >= 0) {
                        f.setPosition(rec.x, rec.y);
                        entities.add(f);
                    }
                } catch (Exception ex) {
                    System.out.println("Could not load furniture image: " + rec.filepath + " -> " + ex);
                }
            }
        } catch (Exception e) {
            System.out.println("Furniture DB load error: " + e);
        }
    }


    // draws the room in the center of the screen along with entities inside
    public void drawRoom(Graphics g) {
        g.drawImage(roomImage.getImage(), 0, 0, Main.width, Main.height, 0, 0, SPRITE_WIDTH, SPRITE_HEIGHT, null);

        // Draw carpets and wall decorations first so they appear below other objects
        for (int i=0; i<entities.size(); i++) {
            Entities ent = entities.get(i);
            if (ent instanceof Furniture) {
                Furniture f = (Furniture) ent;
                String t = f.getType();
                if ("carpet".equals(t) || "walldeco".equals(t)) {
                    ent.drawState(g);
                }
            }
        }

        // Draw all other entities (including cat and regular furniture)
        for (int i=0; i<entities.size(); i++) {
            Entities ent = entities.get(i);
            if (ent instanceof Furniture) {
                Furniture f = (Furniture) ent;
                String t = f.getType();
                if ("carpet".equals(t) || "walldeco".equals(t)) continue;
            }
            ent.drawState(g);
        }
    }

    public void addEntity(Entities e) {
        entities.add(e);
    }
    public int getEntityCount() {
        return entities.size();
    }

    // uses the id from the database
    public Entities findEntity(int dbId) {
        for (Entities e : entities) {
            if (e instanceof Furniture) {  // because the entity array could have other types such as cat  - used https://www.baeldung.com/java-instanceof
                Furniture f = (Furniture) e;
                if (f.getDbId() == dbId) {
                    return f;
                }
            }
        }
        return null;
    }

    public void removeEntity(Entities e) {
        entities.remove(e);
    }


    // handles events from the menu
    public void mousePressed(MouseEvent e) {
        // count backward so that entities are drawn layered correctly
        for (int i=entities.size()-1; i>=0; i--) {
            if (entities.get(i).withinBounds(e.getX(), e.getY()) && !entities.get(i).getGrabbed() && currentEntity==null) {
                entities.get(i).grabbed();
                currentEntity = entities.get(i);
            }
        }
    }
    public void mouseReleased(MouseEvent e) {
        for (int i=0; i<entities.size(); i++) {
            if (entities.get(i).getGrabbed()) {
                entities.get(i).dropped();
                // make location of furniture persist using database
                if (entities.get(i) instanceof Furniture) {
                    Furniture f = (Furniture) entities.get(i);
                    int id = f.getDbId();
                    if (id != -1 && petStoreDB != null) {
                        try {
                            petStoreDB.updateLocation(id, f.getX(), f.getY());
                        } catch (Exception ex) {
                            ex.printStackTrace();
                        }
                    }
                }
            }
        }
        currentEntity = null;
    }

    public void mouseDragged(MouseEvent e) {
        for (int i=0; i<entities.size(); i++) {
            if (entities.get(i).getGrabbed() && currentEntity==entities.get(i)) {
                entities.get(i).setPosition(e.getX()-entities.get(i).getDrawSize()/2, e.getY()+entities.get(i).getDrawSize()/2);
            }
            // the first item in the list should be the highest up (greatest y value)
            // the last item should be on top
            // do some bubble sort shanangins here
            if (i>=1) {
                if (entities.get(i).getY() < entities.get(i-1).getY()) {
                    temp = entities.get(i);

                    entities.set(i, entities.get(i-1));
                    entities.set(i-1, temp); 
                }
            }
            else if (entities.size() > 1) {
                if (entities.get(i).getY() > entities.get(i+1).getY()) {
                    temp = entities.get(i);

                    entities.set(i, entities.get(i+1));
                    entities.set(i+1, temp); 
                }
            }
        }
    }

}