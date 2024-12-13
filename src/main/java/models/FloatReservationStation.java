package models;

public class FloatReservationStation extends ReservationStation {


    public FloatReservationStation() {
        super();
    }


  


    public void clearReservationStation() {
        super.clearReservationStation();
    
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

    public String toString() {
        return tagName + ":" + ", Operation=" + operation + "Vj=" + vJ + ", Vk=" + vK + ", Qj=" + qJ + ", Qk=" + qK
                + ", Busy=" + busy + "\n";
    }

}
