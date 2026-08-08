package Model;
import java.util.ArrayList;
import java.util.List;

public class Party {
	private List<Character> members;
    private int gold;
    
    public Party(int gold) {
    	this.members = new ArrayList<>();
    	this.gold = gold;
    
    }
    public List<Character> getMembers() {
        return members;
    }

    public int getGold() {
        return gold;
    }

    public void setGold(int gold) {
        this.gold = Math.max(0, gold);
    }

    public void addMember(Character character) {
        members.add(character);
    }
    public boolean removeMember(Character character) {
        return members.remove(character);
    }

    public boolean isPartyDefeated() {
        for (Character c : members) {
            if (c.getHealth() > 0) {
                return false;
            }
        }
        return true;
    }
}
