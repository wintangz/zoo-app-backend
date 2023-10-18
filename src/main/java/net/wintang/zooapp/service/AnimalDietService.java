package net.wintang.zooapp.service;

import net.wintang.zooapp.dto.mapper.AnimalDietMapper;
import net.wintang.zooapp.dto.request.AnimalDietRequestDto;
import net.wintang.zooapp.dto.response.ResponseObject;
import net.wintang.zooapp.entity.AnimalDiet;
import net.wintang.zooapp.entity.User;
import net.wintang.zooapp.exception.NotFoundException;
import net.wintang.zooapp.repository.AnimalDietRepository;
import net.wintang.zooapp.util.ApplicationConstants;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

@Service
public class AnimalDietService implements IAnimalDietService {

    private final AnimalDietRepository animalDietRepository;

    public AnimalDietService(AnimalDietRepository animalDietRepository) {
        this.animalDietRepository = animalDietRepository;
    }

    @Override
    public ResponseEntity<ResponseObject> getAnimalDiets() {
        return ResponseEntity.status(HttpStatus.OK).body(
                new ResponseObject(ApplicationConstants.ResponseStatus.OK,
                        ApplicationConstants.ResponseMessage.SUCCESS,
                        AnimalDietMapper.mapToDietDto(animalDietRepository.findAll()))
        );
    }

    @Override
    public ResponseEntity<ResponseObject> getAnimalDietById(int id) throws NotFoundException {
        return ResponseEntity.status(HttpStatus.OK).body(
                new ResponseObject(ApplicationConstants.ResponseStatus.OK,
                        ApplicationConstants.ResponseMessage.SUCCESS,
                        AnimalDietMapper.mapToDietDto(animalDietRepository.findById(id).orElseThrow(() -> new NotFoundException("Diet ID: " + id))))
        );
    }

    @Override
    public ResponseEntity<ResponseObject> createAnimalDiet(AnimalDietRequestDto animalDietRequestDto) {
        UserDetails authenticatedUser = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        System.out.println(animalDietRequestDto.getFoodListIds());
        AnimalDiet animalDiet = AnimalDietMapper.mapToDietEntity(animalDietRequestDto);
        animalDiet.setCreator(User.builder().id(Integer.parseInt(authenticatedUser.getUsername())).build());
        animalDietRepository.save(animalDiet);
        return ResponseEntity.status(HttpStatus.OK).body(
                new ResponseObject(ApplicationConstants.ResponseStatus.OK,
                        ApplicationConstants.ResponseMessage.SUCCESS,
                        null)
        );
    }

    @Override
    public ResponseEntity<ResponseObject> deleteAnimalDIet(int id) throws NotFoundException {
        if (animalDietRepository.existsById(id)) {
            animalDietRepository.deleteById(id);
            return ResponseEntity.status(HttpStatus.OK).body(
                    new ResponseObject(ApplicationConstants.ResponseStatus.OK,
                            ApplicationConstants.ResponseMessage.SUCCESS,
                            id)
            );
        }
        throw new NotFoundException("Diet ID: " + id);
    }
}
