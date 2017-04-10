package persistance;

import model.HasId;

/**
 * Created by Mihai on 14.03.2017.
 */
public interface IRepository<Entity extends HasId> {

    void add(Entity e);
    void delete(Integer id);
    void update(Entity e, Integer id);
    Iterable<Entity> getAll();
}
