package controllers;

import models.FloatReservationStation;
import models.ReservationStation;

public class FloatReservationStationController {


    public static int getEmptyStationIndex(ReservationStation[] rsToSearch){
        for (int i = 0; i < rsToSearch.length; i++) {
            if(!(rsToSearch[i].isBusy())){
                return i;
            }
        }
        return -1;
    }

}
