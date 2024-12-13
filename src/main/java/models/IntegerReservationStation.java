package models;

public class IntegerReservationStation extends ReservationStation {

    public IntegerReservationStation() {
        super();

    }

    public void updateReservationStation(String tagName, MemoryBlock value) {
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

    }
}
