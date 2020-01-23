import java.awt.*;

public class PhysicObj2D {
    private float[] velocity = {0, 0};
    private float[] acceleration = {0, 0};
    private float[] position = {0, 0};

    private final int deltaT;

    public PhysicObj2D(int dT) throws ExceptionInInitializerError{
        if(dT < 0) {
            throw new ExceptionInInitializerError("Delta time provided is not valid. must be >= 0");
        }
        this.deltaT = dT;
    }

    public final float[] getVel() {return this.velocity;}
    public final float[] getPos() {return this.position;}
    public final float[] getAccel() {return this.acceleration;}

    public final void setVel(float[] vel) {
        if(vel.length == 2) {
            this.velocity = vel;
        }
    }

    public final void setPos(float[] pos) {
        if(pos.length == 2) {
            this.position = pos;
        }
    }
    public final void setAccel(float[] accel) {
        if(accel.length == 2) {
            this.acceleration = accel;
        }
    }

    public final void updatePos() {
        float dT = this.deltaT/1000f;
        for(int i=0;i<2;++i) {
            position[i] += velocity[i]*dT + 0.5*acceleration[i]*Math.pow(dT, 2);
            velocity[i] += acceleration[i]*dT;
        }
    }

    public final void revertVel(byte axis) {
        // axis -> 0: x, 1: y, 2: both
        if (axis == 0) {
            velocity[0] = -velocity[0];
        }
        if (axis == 1) {
            velocity[1] = -velocity[1];
        }
        if (axis == 2) {
            velocity[0] = -velocity[0];
            velocity[1] = -velocity[1];
        }

    }

    public final void revertAcc(byte axis) {
        // axis -> 0: x, 1: y, 2: both
        if (axis == 0) {
            acceleration[0] = -acceleration[0];
        }
        if (axis == 1) {
            acceleration[1] = -acceleration[1];
        }
        if (axis == 2) {
            acceleration[0] = -acceleration[0];
            acceleration[1] = -acceleration[1];
        }

    }

    public static void main(String[] args) {
        PhysicObj2D obj = new PhysicObj2D(50);
        float[] vel = {5f, 0};
        obj.setAccel(vel);

        for(int t=0;t<1000;t+=50) {
            obj.updatePos();
            System.out.println(String.format("for t = %d, current pos= %.2f", t, obj.getPos()[0]));
        }

    }
}
