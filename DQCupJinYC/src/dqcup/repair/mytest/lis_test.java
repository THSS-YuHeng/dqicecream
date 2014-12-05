package dqcup.repair.mytest;

public class lis_test {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		int[] A = new int[]{0,0,0,2,5,7,40,50,100,120,200,300,350,370,380,355,390,400,800,432,450,500};
		int n = A.length;
		int[] B = new int[n+1];
		int INF = 100000000;
		int cur_lis_len = 1;
		B[0] = 1;
		for(int i = 1; i < n; i++ ){
			int max_len = 0;
			for(int j = 0; j < i; j++) {
				if(A[i] >= A[j] && B[j] > max_len) {
					max_len = B[j];
				}
			}
			B[i] = max_len+1;
		}
		int l = INF;
		int lb = B[n-1]+1;
		for(int j = n-1; j >= 0; j--) {
			System.out.print(A[j] + "\t" + B[j]);
			if( A[j] <= l && B[j] == lb-1) {
				l = A[j];
				lb = B[j];
				System.out.println("");
			} else {
				System.out.println("-");
			}
		}
		String test = "";
		System.out.println(test.equals(""));
	}
}
