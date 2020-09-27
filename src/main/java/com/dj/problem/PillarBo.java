package com.dj.problem;

import java.util.ArrayList;
import java.util.List;

public class PillarBo {
    public static void main(String[] args) {
        int[][] build2 = {{1,0,0,1},{1,1,1,1},{2,1,0,1},{2,2,1,1},{5,0,0,1},{5,1,0,1},{4,2,1,1},{3,2,1,1}};
        int[][] build_frame = {{0,0,0,1},{2,0,0,1},{4,0,0,1},{0,1,1,1},{1,1,1,1},{2,1,1,1},{3,1,1,1},{2,0,0,0},{1,1,1,0},{2,2,0,1}};
        new PillarBo().solution(5, build2);
        new PillarBo().solution(5, build_frame);

    }

    public int[][] solution(int n, int[][] build_frame) {
        n += 1;
        
        Struct[][] map = new Struct[n][n];
        for(int i = 0; i < n; i++) {
            for(int j = 0; j < n; j++) {
                map[i][j] = new Struct();
            }
        }

        for(int[] build: build_frame) {
            int y = build[1];
            int x = build[0];
            Type origin = map[y][x].type;
            Type type = build[2] == 0 ? Type.PILLAR : Type.BO;

            Type lastType = Type.NONE;

            if(build[3] == 0) {
                if(origin == Type.BOTH) {
                    if(type == Type.PILLAR) {
                        lastType = Type.BO;    
                    } else {
                        lastType = Type.PILLAR;
                    }
                } else {
                    if(origin != type) {
                        System.out.println("없는구조물 삭제");
                    }
                    lastType = Type.NONE;
                }
                System.out.print(type + ": 삭제 (" + x + ", " + y + ")");
            } else {
                if(origin != Type.NONE && origin != type) {
                    type = Type.BOTH;
                }
                lastType = type;
                System.out.print(lastType + ": 생성 (" + x + ", " + y + ")");
            }
            map[y][x].type = lastType;
            System.out.println(" " + origin + " -> " + lastType);
            if(!isRight(map)) {
                System.out.println("취소 : " + origin);
                map[y][x].type = origin;
            }
        }

        

        List<int[]> result = new ArrayList<>();
        for(int x = 0; x < n; x++) {
            for(int y = 0; y < n; y++) {
                if(map[y][x].type == Type.NONE) {
                    continue;
                }
                if(map[y][x].type == Type.PILLAR) {
                    result.add(new int[]{x, y, 0});
                } else if(map[y][x].type == Type.BO) {
                    result.add(new int[]{x, y, 1});
                } else if(map[y][x].type == Type.BOTH) {
                    result.add(new int[]{x, y, 0});
                    result.add(new int[]{x, y, 1});
                }
            }
        }

        int[][] answer = new int[result.size()][3];
        return result.toArray(answer);
    }

    public boolean isRight(Struct[][] map) {
        for(int i = 0; i < map.length; i++) {
            for(int j = 0; j < map[0].length; j++) {
                Struct cur = map[i][j];
                if(cur.type == Type.NONE) {
                    continue;
                }
                if(cur.type == Type.PILLAR) {
                    boolean isFloor = i == 0;
                    boolean isLeftBo = (j <= 0) ? false : map[i][j-1].type == Type.BO || map[i][j-1].type == Type.BOTH;
                    boolean isRightBo = false;
                    boolean isOnPillar = (i <= 0) ? false : map[i-1][j].type == Type.PILLAR || map[i-1][j].type == Type.BOTH;

                    if(!isFloor && !isLeftBo && !isRightBo && !isOnPillar) {
                        return false;
                    }
                } else if(cur.type == Type.BO) {
                    boolean isOnPillar = (i <= 0) ? false : map[i-1][j].type == Type.PILLAR || map[i-1][j].type == Type.BOTH;
                    if(j+1 < map[0].length) {
                        isOnPillar = isOnPillar || map[i-1][j+1].type == Type.PILLAR || map[i-1][j+1].type == Type.BOTH;
                    }
                    boolean isBothBo = (j <= 0) ? false : map[i][j-1].type == Type.BO || map[i][j-1].type == Type.BOTH;
                    isBothBo = isBothBo && ((j+1 >= map[0].length) ? false : map[i][j+1].type == Type.BO || map[i][j+1].type == Type.BOTH);

                    if(!isOnPillar && !isBothBo) {
                        return false;
                    }
                } else {
                    boolean isFloor = i == 0;
                    boolean isOnPillar = (i <= 0) ? false : map[i-1][j].type == Type.PILLAR || map[i-1][j].type == Type.BOTH;
                    if(j+1 < map[0].length) {
                        isOnPillar = isOnPillar || map[i-1][j+1].type == Type.PILLAR || map[i-1][j+1].type == Type.BOTH;
                    }
                    boolean isBothBo = (j <= 0) ? false : map[i][j-1].type == Type.BO || map[i][j-1].type == Type.BOTH;
                    isBothBo = isBothBo && ((j+1 >= map[0].length) ? false : map[i][j+1].type == Type.BO || map[i][j+1].type == Type.BOTH);

                    if(isFloor || (!isOnPillar && !isBothBo)) {
                        return false;
                    }
                }
            }
        }
        return true;
    }

    public enum Type{
        NONE(0),
        PILLAR(1),
        BO(2),
        BOTH(3)
        ;

        private int type;
        private Type(int type) {
			this.type = type;
        }
    };

    public class Struct{
        public Type type;

        public Struct() {
            this.type = Type.NONE;            
        }

        public Struct(Type type) {
            this.type = type;   
        }
    }
}