import javax.swing.*;
import java.awt.*;

public class SimpleAnimation3C {
    private int[] pos = {50, 50};
    private DrawPanel panel = new DrawPanel();
    private JFrame frame = new JFrame();
    private byte actualDirect = 3;
    private final int deltaT = 30;

    class PhSphere extends PhysicObj2D {
        private Color spColor = Color.orange;
        private int[] spSize = {100, 100};

        public int[] getSpSize() {
            return spSize;
        }

        public void setSpSize(int[] spSize) {
            this.spSize = spSize;
        }

        public Color getSpColor() {
            return spColor;
        }

        public void setSpColor(Color spColor) {
            this.spColor = spColor;
        }

        public PhSphere() {
            super(deltaT);
        }
    }

    class DrawPanel extends JPanel {

        private PhSphere sphere = new PhSphere();

        private float[] int2float(int[] it) {
            float[] f = {0, 0};
            for(int i=0;i<2;++i) {
                f[i] = (float) it[i];
            }
            return f;
        }

        public DrawPanel(){
            float[] temp = {-100f, 100f};
            sphere.setAccel(temp);
            temp = int2float(pos);
            sphere.setPos(temp);
        }

        private void setPosInBounds() {
            int[] bounds = {this.getWidth(), this.getHeight()};
            for(int i=0; i<2; ++i) {
                pos[i] = Math.max(pos[i], 0);
                pos[i] = Math.min(pos[i], bounds[i]-sphere.getSpSize()[i]);
            }
        }

        private void syncPos(boolean direction) {
            // direction -> false : pos->sphere, true: sphere-> pos
            float[] temp = sphere.getPos();
            for(int i=0;i<2;++i) {
                if (direction) {
                    sphere.setPos(int2float(pos));
                }
                else {
                    pos[i] = Math.round(temp[i]);
                }
            }
        }

        public void moveSphere() {
            byte result = checkCollision();
            if (result == 1) {
                sphere.revertVel((byte) 0);
                sphere.revertAcc((byte) 0);
            } else if (result == 2) {
                sphere.revertVel((byte) 1);
                sphere.revertAcc((byte) 1);
            }

            while(checkCollision() != 0) {
                setPosInBounds();
                syncPos(true);
            }

            // call update position method
            sphere.updatePos();
            this.syncPos(false);

        }

        public void paintComponent(Graphics g) {
            Graphics2D g2d = (Graphics2D) g;
            g2d.setColor(Color.white);
            g2d.fillRect(0,0, this.getWidth(), this.getHeight());

            int[] spSize = sphere.getSpSize();
            g2d.setColor(sphere.getSpColor());
            g2d.fillOval(pos[0], pos[1], spSize[0], spSize[1]);

        }

        private byte checkCollision() {
            // return -> 0: no collision, 1: x collision, 2: y collision
            if (this.getWidth()<= 0 || this.getHeight() <= 0) {
                return 0;
            }
            int[] spSize = sphere.getSpSize();
            if(pos[0] < 0 || pos[0] + spSize[0] > this.getWidth()) {
                return 1;
            }
            else if(pos[1] < 0 || pos[1] + spSize[1] > this.getHeight()) {
                return 2;
            }
            return 0;
        }
    }

    public SimpleAnimation3C() {
        frame.getContentPane();
        frame.setSize(500, 500);
        frame.setVisible(true);
        frame.add(panel);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    public void runAnimation() {
        while(true) {
            panel.moveSphere();
            panel.repaint();

            try {
                Thread.sleep(50);
            }
            catch (Exception ex) { break;}
        }
    }

    public static void main(String[] args) {
        SimpleAnimation3C gui = new SimpleAnimation3C();
        gui.runAnimation();
    }
}
