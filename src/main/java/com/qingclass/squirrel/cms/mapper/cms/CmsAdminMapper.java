
package com.qingclass.squirrel.cms.mapper.cms;

import com.qingclass.squirrel.cms.entity.cms.CmsAdmin;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CmsAdminMapper {

    @Select({
            "<script>",
            "select id, loginName, password, userName, plain, roleName from squirrel_cms_admins",
            "where loginName = #{loginName, jdbcType=VARCHAR}",
            "</script>"
    })
    CmsAdmin SelectAdminByLoginName(String loginName);

    @Select({
            "<script>",
            "select id, loginName, password, userName, plain, roleName from squirrel_cms_admins",
            "where id = #{id, jdbcType=INTEGER}",
            "</script>"
    })
    CmsAdmin SelectAdminById(Integer id);

    @Select({
            "<script>",
            "select id, loginName, password, userName, plain, roleName from squirrel_cms_admins",
            "where id = #{id, jdbcType=INTEGER} and password = #{password}",
            "</script>"
    })
    CmsAdmin SelectAdminByIdAndPwd(@Param("id") Integer id,@Param("password") String password);

    @Select({
            "<script>",
            "select id, loginName, password, userName, plain, roleName from squirrel_cms_admins order by id desc",
            "</script>"
    })
    List<CmsAdmin> SelectAll();

    @Insert({
            "<script>",
            "insert into squirrel_cms_admins(loginName,password,userName,plain,roleName)",
            "values(#{loginName},#{password},#{userName},#{plain},#{roleName})",
            "</script>"
    })
    int insert(CmsAdmin cmsAdmin);

    @Update({
            "<script>",
            "update squirrel_cms_admins set",
            "password = #{password}",
            "where id = #{id}",
            "</script>"
    })
    int update(CmsAdmin cmsAdmin);

    @Update({
            "<script>",
            "update squirrel_cms_admins set",
            "<if test='loginName != null'>",
            "loginName = #{loginName},",
            "</if>",
            "<if test='userName != null'>",
            "userName = #{userName},",
            "</if>",
            "<if test='roleName != null'>",
            "roleName = #{roleName}",
            "</if>",
            "where id = #{id}",
            "</script>"
    })
    int updateByPrimaryKey(CmsAdmin cmsAdmin);

    @Delete({
            "<script>",
            "delete from squirrel_cms_admins",
            "where id = #{id}",
            "</script>"
    })
    int delete(Integer cmsAdmin);
}
