import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class SimpleMovement extends JFrame {
    static int width = 800;
    static int height = 600;
    static int clientWidth;
    static int clientHeight;
    private BufferedImage image;
    public JPanel panel;
    public JLabel label;
    private boolean dragging = false;
    private int dx = 0;
    private int dy = 0;

    public SimpleMovement() {
        super("simple movement");
        try {
            initGui();
        } catch (IOException ignored) {
            System.out.println("initGui error");
        }
    }

    private void initGui() throws IOException {
        Dimension d = Toolkit.getDefaultToolkit().getScreenSize();

        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setPreferredSize(new Dimension(SimpleMovement.width, SimpleMovement.height));
        this.setLocation(d.width / 2 - SimpleMovement.width / 2, d.height / 2 - SimpleMovement.height / 2);
        this.getContentPane().setBackground(Color.lightGray);
        this.setResizable(false);

        panel = new JPanel();
        panel.setBackground(Color.lightGray);
        panel.setFocusable(true);
        panel.setLayout(null);
        this.getContentPane().add(panel);

        image = ImageIO.read(new File("object-50.jpg"));

        label = new JLabel();
        label.setIcon(new ImageIcon(image));
        label.setBounds(0, 0, image.getWidth(), image.getHeight());
        panel.add(label);


        panel.addMouseMotionListener(new MouseMotionAdapter() {
            @Override
            public void mouseDragged(MouseEvent e) {
                super.mouseDragged(e);
                if (dragging) {
                    label.setLocation(e.getX() - dx, e.getY() - dy);
                }
            }
        });

        panel.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                super.mousePressed(e);
                if (label.getBounds().contains(new Point(e.getX(), e.getY()))) {
                    Rectangle bounds = label.getBounds();
                    dx = e.getX() - bounds.x;
                    dy = e.getY() - bounds.y;
                    dragging = true;
                }
            }

            @Override
            public void mouseReleased(MouseEvent e) {
                super.mouseReleased(e);
                dragging = false;
            }
        });
    }

    public void setVisible(boolean b) {
        super.setVisible(b);
        clientWidth = SimpleMovement.width;
        clientHeight = SimpleMovement.height;
        if (isResizable()) {
            clientWidth = getContentPane().getWidth();
            clientHeight = getContentPane().getHeight();
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                SimpleMovement frame = new SimpleMovement();
                frame.pack();
                frame.setVisible(true);
            }
        });
    }
}
