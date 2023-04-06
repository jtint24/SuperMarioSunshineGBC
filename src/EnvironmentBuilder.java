
public class EnvironmentBuilder {
    static final TileType water = new TileType("water", true, true);
    static final TileType cliff = new TileType("cliff",true, true);
    static final TileType grass = new TileType("grass", true, true);
    static final TileType path = new TileType("path", true, true);
    static final TileType bridge = new TileType("bridge", false, true);
    static final TileType grassEdge = new TileType("grassEdge", false, false);
    static final TileType wall = new TileType("wall", true, true);
    static final TileType roof = new TileType("roof", false, true);
    static final TileType awning = new TileType("awning", true, true);
    static final TileType window = new TileType("window", true, true);
    static final TileType door = new TileType("door", true, true);
    static final TileType sand = new TileType("sand", true, true);
    static final TileType beach = new TileType("beach", true, true);
    static final TileType lava = new TileType("lava", true, true);
    static final TileType platform = new TileType("platform", false, true);
    static final TileType pole = new TileType("pole", true, true);
    static final TileType fountain = new TileType("fountain", false, true);
    static final TileType spout = new TileType("spout", true, true);
    static final TileType poison = new TileType("poison", true, true);
    static final TileType gaddBox = new TileType("gaddBox", false, true);



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

        public void placeCoins() {
            for (int i = minX; i<maxX; i++) {
                for (int j = minY; j<maxY; j++) {
                    for (int k = minZ; k<maxZ; k++) {
                        e.addActorWithShadow(new ActorLibrary.Coin(new Point(i,j,k), e));
                    }
                }
            }
        }

        public void placeCoinArch() {
            e.addActorWithShadow(new ActorLibrary.Coin(new Point(minX,minY,minZ), e));
            e.addActorWithShadow(new ActorLibrary.Coin(new Point(minX+1,minY,minZ+2), e));
            e.addActorWithShadow(new ActorLibrary.Coin(new Point(minX+2,minY,minZ+2), e));
            e.addActorWithShadow(new ActorLibrary.Coin(new Point(minX+3,minY,minZ), e));
        }

        public void placeShadowDelicateCoinArch() {
            e.addActor(new ActorLibrary.Coin(new Point(minX,minY,minZ), e));
            e.addActorWithShadow(new ActorLibrary.Coin(new Point(minX+1,minY,minZ+2), e));
            e.addActorWithShadow(new ActorLibrary.Coin(new Point(minX+2,minY,minZ+2), e));
            e.addActor(new ActorLibrary.Coin(new Point(minX+3,minY,minZ), e));
        }

