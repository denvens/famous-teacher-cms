
package com.qingclass.squirrel.cms.mapper.cms;

import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

@Repository
public interface BigbayMapper {

    @Select({
            "<script>",
            "select bigbaySignKey from squirrel_bigbay ",
            "where bigbayAppId = #{appId,jdbcType=VARCHAR}",
            "</script>"
    })
    String selectKeyByAppId(String appId);
}
