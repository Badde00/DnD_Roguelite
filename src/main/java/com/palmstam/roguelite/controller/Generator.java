package com.palmstam.roguelite.controller;

import com.palmstam.roguelite.model.databaseItems.*;
import com.palmstam.roguelite.model.RollDice;
import com.palmstam.roguelite.model.room.*;
import com.palmstam.roguelite.repository.*;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
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

    @Autowired
    private final ItemRepository itemRepository;

    @Autowired
    private final SocialNPCRepository socialNPCRepository;

    public Generator(
            EnemyRepository enemyRepository,
            DealRepository dealRepository,
            GamblingGamesRepository gamblingGamesRepository,
            PuzzleRepository puzzleRepository,
            ItemRepository itemRepository,
            SocialNPCRepository socialNPCRepository
    ) {
        random = new Random();
        this.enemyRepository = enemyRepository;
        this.dealRepository = dealRepository;
        this.gamblingGamesRepository = gamblingGamesRepository;
        this.puzzleRepository = puzzleRepository;
        this.itemRepository = itemRepository;
        this.socialNPCRepository = socialNPCRepository;
    }

    public Room generateRoom(String encounterType, int level, int numberOfPlayers) {
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
                return generateShopRoom(level, numberOfPlayers);
            case "Social Room":
                return generateSocialRoom(level);
            case "Treasure Room":
                return generateTreasureRoom(level, numberOfPlayers);
            default:
                return new UnavailableRoom();
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

    private ShopRoom generateShopRoom(int level, int numberOfPlayers) {
        // The possible rarities of items, where none are mundane items (or items who erroneously were included while they didn't have rarities
        List<String> rarities = List.of("artifact", "legendary", "very rare", "rare", "uncommon", "common", "none");

        // Choose the highest available rarity of item and how many mundane weapons/armors should be available
        // Shop should have 1 deal item of the highest rarity, one full price item of the highest rarity, (numberOfPlayers - 1) number of
        // items of one lower rarity and the rest should be of two rarities lower.
        int highestRarityPosition = 0;
        int numberOfWeaponsAndArmors = 0;
        if (level <= 3) {
            highestRarityPosition = 4;
            numberOfWeaponsAndArmors = 1;
        } else if (level <= 7) {
            highestRarityPosition = 3;
            numberOfWeaponsAndArmors = 2;
        } else if (level <= 11) {
            highestRarityPosition = 2;
            numberOfWeaponsAndArmors = 4;
        } else if (level <= 15) {
            highestRarityPosition = 1;
            numberOfWeaponsAndArmors = 6;
        }
        int lowerRarityPosition = highestRarityPosition + 1;
        int lowestRarityPosition = highestRarityPosition + 2;

        // Deal item
        Item dealItem = chooseRandom(itemRepository.findByRarity(rarities.get(highestRarityPosition)));
        // Highest rarity item
        Item highestRarityItem = chooseRandom(itemRepository.findByRarity(rarities.get(highestRarityPosition)));

        // All items of lower rarities
        List<Item> lowerRarityItems = itemRepository.findByRarity(rarities.get(lowerRarityPosition));
        List<Item> lowestRarityItems = itemRepository.findByRarity(rarities.get(lowestRarityPosition));

        // Make wares and add highest rarity item
        List<Item> wares = new ArrayList<>();
        wares.add(highestRarityItem);

        // Add lower rarity items
        for (int i = 0; i < numberOfPlayers - 1; i++) {
            wares.add(chooseRandom(lowerRarityItems));
        }
        for (int i = 0; i < numberOfPlayers; i++) {
            wares.add(chooseRandom(lowestRarityItems));
        }

        // Find all mundane weapons and armors
        List<Item> mundaneWeapons = itemRepository.findByIsMundaneAndType(true, "weapon");
        List<Item> mundaneArmors = itemRepository.findByIsMundaneAndTypeIn(true, List.of("light armor", "medium armor", "heavy armor"));

        // Add mundane weapons and armors based on numberOfWeaponsAndArmors
        for (int i = 0; i < numberOfWeaponsAndArmors; i++) {
            wares.add(chooseRandom(mundaneWeapons));
            wares.add(chooseRandom(mundaneArmors));
        }



        return ShopRoom.builder()
                .encounterType("Shop Room")
                .description("A wonderful place where you can buy items in return for gold. It's not recommended to steal or attack the merchant...")
                .level(level)
                .shopDescription("A stony cave with a Lizardfolk merchant sitting on a chequered rug with various items spread out before them.")
                .wares(wares)
                .itemDeal(dealItem)
                .build();
    }

    private SocialRoom generateSocialRoom(int level) {
        SocialNpc npc = null;
        if (level <= 5) {
            npc = socialNPCRepository.getReferenceById(1);
        } else if (level <= 10) {
            npc = socialNPCRepository.getReferenceById(2);
        } else if (level <= 15) {
            npc = socialNPCRepository.getReferenceById(3);
        } else if (level <= 20) {
            npc = socialNPCRepository.getReferenceById(4);
        }

        if (npc == null) return null;

        return SocialRoom.builder()
                .encounterType("Social Room")
                .description("A place to meet unique individuals")
                .level(level)
                .personDescription(npc.getPersonDescription())
                .roomDescription(npc.getRoomDescription())
                .gift(npc.getGift())
                .build();
    }

    private TreasureRoom generateTreasureRoom(int level, int numberOfPlayers) {
        String[] currencies = new String[5];
        currencies[0] = (int)((rollCurrencyRewardPerPerson(level, true, true) * 1.5) * numberOfPlayers) + " gold";
        currencies[1] = (rollCurrencyRewardPerPerson(level, false, false) * numberOfPlayers) + " electrum";
        currencies[2] = (rollCurrencyRewardPerPerson(level, false, false) * numberOfPlayers) + " gemstones";
        currencies[3] = (rollCurrencyRewardPerPerson(level, false, false) * numberOfPlayers) + " faith";
        currencies[4] = (rollCurrencyRewardPerPerson(level, false, false) * numberOfPlayers) + " artwork";

        List<String> rarities = List.of("artifact", "legendary", "very rare", "rare", "uncommon", "common", "none");
        int highestRarityPosition = 0;
        if (level <= 3) {
            highestRarityPosition = 4;
        } else if (level <= 5) {
            highestRarityPosition = 3;
        } else if (level <= 9) {
            highestRarityPosition = 2;
        } else if (level <= 13) {
            highestRarityPosition = 1;
        }
        List<Item> magicItems = itemRepository.findByRarity(rarities.get(highestRarityPosition));
        List<Item> mundaneItems = itemRepository.findByIsMundaneAndTypeIn(true, List.of("light armor", "medium armor", "heavy armor", "weapon"));
        List<Item> items = new ArrayList<>();
        for (int i = 0; i < numberOfPlayers + 1; i++) {
            items.add(chooseRandom(magicItems));
            items.add(chooseRandom(mundaneItems));
        }

        return TreasureRoom.builder()
                .encounterType("Treasure Room")
                .description("Piles of gold, electrum, other valuables and magic items lie in a cave.")
                .level(level)
                .currencies(currencies)
                .items(items)
                .build();
    }

    private CombatRoom generateCombatRoom(int level, int numberOfPlayers) {


        return null;
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
