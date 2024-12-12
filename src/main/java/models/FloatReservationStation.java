package models;

public class FloatReservationStation extends ReservationStation {

    private float vJ;
    private float vK;

    public FloatReservationStation() {
        super();
        vJ = 0;
        vK = 0;
    }

    public void setVJ(float vJ) {
        this.vJ = vJ;
    }

    public void setVK(float vK) {
        this.vK = vK;
    }

    public float getVJ() {
        return vJ;
    }

    public float getVK() {
        return vK;
    }

    public void clearReservationStation() {
        super.clearReservationStation();
        vJ = 0;
        vK = 0;
    }

    public void updateReservationStation(String tagName, float value) {
        if (qJ.equals(tagName)) {
            vJ = value;
            qJ = "";
        }
        if (qK.equals(tagName)) {
            vK = value;
            qK = "";
        }
    }

    public String toString() {
        return tagName + ":" + ", Operation=" + operation + "Vj=" + vJ + ", Vk=" + vK + ", Qj=" + qJ + ", Qk=" + qK
                + ", Busy=" + busy + "\n";
    }

}
