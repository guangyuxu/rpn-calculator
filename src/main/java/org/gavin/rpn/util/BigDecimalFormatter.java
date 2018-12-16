package org.gavin.rpn.util;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class BigDecimalFormatter {
    private final int storeScale;
    private final int presentScale;
    private final RoundingMode roundingMode;

    public BigDecimalFormatter(int storeScale, int presentScale, RoundingMode roundingMode) {
        this.storeScale = storeScale;
        this.presentScale = presentScale;
        this.roundingMode = roundingMode;
    }

    public BigDecimal formatForStore(BigDecimal b){
        return format(b, storeScale);
    }

    public BigDecimal formatForPresent(BigDecimal b){
        return format(b, presentScale);
    }

    private BigDecimal format(BigDecimal b, int scale){
        if(b.scale() <= scale){
            return b;
        }else{
             return b.setScale(scale, roundingMode);
        }
    }
}
