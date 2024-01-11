package applicationObject;

/**
 * Represents a basic application object with an identifier (ID) and a name.
 * This was implemented Late so is not used across the project.
 * I did not realize the inheritable properties when starting the project.
 *
 * TODO [Extra] - restructure entire project to remove non inherited Id and Name values from other application. obj.
 * */
public class ApplicationObject {

    /** The Identification (ID) of the application object. Note: Id is a shortened Identification so ID in not used in Java unlike the DB to follow camel case unless at the end of a name*/
    public int id;

    /** The name of the application object. */
    public String name;

    /**
     * Returns the id of the application object.
     * @return The id.
     */
    public int getId() { return id; }

    /**
     * Sets the id of the application object.
     *
     * @param id new id
     */
    public void setId(int id) { this.id = id; }

    /**
     * Returns the name of the application object.
     *
     * @return name as String
     */
    public String getName() { return name; }

    /**
     * Sets the name of the application object.
     *
     * @param name sets new name
     * */
    public void setName(String name) { this.name = name; }


}
