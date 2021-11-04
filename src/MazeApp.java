
import net.miginfocom.swing.MigLayout;

import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.text.NumberFormat;
import java.util.ArrayList;
import javax.swing.*;
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

        BufferedImage bufferedImage = new BufferedImage(2002, 2002, BufferedImage.TYPE_BYTE_BINARY);
        Graphics g = bufferedImage.getGraphics();

        g.setColor(Color.WHITE);
        g.fillRect (0, 0, bufferedImage.getWidth(), bufferedImage.getHeight());
        g.setColor(Color.BLACK);

        Graphics2D g2d = (Graphics2D) g;


        float strokeSize = Math.min(1000/maze.getHeight(), 1000/ maze.getWidth());
        g2d.setStroke(new BasicStroke(strokeSize));

        int multiplier = Math.min(2000/maze.getWidth(), 2000/maze.getHeight());
        int adjustorX = 0;
        int adjustorY = 0;

        if (maze.getHeight() != maze.getWidth()) {
            if (maze.getHeight() < maze.getWidth()) {
                adjustorY = (1000*(maze.getWidth() - maze.getHeight()))/ maze.getWidth();
            } else {
                adjustorX = (1000*(maze.getHeight() - maze.getWidth()))/ maze.getHeight();
            }
        }

        for (int col=0; col<maze.getWidth(); col++) {
            for (int row=0; row<maze.getHeight(); row++) {
                Cell cell = maze.getFinishedMaze()[col][row];


                if (cell.isNorthWall()){
                    g2d.drawLine((cell.getxPos()*multiplier)+adjustorX,((cell.getyPos()+1)*multiplier)+adjustorY,((cell.getxPos()+1)*multiplier)+adjustorX,((cell.getyPos()+1)*multiplier)+adjustorY);
                }
                if (cell.isSouthWall()){
                    g2d.drawLine((cell.getxPos()*multiplier)+adjustorX,((cell.getyPos())*multiplier)+adjustorY,((cell.getxPos()+1)*multiplier)+adjustorX,((cell.getyPos())*multiplier)+adjustorY);
                }
                if (cell.isEastWall()) {
                    g2d.drawLine(((cell.getxPos()+1)*multiplier)+adjustorX,((cell.getyPos())*multiplier)+adjustorY,((cell.getxPos()+1)*multiplier)+adjustorX,((cell.getyPos()+1)*multiplier )+adjustorY);
                }
                if (cell.isWestWall()) {
                    g2d.drawLine(((cell.getxPos())*multiplier)+adjustorX,((cell.getyPos())*multiplier)+adjustorY,((cell.getxPos())*multiplier)+adjustorX,((cell.getyPos()+1)*multiplier )+adjustorY);
                }
            }
        }
        g2d.dispose();
        g.dispose();
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

        JPanel toolPanel = new JPanel();

        JLabel image = new JLabel(new ImageIcon(scaleImage()));
        image.setBorder(new EmptyBorder(5,5,5,5));
        jPanel.add(image, "push, align center");

        JButton refreshButton = new JButton("Refresh");
        refreshButton.setFocusable(false);
        refreshButton.setMnemonic(KeyEvent.VK_R);
        refreshButton.setToolTipText("Get new maze.");
        refreshButton.setFont(new Font("Calibri", Font.PLAIN, 18));
        refreshButton.setMargin(new Insets(3,3,3,3));

        JFormattedTextField widthField = new JFormattedTextField(NumberFormat.getNumberInstance());
        JFormattedTextField heightField = new JFormattedTextField(NumberFormat.getNumberInstance());

        // ADDING ALL ELEMENTS

        // adding toolPanel
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 5;

        frame.getContentPane().add(toolPanel, gbc);

        //adding width label
        gbc.anchor = GridBagConstraints.LINE_START;
        toolPanel.add(new JLabel("Width"), gbc);

        //adding width field

        gbc.gridx = 1;
        widthField.setEditable(true);
        widthField.setPreferredSize(new Dimension(40, 30));
        toolPanel.add(widthField, gbc);

        //adding refresh button
        gbc.anchor = GridBagConstraints.CENTER;
        gbc.gridx = 2;
        toolPanel.add(refreshButton, gbc);

        //adding height field
        gbc.anchor = GridBagConstraints.LINE_END;
        gbc.gridx = 3;
        heightField.setEditable(true);
        heightField.setPreferredSize(new Dimension(40, 30));
        toolPanel.add(heightField, gbc);

        // adding height label
        gbc.gridx = 4;
        toolPanel.add(new JLabel("Height"), gbc);

        // adding spacer between toolbar and main content
        gbc.anchor = GridBagConstraints.CENTER;
        gbc.gridwidth = GridBagConstraints.RELATIVE;
        gbc.gridx = 2;
        gbc.gridy = 1;
        frame.getContentPane().add(new JPanel(), gbc);

        // adding main content panel
        gbc.gridy = 2;
        gbc.fill = GridBagConstraints.RELATIVE;
        frame.getContentPane().add(jPanel, gbc);

        frame.addComponentListener(new ComponentAdapter() {
            public void componentResized (ComponentEvent componentEvent) {
                windowResizing(jPanel, toolPanel, frame, image);
            }
        });

        refreshButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println("Button Pressed");

                ArrayList<Integer> mazeDimensions = getNewMazeDimensions(widthField, heightField);

                Maze newMaze = new Maze(mazeDimensions.get(0), mazeDimensions.get(1));
                imageToDraw = createImage(newMaze);
                windowResizing(jPanel, toolPanel, frame, image);
            }
        });


        frame.pack();
        frame.setMinimumSize(d);
        frame.setPreferredSize(d);
        frame.setVisible(true);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    private ArrayList<Integer> getNewMazeDimensions(JFormattedTextField widthField, JFormattedTextField heightField) {
        ArrayList<Integer> mazeDimensions = new ArrayList<>();

        String widthText = widthField.getText();
        String heightText = heightField.getText();
        String widthTextStrip = widthText.replaceAll("[^\\d]", "");
        String heightTextStrip = heightText.replaceAll("[^\\d]", "");

        if (widthTextStrip.equals("")) {
            widthField.setText("5");
            widthTextStrip = "5";
        }
        if (heightTextStrip.equals("")) {
            heightField.setText("5");
            heightTextStrip = "5";
        }

        boolean tooSmall = false;
        if (widthTextStrip.equals("1")) {
            widthField.setText("5");
            widthTextStrip = "5";
            tooSmall = true;
        }
        if (heightTextStrip.equals("1")) {
            heightField.setText("5");
            heightTextStrip = "5";
            tooSmall = true;
        }

        if (tooSmall) {
            JOptionPane.showMessageDialog(null, "Value chosen was too small. Defaulted to 5.");
        }

        String finalWidth = firstTwo(widthTextStrip);
        String finalHeight = firstTwo(heightTextStrip);

        widthField.setText(finalWidth);
        heightField.setText(finalHeight);

        mazeDimensions.add(Integer.parseInt(finalWidth));
        mazeDimensions.add(Integer.parseInt(finalHeight));

        return mazeDimensions;
    }

    public String firstTwo(String str) {
        return str.length() < 2 ? str : str.substring(0, 2);
    }


    private void windowResizing(JPanel jPanel, JPanel toolPanel, JFrame frame, JLabel image){
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

        toolPanel.setMinimumSize(new Dimension((int) Math.round(re.width*0.8), 50));
        toolPanel.setPreferredSize(new Dimension((int) Math.round(re.width*0.8), 50));

        image.setMinimumSize(new Dimension((int) Math.round(r.width*1), (int) Math.round(r.height*1)));
        image.setPreferredSize(new Dimension((int) Math.round(r.width*1), (int) Math.round(r.height*1)));
    }


    public static void main (String[] args) {
        MazeApp mazeApp = new MazeApp();
    }
}