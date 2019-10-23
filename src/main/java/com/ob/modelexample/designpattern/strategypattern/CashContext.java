package com.ob.modelexample.designpattern.strategypattern;

/**
 * @Author: oubin
 * @Date: 2019/5/22 13:49
 * @Description: 策略模式和工厂模式相结合
 */
public class CashContext {

    private CashSuper cashSuper = null;

    public CashContext(String type) {
        switch (type) {
            case "正常收费":
                CashNormal cashNormal = new CashNormal();
                cashSuper = cashNormal;
                break;
            case "打8折":
                CashRebate cashRebate = new CashRebate(0.8);
                cashSuper = cashRebate;
                break;
        }
    }

    public double getResult(double money) {
        return cashSuper.acceptCash(money);
    }
}
