import javax.swing.*;
import java.awt.*;
import java.util.Hashtable;

public class CustomSlider extends JSlider {
    private Font monoFont;
    
    public CustomSlider(int min, int max, int value, Font monoFont) {
        super(JSlider.HORIZONTAL, min, max, value);
        this.monoFont = monoFont;
        
        setBackground(new Color(5, 10, 20));
        setForeground(Color.WHITE);
        setPaintTicks(true);
        setPaintLabels(true);
        
        int range = max - min;
        int majorTick = range / 4;
        setMajorTickSpacing(majorTick);
        setMinorTickSpacing(majorTick / 2);
        
        if (min == 1 && max == 365) {
            Hashtable<Integer, JLabel> labelTable = new Hashtable<>();
            labelTable.put(1, createSliderLabel("Jan"));
            labelTable.put(90, createSliderLabel("Apr"));
            labelTable.put(182, createSliderLabel("Jul"));
            labelTable.put(274, createSliderLabel("Oct"));
            labelTable.put(365, createSliderLabel("Dec"));
            setLabelTable(labelTable);
        }
        else if (min == 0 && max == 24) {
            Hashtable<Integer, JLabel> labelTable = new Hashtable<>();
            labelTable.put(0, createSliderLabel("00:00"));
            labelTable.put(6, createSliderLabel("06:00"));
            labelTable.put(12, createSliderLabel("12:00"));
            labelTable.put(18, createSliderLabel("18:00"));
            labelTable.put(24, createSliderLabel("24:00"));
            setLabelTable(labelTable);
        }
        
        setMaximumSize(new Dimension(240, 50));
        setAlignmentX(Component.CENTER_ALIGNMENT);
    }
    
    private JLabel createSliderLabel(String text) {
        JLabel label = new JLabel(text);
        label.setForeground(new Color(170, 180, 230));
        label.setFont(monoFont.deriveFont(10f));
        return label;
    }
} 