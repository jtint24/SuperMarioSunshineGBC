import java.util.ArrayList;

public class Point {
    int x;
    int y;
    int z;
    int offsetX;
    int offsetY;
    int offsetZ;

    public Point(int x, int y, int z, int offsetX, int offsetY, int offsetZ) {
        this.x = x;
        this.y = y;
        this.z = z;
        this.offsetX = offsetX;
        this.offsetY = offsetY;
        this.offsetZ = offsetZ;
    }


    public Point(int x, int y, int z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }

    public ArrayList<Point> getClusteredPoints() {
        int[] xVals;
        int[] yVals;
        int[] zVals;

        if (offsetX > 0) {
            xVals = new int[]{0,1};
        } else if (offsetX < 0) {
            xVals = new int[]{0,-1};
        } else {
            xVals = new int[]{0};
        }

        if (offsetY > 0) {
            yVals = new int[]{0,1};
        } else if (offsetY < 0) {
            yVals = new int[]{0,-1};
        } else {
            yVals = new int[]{0};
        }
        if (offsetZ > 0) {
            zVals = new int[]{0,1};
        } else if (offsetZ < 0) {
            zVals = new int[]{0,-1};
        } else {
            zVals = new int[]{0};
        }
        ArrayList<Point> retList = new ArrayList<>();
        for (int xVal : xVals) {
            for (int yVal : yVals) {
                for (int zVal : zVals) {
                    retList.add(new Point(x+xVal, y+yVal, z+zVal));
                }
            }
        }
        return retList;
    }
}
