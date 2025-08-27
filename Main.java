package water_Tank;

import javax.swing.*;
import java.awt.*;

public class Main {
    private JFrame window;
    private JLabel capacityLbl, levelLbl, stateLbl;
    private JButton fillBtn, drainBtn, infoBtn, quitBtn;
    private WaterTank tank;

    public Main(WaterTank tank) {
        this.tank = tank;
        GUI();
    }

    private void GUI() {
        window = new JFrame("Tank Monitoring");
        window.setSize(450, 350);
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        window.setLayout(new BorderLayout(10, 10));

        JLabel title = new JLabel("WATER STORAGE MANAGER", SwingConstants.CENTER);
        title.setFont(new Font("Arial", Font.BOLD, 16));
        window.add(title, BorderLayout.NORTH);

        JPanel infoPanel = new JPanel(new GridLayout(3, 1, 5, 5));
        capacityLbl = new JLabel("Capacity: " + tank.getMaxCapacity() + " L");
        levelLbl = new JLabel("Current Level: " + tank.getWaterAmount() + " L");
        stateLbl = new JLabel("Status: " + tank.Status());

        infoPanel.add(capacityLbl);
        infoPanel.add(levelLbl);
        infoPanel.add(stateLbl);

        window.add(infoPanel, BorderLayout.CENTER);

        JPanel btnPanel = new JPanel(new GridLayout(2, 2, 10, 10));
        fillBtn = new JButton("Fill Tank");
        drainBtn = new JButton("Drain Tank");
        infoBtn = new JButton("Show Info");
        quitBtn = new JButton("Close");

        btnPanel.add(fillBtn);
        btnPanel.add(drainBtn);
        btnPanel.add(infoBtn);
        btnPanel.add(quitBtn);

        window.add(btnPanel, BorderLayout.SOUTH);

        fillBtn.addActionListener(e -> fillTank());
        drainBtn.addActionListener(e -> drainTank());
        infoBtn.addActionListener(e -> showTankInfo());
        quitBtn.addActionListener(e -> System.exit(0));

        window.setVisible(true);
    }

    private void fillTank() {
        if (tank.isFull()) {
            JOptionPane.showMessageDialog(window, "Tank is already at full capacity!");
            return;
        }
        String input = JOptionPane.showInputDialog(window, "Liters to add:");
        if (input == null) return;
        try {
            float liters = Float.parseFloat(input);
            float freeSpace = tank.getMaxCapacity() - tank.getWaterAmount();
            if (liters > freeSpace) {
                JOptionPane.showMessageDialog(window, "Only " + freeSpace + " liters can be added.");
            } else {
                tank.updateLevel(liters);
                refreshLabels();
                JOptionPane.showMessageDialog(window, liters + " liters successfully added!");
            }
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(window, "Please enter a valid number.");
        }
    }

    private void drainTank() {
        if (tank.isEmpty()) {
            JOptionPane.showMessageDialog(window, "Tank is empty");
            return;
        }
        String input = JOptionPane.showInputDialog(window, "Liters to remove:");
        if (input == null) return;
        try {
            float liters = Float.parseFloat(input);
            if (liters > tank.getWaterAmount()) {
                JOptionPane.showMessageDialog(window, "Not enough water available. Current level: " + tank.getWaterAmount() + " L");
            } else {
                tank.updateLevel(-liters);
                refreshLabels();
                JOptionPane.showMessageDialog(window, liters + " liters successfully removed!");
            }
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(window, "Please enter a valid number.");
        }
    }

    private void showTankInfo() {
        JOptionPane.showMessageDialog(window,
                "Tank Info:\n" +
                        "Status: " + tank.Status() + "\n" +
                        "Level: " + tank.getWaterAmount() + " / " + tank.getMaxCapacity() + " L");
    }

    private void refreshLabels() {
        levelLbl.setText("Current Level: " + tank.getWaterAmount() + " L");
        stateLbl.setText("Condition: " + tank.Status());
    }

    public static void main(String[] args) {
        String[] choices = {"Small Tank (100L)", "Large Tank (200L)"};
        int pick = JOptionPane.showOptionDialog(null, "Select Tank Type:", "Tank Setup",
                JOptionPane.DEFAULT_OPTION, JOptionPane.QUESTION_MESSAGE, null, choices, choices[0]);

        WaterTank tankChoice;
        if (pick == 0) {
            tankChoice = new HomeTank();
        } else if (pick == 1) {
            tankChoice = new BuildingTank();
        } else {
            JOptionPane.showMessageDialog(null, "End");
            return;
        }

        new Main(tankChoice);
    }
}
