package oo.hide;

import java.util.Arrays;

public class PointSet {

    private int capacity;
    private Point[] points;
    private int index = 0;

    public static void main(String[] args) {
        PointSet set = new PointSet();
        set.add(new Point(1, 1));
        set.add(null);
        set.add(new Point(1, 2));
        set.add(null);
        System.out.println(set);
    }

    public PointSet(int capacity) {
        this.capacity = capacity;
        points = new Point[capacity];
    }

    public PointSet() {
        this(10);
    }

    public void add(Point point) {
        if (!contains(point)) {
            if (index == capacity) {
                capacity *= 2;
                points = Arrays.copyOf(points, capacity);
            }
            points[index] = point;
            index++;
        }
    }

    public int size() {
        int length = 0;
        for (Point p : points) {
            if (p != null) {
                length++;
            }
        }
        return length;
    }

    public boolean contains(Point point) {
        if (point == null) {
            return false;
        }
        for (Point el : points) {
            if (el != null && el.x == point.x && el.y == point.y) {
                return true;
            }
        }
        return false;
    }

    @Override
    public String toString() {
        String setToString = "";
        boolean first = true;
        for (int i = 0; i < index; i++) {
            if (points[i] != null) {
                Point el = points[i];
                if (first) {
                    first = false;
                    setToString += "(%s, %s)".formatted(el.x, el.y);
                }
                else {
                    setToString += ", (%s, %s)".formatted(el.x, el.y);
                }
            }
            else {
                if (first) {
                    first = false;
                    setToString += "null";
                }
                else {
                    setToString += ", null";
                }
            }
        }

        return setToString;
    }

    public PointSet subtract(PointSet other) {
        PointSet newArray = new PointSet();
        for (Point p : points) {
            if (p != null && !other.contains(p)) {
                newArray.add(p);
            }
        }
        return newArray;
    }

    public PointSet intersect(PointSet other) {
        PointSet newArray = new PointSet();
        for (Point p : points) {
            if (p != null && other.contains(p)) {
                newArray.add(p);
            }
        }
        return newArray;
    }

    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof PointSet other)) {
            return false;
        }
        if (this.size() != other.size()) {
            return false;
        }
        for (int i = 0; i < this.size(); i++) {
            if (!other.contains(this.points[i])) {
                return false;
            }
        }
        return true;
    }

    public void remove(Point point) {
        int removeIndex = -1;
        for (int i = 0; i < index; i++) {
            if (point != null && points[i] != null &&
                    point.x == points[i].x && point.y == points[i].y) {
                removeIndex = i;
                break;
            }
        }

        if (removeIndex != -1) {
            for (int i = removeIndex; i < index - 1; i++) {
                points[i] = points[i + 1];
            }
            index--;
        }
    }

    @Override
    public int hashCode() {
        return 0; // no need to change this
    }
}
