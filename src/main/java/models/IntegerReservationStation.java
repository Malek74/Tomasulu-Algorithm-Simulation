package models;

public class IntegerReservationStation extends ReservationStation {
    private int vJ;
    private int vK;

    public IntegerReservationStation() {
        super();
        vJ = 0;
        vK = 0;

    }

    public void setvJ(int vJ) {
        this.vJ = vJ;
    }

    public void setvK(int vK) {
        this.vK = vK;
    }

    public int getVJ() {
        return vJ;
    }

    public int getVK() {
        return vK;
    }

    public void updateReservationStation(String tagName, int value) {
        if (qJ.equals(tagName)) {
            vJ = value;
            qJ = "";
        }
        if (qK.equals(tagName)) {
            vK = value;
            qK = "";
        }
        if (qJ.equals("") && qK.equals("")) {
            isReady = true;
        }
    }

    public void clearReservationStation() {
        super.clearReservationStation();
        vJ = 0;
        vK = 0;
    }
}
