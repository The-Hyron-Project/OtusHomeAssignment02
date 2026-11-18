package org.springconfiguration;

import org.DTO.LoginDto;
import org.DTO.WishlistDto;
import org.services.AuthApi;
import org.services.UserApi;
import org.services.WishlistApi;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;

@Configuration
public class TestConfiguration {
  private final String single = "singleton";
  private final String proto = "prototype";
  private final String authorizationUserDTO = "authorizationUserDTO";
  private final String authorizationUserWishlistDTO = "authorizationUserWishlistDTO";
  private final String baseUser = "base.user";
  private final String basePass = "base.pass";
  private final String testWishlistTitle = "Тестовый вишлист";
  private final String testWishlistDescription = "Описание тестового вишлиста";


  @Bean(name = authorizationUserDTO)
  @Scope(single)
  public LoginDto getAuthorizationUserDTO(){
    return new LoginDto(
        System.getProperty(baseUser),
        System.getProperty(basePass)
    );
  }

  @Bean
  @Scope(single)
  public AuthApi getAuthApi(){
    return new AuthApi();
  }

  @Bean
  @Scope(single)
  public UserApi getUserApi(){
    return new UserApi();
  }

  @Bean
  @Scope(single)
  public WishlistApi getWishlistApi(){
    return new WishlistApi();
  }

  @Bean(name = authorizationUserWishlistDTO)
  @Scope(single)
  public WishlistDto getWishlistDto(){
    return new WishlistDto(testWishlistTitle,
        testWishlistDescription
    );
  }
}
