package lotto.domain;

import java.util.*;

import static java.util.stream.Collectors.toList;

public class Lotto implements Iterable<LottoNumber> {

    private final int LOTTO_LIMIT_COUNT = 6;
    private final String LOTTO_COUNT_OVER_ERROR_MESSAGE = "제한된 개수 이상으로 할당받았습니다.";
    private List<LottoNumber> lottoNumbers;

    public Lotto(List<Integer> numbers) {
        validateLottoCountRange(numbers);

        lottoNumbers = Collections.unmodifiableList(numbers.stream()
                .sorted()
                .map(LottoNumber::new)
                .collect(toList())
        );
    }

    private void validateLottoCountRange(List<Integer> numbers) {
        List<Integer> checkedNumbers = numbers.stream()
                .distinct()
                .collect(toList());

        if(checkedNumbers.size() != LOTTO_LIMIT_COUNT) {
            throw new IllegalArgumentException(LOTTO_COUNT_OVER_ERROR_MESSAGE);
        }
    }

    public Winnings acquireWinnings(Lotto winLotto, LottoNumber bonusNumber) {
        int correspondCount = correspondCount(winLotto);
        return Winnings.find(correspondCount, bonusCorrespondCount(bonusNumber));
    }

    private int correspondCount(Lotto winLotto) {
        return this.lottoNumbers.stream()
                .filter(winLotto::contains)
                .collect(toList())
                .size();
    }

    public static Lotto issue(String numbers) {
        return new Lotto(
                Arrays.stream(numbers.split(","))
                .map(String::trim)
                .map(Integer::parseInt)
                .distinct()
                .collect(toList())
        );
    }

    private boolean bonusCorrespondCount(LottoNumber bonusNumber) {
        return contains(bonusNumber);
    }

    public boolean contains(LottoNumber number) {
        return this.lottoNumbers.contains(number);
    }

    @Override
    public Iterator<LottoNumber> iterator() {
        return lottoNumbers.iterator();
    }

    @Override
    public String toString() {
        return lottoNumbers.toString();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Lotto that = (Lotto) o;
        return Objects.equals(lottoNumbers, that.lottoNumbers);
    }

    @Override
    public int hashCode() {
        return Objects.hash(lottoNumbers);
    }
}
