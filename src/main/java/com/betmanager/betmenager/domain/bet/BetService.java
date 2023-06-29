package com.betmanager.betmenager.domain.bet;

import com.betmanager.betmenager.dto.request.BetRequest;
import com.betmanager.betmenager.dto.response.BetResponse;

import java.util.List;
import java.util.Optional;

import java.util.UUID;

public interface BetService {
    void save(BetRequest bet);

    Optional<BetResponse> load(UUID id);

    List<BetResponse> loadPaginated(int page, int limit);

    void delete(UUID id);
}
