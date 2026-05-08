package com.strongmemoryapi.dto.game;

import com.strongmemoryapi.dto.matchhistory.MatchPlayedDTO;
import com.strongmemoryapi.dto.matchhistory.DrawnWordDTO;
import jakarta.validation.constraints.NotNull;

import java.util.List;

public record GameDataDTO(

   @NotNull(message = "Os dados da partida precisam estar definidos.")
   MatchPlayedDTO match,

   @NotNull(message = "As palavra sorteadas da partida precisam estar definidas.")
   List<DrawnWordDTO> drawnWords

) {}
