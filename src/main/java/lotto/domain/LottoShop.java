package lotto.domain;

import lotto.ui.ConsoleInputView;
import lotto.ui.ConsoleResultView;
import lotto.ui.InputView;
import lotto.ui.ResultView;

import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Stream;

import static java.util.Comparator.*;
import static java.util.stream.Collectors.toList;

public class LottoShop {

    private final InputView inputView;
    private final ResultView resultView;
    private final LottoMachine lottoMachine;

    public LottoShop() {
        this.inputView = new ConsoleInputView();
        this.resultView = new ConsoleResultView();
        this.lottoMachine = new LottoMachine(new RandomNumberGenerator());
    }

    public void open() {
        Amount amount = readAmount();
        Lottos lottos = purchaseLottos(amount);
        resultView.printPurchaseAckMessage(lottos.count());
        resultView.printLottos(lottos);

        LottoWinReader lottoWinReader = makeLottoWinReader();

        Map<Winnings, Integer> statistic = giveStatistic(lottoWinReader, lottos);
        resultView.printCorrespondLottoNumber(statistic);
        Revenue revenue = getRevenue(amount, statistic);
        resultView.printTotalRevenueMessage(revenue.profitRate());
    }

    private Amount readAmount() {
        try {
            resultView.printPurchaseAmountMessage();
            return new Amount(Long.parseLong(inputView.readAmount()));
        } catch (IllegalArgumentException e) {
            return readAmount();
        }
    }

    private Lottos purchaseLottos(Amount amount) {
        return lottoMachine.issueAuto(amount);
    }

    private LottoWinReader makeLottoWinReader() {
        try {
            resultView.printLastWinLottoNumbersMessage();
            String readLottoNumbers = inputView.readWinLottoNumbers();
            resultView.printBonusNumberInputMessage();
            String readBonusLottoNumber = inputView.readWinBonusLottoNumber();
            return LottoWinReader.make(readLottoNumbers, readBonusLottoNumber);
        } catch (IllegalArgumentException e) {
            return makeLottoWinReader();
        }
    }

    private Map<Winnings, Integer> giveStatistic(LottoWinReader lottoWinReader, Lottos lottos) {
        resultView.printWinStatisticMessage();
        LottoStatistic lottoStatistic = lottoWinReader.distinguish(lottos);
        List<Winnings> winnings = Arrays.stream(Winnings.values()).collect(toList());
        return lottoStatistic.result(winnings);
    }

    private Revenue getRevenue(Amount amount, Map<Winnings, Integer> statistic) {
        return new Revenue(amount, statistic);
    }

}
