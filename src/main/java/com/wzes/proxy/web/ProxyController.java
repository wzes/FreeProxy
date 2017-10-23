package com.wzes.proxy.web;

import com.wzes.proxy.dao.ProxyMapper;
import com.wzes.proxy.domain.Proxy;
import com.wzes.proxy.service.processor.ENCrawler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class ProxyController {

    @Autowired
    ProxyMapper proxyMapper;

    @RequestMapping(value = "/proxys", method = RequestMethod.GET,
            produces = "application/json;charset=UTF-8")
    @ResponseBody
    public List<Proxy> getProxy(){
        List<Proxy> canUseProxys = ENCrawler.getCanUseProxys(10000);
        for (Proxy proxy : canUseProxys){
            if(proxy.getLocation() != null)
                try {
                    proxyMapper.insert(proxy);
                }catch (Exception e){
                    System.out.println(e.getMessage());
                }
        }
        return canUseProxys;
    }
}
