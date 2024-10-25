package com.example.logbook.ui.helpers;

import com.example.logbook.ui.enums.Units;
import com.example.logbook.ui.models.Result;

public class MeterConverterHelper {
    private final int unit;

    public MeterConverterHelper(int value) {
        this.unit = value;
    }

    public Result[] convertFromMeterToUnits() {
        return new Result[]{
                new Result(this.convertFromMeterToMeter(), Units.METER),
                new Result(this.convertFromMeterToMillimetre(), Units.MILLIMETRE),
                new Result(this.convertFromMeterToMile(), Units.MILE),
                new Result(this.convertFromMeterToFoot(), Units.FOOT),};
    }

    private float convertFromMeterToMeter() {
        return (float) this.unit;
    }

    private float convertFromMeterToMillimetre() {
        return (float) this.unit * 1000;
    }

    private float convertFromMeterToMile() {
        return (float) ((float) this.unit * 0.00062137);
    }

    private float convertFromMeterToFoot() {
        return (float) ((float) this.unit * 3.2808);
    }
}
