import javax.swing.*;
import java.awt.*;
import java.awt.geom.*;
import java.text.DecimalFormat;

// Simulation visualization panel
public class SimulationPanel extends JPanel {
    private double dayOfYear = 80; // Around spring equinox (March 21)
    private double latitude = 0; // Equator
    private double timeOfDay = 12.0; // Noon
    
    private static final double OBLIQUITY = 23.44; // Earth's axial tilt in degrees
    private static final int EARTH_RADIUS = 150;
    private static final int SUN_RADIUS = 50;
    // Darker background color
    private static final Color BACKGROUND_COLOR = new Color(2, 6, 15);
    // More transparent grid color
    private static final Color GRID_COLOR = new Color(50, 60, 100, 40);
    // Adjusted sun color
    private static final Color SUN_COLOR = new Color(255, 200, 60);
    private static final Color EARTH_DAY_COLOR = new Color(100, 180, 255);
    private static final Color EARTH_NIGHT_COLOR = new Color(20, 30, 60);
    private static final Color EQUATOR_COLOR = new Color(255, 100, 100, 150);
    private static final Color AXIS_COLOR = new Color(255, 255, 255, 80); // More transparent axis
    
    // Scientific constants
    private static final double ASTRONOMICAL_UNIT = 149.6; // million km
    private static final double EARTH_RADIUS_KM = 6371; // km
    private static final double SUN_RADIUS_KM = 696340; // km
    
    // Elliptical orbit parameters - increase eccentricity for more visible effect
    private static final double ORBIT_SEMIMAJOR = 350; // Semi-major axis
    private static final double ORBIT_ECCENTRICITY = 0.0667; // Exaggerated for visual effect (Earth's is 0.0167)
    private static final double PERIHELION_DAY = 3; // January 3rd (day of year)
    
    // Grid spacing
    private static final int GRID_SPACING = 35; // Closer grid lines
    
    // Decimal formatter for scientific values
    private static final DecimalFormat df = new DecimalFormat("#,##0.00");
    private static final DecimalFormat df_int = new DecimalFormat("#,##0");
    
    // Font
    private Font monoFont;
    private Font monoBoldFont;
    
    public SimulationPanel() {
        setBackground(BACKGROUND_COLOR);
        
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
    
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2d.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
        g2d.setRenderingHint(RenderingHints.KEY_STROKE_CONTROL, RenderingHints.VALUE_STROKE_PURE);
        g2d.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
        
        int width = getWidth();
        int height = getHeight();
        int centerX = width / 2;
        int centerY = height / 2;
        
        // Draw dimmed starfield background
        drawStarfield(g2d, width, height);
        
        // Draw coordinate grid
        drawGrid(g2d, width, height);
        
        // Draw elliptical orbit path
        drawOrbitPath(g2d, centerX, centerY);
        
        // Calculate sun's position based on day of year
        double sunAngle = calculateSunAngle();
        
        // Draw sun
        Point sunPosition = drawSun(g2d, centerX, centerY, sunAngle);
        
        // Draw Earth
        drawEarth(g2d, centerX, centerY, sunPosition);
        
        // Draw shadow line
        drawShadowLine(g2d, centerX, centerY, sunAngle);
        
        // Draw equator line
        drawEquator(g2d, centerX, centerY);
        
        // Draw information
        drawInformation(g2d);
        
        // Draw scientific details
        drawScientificData(g2d, width, height, sunPosition);
    }
    
    private void drawStarfield(Graphics2D g2d, int width, int height) {
        // Create some random stars in the background - dimmed version
        
        // Use a deterministic seed for consistent star positions
        java.util.Random random = new java.util.Random(42);
        
        for (int i = 0; i < 150; i++) { // Reduced number of stars
            int x = random.nextInt(width);
            int y = random.nextInt(height);
            int size = random.nextInt(2) + 1;
            
            // Vary star brightness - dimmed
            int alpha = 20 + random.nextInt(80); // Reduced brightness
            g2d.setColor(new Color(255, 255, 255, alpha));
            
            g2d.fillOval(x, y, size, size);
        }
    }
    
