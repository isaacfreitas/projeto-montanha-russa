import javax.swing.*;
import java.awt.*;
import java.awt.event.ItemEvent;
import java.util.ArrayList;

public class Main {

    private static int passengerIdCount = 1;
    private static Wagon wagon;
    private static ArrayList<Passenger> passengers = new ArrayList<>();

    public static void main(String[] args) {
        // Colors
        final Color mintGreen = new Color(0x5BBA81);
        final Color darkGray = new Color(0x585858);
        final Color skyBlue = new Color(0xA5D9E4);
        // Labels
        JLabel inspectorTitleLabel = new JLabel("Inspetor");
        inspectorTitleLabel.setForeground(Color.white);
        inspectorTitleLabel.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 100));
        inspectorTitleLabel.setFont(new Font("SansSerif", Font.PLAIN, 28));
        JLabel firstAttributeNameLabel = new JLabel(" ");
        firstAttributeNameLabel.setForeground(Color.white);
        JLabel firstAttributeValueLabel = new JLabel(" ");
        firstAttributeValueLabel.setForeground(Color.white);
        firstAttributeValueLabel.setFont(new Font("SansSerif", Font.PLAIN, 18));
        JLabel secondAttributeNameLabel = new JLabel(" ");
        secondAttributeNameLabel.setForeground(Color.white);
        JLabel secondAttributeValueLabel = new JLabel(" ");
        secondAttributeValueLabel.setForeground(Color.white);
        secondAttributeValueLabel.setFont(new Font("SansSerif", Font.PLAIN, 18));
        JLabel stateNameLabel = new JLabel(" ");
        stateNameLabel.setForeground(Color.white);
        JLabel stateValueLabel = new JLabel(" ");
        stateValueLabel.setForeground(Color.white);
        stateValueLabel.setFont(new Font("SansSerif", Font.PLAIN, 18));
        JLabel consoleTitleLabel = new JLabel("Console");
        consoleTitleLabel.setForeground(Color.white);
        consoleTitleLabel.setBorder(BorderFactory.createEmptyBorder(0, 50, 0, 20));
        consoleTitleLabel.setFont(new Font("SansSerif", Font.PLAIN, 28));
        // Misc
        JComboBox inspectorComboBox = new JComboBox() {
            /**
             * @inherited <p>
             */
            @Override
            public Dimension getMaximumSize() {
                Dimension max = super.getMaximumSize();
                max.width = getPreferredSize().width;
                return max;
            }
        };
        inspectorComboBox.setPrototypeDisplayValue("Passageiro XX");
        inspectorComboBox.setAlignmentX(Component.CENTER_ALIGNMENT);
        inspectorComboBox.addItemListener(e -> {
            if (e.getStateChange() == ItemEvent.SELECTED) {
                Object selectedItem = inspectorComboBox.getSelectedItem();
                if (selectedItem instanceof Wagon) {
                    firstAttributeNameLabel.setText("Quantidade de cadeiras");
                    firstAttributeValueLabel.setText(Integer.toString(((Wagon) selectedItem).chairCount));
                    secondAttributeNameLabel.setText("Tempo de viagem");
                    secondAttributeValueLabel.setText(Float.toString(((Wagon) selectedItem).transitDuration));
                    stateNameLabel.setText("Estado");
                    stateValueLabel.setText(((Wagon) selectedItem).state.toString());
                } else if (selectedItem instanceof Passenger) {
                    firstAttributeNameLabel.setText("Tempo de embarque");
                    firstAttributeValueLabel.setText(Float.toString(((Passenger) selectedItem).boardingDuration));
                    secondAttributeNameLabel.setText("Tempo de desembarque");
                    secondAttributeValueLabel.setText(Float.toString(((Passenger) selectedItem).landingDuration));
                    stateNameLabel.setText("Estado");
                    stateValueLabel.setText(((Passenger) selectedItem).state.toString());
                }
            }
        });
        JTextArea consoleTextArea = new JTextArea("Passageiro 1 desembarcou do vagão");
        consoleTextArea.setBackground(darkGray);
        consoleTextArea.setForeground(Color.white);
        consoleTextArea.setEditable(false);
        JScrollPane consoleScrollPane = new JScrollPane(consoleTextArea);
        consoleScrollPane.setBorder(null);
        // Buttons
        JButton addWagonButton = new JButton("Adicionar Vagão");
        addWagonButton.setBackground(mintGreen);
        JButton addPassengerButton = new JButton("Adicionar Passageiro");
        addPassengerButton.setBackground(mintGreen);
        addPassengerButton.setEnabled(false);
        JButton pauseSimulationButton = new JButton("Pausar Simulação");
        pauseSimulationButton.setBackground(mintGreen);
        pauseSimulationButton.setEnabled(false);
        JButton endSimulationButton = new JButton("Encerrar Simulação");
        endSimulationButton.setBackground(mintGreen);
        endSimulationButton.setEnabled(false);
        addWagonButton.addActionListener(e -> {
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
                    inspectorComboBox.addItem(wagon);
                    addWagonButton.setEnabled(false);
                    addPassengerButton.setEnabled(true);
                } catch (NumberFormatException nfe) {
                    String errorMessage = "A quantidade de cadeiras e o tempo de viagem devem ser números.";
                    JOptionPane.showMessageDialog(null, errorMessage, "Entrada Inválida", JOptionPane.ERROR_MESSAGE);
                }
            }
        });
        addPassengerButton.addActionListener(e -> {
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
                    Passenger passenger = new Passenger(passengerIdCount, boardingDuration, landingDuration);
                    passengers.add(passenger);
                    inspectorComboBox.addItem(passenger);
                    passengerIdCount++;
                } catch (NumberFormatException nfe) {
                    String errorMessage = "O tempo de embarque e de desembarque devem ser números.";
                    JOptionPane.showMessageDialog(null, errorMessage, "Entrada Inválida", JOptionPane.ERROR_MESSAGE);
                }
            }
        });
        // Top Panel
        JPanel inspectorPanel = new JPanel();
        inspectorPanel.setBackground(darkGray);
        inspectorPanel.setLayout(new BoxLayout(inspectorPanel, BoxLayout.Y_AXIS));
        inspectorPanel.add(Box.createRigidArea(new Dimension(0, 5)));
        inspectorPanel.add(inspectorTitleLabel);
        inspectorPanel.add(Box.createRigidArea(new Dimension(0, 5)));
        inspectorPanel.add(inspectorComboBox);
        inspectorPanel.add(Box.createRigidArea(new Dimension(0, 5)));
        inspectorPanel.add(firstAttributeNameLabel);
        inspectorPanel.add(firstAttributeValueLabel);
        inspectorPanel.add(Box.createRigidArea(new Dimension(0, 5)));
        inspectorPanel.add(secondAttributeNameLabel);
        inspectorPanel.add(secondAttributeValueLabel);
        inspectorPanel.add(Box.createRigidArea(new Dimension(0, 5)));
        inspectorPanel.add(stateNameLabel);
        inspectorPanel.add(stateValueLabel);
        inspectorPanel.add(Box.createRigidArea(new Dimension(0, 20)));
        JPanel simulationPanel = new JPanel();
        simulationPanel.setBackground(skyBlue);
        JPanel topPanel = new JPanel();
        topPanel.setLayout(new BoxLayout(topPanel, BoxLayout.X_AXIS));
        topPanel.add(inspectorPanel);
        topPanel.add(simulationPanel);
        // Bottom Panel
        JPanel consolePanel = new JPanel();
        consolePanel.setBorder(BorderFactory.createEmptyBorder(0, 10, 10, 10));
        consolePanel.setBackground(darkGray);
        consolePanel.setLayout(new BoxLayout(consolePanel, BoxLayout.Y_AXIS));
        consolePanel.add(consoleTitleLabel);
        consolePanel.add(consoleScrollPane);
        JPanel buttonsPanel = new JPanel(new GridLayout(2, 2, 50, 50));
        buttonsPanel.setBackground(skyBlue);
        buttonsPanel.setBorder(BorderFactory.createEmptyBorder(50, 50, 50, 50));
        buttonsPanel.add(addWagonButton);
        buttonsPanel.add(addPassengerButton);
        buttonsPanel.add(pauseSimulationButton);
        buttonsPanel.add(endSimulationButton);
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
        consoleTextArea.setText(null);
        frame.setResizable(false);
        frame.setVisible(true);
    }
}
