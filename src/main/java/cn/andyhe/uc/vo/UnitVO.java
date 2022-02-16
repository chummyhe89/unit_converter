package cn.andyhe.uc.vo;

import com.googlecode.aviator.AviatorEvaluator;
import com.googlecode.aviator.Expression;
import org.apache.commons.lang3.StringUtils;

public class UnitVO {
    private String typeName;
    private String typeNameCn;
    private String typeCode;
    private String convertDefault;
    private String name;
    private String nameCn;
    private String symbol;
    private String convertExp;
    private String convertReverseExp;
    private Expression expression;
    private Expression reverseExpression;

    public String getTypeName() {
        return typeName;
    }

    public void setTypeName(String typeName) {
        this.typeName = typeName;
    }

    public String getTypeNameCn() {
        return typeNameCn;
    }

    public void setTypeNameCn(String typeNameCn) {
        this.typeNameCn = typeNameCn;
    }

    public String getTypeCode() {
        return typeCode;
    }

    public void setTypeCode(String typeCode) {
        this.typeCode = typeCode;
    }

    public String getConvertDefault() {
        return convertDefault;
    }

    public void setConvertDefault(String convertDefault) {
        this.convertDefault = convertDefault;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNameCn() {
        return nameCn;
    }

    public void setNameCn(String nameCn) {
        this.nameCn = nameCn;
    }

    public String getSymbol() {
        return symbol;
    }

    public void setSymbol(String symbol) {
        this.symbol = symbol;
    }

    public String getConvertExp() {
        return convertExp;
    }

    public void setConvertExp(String convertExp) {
        this.convertExp = convertExp;
    }

    public Expression getExpression() {
        return expression;
    }

    public void setExpression(Expression expression) {
        this.expression = expression;
    }

    public String getConvertReverseExp() {
        return convertReverseExp;
    }

    public void setConvertReverseExp(String convertReverseExp) {
        this.convertReverseExp = convertReverseExp;
    }

    public Expression getReverseExpression() {
        return reverseExpression;
    }

    public void setReverseExpression(Expression reverseExpression) {
        this.reverseExpression = reverseExpression;
    }

    public static UnitVO of(String typeName , String typeNameCn,
                            String typeCode, String convertDefault,
                            String name, String nameCn,
                            String symbol, String convertExp,String convertReverseExp){
        UnitVO unitVO = new UnitVO();
        unitVO.setTypeName(typeName);
        unitVO.setTypeNameCn(typeNameCn);
        unitVO.setTypeCode(typeCode);
        unitVO.setConvertDefault(convertDefault);
        unitVO.setConvertExp(convertExp);
        unitVO.setConvertReverseExp(convertReverseExp);
        unitVO.setSymbol(symbol);
        unitVO.setName(name);
        unitVO.setNameCn(nameCn);
        unitVO.setExpression(AviatorEvaluator.compile(convertExp));
        if(StringUtils.isNotBlank(convertReverseExp)) {
            unitVO.setReverseExpression(AviatorEvaluator.compile(convertReverseExp));
        }
        return unitVO;
    }

}
