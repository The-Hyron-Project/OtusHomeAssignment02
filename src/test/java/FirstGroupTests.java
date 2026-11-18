import org.DTO.LoginDto;
import org.DTO.WishlistDto;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.objects.Login;
import org.objects.User;
import org.objects.Wishlist;
import org.services.AuthApi;
import org.services.UserApi;
import org.services.WishlistApi;
import org.springconfiguration.TestConfiguration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.SoftAssertions.assertSoftly;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = TestConfiguration.class)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
public class FirstGroupTests {

  @Autowired
  @Qualifier("authorizationUserDTO")
  LoginDto loginDto;

  @Autowired
  AuthApi authApi;

  @Autowired
  UserApi userApi;

  @Autowired
  WishlistApi wishlistApi;

  Login login;
  User user;
  Wishlist wishlist;

  private String idIsEmptyMessage = "Идентификатор пуст";
  private String titleDiffersMessage = "Заголовок отличается";
  private String desciptionDiffersMessage = "Описание отличается";
  private String usernameDiffersMessage = "Имя пользователя отличается";
  private String emailDiffersMessage = "Почта пользователя отличается";
  private String gistlistNotEmptyMessage = "Список подарков не пустой";
  private String gistlistNotPresentMessage = "Список подарков отсустствует в списке";
  private String gistlistNoDeletedMessage = "Список подарков не удалён";

//  Данный тест проверяет возможность создания списка подарков
  @Test
  public void CreateAndFindWishlist01Test(@Autowired @Qualifier("authorizationUserWishlistDTO") WishlistDto wishlistDto){
    login = authApi.authorize(loginDto);
    user = userApi.getCurrentUser(login);
    wishlist = wishlistApi.createWishlist(login.getToken(), wishlistDto);

    try {
      assertSoftly(softly -> {
        softly.assertThat(wishlist.getId()).as(idIsEmptyMessage).isNotNull();
        softly.assertThat(wishlist.getTitle()).as(titleDiffersMessage).isEqualTo(wishlistDto.getTitle());
        softly.assertThat(wishlist.getDescription()).as(desciptionDiffersMessage).isEqualTo(wishlistDto.getDescription());
        softly.assertThat(wishlist.getOwner().getUsername()).as(usernameDiffersMessage).isEqualTo(user.getUsername());
        softly.assertThat(wishlist.getOwner().getEmail()).as(emailDiffersMessage).isEqualTo(user.getEmail());
        softly.assertThat(wishlist.getGifts()).as(gistlistNotEmptyMessage).isNullOrEmpty();

        assertThat(userApi.getUserWishlists(login, user)).as(gistlistNotPresentMessage).contains(wishlist);
      });

    } finally {
      wishlistApi.deleteWishlist(login.getToken(), wishlist);

      assertThat(userApi.getUserWishlists(login, user)).as(gistlistNoDeletedMessage).doesNotContain(wishlist);
    }
  }
}
