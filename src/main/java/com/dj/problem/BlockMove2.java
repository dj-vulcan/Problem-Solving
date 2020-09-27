package com.dj.problem;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Queue;
import java.util.Set;

public class BlockMove2 {
    static int cnt = 0;
    public static void main(String[] args) {
        // int[][] board = {{0, 0, 0, 1, 1},{0, 0, 0, 1, 0},{0, 1, 0, 1, 1},{1, 1, 0, 0,
        // 1},{0, 0, 0, 0, 0}};
        // int[][] board = {
        // {0, 0, 0, 0, 0},
        // {0, 0, 1, 0, 0},
        // {0, 3, 3, 0, 0},
        // {0, 1, 0, 0, 0},
        // {0, 0, 0, 0, 0}
        // };
        int[][] board = new int[100][100];
        for (int i = 0; i < 98; i++) {
            board[1][i] = 1;
        }

        for (int i = 1; i < 100; i++) {
            board[i][97] = 1;
        }
        // board[1][97] = 0;

        int n = 100;
        int[][] board2 = new int[n][n];
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                if (i == 0) {
                    board2[i][j] = 0;
                } else if (j >= n - 2) {
                    board2[i][j] = 0;
                } else {
                    board2[i][j] = 0;
                }
            }
        }
        // board2[1][n - 2] = 0;
        System.out.println(new BlockMove2().solution(board2));
        System.out.println(cnt);

    }

    public int solution(int[][] board) {
        Set<Posi> visited = new HashSet<>();
        Posi curPosi = new Posi(0, 0, 0, 1);

        Queue<Posi> queue = new ArrayDeque<>();
        queue.offer(curPosi);
        int curTime = 0;
        while (!queue.isEmpty()) {
            int quSize = queue.size();
            //System.out.println(curTime + " " + quSize);
            for (int sameLevelIdx = 0; sameLevelIdx < quSize; sameLevelIdx++) {
                Posi cur = queue.poll();
                visited.add(cur);
                if (endCheck(cur, board.length)) {
                    return curTime;
                }

                List<Posi> children = possiblePlace(cur, board, visited);
                children.stream().forEach(queue::offer);
                children.stream().forEach(visited::add);
            }
            curTime++;
        }
        return -1;
    }

    public boolean endCheck(Posi curPosi, int n) {
        return curPosi.get(0) == n - 1 && curPosi.get(1) == n - 1 || curPosi.get(2) == n - 1 && curPosi.get(3) == n - 1;
    }

    public List<Posi> possiblePlace(Posi curPosi, int[][] board, Set<Posi> visited) {
        List<Posi> result = new ArrayList<>();

        for (Dir dir : Dir.values()) {
            Posi item = null;
            item = curPosi.getMove(board, visited, dir);
            if (item != null) {
                result.add(item);
            }
        }

        for (Rot rot : Rot.values()) {
            Posi item = curPosi.getRot(board, visited, rot);
            if (item != null) {
                result.add(item);
            }
        }
        return result;
    }

    public enum Dir {
        UP, DOWN, RIGHT, LEFT
    };

    public enum Rot {
        RIGHT_UP, RIGHT_DOWN, LEFT_UP, LEFT_DOWN
    };

    public static class Posi {
        // y좌표가 작고, x좌표가 작은 순으로 저장함
        int[] coordes;

        public Posi(int y1, int x1, int y2, int x2) {
            coordes = new int[] { y1, x1, y2, x2 };
        }

        public int get(int i) {
            return coordes[i];
        }

        public Posi getRot(int[][] board, Set<Posi> visited, Rot rot) {
            int firstY = this.get(0);
            int firstX = this.get(1);
            int secondY = this.get(2);
            int secondX = this.get(3);

            int toFirstY;
            int toFirstX;
            int toSecondY;
            int toSecondX;

            int[] pathPosi;

            if (firstX == secondX) {
                if (rot == Rot.RIGHT_UP) {
                    toFirstY = firstY;
                    toFirstX = firstX;
                    toSecondY = firstY;
                    toSecondX = firstX + 1;
                    pathPosi = new int[] { firstY + 1, firstX + 1 };
                } else if (rot == Rot.RIGHT_DOWN) {
                    toFirstY = secondY;
                    toFirstX = secondX;
                    toSecondY = secondY;
                    toSecondX = secondX + 1;
                    pathPosi = new int[] { firstY, secondX + 1 };
                } else if (rot == Rot.LEFT_UP) {
                    toFirstY = firstY;
                    toFirstX = firstX - 1;
                    toSecondY = firstY;
                    toSecondX = firstX;
                    pathPosi = new int[] { secondY, secondX - 1 };
                } else {
                    toFirstY = secondY;
                    toFirstX = secondX - 1;
                    toSecondY = secondY;
                    toSecondX = secondX;
                    pathPosi = new int[] { firstY, secondX - 1 };
                }
            } else {
                if (rot == Rot.RIGHT_UP) {
                    toFirstY = firstY - 1;
                    toFirstX = firstX;
                    toSecondY = firstY;
                    toSecondX = firstX;
                    pathPosi = new int[] { firstY - 1, secondX };
                } else if (rot == Rot.RIGHT_DOWN) {
                    toFirstY = firstY;
                    toFirstX = firstX;
                    toSecondY = firstY + 1;
                    toSecondX = firstX;
                    pathPosi = new int[] { firstY + 1, secondX };
                } else if (rot == Rot.LEFT_UP) {
                    toFirstY = secondY - 1;
                    toFirstX = secondX;
                    toSecondY = secondY;
                    toSecondX = secondX;
                    pathPosi = new int[] { firstY - 1, firstX };
                } else {
                    toFirstY = secondY;
                    toFirstX = secondX;
                    toSecondY = secondY + 1;
                    toSecondX = secondX;
                    pathPosi = new int[] { firstY + 1, firstX };
                }
            }

            Posi result = new Posi(toFirstY, toFirstX, toSecondY, toSecondX);

            if (pathPosi[0] >= 0 && pathPosi[0] < board.length &&
                    pathPosi[1] >= 0 && pathPosi[1] < board.length &&
                    board[pathPosi[0]][pathPosi[1]] == 1) {
                return null;
            }
            if (!result.isBounded(board.length) || result.isBlocked(board) || visited.contains(result)) {
                return null;
            }
            return result;

        }

        public Posi getMove(int[][] board, Set<Posi> visited, Dir dir) {

            int dy = 0;
            if (dir == Dir.UP) {
                dy = -1;
            } else if (dir == Dir.DOWN) {
                dy = 1;
            }

            int dx = 0;
            if (dir == Dir.RIGHT) {
                dx = 1;
            } else if (dir == Dir.LEFT) {
                dx = -1;
            }
            int toFirstY = this.get(0) + dy;
            int toFirstX = this.get(1) + dx;
            int toSecondY = this.get(2) + dy;
            int toSecondX = this.get(3) + dx;

            Posi result = new Posi(toFirstY, toFirstX, toSecondY, toSecondX);

            if (!result.isBounded(board.length) || result.isBlocked(board) || visited.contains(result)) {
                return null;
            }
            return result;
        }

        public boolean isBounded(int n) {
            for (int check : this.coordes) {
                if (check < 0 || check >= n) {
                    return false;
                }
            }
            return true;
        }

        public boolean isBlocked(int[][] board) {
            for (int i = 0; i < 2; i++) {
                if (board[this.get(i * 2)][this.get(i * 2 + 1)] == 1) {
                    return true;
                }
            }
            return false;
        }

        @Override
        public String toString() {
            String str = "(" + this.get(0) + ", " + this.get(1) + "), ";
            str += "(" + this.get(2) + ", " + this.get(3) + ")";
            return str;
        }

        @Override
        public int hashCode() {
            int hash = 0;
            for (int coord : this.coordes) {
                hash += hash * 31 + Integer.hashCode(coord);
            }
            return hash;
        }

        @Override
        public boolean equals(Object obj) {
            if(!(obj instanceof Posi)) {
                return false;
            }
            Posi other = (Posi) obj;
            for(int i = 0; i < 4; i++) {
                if(this.coordes[0] != other.coordes[0]) {
                    return false;
                }
            }
            return true;
        }
    }
}