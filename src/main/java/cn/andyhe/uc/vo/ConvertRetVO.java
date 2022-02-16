package cn.andyhe.uc.vo;

import org.apache.commons.lang3.builder.ToStringBuilder;

public class ConvertRetVO {
    private UnitValueVO src;
    private UnitValueVO dest;

    public UnitValueVO getSrc() {
        return src;
    }

    public void setSrc(UnitValueVO src) {
        this.src = src;
    }

    public UnitValueVO getDest() {
        return dest;
    }

    public void setDest(UnitValueVO dest) {
        this.dest = dest;
    }

    public static ConvertRetVO of(UnitValueVO src, UnitValueVO dest){
        ConvertRetVO retVO = new ConvertRetVO();
        retVO.setSrc(src);
        retVO.setDest(dest);
        return retVO;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .append("src",src)
                .append("dest",dest)
                .build();
    }
}
