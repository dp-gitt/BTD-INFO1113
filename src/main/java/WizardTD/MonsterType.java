package WizardTD;

class MonsterType {
    String name;
    float hp;
    float speed;
    float armour;
    float manaGainedOnKill;
    int quantity;

    /**
     * Creates a new MonsterType with the specified attributes.
     *
     * @param name             The name of the monster type.
     * @param hp               The hit points (health) of the monster type.
     * @param speed            The speed of the monster type.
     * @param armour           The armor of the monster type.
     * @param manaGainedOnKill The amount of mana gained when killing this monster
     *                         type.
     * @param quantity         The initial quantity of monsters of this type.
     */
    MonsterType(String name, float hp, float speed, float armour, float manaGainedOnKill, int quantity) {
        this.name = name;
        this.hp = hp;
        this.speed = speed;
        this.armour = armour;
        this.manaGainedOnKill = manaGainedOnKill;
        this.quantity = quantity;
    }

    /**
     * Gets the name of the monster type.
     *
     * @return The name of the monster type.
     */
    public String getName() {
        return name;
    }

    /**
     * Gets the hit points (health) of the monster type.
     *
     * @return The hit points of the monster type.
     */
    public float getHp() {
        return hp;
    }

    /**
     * Gets the speed of the monster type.
     *
     * @return The speed of the monster type.
     */
    public float getSpeed() {
        return speed;
    }

    /**
     * Gets the armor of the monster type.
     *
     * @return The armor of the monster type.
     */
    public float getArmour() {
        return armour;
    }

    /**
     * Gets the amount of mana gained when killing this monster type.
     *
     * @return The amount of mana gained on killing the monster type.
     */
    public float getManaGainedOnKill() {
        return manaGainedOnKill;
    }

    /**
     * Gets the current quantity of monsters of this type.
     *
     * @return The current quantity of monsters.
     */
    public int getQuantity() {
        return quantity;
    }

    /**
     * Decreases the quantity of monsters of this type.
     *
     * @param q The amount by which to decrease the quantity.
     */
    public void decreaseQuantity(int q) {
        quantity -= q;
    }

    /**
     * Multiplies the mana gained on killing this monster type by the specified
     * multiplier.
     *
     * @param manaPoolSpellManaGainedMultiplier The multiplier to apply to mana
     *                                          gained on kill.
     */
    public void multiplyManaGainedOnKill(float manaPoolSpellManaGainedMultiplier) {
        manaGainedOnKill *= manaPoolSpellManaGainedMultiplier;
    }
}