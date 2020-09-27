package com.dj.problem;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class BlockMove {
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
            board[96][i] = 1;
        }

        System.out.println(new BlockMove().solution(board));

    }

    private static class Best {
        public int val = Integer.MAX_VALUE;
    }

    public int solution(int[][] board) {
        Set<Posi> visited = new HashSet<>();
        Best best = new Best();
        Posi curPosi = new Posi();
        curPosi.add(Arrays.asList(0, 0));
        curPosi.add(Arrays.asList(0, 1));
        visited.add(curPosi);

        recur(curPosi, 0, visited, board, best);

        return best.val;
    }

    public void recur(Posi curPosi, int curTime, Set<Posi> visited, int[][] board, Best best) {
        System.out.println(curPosi);
        
        // curPosiq가 목적지에 다았는지 -> min update
        if (endCheck(curPosi, board.length)) {
            if (curTime < best.val) {
                best.val = curTime;
            }
            return;
        }

        if (curTime >= best.val) {
            return;
        }

        // curPosi로 주변 board에 가능한 곳 마다
        List<Posi> positions = possiblePlace(curPosi, board, visited);
        for (Posi posi : positions) {
            // visited 추가
            visited.add(posi);
            // recur
            recur(posi, curTime + 1, visited, board, best);
            // visited 제거
            visited.remove(posi);
        }
    }

    public boolean endCheck(Posi curPosi, int n) {
        for (List<Integer> dot : curPosi) {
            if (dot.get(0) == n - 1 && dot.get(1) == n - 1) {
                return true;
            }
        }
        return false;
    }

    public List<Posi> possiblePlace(Posi curPosi, int[][] board, Set<Posi> visited) {
        List<Posi> result = new ArrayList<>();

        for (Dir dir : Dir.values()) {
            Posi item = null;
            try {
                item = curPosi.getMove(board, visited, dir);
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
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

    public static class Posi extends ArrayList<List<Integer>> {
        // y좌표가 작고, x좌표가 작은 순으로 저장함

        public Posi getRot(int[][] board, Set<Posi> visited, Rot rot) {
            List<Integer> first = this.get(0);
            List<Integer> second = this.get(1);
            int firstY = first.get(0);
            int firstX = first.get(1);
            int secondY = second.get(0);
            int secondX = second.get(1);

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

            Posi result = new Posi();
            result.add(Arrays.asList(toFirstY, toFirstX));
            result.add(Arrays.asList(toSecondY, toSecondX));

            if (pathPosi[0] >= 0 && pathPosi[0] < board.length && pathPosi[1] >= 0 && pathPosi[1] < board.length
                    && board[pathPosi[0]][pathPosi[1]] == 1) {
                return null;
            }
            if (!result.isBounded(board.length) || result.isBlocked(board) || visited.contains(result)) {
                return null;
            }
            return result;

        }

        public Posi getMove(int[][] board, Set<Posi> visited, Dir dir) {
            List<Integer> first = this.get(0);
            List<Integer> second = this.get(1);

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
            int toFirstY = first.get(0) + dy;
            int toFirstX = first.get(1) + dx;
            int toSecondY = second.get(0) + dy;
            int toSecondX = second.get(1) + dx;

            Posi result = new Posi();
            result.add(Arrays.asList(toFirstY, toFirstX));
            result.add(Arrays.asList(toSecondY, toSecondX));

            try {
                if (!result.isBounded(board.length) || result.isBlocked(board) || visited.contains(result)) {
                    return null;
                }
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
            return result;
        }

        public boolean isBounded(int n) {
            List<Integer> first = this.get(0);
            List<Integer> second = this.get(1);
            int[] checkList = new int[] { first.get(0), first.get(1), second.get(0), second.get(1) };
            for (int check : checkList) {
                if (check < 0 || check >= n) {
                    return false;
                }
            }
            return true;
        }

        public boolean isBlocked(int[][] board) {
            List<Integer> first = this.get(0);
            List<Integer> second = this.get(1);
            int[][] checkList = new int[][] { new int[] { first.get(0), first.get(1) },
                    new int[] { second.get(0), second.get(1) } };
            for (int[] check : checkList) {
                if (board[check[0]][check[1]] == 1) {
                    return true;
                }
            }
            return false;
        }

        @Override
        public String toString() {
            // TODO Auto-generated method stub
            String str = "(" + this.get(0).get(0) + ", " + this.get(0).get(1) + "), ";
            str += "(" + this.get(1).get(0) + ", " + this.get(1).get(1) + ")";
            return str;
        }

        
    }
}