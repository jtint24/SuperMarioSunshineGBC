import java.beans.beancontext.BeanContextServiceAvailableEvent;

public class Environment {
    Tile[][][] tiles;
    Actor[] actors;
    Player player;
    Canvas canvas;

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
        while (isValidZ(z-i) && isValidY(y+i)) {
            Tile t = tiles[x][y+i][z-i];
            if (t != null) {
                if (t.type.isBackground) {
                    return;
                }
            }
            i++;
        }
        do {
            i--;
            Tile t = tiles[x][y+i][z-i];
            if ( t != null) {
                tiles[x][y + i][z + i].render(player.location, canvas);
            }
        } while (isValidZ(z-i) && isValidY(y+i));
    }

    void sortActors() {
        // insertion sort of actors in order of what should be rendered firs
        //

        int n = actors.length;
        for (int i = 1; i < n; ++i) {
            Actor key = actors[i];
            int j = i - 1;
            while (j >= 0 && actors[j].drawLayer() > key.drawLayer()) {
                actors[j + 1] = actors[j];
                j = j - 1;
            }
            actors[j + 1] = key;
        }
    }

    boolean isUncovered(Point p) {
        int i = 0;
        while (isValidY(i+p.y) && isValidZ(p.z-i)) {
            if (tiles[p.x][i+p.y][i+p.z].type.isBackground) {
                return false;
            }
        }
        return true;
    }

    void renderActors() {
        for (Actor actor : actors) {
            if (isUncovered(player.location)) {
                actor.render(player.location, canvas);
            }
        }
    }




}
