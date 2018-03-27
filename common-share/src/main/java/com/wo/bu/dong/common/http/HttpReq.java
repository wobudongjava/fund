package com.wo.bu.dong.common.http;

import java.util.Map;
import com.wo.bu.dong.common.base.BaseDTO;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class HttpReq extends BaseDTO {
    private static final long   serialVersionUID = 1L;
    private String              url;
    private String              contentType;
    private Map<String, Object> params;
    private String              paramCharset;
}
