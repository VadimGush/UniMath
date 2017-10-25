package net.djuke.vadim.math;

import net.djuke.vadim.data.Data;

public class DefaultFunction implements Function{

    private Data data;
    private final double V = 6;

    @Override
    public void calculate(Data data) {
        this.data = data;
        double delta = (data.getEnd() - data.getStart()) / data.getSegmentsCount();
        for (int i = 0; i <= data.getSegmentsCount(); i++) {
            double argument = data.getStart() + delta * i;
            data.getFunction().putValue((double)Math.round(argument * 1000) / 1000, function(argument));
        }

    }

    private double function(double x) {
        double sum = 0;
        for (int i = 1; true; i++) {
            double element = Math.pow(-1,i+1) * (Math.pow(V*x, i+(i-1)) / fact(i+(i-1)));
            if (Math.abs(element) < data.getEpsilon()) break;
            else {
                sum += element;
            }
        }
        return sum;
    }

    private double fact(int i) {
        double result = 1;
        for (int counter = 1; counter <= i; counter++) {
            result *= counter;
        }
        return result;
    }


}
