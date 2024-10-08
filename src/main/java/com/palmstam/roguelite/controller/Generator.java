package com.palmstam.roguelite.controller;

import com.palmstam.roguelite.model.databaseItems.Deal;
import com.palmstam.roguelite.model.databaseItems.GamblingGame;
import com.palmstam.roguelite.model.databaseItems.Puzzle;
import com.palmstam.roguelite.model.RollDice;
import com.palmstam.roguelite.model.room.*;
import com.palmstam.roguelite.repository.DealRepository;
import com.palmstam.roguelite.repository.EnemyRepository;
import com.palmstam.roguelite.repository.GamblingGamesRepository;
import com.palmstam.roguelite.repository.PuzzleRepository;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.Random;

public class Generator {
    Random random;

    @Autowired
    private final EnemyRepository enemyRepository;

    @Autowired
    private final DealRepository dealRepository;

    @Autowired
    private final GamblingGamesRepository gamblingGamesRepository;

    @Autowired
    private final PuzzleRepository puzzleRepository;

    public Generator(
            EnemyRepository enemyRepository,
            DealRepository dealRepository,
            GamblingGamesRepository gamblingGamesRepository,
            PuzzleRepository puzzleRepository
    ) {
        random = new Random();
        this.enemyRepository = enemyRepository;
        this.dealRepository = dealRepository;
        this.gamblingGamesRepository = gamblingGamesRepository;
        this.puzzleRepository = puzzleRepository;
    }

    public Room generateRoom(String encounterType, int level) {
        switch (encounterType) {
            case "Deal Room":
                return generateDealRoom(level);
            case "Gambling Room":
                return generateGamblingRoom(level);
            case "Puzzle Room":
                return generatePuzzleRoom(level);
            case "Rest Room":
                return generateRestRoom(level);
            case "Shop Room":
                return null;
            default:
                return null;
        }
    }

    private DealRoom generateDealRoom(int level) {
        List<Deal> deals = dealRepository.findAll();

        if (deals.isEmpty()) return null;

        Deal deal = chooseRandom(deals);
        return DealRoom.builder()
                .encounterType("Deal Room")
                .description("A place to make a shady deal...")
                .level(level)
                .roomDescription(deal.getRoomDescription())
                .deal(deal.getDeal())
                .build();
    }

    private GamblingRoom generateGamblingRoom(int level) {
        List<GamblingGame> games = gamblingGamesRepository.findAll();

        if (games.isEmpty()) return null;

        GamblingGame game = chooseRandom(games);

        int minimumBet = game.getMinimumBet();

        if (level >= 6 && level <= 10) {
            minimumBet = (int) Math.pow(minimumBet, 2); // bet^2
        } else if (level >= 11 && level <= 15) {
            minimumBet = (int) Math.pow(minimumBet, 3); // bet^3
        } else if (level >= 16 && level <= 20) {
            minimumBet = (int) Math.pow(minimumBet, 4); // bet^4
        }

        return GamblingRoom.builder()
                .encounterType("Gambling Room")
                .description("A place where the daring can soar to the heavens!")
                .level(level)
                .roomDescription(game.getRoomDescription())
                .gameType(game.getGameType())
                .rules(game.getRules())
                .minimumBet(minimumBet)
                .build();
    }

    private PuzzleRoom generatePuzzleRoom(int level) {
        List<Puzzle> puzzles = puzzleRepository.findAll();

        if (puzzles.isEmpty()) return null;

        Puzzle puzzle = chooseRandom(puzzles);
        return PuzzleRoom.builder()
                .encounterType("Puzzle Room")
                .description("A place of mysteries.")
                .level(level)
                .roomDescription(puzzle.getRoomDescription())
                .puzzleExplanation(puzzle.getPuzzleExplanation())
                .answer(puzzle.getAnswer())
                .reward(rollCurrencyRewardPerPerson(level, true, false))
                .build();
    }

    private RestRoom generateRestRoom(int level) {
        return RestRoom.builder()
                .encounterType("Rest Room")
                .description("A place to breathe out and regain your energies")
                .level(level)
                .roomDescription("An expansive room with a covered with soft grass. There's a small stream and a fruit tree to rest under")
                .build();
    }

    private ShopRoom generateShopRoom(int level) {
        return null; //TODO
    }

    private <T> T chooseRandom(List<T> itemList) {
        return itemList.get(random.nextInt(itemList.size()));
    }

    private int rollCurrencyRewardPerPerson(int level, boolean isCurrencyRoom, boolean isGoldRoom) {
        int rewardPerPerson = 0;
        RollDice dice = new RollDice();

        if (level >= 1 && level <= 5) {
            rewardPerPerson = dice.rollDiceSum(10) * 5 * (int)Math.round(Math.pow(2, level - 1));
        } else if (level >= 6 && level <= 10) {
            int tempLevel = level % 2 == 1 ? level - 1 : level;
            rewardPerPerson = 50 * tempLevel + 100 * dice.rollDiceSum(tempLevel);
        } else if (level >= 11 && level <= 15) {
            rewardPerPerson = 100 * level + 50 * dice.rollDiceSum(4, 10);
        } else {
            rewardPerPerson = 500 * level + 1000 * dice.rollDiceSum(20);
        }

        if (isCurrencyRoom) {
            rewardPerPerson *= 2;
        }
        if (isGoldRoom) {
            rewardPerPerson *= 2;
        }

        return rewardPerPerson;
    }
}
