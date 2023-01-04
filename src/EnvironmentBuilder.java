import java.util.Arrays;

public class EnvironmentBuilder {
    final TileType water = new TileType("water", true, true);
    final TileType cliff = new TileType("cliff",true, true);
    final TileType grass = new TileType("grass", true, true);
    final TileType path = new TileType("path", true, true);
    final TileType bridge = new TileType("bridge", false, true);
    final TileType grassEdge = new TileType("grassEdge", false, false);
    final TileType wall = new TileType("wall", true, true);
    final TileType roof = new TileType("roof", false, true);
    final TileType awning = new TileType("awning", true, true);
    final TileType window = new TileType("window", true, true);
    final TileType door = new TileType("door", true, true);



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

        public void makeHouse() {
            fillType(wall);
            for (int i = minX; i<maxX; i++) {
                for (int j = minY; j<maxY; j++) {
                    e.tiles[i][j][maxZ-1] = new Tile(new Point(i,j,maxZ-1), roof, null);
                }
            }
            int midZ = (minZ+maxZ-1)/2;
            for (int i = minX; i<maxX; i++) {
                for (int j = minY; j<maxY; j++) {
                    e.tiles[i][j][midZ] = new Tile(new Point(i,j,midZ), awning, null);
                }
            }
            for (int i = minX+1; i<maxX; i+=2) {
                for (int j = minY; j<maxY; j++) {
                    e.tiles[i][j][midZ+1] = new Tile(new Point(i,j,midZ+1), window, null);
                }
            }
            int midX = (minX+maxX)/2;
            e.tiles[midX][maxY-1][minZ].type = door;
        }

        public void finalizeArea() {
            setImages();
            for (int i = minX; i<maxX; i++) {
                for (int j = minY; j < maxY; j++) {
                    for (int k = maxZ - 1; k > minZ - 1; k--) {
                        Tile t = e.tiles[i][j][k];
                        if (t != null) {
                            if (t.type.isWalkable) {
                                e.highestZ[i][j] = k;
                                break;
                            }
                        }
                    }
                }
            }
        }

