package com.example.logbook.ui.helpers;

import com.example.logbook.ui.enums.Units;
import com.example.logbook.ui.models.Result;

public class MillimetreConverterHelper {
    private int unit;

    public MillimetreConverterHelper(int value) {
        this.unit = value;
    }

    public Result[] convertFromMillimetreToUnits() {
        return new Result[]{
                            new Result(this.convertFromMmToMeter(), Units.METER),
                            new Result(this.convertFromMmToMillimetre(), Units.MILLIMETRE),
                            new Result(this.convertFromMmToMile(), Units.MILE),
                            new Result(this.convertFromMmToFoot(), Units.FOOT)};
    }

    private float convertFromMmToMeter() {
        return (float) this.unit / 1000;
    }

    private float convertFromMmToMillimetre() {
        return (float) this.unit;
    }

    private float convertFromMmToMile() {
        return (float) ((float) this.unit * 0.00000062137);
    }

    private float convertFromMmToFoot() {
        return (float) ((float) this.unit * 0.0032808);
    }
}
