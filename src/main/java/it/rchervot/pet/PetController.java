package it.rchervot.pet;

import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/pet")
public class PetController {
    private final PetService petService;

    public PetController(PetService petService) {
        this.petService = petService;
    }

    @GetMapping()
    public List<Pet> findAll() {
        return petService.findAll();
    }

    @GetMapping("/{id}")
    public Optional<Pet> findById(@PathVariable Long id) {
        return petService.findById(id);
    }

    @PostMapping()
    public Pet create(@RequestBody Pet pet) {
        return petService.save(pet);
    }

    @PutMapping("/{id}")
    public Pet update(@RequestBody Pet pet, @PathVariable Long id) {
        return petService.findById(id).map((savedPet) -> {
            savedPet.setName(pet.getName());
            savedPet.setSpecie(pet.getSpecie());
            savedPet.setAge(pet.getAge());
            savedPet.setOwnerName(pet.getOwnerName());
            return petService.save(pet);
        }).orElseGet(() -> petService.save(pet));
    }

    @DeleteMapping("/{id}")
    public void deleteById(@PathVariable Long id) {
        petService.deleteById(id);
    }
}
