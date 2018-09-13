package com.wo.bu.dong.sample.web;

import java.util.concurrent.TimeUnit;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.wo.bu.dong.common.base.ResultDTO;
import com.wo.bu.dong.common.exception.LockException;
import com.wo.bu.dong.common.lock.BusinessPrefixEnum;
import com.wo.bu.dong.common.lock.SysLock;

import lombok.extern.slf4j.Slf4j;

@Controller
@Slf4j
@RequestMapping("lock")
public class LockController {
    @Autowired
    private SysLock lock;

    @GetMapping("demo")
    @ResponseBody
    public ResultDTO<Void> lock() {
        log.info("lock==> begin");
        ResultDTO<Void> result = null;
        String uniqueKey = System.currentTimeMillis() + "";
        log.info("lock,uniqueKey:{}", uniqueKey);
        boolean isLocked = false;
        try {
            //加锁
            isLocked = lock.tryLock(BusinessPrefixEnum.SAMPLE, uniqueKey, 60);
            if (!isLocked) {
                throw new LockException("未获取到锁");
            }
            //业务处理
            result = new ResultDTO<>();
            TimeUnit.SECONDS.sleep(20);
            result.setDetail("业务处理成功");
        } catch (Exception e) {
            log.error("lock, 异常", e);
            result = ResultDTO.getResultOfSysException(e.getMessage());
        } finally {
            //解锁
            if (isLocked) {
                if (!lock.unlock(BusinessPrefixEnum.SAMPLE, uniqueKey)) {
                    log.error("lock,解锁失败");
                }
            }
        }
        log.info("lock, result={}", result);
        log.info("lock==> end");
        return result;
    }

}
