package com.wo.bu.dong.sample.dto;

import java.math.BigDecimal;
import java.util.Date;

import com.wo.bu.dong.common.base.BaseDto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SampleDTO extends BaseDto {

    private static final long serialVersionUID = 1L;

    private Boolean           sampleBoolean;
    private Character         sampleCharacter;
    private Byte              sampleByte;
    private Short             sampleShort;
    private Integer           sampleInteger;
    private Long              sampleLong;
    private Float             sampleFloat;
    private Double            sampleDouble;
    private String            sampleString;
    private BigDecimal        sampleBigDecimal;
    private Date              sampleDate;

    public void init() {
        this.sampleBoolean = true;
        this.sampleCharacter = 'a';
        this.sampleByte = 1;
        this.sampleShort = 1;
        this.sampleInteger = 1;
        this.sampleLong = 1L;
        this.sampleFloat = 1.9F;
        this.sampleDouble = 1.9;
        this.sampleString = "Hello world!";
        this.sampleBigDecimal = BigDecimal.ONE;
        this.sampleDate = new Date();
    }

}
