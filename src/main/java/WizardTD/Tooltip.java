package WizardTD;

public class ToolTip {

        ManaBar manaBar;

        public ToolTip(ManaBar manaBar) {
                this.manaBar = manaBar;
        }

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



                // int totalCost = 0;

                // app.fill(255);
                // app.rect(tooltipX + 5 ,tooltipY, 90 , 100);
                // app.textSize(12);
                // app.stroke(1);

                // app.strokeWeight(5);
                // app.fill(0);

                // if (rangeUpgrade) {
                // app.text("Range Upgrade: " + rangeUpgradeCost, tooltipX + 10, tooltipY + 20);
                // totalCost += rangeUpgradeCost;
                // }

                // // Check if speed upgrade is selected and display its cost
                // if (speedUpgrade) {
                // app.text("Speed Upgrade: " + speedUpgradeCost, tooltipX + 10, tooltipY + 40);
                // totalCost += speedUpgradeCost;
                // }

                // // Check if damage upgrade is selected and display its cost
                // if (damageUpgrade) {
                // app.text("Damage Upgrade: " + damageUpgradeCost, tooltipX + 10, tooltipY +
                // 60);
                // totalCost += damageUpgradeCost;
                // }

                // // Display the total cost at the bottom of the tooltip
                // app.text("Total Cost: " + totalCost, tooltipX + 10, tooltipY + 80);

        }

        // tooltipCostCheck(true, true, false);
        /// with arguments (range, speed, damage)
        // it then reads the levels and then checks the cost. If there is insufficient,
        // then it doesnt allow the lines below.
        // tooltip.drawCost()

}
