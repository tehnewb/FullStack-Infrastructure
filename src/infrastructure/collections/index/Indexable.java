package infrastructure.collections.index;

/**
 * The Indexable interface represents an object that can be stored in an IndexableList.
 * The index is assigned based on the location it is placed within the IndexableList.
 *
 * @author Albert Beaupre
 */
public interface Indexable {

    /**
     * Gets the index of the indexable within an IndexableList.
     *
     * @return the index of the indexable
     */
    int getIndex();

    /**
     * Sets the index of the indexable within an IndexableList.
     *
     * @param index the new index to set
     */
    void setIndex(int index);
}

