package gps;

public class CompareCoordinates {
    double[] lat;
    double[] lng;
    int closest;

    public CompareCoordinates(double[] lat, double[] lng) {
        this.lat = lat;
        this.lng = lng;
    }
    public double[] findClosest(double currentlat, double currentlng) {
        double resultLat = currentlat;
        double resultLng = currentlng;
        double distance1 = 10000000;
        closest = 0;

        DistanceCalculator dc = new DistanceCalculator();

        for (int i = 0; i < lat.length; i++) {
            double distance2 = dc.distFrom(resultLat, resultLng, lat[i], lng[i]);
            if (distance2 < distance1) {
                distance1 = distance2;
                closest = i;
            }
        }
        double[] returncoordinates = {
                lat[closest],
                lng[closest]};
        return returncoordinates;
    }

    public int getClosestID() {
        return closest;
    }
}