package com.lcwd.electronic.store.repositories;

import com.lcwd.electronic.store.entites.Category;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepository extends JpaRepository<Category,String> {
}
