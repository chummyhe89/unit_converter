package cn.andyhe.uc.vo;

import java.util.List;

public class UnitConfTypeVO {
    private String name;
    private String nameCn;
    private String code;
    private String convertDefault;
    private List<UnitConfVO> units;

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

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getConvertDefault() {
        return convertDefault;
    }

    public void setConvertDefault(String convertDefault) {
        this.convertDefault = convertDefault;
    }

    public List<UnitConfVO> getUnits() {
        return units;
    }

    public void setUnits(List<UnitConfVO> units) {
        this.units = units;
    }
}
