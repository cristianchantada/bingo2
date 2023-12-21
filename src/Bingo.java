
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Arrays;

public class Bingo extends JFrame implements ActionListener {

    /**
     * Set the following configuration variables
     */

    final String ABSOLUTE_PATH_TO_DIR_IMG = "C:\\DAW\\Programación\\Java2Evaluacion\\bingoVersion2\\img";

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
            {{26, 40}, {0,0}, {149,40}, {0,0}, {273,40}, {336, 40}, {0,0}, {0,0}, {522, 40}},
            {{26, 122}, {87, 122},{0,0}, {211, 122}, {0,0}, {336, 122}, {0,0}, {459, 122}, {0,0}},
            {{0,0}, {87, 204}, {149, 204}, {0,0}, {273, 204}, {0,0}, {397,204}, {459,204}, {0,0}}
    };

    int extractions = 1;
    int auxI;
    int auxColumna;
    int auxBall;

    public Bingo(){
        setSize(842,750);
        setTitle("Bingooooooooo !!!");
        setResizable(true);
        setLocation(25, 25);

        int[] num = coordenadasBolasCarton[0][1];

        System.out.println(num);

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
        numbersImageLabel.setBounds(477, 15, 298, 247);
        jpanel.add(numbersImageLabel);

        // Imagen bingoIESVDA:
        bingoIESVSAImageContainer.setBounds(49, 12, 300, 249);
        jpanel.add(bingoIESVSAImageContainer);

        // Imagen cartón vacío:
        imagenCartonVacioContainer.setBounds(113, 263,600, 297);
        jpanel.add(imagenCartonVacioContainer);
        jpanel.setComponentZOrder(imagenCartonVacioContainer, 1);

        // Botón sacar número:
        button.addActionListener(this);
        button.setBounds(105, 622, 200, 25);
        jpanel.add(button);

        // Botón colocar números:
        botonColocar.addActionListener(this);
        botonColocar.setBounds(315, 622, 200, 25);
        botonColocar.setEnabled(false);
        jpanel.add(botonColocar);

        // Botón restablecer:
        botonRestablecer.addActionListener(this);
        botonRestablecer.setBounds(525, 622, 200, 25);
        jpanel.add(botonRestablecer);

    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource()==button){

            if(extractions > 15){
                button.setEnabled(false);
                botonColocar.setEnabled(true);
            }

            externalLoop:
            for(int j = 0; j < 9; j++){
                for(int i = 0; i < 3; i++){
                    if(imagesBallsArray[i][j] != null){
                        if(!imagesBallsArray[i][j].isVisible()) {
                            imagesBallsArray[i][j].setVisible(true);
                            extractions++;
                            break externalLoop;
                        } else {
                            continue;
                        }
                    }

                }
            }

        } else if (e.getSource()== botonColocar){

            botonColocar.setEnabled(false);
            for(int j = 0; j < 9; j++){
                for(int i = 0; i < 3; i++){
                    if(imagesBallsInCartonArray[i][j] != null){
                        if(!imagesBallsInCartonArray[i][j].isVisible()) {
                            imagesBallsInCartonArray[i][j].setVisible(true);
                        } else {
                            continue;
                        }
                    }
                }
            }

        } else if (e.getSource()== botonRestablecer){
            dispose();
            new Bingo();
        }
    }

    public void turnBomb(){

        System.out.println("extractions = " + extractions);

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

        for(int i = j; i < 3; i++) {
            if(coordenadasBolasCarton[i][columna][0] != 0 && coordenadasBolasCarton[i][columna][1] != 0) {
                do {
                    ball = min + (int) (Math.random() * (max - min + 1));
                    ballStr = ball + ".png";
                    repeated = actuallyBalls.contains(ballStr);
                } while (repeated && ball >= min && ball <= max);

                if(auxBall > ball ){
                    imagesBallsArray[auxI][auxColumna].setBounds(ballImageXPosition, 563, 55, 55);

                    imagesBallsInCartonArray[auxI][auxColumna].setBounds(
                            coordenadasBolasCarton[i][columna][0]+113, coordenadasBolasCarton[i][columna][1]+260,
                            55, 55
                    );

                    ImageIcon icon = new ImageIcon(ABSOLUTE_PATH_TO_DIR_IMG + "\\" + ballStr);

                    imagesBallsArray[i][columna] = new JLabel(icon);
                    jpanel.add(imagesBallsArray[i][columna]);
                    ballImageXPosition += 55;
                    imagesBallsArray[i][columna].setVisible(false);

                    imagesBallsInCartonArray[i][columna] = new JLabel(icon);
                    imagesBallsArray[i][columna].setBounds(ballImageXPosition -55, 563, 55, 55);
                    imagesBallsInCartonArray[i][columna].setBounds(
                            coordenadasBolasCarton[auxI][auxColumna][0]+113, coordenadasBolasCarton[auxI][auxColumna][1]+260,
                            55, 55
                    );

                    jpanel.add(imagesBallsInCartonArray[i][columna]);
                    jpanel.setComponentZOrder(imagesBallsInCartonArray[i][columna], 0);
                    imagesBallsInCartonArray[i][columna].setVisible(false);
                    break;
                }

                auxI = i;
                auxColumna = columna;
                auxBall = ball;


                actuallyBalls += ballStr + " ";

                ImageIcon icon = new ImageIcon(ABSOLUTE_PATH_TO_DIR_IMG + "\\" + ballStr);

                /// Array de fila de bolas:
                imagesBallsArray[i][columna] = new JLabel(icon);
                imagesBallsArray[i][columna].setBounds(ballImageXPosition, 563, 55, 55);
                jpanel.add(imagesBallsArray[i][columna]);
                ballImageXPosition += 55;
                imagesBallsArray[i][columna].setVisible(false);

                // Array de bolas en cartón:
                imagesBallsInCartonArray[i][columna] = new JLabel(icon);
                imagesBallsInCartonArray[i][columna].setBounds(
                        coordenadasBolasCarton[i][columna][0]+113, coordenadasBolasCarton[i][columna][1]+260,
                                55, 55
                );

                jpanel.add(imagesBallsInCartonArray[i][columna]);
                jpanel.setComponentZOrder(imagesBallsInCartonArray[i][columna], 0);
                imagesBallsInCartonArray[i][columna].setVisible(false);
            }
        }

        return  ballImageXPosition;

    }

    public static void main(String[] args) {
        new Bingo();
    }

}
