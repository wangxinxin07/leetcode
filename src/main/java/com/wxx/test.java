
public class test {
	
	public void getZeroInFactorial(int start, int num){
        int count2 = 0;
        int count5 = 0;
        for (int i = start; i<=num; i++){
            count2 += countDivisor(i,2);
            count5 += countDivisor(i,5);
        }
        System.out.println(start + "乘到" + num + "的末尾有" + (count2>count5?count5:count2) + "个0！");
    }
 
    /*** 统计数字num的因子divisor的个数
     * @return
     */
    private int countDivisor(int num,int divisor){
        //统计个数
        int temp = 0;
        for (int i = 0; i < num; i++) {
            if (num%divisor==0){
                num /= divisor;
                temp++;
            }else {
                break;
            }
        }
        return temp;
    }
	
	public static void main(String args[])
	{
//第一题
		//test t = new test();
		//t.getZeroInFactorial(1,100);
//第二题
//		int s=1;
//		int a=1;
//		int i;
//		for(i=1;i<66;i++)
//		{
//			s=a*(a+i)*(a+2*i);
//			if(s==66)
//				break;
//		}
//		System.out.println(a+" and "+i);

//第三题
		int a = 1;
		
		for (a=1;a<42560;a++)
		{
			if(a*(2*a+6)/2*(a+6)==42560)
				break;
		}
		System.out.println(a+","+(2*a+6)/2+","+(a+6));
	}
	

}
