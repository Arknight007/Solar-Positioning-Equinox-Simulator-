import javax.swing.*;
import java.awt.*;

public class ControlPanelSetup {
    private ControlPanel controlPanel;
    private SimulationPanel simulationPanel;
    private Font monoFont;
    private Font monoBoldFont;
    
    public ControlPanelSetup(ControlPanel controlPanel, SimulationPanel simulationPanel, Font monoFont, Font monoBoldFont) {
        this.controlPanel = controlPanel;
        this.simulationPanel = simulationPanel;
        this.monoFont = monoFont;
        this.monoBoldFont = monoBoldFont;
    }
    
    public void setupTitlePanel() {
        JPanel titlePanel = new JPanel();
        titlePanel.setOpaque(false);
        titlePanel.setLayout(new BoxLayout(titlePanel, BoxLayout.Y_AXIS));
        titlePanel.setAlignmentX(Component.CENTER_ALIGNMENT);
        
        JLabel titleLabel = new JLabel("SOLAR POSITIONING MODEL");
        titleLabel.setForeground(new Color(200, 200, 255));
        titleLabel.setFont(monoBoldFont.deriveFont(14f));
        titleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        
        JLabel subtitleLabel = new JLabel("SIMULATION PARAMETERS");
        subtitleLabel.setForeground(new Color(150, 150, 220));
        subtitleLabel.setFont(monoFont.deriveFont(12f));
        subtitleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        
        titlePanel.add(titleLabel);
        titlePanel.add(Box.createVerticalStrut(5));
        titlePanel.add(subtitleLabel);
        
        controlPanel.add(titlePanel);
        controlPanel.addSpacer(20);
    }
    
    public void setupSliders() {
        JLabel dayLabel = createSectionLabel("Day of Year");
        controlPanel.add(dayLabel);
        
        JSlider daySlider = new CustomSlider(1, 365, (int)simulationPanel.getDayOfYear(), monoFont);
        daySlider.addChangeListener(e -> simulationPanel.setDayOfYear(daySlider.getValue()));
        controlPanel.add(daySlider);
        controlPanel.setDaySlider(daySlider);
        controlPanel.addSpacer(15);
        
        JLabel latLabel = createSectionLabel("Latitude (°)");
        controlPanel.add(latLabel);
        
        JSlider latitudeSlider = new CustomSlider(-90, 90, (int)simulationPanel.getLatitude(), monoFont);
        latitudeSlider.addChangeListener(e -> simulationPanel.setLatitude(latitudeSlider.getValue()));
        controlPanel.add(latitudeSlider);
        controlPanel.addSpacer(15);
        
        JLabel timeLabel = createSectionLabel("Time of Day");
        controlPanel.add(timeLabel);
        
        JSlider timeSlider = new CustomSlider(0, 24, (int)simulationPanel.getTimeOfDay(), monoFont);
        timeSlider.addChangeListener(e -> simulationPanel.setTimeOfDay(timeSlider.getValue()));
        controlPanel.add(timeSlider);
        controlPanel.addSpacer(25);
    }
    
    public void setupSpecialDayButtons() {
        JLabel presetLabel = createSectionLabel("Astronomical Events");
        controlPanel.add(presetLabel);
        controlPanel.addSpacer(10);
        
        JPanel buttonPanel = new JPanel(new GridLayout(2, 2, 10, 10));
        buttonPanel.setBackground(new Color(5, 10, 20));
        buttonPanel.setMaximumSize(new Dimension(280, 80));
        buttonPanel.setAlignmentX(Component.CENTER_ALIGNMENT);
        
        JButton springEquinoxBtn = new SpecialDayButton("Spring Equinox", 80, new Color(60, 140, 60), monoBoldFont,
            () -> {
                simulationPanel.setDayOfYear(80);
                controlPanel.getDaySlider().setValue(80);
            });
        
        JButton summerSolsticeBtn = new SpecialDayButton("Summer Solstice", 172, new Color(180, 100, 30), monoBoldFont,
            () -> {
                simulationPanel.setDayOfYear(172);
                controlPanel.getDaySlider().setValue(172);
            });
        
        JButton fallEquinoxBtn = new SpecialDayButton("Fall Equinox", 266, new Color(140, 60, 30), monoBoldFont,
            () -> {
                simulationPanel.setDayOfYear(266);
                controlPanel.getDaySlider().setValue(266);
            });
        
        JButton winterSolsticeBtn = new SpecialDayButton("Winter Solstice", 356, new Color(60, 100, 180), monoBoldFont,
            () -> {
                simulationPanel.setDayOfYear(356);
                controlPanel.getDaySlider().setValue(356);
            });
        
        buttonPanel.add(springEquinoxBtn);
        buttonPanel.add(summerSolsticeBtn);
        buttonPanel.add(fallEquinoxBtn);
        buttonPanel.add(winterSolsticeBtn);
        
        controlPanel.add(buttonPanel);
        controlPanel.addSpacer(25);
    }
    
    public void setupAnimationControls() {
        JLabel animLabel = createSectionLabel("Animation Control");
        controlPanel.add(animLabel);
        controlPanel.addSpacer(10);
        
        JPanel animPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        animPanel.setBackground(new Color(5, 10, 20));
        animPanel.setMaximumSize(new Dimension(280, 40));
        
        JButton playBtn = new JButton("Run Simulation");
        playBtn.setFont(monoBoldFont.deriveFont(12f));
        playBtn.setFocusPainted(false);
        playBtn.setPreferredSize(new Dimension(140, 30));
        playBtn.setBackground(new Color(30, 40, 70));
        playBtn.setForeground(new Color(230, 230, 255));
        playBtn.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(100, 120, 180), 1),
            BorderFactory.createEmptyBorder(4, 8, 4, 8)
        ));
        playBtn.addActionListener(e -> controlPanel.toggleAnimation());
        animPanel.add(playBtn);
        
        controlPanel.add(animPanel);
        controlPanel.addSpacer(25);
    }
    
    public void setupInfoPanel() {
        controlPanel.add(new InfoPanel(monoFont, monoBoldFont));
    }
    
    public void setupAnimationTimer() {
        javax.swing.Timer[] timerRef = new javax.swing.Timer[1];
        timerRef[0] = new javax.swing.Timer(50, e -> {
            double day = simulationPanel.getDayOfYear();
            double nextDay = (day % 365) + 1;
            
            boolean isApproachingSpecialDay = isApproachingSpecialDay(day, nextDay);
            
            simulationPanel.setDayOfYear(nextDay);
            controlPanel.getDaySlider().setValue((int)nextDay);
            
            if (isApproachingSpecialDay) {
                timerRef[0].setDelay(200);
            } else {
                timerRef[0].setDelay(50);
            }
        });
        controlPanel.setAnimationTimer(timerRef[0]);
    }
    
    private boolean isApproachingSpecialDay(double currentDay, double nextDay) {
        double[] specialDays = {80, 172, 266, 356};
        
        for (double specialDay : specialDays) {
            if ((currentDay < specialDay && nextDay >= specialDay) || 
                (specialDay == 356 && currentDay > 356 && nextDay < 10)) {
                return true;
            }
        }
        return false;
    }
    
    private JLabel createSectionLabel(String text) {
        JLabel label = new JLabel(text.toUpperCase());
        label.setForeground(new Color(170, 180, 230));
        label.setFont(monoBoldFont.deriveFont(12f));
        label.setAlignmentX(Component.CENTER_ALIGNMENT);
        return label;
    }
} 