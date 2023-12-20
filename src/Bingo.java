import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Bingo extends JFrame implements ActionListener {

    /**
     * Set the following configuration variables
     */

    final String ABSOLUTE_PATH_TO_DIR_IMG = "C:\\Users\\Usuario\\Desktop\\bingo2\\img";

    // Imagen con números:
    final String NUMBERS_IMAGE_NAME = "tableroNumeros.png";
    final String NUMBERS_IMAGE_PATH = ABSOLUTE_PATH_TO_DIR_IMG + "\\" + NUMBERS_IMAGE_NAME;
    ImageIcon numbersImage = new ImageIcon(NUMBERS_IMAGE_PATH);
    JLabel numbersImageLabel = new JLabel(numbersImage);

    // Imagen bingoIESVDA:
    final String IMAGE_BINGO_IESVDA = "bingoIESVDA.png";
    final String IMAGE_BINGO_IESVDA_PATH = ABSOLUTE_PATH_TO_DIR_IMG + "\\" + IMAGE_BINGO_IESVDA;
    ImageIcon bingoIESVSAImage = new ImageIcon(IMAGE_BINGO_IESVDA_PATH);
    JLabel bingoIESVSAImageContainer = new JLabel(bingoIESVSAImage);

    // Imagen cartón vacío:
    final String IMAGEN_CARTON_VACIO = "cartonBingoEnBlanco.png";
    final String IMAGEN_CARTON_VACIO_PATH = ABSOLUTE_PATH_TO_DIR_IMG + "\\" + IMAGEN_CARTON_VACIO;
    ImageIcon imagenCartonVacio = new ImageIcon(IMAGEN_CARTON_VACIO_PATH);
    JLabel imagenCartonVacioContainer = new JLabel(imagenCartonVacio);

    JPanel jpanel = (JPanel) this.getContentPane();


    // Botón sacar número:
    JButton button = new JButton("Sacar número");

    // Botón colocar números:
    JButton botonColocar = new JButton("Colocar números");

    // Botón restablecer:
    JButton botonRestablecer = new JButton("Restablecer");

    // Array de doble dimensión para guardar las bolas extraídas para la fila de bolas:
    JLabel[][] imagesBallsArray = new JLabel[3][9];

    // Array de doble dimensión para presentar las bolas en el cartón:
    JLabel[][] imagesBallsInCartonArray = new JLabel[3][9];
    int[][][] coordenadasBolasCarton = {
            {{26, 40}, {}, {149,40}, {}, {273,40}, {336, 40}, {}, {}, {522, 40}},
            {{26, 122}, {87, 122},{}, {211, 122}, {}, {336, 122}, {}, {459, 122}, {}},
            {{}, {87, 204}, {149, 204}, {}, {273, 204}, {}, {397,204}, {459,204}, {}}
    };

    int extractions = 1;

    public Bingo(){
        setSize(826,750);
        setTitle("Bingooooooooo !!!");
        setResizable(true);
        setLocation(25, 25);

        // Disposición nula para control total de panel
        jpanel.setLayout(null);

        Color color = Color.decode("#FFC482");
        jpanel.setBackground(color);

        bingoPanelBuilder();
        turnBomb();
        setVisible(true);

    }

    public void bingoPanelBuilder(){

        // Imagen con los números
        numbersImageLabel.setBounds(477, 19, 300, 249);
        jpanel.add(numbersImageLabel);

        // Imagen bingoIESVDA:
        bingoIESVSAImageContainer.setBounds(49, 13, 300, 249);
        jpanel.add(bingoIESVSAImageContainer);

        // Imagen cartón vacío:
        imagenCartonVacioContainer.setBounds(113, 260,600, 297);
        jpanel.add(imagenCartonVacioContainer);

        // Botón sacar número:
        button.addActionListener(this);
        button.setBounds(105, 615, 200, 25);
        jpanel.add(button);

        // Botón colocar números:
        botonColocar.addActionListener(this);
        botonColocar.setBounds(315, 615, 200, 25);
        jpanel.add(botonColocar);

        // Botón restablecer:
        botonRestablecer.addActionListener(this);
        botonRestablecer.setBounds(525, 615, 200, 25);
        jpanel.add(botonRestablecer);

    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource()==button){
            turnBomb();
        }
    }

    public void turnBomb(){

        if(extractions > 14){
            button.setEnabled(false);
        }

        int min = 1;
        int max = 10;
        int columnaArrayBolas = 0;
        int ballImageXPosition = 0;

        for(int i = 0; i < 9; i++){
            ballImageXPosition = seleccionarBolas(min, max, columnaArrayBolas, ballImageXPosition);
            min += 10;
            max += 10;
            columnaArrayBolas++;

        }
        ++extractions;
    }

    public int seleccionarBolas (int min, int max, int columna, int ballImageXPosition) {

        String actuallyBalls = "";
        boolean repeated;
        int ball;
        int j = 0;
        String ballStr;

        if(columna == 3 || columna == 6 || columna == 8 ){
            j = 1;
        }

        for(int i = j; i < 2; i++) {
            do {
                ball = min + (int) (Math.random() * (max - min + 1));
                ballStr = ball + ".png";
                repeated = actuallyBalls.contains(ballStr);
            } while (repeated && ball >= min && ball <= max);

            actuallyBalls += ballStr + " ";

            ImageIcon icon = new ImageIcon(ABSOLUTE_PATH_TO_DIR_IMG + "\\" + ballStr);

            /// Array de fila de bolas:
            imagesBallsArray[i][columna] = new JLabel(icon);
            imagesBallsArray[i][columna].setVisible(true);
            imagesBallsArray[i][columna].setBounds(ballImageXPosition, 547, 55, 55);
            jpanel.add(imagesBallsArray[i][columna]);
            ballImageXPosition += 55;

            // Array de bolas en cartón:
            imagesBallsInCartonArray[i][columna] = new JLabel(icon);
            imagesBallsInCartonArray[i][columna].setVisible(true);
            imagesBallsInCartonArray[i][columna].
                    setBounds(coordenadasBolasCarton[i][columna][0], coordenadasBolasCarton[i][columna][1],
                            //55, 55);
            jpanel.add(imagesBallsInCartonArray[i][columna]);
        }

        return  ballImageXPosition;

    }

    public static void main(String[] args) {
        new Bingo();
    }

}
