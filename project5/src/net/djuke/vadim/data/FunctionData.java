package net.djuke.vadim.data;

import java.util.ArrayList;
import java.util.List;

public class FunctionData {

    private List<Double> arguments = new ArrayList<>();
    private List<Double> values = new ArrayList<>();

    public FunctionData() {
    }

    public void putValue(double argument, double value) {
        arguments.add(argument);
        values.add(value);
    }

    public double getValue(double argument) {
        return values.get(arguments.indexOf(argument));
    }

    public List<Double> getArgumentsList() {
        return arguments;
    }

    public List<Double> getValuesList() {
        return values;
    }
}
