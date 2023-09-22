package gdx.proto;

import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

/**
 * The Alignment interface provides alignment constants and methods for calculating
 * the position of a UI component within its container based on the chosen alignment.
 */
public interface Alignment {
    /**
     * Represents center alignment. This constant can be used to horizontal
     * and vertical center-align a UI component within its container.
     */
    Alignment Center = new Alignment() {
        /**
         * Calculates the position to center-align the actor within the parent container.
         *
         * @param parent The bounding rectangle of the parent container.
         * @param actor  The bounding rectangle of the UI component (actor).
         * @return A Vector2 representing the calculated position for center alignment.
         */
        @Override
        public Vector2 calculatePosition(Rectangle parent, Rectangle actor) {
            float x = (parent.getWidth() - actor.getWidth()) / 2;
            float y = (parent.getHeight() - actor.getHeight()) / 2;
            return new Vector2(x, y);
        }
    };

    /**
     * Represents right alignment. This constant can be used to right-align a
     * UI component within its container.
     */
    Alignment Right = new Alignment() {
        /**
         * Calculates the position to right-align the actor within the parent container.
         *
         * @param parent The bounding Rectangle of the parent container.
         * @param actor  The bounding Rectangle of the UI component (actor).
         * @return A Vector2 representing the calculated position for right alignment.
         */
        @Override
        public Vector2 calculatePosition(Rectangle parent, Rectangle actor) {
            float x = parent.getWidth() - actor.getWidth();
            return new Vector2(x, actor.getY());
        }
    };

    /**
     * Represents left alignment. This constant can be used to left-align a
     * UI component within its container.
     */
    Alignment Left = new Alignment() {
        /**
         * Calculates the position to left-align the actor within the parent container.
         *
         * @param parent The bounding Rectangle of the parent container.
         * @param actor  The bounding Rectangle of the UI component (actor).
         * @return A Vector2 representing the calculated position for left alignment.
         */
        @Override
        public Vector2 calculatePosition(Rectangle parent, Rectangle actor) {
            return new Vector2(0, actor.getY());
        }
    };

    /**
     * Represents top alignment. This constant can be used to align a UI component
     * to the top of its container.
     */
    Alignment Top = new Alignment() {
        /**
         * Calculates the position to align the actor to the top of the parent container.
         *
         * @param parent The bounding Rectangle of the parent container.
         * @param actor  The bounding Rectangle of the UI component (actor).
         * @return A Vector2 representing the calculated position for top alignment.
         */
        @Override
        public Vector2 calculatePosition(Rectangle parent, Rectangle actor) {
            float y = parent.getHeight() - actor.getHeight();
            return new Vector2(actor.getX(), y);
        }
    };

    /**
     * Represents bottom alignment. This constant can be used to align a UI component
     * to the bottom of its container.
     */
    Alignment Bottom = new Alignment() {
        /**
         * Calculates the position to align the actor to the bottom of the parent container.
         *
         * @param parent The bounding Rectangle of the parent container.
         * @param actor  The bounding Rectangle of the UI component (actor).
         * @return A Vector2 representing the calculated position for bottom alignment.
         */
        @Override
        public Vector2 calculatePosition(Rectangle parent, Rectangle actor) {
            return new Vector2(actor.getX(), 0);
        }
    };

    /**
     * Represents bottom-center alignment. This constant can be used to align a UI component
     * to the bottom-center of its container horizontally and to the bottom vertically.
     */
    Alignment BottomCenter = new Alignment() {
        /**
         * Calculates the position to align the actor to the bottom-center of the parent container.
         *
         * @param parent The bounding Rectangle of the parent container.
         * @param actor  The bounding Rectangle of the UI component (actor).
         * @return A Vector2 representing the calculated position for bottom-center alignment.
         */
        @Override
        public Vector2 calculatePosition(Rectangle parent, Rectangle actor) {
            float x = (parent.getWidth() - actor.getWidth()) / 2;
            return new Vector2(x, 0);
        }
    };

    /**
     * Represents bottom-left alignment. This constant can be used to align a UI component
     * to the bottom-left corner of its container.
     */
    Alignment BottomLeft = new Alignment() {
        /**
         * Calculates the position to align the actor to the bottom-left corner of the parent container.
         *
         * @param parent The bounding Rectangle of the parent container.
         * @param actor  The bounding Rectangle of the UI component (actor).
         * @return A Vector2 representing the calculated position for bottom-left alignment.
         */
        @Override
        public Vector2 calculatePosition(Rectangle parent, Rectangle actor) {
            return new Vector2(0, 0);
        }
    };

