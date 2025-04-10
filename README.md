<h1 align="center">🌞 Solar Positioning Simulation</h1>

<p align="center">
  <a href="https://www.java.com"><img alt="Java" src="https://img.shields.io/badge/Java-23-blue.svg"></a>
  <a href="LICENSE"><img alt="License" src="https://img.shields.io/badge/License-MIT-green.svg"></a>
  <img alt="Build Status" src="https://img.shields.io/badge/Build-Passing-brightgreen.svg">
  <img alt="Version" src="https://img.shields.io/badge/Version-1.0.0-orange.svg">
</p>

<p align="center">
  A <strong>Java-based interactive simulation</strong> that visualizes the sun's position relative to Earth at various times of the year, latitudes, and times of day. This project combines <strong>astronomical concepts</strong> with <strong>educational interactivity</strong> for an engaging user experience.
</p>


<h2>📸 Screenshots</h2>
<p align="center">
  <img src="image.png" alt="Solar Positioning Simulation">
</p>


<h2>🎥 Demo Video</h2>
<p align="center">
  <a href="video.mp4"><img alt="Watch the Demo" src="https://img.shields.io/badge/Video-Demo-red"></a>
</p>


<h2> Features</h2>

<h3>🌍 Interactive Solar Position Visualization</h3>
<ul>
  <li><strong>Real-time Sun Position Calculation</strong> based on time, latitude, and day of the year.</li>
  <li><strong>Adjustable Parameters</strong>: Day, latitude, and time.</li>
  <li><strong>Smooth Animation Controls</strong> for enhanced simulation.</li>
</ul>

<h3>🌌 Astronomical Events</h3>
<ul>
  <li>Simulate significant astronomical phenomena:</li>
  <ul>
    <li><strong>Spring Equinox</strong></li>
    <li><strong>Summer Solstice</strong></li>
    <li><strong>Fall Equinox</strong></li>
    <li><strong>Winter Solstice</strong></li>
  </ul>
</ul>

<h3> Educational Information</h3>
<ul>
  <li><strong>Scientific Notes</strong> to explain concepts.</li>
  <li><strong>Real-time Updates</strong> for interactive learning.</li>
  <li><strong>Astronomical Concept Integrations</strong> for deeper understanding.</li>
</ul>


<h2> Getting Started</h2>

<h3>Prerequisites</h3>
<p>Ensure you have the following installed:</p>
<pre>
<code>
- Java 23 or higher
- Git (optional)
	- Gitlens
</code>
</pre>

