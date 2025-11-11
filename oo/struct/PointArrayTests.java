package oo.struct;

import org.junit.jupiter.api.Test;

public class PointArrayTests {

    @Test
    public void coordinatesAsArrays() {

        int[][] trianglePoints = {{1, 1, 0}, {5, 1, 0}, {3, 7, 1}};

        for (int[] each : trianglePoints) {
            System.out.println(each[2]);
        }

    }

    @Test
    public void coordinatesAsObjects() {
        Point3D point1 = new Point3D(1, 2, 3);
        Point3D point2 = new Point3D(4, 5, 6);
        Point3D point3 = new Point3D(7, 8, 9);
        Point3D[] points = {point1, point2, point3};

        for (Point3D point : points) {
            System.out.println(point.z());
        }
    }


}
