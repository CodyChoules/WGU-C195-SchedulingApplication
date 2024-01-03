package applicationTools;

public class DarkModeTool {
    private boolean darkModeOn;
    private String cssResource;

    public DarkModeTool() {
        this.darkModeOn = false;
        this.cssResource = "default.css";
    }

    public DarkModeTool(boolean darkModeOn, String cssResource) {
        this.darkModeOn = darkModeOn;
        this.cssResource = cssResource;
    }

    public boolean isDarkModeOn() { return darkModeOn; }
    public void setDarkModeOn(boolean darkModeOn) { this.darkModeOn = darkModeOn; }

    public String getDarkModeStateAsString() {
        if (darkModeOn){
            return "On";
        } else {
            return "Off";
        }
    }

    public String getCssResource() { return cssResource; }
    public void setCssResource(String cssResource) { this.cssResource = cssResource; }
}

