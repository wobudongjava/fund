package com.zhongan.io.xdy.front.dao;

import com.zhongan.io.xdy.front.dbBean.SysRoleDO;

public interface SysRoleDOMapper {
    int deleteByPrimaryKey(String id);

    int insert(SysRoleDO record);

    int insertSelective(SysRoleDO record);

    SysRoleDO selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(SysRoleDO record);

    int updateByPrimaryKey(SysRoleDO record);
}