class Actor implements Renderable {

    int x = 0;
    int y = 0;
    int z = 0;

    int offsetX = 0;
    int offsetY = 0;
    int offsetZ = 0;

    // higher means draw this one last
    // lower means draw this one first
    int drawLayer() {
        return z-y;
    }

    void updateOffsets() {
        int addToX = offsetX / 16;
        offsetX = offsetX % 16;
        int addToY = offsetY / 16;
        offsetY = offsetY % 16;
        int addToZ = offsetZ / 16;
        offsetZ = offsetZ % 16;

        x += addToX;
        y += addToY;
        z += addToZ;
    }

    @Override
    public void render() {

    }
}
