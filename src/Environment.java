import java.util.ArrayList;

public class Environment {
    Tile[][][] tiles;
    int[][] highestZ;
    ArrayList<Actor> actors = new ArrayList<>();
    Player player;
    Canvas canvas;
    private final ArrayList<Actor> actorsToRemove = new ArrayList<>();
    private final ArrayList<Actor> actorsToAdd = new ArrayList<>();
    HUD hud;


    Environment(Player _player, Tile[][][] _tiles, ArrayList<Actor> _actors,  Canvas _canvas, HUD _hud) {
        tiles = _tiles;
        player = _player;
        canvas = _canvas;
        highestZ = new int[tiles.length][tiles[0].length];
        hud = _hud;

        if (_actors != null) {
            actors.addAll(_actors);
        }
        addActorWithShadow(player);

        for (Actor actor : actors) {
            actor.environment = this;
        }

    }

    Tile[] adjacentTiles(int x, int y, int z) {
        assert x>=0;
        assert y>=0;
        assert z>=0;
        int edges = 0;
        if (x==0 || x == tiles.length-1) {
            edges++;
        }
        if (y==0 || y == tiles[0].length-1) {
            edges++;
        }
        if (z==0 || z == tiles[0][0].length-1) {
            edges++;
        }
        Tile[] retTiles;
        switch (edges) {
            case 0 -> retTiles= new Tile[26];
            case 1 -> retTiles = new Tile[17];
            default -> retTiles = new Tile[7];
        }

        int tilesAdded = 0;
        for (int i = -1; i<2; i++) {
            for (int j = -1; j<2; j++) {
                for (int k = -1; k<2; k++) {
                    if (isValidX(x + i) && isValidY(y + j) && isValidZ(z + k) && (i!=0 || j!=0 || k!=0)) {
                        retTiles[tilesAdded++] = tiles[x + i][y + j][z + 1];
                    }
                }
            }
        }
        return retTiles;
    }
    boolean isValidX(int x) {
        return x>=0 && x < tiles.length;
    }
    boolean isValidY(int y) {
        return y>=0 && y < tiles[0].length;
    }
    boolean isValidZ(int z) {
        return z>=0 && z < tiles[0][0].length;
    }

    void renderColumn(int x, int y, int z) {
        int i = 0;
        while (isValidZ(z-i) && isValidY(y-i)) {
            Tile t = tiles[x][y-i][z-i];
            if (t != null) {
                if (t.type.isBackground) {
                    break;
                }
            }
            i++;
        }
        while (isValidZ(z-i) && isValidY(y-i)) {
            Tile t = tiles[x][y-i][z-i];
            if (t != null) {
                tiles[x][y - i][z - i].render(player.location, canvas);
            }
            i--;
        }
    }

    void sortActors() {
        // insertion sort of actors in order of what should be rendered first

        int n = actors.size();
        for (int i = 1; i < n; ++i) {
            Actor key = actors.get(i);
            int j = i - 1;
            while (j >= 0 && actors.get(j).drawLayer() > key.drawLayer()) {
                actors.set(j + 1, actors.get(j));
                j--;
            }
            actors.set(j + 1, key);
        }

    }

    boolean isUncoveredStrict(Point p) {
        int i = 0;
        while (isValidY(i+p.y) && isValidZ(p.z+i)) {
            if (tiles[p.x][i+p.y][i+p.z] != null) {
                return false;
            }
            i++;
        }
        return true;
    }

    boolean isUncovered(Point p) {
        ArrayList<Point> pointList = p.getClusteredPoints();

        // System.out.println(pointList.size());

        for (Point point : pointList) {
            if (!isUncoveredStrict(point)) {
                // System.out.println(point.x+" "+point.y+" "+ point.z);
                return false;
            }
        }

        return true;
    }

    void renderActors() {
        boolean playerCovered = false;
        for (Actor actor : actors) {
            if (isUncovered(actor.location)) {
                actor.render(player.location, canvas);
            } else {
                if (actor instanceof Player) {
                    playerCovered = true;
                }
            }
        }
        if (playerCovered) {
            player.render(player.location, canvas);
        }

    }

