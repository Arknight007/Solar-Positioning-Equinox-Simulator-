class SolarCalculations {
    private static final double OBLIQUITY = 23.44;
    

    public static double calculateSolarDeclination(double dayOfYear) {
        // Cooper's equation for solar declination
        return OBLIQUITY * Math.sin((2 * Math.PI * (dayOfYear - 81)) / 365);
    }
    
    public static double calculateEquationOfTime(double dayOfYear) {
        double b = 2 * Math.PI * (dayOfYear - 81) / 365;
        return 9.87 * Math.sin(2*b) - 7.53 * Math.cos(b) - 1.5 * Math.sin(b);
    }
    

    public static double calculateHourAngle(double localTime, double longitude, double eqTime) {
        // Convert local time to local solar time
        double localSolarTime = localTime + eqTime/60 + longitude/15;
        return 15 * (localSolarTime - 12);
    }
    

    public static double calculateZenithAngle(double declination, double latitude, double hourAngle) {
        double decRad = Math.toRadians(declination);
        double latRad = Math.toRadians(latitude);
        double hourRad = Math.toRadians(hourAngle);
        
        double cosZenith = Math.sin(latRad) * Math.sin(decRad) + 
                          Math.cos(latRad) * Math.cos(decRad) * Math.cos(hourRad);
        return Math.toDegrees(Math.acos(cosZenith));
    }
    

    public static double calculateAzimuthAngle(double declination, double latitude, 
                                              double hourAngle, double zenithAngle) {
        double decRad = Math.toRadians(declination);
        double latRad = Math.toRadians(latitude);
        double zenRad = Math.toRadians(zenithAngle);
        
        double cosAzimuth = (Math.sin(decRad) * Math.cos(latRad) - 
                           Math.cos(decRad) * Math.sin(latRad) * Math.cos(Math.toRadians(hourAngle))) / 
                           Math.sin(zenRad);
        
        double azimuth = Math.toDegrees(Math.acos(cosAzimuth));
        
        // Adjust for hemisphere
        if (hourAngle > 0) {
            azimuth = 360 - azimuth;
        }
        
        return azimuth;
    }
    

    public static double calculateDayLength(double declination, double latitude) {
        double decRad = Math.toRadians(declination);
        double latRad = Math.toRadians(latitude);
        
        // Hour angle at sunrise/sunset
        double ha = Math.acos(-Math.tan(latRad) * Math.tan(decRad));
        
        // Convert to hours (divide by 15 degrees per hour)
        return 2 * Math.toDegrees(ha) / 15;
    }
}