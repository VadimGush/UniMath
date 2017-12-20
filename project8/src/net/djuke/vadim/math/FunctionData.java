package net.djuke.vadim.math;

import java.util.ArrayList;
import java.util.List;

public class FunctionData {

    private List<Double> arguments;
    private List<Double> values;


    public FunctionData() {
        arguments = new ArrayList<>();
        values = new ArrayList<>();
    }

    public List<Double> getArguments() {
        return arguments;
    }

    public double getValue(double argument) {
        return values.get(arguments.indexOf(argument));
    }

    public List<Double> getValues() {
        return values;
    }

    public void putValue(double argument, double value) {
        arguments.add(argument);
        values.add(value);
    }

}
