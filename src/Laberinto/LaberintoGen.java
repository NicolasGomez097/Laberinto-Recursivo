package Laberinto;

import java.util.Collections;
import java.util.Arrays;

/*
 * recursive backtracking algorithm
 * shamelessly borrowed from the ruby at
 * http://weblog.jamisbuck.org/2010/12/27/maze-generation-recursive-backtracking
 */
public class LaberintoGen {

    private final int w;
    private final int h;


    private final int[][] maze;

    public LaberintoGen(int w, int h) {
        this.w = w;
        this.h = h;
        maze = new int[this.w][this.h];
        generateMaze(0, 0);
    }

    public int getData(int x, int y) {
        return maze[x][y];
    }

    public int getW() {
        return w;
    }

    public int getH() {
        return h;
    }

    public void visitar(int x, int y) {
        maze[x][y] |= 0x10;
    }

    public boolean esVisitado(int x, int y) {
        return (maze[x][y] & 0x10) != 0;
    }
    
    public boolean hayParedD(int x, int y){
        if(x+1 >= w) return true;
        return (maze[x+1][y] & 8) == 0 ? true : false;
    }
    public boolean hayParedI(int x, int y){
        return (maze[x][y] & 8) == 0 ? true : false;
    }
    
    public boolean hayParedArriba(int x,int y){
        return (maze[x][y] & 1) == 0 ? true : false;
    }
    public boolean hayParedAbajo(int x,int y){
        if(y+1 >= h) return true;
        return (maze[x][y+1] & 1) == 0 ? true : false;
    }

    public void display() {
        for (int i = 0; i < h; i++) {
            // draw the north edge
            for (int j = 0; j < w; j++) {
                System.out.print((maze[j][i] & 1) == 0 ? "+---" : "+   ");
            }
            System.out.println("+");
            // draw the west edge
            for (int j = 0; j < w; j++) {
                System.out.print((maze[j][i] & 8) == 0 ? "|   " : "    ");
            }
            System.out.println("|");
        }
        // draw the bottom line
        for (int j = 0; j < w; j++) {
            System.out.print("+---");
        }
        System.out.println("+");
    }

    private void generateMaze(int cx, int cy) {
        DIR[] dirs = DIR.values();
        Collections.shuffle(Arrays.asList(dirs));
        for (DIR dir : dirs) {
            int nx = cx + dir.dx;
            int ny = cy + dir.dy;
            if (between(nx, w) && between(ny, h)
                    && (maze[nx][ny] == 0)) {
                maze[cx][cy] |= dir.bit;
                maze[nx][ny] |= dir.opposite.bit;
                generateMaze(nx, ny);
            }
        }
    }

    private static boolean between(int v, int upper) {
        return (v >= 0) && (v < upper);
    }

    private enum DIR {
        N(1, 0, -1), S(2, 0, 1), E(4, 1, 0), W(8, -1, 0);
        private final int bit;
        private final int dx;
        private final int dy;
        private DIR opposite;

        // use the static initializer to resolve forward references
        static {
            N.opposite = S;
            S.opposite = N;
            E.opposite = W;
            W.opposite = E;
        }

        private DIR(int bit, int dx, int dy) {
            this.bit = bit;
            this.dx = dx;
            this.dy = dy;
        }
    }
}