package com.Internship.Backend.security.services;


import java.time.Instant;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.Internship.Backend.exception.TokenRefreshException;
import com.Internship.Backend.models.RefreshToken;
import com.Internship.Backend.models.UserModel;
import com.lowagie.text.List;
import com.Internship.Backend.mappers.RefreshTokenMapper;
import com.Internship.Backend.mappers.UserMapper;

@Service
public class RefreshTokenService {
  @Value("${Awash.Gms.jwtRefreshExpirationMs}")
  private Long refreshTokenDurationMs;

  @Autowired
  private RefreshTokenMapper refreshTokenRepository;

  @Autowired
  private UserMapper userRepository;

  public Optional<RefreshToken> findByToken(String token) {
     
    Optional<RefreshToken> response = refreshTokenRepository.findByToken(token);
    // response.setUser(user);
    return response;
  }

  public UserModel getUsersDetail(String token){
        UserModel user = refreshTokenRepository.getUserByToken(token);
        return user;
  }

  public RefreshToken createRefreshToken(Long userId, String ipAddress,  String browser, String browserVersion) {
    RefreshToken refreshToken = new RefreshToken();

    refreshToken.setUser(userRepository.findById(userId).get());
    refreshToken.setExpiryDate(Instant.now().plusMillis(refreshTokenDurationMs));
    refreshToken.setToken(UUID.randomUUID().toString());
     RefreshToken isExist = refreshTokenRepository.isTokenExist(userId, ipAddress, browser, browserVersion);
     if(isExist!=null){
       refreshTokenRepository.updateRefresh(refreshToken.getUser().getId(), refreshToken.getToken(), refreshToken.getExpiryDate());
     }else{
       refreshTokenRepository.save(refreshToken.getUser().getId(), refreshToken.getToken(), refreshToken.getExpiryDate(), ipAddress, browser, browserVersion);
     }
   
     return refreshToken;
  }

  public RefreshToken verifyExpiration(RefreshToken token) {
    if (token.getExpiryDate().compareTo(Instant.now()) < 0) {
      refreshTokenRepository.delete(token.getToken());
      throw new TokenRefreshException(token.getToken(), "Refresh token was expired. Please make a new signin request");
    }

    return token;
  }

  @Transactional
  public int deleteByUserId(Long userId) {
    return refreshTokenRepository.deleteByUser(userId);
  }
}