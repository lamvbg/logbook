package com.example.logbook.ui.models;

import com.example.logbook.ui.enums.Units;

public class Result {
    public float value;
    public Units unit;

    public Result(float value, Units unit) {
        this.value = value;
        this.unit = unit;
    }
}
