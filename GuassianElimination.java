//Loosineh Hartoonian

import java.util.Arrays;
import java.util.Scanner;
import java.io.File;
import java.io.FileNotFoundException;


public class main {

    /**
     * @param args
     * @throws FileNotFoundException
     */
    public static void main(String[] args) throws FileNotFoundException
    {
        int choice;
        int numOfEquations;
        Scanner input = new Scanner(System.in);
        System.out.println("Welcome to the Gaussian Elimination Calculation!!!");
        System.out.println("Please enter the number of linear equations you would like to solve: ");
        numOfEquations = input.nextInt();
        double[][] array = new double[numOfEquations][numOfEquations+1];
        double[] arrayB = new double[numOfEquations];

        //The number of linear equations must be greater than 2 and less than 11 
        //Check if the number of equations entered by user is valid or not
        if (numOfEquations < 3 || numOfEquations > 10) 
        {
            System.out.println("The number of equations you entered is invalid!!!");
            System.exit(0);
        }//end if

        File filename = new File("myfile.txt");
        System.out.println("1) Enter the coefficients from the command line");
        System.out.println("2) Enter the file name which has the augmented coefficient matrix");
        System.out.println("Please make a selection: ");
        choice = input.nextInt();
        if (choice == 1) 
        {
            System.out.println("You have selected to enter the coefficients from the command line!");
            getCoefficients(numOfEquations, array);
        }//end if

        else if (choice == 2) 
        {
            System.out.println("You have selected to enter the file name which has the augmented coefficient matrix!");
            getFileName(numOfEquations, array, null);
        }//end else if

        getLastRow(numOfEquations, array, arrayB);
        solve(array, arrayB);

    }//end main

    public static void getCoefficients(int num, double[][] array) 
    {
        int row = 0;
        int iterator = 0;
        Scanner input = new Scanner(System.in);
        for (int i = 0; i < num; i++)
        { 
            System.out.println("Please enter the value of each coefficient then press enter.");
            while (iterator < (num+1)) 
            {  
                int get = input.nextInt();
                array[row][iterator] = get;
                System.out.println(Arrays.deepToString(array));
                iterator++;
            }//end while
            iterator = 0;
            row++;
        }//end for loop
    }// end getCoefficients

    public static void getFileName(int num, double[][] array, String fileName) throws FileNotFoundException 
    {
        Scanner input = new Scanner(System.in);
        System.out.println("Please enter the name of the file which contains which has the augmented coefficient matrix");
        System.out.println("The file name would then look like this (if using kotlin script) example: 'matrix.txt.kt'");
        System.out.println("Otherwise, '.txt' is fine");
        fileName = input.nextLine();

        try 
        {
            File file1 = new File(fileName);
            Scanner input1 = new Scanner(file1);
            while (input1.hasNextInt())
            {
                for (int i = 0; i < num; i++) {
                    for (int j = 0; j < (num+1); j++) {
                        int data = input1.nextInt();
                        array[i][j] = data;
                    }
                }//end for
            }//end while
            System.out.println(Arrays.deepToString(array));
            input1.close();
        } catch (FileNotFoundException e) {
            System.out.println("Error!");
            e.printStackTrace();
        }
    }// end getFileName


    //This method is for arrayB for the b values
    public static void getLastRow(int num, double[][] arr, double[] arrB) 
    {
        int iterator = 0;
        int row;
        System.out.println("The system will now create a new array called arrayB to solve for B values in matrix: ");
        for (row = 0; row< num; row++) 
        { 
            while (iterator < (num+1)) 
            {  
                if (iterator == num) 
                {
                    arrB[row] = arr[row][iterator];
                }//end if
                iterator++;
            }//end while

            iterator = 0;

        }//end for loop
        System.out.print(Arrays.toString(arrB));
    }// end getLastRow


    //This method solve the equation by using the the Gaussian elimination with Scaled Partial Pivoting method.
    public static void solve(double[][] arr, double[] arrB) 
    {
        
        int len = arrB.length;
        for (int k = 0; k < len; k++) 
        {
            //Pivot row
            int max = k;
            for (int i = k + 1; i < len; i++)
                if (Math.abs(arr[i][k]) > Math.abs(arr[max][k]))
                    max = i;

            double[] a = arr[k];
            arr[k] = arr[max];
            arr[max] = a;

            double b = arrB[k];
            arrB[k] = arrB[max];
            arrB[max] = b;

            for (int i = k + 1; i < len; i++) 
            {
                double factor = arr[i][k] / arr[k][k];
                arrB[i] = arrB[i] - (factor * arrB[k]);
                for (int j = k; j < len; j++)
                    arr[i][j] -= factor * arr[k][j];
            }//end for loop
        }//end for loop

        printRowEchelonForm(arr, arrB);

        double[] solution = new double[len];
        for (int i = len - 1; i >= 0; i--) {
            double sum = 0;
            for (int j = i + 1; j < len; j++)
                sum += arr[i][j] * solution[j];
            solution[i] = (arrB[i] - sum) / arr[i][i];
        }
        displaySolution(solution);
    }// end solve

    public static void printRowEchelonForm (double[][] arr, double[] arrB)
    {
        int len = arrB.length;
        System.out.println();
        System.out.println("Row Echelon form : ");
        System.out.println(Arrays.deepToString(arr));
        System.out.println(Arrays.toString(arrB));
        System.out.println();
    }

    public static void displaySolution(double[] result)
    {
        int len = result.length;
        System.out.println("The result of this matrix will be: ");
        for (int i = 0; i < len; i++)
            System.out.printf("%.1f ", result[i]);
        System.out.println();
    }

}
