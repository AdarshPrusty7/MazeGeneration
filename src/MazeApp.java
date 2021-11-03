
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GridBagLayout;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.Toolkit;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;


public class MazeApp extends JPanel{
    int windowHeight = 400;
    int windowWidth = 300;
    private BufferedImage imageToDraw;

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        Dimension d = getWindowSize();
        g.drawImage(imageToDraw, 0, 0, d.width, d.height, null);
    }

    private BufferedImage createImage(Maze maze) {
        Dimension d = getWindowSize();
        int size = Math.min(d.width, d.height) + 2;
        BufferedImage bufferedImage = new BufferedImage(size, size, BufferedImage.TYPE_BYTE_BINARY);
        Graphics g = bufferedImage.getGraphics();

        g.setColor(Color.WHITE);
        g.fillRect (0, 0, bufferedImage.getWidth(), bufferedImage.getHeight());
        g.setColor(Color.BLACK);

        Graphics2D g2d = (Graphics2D) g;

        g2d.setStroke(new BasicStroke(3f));


        int multiplier = Math.min((d.height)/maze.getHeight(), (d.width)/ maze.getWidth());
        int adjustor = 50;

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
        return bufferedImage;
    }

    private Dimension getWindowSize() {
        return new Dimension (this.windowWidth-10, this.windowHeight-10);
    }


    public void scaleImage (int oldW, int oldH, int newW, int newH) {
        float propH = (float) newH/oldH;
        float propW = (float) newW/oldW;
        System.out.println(propW + " " + propH);

        BufferedImage newImage = new BufferedImage(newW, newH, BufferedImage.SCALE_FAST);

        Graphics2D g = newImage.createGraphics();
        g.scale(propW, propH);
        g.dispose();
        this.imageToDraw = newImage;
    }

    public MazeApp(){
        Maze maze = new Maze(10, 10);

        JFrame frame = new JFrame();
        Dimension d = new Dimension(300, 400);

        frame.getContentPane().setLayout(new GridBagLayout());
        frame.setMinimumSize(d);

        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);

        this.imageToDraw = createImage(maze);
        JLabel image = new JLabel(new ImageIcon(imageToDraw));

        frame.getContentPane().add(image);

        /*
        frame.addComponentListener(new ComponentAdapter() {
            public void componentResized (ComponentEvent componentEvent) {
                Rectangle r = frame.getBounds();
                int oldWidth = windowWidth;
                int oldHeight = windowHeight;
                windowWidth = r.width;
                windowHeight = r.height;

                scaleImage (oldWidth-10, oldHeight-10, windowWidth-10, windowHeight-10);

                frame.remove(image);
                JLabel image = new JLabel(new ImageIcon(imageToDraw));
                frame.getContentPane().add(image);

            }
        });*/

    }

    public static void main (String[] args) {
        MazeApp mazeApp = new MazeApp();


    }


}
