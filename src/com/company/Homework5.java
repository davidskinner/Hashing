package com.company;
import java.util.Random;
import static java.lang.Math.abs;

public class Homework5 {

    public enum ProbingType{
        linear,quadratic,doubleHash
    }

    public static void log(String x)
    {
         System.out.println(x);
    }

    public static int linearProbing(int k, int i, int m)
    {
        return abs((k +i)%m);
    }

    public static int hashInsert(ProbingType p, int[] a, int k)
    {
        int i = 0;
        int j = 0;
        int probes = 1;
        while(i != a.length)
        {
            j = probe(p,k,i,a.length);
            if(a[j] == 0)
            {
                a[j] = k;
                return probes;
            }
            else
            {
                i++;
                probes++;
            }

        }
        throw new RuntimeException();
    }

    public static int probe(ProbingType pType, int k, int i, int m)
    {
        int position = 0;

        switch(pType)
        {
            case linear:
                position = linearProbing(k,i,m);
                break;

                case quadratic:
                    position = quadraticProbing(k,i,m);
                break;

            case doubleHash:
                position = doubleHashing(k,i,m);
                break;
        }
        return position;

    }

    public static  int quadraticProbing(int k, int i, int m)
    {
        return abs((k + i+(3)*(i^2))%m);
    }

    public static  int doubleHashing(int k, int i, int m)
    {
        return abs((k + i*(1 + (k % (m-1)))) % m);
    }

    public static void main(String[] args) {

        Random rand = new Random();
        //make an array of size 1009
        int[] a = new int[1009];
        int[] ninetyPercent = new int[900];
        int[] fifty = new int[50];
        int[] probeCount = new int[50];

        //pick 900 random numbers and place in array to be reused
        for (int i = 0; i < 900; i++)
        {
                ninetyPercent[i] = rand.nextInt();
        }

        //pick 50 random numbers to be reused
        for (int i = 0; i < 50; i++)
        {
            fifty[i] = rand.nextInt();
        }

        log("Linear Probing");
        // linear probing

        for (int i = 0; i < ninetyPercent.length; i++)
        {
            // insert 900 values using linear hashing
            int k = ninetyPercent[i];
            hashInsert(ProbingType.linear,a,k);
        }

        for (int i = 0; i < fifty.length; i++)
        {
            // insert 50 more values with linear search and count number of probes
            int k = fifty[i];
            int numberOfProbes = hashInsert(ProbingType.linear,a,k);
            probeCount[i] = numberOfProbes;
        }

        for (int i = 0; i < probeCount.length; i++)
        {
            log(String.valueOf(probeCount[i]));
        }

        // quadratic probing

        // h(k,i) = (k + c1*i+c2*(i^2))%m


        // double hashing

        // h(k,i) = (k + i(1 + (k % (m -1))

    }
}
