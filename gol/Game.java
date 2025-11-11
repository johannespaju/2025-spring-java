package gol;

import java.util.HashSet;
import java.util.Set;

public class Game {

    private Set<String> aliveCells = new HashSet<>();

    public void markAlive(int x, int y) {
        aliveCells.add(x + "," + y);
    }

    public boolean isAlive(int x, int y) {
        return aliveCells.contains(x + "," + y);
    }

    public void toggle(int x, int y) {
        String key = x + "," + y;
        if (aliveCells.contains(key)) {
            aliveCells.remove(key);
        } else {
            aliveCells.add(key);
        }
    }

    public Integer getNeighbourCount(int x, int y) {
        int count = 0;
        // Kontrollime kõiki 8 naabrit (üleval, all, paremal, vasakul + diagonaalid)
        for (int dx = -1; dx <= 1; dx++) {
            for (int dy = -1; dy <= 1; dy++) {
                // Jätame vahele keskse raku (iseenda)
                if (dx == 0 && dy == 0) {
                    continue;
                }
                if (isAlive(x + dx, y + dy)) {
                    count++;
                }
            }
        }
        return count;
    }

    public void nextFrame() {
        Set<String> newAliveCells = new HashSet<>();
        Set<String> cellsToCheck = new HashSet<>();

        // Lisame kõik elavad rakud ja nende naabrid kontrollimiseks
        for (String cell : aliveCells) {
            String[] coords = cell.split(",");
            int x = Integer.parseInt(coords[0]);
            int y = Integer.parseInt(coords[1]);

            // Lisame raku enda
            cellsToCheck.add(cell);

            // Lisame kõik naabrid
            for (int dx = -1; dx <= 1; dx++) {
                for (int dy = -1; dy <= 1; dy++) {
                    cellsToCheck.add((x + dx) + "," + (y + dy));
                }
            }
        }

        // Kontrollime iga raku järgmist seisundit
        for (String cell : cellsToCheck) {
            String[] coords = cell.split(",");
            int x = Integer.parseInt(coords[0]);
            int y = Integer.parseInt(coords[1]);

            boolean currentlyAlive = isAlive(x, y);
            int neighborCount = getNeighbourCount(x, y);

            if (nextState(currentlyAlive, neighborCount)) {
                newAliveCells.add(cell);
            }
        }

        aliveCells = newAliveCells;
    }

    public void clear() {
        aliveCells.clear();
    }

    public boolean nextState(boolean isLiving, int neighborCount) {
        if (isLiving) {
            // Elav rakk jääb elama ainult 2 või 3 naabriga
            return neighborCount == 2 || neighborCount == 3;
        } else {
            // Surnud rakk elustub täpselt 3 naabriga
            return neighborCount == 3;
        }
    }
}
