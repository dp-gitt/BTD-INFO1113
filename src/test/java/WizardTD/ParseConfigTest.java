package WizardTD;

import processing.core.PApplet;
import processing.data.JSONArray;
import processing.data.JSONObject;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class ParseConfigTest {
    @Test
    private JSONObject createSampleConfig() {

        JSONObject config = new JSONObject();
        config.setString("layout", "SampleMap");
        config.setInt("initial_tower_range", 3);
        config.setFloat("initial_tower_firing_speed", 1.5f);
        config.setInt("initial_tower_damage", 10);
        config.setInt("initial_mana", 100);
        config.setInt("initial_mana_cap", 200);
        config.setFloat("initial_mana_gained_per_second", 2.5f);
        config.setInt("tower_cost", 50);
        config.setInt("mana_pool_spell_initial_cost", 30);
        config.setInt("mana_pool_spell_cost_increase_per_use", 10);
        config.setFloat("mana_pool_spell_cap_multiplier", 1.5f);
        config.setFloat("mana_pool_spell_mana_gained_multiplier", 2.0f);
    
        // Create a JSON array for waves
        JSONArray waves = new JSONArray();
        JSONObject wave1 = new JSONObject();
        wave1.setInt("duration", 30);
        wave1.setInt("pre_wave_pause", 10);
    
        // Create an array for monsters within the wave
        JSONArray monsters = new JSONArray();
        JSONObject monster1 = new JSONObject();
        monster1.setString("type", "Gremlin");
        monster1.setFloat("hp", 20.0f);
        monster1.setFloat("speed", 2.0f);
        monster1.setFloat("armour", 5.0f);
        monster1.setFloat("mana_gained_on_kill", 15.0f);
        monster1.setInt("quantity", 5);
        monsters.append(monster1);
    
        // Add more monsters as needed
        // ...
    
        wave1.setJSONArray("monsters", monsters);
        waves.append(wave1);
    
        // Add more waves as needed
        // ...
    
        config.setJSONArray("waves", waves);
    
        return config;
    }

    @Test
    public void ConfigParseValuesTest() {

        
        App app = new App();
        JSONObject config = createSampleConfig();
        
        app.parseConfig(config);

        assertEquals("SampleMap", app.getLevelName());
        assertEquals(3, app.getInitialTowerRange());
        assertEquals(1.5f, app.getInitialTowerFiringSpeed(), 0.001f);
        assertEquals(10, app.getInitialTowerDamage());
        assertEquals(100, app.getInitialMana());
        assertEquals(200, app.getInitialManaCap());
        assertEquals(2.5f, app.getInitialManaGainedPerSecond(), 0.001f);
        assertEquals(50, app.getInitialTowerCost());
        assertEquals(30, app.getManaPoolSpellInitialCost());
        assertEquals(10, app.getManaPoolSpellCostIncreasePerUse());
        assertEquals(1.5f, app.getManaPoolSpellCapMultiplier(), 0.001f);
        assertEquals(2.0f, app.getManaPoolSpellManaGainedMultiplier(), 0.001f);
    }
}
