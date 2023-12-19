import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Bingo extends JFrame implements ActionListener {

    /**
     * Set the following configuration variables
     */

    final String ABSOLUTE_PATH_TO_DIR_IMG = "C:\\Users\\Usuario\\Desktop\\bingo\\bingo\\img";
    final String IMAGE_NAME = "numerosBingo.jpg";

    // Icono
    final String IMAGE_PATH = ABSOLUTE_PATH_TO_DIR_IMG + "\\" + IMAGE_NAME;
    ImageIcon icon = new ImageIcon(IMAGE_PATH);
    JLabel iconLabel = new JLabel(icon);

    JPanel jpanel = (JPanel) this.getContentPane();
    JTextField bingoNumbers = new JTextField();
    JLabel numbersLabel = new JLabel();
    JButton button = new JButton("Vai bola");
    JLabel bingoFinishedLabel = new JLabel();
    JLabel[] imagesBallsArray = new JLabel[20];
    int extractions = 1;
    int ballImageXPosition = 25;

    public Bingo(){
        setSize(900,900);
        setTitle("Bingooooooooo !!!");
        setResizable(true);
        setLocation(250, 15);

        // Disposición nula para control total de panel
        jpanel.setLayout(null);

        Color color = Color.decode("#FFC482");
        jpanel.setBackground(color);

        bingoPanelBuilder();
        setVisible(true);

    }

    public void bingoPanelBuilder(){

        Font font = new Font("Arial", Font.PLAIN, 24);

        jpanel.add(iconLabel);
        iconLabel.setBounds(50, 25, 762, 610);

        numbersLabel.setText("Bolas extraídas: ");
        numbersLabel.setBounds(100, 650, 200, 30);
        numbersLabel.setFont(font);
        jpanel.add(numbersLabel);

        bingoNumbers.setBounds(new Rectangle(100, 690,675, 30));
        bingoNumbers.setEditable(false);
        bingoNumbers.setHorizontalAlignment(JTextField.CENTER);
        bingoNumbers.setFont(font);
        jpanel.add(bingoNumbers);

        button.addActionListener(this);
        button.setBounds(375, 740, 150, 100);
        jpanel.add(button);

        bingoFinishedLabel.setText("Bingo finalizado");
        Font finishedLabelFont = new Font("Arial", Font.BOLD, 32);
        bingoFinishedLabel.setFont(finishedLabelFont);
        bingoFinishedLabel.setVisible(false);
        bingoFinishedLabel.setForeground(Color.RED);
        bingoFinishedLabel.setBounds(100, 750, 325, 40);
        jpanel.add(bingoFinishedLabel);

    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource()==button){
            turnBomb();
        }
    }

    private void turnBomb(){
        System.out.println("extractions = " + extractions);
        if(extractions > 19){
            button.setEnabled(false);
            bingoFinishedLabel.setVisible(true);
        }

        boolean repeated;
        String actuallyBalls = bingoNumbers.getText();
        int ball;

        do {
            ball = 1 + (int) (Math.random() * 90);
            repeated = actuallyBalls.contains(String.valueOf(ball));
        } while(repeated);
        String ballStr = ball + ".png";

        // ball images
        ImageIcon icon = new ImageIcon(ABSOLUTE_PATH_TO_DIR_IMG+ballStr);
        imagesBallsArray[extractions] = new JLabel(icon);

        numbersLabel.setText("Bolas extraídas: ");
        imagesBallsArray[extractions].setBounds(ballImageXPosition, 250, 55, 55);
        jpanel.add(imagesBallsArray[extractions]);
        ballImageXPosition += 58;

        ++extractions;
        bingoNumbers.setText(actuallyBalls + " " + ball);

    }

    public static void main(String[] args) {
        new Bingo();
    }

}