    void render() {
        canvas.clear();

        sortActors();

        int lowerX = Math.max(player.location.x-6, 0);
        int upperX = Math.min(player.location.x+6, tiles.length);
        int lowerY = Math.max(player.location.y-player.location.z-1, 0);
        int upperY = Math.min(player.location.y-player.location.z+20, tiles[0].length);


        for (int i = lowerX; i< upperX; i++) {
            for (int j = lowerY; j < upperY; j++) {
                // System.out.println("rendering column "+i+" "+j);
                renderColumn(i, j, tiles[0][0].length - 1);
            }
        }
        renderActors();

        canvas.repaint();
    }

    boolean tileAboveIs(int i, int j, int k, TileType t) {
        if (k<tiles.length-2) {
            Tile tile = tiles[i][j][k+1];
            if (tile == null) {
                return false;
            }
            return tile.type == t;
        } else {
            return false;
        }
    }

    boolean tileBelowIs(int i, int j, int k, TileType t) {
        if (k>0) {
            Tile tile = tiles[i][j][k-1];
            if (tile == null) {
                return false;
            }
            return tile.type == t;
        } else {
            return false;
        }
    }

    boolean tileFrontIs(int i, int j, int k, TileType t) {
        if (isValidY(j+1)) {
            Tile tile = tiles[i][j+1][k];
            if (tile == null) {
                return false;
            }
            return tile.type == t;
        } else {
            return false;
        }
    }

    boolean tileBehindIs(int i, int j, int k, TileType t) {
        if (isValidY(j-1)) {
            Tile tile = tiles[i][j-1][k];
            if (tile == null) {
                return false;
            }
            return tile.type == t;
        } else {
            return false;
        }
    }

    boolean tileLeftIs(int i, int j, int k, TileType t) {
        if (isValidX(i-1)) {
            Tile tile = tiles[i-1][j][k];
            if (tile == null) {
                return false;
            }
            return tile.type == t;
        } else {
            return false;
        }
    }

    boolean tileRightIs(int i, int j, int k, TileType t) {
        if (isValidX(i+1)) {
            Tile tile = tiles[i+1][j][k];
            if (tile == null) {
                return false;
            }
            return tile.type == t;
        } else {
            return false;
        }
    }

    ArrayList<Tile> tilesAroundYZ(Point p) {
        ArrayList<Tile> retList = new ArrayList<>();
        addIfPossible(retList, p.x, p.y, p.z);
        if (p.offsetY > 0) {
            addIfPossible(retList, p.x, p.y+1, p.z);
            if (p.offsetZ > 0) {
                addIfPossible(retList, p.x, p.y+1, p.z+1);
            } else if (p.offsetZ < 0) {
                addIfPossible(retList, p.x, p.y+1, p.z-1);
            }
        } else if (p.offsetY < 0) {
            addIfPossible(retList, p.x, p.y-1, p.z);
            if (p.offsetZ > 0) {
                addIfPossible(retList, p.x, p.y-1, p.z+1);
            } else if (p.offsetZ < 0) {
                addIfPossible(retList, p.x, p.y-1, p.z-1);
            }
        }
        if (p.offsetZ < 0) {
            addIfPossible(retList, p.x, p.y, p.z-1);
        } else {
            addIfPossible(retList, p.x, p.y, p.z+1);

        }
        return retList;
    }

    ArrayList<Tile> tilesAroundXZ(Point p) {
        ArrayList<Tile> retList = new ArrayList<>();
        addIfPossible(retList, p.x, p.y, p.z);
        if (p.offsetX > 0) {
            addIfPossible(retList, p.x+1, p.y, p.z);
            if (p.offsetZ > 0) {
                addIfPossible(retList, p.x+1, p.y, p.z+1);
            } else if (p.offsetZ < 0) {
                addIfPossible(retList, p.x-1, p.y, p.z-1);
            }
        } else if (p.offsetX < 0) {
            addIfPossible(retList, p.x-1, p.y, p.z);
            if (p.offsetZ > 0) {
                addIfPossible(retList, p.x-1, p.y, p.z+1);
            } else if (p.offsetZ < 0) {
                addIfPossible(retList, p.x-1, p.y, p.z-1);
            }
        }
        if (p.offsetZ < 0) {
            addIfPossible(retList, p.x, p.y, p.z-1);
        } else {
            addIfPossible(retList, p.x, p.y, p.z+1);
        }
        return retList;
    }

