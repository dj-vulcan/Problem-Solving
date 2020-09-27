package com.dj.problem;

public class LeftHandMaze {
    public static void main(String[] args) {
        //int[][] maze = {{0, 1, 0, 1}, {0, 1, 0, 0}, {0, 0, 0, 0}, {1, 0, 1, 0}};
        int[][] maze = {{0, 1, 0, 0, 0, 0}, {0, 0, 1, 0, 0, 0}, {0, 0, 0, 1, 0, 0}, {0, 0, 0, 0, 1, 0}, {0, 0, 0, 0, 0, 0}, {1, 1, 1, 1, 1, 0}};
        System.out.println(new LeftHandMaze().solution(maze));

    }

    public int solution(int[][] maze) {
        int N = maze.length;
        Posi curPosi = new Posi(0, 0);
        Dir dir = Dir.RIGHT;
        Posi handPosi = new Posi(-1, 0);

        int time = 0;
        while(true) {
            if(curPosi.y == N-1 && curPosi.x == N-1) {
                return time;
            }


            Posi nextPosi = new Posi(curPosi.y + dir.dy, curPosi.x + dir.dx);
            Posi nextHandPosi = new Posi(handPosi.y + dir.dy, handPosi.x + dir.dx);
            boolean handValid = nextHandPosi.handValid(maze);
            boolean personValid = nextPosi.personValid(maze);
            //손 빈공간, 사람은 안막힘 valid -> 사람이 그쪽으로, 손그대로
            //손 빈공간, 사람 막힘 -> 오른쪽회전
            //손 벽 valid, 사람 막힘 -> 오른쪽회전
            //손 벽 valid, 사람은 안막힘 valid -> 진행

            if(!handValid && personValid) {
                System.out.println("turing round");
                curPosi = nextHandPosi;
                if(dir == Dir.RIGHT) {
                    dir = Dir.UP;
                } else if(dir == Dir.LEFT) {
                    dir = Dir.DOWN;
                } else if(dir == Dir.UP) {
                    dir = Dir.LEFT;
                } else {
                    dir = Dir.RIGHT;
                }

                time += 2;
            } else if(!personValid) {
                System.out.println("turn Right");
                if(dir == Dir.RIGHT) {
                    dir = Dir.DOWN;
                } else if(dir == Dir.LEFT) {
                    dir = Dir.UP;
                } else if(dir == Dir.UP) {
                    dir = Dir.RIGHT;
                } else {
                    dir = Dir.LEFT;
                }
                handPosi = nextPosi;
            } else {
                System.out.println("Go");
                curPosi = nextPosi;
                handPosi = nextHandPosi;
                time++;
            }
        }
    }

    public enum Dir {
        LEFT(0, -1),
        RIGHT(0, 1),
        UP(-1, 0),
        DOWN(1, 0)
        ;

        int dy;
        int dx;

        Dir(int dy, int dx) {
            this.dy = dy;
            this.dx = dx;
        }
    }

    public static class Posi {
        int y;
        int x;

        public Posi(int y, int x) {
            this.y = y;
            this.x = x;
        }

        public boolean handValid(int[][] maze) {
            int N = maze.length;

            if(y < 0 || y >= N || x < 0 || x >= N) {
                return true;
            }

            return maze[y][x] == 1;
        }

        public boolean personValid(int[][] maze) {
            int N = maze.length;

            if(y < 0 || y >= N || x < 0 || x >= N) {
                return false;
            }

            return maze[y][x] == 0;
        }
    }
}