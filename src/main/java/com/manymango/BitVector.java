package com.manymango;

/**
 * @author manymango
 * @date 2019/10/30 22:28
 */
public class BitVector {

    private final long[] words;

    private static final int NUMBER_BIT_LENGTH = 64;

    /**
     * 新建一个 BitVector
     * @param length  向量的长度
     */
    public BitVector(int length) {
        // length >> 6 就相当于处于64，long为64bit
        words = new long[1 + (length>>6)];
    }

    /**
     * 将向量的index位设置为1
     * @param index  向量下标，下标从0开始计算
     */
    public void set(int index) {
        int wordsIndex = index >> 6;
        // 1L >> index 将1左移
        words[wordsIndex] |= 1L << index;
    }

    /**
     * 获取向量下标对应的值
     * @param index  向量下标
     * @return       true:0, false:1
     */
    public boolean get(int index) {
        int wordsIndex = index >> 6;
        return (words[wordsIndex] & (1L << index)) != 0;
    }

}
