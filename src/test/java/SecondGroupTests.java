import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.SoftAssertions.assertSoftly;

import org.dto.GiftDto;
import org.dto.LoginDto;
import org.dto.WishlistDto;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.objects.Gift;
import org.objects.Login;
import org.objects.User;
import org.objects.Wishlist;
import org.prepare.GiftDtoArray;
import org.services.AuthApi;
import org.services.GiftsApi;
import org.services.UserApi;
import org.services.WishlistApi;
import org.springconfiguration.TestConfiguration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = TestConfiguration.class)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
public class SecondGroupTests {

  @Autowired
  @Qualifier("authorizationUserDto")
  LoginDto loginDto;

  @Autowired
  AuthApi authApi;

  @Autowired
  UserApi userApi;

  @Autowired
  GiftsApi giftsApi;

  @Autowired
  WishlistApi wishlistApi;

  @Autowired
  @Qualifier("authorizationUserWishlistDto")
  WishlistDto wishlistDto;

  Login login;
  User user;
  Wishlist[] wishlistArray;
  Gift gift;

  private String wishlistArrayLenthDiffersMessage = "Размер массива гифтлистов отличается";
  private int wishlistArrayExpectedLenth = 1;
  private String gistlistNotEmptyMessage = "Список подарков не пустой";
  private String giftArraysDifferMessage = "Списоки подарков отличаются";
  private String idIsEmptyMessage = "Идентификатор пуст";
  private String titleDiffersMessage = "Заголовок отличается";
  private String desciptionDiffersMessage = "Описание отличается";
  private String giftsDifferMessage = "Подарки отличаются";
  private String imageUrlDiffersMessage = "Ссылка на изображение отличается";
  private String priceDiffersMessage = "Цена отличается";
  private String storeUrlDiffersMessage = "Ссылка на магазин отличается";
  private String reservedStateDiffersMessage = "Состояние резервирования отличается";
  private String giftFoundMessage = "Подарк найден";
  private String giftCreatedMessage = "Подарок создался";
  private String nonExistentUsedID = "3fdf37bd-3df6-4146-b920-78db93df24d7";

  private int expectedGiftListLenth = 1;
  private int expectedNotAuthorizedResponceCode = 401;
  private int forbiddenResponseCode = 403;

  //  Тест проверяет возможность доабвления подарков в список подарков
//  и их получения
  @Test
  public void CreateAndFindMostExpensiveAndCheapestGifts01Test() {
    login = authApi.authorize(loginDto);
    user = userApi.getCurrentUser(login);
    wishlistArray = wishlistApi.getUserWishlistsWithReturnValue(login, user);
    ArrayList<Gift> expectedGiftList = new ArrayList<>();

    assertThat(wishlistArray.length)
        .as(wishlistArrayLenthDiffersMessage).isEqualTo(wishlistArrayExpectedLenth);

    assertSoftly(softly -> {
      softly.assertThat(wishlistArray[0].getGifts()).as(gistlistNotEmptyMessage).isNullOrEmpty();
    });

    try {
          for(int i = 0;GiftDtoArray.giftDtoArray.length > i; i++) {
            gift = giftsApi.addGiftWithReturnValue(login, wishlistArray[0],
                GiftDtoArray.giftDtoArray[i]);
            expectedGiftList.add(gift);
            int index = i;
            assertSoftly(softly -> {
              softly.assertThat(gift.getId()).as(idIsEmptyMessage).isNotNull();
              softly.assertThat(gift.getName())
                  .as(titleDiffersMessage).isEqualTo(GiftDtoArray.giftDtoArray[index].getName());
              softly.assertThat(gift.getName())
                  .as(titleDiffersMessage).isEqualTo(GiftDtoArray.giftDtoArray[index].getName());
              softly.assertThat(gift.getDescription())
                  .as(desciptionDiffersMessage)
                  .isEqualTo(GiftDtoArray.giftDtoArray[index].getDescription());
              softly.assertThat(gift.getImageUrl())
                  .as(imageUrlDiffersMessage)
                  .isEqualTo(GiftDtoArray.giftDtoArray[index].getImageUrl());
              softly.assertThat(gift.getPrice())
                  .as(priceDiffersMessage).isEqualTo(GiftDtoArray.giftDtoArray[index].getPrice());
              softly.assertThat(gift.getStoreUrl())
                  .as(storeUrlDiffersMessage)
                  .isEqualTo(GiftDtoArray.giftDtoArray[index].getStoreUrl());
              softly.assertThat(gift.getReserved())
                  .as(reservedStateDiffersMessage)
                  .isEqualTo(GiftDtoArray.giftDtoArray[index].getReserved());
            });
          }

      wishlistArray = wishlistApi.getUserWishlistsWithReturnValue(login, user);

      assertThat(wishlistArray.length)
          .as(wishlistArrayLenthDiffersMessage).isEqualTo(wishlistArrayExpectedLenth);
      assertThat(wishlistArray[0].getGifts())
          .as(giftArraysDifferMessage).isEqualTo(expectedGiftList);

      assertSoftly(softly -> {
          softly.assertThat(Arrays.stream(expectedGiftList.toArray(Gift[]::new))
              .max(Comparator.comparing(Gift::getPrice))
              .get()).as(giftsDifferMessage).isEqualTo(
                  wishlistArray[0].getGifts().stream()
              .max(Comparator.comparing(Gift::getPrice))
              .get());

        softly.assertThat(Arrays.stream(expectedGiftList.toArray(Gift[]::new))
            .min(Comparator.comparing(Gift::getPrice))

            .get()).as(giftsDifferMessage).isEqualTo(
            wishlistArray[0].getGifts().stream()
                .min(Comparator.comparing(Gift::getPrice))
                .get());
      });
    } finally {
        for(int i = 0;expectedGiftList.size() > i; i++) {
          giftsApi.deleteGift(login, expectedGiftList.get(i));
        }

      wishlistArray = wishlistApi.getUserWishlistsWithReturnValue(login, user);

      assertThat(wishlistArray.length)
          .as(wishlistArrayLenthDiffersMessage).isEqualTo(wishlistArrayExpectedLenth);

      assertSoftly(softly -> {
        softly.assertThat(wishlistArray[0].getGifts()).as(gistlistNotEmptyMessage).isNullOrEmpty();
      });
    }
  }

