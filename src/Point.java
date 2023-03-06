import java.util.ArrayList;

public class Point implements Cloneable {
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

    public int distanceToSQ(Point p) {
        int xDiff = (p.x-x)*16+(p.offsetX-offsetX);
        int yDiff = (p.y-y)*16+(p.offsetY-offsetY);
        int zDiff = (p.z-z)*16+(p.offsetZ-offsetZ);
        return xDiff*xDiff + yDiff*yDiff + zDiff*zDiff;

    }

    @Override
    public String toString() {
        return "("+x+":"+offsetX+", "+y+":"+offsetY+", "+z+":"+offsetZ+")";
    }

    public Object clone() {
        return new Point(x,y,z, offsetX,offsetY, offsetZ);
    }

    public static Point average(Point a, Point b) {
        int totalOffsetX = (a.x*16 + b.x*16 + b.offsetX + a.offsetX) / 2;
        int totalOffsetY = (a.y*16 + b.y*16 + b.offsetY + a.offsetY) / 2;
        int totalOffsetZ = (a.z*16 + b.z*16 + b.offsetZ + a.offsetZ) / 2;
        Point p = new Point(totalOffsetX / 16, totalOffsetY / 16, totalOffsetZ / 16, totalOffsetX % 16, totalOffsetY % 16, totalOffsetZ % 16);
        return p;
    }

    public Point randomize() {
        return new Point(x,y,z, (int)Math.round(offsetX+8*Math.random()-4)+8, (int)Math.round(offsetY+8*Math.random()-4), offsetZ);
    }
}
