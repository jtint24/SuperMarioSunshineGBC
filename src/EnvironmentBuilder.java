import java.util.Arrays;

public class EnvironmentBuilder {
    final TileType water = new TileType("water", true, false);
    final TileType cliff = new TileType("cliff",true, false);
    final TileType grass = new TileType("grass", true, true);
    final TileType path = new TileType("path", true, true);


    Environment e;


    EnvironmentBuilder(Environment e) {
        this.e = e;
    }

    Subarea getArea(int minx, int maxx, int miny, int maxy) {
        return new Subarea(e, minx, maxx, miny, maxy);
    }

    Subarea getArea(int minx, int maxx, int miny, int maxy, int minz, int maxz) {
        return new Subarea(e, minx, maxx, miny, maxy, minz, maxz);
    }

    Subarea getArea() {
        return new Subarea(e, 0, e.tiles.length, 0, e.tiles[0].length, 0, e.tiles[0][0].length);
    }

    Subarea getFloor() {
        return new Subarea(e, 0, e.tiles.length, 0, e.tiles[0].length);
    }

    class Subarea {
        Environment e;
        int minX;
        int maxX;
        int minY;
        int maxY;
        int minZ;
        int maxZ;

        Subarea(Environment e, int minX, int maxX, int minY, int maxY) {
            this.e = e;
            this.minX = minX;
            this.maxX = maxX;
            this.minY = minY;
            this.maxY = maxY;
            this.minZ = 0;
            this.maxZ = 1;
        }

        Subarea(Environment e, int minX, int maxX, int minY, int maxY, int minZ, int maxZ) {
            this.e = e;
            this.minX = minX;
            this.maxX = maxX;
            this.minY = minY;
            this.maxY = maxY;
            this.minZ = minZ;
            this.maxZ = maxZ;
        }

        public void makeWater() {
            fillType(water);
        }
        public void makePath() {
            fillType(path);
        }
        public void makeCliff() {
            fillType(cliff);
        }
        public void makeGrass() {
            fillType(grass);
        }
        public void fillType(TileType t) {
            for (int i = minX; i<maxX; i++) {
                for (int j = minY; j<maxY; j++) {
                    for (int k = minZ; k<maxZ; k++) {
                        e.tiles[i][j][k] = new Tile(new Point(i,j,k), t, null);
                    }
                }
            }
        }

        public void setImages() {
            for (int i = minX; i<maxX; i++) {
                for (int j = minY; j<maxY; j++) {
                    for (int k = minZ; k<maxZ; k++) {
                        Tile t = e.tiles[i][j][k];
                        if (t == null) {
                            continue;
                        }
                        t.imageFetcher = switch (t.type.name) {
                            case "water" -> () -> Images.getImage("water1");
                            case "grass" -> {
                                String coordinates = ""+(i+1)*(j+1)+""+(j+1)*(k+1)+""+(k+1)*(i+1);
                                if (coordinates.hashCode() % 10 == 0) {
                                    yield () -> Images.getImage("grassDetail");
                                } else {
                                    yield () -> Images.getImage("grass");
                                }
                            }
                            case "cliff" -> {
                                if (e.tileBelowIs(i,j,k, cliff) && e.tileAboveIs(i,j,k,cliff)) {
                                    yield () -> Images.getImage("cliffM");
                                } else if (e.tileAboveIs(i,j,k,cliff)) {
                                    yield () -> Images.getImage("cliffB");
                                } else {
                                    yield () -> Images.getImage("cliffT");
                                }
                            }
                            case "path" -> {
                                if (e.tileFrontIs(i,j,k, path) && e.tileBehindIs(i,j,k,path) && e.tileLeftIs(i,j,k,path) && e.tileRightIs(i,j,k,path)) {
                                    yield () -> Images.getImage("pathMM");
                                } else if (e.tileFrontIs(i,j,k, path) && e.tileBehindIs(i,j,k,path) && e.tileLeftIs(i,j,k,path)) {
                                    yield () -> Images.getImage("pathMR");
                                } else if (e.tileBehindIs(i,j,k,path) && e.tileLeftIs(i,j,k,path) && e.tileRightIs(i,j,k,path)) {
                                    yield () -> Images.getImage("pathBM");
                                } else if (e.tileFrontIs(i,j,k, path) && e.tileLeftIs(i,j,k,path) && e.tileRightIs(i,j,k,path)) {
                                    yield () -> Images.getImage("pathTM");
                                } else if (e.tileFrontIs(i,j,k, path) && e.tileBehindIs(i,j,k,path) && e.tileRightIs(i,j,k,path) ) {
                                    yield () -> Images.getImage("pathML");
                                } else if (e.tileFrontIs(i,j,k, path) && e.tileRightIs(i,j,k,path) ) {
                                    yield () -> Images.getImage("pathTL");
                                } else if (e.tileBehindIs(i,j,k, path) && e.tileRightIs(i,j,k,path) ) {
                                    yield () -> Images.getImage("pathBL");
                                } else if (e.tileBehindIs(i,j,k, path) && e.tileLeftIs(i,j,k,path) ) {
                                    yield () -> Images.getImage("pathBR");
                                } else if (e.tileFrontIs(i,j,k, path) && e.tileLeftIs(i,j,k,path) ) {
                                    yield () -> Images.getImage("pathTR");
                                } else {
                                    yield () -> Images.getImage("lpathTR");
                                }
                            }
                            default -> () -> Images.getImage("water1");
                        };
                    }
                }
            }
        }
    }
}
