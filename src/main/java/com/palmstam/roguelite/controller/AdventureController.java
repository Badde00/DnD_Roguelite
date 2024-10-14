package com.palmstam.roguelite.controller;

import com.palmstam.roguelite.model.RollDice;
import com.palmstam.roguelite.model.data.*;
import com.palmstam.roguelite.model.databaseItems.Enemy;
import com.palmstam.roguelite.model.databaseItems.Item;
import com.palmstam.roguelite.model.room.Room;
import com.palmstam.roguelite.model.room.UnavailableRoom;
import com.palmstam.roguelite.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

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

                items.add(new Item(dto));
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

    @PostMapping("addEnemies/csv")
    ResponseEntity<ApiResponse<?>> addEnemyToDatabase(@RequestParam("file") MultipartFile file) {
        /*
        * Set body to form data, set the key to 'file' and the value to the actual file
        * and the Content-Type header to multipart/form-data
        */
        EnemyCsvParserService ecps = new EnemyCsvParserService();
        List<Enemy> enemies;
        try {
            List<EnemyDTO> enemyDTOs = ecps.parseCsv(file);

            Set<String> existingEnemyNames = enemyRepository.findAll()
                    .stream()
                    .map(Enemy::getName)
                    .collect(Collectors.toSet());
            Set<String> processedNames = new HashSet<>();

            enemies = enemyDTOs.stream()
                    .map(Enemy::new)
                    .filter(enemy -> {
                        String name = enemy.getName();
                        // Check if name is in the database or was processed earlier in the CSV
                        if (existingEnemyNames.contains(name) || processedNames.contains(name)) {
                            return false;
                        } else {
                            processedNames.add(name);  // Add name to processed set
                            return true;
                        }
                    })
                    .collect(Collectors.toList());

            if (enemies.isEmpty()) {
                ApiResponse<String> response = new ApiResponse<>("error", "No new enemies to add. All names already exist.");
                return new ResponseEntity<>(response, HttpStatus.CONFLICT);
            }

            enemyRepository.saveAll(enemies);
            ApiResponse<List<Enemy>> response = new ApiResponse<>("success", enemies);
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            ApiResponse<String> response = new ApiResponse<>("error", "Could not create the desired enemies.");
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        }
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
