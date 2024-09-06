package com.Internship.Backend.mappers;

import java.time.Instant;
import java.util.Optional;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import com.Internship.Backend.models.RefreshToken;
import com.Internship.Backend.models.UserModel;

public interface RefreshTokenMapper {

    @Select("select * from tbl_refresh_token where token=#{token}")
    Optional<RefreshToken> findByToken(String token);

    @Delete("delete from tbl_refresh_token where token=#{token}")
    int delete(String token);

   
    @Insert("insert into tbl_refresh_token(user_id, token, expiryDate, ipaddress, browser, browser_version) values(#{id},#{token}, #{expiryDate}, #{ipaddress}, #{browser}, #{browser_version})")
    void save(Long id, String token, Instant expiryDate, String ipaddress, String browser, String browser_version);

    @Select("select u.username, u.id from tbl_user u, tbl_refresh_token rt where rt.token=#{token} and rt.user_id=u.id")
    UserModel getUserByToken(String token);

    @Delete("delete from tbl_refresh_token where user_id=#{id}")
    int deleteByUser(Long id);

    @Select("select * from tbl_refresh_token where user_id=#{userId} and ipaddress=#{ipaddress} and browser=#{browser} and browser_version=#{browser_version}")
    RefreshToken isTokenExist(Long userId, String ipaddress, String browser, String browser_version);

    @Select("select * from tbl_refresh_token where user_id=#{userId}")
    RefreshToken isUserLoggedIn(Long userId);

    @Update("update tbl_refresh_token set token =#{token}, expiryDate=#{expiryDate} where user_id=#{id}")
    void updateRefresh(Long id, String token, Instant expiryDate);
}
