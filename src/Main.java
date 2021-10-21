import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class Main {

    private static Wagon wagon;
    private static ArrayList<Passenger> passengers = new ArrayList<>();

    public static void main(String[] args) {
        // Colors
        Color mintGreen = new Color(0x5BBA81);
        Color darkGray = new Color(0x585858);
        Color skyBlue = new Color(0xA5D9E4);
        // Labels
        JLabel inspectorTitleLabel = new JLabel("Inspetor");
        inspectorTitleLabel.setForeground(Color.white);
        JLabel consoleTitleLabel = new JLabel("Console");
        consoleTitleLabel.setForeground(Color.white);
        // Buttons
        JButton addWagonButton = new JButton("Adicionar Vagão");
        addWagonButton.setBackground(mintGreen);
        addWagonButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JTextField chairCountField = new JTextField();
                JTextField transitDurationField = new JTextField();
                Object[] message = {
                        "Quantidade de cadeiras", chairCountField,
                        "Tempo de viagem", transitDurationField
                };
                int option = JOptionPane.showConfirmDialog(null, message, "Novo Vagão", JOptionPane.OK_CANCEL_OPTION);
                if (option == JOptionPane.OK_OPTION) {
                    try {
                        int chairCount = Integer.parseInt(chairCountField.getText());
                        float transitDuration = Float.parseFloat(transitDurationField.getText());
                        wagon = new Wagon(chairCount, transitDuration);
                    } catch (NumberFormatException nfe) {
                        String errorMessage = "A quantidade de cadeiras e o tempo de viagem devem ser números.";
                        JOptionPane.showMessageDialog(null, errorMessage, "Entrada Inválida", JOptionPane.ERROR_MESSAGE);
                    }
                }
            }
        });
        JButton addPassengerButton = new JButton("Adicionar Passageiro");
        addPassengerButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JTextField boardingDurationField = new JTextField();
                JTextField landingDurationField = new JTextField();
                Object[] message = {
                        "Tempo de embarque", boardingDurationField,
                        "Tempo de desembarque", landingDurationField
                };
                int option = JOptionPane.showConfirmDialog(null, message, "Novo Passageiro", JOptionPane.OK_CANCEL_OPTION);
                if (option == JOptionPane.OK_OPTION) {
                    try {
                        float boardingDuration = Float.parseFloat(boardingDurationField.getText());
                        float landingDuration = Float.parseFloat(landingDurationField.getText());
                        passengers.add(new Passenger(boardingDuration, landingDuration));
                    } catch (NumberFormatException nfe) {
                        String errorMessage = "O tempo de embarque e de desembarque devem ser números.";
                        JOptionPane.showMessageDialog(null, errorMessage, "Entrada Inválida", JOptionPane.ERROR_MESSAGE);
                    }
                }
            }
        });
        addPassengerButton.setBackground(mintGreen);
        JButton pauseSimulationButton = new JButton("Pausar Simulação");
        pauseSimulationButton.setBackground(mintGreen);
        JButton endSimulationButton = new JButton("Encerrar Simulação");
        endSimulationButton.setBackground(mintGreen);
        // Top Panel
        JPanel inspectorPanel = new JPanel();
        inspectorPanel.setBackground(darkGray);
        inspectorPanel.add(inspectorTitleLabel);
        JPanel simulationPanel = new JPanel();
        simulationPanel.setBackground(skyBlue);
        JPanel topPanel = new JPanel();
        topPanel.setLayout(new BoxLayout(topPanel, BoxLayout.X_AXIS));
        topPanel.add(inspectorPanel);
        topPanel.add(simulationPanel);
        // Bottom Panel
        JPanel consolePanel = new JPanel();
        consolePanel.setBackground(darkGray);
        consolePanel.add(consoleTitleLabel);
        JPanel buttonsGrid = new JPanel(new GridLayout(2, 2, 50, 50));
        buttonsGrid.setBackground(skyBlue);
        buttonsGrid.add(addWagonButton);
        buttonsGrid.add(addPassengerButton);
        buttonsGrid.add(pauseSimulationButton);
        buttonsGrid.add(endSimulationButton);
        JPanel buttonsPanel = new JPanel(new BorderLayout());
        buttonsPanel.add(buttonsGrid);
        JPanel bottomPanel = new JPanel();
        bottomPanel.setLayout(new BoxLayout(bottomPanel, BoxLayout.X_AXIS));
        bottomPanel.add(consolePanel);
        bottomPanel.add(buttonsPanel);
        // Frame
        JFrame frame = new JFrame();
        frame.setTitle("Simulação de Montanha Russa");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new BoxLayout(frame.getContentPane(), BoxLayout.Y_AXIS));
        frame.add(topPanel);
        frame.add(bottomPanel);
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }
}
