
import net.miginfocom.swing.MigLayout;

import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.EmptyBorder;


public class MazeApp extends JPanel{
    int jPanelHeight = 400;
    int jPanelWidth = 300;
    private BufferedImage imageToDraw;

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        Dimension d = getWindowSize();
        g.drawImage(imageToDraw, 0, 0, d.width, d.height, null);
    }

    private BufferedImage createImage(Maze maze) {
        Dimension d = getWindowSize();
        BufferedImage bufferedImage = new BufferedImage(2002, 2002, BufferedImage.TYPE_BYTE_BINARY);
        Graphics g = bufferedImage.getGraphics();

        g.setColor(Color.WHITE);
        g.fillRect (0, 0, bufferedImage.getWidth(), bufferedImage.getHeight());
        g.setColor(Color.BLACK);

        Graphics2D g2d = (Graphics2D) g;

        g2d.setStroke(new BasicStroke(30f));


        int multiplier = Math.min(2000/maze.getHeight(), 2000/ maze.getWidth());


        for (int col=0; col<maze.getWidth(); col++) {
            for (int row=0; row<maze.getHeight(); row++) {
                Cell cell = maze.getFinishedMaze()[col][row];


                if (cell.isNorthWall()){
                    g2d.drawLine(cell.getxPos()*multiplier,(cell.getyPos()+1)*multiplier,(cell.getxPos()+1)*multiplier,(cell.getyPos()+1)*multiplier);
                }
                if (cell.isSouthWall()){
                    g2d.drawLine(cell.getxPos()*multiplier,(cell.getyPos())*multiplier,(cell.getxPos()+1)*multiplier,(cell.getyPos())*multiplier);
                }
                if (cell.isEastWall()) {
                    g2d.drawLine((cell.getxPos()+1)*multiplier,(cell.getyPos())*multiplier,(cell.getxPos()+1)*multiplier,(cell.getyPos()+1)*multiplier );
                }
                if (cell.isWestWall()) {
                    g2d.drawLine((cell.getxPos())*multiplier,(cell.getyPos())*multiplier,(cell.getxPos())*multiplier,(cell.getyPos()+1)*multiplier );
                }
            }
        }
        //g2d.dispose();
        //g.dispose();
        this.imageToDraw = bufferedImage;
        return bufferedImage;
    }

    private Dimension getWindowSize() {
        return new Dimension (this.jPanelWidth, this.jPanelHeight);
    }


    public BufferedImage scaleImage () {

        int size = Math.min(this.jPanelWidth, this.jPanelHeight);
        int calcSize = (int) Math.round(size*0.9);
        BufferedImage newImage = new BufferedImage(calcSize, calcSize, BufferedImage.TYPE_BYTE_BINARY);

        Graphics2D g = newImage.createGraphics();
        g.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);

        g.drawImage(imageToDraw, 0, 0, calcSize, calcSize, null);
        g.dispose();

        return newImage;
    }

    public MazeApp(){
        int mazeW = 5;
        int mazeH = 5;

        Maze maze = new Maze(mazeW, mazeH);

        JFrame frame = new JFrame();
        Dimension d = new Dimension(450, 450);

        frame.getContentPane().setLayout(new GridBagLayout());

        GridBagConstraints gbc = new GridBagConstraints();
        
        this.imageToDraw = createImage(maze);

        JPanel jPanel = new JPanel();
        jPanel.setLayout(new MigLayout());

        JLabel image = new JLabel(new ImageIcon(scaleImage()));
        image.setBorder(new EmptyBorder(5,5,5,5));
        jPanel.add(image, "push, align center");

        JButton refreshButton = new JButton("Refresh");
        refreshButton.setFocusable(false);
        refreshButton.setMnemonic(KeyEvent.VK_R);
        refreshButton.setToolTipText("Get new maze.");
        refreshButton.setFont(new Font("Calibri", Font.PLAIN, 18));
        refreshButton.setMargin(new Insets(3,3,3,3));

        gbc.gridx = 0;
        gbc.gridy = 0;
        frame.getContentPane().add(refreshButton, gbc);
        gbc.gridy = 1;
        frame.getContentPane().add(new JPanel(), gbc);
        gbc.gridy = 2;
        gbc.fill = GridBagConstraints.RELATIVE;

        frame.getContentPane().add(jPanel, gbc);

        frame.addComponentListener(new ComponentAdapter() {
            public void componentResized (ComponentEvent componentEvent) {
                windowResizing(jPanel, frame, image);
            }
        });

        refreshButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println("Test");

                Maze newMaze = new Maze(mazeW, mazeH);
                imageToDraw = createImage(newMaze);
                windowResizing(jPanel, frame, image);
            }
        });


        frame.pack();
        frame.setMinimumSize(d);
        frame.setPreferredSize(d);
        frame.setVisible(true);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    private void windowResizing(JPanel jPanel, JFrame frame, JLabel image){
        Rectangle r = jPanel.getBounds();
        jPanelWidth = r.width;
        jPanelHeight = r.height;

        Rectangle re = frame.getBounds();


        System.out.println("JPanel: " + jPanelWidth + " " + jPanelHeight);
        System.out.println("JFrame: " + re.width + " " + re.height);


        image.setIcon(new ImageIcon(scaleImage()));
        frame.repaint();
        jPanel.setMinimumSize(new Dimension((int) Math.round(re.width*0.8), (int) Math.round(re.height*0.8)));
        jPanel.setPreferredSize(new Dimension((int) Math.round(re.width*0.8), (int) Math.round(re.height*0.8)));

        image.setMinimumSize(new Dimension((int) Math.round(r.width*1), (int) Math.round(r.height*1)));
        image.setPreferredSize(new Dimension((int) Math.round(r.width*1), (int) Math.round(r.height*1)));
    }


    public static void main (String[] args) {
        MazeApp mazeApp = new MazeApp();
    }


}