    /**
     * Represents bottom-right alignment. This constant can be used to align a UI component
     * to the bottom-right corner of its container.
     */
    Alignment BottomRight = new Alignment() {
        /**
         * Calculates the position to align the actor to the bottom-right corner of the parent container.
         *
         * @param parent The bounding Rectangle of the parent container.
         * @param actor  The bounding Rectangle of the UI component (actor).
         * @return A Vector2 representing the calculated position for bottom-right alignment.
         */
        @Override
        public Vector2 calculatePosition(Rectangle parent, Rectangle actor) {
            float x = parent.getWidth() - actor.getWidth();
            return new Vector2(x, 0);
        }
    };

    /**
     * Represents top-center alignment. This constant can be used to align a UI component
     * to the top-center of its container horizontally and to the top vertically.
     */
    Alignment TopCenter = new Alignment() {
        /**
         * Calculates the position to align the actor to the top-center of the parent container.
         *
         * @param parent The bounding Rectangle of the parent container.
         * @param actor  The bounding Rectangle of the UI component (actor).
         * @return A Vector2 representing the calculated position for top-center alignment.
         */
        @Override
        public Vector2 calculatePosition(Rectangle parent, Rectangle actor) {
            float x = (parent.getWidth() - actor.getWidth()) / 2;
            float y = parent.getHeight() - actor.getHeight();
            return new Vector2(x, y);
        }
    };

    /**
     * Represents top-left alignment. This constant can be used to align a UI component
     * to the top-left corner of its container.
     */
    Alignment TopLeft = new Alignment() {
        /**
         * Calculates the position to align the actor to the top-left corner of the parent container.
         *
         * @param parent The bounding Rectangle of the parent container.
         * @param actor  The bounding Rectangle of the UI component (actor).
         * @return A Vector2 representing the calculated position for top-left alignment.
         */
        @Override
        public Vector2 calculatePosition(Rectangle parent, Rectangle actor) {
            float y = parent.getHeight() - actor.getHeight();
            return new Vector2(0, y);
        }
    };

    /**
     * Represents top-right alignment. This constant can be used to align a UI component
     * to the top-right corner of its container.
     */
    Alignment TopRight = new Alignment() {
        /**
         * Calculates the position to align the actor to the top-right corner of the parent container.
         *
         * @param parent The bounding Rectangle of the parent container.
         * @param actor  The bounding Rectangle of the UI component (actor).
         * @return A Vector2 representing the calculated position for top-right alignment.
         */
        @Override
        public Vector2 calculatePosition(Rectangle parent, Rectangle actor) {
            float x = parent.getWidth() - actor.getWidth();
            float y = parent.getHeight() - actor.getHeight();
            return new Vector2(x, y);
        }
    };

    /**
     * Represents left-center alignment. This constant can be used to left-center-align a
     * UI component within its container horizontally and center vertically.
     */
    Alignment LeftCenter = new Alignment() {
        /**
         * Calculates the position to left-center-align the actor within the parent container.
         *
         * @param parent The bounding Rectangle of the parent container.
         * @param actor  The bounding Rectangle of the UI component (actor).
         * @return A Vector2 representing the calculated position for left-center alignment.
         */
        @Override
        public Vector2 calculatePosition(Rectangle parent, Rectangle actor) {
            float y = (parent.getHeight() - actor.getHeight()) / 2;
            return new Vector2(0, y);
        }
    };

    /**
     * Represents right-center alignment. This constant can be used to right-center-align a
     * UI component within its container horizontally and center vertically.
     */
    Alignment RightCenter = new Alignment() {
        /**
         * Calculates the position to right-center-align the actor within the parent container.
         *
         * @param parent The bounding Rectangle of the parent container.
         * @param actor  The bounding Rectangle of the UI component (actor).
         * @return A Vector2 representing the calculated position for right-center alignment.
         */
        @Override
        public Vector2 calculatePosition(Rectangle parent, Rectangle actor) {
            float x = parent.getWidth() - actor.getWidth();
            float y = (parent.getHeight() - actor.getHeight()) / 2;
            return new Vector2(x, y);
        }
    };


    /**
     * Calculates the position of the actor within its parent container based on the
     * chosen alignment.
     *
     * @param parent The bounding Rectangle of the parent container.
     * @param actor  The bounding Rectangle of the UI component (actor).
     * @return A Vector2 representing the calculated position of the actor.
     */
    Vector2 calculatePosition(Rectangle parent, Rectangle actor);
}


