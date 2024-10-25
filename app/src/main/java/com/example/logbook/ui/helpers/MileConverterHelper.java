package com.example.logbook.ui.helpers;

import com.example.logbook.ui.enums.Units;
import com.example.logbook.ui.models.Result;

public class MileConverterHelper {
    private int unit;

    public MileConverterHelper(int value) {
        this.unit = value;
    }

    public Result[] convertFromMileToUnits() {
        return new Result[]{
                new Result(this.convertFromMileToMeter(), Units.METER),
                new Result(this.convertFromMileToMillimetre(), Units.MILLIMETRE),
                new Result(this.convertFromMileToMile(), Units.MILE),
                new Result(this.convertFromMileToFoot(), Units.FOOT),
                };
    }

    private float convertFromMileToMeter() {
        return (float) this.unit * 1609.34f;
    }

    private float convertFromMileToMillimetre() {
        return (float) this.unit * 1_609_344f;
    }

    private float convertFromMileToMile() {
        return (float) this.unit;
    }

    private float convertFromMileToFoot() {
        return (float) this.unit * 5280f;
    }
}
