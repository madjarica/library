package rs.levi9.library.repository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicLong;
import org.springframework.stereotype.Repository;
import rs.levi9.library.domain.BaseEntity;
import rs.levi9.library.domain.Category;

@Repository
public class InMemoryCategoryRepository implements CategoryRepository {

    private Map<Long, Category> categories = new HashMap<>();
    private final AtomicLong sequence = new AtomicLong(1);

    
    @Override
    public Category findOne(Long id) throws IllegalArgumentException {
        Category category = categories.get(id);
        if (category == null) {
            throw new IllegalArgumentException();
        }
        return category;
    }

    @Override
    public List<Category> findAll() {
        return new ArrayList<>(categories.values());
    }

    @Override
    public void delete(Long id) throws IllegalArgumentException {
        if (!categories.containsKey(id)) {
            throw new IllegalArgumentException("category does not exist");
        }
        categories.remove(id);
        
    }

    @Override
    public <T extends BaseEntity> T save(T entity) {
        if (entity.getId() == null) {
            entity.setId(sequence.getAndIncrement());
        }
        categories.put(entity.getId(), (Category) entity);
        return entity;
    }

}
