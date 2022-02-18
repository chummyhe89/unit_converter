package cn.andyhe.uc;

import cn.andyhe.uc.vo.*;
import com.google.common.base.Preconditions;
import com.google.common.collect.ImmutableMap;
import com.googlecode.aviator.Expression;
import org.apache.commons.lang3.StringUtils;
import org.yaml.snakeyaml.Yaml;

import java.io.InputStream;
import java.util.*;

public class UnitConverter {

    public static final String CONF_NAME = "units.yaml";

    private final static Map<String, Map<String,UnitVO>> unitCaches = new HashMap<>();
    private final static Map<String,String> unit2Type = new HashMap<>();

    public static void loadConf(){
        Yaml yaml = new Yaml();
        InputStream is = UnitConverter.class.getClassLoader().getResourceAsStream(CONF_NAME);
        UnitDatabase unitData = yaml.loadAs(is, UnitDatabase.class);
        for (UnitConfTypeVO confTypeVO : unitData.getConf()) {
            String typeCode = confTypeVO.getCode();
            String typeName = confTypeVO.getName();
            String typeNameCn = confTypeVO.getNameCn();
            String convertDefault = confTypeVO.getConvertDefault();
            Map<String,UnitVO> typeUnits = new HashMap<>();
            for (UnitConfVO unitConfVO: confTypeVO.getUnits()) {
                List<String> nameList = new ArrayList<>();
                String name = unitConfVO.getName();
                String nameCn = unitConfVO.getNameCn();
                String symbol = unitConfVO.getSymbol();
                nameList.addAll(Arrays.asList(StringUtils.split(name,"|")));
                nameList.addAll(Arrays.asList(StringUtils.split(nameCn,"|")));
                nameList.addAll(Arrays.asList(StringUtils.split(symbol,"|")));
                String convertExp = unitConfVO.getConvertExp();
                String convertReverseExp  = unitConfVO.getConvertReverseExp();
                UnitVO unitVO = UnitVO.of(typeName,typeNameCn,typeCode,convertDefault,name,nameCn,symbol,convertExp,convertReverseExp);
                for (String nameItem : nameList) {
                    String normName = StringUtils.lowerCase(nameItem);
                    typeUnits.put(normName,unitVO);
                    // 单位所属类型映射
                    unit2Type.put(normName,typeCode);
                }
            }
            List<String> typeList = new ArrayList<>();
            typeList.addAll(Arrays.asList(StringUtils.split(typeCode,"|")));
            typeList.addAll(Arrays.asList(StringUtils.split(typeName,"|")));
            typeList.addAll(Arrays.asList(StringUtils.split(typeNameCn,"|")));
            for (String typeItem : typeList) {
                String normName = StringUtils.lowerCase(typeItem);
                unitCaches.put(normName,typeUnits);
            }
        }
    }

    static {
        loadConf();
    }

    public static ConvertRetVO convert(Double val, String unitSrc){
        String unitType = unit2Type.get(normStr(unitSrc));
        Preconditions.checkNotNull(unitType);
        return convert(unitType,val,unitSrc);
    }

    public static ConvertRetVO convert(String unitType, Double val, String unitSrc){
        Map<String,UnitVO> unitVOMap = unitCaches.get(normStr(unitType));
        if(unitVOMap != null) {
            UnitVO unitVO = unitVOMap.get(normStr(unitSrc));
            String destUnit = unitVO.getConvertDefault();
            UnitVO destUnitVO = unitVOMap.get(normStr(destUnit));
            Expression exp = unitVO.getExpression();
            Map<String,Object> params = ImmutableMap.<String,Object>of("val",val);
            Double destVal = (Double) exp.execute(params);
            UnitValueVO srcUnitVal = UnitValueVO.of(unitVO,val);
            UnitValueVO destUnitVal = UnitValueVO.of(destUnitVO,destVal);
            return ConvertRetVO.of(srcUnitVal,destUnitVal);
        }
        return null;
    }

    private static ConvertRetVO convertFromDefault(String unitType, Double val, String unitDest){
        Map<String,UnitVO> unitVOMap = unitCaches.get(normStr(unitType));
        if(unitVOMap != null) {
            UnitVO destUnitVO = unitVOMap.get(normStr(unitDest));
            UnitVO srcUnitVO = unitVOMap.get(normStr(destUnitVO.getConvertDefault()));
            Expression exp = destUnitVO.getReverseExpression();
            Map<String,Object> params = ImmutableMap.<String,Object>of("val",val);
            Double destVal = (Double) exp.execute(params);
            UnitValueVO srcUnitVal = UnitValueVO.of(srcUnitVO,val);
            UnitValueVO destUnitVal = UnitValueVO.of(destUnitVO,destVal);
            return ConvertRetVO.of(srcUnitVal,destUnitVal);
        }
        return null;
    }

    public static ConvertRetVO convert(String unitType, Double val, String unitSrc, String unitDest){
        ConvertRetVO srcDefault = convert(unitType,val,unitSrc);
        ConvertRetVO destFromDefault = convertFromDefault(unitType,srcDefault.getDest().getVal(),unitDest);
        return ConvertRetVO.of(srcDefault.getSrc(),destFromDefault.getDest());
    }

    private static String normStr(String raw){
        return StringUtils.lowerCase(raw);
    }

    public static void main(String[] args) {
        ConvertRetVO ret = UnitConverter.convert("mass",1.0,"kg","cwt");
        System.out.println(ret);
    }
}
