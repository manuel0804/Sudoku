package de.hft_stuttgart.ip1.sudokuGen;
import java.lang.*;

public class Generator
{
    private int[][] mat;
    private int n; // number of columns/rows.

    private int srn; // square root of N
    private int k; // No. Of missing digits

    // Constructor
    public Generator(int N, int K)
    {
        this.n = N;
        this.k = K;

        // Compute square root of N
        Double SRNd = Math.sqrt(N);
        srn = SRNd.intValue();

        mat = new int[N][N];
    }

    // Sudoku Generator
    public void fillValues()
    {
        // Fill the diagonal of SRN x SRN matrices
        fillDiagonal();

        // Fill remaining blocks
        fillRemaining(0, srn);

        // Remove Randomly K digits to make game
        //removeKDigits();
    }

    // Fill the diagonal SRN number of SRN x SRN matrices
    void fillDiagonal()
    {

        for (int i = 0; i<n; i=i+ srn)

            // for diagonal box, start coordinates->i==j
            fillBox(i, i);
    }

    // Returns false if given 3 x 3 block contains num.
    boolean unUsedInBox(int rowStart, int colStart, int num)
    {
        for (int i = 0; i< srn; i++)
            for (int j = 0; j< srn; j++)
                if (mat[rowStart+i][colStart+j]==num)
                    return false;

        return true;
    }

    // Fill a SRN x SRN matrix.
    void fillBox(int row,int col)
    {
        int num;
        for (int i = 0; i< srn; i++)
        {
            for (int j = 0; j< srn; j++)
            {
                do
                {
                    num = randomGenerator(n);
                }
                while (!unUsedInBox(row, col, num));

                mat[row+i][col+j] = num;
            }
        }
    }

    // Random generator
    int randomGenerator(int num)
    {
        int min = 1;
        int rand = (int)(Math.random() * num) + min;
        return rand;
    }

    // Check if safe to put in cell
    boolean CheckIfSafe(int i,int j,int num)
    {
        return (unUsedInRow(i, num) &&
                unUsedInCol(j, num) &&
                unUsedInBox(i-i% srn, j-j% srn, num));
    }

    // check in the row for existence
    boolean unUsedInRow(int i,int num)
    {
        for (int j = 0; j<n; j++)
            if (mat[i][j] == num)
                return false;
        return true;
    }

    // check in the row for existence
    boolean unUsedInCol(int j,int num)
    {
        for (int i = 0; i<n; i++)
            if (mat[i][j] == num)
                return false;
        return true;
    }

    // A recursive function to fill remaining
    // matrix
    boolean fillRemaining(int i, int j)
    {
        if (j>=n && i<n-1)
        {
            i = i + 1;
            j = 0;
        }
        if (i>=n && j>=n)
            return true;

        if (i < srn)
        {
            if (j < srn)
                j = srn;
        }
        else if (i < n- srn)
        {
            if (j==(int)(i/ srn)* srn)
                j =  j + srn;
        }
        else
        {
            if (j == n- srn)
            {
                i = i + 1;
                j = 0;
                if (i>=n)
                    return true;
            }
        }

        for (int num = 1; num<=n; num++)
        {
            if (CheckIfSafe(i, j, num))
            {
                mat[i][j] = num;
                if (fillRemaining(i, j+1))
                    return true;

                mat[i][j] = 0;
            }
        }
        return false;
    }

    // Remove the K no. of digits to
    // complete game
    public void removeKDigits()
    {
        int count = k;
        while (count != 0)
        {
            int cellId = randomGenerator(n*n)-1;

            // System.out.println(cellId);
            // extract coordinates i  and j
            int i = (cellId/n);
            int j = cellId%n;
            if (j != 0)
                j = j - 1;

            // System.out.println(i+" "+j);
            if (mat[i][j] != 0)
            {
                count--;
                mat[i][j] = 0;
            }
        }
    }

    public Integer[][] getSudoku(){
        Integer[][] mat2 = new Integer[mat.length][mat.length];
        for(int i = 0; i < mat.length; i++){
            for(int j = 0; j < mat.length; j++){
                mat2 [i][j] = mat[i][j];
            }
        }
        return mat2;
    }

    public Character[] getCharSudoku(){
        Character[] cmat = new Character[mat.length*mat.length];
        int start = 64;
        for (int i = 0; i < mat.length * mat.length; i++) {
            if (mat[i / mat.length][i % mat.length] != 0) {
                cmat[i] = (char) (start + mat[i / mat.length][i % mat.length]);
            }
        }
        return cmat;
    }
}