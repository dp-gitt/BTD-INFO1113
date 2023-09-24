package WizardTD;

import java.util.ArrayList;
import processing.core.PVector;

public class Monster {
    String type;
    int hp;
    float speed;
    float armour;
    float manaGainedOnKill;
    PVector spawnPoint;

    public Monster(String type, int hp,float speed,float armour,float manaGainedOnKill, PVector spawnPoint) {
        this.type = type;
        this.hp = hp;
        this.speed = speed;
        this.armour = armour;
        this.manaGainedOnKill = manaGainedOnKill;
        this.spawnPoint = spawnPoint;
    }

}