        public void placeGoop() {
            for (int i = minX; i<maxX; i++) {
                for (int j = minY; j<maxY; j++) {
                    for (int k = minZ; k<maxZ; k++) {
                        e.addActor(new ActorLibrary.Goop(new Point(i,j,k),e));
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

        public void makeFountain() {
            e.tiles[minX][minY][maxZ-1] = new Tile(new Point(minX,minY, maxZ-1), fountain, null);
            e.tiles[minX][minY][maxZ-2] = new Tile(new Point(minX,minY, maxZ-2), fountain, null);
        }
        public void makeSpout() {
            e.tiles[minX][minY][maxZ] = new Tile(new Point(minX,minY, maxZ), spout, null);
            e.tiles[minX+1][minY][maxZ] = new Tile(new Point(minX+1,minY, maxZ), spout, null);
        }

        public void makePlatform() {
            int midX = (maxX+minX)/2;
            int midY = (maxY+minY)/2;
            for (int i = midX-1; i<midX+1; i++) {
                for (int j = midY-1; j<midY+1; j++) {
                    for (int k = minZ; k<maxZ; k++) {
                        e.tiles[i][j][k] = new Tile(new Point(i,j,k), pole, null);
                    }
                }
            }

            for (int i = minX; i<maxX; i++) {
                for (int j = minY; j<maxY; j++) {
                    for (int k = maxZ-2; k<maxZ; k++) {
                        e.tiles[i][j][k] = new Tile(new Point(i,j,k), platform, null);
                    }
                }
            }
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
                            case "water" -> () -> Images.getImage("water"+Application.frameNumber(320,6));
                            case "poison" -> () -> Images.getImage("poison"+Application.frameNumber(320,4));
                            case "grass" -> {
                                String coordinates = ""+(i+1)*(j+1)+""+(j+1)*(k+1)+""+(k+1)*(i+1);
                                if (coordinates.hashCode() % 10 == 0) {
                                    yield () -> Images.getImage("grassDetail");
                                } else if (coordinates.hashCode() % 50 == 1) {
                                    yield () -> Images.getImage("flowerTile");
                                } else {
                                    yield () -> Images.getImage("grass");
                                }
                            }
                            case "fountain" -> {
                                if (e.tileBelowIs(i,j,k, fountain)) {
                                    yield () -> Images.getImage("fountainT"+Application.frameNumber(160, 2));
                                } else {
                                    yield () -> Images.getImage("fountainB"+Application.frameNumber(160, 2));
                                }
                            }
                            case "lava" -> () -> Images.getImage("lava"+Application.frameNumber(640, 4));
                            case "sand" -> {
                                String coordinates = ""+(i+1)*(j+1)+""+(j+1)*(k+1)+""+(k+1)*(i+1);
                                if (coordinates.hashCode() % 10 == 0) {
                                    yield () -> Images.getImage("sandDetail");
                                } else {
                                    yield () -> Images.getImage("sand");
                                }
                            }
                            case "beach" -> () -> Images.getImage("beach"+Application.frameNumber(320, 4));
                            case "bridge" ->{
                                if (e.tileRightIs(i,j,k, bridge) && e.tileLeftIs(i,j,k,bridge)) {
                                    yield () -> Images.getImage("bridgeM");
                                } else if (e.tileLeftIs(i,j,k, bridge)) {
                                    yield () -> Images.getImage("bridgeR");
                                } else {
                                    yield () -> Images.getImage("bridgeL");
                                }
                            }
                            case "pole" -> {
                                if (e.tileBelowIs(i,j,k,pole)) {
                                    if (e.tileRightIs(i, j, k, pole)) {
                                        yield () -> Images.getImage("poleL");
                                    } else {
                                        yield () -> Images.getImage("poleR");
                                    }
                                } else {
                                    if (e.tileRightIs(i, j, k, pole)) {
                                        yield () -> Images.getImage("poleBL");
                                    } else {
                                        yield () -> Images.getImage("poleBR");
                                    }
                                }
                            }
                            case "platform" -> {
                                if (e.tileAboveIs(i,j,k,platform)) {
                                    if (e.tileLeftIs(i,j,k,platform) && e.tileRightIs(i,j,k,platform)) {
                                        yield () -> Images.getImage("platformBM");
                                    } else if (e.tileLeftIs(i,j,k,platform)) {
                                        yield () -> Images.getImage("platformBR");
                                    } else {
                                        yield () -> Images.getImage("platformBL");
                                    }
                                } else {
                                    if (e.tileRightIs(i,j,k,platform) && e.tileLeftIs(i,j,k,platform) && e.tileFrontIs(i,j,k,platform) && e.tileBehindIs(i,j,k,platform)) {
                                        yield () -> Images.getImage("platformTMM");
                                    }  else if (!e.tileRightIs(i,j,k,platform) && e.tileLeftIs(i,j,k,platform) && e.tileFrontIs(i,j,k,platform) && e.tileBehindIs(i,j,k,platform)) {
                                        yield () -> Images.getImage("platformTMR");
                                    } else if (e.tileRightIs(i,j,k,platform) && !e.tileLeftIs(i,j,k,platform) && e.tileFrontIs(i,j,k,platform) && e.tileBehindIs(i,j,k,platform)) {
                                        yield () -> Images.getImage("platformTML");
                                    } else if (e.tileRightIs(i,j,k,platform) && e.tileLeftIs(i,j,k,platform) && !e.tileFrontIs(i,j,k,platform) && e.tileBehindIs(i,j,k,platform)) {
                                        yield () -> Images.getImage("platformTBM");
                                    } else if (e.tileRightIs(i,j,k,platform) && e.tileLeftIs(i,j,k,platform) && e.tileFrontIs(i,j,k,platform) && !e.tileBehindIs(i,j,k,platform)) {
                                        yield () -> Images.getImage("platformTTM");
                                    } else if (e.tileFrontIs(i,j,k,platform) && e.tileLeftIs(i,j,k,platform)) {
                                        yield () -> Images.getImage("platformTTR");
                                    } else if (e.tileBehindIs(i,j,k,platform) && e.tileLeftIs(i,j,k,platform)) {
                                        yield () -> Images.getImage("platformTBR");
                                    } else if (e.tileFrontIs(i,j,k,platform) && e.tileRightIs(i,j,k,platform)) {
                                        yield () -> Images.getImage("platformTTL");
                                    } else if (e.tileBehindIs(i,j,k,platform) && e.tileRightIs(i,j,k,platform)) {
                                        yield () -> Images.getImage("platformTBL");
                                    } else {
                                        yield () -> Images.getImage("platformTMM");
                                    }
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
                                if (!(e.tileAboveIs(i, j, k, roof) || e.tileAboveIs(i, j, k, wall) || e.tileAboveIs(i, j, k, window) ||  e.tileAboveIs(i, j, k, awning) || e.tileAboveIs(i,j,k,door) || e.tileAboveIs(i,j,k,spout))) {
                                    if (e.tileFrontIs(i,j,k, wall) && e.tileLeftIs(i,j,k,wall) && e.tileRightIs(i,j,k,wall) && e.tileBehindIs(i,j,k, wall) ) {
                                        yield () -> Images.getImage("wallTopMM");
                                    } else if (!e.tileFrontIs(i,j,k, wall) && e.tileLeftIs(i,j,k,wall) && e.tileRightIs(i,j,k,wall) && e.tileBehindIs(i,j,k, wall) ) {
                                        yield () -> Images.getImage("wallTopBM");
                                    } else if (e.tileFrontIs(i,j,k, wall) && e.tileLeftIs(i,j,k,wall) && e.tileRightIs(i,j,k,wall) && !e.tileBehindIs(i,j,k, wall) ) {
                                        yield () -> Images.getImage("wallTopTM");
                                    } else if (e.tileFrontIs(i,j,k, wall) && !e.tileLeftIs(i,j,k,wall) && e.tileRightIs(i,j,k,wall) && e.tileBehindIs(i,j,k, wall) ) {
                                        yield () -> Images.getImage("wallTopML");
                                    } else if (e.tileFrontIs(i,j,k, wall) && e.tileLeftIs(i,j,k,wall) && !e.tileRightIs(i,j,k,wall) && e.tileBehindIs(i,j,k, wall) ) {
                                        yield () -> Images.getImage("wallTopMR");
                                    } else if (e.tileFrontIs(i,j,k, wall)  && e.tileRightIs(i,j,k,wall)) {
                                        yield () -> Images.getImage("wallTopTL");
                                    } else if (e.tileBehindIs(i,j,k, wall)  && e.tileRightIs(i,j,k,wall)) {
                                        yield () -> Images.getImage("wallTopBL");
                                    } else if (e.tileFrontIs(i,j,k, wall)  && e.tileLeftIs(i,j,k,wall)) {
                                        yield () -> Images.getImage("wallTopTR");
                                    } else if (e.tileBehindIs(i,j,k, wall)  && e.tileLeftIs(i,j,k,wall)) {
                                        yield () -> Images.getImage("wallTopBR");
                                    }
                                }


                                if ((e.tileBelowIs(i, j, k, wall) || e.tileBelowIs(i, j, k, window) ||  e.tileBelowIs(i, j, k, awning) || e.tileBelowIs(i,j,k,door) || e.tileBelowIs(i,j,k,spout))) {
                                    if ((e.tileRightIs(i, j, k, wall) || e.tileRightIs(i, j, k, window) || e.tileRightIs(i,j,k,door)) && (e.tileLeftIs(i, j, k, wall) || e.tileLeftIs(i, j, k, window) || e.tileLeftIs(i,j,k,door) || e.tileLeftIs(i,j,k,spout))) {
                                        yield () -> Images.getImage("wallMM");
                                    } else if ((e.tileRightIs(i, j, k, wall) || e.tileRightIs(i, j, k, window) || e.tileRightIs(i,j,k,door))) {
                                        yield () -> Images.getImage("wallML");
                                    } else {
                                        yield () -> Images.getImage("wallMR");
                                    }
                                } else {
                                    if ((e.tileRightIs(i, j, k, wall) || e.tileRightIs(i, j, k, window) || e.tileRightIs(i,j,k,door)) && (e.tileLeftIs(i, j, k, wall) || e.tileLeftIs(i, j, k, window) || e.tileLeftIs(i,j,k,door))) {
                                        yield () -> Images.getImage("wallBM");
                                    } else if ((e.tileRightIs(i, j, k, wall) || e.tileRightIs(i, j, k, window) || e.tileRightIs(i,j,k,door) || e.tileRightIs(i,j,k,spout))) {
                                        yield () -> Images.getImage("wallBL");
                                    } else {
                                        yield () -> Images.getImage("wallBR");
                                    }
                                }
                            }
                            case "window" -> () -> Images.getImage("window");
                            case "spout" -> {
                                if (e.tileRightIs(i,j,k, spout)) {
                                    yield () -> Images.getImage("spoutL"+Application.frameNumber(160,2));
                                } else {
                                    yield () -> Images.getImage("spoutR"+Application.frameNumber(160,2));
                                }
                            }
                            case "gaddBox"-> () -> Images.getImage("gaddBox");
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
