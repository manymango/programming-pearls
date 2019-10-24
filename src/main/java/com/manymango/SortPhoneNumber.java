package com.manymango;

import java.io.*;
import java.util.Arrays;
import java.util.Random;

/**
 * @author manymango
 * @date 2019/10/20 22:27
 * 给手机号排序问题
 */
public class SortPhoneNumber {

    /** 由于内存有限，内存只能容纳250000个电话号码 */
    private static final int BATCH_READ_NUMBER_MAX_SIZE = 250000;

    public static void main(String[] args) {
        String path = getResourcesAbsolutelyPath();
        path += "\\phoneNumberSort\\phoneNumber.txt";
        File file = new File(path);
        int[] array = readAppointLines(file, 1, 1);
        System.out.println(Arrays.toString(array));
    }

    /**
     * 读取文件的指定行
     * @param file      待读取文件
     * @param start     开始读取的行下标，第一行的下标为0
     * @param size      读取的条数
     * @return          读取的整数数组
     */
    public static int[] readAppointLines(File file, int start, int size) {
        int end = start + size;
        if (start<0 || start>=end) {
            return new int[0];
        }
        int[] result = new int[size];
        int[] finalResult = new int[0];
        try {
            FileReader in = new FileReader(file);
            LineNumberReader reader = new LineNumberReader(in);
            int resultIndex = 0;
            int readerIndex = 0;
            String numberStr;
            while (null != (numberStr = reader.readLine())) {
                if (readerIndex < start) {
                    readerIndex++;
                    continue;
                }
                if (readerIndex >= end) {
                    readerIndex++;
                    break;
                }
                readerIndex++;
                result[resultIndex++] = Integer.parseInt(numberStr);

            }
            reader.close();
            in.close();

            // 如果result数据全部填满则直接返回
            if (resultIndex >= result.length) {
                return result;
            }
            // 否则只取result前面有赋值的那部分数组
            finalResult = new int[resultIndex];
            for (int i=0; i<resultIndex; ++i) {
                finalResult[i] = result[i];
            }
            return finalResult;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return finalResult;
    }





    /**
     * 创建10^7个电话号码，并将其顺序打乱写到文件
     */
    public static void createThePhoneNumberTooFile()  {
        // 构造这10^7个电话号码
        int sum = 10000000;
        String[] phoneArray = new String[sum];
        for (int i=0; i<sum; ++i) {
            String phoneNumber = i + "";
            switch (phoneNumber.length()) {
                case (1):
                    phoneNumber = "000000" + phoneNumber;
                    break;
                case (2):
                    phoneNumber = "00000" + phoneNumber;
                    break;
                case (3):
                    phoneNumber = "0000" + phoneNumber;
                    break;
                case (4):
                    phoneNumber = "000" + phoneNumber;
                    break;
                case (5):
                    phoneNumber = "00" + phoneNumber;
                    break;
                case (6):
                    phoneNumber = "0" + phoneNumber;
                    break;
                default:break;
            }
            phoneArray[i] = phoneNumber;
        }

        // 随机打乱号码顺序
        Random random = new Random(sum);
        for (int i=0; i<sum; ++i) {
            int randomPosition = random.nextInt(sum);
            String middlePhNum = phoneArray[i];
            phoneArray[i] = phoneArray[randomPosition];
            phoneArray[randomPosition] = middlePhNum;
        }
        String resourcePath = getResourcesAbsolutelyPath();

        //创建phoneNumberSort文件夹
        resourcePath += "\\phoneNumberSort";
        File phoneNumSaveFile = new File(resourcePath);
        if (!phoneNumSaveFile.exists()) {
            phoneNumSaveFile.mkdir();
        }
        // 将电话号码写入文件
        resourcePath += "\\phoneNumber.txt";
        phoneNumSaveFile = new File(resourcePath);
        Writer out;
        try {
            out = new FileWriter(phoneNumSaveFile);
        } catch (IOException e) {
            e.printStackTrace();
            return;
        }
        int i=0;
        for (String phoneNumber : phoneArray) {
            try {
                ++i;
                out.write(phoneNumber + "\n");
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        try {
            out.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        System.out.println("create the phone number success, its "+resourcePath);
    }

    /**
     * 得到项目中resources文件夹的绝对路径
     * @return    resources的绝对路径
     */
    public static String getResourcesAbsolutelyPath() {
        // 得到resource文件夹绝对路径
        File directory = new File("");
        String resourcePath =directory.getAbsolutePath();
        resourcePath += "\\src\\main\\resources";
        return resourcePath;
    }




    /**
     * 普通的归并排序测试，写这个只是为了回顾归并排序，
     * 为基于磁盘归并排序打下基础，嘻嘻
     */
    public static void mergeSortTest() {
        int[] array = {5,4,3,2,1,6,7,8,9};
        mergeSort(array, 0, array.length-1);
        System.out.println(Arrays.toString(array));
    }

    public static void mergeSort(int[] array, int start, int end) {
        if (start>=end) {
            return;
        }
        int middle = (start+end)/2;
        // 左边分治
        mergeSort(array, start, middle);
        // 右边分治
        mergeSort(array, middle+1, end);
        // 合并左右
        merge(array, start, end, middle);
    }

    public static void merge(int[] array, int start, int end, int middle) {
        int leftIndex = start;
        int rightIndex = middle+1;

        int[] temp = new int[end - start + 1];
        int i =0;
        while (leftIndex <= middle && rightIndex <= end) {
            int left = array[leftIndex];
            int right = array[rightIndex];
            if (left <= right) {
                temp[i++] = left;
                leftIndex++;
            } else {
                temp[i++] = right;
                rightIndex++;
            }
        }

        while (leftIndex <= middle) {
            temp[i++] = array[leftIndex++];
        }

        while (rightIndex <= end) {
            temp[i++] = array[rightIndex++];
        }

        for (i = 0; i< temp.length;i++) {
            array[start+i] = temp[i];
        }
    }

}
