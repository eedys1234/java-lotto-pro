package lotto.ui;

public interface InputView {

    Long readPurchaseAmount();
    String readWinLottoNumbers();
    String readWinBonusLottoNumber();
    int readManualLottosCount();
    String readManualLottosNumber();
}
