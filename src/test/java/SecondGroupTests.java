import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.InstanceOfAssertFactories.predicate;
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

  private String wishlistArrayLenthDiffersMessage = "Размер массива гифтлистов отличается";
  private int wishlistArrayExpectedLenth = 1;
  private String gistlistNotEmptyMessage = "Список подарков не пустой";
  private String giftArraysDifferMessage = "Списоки подарков отличаются";
  private String idIsEmptyMessage = "Идентификатор пуст";
  private String titleDiffersMessage = "Заголовок отличается";
  private String desciptionDiffersMessage = "Описание отличается";
  private String giftsDiffer = "Подарки отличаются";
  private String imageUrlDiffersMessage = "Ссылка на изображение отличается";
  private String priceDiffersMessage = "Цена отличается";
  private String storeUrlDiffersMessage = "Ссылка на магазин отличается";
  private String reservedStateDiffersMessage = "Состояние резервирования отличается";


  @Test
  public void CreateAndFindMostExpensiveAndCheapestGifts01Test() {
    login = authApi.authorize(loginDto);
    user = userApi.getCurrentUser(login);
    wishlistArray = userApi.getUserWishlists(login, user);
    ArrayList<Gift> expectedGiftList = new ArrayList<>();

    assertThat(wishlistArray.length)
        .as(wishlistArrayLenthDiffersMessage).isEqualTo(wishlistArrayExpectedLenth);

    assertSoftly(softly -> {
      softly.assertThat(wishlistArray[0].getGifts()).as(gistlistNotEmptyMessage).isNullOrEmpty();
    });

    try {
          for(int i = 0;GiftDtoArray.giftDtoArray.length > i; i++) {
            Gift gift = giftsApi.addGift(login, wishlistArray[0], GiftDtoArray.giftDtoArray[i]);
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

      wishlistArray = userApi.getUserWishlists(login, user);

      assertThat(wishlistArray.length)
          .as(wishlistArrayLenthDiffersMessage).isEqualTo(wishlistArrayExpectedLenth);
      assertThat(wishlistArray[0].getGifts())
          .as(giftArraysDifferMessage).isEqualTo(expectedGiftList);

      assertSoftly(softly -> {
          softly.assertThat(Arrays.stream(expectedGiftList.toArray(Gift[]::new))
              .max(Comparator.comparing(Gift::getPrice))
              .get()).as(giftsDiffer).isEqualTo(
                  wishlistArray[0].getGifts().stream()
              .max(Comparator.comparing(Gift::getPrice))
              .get());

        softly.assertThat(Arrays.stream(expectedGiftList.toArray(Gift[]::new))
            .min(Comparator.comparing(Gift::getPrice))

            .get()).as(giftsDiffer).isEqualTo(
            wishlistArray[0].getGifts().stream()
                .min(Comparator.comparing(Gift::getPrice))
                .get());
      });
    } finally {
        for(int i = 0;expectedGiftList.size() > i; i++) {
          giftsApi.deleteGift(login, expectedGiftList.get(i));
        }

      wishlistArray = userApi.getUserWishlists(login, user);

      assertThat(wishlistArray.length)
          .as(wishlistArrayLenthDiffersMessage).isEqualTo(wishlistArrayExpectedLenth);

      assertSoftly(softly -> {
        softly.assertThat(wishlistArray[0].getGifts()).as(gistlistNotEmptyMessage).isNullOrEmpty();
      });
    }
  }
}
