package com.wo.bu.dong.sample.req;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import com.wo.bu.dong.common.base.BaseReqDTO;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class CarReq extends BaseReqDTO {
    private static final long serialVersionUID = 1L;

    @NotNull
    private String            manufacturer;

    @NotNull
    @Size(min = 2, max = 14)
    private String            licensePlate;

    @Min(2)
    private int               seatCount;

    public CarReq(String manufacturer, String licensePlate, int seatCount) {
        super();
        this.manufacturer = manufacturer;
        this.licensePlate = licensePlate;
        this.seatCount = seatCount;
    }

}
