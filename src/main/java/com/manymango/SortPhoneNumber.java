package com.manymango;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.util.Random;

/**
 * @author manymango
 * @date 2019/10/20 22:27
 * 给手机号排序问题
 */
public class SortPhoneNumber {
    public static void main(String[] args) {
        createThePhoneNumberTooFile();
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
        // 得到resource文件夹绝对路径
        File directory = new File("");
        String resourcePath =directory.getAbsolutePath();
        resourcePath += "\\src\\main\\resources";

        // 将电话号码写入文件
        resourcePath += "\\phoneNumber.txt";
        File phoneNumSaveFile = new File(resourcePath);
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

}
