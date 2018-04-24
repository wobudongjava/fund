package com.wo.bu.dong.common.util;

import java.math.BigDecimal;
import java.math.RoundingMode;

import org.apache.commons.lang3.ArrayUtils;

public class MoneyUtil {

    /**
     * 与0比较
     * 
     * @param amount
     * @return
     */
    public static boolean greaterThanZero(BigDecimal amount) {
        return amount != null && amount.compareTo(BigDecimal.ZERO) > 0;
    }

    /**
     * 两个金额比较
     * 
     * @param amount1
     * @param amount2
     * @return
     */
    public static int compare(BigDecimal amount1, BigDecimal amount2) {
        return nullToZero(amount1).compareTo(nullToZero(amount2));
    }

    /**
     * null转换为0
     * 
     * @param amount
     * @return
     */
    public static BigDecimal nullToZero(BigDecimal amount) {
        return amount == null ? BigDecimal.ZERO : amount;
    }

    /**
     * 多个金额相加，不处理精度
     */
    public static BigDecimal add(BigDecimal amount1, BigDecimal amount2, BigDecimal... amounts) {
        BigDecimal result = nullToZero(amount1).add(nullToZero(amount2));
        if (ArrayUtils.isNotEmpty(amounts)) {
            for (BigDecimal amount : amounts) {
                result = result.add(nullToZero(amount));
            }
        }
        return result;
    }

    /**
     * 两个金额相减，不处理精度
     *
     * @param amount1
     * @param amount2
     * @return
     */
    public static BigDecimal subtract(BigDecimal amount1, BigDecimal amount2) {
        return nullToZero(amount1).subtract(nullToZero(amount2));
    }

    /**
     * 两个金额相减，四舍五入保留两位小数
     *
     * @param amount1
     * @param amount2
     * @return
     */
    public static BigDecimal subtractWithScale(BigDecimal amount1, BigDecimal amount2) {
        BigDecimal result = nullToZero(amount1).subtract(nullToZero(amount2));
        return result.setScale(2, RoundingMode.HALF_UP);
    }

    /**
     * 多个金额相乘，不处理精度
     */
    public static BigDecimal multiply(BigDecimal amount1, BigDecimal amount2, BigDecimal... amounts) {
        BigDecimal result = nullToZero(amount1).multiply(nullToZero(amount2));
        if (ArrayUtils.isNotEmpty(amounts)) {
            for (BigDecimal amount : amounts) {
                result = result.multiply(nullToZero(amount));
            }
        }
        return result;
    }

    /**
     * 多个金额相乘，四舍五入保留两位小数
     */
    public static BigDecimal multiplyWithScale(BigDecimal amount1, BigDecimal amount2, BigDecimal... amounts) {
        BigDecimal result = nullToZero(amount1).multiply(nullToZero(amount2));
        if (ArrayUtils.isNotEmpty(amounts)) {
            for (BigDecimal amount : amounts) {
                result = result.multiply(nullToZero(amount));
            }
        }
        return result.setScale(2, RoundingMode.HALF_UP);
    }

    /**
     * 两数相除
     * 
     * @param src 被除数
     * @param dest 除数
     * @param scale 精度
     * @return
     */
    public static BigDecimal divide(BigDecimal src, BigDecimal dest, int scale) {
        BigDecimal srcDeci = nullToZero(src);
        BigDecimal destDeci = nullToZero(dest);
        return srcDeci.divide(destDeci, scale, RoundingMode.HALF_UP);
    }

    /**
     * 金额单位元转分
     */
    public static BigDecimal yuanToFen(BigDecimal amount) {
        if (amount == null) {
            return BigDecimal.ZERO;
        }
        return amount.movePointRight(2).setScale(0, BigDecimal.ROUND_DOWN);
    }

    /**
     * 金额单位分转元
     */
    public static BigDecimal fenToyuan(BigDecimal amount) {
        return nullToZero(amount).movePointLeft(2).setScale(2, BigDecimal.ROUND_HALF_UP);
    }

    /**
     * 检查是否是正确的资金金额
     *
     * @param val
     * @return
     */
    public static boolean checkValidAmount(BigDecimal val) {
        if (null == val) {
            return true;
        }
        BigDecimal result = new BigDecimal(val.toString()).setScale(2, RoundingMode.DOWN);

        if (result.compareTo(val) == 0) {
            return true;
        } else {
            return false;
        }

    }

    /**
     * NPV公式，用于计算现值PV，保留6位小数，四舍五入
     *
     * @param perNCF 每期的净现金流(除最后一期外)
     * @param perRate 收益率
     * @param periods 期数
     * @param lastPerNCF 最后一期的净现金流
     * @return
     */
    public static BigDecimal calPv(BigDecimal perNCF, BigDecimal perRate, int periods, BigDecimal lastPerNCF) {
        BigDecimal result = BigDecimal.ZERO;
        for (int i = 1; i <= periods - 1; i++) {
            result = result.add(perNCF.divide(perRate.add(BigDecimal.ONE).pow(i), 8, RoundingMode.DOWN));
        }
        result = result.add(lastPerNCF.divide(perRate.add(BigDecimal.ONE).pow(periods), 8, RoundingMode.DOWN));
        result = result.divide(new BigDecimal(1), 6, RoundingMode.HALF_UP);
        return result;
    }

    /**
     * PMT公式，用于计算每期净现金流NCF，保留6位小数，四舍五入
     *
     * @param firstFlow 期初现金流出
     * @param perRate 收益率
     * @param periods 期数
     * @return
     */
    public static BigDecimal calEachNcf(BigDecimal firstFlow, BigDecimal perRate, int periods) {
        BigDecimal divisor = firstFlow.multiply(perRate.add(BigDecimal.ONE).pow(periods));
        BigDecimal dividend = BigDecimal.ZERO;
        BigDecimal result = BigDecimal.ZERO;
        for (int i = 0; i < periods; i++) {
            dividend = dividend.add(perRate.add(BigDecimal.ONE).pow(i));
        }
        result = divisor.divide(dividend, 6, RoundingMode.HALF_UP);
        return result;
    }

    /**
     * 计算第N期的终值（前提：前几期每期的净现金流必须相等）,保留6位小数，四舍五入
     *
     * @param PV 未来收益的现值
     * @param preEachNcf 前几期每期的净现金流
     * @param perRate 收益率
     * @param periods 当前的期数
     * @return
     */
    public static BigDecimal calFvByPV(BigDecimal PV, BigDecimal preEachNcf, BigDecimal perRate, int periods) {
        BigDecimal result = PV.multiply(perRate.add(BigDecimal.ONE).pow(periods));
        for (int i = 1; i < periods; i++) {
            result = result.subtract(preEachNcf.multiply(perRate.add(BigDecimal.ONE).pow(i)));
        }
        result = result.divide(new BigDecimal(1), 6, RoundingMode.HALF_UP);
        return result;
    }

    /**
     * 检查分期金额是否合法
     *
     * @param amount 金额
     * @param peroid 期数
     * @return
     */
    public static boolean checkPeriodAmount(BigDecimal amount, Integer peroid) {

        if (peroid > 0) {
            BigDecimal periodAmount = divide(amount, new BigDecimal(peroid), 2);
            return (periodAmount.compareTo(BigDecimal.ZERO) > 0);
        }

        return true;
    }

    /**
     * 金额精度检查
     *
     * @param val
     * @param size
     * @return
     */
    public static boolean checkAmount(BigDecimal val, int size) {

        if (val == null || val.toString().split("\\.").length <= 1) {
            return false;
        }
        if (val.toString().split("\\.")[1].length() == size) {
            return true;
        }

        return false;
    }
}