    private void drawGrid(Graphics2D g2d, int width, int height) {
        g2d.setColor(GRID_COLOR);
        g2d.setStroke(new BasicStroke(0.5f));
        
        // Draw horizontal lines
        for (int y = 0; y < height; y += GRID_SPACING) {
            g2d.drawLine(0, y, width, y);
        }
        // Draw vertical lines
        for (int x = 0; x < width; x += GRID_SPACING) {
            g2d.drawLine(x, 0, x, height);
        }
    }
    
    private void drawOrbitPath(Graphics2D g2d, int centerX, int centerY) {
        g2d.setColor(new Color(70, 70, 120, 60));
        g2d.setStroke(new BasicStroke(1.0f, BasicStroke.CAP_BUTT, BasicStroke.JOIN_BEVEL, 
                                      0, new float[]{3, 3}, 0));
        
        // Calculate semi-minor axis
        double semiMinor = ORBIT_SEMIMAJOR * Math.sqrt(1 - ORBIT_ECCENTRICITY * ORBIT_ECCENTRICITY);
        
        // Focal distance from center
        double c = ORBIT_ECCENTRICITY * ORBIT_SEMIMAJOR;
        
        // Draw elliptical orbit - shifted to the left to put the Sun at the right focus
        AffineTransform oldTransform = g2d.getTransform();
        g2d.translate(centerX - c, centerY); // Shift center to put the Sun at the right focus
        g2d.draw(new Ellipse2D.Double(-ORBIT_SEMIMAJOR, -semiMinor, 
                                    ORBIT_SEMIMAJOR * 2, semiMinor * 2));
        
        // Draw the foci - very subtle
        g2d.setColor(new Color(255, 100, 0, 20));
        g2d.fillOval((int)(ORBIT_SEMIMAJOR * ORBIT_ECCENTRICITY * 2) - 3, -3, 6, 6); // Right focus (Sun)
        
        g2d.setTransform(oldTransform);
    }
    
    private Point drawSun(Graphics2D g2d, int centerX, int centerY, double sunAngle) {
        // Calculate elliptical orbit position
        double distance = calculateOrbitalDistance(sunAngle);
        double semiMinor = ORBIT_SEMIMAJOR * Math.sqrt(1 - ORBIT_ECCENTRICITY * ORBIT_ECCENTRICITY);
        
        // Focal distance
        double c = ORBIT_ECCENTRICITY * ORBIT_SEMIMAJOR;
        
        // Calculate sun position using the elliptical orbit
        int sunX = (int)(centerX + c + distance * Math.cos(sunAngle));
        int sunY = (int)(centerY + distance * (semiMinor / ORBIT_SEMIMAJOR) * Math.sin(sunAngle));
        
        // Simple sun with subtle gradient - no rays or decorations
        Paint sunGradient = new RadialGradientPaint(
            new Point(sunX, sunY),
            SUN_RADIUS,
            new float[]{0.0f, 1.0f},
            new Color[]{new Color(255, 255, 150), SUN_COLOR}
        );
        g2d.setPaint(sunGradient);
        g2d.fillOval(sunX - SUN_RADIUS, sunY - SUN_RADIUS, SUN_RADIUS * 2, SUN_RADIUS * 2);
        
        return new Point(sunX, sunY);
    }
    
