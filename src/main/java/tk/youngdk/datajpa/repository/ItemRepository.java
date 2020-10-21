package tk.youngdk.datajpa.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import tk.youngdk.datajpa.domain.Item;

public interface ItemRepository extends JpaRepository<Item, Long> {
}
