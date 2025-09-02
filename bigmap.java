import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.border.Border;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Random;
import java.util.List;
import java.util.Collections;


public class bigmap {

    // her er alt hvad du behøver at mudelere tseskjhgghgjjgjhgh
    private static final int Height = 30;
    private static final int Width = 98;
    private static int antalPits = 300; // sæt mængden af pits
    private static double mapSizeW = 0.99; // Example: 80% of screen width 0,42
    private static double mapSizeH = 0.86; // Example: 70% of screen height 0,6

    private static char[][] bane = new char[Height][Width];
    private static int tX = 0; // Startposition for 't' i X-aksen
    private static int tY = 0; // Startposition for 't' i Y-aksen
    private static char banetegn = '▢';
    private static char spiller = '▣';
    private static char pit = '▨'; 
    private static char pathicon = '⬚';
    // private static char slut = "⛾";

    public static void main(String[] args) {
        // Initialiser banen
        initialiserBane();
          
        JFrame frame = gameWindow();
        JPanel buttonPanel = buttonPanel();
        JButton højre = new JButton(" -> ");
        JButton venstre = new JButton(" <- ");
        JButton ned = new JButton(" \\/ ");
        JButton op = new JButton(" /\\ "); 

        buttonPanel.add(venstre);
        buttonPanel.add(ned);
        buttonPanel.add(op);
        buttonPanel.add(højre);

        int buttonW = 75;
        int buttonH = 40;
        
        buttonPanel.setPreferredSize(new Dimension(200, 75));
        op.setPreferredSize(new Dimension(buttonW, buttonH));
        ned.setPreferredSize(new Dimension(buttonW, buttonH));
        venstre.setPreferredSize(new Dimension(buttonW, buttonH));
        højre.setPreferredSize(new Dimension(buttonW, buttonH));

        frame.getContentPane().add(buttonPanel, BorderLayout.SOUTH);
        
        JTextArea spilBoarder = new JTextArea();

        //    Lav border med padding ind til teksten
        Border paddingBorder = new EmptyBorder(20, 20, 0, 0);
                
        // Lav en synlig border med en spesifik farve
        Border lineBorder = BorderFactory.createLineBorder(Color.black);

        // Sæt borderene sammen så de ikke overskriver hindanden
        Border samletBorder = BorderFactory.createCompoundBorder(lineBorder, paddingBorder);
        
        // Set the compound border to the JTextArea
        spilBoarder.setBorder(samletBorder);

        // Sæt tekstområdet til at vise den initialiserede bane
        spilBoarder.setText(baneSomString());

       // Get the screen size
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        double width = screenSize.getWidth();
        double height = screenSize.getHeight();

        // Calculate preferred size based on the screen resolution
        int preferredWidth = (int)(width * mapSizeW); // Example: 80% of screen width 0,42
        int preferredHeight = (int)(height * mapSizeH); // Example: 70% of screen height 0,6
        JScrollPane scrollPane = new JScrollPane(spilBoarder);
        scrollPane.setPreferredSize(new Dimension(preferredWidth, preferredHeight));

        // Create an inner panel with GridBagLayout to hold the JTextArea
        JPanel innerPanel = new JPanel(new GridBagLayout());
            innerPanel.add(scrollPane); // The text area is wrapped by a scroll pane

        // Create an outer panel with BorderLayout
        JPanel outerPanel = new JPanel(new BorderLayout());
            outerPanel.add(innerPanel, BorderLayout.CENTER); // Add the inner panel to the center

        // Calculate an appropriate font size based on screen size
        int fontSize = (int)(Math.min(width, height) / 50); // Example: font size based on screen dimensions
        spilBoarder.setFont(new Font("Serif", Font.PLAIN, fontSize));
        //spilBoarder.setFont(new Font("Serif", Font.PLAIN, 24));
        spilBoarder.setWrapStyleWord(true);
        spilBoarder.setEditable(false);
    
frame.add(outerPanel);
frame.setLocationRelativeTo(null); // Center the window
frame.setVisible(true);
    


        // map generator
int forsøg = 1;
while (!isRandomPathAvailable(bane, tY, tX)) {
    System.out.println(forsøg+" Maps generated");
    forsøg++;
    restartGame();
    spilBoarder.setText(baneSomString());
    

} if (isRandomPathAvailable(bane, tY, tX)){
//    pathGameOver();
System.out.println("playebel map found");

}

        //addActionerListner key functions (Control enviroment)
        højre.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                // Flyt Player til næste position
                 if (isValidMove(tY, tX + 1)) {
                højre();
                }
                             
        if (isRandomPathAvailable(bane, tY, tX)) {
            // System.out.println("Vej fundet:");
            spilBoarder.setText(baneSomString());
        } else {
        //    pathGameOver();
        restartGame();

           spilBoarder.setText(baneSomString());
        }
         if (tX == Width-1 && tY == Height-1) {
            win();  
            spilBoarder.setText(baneSomString());

                }
            }
        });

        venstre.addActionListener(new ActionListener() {
            
            public void actionPerformed(ActionEvent e) {
                // Flyt Player til næste position
                if (isValidMove(tY, tX - 1)) {
                venstre();
                }
                // Opdater tekstområdet
                 if (isRandomPathAvailable(bane, tY, tX)) {
            // System.out.println("Vej fundet:");
            spilBoarder.setText(baneSomString());
        } else {
            
            // pathGameOver();
            restartGame();

            
            spilBoarder.setText(baneSomString());
        }
         if (tX == Width-1 && tY == Height-1) {
            win();
            spilBoarder.setText(baneSomString());
  
                }
            }
        });
        
        op.addActionListener(new ActionListener() {
            
            public void actionPerformed(ActionEvent e) {
                // Flyt Player til næste position
                if (isValidMove(tY - 1, tX)) {// bruder du den her?
                op();
                }
                // Opdater tekstområdet
                 if (isRandomPathAvailable(bane, tY, tX)) {
            // System.out.println("Vej fundet:");
            spilBoarder.setText(baneSomString());
        } else {
            // pathGameOver();
            restartGame();

            spilBoarder.setText(baneSomString());
        }
            if (tX == Width-1 && tY == Height-1) {
            win();  
            spilBoarder.setText(baneSomString());

                }
            }
        });

        ned.addActionListener(new ActionListener() {
            
            public void actionPerformed(ActionEvent e) {
                // Flyt Player til næste position  
                if (isValidMove(tY + 1, tX)) {
                ned();
                }
                // Opdater tekstområdet
                 if (isRandomPathAvailable(bane, tY, tX)) {
            // System.out.println("Vej fundet:");
            spilBoarder.setText(baneSomString());
        } else {
            // pathGameOver();
            restartGame();

            spilBoarder.setText(baneSomString());
        }
                if (tX == Width-1 && tY == Height-1) {
            win();  
            spilBoarder.setText(baneSomString());

                } 
                if(bane[tX]  == bane[pitX] && bane[tY] == bane[pitY]){
            // pitGameOver();
            restartGame();

            spilBoarder.setText(baneSomString());
    }
            
            }
        });
   
     
    }
   

    
    // Flyt 'player' en position til højre
    private static void højre() {
        // Fjern Player fra nuværende position
        bane[tY][tX] = banetegn;
        // Flyt Player en position til højre
        
        if (tX >= Width-1) { // Når Player når til kanten, bliver den på banen, og lav kun pits hvis spilleren flytter sig
            tX = Width-1;
        
        }else {tX++;
        // placePits();
        }
        

        // Sæt Player på ny position
        bane[tY][tX] = spiller;
    }    

 // Flyt 'player' en position til venstre
    private static void venstre() {
        // Fjern Player fra nuværende position
        bane[tY][tX] = banetegn;

        
        if (tX <= 0) { // Når Player når til kanten, bliver den på banen, og lav kun pits hvis spilleren flytter sig
            tX = 0;
            
        } else {tX--;
        // placePits();
        } 

        // Sæt Player på ny position
        bane[tY][tX] = spiller;
    }

    // Flyt 'player' en position til ned
    private static void ned() {
        // Fjern Player fra nuværende position
        bane[tY][tX] = banetegn;
        // Flyt Player en position ned
        
        if (tY >= Height-1) { // Når Player når til kanten, bliver den på banen, og lav kun pits hvis spilleren flytter sig
            tY = Height-1;
        } else {
        tY++;
        // placePits();
    }

        // Sæt Player på ny position
        bane[tY][tX] = spiller;
      
    }

    // Flyt 'player' en position til op
    private static void op() {
        // Fjern Player fra nuværende position
        bane[tY][tX] = banetegn;

        if (tY <= 0) { // Når Player når til kanten, bliver den på banen, og lav kun pits hvis spilleren flytter sig
            tY = 0;

        } else {tY--;
            // placePits();
        }

        // Sæt Player på ny position
        bane[tY][tX] = spiller;
    }
    // konvertere bane-arrayet til en streng
    private static String baneSomString() {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < Height; i++) {
            for (int j = 0; j < Width; j++) {
                sb.append(bane[i][j]).append("");
            }
            sb.append("\n");
        }
        return sb.toString();
    }
    // Metode til at initialisere banen
    private static void initialiserBane() {
        for (int i = 0; i < Height; i++) {
            for (int j = 0; j < Width; j++) {
                bane[i][j] =  banetegn;
                bane[Height-1][Width-1] = '⛾'; //Nemt og smukt
            }
        }
        // Placer Player ved startpositionen
        
        bane[tY][tX] = spiller;
        isRandomPathAvailable(bane, pitX, pitY);
        // isPathAvailable(bane, pitX, pitY);

        placePits(); // Place initial pits
        
}

