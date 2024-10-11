package com.palmstam.roguelite.controller;

import com.palmstam.roguelite.model.RollDice;
import com.palmstam.roguelite.model.data.ApiResponse;
import com.palmstam.roguelite.model.data.GenerateRoomDTO;
import com.palmstam.roguelite.model.data.ItemDTO;
import com.palmstam.roguelite.model.databaseItems.Enemy;
import com.palmstam.roguelite.model.databaseItems.Item;
import com.palmstam.roguelite.model.room.Room;
import com.palmstam.roguelite.model.room.UnavailableRoom;
import com.palmstam.roguelite.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;
import java.util.stream.Collectors;


@RestController
@RequestMapping("roguelite")
public class AdventureController {
    private Generator generator;

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

    public AdventureController(
            EnemyRepository enemyRepository,
            DealRepository dealRepository,
            GamblingGamesRepository gamblingGamesRepository,
            PuzzleRepository puzzleRepository,
            ItemRepository itemRepository,
            SocialNPCRepository socialNPCRepository
    ) {
        this.generator = new Generator(enemyRepository, dealRepository, gamblingGamesRepository, puzzleRepository, itemRepository, socialNPCRepository);
        this.enemyRepository = enemyRepository;
        this.dealRepository = dealRepository;
        this.gamblingGamesRepository = gamblingGamesRepository;
        this.puzzleRepository = puzzleRepository;
        this.itemRepository = itemRepository;
        this.socialNPCRepository = socialNPCRepository;
    }

    @PostMapping("addItems")
    public ResponseEntity<ApiResponse<?>> addItemsToDatabase(@RequestBody List<ItemDTO> itemDTOs) {
        List<Item> items = new ArrayList<>();
        Set<String> itemNames = new HashSet<>();
        try {
            for (ItemDTO dto : itemDTOs) {
                // Check if an item with the same name already exists
                Optional<Item> existingItem = itemRepository.findByName(dto.getName());

                if (existingItem.isPresent() || itemNames.contains(dto.getName())) {
                    // If the item already exists, skip it
                    System.out.println("Item with name '" + dto.getName() + "' already exists. Skipping...");
                    continue;
                }

                itemNames.add(dto.getName());

                Item item = new Item();
                item.setName(dto.getName());

                if (dto.getEntries() != null && !dto.getEntries().isEmpty()) {
                    item.setDescription(dto.getEntries().getFirst().toString());
                }

                item.setRarity(dto.getRarity());
                item.setType(Item.cleanType(dto.getType()));

                if (dto.getPrice() != 0) {
                    item.setPrice(dto.getPrice() / 100);
                } else if (dto.isMundane()) {
                    item.setPrice(-1);
                } else {
                    int price = Item.getPrice(item);
                    item.setPrice(price);
                }

                item.setMundane(dto.isMundane());
                item.setSource(dto.getSource());
                item.setPage(dto.getPage());

                items.add(item);
            }

            if (!items.isEmpty()) {
                this.itemRepository.saveAll(items);
            } else {
                return new ResponseEntity<>(new ApiResponse<>("No new items added. All items already existed.", items), HttpStatus.OK);
            }
        } catch (Exception e) {
            System.err.println("e: " + e);
            ApiResponse<Exception> response = new ApiResponse<>("error", e);
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        }

        ApiResponse<List<Item>> response = new ApiResponse<>("success", items);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PostMapping ResponseEntity<ApiResponse<?>> addEnemyToDatabase(@RequestBody List<Enemy> enemies) {
        return null;
    }

    @GetMapping("items")
    public ResponseEntity<ApiResponse<List<Item>>> getAllItems() {
        List<Item> items = this.itemRepository.findAll();
        ApiResponse<List<Item>> response = new ApiResponse<>("success", items);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }


    @GetMapping("room")
    public ResponseEntity<ApiResponse<?>> generateRoom(@RequestBody GenerateRoomDTO grdto) {
        Room room = generator.generateRoom(grdto.getEncounterType(), grdto.getLevel(), grdto.getNumberOfPlayers());

        ApiResponse<?> response;
        if (room instanceof UnavailableRoom) {
            response = new ApiResponse<>("error", "Could not generate room of desired type.");
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        } else {
            response = new ApiResponse<>("success", room);
            return new ResponseEntity<>(response, HttpStatus.OK);
        }
    }

    @GetMapping("test")
    public ResponseEntity<ApiResponse<?>> test() {
        ApiResponse<Room> response = new ApiResponse<>("success", generator.generateRoom("Social Room", 2, 4));
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}