    private void drawEarth(Graphics2D g2d, int centerX, int centerY, Point sunPosition) {
        // Calculate earth tilt based on current time of day
        double hourAngle = (timeOfDay - 12) * Math.PI / 12;
        
        // Calculate the angle between Earth and Sun for gradient alignment
        double sunEarthAngle = Math.atan2(sunPosition.y - centerY, sunPosition.x - centerX);
        
        // Save current transform
        AffineTransform oldTransform = g2d.getTransform();
        
        // Set up Earth's rotation
        g2d.translate(centerX, centerY);
        g2d.rotate(hourAngle);
        
        // Earth gradient (day/night) - align with Sun direction
        // Create a rotation transform to align gradient with Sun direction
        AffineTransform rotateGradient = new AffineTransform();
        rotateGradient.rotate(sunEarthAngle - hourAngle);
        
        // Create a gradient paint aligned with the Sun
        Point2D start = new Point2D.Double(-EARTH_RADIUS, 0);
        Point2D end = new Point2D.Double(EARTH_RADIUS, 0);
        
        // Transform the gradient points
        Point2D startTransformed = rotateGradient.transform(start, null);
        Point2D endTransformed = rotateGradient.transform(end, null);
        
        // Create gradient paint
        GradientPaint earthGradient = new GradientPaint(
            (float)startTransformed.getX(), (float)startTransformed.getY(), EARTH_NIGHT_COLOR,
            (float)endTransformed.getX(), (float)endTransformed.getY(), EARTH_DAY_COLOR
        );
        
        g2d.setPaint(earthGradient);
        g2d.fillOval(-EARTH_RADIUS, -EARTH_RADIUS, EARTH_RADIUS * 2, EARTH_RADIUS * 2);
        
        // Subtle Earth outline
        g2d.setColor(new Color(255, 255, 255, 60));
        g2d.setStroke(new BasicStroke(1.0f));
        g2d.drawOval(-EARTH_RADIUS, -EARTH_RADIUS, EARTH_RADIUS * 2, EARTH_RADIUS * 2);
        
        // Draw Earth's axis - corrected direction
        g2d.setColor(AXIS_COLOR);
        g2d.setStroke(new BasicStroke(2.0f, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND));
        
        // Apply axial tilt in the correct direction
        g2d.rotate(Math.toRadians(OBLIQUITY));
        
        // Draw the axis line extending beyond the Earth
        int axisExtension = EARTH_RADIUS / 3;
        g2d.drawLine(0, -EARTH_RADIUS - axisExtension, 0, EARTH_RADIUS + axisExtension);
        
        // Draw small circles at the poles - subtle
        g2d.setColor(new Color(200, 220, 255, 100));
        g2d.fillOval(-4, -EARTH_RADIUS - 4, 8, 8); // North pole
        g2d.setColor(new Color(255, 200, 200, 100));
        g2d.fillOval(-4, EARTH_RADIUS - 4, 8, 8);  // South pole
        
        // Restore transform
        g2d.setTransform(oldTransform);
    }
    
    private void drawShadowLine(Graphics2D g2d, int centerX, int centerY, double sunAngle) {
        // Calculate solar declination based on day of year
        double declination = calculateSolarDeclination();
        
        // Save current transform
        AffineTransform oldTransform = g2d.getTransform();
        
        g2d.translate(centerX, centerY);
        
        // Draw Earth's shadow line
        g2d.setColor(new Color(255, 255, 255, 70));
        g2d.setStroke(new BasicStroke(1.0f, BasicStroke.CAP_BUTT, BasicStroke.JOIN_BEVEL, 0, new float[]{3, 3}, 0));
        g2d.drawLine(-EARTH_RADIUS * 2, 0, EARTH_RADIUS * 2, 0);
        
        // Draw the declination line (where sun rays hit perpendicular to Earth's surface)
        g2d.setColor(new Color(255, 255, 0, 70));
        g2d.setStroke(new BasicStroke(1.0f));
        int yOffset = (int)(EARTH_RADIUS * Math.sin(Math.toRadians(declination)));
        g2d.drawLine(-EARTH_RADIUS, yOffset, EARTH_RADIUS, yOffset);
        
        // Restore transform
        g2d.setTransform(oldTransform);
    }
    
    private void drawEquator(Graphics2D g2d, int centerX, int centerY) {
        // Save current transform
        AffineTransform oldTransform = g2d.getTransform();
        
        g2d.translate(centerX, centerY);
        
        // Draw equator
        g2d.setColor(EQUATOR_COLOR);
        g2d.setStroke(new BasicStroke(1.5f));
        g2d.drawOval(-EARTH_RADIUS, -EARTH_RADIUS, EARTH_RADIUS * 2, EARTH_RADIUS * 2);
        
        // Calculate and draw latitude circle
        if (latitude != 0) {
            g2d.setColor(new Color(100, 255, 100, 100));
            g2d.setStroke(new BasicStroke(1.0f, BasicStroke.CAP_BUTT, BasicStroke.JOIN_BEVEL, 0, new float[]{5, 2}, 0));
            
            // Calculate radius of latitude circle
            double latRadius = EARTH_RADIUS * Math.cos(Math.toRadians(latitude));
            int latY = (int)(EARTH_RADIUS * Math.sin(Math.toRadians(latitude)));
            
            g2d.drawOval((int)-latRadius, -latY, (int)(latRadius * 2), (int)(latRadius * 2));
        }
        
        // Restore transform
        g2d.setTransform(oldTransform);
    }
    
