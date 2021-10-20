import javax.swing.*;
import java.awt.*;

public class MainFrame extends JFrame {

    MainFrame() {
        this.setTitle("Montanha Russa");
        this.setMinimumSize(new Dimension(400, 400));
        this.setLocationRelativeTo(null);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setVisible(true);
    }
}
