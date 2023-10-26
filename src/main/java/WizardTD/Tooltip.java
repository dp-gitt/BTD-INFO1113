package WizardTD;

public class ToolTip {

        ManaBar manaBar;

        /**
         * Creates a new ToolTip associated with the given ManaBar.
         *
         * @param manaBar The ManaBar instance to associate the ToolTip with.
         */
        public ToolTip(ManaBar manaBar) {
                this.manaBar = manaBar;
        }

        /**
         * Checks if there is enough mana to purchase the selected tower upgrades.
         *
         * @param rangeUpgrade  Indicates whether the range upgrade is selected.
         * @param speedUpgrade  Indicates whether the speed upgrade is selected.
         * @param damageUpgrade Indicates whether the damage upgrade is selected.
         * @param tower         The tower for which upgrades are being checked.
         * 
         * @return `true` if there is enough mana, `false` otherwise.
         */
        public boolean toolTipCostCheck(boolean rangeUpgrade, boolean speedUpgrade, boolean damageUpgrade,
                        Tower tower) {

                boolean isEnough;

                int rangeUpgradeCost = tower.getUpgradeRangeCost();
                int speedUpgradeCost = tower.getUpgradeSpeedCost();
                int damageUpgradeCost = tower.getUpgradeDamageCost();

                int selectedUpgradeCost = 0;

                if (rangeUpgrade) {
                        selectedUpgradeCost += rangeUpgradeCost;
                }

                if (speedUpgrade) {
                        selectedUpgradeCost += speedUpgradeCost;
                }

                if (damageUpgrade) {
                        selectedUpgradeCost += damageUpgradeCost;
                }

                if ((ManaBar.getMana() - selectedUpgradeCost) <= 0) {
                        isEnough = false;
                } else {
                        isEnough = true;
                }

                return isEnough;

        }

        /**
         * Draws a tooltip on the screen with information about selected tower upgrades.
         *
         * @param app           The main app instance.
         * @param rangeUpgrade  Indicates whether the range upgrade is selected.
         * @param speedUpgrade  Indicates whether the speed upgrade is selected.
         * @param damageUpgrade Indicates whether the damage upgrade is selected.
         * @param tower         The tower for which upgrades are being displayed.
         */
        public void drawToolTip(App app, boolean rangeUpgrade, boolean speedUpgrade, boolean damageUpgrade,
                        Tower tower) {

                int rangeUpgradeCost = tower.getUpgradeRangeCost();
                int speedUpgradeCost = tower.getUpgradeSpeedCost();
                int damageUpgradeCost = tower.getUpgradeDamageCost();

                float tooltipX = 640 + 2; // Adjust the X position as needed
                float tooltipY = 640 - 100; // Adjust the Y position as needed
                float tooltipWidth = 115; // Adjust the width as needed
                float tooltipHeight = 110; // Adjust the height as needed
                float cornerRadius = 5; // Adjust the corner radius as needed

                // Set the background color and stroke for the tooltip
                app.fill(255); // Background color (white)
                app.stroke(0); // Border color (black)
                app.strokeWeight(1);

                // Draw the tooltip background with rounded corners
                app.rect(tooltipX, tooltipY - 20, tooltipWidth, tooltipHeight, cornerRadius);

                // Set text properties for the tooltip
                app.fill(0); // Text color (black)
                app.textSize(11); // Set the text size here

                // Initialize total cost
                int totalCost = 0;

                // Display upgrade costs
                float textY = tooltipY + 20; // Initial Y position for text

                // Check if range upgrade is selected and display its cost
                if (rangeUpgrade) {
                        // Draw a white background behind the text
                        app.fill(255); // White background color
                        app.noStroke(); // No border for the background
                        app.rect(tooltipX + 10, textY - 14, tooltipWidth - 20, 20); // Adjust size and position

                        // Display the text on the white background
                        app.fill(0); // Text color (black)
                        app.text("Range Upgrade: " + rangeUpgradeCost, tooltipX + 5, textY);

                        totalCost += rangeUpgradeCost;
                        textY += 20; // Adjust the vertical spacing
                }

                // Check if speed upgrade is selected and display its cost
                if (speedUpgrade) {
                        // Draw a white background behind the text
                        app.fill(255); // White background color
                        app.noStroke(); // No border for the background
                        app.rect(tooltipX + 10, textY - 14, tooltipWidth - 20, 20); // Adjust size and position

                        // Display the text on the white background
                        app.fill(0); // Text color (black)
                        app.text("Speed Upgrade: " + speedUpgradeCost, tooltipX + 5, textY);

                        totalCost += speedUpgradeCost;
                        textY += 20; // Adjust the vertical spacing
                }

                // Check if damage upgrade is selected and display its cost
                if (damageUpgrade) {
                        // Draw a white background behind the text
                        app.fill(255); // White background color
                        app.noStroke(); // No border for the background
                        app.rect(tooltipX + 10, textY - 14, tooltipWidth - 20, 20); // Adjust size and position

                        // Display the text on the white background
                        app.fill(0); // Text color (black)
                        app.text("Damage Upgrade: " + damageUpgradeCost, tooltipX + 5, textY);

                        totalCost += damageUpgradeCost;
                        textY += 20; // Adjust the vertical spacing
                }

                // Display the total cost at the bottom of the tooltip
                app.fill(0); // Text color (black)
                app.textSize(14);
                app.text("Total Cost: " + totalCost, tooltipX + 5, tooltipY + 80);
                app.text("Upgrade Cost", tooltipX + 5, tooltipY);
        }
}
