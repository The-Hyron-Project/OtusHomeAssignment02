import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.SoftAssertions.assertSoftly;

import org.dto.LoginDto;
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
public class ThirdGroupTests {
  @Autowired
  @Qualifier("authorizationUserDto")
  LoginDto loginDto;

  @Autowired
  AuthApi authApi;

  @Autowired
  UserApi userApi;

  @Autowired
  WishlistApi wishlistApi;

  Login login;
  User user;
  Wishlist[] wishlistArray;

  private String wishlistArrayLenthDiffersMessage = "Размер массива гифтлистов отличается";
  private int wishlistArrayAndGiftListExpectedLenth = 1;
  private int wishlistArrayNegativeExpectedLenth = 0;
  private String gistlistIsEmptyMessage = "Список подарков пустой";
  private String idIsEmptyMessage = "Идентификатор пуст";
  private String titleDiffersMessage = "Заголовок отличается";
  private String desciptionDiffersMessage = "Описание отличается";
  private String usernameDiffersMessage = "Имя пользователя отличается";
  private String emailDiffersMessage = "Почта пользователя отличается";
  private String imageUrlDiffersMessage = "Ссылка на изображение отличается";
  private String priceDiffersMessage = "Цена отличается";
  private String storeUrlDiffersMessage = "Ссылка на магазин отличается";
  private String reservedStateDiffersMessage = "Состояние резервирования отличается";
  private String gistlistFoundMessage = "Список подарков найден";

  private String expectedGiftListName = "Список желаний Джефа";
  private String expectedGiftListDescription = "Список вещей, которые хочет Джефф";
  private String expectedGiftName = "160шт.Конструктор Alyx Half-Life 2 - Brickheadz Набор строительных блоков";
  private String expectedGiftDescription = "Фигурка Аликс Венс";
  private String expectedGiftImageUrl = "https://ir.ozone.ru/s3/multimedia-1-b/wc1000/10855036955.jpg";
  private Double expectedGiftPrice = 2515.0;
  private String expectedGiftStoreUrl = "https://www.ozon.ru/product/160sht-konstruktor-alyx-half-life-2-brickheadz-nabor-stroitelnyh-blokov-4465119782/";
  private Boolean expectedGiftReservedState = false;
  private String nonExistentUserId =  "00000000-0000-0000-0000-000000000000";

