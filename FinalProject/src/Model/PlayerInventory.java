package Model;
import java.util.ArrayList;
import java.util.List;


public class PlayerInventory {
	private List<Item> items;
	private int capacity;
	
	public PlayerInventory(int capacity) {
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
	
	public void useItem(int index, Character target) {
	    Item item = items.get(index);
	    if (item instanceof Potion potion) {
	        potion.use(target);
	        items.remove(potion);
	    } else if (item instanceof Weapon weapon) {
	        weapon.equip(target);
	    }
	}
}
