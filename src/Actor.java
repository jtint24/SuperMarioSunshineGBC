class Actor implements Renderable {

    Point location;

    public Actor(Point location) {
        this.location = location;
    }


    // higher means draw this one last
    // lower means draw this one first
    int drawLayer() {
        return location.z-location.y;
    }

    void updateOffsets() {
        int addToX = location.offsetX / 16;
        location.offsetX = location.offsetX % 16;
        int addToY = location.offsetY / 16;
        location.offsetY = location.offsetY % 16;
        int addToZ = location.offsetZ / 16;
        location.offsetZ = location.offsetZ % 16;

        location.x += addToX;
        location.y += addToY;
        location.z += addToZ;

        if (location.offsetZ < 0) {
            location.z -= 1;
            location.offsetZ += 16;
        }
    }

    @Override
    public void render(Point p, Canvas c) {

    }
}