// Check if the move is valid and not stepping on a pit
private static boolean isValidMove(int newY, int newX) {
    // Check bounds
    if (newX < 0 || newX >= Width || newY < 0 || newY >= Height) {
        return false;
    }
    // Check for pit
    if (bane[newY][newX] == pit) {
        // Trigger game over 
        // pitGameOver();
        restartGame();

        //spilBoarder.setText(baneSomString());
        return false;

    }
    return true;
}

private static void win() {
    int response = JOptionPane.showConfirmDialog(null, "You won! And the coffee is still HOT!", "YOU WIN!", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);

    if (response == JOptionPane.YES_OPTION) {
        restartGame();
    } else {
        System.exit(0); // Exit the application
    }
}

private static void restartGame() {
    // Reset the player's position
    tX = 0;
    tY = 0; 
    // Reinitialize the game board
    initialiserBane();

}
public static int pitX, pitY;
// public static void placePits() {
//     Random rand = new Random();
//     int numberOfWalls = antalPits + rand.nextInt(antalPits); // Tilfældigt antal vægge

//     int maxWallLength = 13; // Maksimal længde på en væg, juster efter behov

//     for (int i = 0; i < 200; i++) {
//         // Vælg en tilfældig startposition for væggen
//         int x, y;
//         do {
//             x = rand.nextInt(Width);
//             y = rand.nextInt(Height);
//         } while (bane[pitY][pitX] == pit || (pitX == tX && pitY == tY) || (pitX == Width && pitY == Height));
                
