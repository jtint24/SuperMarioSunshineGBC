
class TileType {
    boolean isBackground;
    boolean isWalkable;
    TileImageFetcher imageFetcher;

    public TileType(boolean isBackground, TileImageFetcher imageFetcher) {
        this.isBackground = isBackground;
        this.imageFetcher = imageFetcher;
    }
}
