package house.strategy;

import creatures.entities.Entity;

import java.io.IOException;

/**
 * Strategy interface for different automation modes.
 * Implements the Strategy pattern.
 */
public interface Strategy {
    /**
     * Find an activity for the entity based on the strategy.
     *
     * @param entity The entity to find an activity for
     * @throws IOException If an error occurs
     */
    void findActivity(Entity entity) throws IOException;
    
    /**
     * Get the name of the strategy.
     *
     * @return The strategy name
     */
    String getName();
}
