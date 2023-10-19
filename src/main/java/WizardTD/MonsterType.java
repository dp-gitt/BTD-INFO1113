package WizardTD;

class MonsterType {
    String name;
    float hp;
    float speed;
    float armour;
    float manaGainedOnKill;
    int quantity;
    
    MonsterType(String name, float hp, float speed, float armour, float manaGainedOnKill, int quantity) {
        this.name = name;
        this.hp = hp;
        this.speed = speed;
        this.armour = armour;
        this.manaGainedOnKill = manaGainedOnKill;
        this.quantity = quantity;
    }

    public String getName() {
        return name;
    }

    public float getHp() {
        return hp;
    }

    public float getSpeed() {
        return speed;
    }

    public float getArmour() {
        return armour;
    }

    public float getManaGainedOnKill() {
        return manaGainedOnKill;
    }

    public int getQuantity() {
        return quantity;
    }

    public void decreaseQuantity(int q) {
        quantity -= q;
    }
}
