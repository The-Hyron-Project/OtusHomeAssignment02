import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.SoftAssertions.assertSoftly;

import org.dto.LoginDto;
import org.dto.WishlistDto;
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

  @Autowired
  @Qualifier("authorizationUserWishlistDTO")
  WishlistDto wishlistDto;

  Login login;
  User user;
  Wishlist wishlist;
  Wishlist editedWishlist;

  private String idIsEmptyMessage = "Идентификатор пуст";
  private String idDiffersMessage = "Идентификатор отличается";
  private String titleDiffersMessage = "Заголовок отличается";
  private String desciptionDiffersMessage = "Описание отличается";
  private String usernameDiffersMessage = "Имя пользователя отличается";
  private String emailDiffersMessage = "Почта пользователя отличается";
  private String gistlistNotEmptyMessage = "Список подарков не пустой";
  private String gistlistNotPresentMessage = "Список подарков отсустствует в списке";
  private String gistlistNoDeletedMessage = "Список подарков не удалён";
  private String gistlistDiffersMessage = "Списоки подарков отличаются";
  private String gistlistCreatedMessage = "Список подарков создался";
  private String space = "";
  private String wishlistDescriptionWithNoTitle = "Описание списка без названия";

  private int forbiddenResponseCode = 403;


  //  Данный тест проверяет возможность создания списка подарков
  @Test
  public void CreateAndFindWishlist01Test() {
    login = authApi.authorize(loginDto);
    user = userApi.getCurrentUser(login);
    wishlist = wishlistApi.createWishlistWithReturnValue(login, wishlistDto);

    try {
      assertSoftly(softly -> {
        softly.assertThat(wishlist.getId()).as(idIsEmptyMessage).isNotNull();
        softly.assertThat(wishlist.getTitle()).as(titleDiffersMessage)
            .isEqualTo(wishlistDto.getTitle());
        softly.assertThat(wishlist.getDescription()).as(desciptionDiffersMessage)
            .isEqualTo(wishlistDto.getDescription());
        softly.assertThat(wishlist.getOwner().getUsername()).as(usernameDiffersMessage)
            .isEqualTo(user.getUsername());
//        softly.assertThat(wishlist.getOwner().getEmail()).as(emailDiffersMessage).isEqualTo
//        (user.getEmail());
        softly.assertThat(wishlist.getGifts()).as(gistlistNotEmptyMessage).isNullOrEmpty();

        assertThat(userApi.getUserWishlists(login, user))
            .as(gistlistNotPresentMessage).contains(wishlist);
      });

    } finally {
      wishlistApi.deleteWishlist(login, wishlist);

      assertThat(userApi.getUserWishlists(login, user))
          .as(gistlistNoDeletedMessage).doesNotContain(wishlist);
    }
  }

  //  Данный тест проверяет возможность создания и изменения списка подарков
  @Test
  public void CreateAndChangeWishlist02Test(@Autowired @Qualifier(
      "authorizationUserWishlistEditedDTO") WishlistDto editedWishlistDto) {
    login = authApi.authorize(loginDto);
    user = userApi.getCurrentUser(login);
    wishlist = wishlistApi.createWishlistWithReturnValue(login, wishlistDto);

    try {
      assertSoftly(softly -> {
        softly.assertThat(wishlist.getId()).as(idIsEmptyMessage).isNotNull();
        softly.assertThat(wishlist.getTitle())
            .as(titleDiffersMessage).isEqualTo(wishlistDto.getTitle());
        softly.assertThat(wishlist.getDescription())
            .as(desciptionDiffersMessage).isEqualTo(wishlistDto.getDescription());
        softly.assertThat(wishlist.getOwner().getUsername())
            .as(usernameDiffersMessage).isEqualTo(user.getUsername());
//        softly.assertThat(wishlist.getOwner().getEmail()).as(emailDiffersMessage).isEqualTo
//        (user.getEmail());
        softly.assertThat(wishlist.getGifts()).as(gistlistNotEmptyMessage).isNullOrEmpty();

        assertThat(wishlistApi.getWishlistById(login, wishlist))
            .as(gistlistDiffersMessage).isEqualTo(wishlist);

        editedWishlist = wishlistApi.editWishlist(login, wishlist, editedWishlistDto);

        softly.assertThat(editedWishlist.getId())
            .as(idDiffersMessage).isEqualTo(wishlist.getId());
        softly.assertThat(editedWishlist.getTitle())
            .as(titleDiffersMessage).isEqualTo(editedWishlistDto.getTitle());
        softly.assertThat(editedWishlist.getDescription())
            .as(desciptionDiffersMessage).isEqualTo(editedWishlistDto.getDescription());
        softly.assertThat(editedWishlist.getOwner().getUsername())
            .as(usernameDiffersMessage).isEqualTo(user.getUsername());
//        softly.assertThat(editedWishlist.getOwner().getEmail()).as(emailDiffersMessage)
//        .isEqualTo(user.getEmail());
        softly.assertThat(editedWishlist.getGifts()).as(gistlistNotEmptyMessage).isNullOrEmpty();

        assertThat(userApi.getUserWishlists(login, user))
            .as(gistlistNotPresentMessage).contains(editedWishlist);

      });

    } finally {
      wishlistApi.deleteWishlist(login, wishlist);
      wishlistApi.deleteWishlist(login, editedWishlist);

      assertSoftly(softly -> {
        assertThat(userApi.getUserWishlists(login, user))
            .as(gistlistNoDeletedMessage).doesNotContain(wishlist);
        assertThat(userApi.getUserWishlists(login, user))
            .as(gistlistNoDeletedMessage).doesNotContain(editedWishlist);
      });
    }
  }

  //  Данный тест проверяет невозможность создания списка подарков без авторизации
  @Test
  public void CreateWishlistNoAuthNegativeTest() {
    assertThat(wishlistApi.createWishlist(new Login(space), wishlistDto)
        .extract().statusCode()).as(gistlistCreatedMessage).isEqualTo(forbiddenResponseCode);
  }

  //  Данный тест проверяет невозможность создания списка подарков без названия
  @Test
  public void CreateWishlistNoTitleNegativeTest() {
    assertThat(wishlistApi.createWishlist(authApi.authorize(loginDto), new WishlistDto(space,
        wishlistDescriptionWithNoTitle)).extract().statusCode())
        .as(gistlistCreatedMessage).isEqualTo(forbiddenResponseCode);
  }
}