<h3>Installation</h3>
<ol>
  <li><strong>Clone the Repository</strong></li>
  <pre>
  <code>
  git clone https://github.com/Arknight007/Solar-Positioning-Equinox-Simulator-.git
  cd Solar-Positioning-Equinox-Simulator-
  </code>
  </pre>

  <li><strong>Build</strong></li>
  <pre>
  <code>
  # Compile
  javac src/*.java
  
  <li><strong>Run</strong></li>
  java -cp src MainFrame
  </code>
  </pre>
</ol>

---

<h2>📂 Project Structure</h2>

<pre>
<code>
Solar-Positioning-Simulation/
├── src/
│   ├── MainFrame.java          # Main application window
│   ├── SimulationPanel.java    # 2D simulation and calculations
│   ├── ControlPanel.java       # Parameter adjustments
│   ├── ControlPanelSetup.java  # UI setup and event handling
│   ├── InfoPanel.java          # Info display and updates
│   ├── CustomSlider.java       # Sliders for parameter control
│   └── SpecialDayButton.java   # Buttons for astronomical events
└── README.md                   # Documentation
</code>
</pre>

---

<h2>🧩 Components Overview</h2>

<h3>MainFrame</h3>
<ul>
  <li><strong>Role</strong>: Acts as the primary application window.</li>
  <li><strong>Responsibilities</strong>: Component coordination, layout management.</li>
</ul>

<h3>SimulationPanel</h3>
<ul>
  <li><strong>Role</strong>: Handles solar position calculations and 2D visualization.</li>
  <li><strong>Features</strong>: Real-time updates, smooth animations.</li>
</ul>

<h3>ControlPanel</h3>
<ul>
  <li><strong>Role</strong>: Provides user controls for adjusting parameters.</li>
  <li><strong>Features</strong>: Animation speed, event selection, time controls.</li>
</ul>

<h3>ControlPanelSetup</h3>
<ul>
  <li><strong>Role</strong>: Initializes UI components and event handling.</li>
  <li><strong>Features</strong>: Layout configuration, interactive setup.</li>
</ul>

<h3>InfoPanel</h3>
<ul>
  <li><strong>Role</strong>: Displays scientific data and explanations.</li>
  <li><strong>Features</strong>: Real-time updates, concept clarifications.</li>
</ul>

<h3>CustomSlider</h3>
<ul>
  <li><strong>Role</strong>: Interactive sliders for parameter adjustments.</li>
  <li><strong>Features</strong>: Smooth interaction, range-specific labels.</li>
</ul>

<h3>SpecialDayButton</h3>
<ul>
  <li><strong>Role</strong>: Quick access to astronomical events.</li>
  <li><strong>Features</strong>: Event-specific styling, state updates.</li>
</ul>

---

<h2>🔧 Usage Instructions</h2>

<h3>1. Adjust Parameters</h3>
<ul>
  <li>Use sliders to modify:</li>
  <ul>
    <li><strong>Day of the Year</strong></li>
    <li><strong>Latitude</strong></li>
    <li><strong>Time of Day</strong></li>
  </ul>
</ul>

<h3>2. Simulate Special Events</h3>
<ul>
  <li>Click buttons to:</li>
  <ul>
    <li><strong>Spring Equinox</strong></li>
    <li><strong>Summer Solstice</strong></li>
    <li><strong>Fall Equinox</strong></li>
    <li><strong>Winter Solstice</strong></li>
  </ul>
</ul>

<h3>3. Control Animation</h3>
<ul>
  <li><strong>Start/Stop Simulation</strong></li>
  <li><strong>Adjust Animation Speed</strong></li>
  <li>Observe real-time solar positioning changes.</li>
</ul>

---

<h2>📊 Technical Details</h2>

<h3>Solar Position Calculation</h3>
<pre>
<code>
// Example calculation
double declination = calculateDeclination(dayOfYear);
double hourAngle = calculateHourAngle(timeOfDay);
double elevation = calculateElevation(declination, hourAngle, latitude);
</code>
</pre>

<h3>Animation System</h3>
<pre>
<code>
// Animation timer setup
Timer animationTimer = new Timer(50, e -> {
    updateSimulation();
    repaint();
});
</code>
</pre>

---

<h2>🤝 Contributing</h2>
<p>We welcome contributions from the community! To get started:</p>
<ol>
  <li><strong>Fork the Repository</strong>.</li>
  <li><strong>Create a Feature Branch</strong>:</li>
  <pre>
  <code>
  git checkout -b feature/AmazingFeature
  </code>
  </pre>
  <li><strong>Commit Your Changes</strong>:</li>
  <pre>
  <code>
  git commit -m 'Add AmazingFeature'
  </code>
  </pre>
  <li><strong>Push the Branch</strong>:</li>
  <pre>
  <code>
  git push origin feature/AmazingFeature
  </code>
  </pre>
  <li><strong>Open a Pull Request</strong>.</li>
</ol>

---

<h2>📝 License</h2>
<p>This project is licensed under the MIT License. See the <a href="LICENSE">LICENSE</a> file for more details.</p>

---

<h2>🙏 Acknowledgments</h2>
<ul>
  <li><strong>Astronomical Formulas</strong>: Based on standard and verified calculations.</li>
  <li><strong>UI Design</strong>: Inspired by modern scientific applications.</li>
  <li><strong>Contributors and Testers</strong>: Special thanks for your support and feedback.</li>
</ul>

---

<h2>📧 Contact</h2>
<ul>
  <li><strong>Author</strong>: Arknight007</li>
  <li><strong>Email</strong>: <a href="mailto:knightark007@gmail.com">email@example.com</a></li>
  <li><strong>Project Link</strong>: <a href="https://github.com/Arknight007/Solar-Positioning-Equinox-Simulator-">Solar Positioning Simulation</a></li>
</ul>

---

<h2 align="center">🌟 Star the Project</h2>
<p align="center">If you find this project useful or interesting, don't forget to <strong>star</strong> the repository!</p>
