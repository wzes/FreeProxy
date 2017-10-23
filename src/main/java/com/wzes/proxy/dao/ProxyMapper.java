package com.wzes.proxy.dao;

import com.wzes.proxy.domain.Proxy;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface ProxyMapper {
    /**
     * @param
     * @return
     */
    @Insert("INSERT INTO proxy (ip, port, location) VALUES (#{ip},#{port},#{location});")
    int insert(Proxy proxy);

    @Select("select ip, port, location from proxy")
    List<Proxy> get();
}
