package db;

import db.exception.*;
import java.util.*;

public class Database {
    private static final ArrayList<Entity> entities = new ArrayList<>();
    private static int idCounter = 1;

    private Database() {}

    public static void add(Entity e) {
        e.id = idCounter++;
        entities.add(e.copy());
    }

    public static Entity get(int id) throws EntityNotFoundException {
        for (Entity e : entities) {
            if (e.id == id) {
                return e.copy();
            }
        }
        throw new EntityNotFoundException(id);
    }

    public static void delete(int id) {
        Entity entity = get(id).copy();
        entities.remove(entity);
    }

    public static void update(Entity e) {
        for (int i = 0; i < entities.size(); i++) {
            if (entities.get(i).id == e.id) {
                entities.set(i, e.copy());
                return;
            }
        }
        throw new EntityNotFoundException(e.id);
    }
}
