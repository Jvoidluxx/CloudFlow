package cloud.main.module;

public enum Category {
    COMBAT("Combat"),
    MOVEMENT("Movement"),
    WORLD("World"),
    RENDER("Render"),
    MISC("Misc");

    public String displayName;

    Category(String displayName) {
        this.displayName = displayName;
    }
}
