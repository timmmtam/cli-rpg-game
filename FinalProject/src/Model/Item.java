package Model;

public abstract class Item {

    private String name;
    private String description;
    private int value;

    public Item(String name, String description, int value) {
        this.name = name;
        this.description = description;
        this.value = value;
    }

    public Item(String name, int value) {
        this(name, "No description available.", value);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        if (value < 0) {
        	this.value = Math.max(0, value);
        } else {
        	this.value = value;
        }
    }

    @Override
    public String toString() {
        return String.format("%s (worth %d gold) - %s", name, value, description);
    }
}
