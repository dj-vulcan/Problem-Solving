package com.dj.problem;

public class LockAndKey {
    public static void main(String[] args) {
        int[][] key = {{1, 0, 0}, {0, 1, 0}, {0, 1, 1}};
        int[][] lock = {{1, 1, 0}, {1, 0, 0}, {0, 1, 1}};


        System.out.println(new LockAndKey().solution(key, lock));

        
    }

    public boolean solution(int[][] key, int[][] lock) {
        int N = lock.length;
        int M = key.length;

        for(int i = - M + 1; i < N; i++) {
            for(int j = - M + 1; j < N; j++) {
                int[][] rotated = rotate(key);
                for(int r = 0; r < 4; r++) {
                    rotated = rotate(rotated);
                    int[][] croped = crop(rotated, N, i, j);
                    if(isDone(croped, lock)) {
                        System.out.println("=== key ===");
                        print(rotated);
                        System.out.println("=== lock ===");
                        print(lock);                        
                        System.out.println("=== croped ===");
                        print(croped);         
                        System.out.println("dy: " + i + ", dx: " + j);               

                        return true;
                    }
                }
            }
        }
        return false;
    }

    public int[][] rotate(int[][] key) {
        int M = key.length;
        int[][] result = new int[M][M];

        for(int i = 0; i < M; i++) {
            for(int j = 0; j < M; j++) {
                int y = j;
                int x = -i + M - 1;
                result[y][x] = key[i][j];
            }
        }
        return result;
    }

    public int[][] crop(int[][] key, int N, int dy, int dx) {
        int[][] result = new int[N][N];
        int M = key.length;
        for(int i = 0; i < N; i++) {
            for(int j = 0; j < N; j++) {
                int y = i - dy;
                int x = j - dx;
                result[i][j] = (y >= 0 && y < M && x >= 0 && x < M) ? key[y][x] : 0;
            }
        }

        return result;
    }

    public boolean isDone(int[][] cropedKey, int[][] lock) {
        int N = lock.length;

        for(int i = 0; i < N; i++) {
            for(int j = 0; j < N; j++) {
                if(cropedKey[i][j] == 1 && lock[i][j] == 1 || cropedKey[i][j] == 0 && lock[i][j] == 0) {
                    return false;
                }
            }
        }
        return true;
    }

    public void print(int[][] arr) {
        for(int[] ar: arr) {
            for(int a: ar) {
                System.out.print(a);
            }
            System.out.println();
        }
    }
}