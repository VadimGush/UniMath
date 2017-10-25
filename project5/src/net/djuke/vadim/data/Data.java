package net.djuke.vadim.data;

public class Data {

    // Input data
    private double  epsilon;
    private int     segmentsCount;
    private double  start;
    private double  end;
    private int     interpolatePoints;

    // Main data
    private FunctionData    function;
    private DifTable        difTable;
    private FunctionData    interpolate;

    public Data() {
        function = new FunctionData();
        interpolate = new FunctionData();
        difTable = new DifTable();
    }

    public DifTable getDifTable() {
        return difTable;
    }

    public void setDifTable(DifTable difTable) {
        this.difTable = difTable;
    }

    public double getEpsilon() {
        return epsilon;
    }

    public void setEpsilon(double epsilon) {
        this.epsilon = epsilon;
    }

    public int getSegmentsCount() {
        return segmentsCount;
    }

    public void setSegmentsCount(int segmentsCount) {
        this.segmentsCount = segmentsCount;
    }

    public double getStart() {
        return start;
    }

    public void setStart(double start) {
        this.start = start;
    }

    public double getEnd() {
        return end;
    }

    public void setEnd(double end) {
        this.end = end;
    }

    public int getInterpolatePoints() {
        return interpolatePoints;
    }

    public void setInterpolatePoints(int interpolatePoints) {
        this.interpolatePoints = interpolatePoints;
    }

    public FunctionData getFunction() {
        return function;
    }

    public void setFunction(FunctionData function) {
        this.function = function;
    }

    public FunctionData getInterpolate() {
        return interpolate;
    }

    public void setInterpolate(FunctionData interpolate) {
        this.interpolate = interpolate;
    }
}
