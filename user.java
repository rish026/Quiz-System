/**
 * Abstract base class representing a User.
 * Demonstrates encapsulation, validation, and extensibility.
 */
public abstract class User {

    private String name;

    /**
     * Constructor with validation
     * @param name Name of the user
     */
    public User(String name) {
        setName(name);
    }

    /**
     * Getter for name
     * @return user's name
     */
    public String getName() {
        return name;
    }

    /**
     * Setter with validation
     * @param name user's name
     */
    public void setName(String name) {
        if (name == null || name.trim().isEmpty()) {
            throw new IllegalArgumentException("Name cannot be null or empty");
        }
        this.name = name.trim();
    }

    /**
     * Abstract method to define role-specific behavior
     */
    public abstract void displayRole();

    /**
     * Common method for all users
     */
    public void displayInfo() {
        System.out.println("User Name: " + name);
    }
}
