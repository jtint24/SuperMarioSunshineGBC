public class Point {
    int x;
    int y;
    int z;
    int offsetX;
    int offsetY;

    public Point(int x, int y, int z, int offsetX, int offsetY, int offsetZ) {
        this.x = x;
        this.y = y;
        this.z = z;
        this.offsetX = offsetX;
        this.offsetY = offsetY;
        this.offsetZ = offsetZ;
    }

    int offsetZ;

    public Point(int x, int y, int z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }
}
