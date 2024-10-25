package com.example.logbook.ui.helpers;

import com.example.logbook.ui.enums.Units;
import com.example.logbook.ui.models.Result;

public class FootConverterHelper {
    private int unit;

    public FootConverterHelper(int value) {
        this.unit = value;
    }

    public Result[] convertFromFootToUnits() {
        return new Result[]{
                new Result(this.convertFromFootToMeter(), Units.METER),
                new Result(this.convertFromFootToMillimetre(), Units.MILLIMETRE),
                new Result(this.convertFromFootToMile(), Units.MILE),
                new Result(this.convertFromFootToFoot(), Units.FOOT)};
    }

    private float convertFromFootToMeter() {
        return (float) this.unit * 0.3048f;
    }

    private float convertFromFootToMillimetre() {
        return (float) this.unit * 304.8f;
    }

    private float convertFromFootToMile() {
        return (float) this.unit * 0.000189394f;
    }

    private float convertFromFootToFoot() {
        return (float) this.unit;
    }
}
