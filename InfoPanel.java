import javax.swing.*;
import java.awt.*;

public class InfoPanel extends JPanel {
    private Font monoFont;
    private Font monoBoldFont;
    
    public InfoPanel(Font monoFont, Font monoBoldFont) {
        this.monoFont = monoFont;
        this.monoBoldFont = monoBoldFont;
        
        setBackground(new Color(20, 30, 50));
        setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(70, 90, 140), 1),
            BorderFactory.createEmptyBorder(12, 12, 12, 12)
        ));
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        setMaximumSize(new Dimension(280, 150));
        setAlignmentX(Component.CENTER_ALIGNMENT);
        
        JLabel infoTitle = new JLabel("SCIENTIFIC NOTES");
        infoTitle.setForeground(new Color(200, 200, 255));
        infoTitle.setFont(monoBoldFont.deriveFont(12f));
        infoTitle.setAlignmentX(Component.CENTER_ALIGNMENT);
        add(infoTitle);
        add(Box.createVerticalStrut(10));
        
        JTextArea infoTextArea = new JTextArea(
            "Equinoxes occur when the Sun crosses the equator (declination = 0°). " +
            "At these times, day and night are equal length everywhere on Earth.\n\n" +
            "Solstices mark the Sun's maximum northern/southern declination (±23.44°), " +
            "creating the longest and shortest days of the year."
        );
        infoTextArea.setWrapStyleWord(true);
        infoTextArea.setLineWrap(true);
        infoTextArea.setOpaque(false);
        infoTextArea.setEditable(false);
        infoTextArea.setForeground(new Color(180, 180, 220));
        infoTextArea.setFont(monoFont.deriveFont(11f));
        infoTextArea.setAlignmentX(Component.CENTER_ALIGNMENT);
        infoTextArea.setBorder(null);
        infoTextArea.setPreferredSize(new Dimension(230, 90));
        
        add(infoTextArea);
    }
} 