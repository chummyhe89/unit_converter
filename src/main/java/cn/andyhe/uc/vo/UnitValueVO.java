package cn.andyhe.uc.vo;

import org.apache.commons.lang3.builder.ToStringBuilder;

public class UnitValueVO{

    private UnitVO unitVO;
    private Double val;

    public Double getVal() {
        return val;
    }

    public void setVal(Double val) {
        this.val = val;
    }

    public UnitVO getUnitVO() {
        return unitVO;
    }

    public void setUnitVO(UnitVO unitVO) {
        this.unitVO = unitVO;
    }

    public static UnitValueVO of(UnitVO unitVO, Double val){
        UnitValueVO unitValueVO = new UnitValueVO();
        unitValueVO.setUnitVO(unitVO);
        unitValueVO.setVal(val);
        return unitValueVO;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this).append("type",unitVO.getTypeNameCn())
                .append("symbol",unitVO.getSymbol())
                .append("val",val)
                .build();
    }
}