    private void drawInformation(Graphics2D g2d) {
        // Create semi-transparent background for text
        g2d.setColor(new Color(10, 20, 40, 160));
        g2d.fillRoundRect(10, 10, 250, 180, 10, 10);
        g2d.setColor(new Color(70, 100, 150, 100));
        g2d.setStroke(new BasicStroke(1.0f));
        g2d.drawRoundRect(10, 10, 250, 180, 10, 10);
        
        g2d.setColor(Color.WHITE);
        g2d.setFont(monoFont.deriveFont(13f));
        
        // Calculate day name based on day of year
        String date = getDayDescription();
        double declination = calculateSolarDeclination();
        double distance = calculateOrbitalDistance(calculateSunAngle());
        // Convert to millions of km (approximate scale)
        double distanceInMillionKm = distance / ORBIT_SEMIMAJOR * ASTRONOMICAL_UNIT;
        
        int y = 35;
        g2d.drawString("Date: " + date, 20, y);
        y += 22;
        g2d.drawString(String.format("Day of Year: %.0f", dayOfYear), 20, y);
        y += 22;
        g2d.drawString(String.format("Solar Declination: %s°", df.format(declination)), 20, y);
        y += 22;
        g2d.drawString(String.format("Sun Distance: %s × 10⁶ km", df.format(distanceInMillionKm)), 20, y);
        y += 22;
        g2d.drawString(String.format("Latitude: %s°", df.format(latitude)), 20, y);
        y += 22;
        g2d.drawString(String.format("Time: %s:00", df.format(timeOfDay)), 20, y);
        
        // Equinox/solstice status
        if (Math.abs(dayOfYear - 80) < 2) { // Spring equinox (around day 80)
            drawStatusBadge(g2d, "Spring Equinox", new Color(100, 200, 100));
        } else if (Math.abs(dayOfYear - 172) < 2) { // Summer solstice (around day 172)
            drawStatusBadge(g2d, "Summer Solstice", new Color(255, 150, 50));
        } else if (Math.abs(dayOfYear - 266) < 2) { // Fall equinox (around day 266)
            drawStatusBadge(g2d, "Fall Equinox", new Color(200, 100, 50));
        } else if (Math.abs(dayOfYear - 356) < 2 || dayOfYear < 10) { // Winter solstice (around day 356)
            drawStatusBadge(g2d, "Winter Solstice", new Color(100, 150, 255));
        }
    }
    
    private void drawScientificData(Graphics2D g2d, int width, int height, Point sunPosition) {
        // Scientific data panel at bottom left
        int panelWidth = 320;
        int panelHeight = 140;
        int padding = 15;
        
        g2d.setColor(new Color(5, 15, 30, 180));
        g2d.fillRoundRect(15, height - panelHeight - 15, panelWidth, panelHeight, 8, 8);
        g2d.setColor(new Color(60, 80, 120, 100));
        g2d.setStroke(new BasicStroke(1.0f));
        g2d.drawRoundRect(15, height - panelHeight - 15, panelWidth, panelHeight, 8, 8);
        
        g2d.setFont(monoBoldFont.deriveFont(12f));
        g2d.setColor(new Color(200, 200, 255));
        g2d.drawString("PHYSICAL PARAMETERS", 30, height - panelHeight - 15 + padding + 12);
        
        g2d.setFont(monoFont.deriveFont(11f));
        g2d.setColor(new Color(180, 180, 220));
        
        int y = height - panelHeight - 15 + padding + 38;
        int x1 = 30;
        int x2 = 200;
        
        // Sun data
        g2d.drawString("Sun radius:", x1, y);
        g2d.drawString(df_int.format(SUN_RADIUS_KM) + " km", x2, y);
        y += 18;
        
        // Earth data
        g2d.drawString("Earth radius:", x1, y);
        g2d.drawString(df_int.format(EARTH_RADIUS_KM) + " km", x2, y);
        y += 18;
        
        // Orbital eccentricity
        g2d.drawString("Orbital eccentricity:", x1, y);
        g2d.drawString(df.format(0.0167), x2, y);
        y += 18;
        
        // Axial tilt
        g2d.drawString("Axial tilt:", x1, y);
        g2d.drawString(df.format(OBLIQUITY) + "°", x2, y);
        y += 18;
        
        // Orbital period
        g2d.drawString("Orbital period:", x1, y);
        g2d.drawString("365.256 days", x2, y);
        
        // Draw Earth scale indicator on bottom right
        drawScaleIndicator(g2d, width - 200, height - 60);
    }
    
