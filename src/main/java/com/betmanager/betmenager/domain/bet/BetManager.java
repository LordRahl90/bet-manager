package com.betmanager.betmenager.domain.bet;

import com.betmanager.betmenager.data.bets.Bet;
import com.betmanager.betmenager.data.bets.BetRepository;
import com.betmanager.betmenager.dto.request.BetRequest;
import com.betmanager.betmenager.dto.response.BetResponse;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class BetManager implements BetService {
    private final BetRepository betRepository;

    public BetManager(BetRepository betRepository) {
        this.betRepository = betRepository;
    }

    @Override
    public void save(BetRequest bet) {
        Bet newBet = Bet.builder().
                id(UUID.randomUUID()).
                oddsFavour(bet.getOddsFor()).
                oddsAgainst(bet.getOddsAgainst()).
                build();
        betRepository.save(newBet);
    }

    @Override
    public Optional<BetResponse> load(UUID id) {
        Bet bet = this.betRepository.findById(id).orElse(null);
        if (bet == null) {
            return Optional.empty();
        }
        BetResponse response = BetResponse.builder().
                id(bet.getId()).
                oddsFor(bet.getOddsFavour()).
                oddsAgainst(bet.getOddsAgainst()).
                build();

        return Optional.of(response);
    }

    @Override
    public List<BetResponse> loadPaginated(int page, int size) {
        List<Bet> bets = this.betRepository.paginate(page, size);
        return bets.stream().map(bet -> BetResponse.builder().
                id(bet.getId()).
                oddsFor(bet.getOddsFavour()).
                oddsAgainst(bet.getOddsAgainst()).
                createdAt(bet.getCreatedAt()).
                build()).toList();
    }

    @Override
    public void delete(UUID id) {
        this.betRepository.deleteById(id);
    }
}
