package applicationTools;

/**
 * Utility class for managing dark mode across the project. Initialize on start.
 */
public class DarkModeTool {
    private boolean darkModeOn;
    private String cssResource;

    /**
     * Constructs a DarkModeTool with the specified initial state and CSS resource.
     * @param darkModeOn   Initial state of dark mode (true if enabled, false if disabled).
     * @param cssResource CSS resource file for dark mode
     */
    public DarkModeTool(boolean darkModeOn, String cssResource) {
        this.darkModeOn = darkModeOn;
        this.cssResource = cssResource;
    }

    /**
     * Checks if dark mode is currently enabled.
     * @return True if dark mode is enabled
     */
    public boolean isDarkModeOn() { return darkModeOn; }
    /**
     * Sets the state of dark mode.
     * @param darkModeOn True to enable dark mode; false to disable.
     */
    public void setDarkModeOn(boolean darkModeOn) { this.darkModeOn = darkModeOn; }

    /**
     * Gets the state of dark mode as a string ("On" if enabled, "Off" if disabled).
     *
     * @return The state of dark mode as a string "On" -or- "Off"
     */
    public String getDarkModeStateAsString() {
        if (darkModeOn){
            return "On";
        } else {
            return "Off";
        }
    }

    /**
     * Gets the CSS resource file for dark mode
     *
     * @return CSS resource
     */
    public String getCssResource() { return cssResource; }

    /**
     * Sets the CSS resource file for dark mode
     *
     * @param cssResource CSS resource
     */
    public void setCssResource(String cssResource) { this.cssResource = cssResource; }
}