  //  Тест проверяет возможность доабвления подарков в список подарков
//  и их получения
  @Test
  public void AddAndDeleteGiftAndCheckIfDeletedGiftIsUnavailable02Test() {
    login = authApi.authorize(loginDto);
    user = userApi.getCurrentUser(login);
    wishlistArray = wishlistApi.getUserWishlistsWithReturnValue(login, user);

    assertThat(wishlistArray.length)
        .as(wishlistArrayLenthDiffersMessage).isEqualTo(wishlistArrayExpectedLenth);

    assertSoftly(softly -> {
      softly.assertThat(wishlistArray[0].getGifts()).as(gistlistNotEmptyMessage).isNullOrEmpty();
    });

    gift = giftsApi.addGiftWithReturnValue(login, wishlistArray[0], GiftDtoArray.giftDtoArray[0]);

    try {
      assertSoftly(softly -> {
        softly.assertThat(gift.getId()).as(idIsEmptyMessage).isNotNull();
        softly.assertThat(gift.getName())
            .as(titleDiffersMessage).isEqualTo(GiftDtoArray.giftDtoArray[0].getName());
        softly.assertThat(gift.getName())
            .as(titleDiffersMessage).isEqualTo(GiftDtoArray.giftDtoArray[0].getName());
        softly.assertThat(gift.getDescription())
            .as(desciptionDiffersMessage).isEqualTo(GiftDtoArray.giftDtoArray[0].getDescription());
        softly.assertThat(gift.getImageUrl())
            .as(imageUrlDiffersMessage).isEqualTo(GiftDtoArray.giftDtoArray[0].getImageUrl());
        softly.assertThat(gift.getPrice())
            .as(priceDiffersMessage).isEqualTo(GiftDtoArray.giftDtoArray[0].getPrice());
        softly.assertThat(gift.getStoreUrl())
            .as(storeUrlDiffersMessage).isEqualTo(GiftDtoArray.giftDtoArray[0].getStoreUrl());
        softly.assertThat(gift.getReserved())
            .as(reservedStateDiffersMessage).isEqualTo(GiftDtoArray.giftDtoArray[0].getReserved());
      });

      wishlistArray = wishlistApi.getUserWishlistsWithReturnValue(login, user);

      assertThat(wishlistArray.length)
          .as(wishlistArrayLenthDiffersMessage).isEqualTo(wishlistArrayExpectedLenth);
      assertThat(wishlistArray[0].getGifts().size())
          .as(giftArraysDifferMessage).isEqualTo(expectedGiftListLenth);
      assertThat(wishlistArray[0].getGifts().getFirst())
          .as(giftArraysDifferMessage).isEqualTo(gift);
    } finally {
      giftsApi.deleteGift(login, gift);


      wishlistArray = wishlistApi.getUserWishlistsWithReturnValue(login, user);

      assertThat(wishlistArray.length)
          .as(wishlistArrayLenthDiffersMessage).isEqualTo(wishlistArrayExpectedLenth);

      assertSoftly(softly -> {
        softly.assertThat(wishlistArray[0].getGifts()).as(gistlistNotEmptyMessage).isNullOrEmpty();
      });

      assertThat(giftsApi.getGiftById(login, gift)
          .extract().statusCode())
          .as(giftFoundMessage).isEqualTo(expectedNotAuthorizedResponceCode);

    }
  }

  //  Данный тест проверяет невозможность создания подарка без обязательных полей
  @Test
  public void CreateWishlistNoTitleNegativeTest() {
    login = authApi.authorize(loginDto);
    user = userApi.getCurrentUser(login);
    wishlistArray = wishlistApi.getUserWishlistsWithReturnValue(login, user);

    assertThat(wishlistArray.length)
        .as(wishlistArrayLenthDiffersMessage).isEqualTo(wishlistArrayExpectedLenth);

    assertSoftly(softly -> {
      softly.assertThat(wishlistArray[0].getGifts()).as(gistlistNotEmptyMessage).isNullOrEmpty();
    });

    assertThat(giftsApi.addGift(login, wishlistArray[0], GiftDto.builder()
        .build()).extract().statusCode()).as(giftCreatedMessage).isEqualTo(forbiddenResponseCode);
  }

  //  Данный тест проверяет невозможность создания подарка в несуществующий список
  @Test
  public void CreateWishlistNoWishlistNegativeTest() {
    login = authApi.authorize(loginDto);

    assertThat(giftsApi.addGift(login, Wishlist.builder().id(nonExistentUsedID).build(),
            GiftDtoArray.giftDtoArray[0])
        .extract().statusCode()).as(giftCreatedMessage)
        .isEqualTo(expectedNotAuthorizedResponceCode);
  }
}