    private void drawScaleIndicator(Graphics2D g2d, int x, int y) {
        int lineLength = 100;
        
        g2d.setColor(new Color(150, 150, 200, 120));
        g2d.setStroke(new BasicStroke(1.0f));
        g2d.drawLine(x, y, x + lineLength, y);
        
        // Ticks
        g2d.drawLine(x, y - 3, x, y + 3);
        g2d.drawLine(x + lineLength, y - 3, x + lineLength, y + 3);
        
        // Scale label
        g2d.setFont(monoFont.deriveFont(10f));
        g2d.drawString("Scale: 1:" + df_int.format(ASTRONOMICAL_UNIT * 1000000 / ORBIT_SEMIMAJOR), x, y - 10);
        g2d.drawString(df_int.format(lineLength * ASTRONOMICAL_UNIT / ORBIT_SEMIMAJOR) + " million km", x + 10, y + 15);
    }
    
    private void drawStatusBadge(Graphics2D g2d, String text, Color color) {
        g2d.setFont(monoBoldFont.deriveFont(12f));
        FontMetrics fm = g2d.getFontMetrics();
        int textWidth = fm.stringWidth(text);
        
        int x = getWidth() - textWidth - 25;
        int y = 30;
        
        // Draw badge background
        g2d.setColor(new Color(color.getRed(), color.getGreen(), color.getBlue(), 40));
        g2d.fillRoundRect(x - 10, y - 20, textWidth + 20, 26, 8, 8);
        
        g2d.setColor(new Color(color.getRed(), color.getGreen(), color.getBlue(), 100));
        g2d.setStroke(new BasicStroke(1.0f));
        g2d.drawRoundRect(x - 10, y - 20, textWidth + 20, 26, 8, 8);
        
        g2d.setColor(new Color(color.getRed(), color.getGreen(), color.getBlue(), 220));
        g2d.drawString(text, x, y);
    }
    
    // Calculate sun's position angle based on day of year
    private double calculateSunAngle() {
        return 2 * Math.PI * dayOfYear / 365;
    }
    
    // Calculate distance in the elliptical orbit based on angle
    private double calculateOrbitalDistance(double angle) {
        // Use the polar form of an ellipse with one focus at the origin
        return ORBIT_SEMIMAJOR * (1 - ORBIT_ECCENTRICITY * ORBIT_ECCENTRICITY) / 
               (1 + ORBIT_ECCENTRICITY * Math.cos(angle - Math.toRadians(PERIHELION_DAY * 360 / 365)));
    }
    
    // Calculate solar declination using Cooper's equation
    private double calculateSolarDeclination() {
        return -OBLIQUITY * Math.cos(2 * Math.PI * (dayOfYear + 10) / 365);
    }
    
    private String getDayDescription() {
        // Approximate month and day from day of year
        int[] daysInMonth = {31, 28, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31};
        String[] monthNames = {"January", "February", "March", "April", "May", "June", 
                              "July", "August", "September", "October", "November", "December"};
        
        int remainingDays = (int)dayOfYear;
        int month = 0;
        
        while (month < 12 && remainingDays > daysInMonth[month]) {
            remainingDays -= daysInMonth[month];
            month++;
        }
        
        return monthNames[month] + " " + remainingDays;
    }
    
    // Setter methods for control panel
    public void setDayOfYear(double day) {
        this.dayOfYear = day;
        repaint();
    }
    
    public void setLatitude(double latitude) {
        this.latitude = latitude;
        repaint();
    }
    
    public void setTimeOfDay(double time) {
        this.timeOfDay = time;
        repaint();
    }
    
    // Get the current values
    public double getDayOfYear() {
        return dayOfYear;
    }
    
    public double getLatitude() {
        return latitude;
    }
    
    public double getTimeOfDay() {
        return timeOfDay;
    }
} 