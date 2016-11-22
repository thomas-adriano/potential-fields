package br.com.furb.potentialfields;

/**
 * Created by Thomas.Adriano on 22/11/2016.
 */
public class Action {

    public final double directionAngle;
    public final double velocity;

    public Action(double directionAngle, double velocity) {
        this.directionAngle = directionAngle;
        this.velocity = velocity;
    }

    @Override
    public String toString() {
        return "{" +
                "ang=" + Math.round(directionAngle) +
                ", vel=" + Math.round(velocity) +
                '}';
    }

    public static final Action emptyAction() {
        return new Action(0, 0);
    }
}
