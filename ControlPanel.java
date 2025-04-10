import javax.swing.*;
import java.awt.*;
import java.text.DecimalFormat;

// Control panel for user inputs
public class ControlPanel extends JPanel {
    private SimulationPanel simulationPanel;
    private JSlider daySlider;
    private javax.swing.Timer animationTimer;
    private boolean isPlaying = false;
    
    // Decimal formatter
    private DecimalFormat df = new DecimalFormat("#,##0.00");
    
    // Fonts
    private Font monoFont;
    private Font monoBoldFont;
    
    public ControlPanel(SimulationPanel simPanel) {
        this.simulationPanel = simPanel;
        
        // Load JetBrains Mono font if available, otherwise fallback to monospaced
        try {
            GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
            monoFont = new Font("JetBrains Mono", Font.PLAIN, 12);
            monoBoldFont = new Font("JetBrains Mono", Font.BOLD, 12);
            
            // Fallback if JetBrains Mono is not available
            if (!isFontAvailable("JetBrains Mono")) {
                monoFont = new Font(Font.MONOSPACED, Font.PLAIN, 12);
                monoBoldFont = new Font(Font.MONOSPACED, Font.BOLD, 12);
            }
        } catch (Exception e) {
            monoFont = new Font(Font.MONOSPACED, Font.PLAIN, 12);
            monoBoldFont = new Font(Font.MONOSPACED, Font.BOLD, 12);
        }
        
        // Use a fixed width for better proportions
        setPreferredSize(new Dimension(280, 600));
        setBackground(new Color(5, 10, 20));
        setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        
        ControlPanelSetup setup = new ControlPanelSetup(this, simulationPanel, monoFont, monoBoldFont);
        setup.setupTitlePanel();
        setup.setupSliders();
        setup.setupSpecialDayButtons();
        setup.setupAnimationControls();
        setup.setupInfoPanel();
        setup.setupAnimationTimer();
    }
    
    public void setDaySlider(JSlider daySlider) {
        this.daySlider = daySlider;
    }
    
    public JSlider getDaySlider() {
        return daySlider;
    }
    
    public void setAnimationTimer(javax.swing.Timer animationTimer) {
        this.animationTimer = animationTimer;
    }
    
    public void toggleAnimation() {
        isPlaying = !isPlaying;
        if (isPlaying) {
            animationTimer.start();
        } else {
            animationTimer.stop();
        }
    }
    
    public void addSpacer(int height) {
        add(Box.createVerticalStrut(height));
    }
    
    private boolean isFontAvailable(String fontName) {
        GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
        String[] fontNames = ge.getAvailableFontFamilyNames();
        for (String name : fontNames) {
            if (name.equals(fontName)) {
                return true;
            }
        }
        return false;
    }
} 