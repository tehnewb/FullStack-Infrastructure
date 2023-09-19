package entity;

/**
 * The Entity interface represents an entity that can be stored in an EntityList.
 * An entity has an associated index within the list.
 *
 * @author Albert Beaupre
 */
public interface Entity {

    /**
     * Gets the index of the entity within an EntityList.
     *
     * @return the index of the entity
     */
    int getIndex();

    /**
     * Sets the index of the entity within an EntityList.
     *
     * @param index the new index to set
     */
    void setIndex(int index);
}

