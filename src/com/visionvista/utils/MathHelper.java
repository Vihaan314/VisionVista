package com.visionvista.utils;

public class MathHelper {
    public static float[] scalerMultiply( double scaler, float[] kernal) {
        for (int i = 0; i < kernal.length; i++) {
            kernal[i] *= (float) scaler;
        }
        return kernal;
    }

    public static float kernalSum(float[] kernal) {
        float sum = 0f;
        for (int i = 0; i < kernal.length; i++) {
            sum += kernal[i];
        }
        return sum;
    }

    public static float[] flatten2D(float[][] matrix) {
        float[] flatArr = new float[matrix.length*matrix[0].length];
        for (int i = 0; i < matrix[0].length; i++) {
            for (int j = 0; j < matrix[0].length; j++) {
                flatArr[i*matrix[0].length+j] = matrix[i][j];
            }
        }
        return flatArr;
    }

    public static float[][] normalizeKernal(float[][] kernal) {
        float normFactor = kernal[0][0];
        float[][] normKernal = new float[kernal.length][kernal.length];
        for (int i = 0; i < kernal.length; i++) {
            for (int j = 0; j < kernal.length; j++) {
                normKernal[i][j] = kernal[i][j] / normFactor;
            }
        }
        return normKernal;
    }
}