        private void setImages() {
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
                            case "bridge" ->{
                                if (e.tileRightIs(i,j,k, bridge) && e.tileLeftIs(i,j,k,bridge)) {
                                    yield () -> Images.getImage("bridgeM");
                                } else if (e.tileLeftIs(i,j,k, bridge)) {
                                    yield () -> Images.getImage("bridgeR");
                                } else {
                                    yield () -> Images.getImage("bridgeL");
                                }
                            }
                            case "cliff" -> {
                                if (e.tileBelowIs(i,j,k, cliff) && e.tileAboveIs(i,j,k,cliff)) {
                                    yield () -> Images.getImage("cliffM");
                                } else if (e.tileAboveIs(i,j,k,cliff)) {
                                    yield () -> Images.getImage("cliffB");
                                } else if (e.tileBelowIs(i,j,k,cliff)) {
                                    yield () -> Images.getImage("cliffT");
                                } else {
                                    yield () -> Images.getImage("cliffS");
                                }
                            }
                            case "grassEdge" -> () -> Images.getImage("grassEdge");
                            case "path" -> {
                                if (e.tileFrontIs(i,j,k, path) && e.tileBehindIs(i,j,k,path) && e.tileFrontIs(i-1,j,k, path) && e.tileBehindIs(i-1,j,k,path) && e.tileFrontIs(i+1,j,k, path) && e.tileBehindIs(i+1,j,k,path) &&  e.tileLeftIs(i,j,k,path) && e.tileRightIs(i,j,k,path)) {
                                    yield () -> Images.getImage("pathMM");
                                } else if (e.tileFrontIs(i,j,k, path) && e.tileBehindIs(i,j,k,path) && !e.tileFrontIs(i-1,j,k, path) && e.tileBehindIs(i-1,j,k,path) && e.tileFrontIs(i+1,j,k, path) && e.tileBehindIs(i+1,j,k,path) &&  e.tileLeftIs(i,j,k,path) && e.tileRightIs(i,j,k,path)) {
                                    yield () -> Images.getImage("lpathBL");
                                } else if (e.tileFrontIs(i,j,k, path) && e.tileBehindIs(i,j,k,path) && e.tileFrontIs(i-1,j,k, path) && !e.tileBehindIs(i-1,j,k,path) && e.tileFrontIs(i+1,j,k, path) && e.tileBehindIs(i+1,j,k,path) &&  e.tileLeftIs(i,j,k,path) && e.tileRightIs(i,j,k,path)) {
                                    yield () -> Images.getImage("lpathTL");
                                } else if (e.tileFrontIs(i,j,k, path) && e.tileBehindIs(i,j,k,path) && e.tileFrontIs(i-1,j,k, path) && e.tileBehindIs(i-1,j,k,path) && !e.tileFrontIs(i+1,j,k, path) && e.tileBehindIs(i+1,j,k,path) &&  e.tileLeftIs(i,j,k,path) && e.tileRightIs(i,j,k,path)) {
                                    yield () -> Images.getImage("lpathBR");
                                } else if (e.tileFrontIs(i,j,k, path) && e.tileBehindIs(i,j,k,path) && e.tileFrontIs(i-1,j,k, path) && e.tileBehindIs(i-1,j,k,path) && e.tileFrontIs(i+1,j,k, path) && !e.tileBehindIs(i+1,j,k,path) &&  e.tileLeftIs(i,j,k,path) && e.tileRightIs(i,j,k,path)) {
                                    yield () -> Images.getImage("lpathTR");
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
                            case "door" -> () -> Images.getImage("door");
                            case "wall" -> {
                                if ((e.tileBelowIs(i, j, k, wall) || e.tileBelowIs(i, j, k, window) ||  e.tileBelowIs(i, j, k, awning) || e.tileBelowIs(i,j,k,door))) {
                                    if ((e.tileRightIs(i, j, k, wall) || e.tileRightIs(i, j, k, window) || e.tileRightIs(i,j,k,door)) && (e.tileLeftIs(i, j, k, wall) || e.tileLeftIs(i, j, k, window) || e.tileLeftIs(i,j,k,door))) {
                                        yield () -> Images.getImage("wallMM");
                                    } else if ((e.tileRightIs(i, j, k, wall) || e.tileRightIs(i, j, k, window) || e.tileRightIs(i,j,k,door))) {
                                        yield () -> Images.getImage("wallML");
                                    } else {
                                        yield () -> Images.getImage("wallMR");
                                    }
                                } else {
                                    if ((e.tileRightIs(i, j, k, wall) || e.tileRightIs(i, j, k, window) || e.tileRightIs(i,j,k,door)) && (e.tileLeftIs(i, j, k, wall) || e.tileLeftIs(i, j, k, window) || e.tileLeftIs(i,j,k,door))) {
                                        yield () -> Images.getImage("wallBM");
                                    } else if ((e.tileRightIs(i, j, k, wall) || e.tileRightIs(i, j, k, window) || e.tileRightIs(i,j,k,door))) {
                                        yield () -> Images.getImage("wallBL");
                                    } else {
                                        yield () -> Images.getImage("wallBR");
                                    }
                                }
                            }
                            case "window" -> () -> Images.getImage("window");
                            case "awning" -> {
                                if (e.tileRightIs(i,j,k, awning) && e.tileLeftIs(i,j,k,awning)) {
                                    yield () -> Images.getImage("awningM");
                                } else if (e.tileRightIs(i,j,k, awning)) {
                                    yield () -> Images.getImage("awningL");
                                } else {
                                    yield () -> Images.getImage("awningR");
                                }
                            }
                            case "roof" -> {
                                if (e.tileRightIs(i,j,k,roof) && e.tileLeftIs(i,j,k,roof) && e.tileFrontIs(i,j,k,roof) && e.tileBehindIs(i,j,k,roof)) {
                                    yield () -> Images.getImage("roofMM");
                                }  else if (!e.tileRightIs(i,j,k,roof) && e.tileLeftIs(i,j,k,roof) && e.tileFrontIs(i,j,k,roof) && e.tileBehindIs(i,j,k,roof)) {
                                    yield () -> Images.getImage("roofMR");
                                } else if (e.tileRightIs(i,j,k,roof) && !e.tileLeftIs(i,j,k,roof) && e.tileFrontIs(i,j,k,roof) && e.tileBehindIs(i,j,k,roof)) {
                                    yield () -> Images.getImage("roofML");
                                } else if (e.tileRightIs(i,j,k,roof) && e.tileLeftIs(i,j,k,roof) && !e.tileFrontIs(i,j,k,roof) && e.tileBehindIs(i,j,k,roof)) {
                                    yield () -> Images.getImage("roofBM");
                                } else if (e.tileRightIs(i,j,k,roof) && e.tileLeftIs(i,j,k,roof) && e.tileFrontIs(i,j,k,roof) && !e.tileBehindIs(i,j,k,roof)) {
                                    yield () -> Images.getImage("roofTM");
                                } else if (e.tileFrontIs(i,j,k,roof) && e.tileLeftIs(i,j,k,roof)) {
                                    yield () -> Images.getImage("roofTR");
                                } else if (e.tileBehindIs(i,j,k,roof) && e.tileLeftIs(i,j,k,roof)) {
                                    yield () -> Images.getImage("roofBR");
                                } else if (e.tileFrontIs(i,j,k,roof) && e.tileRightIs(i,j,k,roof)) {
                                    yield () -> Images.getImage("roofTL");
                                } else if (e.tileBehindIs(i,j,k,roof) && e.tileRightIs(i,j,k,roof)) {
                                    yield () -> Images.getImage("roofBL");
                                } else {
                                    yield () -> Images.getImage("roofMM");
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
