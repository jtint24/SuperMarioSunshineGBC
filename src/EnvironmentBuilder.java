public class EnvironmentBuilder {
    final TileType water = new TileType("water", true, false);
    final TileType cliff = new TileType("cliff",true, false);
    final TileType grass = new TileType("grass", true, false);

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
        public void makeCliff() {
            fillType(cliff);
        }
        public void makeGrass() {
            fillType(grass);
        }
        public void fillType(TileType t) {
            System.out.println("filling with "+t.name);
            for (int i = minX; i<maxX; i++) {
                for (int j = minY; j<maxY; j++) {
                    for (int k = minZ; k<maxZ; k++) {
                        System.out.println("making "+i+" "+j+" "+k+" a "+t.name);
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
                            case "water" -> () -> Images.water1;
                            case "cliff" -> {
                                if (e.tileBelowIs(i,j,k, cliff) && e.tileBelowIs(i,j,k,cliff)) {
                                    yield () -> Images.cliffM;
                                } else if (e.tileAboveIs(i,j,k,cliff)) {
                                    yield () -> Images.cliffB;
                                } else {
                                    yield () -> Images.cliffT;
                                }
                            }
                            default -> () -> Images.water1;
                        };
                    }
                }
            }
        }
    }
}