  //  Данный тест проверяет возможность получения списка желаний другого пользователя
//  через /wishlists/user/{userId}
  @Test
  public void GetAnotherUserWishlist01Test() {
    login = authApi.authorize(loginDto);
    user = userApi.getSecondaryUser(login);
    wishlistArray = wishlistApi.getUserWishlistsWithReturnValue(login, user);

    assertThat(wishlistArray.length)
        .as(wishlistArrayLenthDiffersMessage).isEqualTo(wishlistArrayAndGiftListExpectedLenth);

    assertThat(wishlistArray[0].getGifts().size())
        .as(gistlistIsEmptyMessage).isEqualTo(wishlistArrayAndGiftListExpectedLenth);

    assertSoftly(softly -> {
      softly.assertThat(wishlistArray[0].getId()).as(idIsEmptyMessage).isNotNull();
      softly.assertThat(wishlistArray[0].getTitle()).as(titleDiffersMessage)
          .isEqualTo(expectedGiftListName);
      softly.assertThat(wishlistArray[0].getDescription()).as(desciptionDiffersMessage)
          .isEqualTo(expectedGiftListDescription);
      softly.assertThat(wishlistArray[0].getOwner().getUsername()).as(usernameDiffersMessage)
          .isEqualTo(user.getUsername());
//      softly.assertThat(wishlistArray[0].getOwner().getEmail())
//            .as(emailDiffersMessage).isEqualTo(user.getEmail());
      softly.assertThat(wishlistArray[0].getGifts().size())
          .as(gistlistIsEmptyMessage).isEqualTo(wishlistArrayAndGiftListExpectedLenth);
      softly.assertThat(wishlistArray[0].getGifts().getFirst().getId())
          .as(idIsEmptyMessage).isNotNull();
      softly.assertThat(wishlistArray[0].getGifts().getFirst().getName())
          .as(titleDiffersMessage).isEqualTo(expectedGiftName);
      softly.assertThat(wishlistArray[0].getGifts().getFirst().getDescription())
          .as(desciptionDiffersMessage)
          .isEqualTo(expectedGiftDescription);
      softly.assertThat(wishlistArray[0].getGifts().getFirst().getImageUrl())
          .as(imageUrlDiffersMessage)
          .isEqualTo(expectedGiftImageUrl);
      softly.assertThat(wishlistArray[0].getGifts().getFirst().getPrice())
          .as(priceDiffersMessage).isEqualTo(expectedGiftPrice);
      softly.assertThat(wishlistArray[0].getGifts().getFirst().getStoreUrl())
          .as(storeUrlDiffersMessage)
          .isEqualTo(expectedGiftStoreUrl);
      softly.assertThat(wishlistArray[0].getGifts().getFirst().getReserved())
          .as(reservedStateDiffersMessage)
          .isEqualTo(expectedGiftReservedState);
    });
  }

//  Данный тест проверяет возможность получения списка желаний другого пользователя
//  через /users/{userId}/wishlists
  @Test
  public void GetAnotherUserWishlist02Test() {
    login = authApi.authorize(loginDto);
    user = userApi.getSecondaryUser(login);
    wishlistArray = userApi.getUserWishlistsWithReturnValue(login, user);

    assertThat(wishlistArray.length)
        .as(wishlistArrayLenthDiffersMessage).isEqualTo(wishlistArrayAndGiftListExpectedLenth);

    assertThat(wishlistArray[0].getGifts().size())
        .as(gistlistIsEmptyMessage).isEqualTo(wishlistArrayAndGiftListExpectedLenth);

    assertSoftly(softly -> {
      softly.assertThat(wishlistArray[0].getId()).as(idIsEmptyMessage).isNotNull();
      softly.assertThat(wishlistArray[0].getTitle()).as(titleDiffersMessage)
          .isEqualTo(expectedGiftListName);
      softly.assertThat(wishlistArray[0].getDescription()).as(desciptionDiffersMessage)
          .isEqualTo(expectedGiftListDescription);
      softly.assertThat(wishlistArray[0].getOwner().getUsername()).as(usernameDiffersMessage)
          .isEqualTo(user.getUsername());
//      softly.assertThat(wishlistArray[0].getOwner().getEmail())
//            .as(emailDiffersMessage).isEqualTo(user.getEmail());
      softly.assertThat(wishlistArray[0].getGifts().size())
          .as(gistlistIsEmptyMessage).isEqualTo(wishlistArrayAndGiftListExpectedLenth);
      softly.assertThat(wishlistArray[0].getGifts().getFirst().getId())
          .as(idIsEmptyMessage).isNotNull();
      softly.assertThat(wishlistArray[0].getGifts().getFirst().getName())
          .as(titleDiffersMessage).isEqualTo(expectedGiftName);
      softly.assertThat(wishlistArray[0].getGifts().getFirst().getDescription())
          .as(desciptionDiffersMessage)
          .isEqualTo(expectedGiftDescription);
      softly.assertThat(wishlistArray[0].getGifts().getFirst().getImageUrl())
          .as(imageUrlDiffersMessage)
          .isEqualTo(expectedGiftImageUrl);
      softly.assertThat(wishlistArray[0].getGifts().getFirst().getPrice())
          .as(priceDiffersMessage).isEqualTo(expectedGiftPrice);
      softly.assertThat(wishlistArray[0].getGifts().getFirst().getStoreUrl())
          .as(storeUrlDiffersMessage)
          .isEqualTo(expectedGiftStoreUrl);
      softly.assertThat(wishlistArray[0].getGifts().getFirst().getReserved())
          .as(reservedStateDiffersMessage)
          .isEqualTo(expectedGiftReservedState);
    });
  }

//  Данный тест проверяет невозможность получения списка подарков
//несуществующего пользователя через /wishlists/user/{userId}
  @Test
  public void GetWishlistOfNonexistentUserNegative01Test() {
    assertThat(wishlistApi.getUserWishlistsWithReturnValue(authApi.authorize(loginDto),
        User.builder().id(nonExistentUserId).build()).length)
        .as(gistlistFoundMessage).isEqualTo(wishlistArrayNegativeExpectedLenth);
  }

  //  Данный тест проверяет невозможность получения списка подарков
//несуществующего пользователя через через /users/{userId}/wishlists
  @Test
  public void GetWishlistOfNonexistentUserNegative02Test() {
    assertThat(userApi.getUserWishlistsWithReturnValue(authApi.authorize(loginDto),
        User.builder().id(nonExistentUserId).build()).length)
        .as(gistlistFoundMessage).isEqualTo(wishlistArrayNegativeExpectedLenth);
  }
}