    ArrayList<Tile> tilesAroundXY(Point p) {
        ArrayList<Tile> retList = new ArrayList<>();
        addIfPossible(retList, p.x, p.y, p.z);
        if (p.offsetX > 0) {
            addIfPossible(retList, p.x+1, p.y, p.z);
            if (p.offsetY > 0) {
                addIfPossible(retList, p.x+1, p.y+1, p.z);
            } else if (p.offsetY < 0) {
                addIfPossible(retList, p.x+1, p.y-1, p.z);
            }
        } else if (p.offsetX < 0) {
            addIfPossible(retList, p.x-1, p.y, p.z);
            if (p.offsetY > 0) {
                addIfPossible(retList, p.x-1, p.y+1, p.z);
            } else if (p.offsetY < 0) {
                addIfPossible(retList, p.x-1, p.y-1, p.z);
            }
        }
        if (p.offsetY < 0) {
            addIfPossible(retList, p.x, p.y-1, p.z);
        } else {
            addIfPossible(retList, p.x, p.y+1, p.z);

        }
        return retList;
    }

    int highestZAt(Point p) {
        ArrayList<Integer> zValues = new ArrayList<>();
        zValues.add(highestZ[p.x][p.y]);
        if (p.offsetX > 0) {
            zValues.add(highestZ[p.x+1][p.y]);
            if (p.offsetY > 0) {
                zValues.add(highestZ[p.x + 1][p.y + 1]);
            } else if (p.offsetY < 0) {
                zValues.add(highestZ[p.x + 1][p.y - 1]);
            }
        } else {
            zValues.add(highestZ[p.x-1][p.y]);
            if (p.offsetY > 0) {
                zValues.add(highestZ[p.x - 1][p.y + 1]);
            } else if (p.offsetY < 0) {
                zValues.add(highestZ[p.x - 1][p.y - 1]);
            }
        }
        if (p.offsetY > 0) {
            zValues.add(highestZ[p.x][p.y + 1]);
        } else if (p.offsetY < 0) {
            zValues.add(highestZ[p.x][p.y - 1]);
        }

        int max = -1;
        for (int zValue : zValues) {
            if (zValue > max) {
                max = zValue;
            }
        }

        return max;
    }

    private void addIfPossible(ArrayList<Tile> a, int x, int y, int z) {
        if (isValidX(x) && isValidY(y) && isValidZ(z)) {
            if (tiles[x][y][z] != null) {
                a.add(tiles[x][y][z]);
            }
        }
    }

    public void runFrame() {
        for (Actor actor : actorsToRemove) {
            actors.remove(actor);
        }
        actorsToRemove.clear();

        actors.addAll(actorsToAdd);
        actorsToAdd.clear();

        for (Actor actor : actors) {
            actor.move();
        }
    }

    public void runLockedFrame() {
        for (Actor actor : actorsToRemove) {
            actors.remove(actor);
        }
        actorsToRemove.clear();

        actors.addAll(actorsToAdd);
        actorsToAdd.clear();

        for (Actor actor : actors) {
            if (actor instanceof Player) {
                actor.applyGravity();
            } else {
                actor.move();
            }
        }
    }

    public void deleteActor(Actor a) {
        actorsToRemove.add(a);
    }
    public void addActor(Actor a) {
        a.environment = this;
        actorsToAdd.add(a);
    }
    public void addActorWithShadow(Actor a) {
        a.environment = this;
        ActorLibrary.Shadow shadow = new ActorLibrary.Shadow(a, this);
        actorsToAdd.add(a);
        actorsToAdd.add(shadow);
    }

    public boolean hasActorType(Class c) {
        for (Actor a : actors) {
            if (a.getClass() == c) {
                return true;
            }
        }
        return false;
    }

    public ActorLibrary.Shine getShine() {
        for (Actor a : actors) {
            if (a instanceof ActorLibrary.Shine) {
                return (ActorLibrary.Shine)a;
            }
        }
        return null;
    }
}
