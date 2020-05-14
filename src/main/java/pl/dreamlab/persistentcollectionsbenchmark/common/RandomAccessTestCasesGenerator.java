package pl.dreamlab.persistentcollectionsbenchmark.common;

import java.util.ArrayList;
import java.util.Random;

public class RandomAccessTestCasesGenerator {
    private static final int EXISTING_VALUES_QUERIES_PERCENTAGE = 90;

    public static ArrayList<Integer> generate(int numberOfReadOperations, Random random, ArrayList<Integer> writeTestCases) {
        ArrayList<Integer> testCases = new ArrayList<>();

        for (int i = 0; i < numberOfReadOperations; i++) {
            int option = random.nextInt(100);

            if (option > EXISTING_VALUES_QUERIES_PERCENTAGE) {
                testCases.add(random.nextInt());
            } else {
                testCases.add(writeTestCases.get(random.nextInt(numberOfReadOperations)));
            }
        }

        return testCases;
    }
}