//         // Vælg en tilfældig retning for væggen
//         int[][] directions = {
//             {0, -1}, // Op
//             {0, 1},  // Ned
//             {-1, 0}, // Venstre
//             {1, 0}   // Højre
//         };
//         int[] dir = directions[rand.nextInt(directions.length)];

//         // Vælg en tilfældig længde for væggen
//         int wallLength = rand.nextInt(maxWallLength) + 1; // Længde mellem 1 og maxWallLength

//         // Byg væggen
//         for (int j = 0; j < wallLength; j++) {
//             int newX = x + dir[0] * j;
//             int newY = y + dir[1] * j;

//             // Tjek om den nye position er inden for gitteret og ikke allerede optaget
//             if (newX >= 0 && newX < Width && newY >= 0 && newY < Height &&
//                 bane[newY][newX] != pit && bane[newY][newX] !=  pathicon
//                 &&
//                 !(newX == tX && newY == tY) && !(newX == Width && newY == Height)) {
//                 // Placér pitten (væggen)
//                 bane[newY][newX] = pit;
//             } else {
//                 // Stop væggen, hvis vi når kanten eller en optaget celle
//                 break;
//             }
//         }
        
//     }

// }


public static void placePits() {
    Random rand = new Random();
    int numberOfPits = antalPits + rand.nextInt(antalPits); // Tilfældigt antal pits mellem 'antalPits' og '2 * antalPits'
    
    double clusterProbability = 0.9; // Juster denne værdi mellem 0 og 1 for at kontrollere klyngedannelsen
    
    List<int[]> pitPositions = new ArrayList<>();
    
    for (int i = 0; i < 1000; i++) {
        if (!pitPositions.isEmpty() && rand.nextDouble() < clusterProbability) {
            // Placér pit ved siden af en eksisterende pit
            int[] existingPit = pitPositions.get(rand.nextInt(pitPositions.size()));
            int x = existingPit[0];
            int y = existingPit[1];
            
            List<int[]> adjacentPositions = new ArrayList<>();
            // Definer mulige retninger: op, ned, venstre, højre
            int[][] directions = {{-1,0},{1,0},{0,-1},{0,1}};
            
            for (int[] dir : directions) {
                int newX = x + dir[0];
                int newY = y + dir[1];
                // Tjek om den nye position er inden for gitteret og ikke allerede optaget
                if (newX >= 0 && newX < Width && newY >= 0 && newY < Height &&
                    bane[newY][newX] != pit && bane[newY][newX] != pathicon &&
                    !(newX == tX && newY == tY) && !(newX == Width && newY == Height)) {
                    adjacentPositions.add(new int[]{newX, newY});
                }
            }
            
            if (!adjacentPositions.isEmpty()) {
                int[] pos = adjacentPositions.get(rand.nextInt(adjacentPositions.size()));
                pitX = pos[0];
                pitY = pos[1];
            } else {
                // Hvis ingen tilstødende positioner er tilgængelige, placér pit tilfældigt
                placePitRandomly(rand);
            }
        } else {
            // Placér pit tilfældigt
            placePitRandomly(rand);
        }
        
        // Placér pitten
        bane[pitY][pitX] = pit;
        pitPositions.add(new int[]{pitX, pitY});
    }
}

