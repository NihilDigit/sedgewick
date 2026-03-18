package ch01;

public class ex1127 {
    public static double binomial_recursive(int N, int k, double p) {
        if (N == 0 && k == 0) return 1.0;
        if (N < 0 || k < 0) return 0.0;
        return (1.0 - p)*binomial_recursive(N-1, k ,p) + p*binomial_recursive(N-1, k-1, p);
    }


    public static double binomial(int N, int k, double p){
        double[][] memo = new double[N+1][k+1];
        memo[0][0] = 1.0;
        for (int i = 1; i <= N; i++) {
            if (k == 0) {
                memo[i][0] = (1.0-p) * memo[i-1][0]; // 需要特殊处理，数组不存在[-1]的索引
            }
            for (int j = 1; j <= k; j++) {
                memo[i][j] = (1.0-p) * memo[i-1][j] + p * memo[i-1][j-1];
            }
        }
        return memo[N][k];
    }


    public static void main(String[] args) {

    }
}
