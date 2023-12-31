package net.wintang.zooapp.service;

import net.wintang.zooapp.dto.mapper.FoodMapper;
import net.wintang.zooapp.dto.request.FoodRequestDTO;
import net.wintang.zooapp.dto.response.ResponseObject;
import net.wintang.zooapp.entity.Food;
import net.wintang.zooapp.entity.User;
import net.wintang.zooapp.exception.NotFoundException;
import net.wintang.zooapp.repository.FoodRepository;
import net.wintang.zooapp.util.ApplicationConstants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

@Service
public class FoodService implements IFoodService {

    private final FoodRepository foodRepository;

    @Autowired
    public FoodService(FoodRepository foodRepository) {
        this.foodRepository = foodRepository;
    }

    @Override
    public ResponseEntity<ResponseObject> getFoods() {
        return ResponseEntity.status(HttpStatus.OK).body(
                new ResponseObject(ApplicationConstants.ResponseStatus.OK,
                        ApplicationConstants.ResponseMessage.SUCCESS,
                        FoodMapper.mapToFoodDto(foodRepository.findAll()))
        );
    }

    @Override
    public ResponseEntity<ResponseObject> getFoodById(int id) throws NotFoundException {
        return ResponseEntity.status(HttpStatus.OK).body(
                new ResponseObject(ApplicationConstants.ResponseStatus.OK,
                        ApplicationConstants.ResponseMessage.SUCCESS,
                        FoodMapper.mapToFoodDto(foodRepository.findById(id).orElseThrow(() -> new NotFoundException("Food ID: " + id))))
        );
    }

    @Override
    public ResponseEntity<ResponseObject> createFood(FoodRequestDTO foodRequestDTO) {
        UserDetails creator = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Food food = FoodMapper.mapToFoodEntity(foodRequestDTO);
        food.setCreator(User.builder().id(Integer.parseInt(creator.getUsername())).build());
        foodRepository.save(food);
        return ResponseEntity.status(HttpStatus.OK).body(
                new ResponseObject(ApplicationConstants.ResponseStatus.OK,
                        ApplicationConstants.ResponseMessage.SUCCESS,
                        foodRequestDTO)
        );
    }

    @Override
    public ResponseEntity<ResponseObject> updateFoodById(int id, FoodRequestDTO foodRequestDTO) throws NotFoundException {
        Food food = foodRepository.findById(id).orElseThrow(() -> new NotFoundException("Food ID: " + id));
        food.setName(foodRequestDTO.getName().trim());
        food.setStatus(foodRequestDTO.isStatus());
        food.setQuantity(food.getQuantity());
        food.setType(foodRequestDTO.getType().trim());
        foodRepository.save(food);
        return ResponseEntity.status(HttpStatus.OK).body(
                new ResponseObject(ApplicationConstants.ResponseStatus.OK,
                        ApplicationConstants.ResponseMessage.SUCCESS,
                        foodRequestDTO)
        );
    }

    @Override
    public ResponseEntity<ResponseObject> deleteFoodById(int id) throws NotFoundException {
        if (foodRepository.existsById(id)) {
            try {
                foodRepository.deleteById(id);
                return ResponseEntity.status(HttpStatus.OK).body(
                        new ResponseObject(ApplicationConstants.ResponseStatus.OK,
                                ApplicationConstants.ResponseMessage.SUCCESS,
                                id)
                );
            } catch (DataAccessException ex) {
                Food food = foodRepository.findById(id).get();
                food.setStatus(false);
                foodRepository.save(food);
                return ResponseEntity.status(HttpStatus.OK).body(
                        new ResponseObject(ApplicationConstants.ResponseStatus.OK,
                                ApplicationConstants.ResponseMessage.SUCCESS,
                                id)
                );
            }
        }
        throw new NotFoundException("Food ID: " + id);
    }
}
