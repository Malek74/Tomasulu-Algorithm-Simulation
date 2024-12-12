package models;

public class FloatReservationStation extends ReservationStation {

    private double vJ;
    private double vK;

    public FloatReservationStation() {
        super();
        vJ = 0;
        vK = 0;
    }

    public void setVJ(double vJ) {
        this.vJ = vJ;
    }

    public void setVK(double vK) {
        this.vK = vK;
    }

    public double getVJ() {
        return vJ;
    }

    public double getVK() {
        return vK;
    }

    public void clearReservationStation() {
        super.clearReservationStation();
        vJ = 0;
        vK = 0;
    }

    public String toString() {
        return tagName + ":" + ", Operation=" + operation + "Vj=" + vJ + ", Vk=" + vK + ", Qj=" + qJ + ", Qk=" + qK
                + ", Busy=" + busy + "\n";
    }

}
