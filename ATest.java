package pl.com.aay;

public class ATest {

     int[][] a = new int[][]{{0, 0, 1, 1, 0, 1},
                             {0, 0, 1, 1, 0, 0}};

    private boolean kolo()
    {
        for (int i=0;i<2;i++) {
            for (int j = 0; j <4;j++)
            {
                if (a[i][j]==a[i][j+1] && a[i][j+1] == a[i][j+2])
                {
                    return true;
                }
            }
        }
        return false;
    }

    public static void main(String[] args)
    {
        ATest test = new ATest();
        System.out.println(test.kolo());
    }
}
