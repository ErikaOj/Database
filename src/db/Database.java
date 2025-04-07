package db;

import db.exception.*;
import java.util.*;

public class Database {
    private static final ArrayList<Entity> entities = new ArrayList<>();
    private static int idCounter = 1;
    private static HashMap<Integer, Validator> validators = new HashMap<>();

    private Database() {}

    public static void registerValidator(int entityCode, Validator validator) {
        if (validators.containsKey(entityCode)) {
            throw new IllegalArgumentException("Validator already registered for this entity code");
        }
        else
            validators.put(entityCode, validator);
    }

    public static void add(Entity e) throws InvalidEntityException {
        Validator validator = validators.get(e.getEntityCode());
        if (validator != null) {
            validator.validate(e);
        }

        e.id = idCounter++;

        if (e instanceof Trackable) {
            Date now = new Date();
            Trackable trackable = (Trackable) e;
            trackable.setCreationDate(now);
            trackable.setLastModificationDate(now);
        }

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

    public static void update(Entity e) throws InvalidEntityException {
        Validator validator = validators.get(e.getEntityCode());
        if (validator != null) {
            validator.validate(e);
        }

        if (e instanceof Trackable) {
            Trackable trackable = (Trackable) e;
            trackable.setLastModificationDate(new Date());
        }

        for (int i = 0; i < entities.size(); i++) {
            if (entities.get(i).id == e.id) {
                entities.set(i, e.copy());
                return;
            }
        }
        throw new EntityNotFoundException(e.id);
    }

    public static ArrayList<Entity> getAll(int entityCode) {
        ArrayList<Entity> result = new ArrayList<>();
        for (Entity e : entities) {
            if (e.getEntityCode() == entityCode) {
                result.add(e.copy());
            }
        }
        return result;
    }

}
