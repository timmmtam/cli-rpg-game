package Model;
import java.util.ArrayList;
import java.util.List;


public class Inventory {
	private List<Item> items;
	private int capacity;
	
	public Inventory(int capacity) {
		this.items = new ArrayList<>();
		this.capacity = capacity;
		
	}

	public List<Item> getItems() {
		return items;
	}

	public void setItems(List<Item> items) {
		this.items = items;
	}

	public int getCapacity() {
		return capacity;
	}

	public void setCapacity(int capacity) {
		this.capacity = capacity;
	}
	
	public void addItem(Item item) {
		if (items.size() >= this.capacity) {
			System.out.println("Inventory is full, cannot add " + item.getName());
	        return;
		}
	    items.add(item);
	}
	
	public boolean removeItem(Item item) {
		return items.remove(item);
	}
	
	public void useItem(Item item, Character target) {
        if (items.contains(item)) {
            if (item instanceof Potion potion) {
                potion.use(target);
                items.remove(potion); // Consumes the potion
            } else if (item instanceof Weapon weapon) {
                weapon.equip(target);
            }
        } else {
            System.out.println("Item not found in inventory.");
        }
    }
}
