import javax.swing.*;
import java.awt.*;

// Main application frame
public class MainFrame extends JFrame {
    private SimulationPanel simulationPanel;
    private ControlPanel controlPanel;
    
    public MainFrame() {
        setTitle("Solar Positioning Model - Earth Orbital Simulation");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(1200, 800);
        setLocationRelativeTo(null);
        
        // Set up layout
        setLayout(new BorderLayout());
        
        // Create panels
        simulationPanel = new SimulationPanel();
        controlPanel = new ControlPanel(simulationPanel);
        
        // Add panels to frame
        add(simulationPanel, BorderLayout.CENTER);
        add(controlPanel, BorderLayout.EAST);
        
        // Set dark theme for entire application
        try {
            // Set cross-platform look and feel for consistency
            UIManager.setLookAndFeel(UIManager.getCrossPlatformLookAndFeelClassName());
            
            // Override default colors for dark theme
            UIManager.put("Panel.background", new Color(5, 10, 20));
            UIManager.put("OptionPane.background", new Color(10, 20, 30));
            UIManager.put("ToolTip.background", new Color(20, 30, 50));
            UIManager.put("ToolTip.foreground", new Color(220, 220, 240));
            UIManager.put("ToolTip.border", BorderFactory.createLineBorder(new Color(70, 90, 140), 1));
            
            // Update UI
            SwingUtilities.updateComponentTreeUI(this);
        } catch (Exception e) {
            e.printStackTrace();
        }
        
        // Set app icon if available
        try {
            ImageIcon icon = new ImageIcon(getClass().getResource("/icon.png"));
            if (icon.getIconWidth() > 0) {
                setIconImage(icon.getImage());
            }
        } catch (Exception e) {
            // No icon available, ignore
        }
    }
} 