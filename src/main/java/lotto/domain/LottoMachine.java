package lotto.domain;

import java.util.Arrays;
import java.util.List;
import java.util.stream.LongStream;

import static java.util.stream.Collectors.*;

public class LottoMachine {

    public static final int LOTTO_PRICE = 1000;
    private static final int LOTTO_NUMBER_COUNT = 6;

    private LottoMachine() {
    }

    public static Lottos issueAuto(Amount amount, NumberSupplier numberSupplier) {
        long lottoCount = amount.divide(new Amount(LOTTO_PRICE));
        List<Lotto> lottos = LongStream.rangeClosed(1, lottoCount)
                .mapToObj(n -> issueAuto(numberSupplier))
                .collect(toList());

        return new Lottos(lottos);
    }

    private static Lotto issueAuto(NumberSupplier supplier) {
        return new Lotto(supplier.getAsInts(LOTTO_NUMBER_COUNT));
    }

    public static Lottos issueManual(List<String> numbers) {
        List<Lotto> lottos = numbers.stream()
                .map(Lotto::issue)
                .collect(toList());

        return new Lottos(lottos);
    }
}
