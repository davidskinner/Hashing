package com.company;

import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.Random;
import java.util.Spliterator;

import static java.lang.Math.abs;

public class Homework5
{

	public enum ProbingType
	{
		linear, quadratic, doubleHash
	}

	public static void log(String x)
	{
		System.out.println(x);
	}

	public static int linearProbing(int k, int i, int m)
	{
		return abs((k + i) % m);
	}

	public static int quadraticProbing(int k, int i, int m)
	{
		return abs((k + i + (3) * (i ^ 2)) % m);
	}

	public static int doubleHashing(int k, int i, int m)
	{
		return abs((k + i * (1 + (k % (m - 1)))) % m);
	}

	public static int hashInsert(ProbingType p, int[] a, int k)
	{
		int i = 0;
		int j = 0;
		int probes = 0;
		while (i != a.length)
		{
			j = probe(p, k, i, a.length);
			probes++;

			if (a[j] == 0)
			{
				a[j] = k;
				return probes;
			} else
			{
				i++;
			}
		}

		int zeros = 0;

		for (int l = 0; l < a.length; l++)
		{
			if(a[l] == 0)
				zeros++;
		}
		if(zeros > 0)
		{
			// loop through and pick a spot with a 0
			for (int l = 0; l < a.length; l++)
			{
				probes++;
				if(a[l] == 0)
				{
					a[l] = k;
					return probes;
				}
			}
		}


		log("there are : " + String.valueOf(zeros) + " zeros left");
		log(String.valueOf(k));
		throw new RuntimeException();
	}

	public static int probe(ProbingType pType, int k, int i, int m)
	{
		int position = 8;

		switch (pType)
		{
			case linear:
				position = linearProbing(k, i, m);
				break;

			case quadratic:
				position = quadraticProbing(k, i, m);
				break;

			case doubleHash:
				position = doubleHashing(k, i, m);
				break;
		}
		return position;

	}


	public static void main(String[] args)
	{

		Random rand = new Random();
		LinkedHashSet<Integer> randomSet = new LinkedHashSet<>();

		//get 950 unique numbers to put in the set
		for (int i = 0; i < 950; i++)
		{
			int temp = rand.nextInt();
			while (randomSet.contains(temp))
			{
				temp = rand.nextInt(100000);
			}
			randomSet.add(temp);
		}

		for (ProbingType probingType : ProbingType.values())
		{
			// clear out the arrays
			int[] probeCount = new int[50];
			int[] a = new int[1009];

			log("");
			log(probingType.toString());
			log("there are this many unique elements in the set: " + String.valueOf(randomSet.size()));

			Iterator<Integer> d = randomSet.iterator();

			for (int i = 0; i < 900; i++)
			{
				// insert 900 values using linear hashing
				if (d.hasNext())
				{
					hashInsert(probingType, a, d.next());
				} else
				{
					throw new RuntimeException("Ran out in 900");
				}
			}

			for (int i = 0; i < 50; i++)
			{
				// insert 50 more values with linear search and count number of probes
				if (d.hasNext())
				{
					int numberOfProbes = hashInsert(probingType, a, d.next());
					probeCount[i] = numberOfProbes;
				} else
				{
					throw new RuntimeException("Ran out of room in 950");
				}
			}

			int total = 0;
			// sum array
			for (int i = 0; i < probeCount.length; i++)
			{
				total += probeCount[i];
			}


			int zeros = 0;

			for (int l = 0; l < a.length; l++)
			{
				if(a[l] == 0)
					zeros++;
			}
			log("There are : " + zeros + " zeros at the end");
			log("Total probes for last 50 insertions is : " + String.valueOf(total));

		}
	}
}
