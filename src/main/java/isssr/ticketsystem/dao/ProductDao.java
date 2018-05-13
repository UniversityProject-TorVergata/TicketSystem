package isssr.ticketsystem.dao;

import isssr.ticketsystem.entity.Product;
import isssr.ticketsystem.entity.RegisteredUser;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductDao extends JpaRepository<Product, Long> {
}
