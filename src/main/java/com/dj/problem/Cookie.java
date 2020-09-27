package com.dj.problem;

public class Cookie {
    public static void main(String[] args) {
        int[] cookie = {1,1,2,3};
        System.out.println(new Cookie().solution(cookie));
    }

    int best = 0;
    int[] leftSum;
    int[] rightSum;


    public int solution(int[] cookie) {
        int n = cookie.length;
        leftSum = new int[n];
        rightSum = new int[n];

        int sum = 0;
        for(int i = 0; i < n; i++) {
            sum += cookie[i];
            leftSum[i] = sum;
        }

        sum = 0;
        for(int i = n - 1; i >= 0; i--) {
            sum += cookie[i];
            rightSum[i] = sum;
        }


        for(int l = 0; l < n - 1; l++) {
            for(int r = l + 1; r < n; r++) {
                binarySearch(l, r, n);
            }
        }
        return best;
    }

    public void binarySearch(int l, int r, int n) {
        int lo = l, hi = r;

        while(lo < hi) {
            int mid = lo + (hi - lo) / 2;
            if(predicate(mid, l, r, n)) {
                hi = mid;
            } else {
                lo = mid + 1;
            }
        }

        //System.out.println(l + " " + r + " " + lo);
        int left = getLeftSum(lo, l, n);
        int right = getRightSum(lo, r, n);

        if(left == right) {
            best = Math.max(best, left);
        }
    }

    public boolean predicate(int mid, int l, int r, int n){

        int left = getLeftSum(mid, l, n);
        int right = getRightSum(mid, r, n);

        return left >= right;
    }

    public int getLeftSum(int mid, int l, int n) {
        int left = leftSum[mid];
        if(l-1 >= 0) {
            left -= leftSum[l-1];
        }
        return left;
    }

    public int getRightSum(int mid, int r, int n) {
        int right = 0;
        if(mid+1 < n) {
            right = rightSum[mid + 1];
        }
        if(r+1 < n) {
            right -= rightSum[r+1];
        }
        return right;
    }
}