private static void placePitRandomly(Random rand) {
    do {
        pitX = rand.nextInt(Width);
        pitY = rand.nextInt(Height);
    } while (bane[pitY][pitX] == pit || bane[pitY][pitX] == pathicon ||
             (pitX == tX && pitY == tY) || (pitX == Width && pitY == Height));
}




     // Koordinater på gitteret
     static class Koordinat  {
        int x, y;
        // Til at gemme stile/vejen fra S til E
        Koordinat forrige;

        // Konstruktør til at initialisere et punkt med koordinater og en reference til det forrige punkt
        Koordinat(int x, int y, Koordinat forrige) {
            this.x = x;
            this.y = y;
            this.forrige = forrige;
        }
    }

    // // Tjekker for næste skridt er godt nok/kan lade sig gøre
    // static boolean godNok(char[][] bane, boolean[][] tjekket, int række, int kolonne) {
    //     return række >= 0 && række < Height &&
    //            kolonne >= 0 && kolonne < Width &&
    //            bane[række][kolonne] != pit && !tjekket[række][kolonne];
    // }
  
    static boolean godNok(char[][] bane, boolean[][] tjekket, int række, int kolonne) {
        return række >= 0 && række < Height &&
               kolonne >= 0 && kolonne < Width &&
               bane[række][kolonne] != pit && !tjekket[række][kolonne];
    }
    

    // static boolean godNok(char[][] bane, boolean[][] tjekket, int række, int kolonne) {
    //     // Tjek om vi er inden for banen, feltet ikke er tjekket, og der ikke er en pit
    //     return række >= 0 && række < bane.length && kolonne >= 0 && kolonne < bane[0].length &&
    //            !tjekket[række][kolonne] && bane[række][kolonne] != pit; 
    // }
    // holder styr på, hvilke punkter i gitteret der allerede er besøgt under søgningen
    // static boolean isPathAvailable(char[][] bane, int tX, int tY) {
    //     boolean[][] tjekket = new boolean[Height][Width];
    //     ArrayList<Koordinat> liste = new ArrayList<>();
    //      // Start from the player's current position
    //     liste.add(new Koordinat(tX, tY, null));
    //     //liste.add(tX,tY); // tjek når koden virker
    //     int[] lodret = {-1, 1, 0, 0};
    //     int[] vandret = {0, 0, -1, 1};
        
    //     while (!liste.isEmpty()) {
    //         Koordinat punkt = liste.remove(0);

    //         // Markerer stien på banen
    //         if (punkt.x == Height-1 && punkt.y == Width-1) {
    //             path(bane, punkt);
    //             // Når slutpunkt rammes
    //             return true; 
    //         }
            
    //         // Undersøg alle fire mulige retninger op, ned, venstre, højre
    //         for (int i = 0; i < 4; i++) {
    //             int næsteRække = punkt.x + lodret[i];
    //             int næsteKolonne = punkt.y + vandret[i];
    
    //             if (godNok(bane, tjekket, næsteRække, næsteKolonne)) {
    //                 tjekket[næsteRække][næsteKolonne] = true;
    //                 liste.add(new Koordinat(næsteRække, næsteKolonne, punkt));
                    
    //             }
    //         }
    //     }
    //     return false; 
    // }

    static boolean isRandomPathAvailable(char[][] bane, int tX, int tY) {
        int maxSteps = 10000; // Sæt en passende værdi
        int steps = 0;
        Random rand = new Random();

        int[][] besøgstæller = new int[Height][Width];


        boolean[][] tjekket = new boolean[bane.length][bane[0].length];
        ArrayList<Koordinat> liste = new ArrayList<>();
        liste.add(new Koordinat(tX, tY, null));


        while (!liste.isEmpty()  && steps < maxSteps) {
            steps++;


            int index = rand.nextInt(liste.size());
            Koordinat punkt = liste.remove(index);
    
            if (punkt.x == bane.length - 1 && punkt.y == bane[0].length - 1) {
                path(bane, punkt);
                return true;
            }
    
            // Prøv op til 8 tilfældige bevægelser
            for (int i = 0; i < 8; i++) {
                int deltaX = 0;
                int deltaY = 0;
                if (rand.nextBoolean()) {
                    deltaX = rand.nextInt(3) - 1; // -1 til 1
                } else {
                    deltaY = rand.nextInt(3) - 1; // -1 til 1
                }
                if (deltaX == 0 && deltaY == 0) {
                    continue;
                }
    
                int næsteRække = punkt.x + deltaX;
                int næsteKolonne = punkt.y + deltaY;
    
               

                if (godNok(bane, tjekket, næsteRække, næsteKolonne)) {
                    if (besøgstæller[næsteRække][næsteKolonne] < 2) {
                        besøgstæller[næsteRække][næsteKolonne]++;
                        liste.add(new Koordinat(næsteRække, næsteKolonne, punkt));
                    }
                }else {
                }
            }
            System.out.println("Algoritmen brugte " + steps + " steps til at finde en vej");

        }

            return false; 

        }



static boolean godNok(char[][] bane, int x, int y) {
    // Tjek om positionen er inden for banen og ikke er en mur
    return x >= 0 && x < bane.length && y >= 0 && y < bane[0].length && bane[x][y] != '#';
}

       // Markerer stien på banen ved at gå baglæns fra slutpunktet
       static void path(char[][] bane, Koordinat slutpunkt) {
        Koordinat step = slutpunkt;
        while (step.forrige != null) { 
            // Markerer stien
            bane[step.x][step.y] = pathicon; 
            step = step.forrige;
       
        }
        
        // bane[0][0] = 'S'; // Marker startpunktet
            //Height - Width for somereason
        bane[Height-1][Width-1] = '⛾'; // Marker slutpunktet
    }

public static JFrame gameWindow(){
        JFrame frame = new JFrame("The Coffee Game");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(2300, 900);
        return frame;
}

public static JPanel buttonPanel(){
 JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));  

    return buttonPanel;
}
}


