package org.springconfiguration;

import org.dto.LoginDto;
import org.dto.WishlistDto;
import org.services.AuthApi;
import org.services.GiftsApi;
import org.services.UserApi;
import org.services.WishlistApi;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;

@Configuration
public class TestConfiguration {
  private final String single = "singleton";
  private final String proto = "prototype";
  private final String authorizationUserDto = "authorizationUserDto";
  private final String authorizationUserWishlistDto = "authorizationUserWishlistDto";
  private final String authorizationUserWishlistEditedDto = "authorizationUserWishlistEditedDto";
  private final String baseUser = "base.user";
  private final String basePass = "base.pass";
  private final String testWishlistTitle = "Тестовый вишлист";
  private final String testWishlistDescription = "Описание тестового вишлиста";
  private final String testWishlistEditedTitle = "Тестовый отредактированный вишлист";
  private final String testWishlistEditedDescription = "Отредактированное описание тестового "
      + "вишлиста";

  @Bean(name = authorizationUserDto)
  @Scope(single)
  public LoginDto getAuthorizationUserDto() {
    return new LoginDto(System.getProperty(baseUser), System.getProperty(basePass));
  }

  @Bean
  @Scope(single)
  public AuthApi getAuthApi() {
    return new AuthApi();
  }

  @Bean
  @Scope(single)
  public UserApi getUserApi() {
    return new UserApi();
  }

  @Bean
  @Scope(single)
  public WishlistApi getWishlistApi() {
    return new WishlistApi();
  }

  @Bean
  @Scope(single)
  public GiftsApi getGiftsApi() {
    return new GiftsApi();
  }

  @Bean(name = authorizationUserWishlistDto)
  @Scope(single)
  public WishlistDto getAuthorizationUserOriginalWishlistDto() {
    return new WishlistDto(testWishlistTitle, testWishlistDescription);
  }

  @Bean(name = authorizationUserWishlistEditedDto)
  @Scope(single)
  public WishlistDto getAuthorizationUserEditedWishlistDto() {
    return new WishlistDto(testWishlistEditedTitle, testWishlistEditedDescription);
  }
}
