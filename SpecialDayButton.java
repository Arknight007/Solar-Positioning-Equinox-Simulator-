import javax.swing.*;
import java.awt.*;

public class SpecialDayButton extends JButton {
    public SpecialDayButton(String text, int day, Color color, Font monoBoldFont, Runnable onAction) {
        super(text);
        setFont(monoBoldFont.deriveFont(11f));
        setBackground(new Color(color.getRed()/4, color.getGreen()/4, color.getBlue()/4));
        setForeground(new Color(240, 240, 250));
        setFocusPainted(false);
        setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(color.getRed(), color.getGreen(), color.getBlue(), 180), 1),
            BorderFactory.createEmptyBorder(4, 4, 4, 4)
        ));
        
        String date = getDateForDayOfYear(day);
        setToolTipText(date + " (Day " + day + ")");
        
        addActionListener(e -> onAction.run());
    }
    
    private String getDateForDayOfYear(int day) {
        String[] monthNames = {"January", "February", "March", "April", "May", "June", 
                              "July", "August", "September", "October", "November", "December"};
        int[] daysInMonth = {31, 28, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31};
        
        int remainingDays = day;
        int month = 0;
        
        while (month < 12 && remainingDays > daysInMonth[month]) {
            remainingDays -= daysInMonth[month];
            month++;
        }
        
        return monthNames[month] + " " + remainingDays;
    }
} 