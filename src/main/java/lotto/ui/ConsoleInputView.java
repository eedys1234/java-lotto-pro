package lotto.ui;

public class ConsoleInputView implements InputView {

    @Override
    public Long readPurchaseAmount() {
        try {
            return Long.parseLong(UIBufferedReaders.readLine());
        } catch (IllegalArgumentException e) {
            return readPurchaseAmount();
        }
    }

    @Override
    public String readWinLottoNumbers() {
        return UIBufferedReaders.readLine();
    }

    @Override
    public String readWinBonusLottoNumber() {
        return UIBufferedReaders.readLine();
    }

    @Override
    public int readManualLottosCount() {
        try {
            return Integer.parseInt(UIBufferedReaders.readLine());
        } catch (IllegalArgumentException e) {
            return readManualLottosCount();
        }
    }

    @Override
    public String readManualLottosNumber() {
        return UIBufferedReaders.readLine();
    }

}
